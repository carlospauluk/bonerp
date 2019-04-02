package com.bonsucesso.erp.fiscal.business;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.Endereco;
import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.NotaFiscalItem;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.strings.StringUtils;

import br.inf.portalfiscal.nfe.TNFe.InfNFe.Det;
import br.inf.portalfiscal.nfe.TNFe.InfNFe.Det.Imposto.ICMS;
import br.inf.portalfiscal.nfe.TNfeProc;


@Component("parseNFe")
public class ParseNFe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8993550631789912400L;

	protected static Logger logger = Logger.getLogger(ParseNFe.class);

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			JAXBContext context = JAXBContext.newInstance(TNfeProc.class);
			Unmarshaller u = context.createUnmarshaller();
			//File file = new File("D:/ocabit/bonerp/trunk/bonerp-businessdata-jar/src/main/java/com/bonsucesso/erp/nfe/41160777498442000134550010000005041500005070-procNfe.xml");
			File file = new File("D:/ocabit/bonerp/trunk/bonerp-businessdata-jar/src/main/java/com/bonsucesso/erp/nfe/sombrinhas-35160751562031000177550010000078981001642037-nfe.xml");
			JAXBElement<TNfeProc> t = (JAXBElement<TNfeProc>) u.unmarshal(file);
			TNfeProc tNfeProc = t.getValue();
			System.out.println(tNfeProc.getNFe().getInfNFe().getEmit());
			System.out.println("bla");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public NotaFiscal parseXML(String strXML) throws ViewException {
		return parseXML(StringUtils.stringToinputStream(strXML));
	}

	@SuppressWarnings("unchecked")
	public NotaFiscal parseXML(InputStream is) throws ViewException {
		try {
			NotaFiscal nf = new NotaFiscal();
			
			// Clono o InputStream para não ler diretamente dele (e assim mantê-lo intacto caso precise ser lido novamente)
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte [] content = IOUtils.toByteArray(is);
			IOUtils.copy(new ByteArrayInputStream(content), baos);
			InputStream isClone = new ByteArrayInputStream(content);
		
			// Já seta o arquivo
			nf.setXmlNota(StringUtils.inputStreamToString(new ByteArrayInputStream(content)));

			JAXBContext context = JAXBContext.newInstance(TNfeProc.class);
			Unmarshaller u = context.createUnmarshaller();
			JAXBElement<TNfeProc> t = (JAXBElement<TNfeProc>) u.unmarshal(isClone);
			TNfeProc tNfeProc = t.getValue();
			// tenta encontrar o fornecedor pelo CNPJ
			String cnpj = tNfeProc.getNFe().getInfNFe().getEmit().getCNPJ();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			nf.setNumero(Integer.valueOf(tNfeProc.getNFe().getInfNFe().getIde().getNNF()));
			nf.setDtEmissao(sdf.parse(tNfeProc.getNFe().getInfNFe().getIde().getDhEmi().substring(0, 10)));
			nf.setInformacoesComplementares(tNfeProc.getNFe().getInfNFe().getInfAdic().getInfCpl());
			nf.setValorTotal(new BigDecimal(tNfeProc.getNFe().getInfNFe().getTotal().getICMSTot().getVNF()));

			try {
				Fornecedor fornecedor = getFornecedorFinder().findByDoc(cnpj);
				 // detach para não tentar persistir antes
				// se não encontrou, gera um novo
				if (fornecedor == null) {
					fornecedor = new Fornecedor();
					fornecedor.setPessoa(new Pessoa());
				}
				// *** ATENÇÃO: perceba que aqui, mesmo caso já exista um fornecedor com o CNPJ, os dados estão sendo (re)setados. 
				// *** É NECESSÁRIO VERIFICAR SE HOUVE ALTERAÇÕES E CONFIRMAR COM O USUÁRIO NA TELA SE ELE DESEJA TROCAR OS DADOS ANTES DE SALVAR. 
				fornecedor.getPessoa().setDocumento(cnpj);
				fornecedor.getPessoa().setNome(tNfeProc.getNFe().getInfNFe().getEmit().getXNome());
				fornecedor.getPessoa().setNomeFantasia(tNfeProc.getNFe().getInfNFe().getEmit().getXFant());
				fornecedor.setInscricaoEstadual(tNfeProc.getNFe().getInfNFe().getEmit().getIE());

				fornecedor.getPessoa().setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);

				Endereco endereco = new Endereco();
				endereco.setLogradouro(tNfeProc.getNFe().getInfNFe().getEmit().getEnderEmit().getXLgr());
				endereco.setNumero(Integer.parseInt(tNfeProc.getNFe().getInfNFe().getEmit().getEnderEmit().getNro()));
				endereco.setComplemento(tNfeProc.getNFe().getInfNFe().getEmit().getEnderEmit().getXCpl());
				endereco.setBairro(tNfeProc.getNFe().getInfNFe().getEmit().getEnderEmit().getXBairro());
				endereco.setCidade(tNfeProc.getNFe().getInfNFe().getEmit().getEnderEmit().getXMun());
				endereco.setEstado(tNfeProc.getNFe().getInfNFe().getEmit().getEnderEmit().getUF().name());
				endereco.setCep(tNfeProc.getNFe().getInfNFe().getEmit().getEnderEmit().getCEP());
				fornecedor.setFone1(tNfeProc.getNFe().getInfNFe().getEmit().getEnderEmit().getFone());

				if (fornecedor.getEnderecos() != null && !fornecedor.getEnderecos().isEmpty()) {
					if (!fornecedor.getEnderecos().contains(endereco)) {
						fornecedor.getEnderecos().add(endereco);
					}
				} else {
					fornecedor.getEnderecos().add(endereco);
				}

				// Seta o fornecedor no @Transient do NotaFiscal
				getFornecedorFinder().getEntityManager().detach(fornecedor);
				nf.setPessoaEmitente(fornecedor.getPessoa());
				
			} catch (Exception e) {
				throw new ViewException("Erro ao obter fornecedor da NF", e);
			}

			List<Det> dets = tNfeProc.getNFe().getInfNFe().getDet();
			for (Det d : dets) {
				NotaFiscalItem nfeItem = new NotaFiscalItem();
				nfeItem.setNotaFiscal(nf);
				nfeItem.setCodigo(d.getProd().getCProd());
				nfeItem.setNcm(d.getProd().getNCM());
				nfeItem.setCfop(d.getProd().getCFOP());
				nfeItem.setDescricao(d.getProd().getXProd());
				nfeItem.setIcmsAliquota(ParseNFe.getICMS(d));
				nfeItem.setUnidade(d.getProd().getUCom());
				nfeItem.setOrdem(Integer.valueOf(d.getNItem()));
				nfeItem.setQtde(new BigDecimal(d.getProd().getQCom()));
				nfeItem.setValorUnit(new BigDecimal(d.getProd().getVUnCom()));
				nfeItem.setValorTotal(new BigDecimal(d.getProd().getVProd()));
				nf.getItens().add(nfeItem);
			}

			return nf;

		} catch (JAXBException e) {
			throw new ViewException("Erro ao processar arquivo da NFe", e);
		} catch (Exception e) {
			throw new ViewException("Erro ao processar arquivo da NFe", e);
		}

	}

	@SuppressWarnings("rawtypes")
	public static BigDecimal getICMS(Det d) {
		for (JAXBElement o : d.getImposto().getContent()) {
			if (o.getName().getLocalPart().equals("ICMS")) {
				ICMS icms = (ICMS) o.getValue();
				if (icms != null) {
					if (icms.getICMS00() != null) {
						return new BigDecimal(icms.getICMS00().getVICMS());
					} else if (icms.getICMS10() != null) {
						return new BigDecimal(icms.getICMS10().getVICMS());
					} else if (icms.getICMS20() != null) {
						return new BigDecimal(icms.getICMS20().getVICMS());
					} else if (icms.getICMS30() != null) {
						return new BigDecimal(icms.getICMS30().getVICMSDeson()); // FIXME: está certo?
					} else if (icms.getICMS40() != null) {
						return new BigDecimal(icms.getICMS40().getVICMSDeson()); // FIXME: está certo?
					} else if (icms.getICMS51() != null) {
						return new BigDecimal(icms.getICMS51().getVICMS());
					} else if (icms.getICMS60() != null) {
						return new BigDecimal(icms.getICMS60().getVICMSSTRet()); // FIXME: está certo?
					} else if (icms.getICMS70() != null) {
						return new BigDecimal(icms.getICMS70().getVICMS());
					} else if (icms.getICMS90() != null) {
						return new BigDecimal(icms.getICMS90().getVICMS());
					} else if (icms.getICMSSN101() != null) {
						return new BigDecimal(icms.getICMSSN101().getVCredICMSSN());
					} else if (icms.getICMSSN102() != null) {
					
					} else if (icms.getICMSSN201() != null) {
						
					} else if (icms.getICMSSN202() != null) {
						
					} else if (icms.getICMSSN500() != null) {
						
					} else if (icms.getICMSSN900() != null) {
						
					}
				}
			}
		}
		return new BigDecimal("0.00");
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

}
