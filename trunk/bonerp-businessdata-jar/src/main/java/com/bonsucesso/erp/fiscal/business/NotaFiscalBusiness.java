package com.bonsucesso.erp.fiscal.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.data.PessoaFinder;
import com.bonsucesso.erp.base.model.PessoaCadastro;
import com.bonsucesso.erp.crm.data.ClienteFinder;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.fiscal.data.NotaFiscalDataMapper;
import com.bonsucesso.erp.fiscal.data.NotaFiscalFinder;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.NotaFiscalItem;
import com.bonsucesso.erp.vendas.business.VendaBusiness;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;


@Component("notaFiscalBusiness")
public class NotaFiscalBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9062911424794550539L;

	protected static Logger logger = Logger.getLogger(VendaBusiness.class);

	@Autowired
	private ConfigFinder configFinder;

	@Autowired
	private NotaFiscalFinder notaFiscalFinder;

	@Autowired
	private NotaFiscalDataMapper notaFiscalDataMapper;

	@Autowired
	private PessoaFinder pessoaFinder;

	@Autowired
	private ClienteFinder clienteFinder;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	/**
	 * Só exibe o botão faturar se tiver nestas condições.
	 * Lembrando que o botão "Faturar" serve tanto para faturar a primeira vez, como para tentar faturar novamente nos casos de erros.
	 * 
	 * @param venda
	 * @return
	 */
	public boolean permiteFaturamento(NotaFiscal notaFiscal) {
		// Se o status for 100, não precisa refaturar.
		if (notaFiscal.getSpartacusStatus() != null) {
			// aprovada
			if ("100".equals(notaFiscal.getSpartacusStatus().toString())) {
				return false;
			}
			// cancelada
			if ("101".equals(notaFiscal.getSpartacusStatus().toString())) {
				return false;
			}
			if ("204".equals(notaFiscal.getSpartacusStatus().toString())) {
				return false;
			}

			if ("0".equals(notaFiscal.getSpartacusStatus().toString())) {

				if (notaFiscal.getSpartacusMensRetornoReceita().contains("DUPLICIDADE DE NF")) {
					return false;
				}
				
				if ("AGUARDANDO FATURAMENTO".equals(notaFiscal.getSpartacusMensRetornoReceita())) {
					if (notaFiscal.getDtSpartacusStatus() != null) {
						long dtStatus = notaFiscal.getDtSpartacusStatus().getTime();
						long dif = new Date().getTime() - dtStatus;
						// Se já passou 3 minutos, então permite refaturar.
						if (dif > (3 * 60 * 1000)) {
							return true;
						}
					}
					return false;
				}
			}
		}

		return true;
	}

	public void handleNotaSaida(NotaFiscal nf) throws ViewException {

		if (nf.getId() != null) {
			throw new ViewException("Nota Fiscal já tratada.");
		}

		String ambiente = getConfigFinder().findValorByChave("bonsucesso.fiscal.ambiente");

		if (ambiente == null || "".equals(ambiente)) {
			throw new ViewException("Ambiente não encontrado");
		}

		nf.setAmbiente(ambiente);

		try {
			// O Spartacus está utilizando o campo SERIE para decidir quais notas imprimir automaticamente
			// Serie 2 (Bonsucesso), Serie 3 (Ipê)
			String chave = "bonsucesso.fiscal." + nf.getTipoNotaFiscal().toString().toLowerCase() + ".serie";
			String serie = getConfigFinder().findValorByChave(chave);
			if (serie == null || "".equals(serie)) {
				throw new ViewException();
			}
			nf.setSerie(Integer.parseInt(serie));
		} catch (Exception e) {
			throw new ViewException("Erro ao obter valor para 'bonsucesso.fiscal.nfce.serie'", e);
		}

		Integer nnf = getNotaFiscalFinder()
				.findProxNumFiscal("PROD".equals(ambiente), nf.getSerie(), nf.getTipoNotaFiscal());
		nf.setNumero(nnf);

		nf.setPessoaEmitente(getPessoaFinder().findPessoaByDocumento("77498442000134"));

		try {
			String serie = getConfigFinder().findValorByChave("bonsucesso.fiscal.nfce.serie");
			if (serie == null || "".equals(serie)) {
				throw new ViewException();
			}
			nf.setSerie(Integer.parseInt(serie));
		} catch (Exception e) {
			throw new ViewException("Erro ao obter valor para 'bonsucesso.fiscal.nfce.serie'", e);
		}

	}

	/**
	 * Calcula o total da nota e o total de descontos.
	 * 
	 * @param nf
	 * @throws ViewException
	 */
	public void calcularTotais(NotaFiscal nf) throws ViewException {
		BigDecimal bdTotal = BigDecimal.ZERO;
		BigDecimal bdSubTotal = BigDecimal.ZERO;
		BigDecimal bdDescontos = BigDecimal.ZERO;
		for (NotaFiscalItem item : nf.getItens()) {
			bdTotal = bdTotal.add(item.getValorTotal());
			bdSubTotal = bdSubTotal.add(item.getSubTotal());
			bdDescontos = bdDescontos.add(item.getValorDesconto() != null ? item.getValorDesconto() : BigDecimal.ZERO);
		}

		nf.setSubTotal(bdSubTotal);
		nf.setValorTotal(bdTotal);
		nf.setTotalDescontos(bdDescontos);
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW,
			rollbackFor = { RuntimeException.class, ViewException.class })
	public NotaFiscal corrigirPessoaDestinatario(NotaFiscal nf) throws ViewException {
		try {
			String documento = nf.getPessoaDestinatario().getDocumento();

			if (nf.getPessoaCadastro() == null) {
				Cliente cliente = getClienteFinder().findClienteByDoc(documento);
				if (cliente != null) {
					nf.setPessoaCadastro(PessoaCadastro.CLIENTE);
				} else {
					Fornecedor fornecedor = getFornecedorFinder().findByDoc(documento);
					if (fornecedor != null) {
						nf.setPessoaCadastro(PessoaCadastro.FORNECEDOR);
					} else {
						throw new ViewException("Destinatário não encontrado em Clientes ou Fornecedores.");
					}
				}
			} else {
				if (PessoaCadastro.CLIENTE.equals(nf.getPessoaCadastro())) {
					Cliente cliente = getClienteFinder().findClienteByDoc(documento);
					if (cliente == null) {
						Fornecedor fornecedor = getFornecedorFinder().findByDoc(documento);
						if (fornecedor == null) {
							throw new ViewException("Destinatário não encontrado em Clientes ou Fornecedores.");
						}
					}
				} else {
					Fornecedor fornecedor = getFornecedorFinder().findByDoc(documento);
					if (fornecedor == null) {
						Cliente cliente = getClienteFinder().findClienteByDoc(documento);
						if (cliente == null) {
							throw new ViewException("Destinatário não encontrado em Clientes ou Fornecedores.");
						}
					}
				}
			}

			return getNotaFiscalDataMapper().save(nf);
		} catch (ViewException e) {
			throw new ViewException("Erro ao corrigir a pessoa/destinatário.", e);
		}

	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW,
			rollbackFor = { RuntimeException.class, ViewException.class })
	public boolean findNCMExiste(String ncm) throws ViewException {
		try {
			Query qry = getNotaFiscalFinder().getEntityManager()
					.createNativeQuery("SELECT 1 FROM fis_ncm WHERE codigo = '" + ncm + "'");
			qry.getSingleResult();
			return true;
		} catch (NonUniqueResultException e) {
			logger.info("Encontrou mais de 1: '" + ncm + "'");
			return true;
		} catch (NoResultException e) {
			logger.info("NCM não encontrado: '" + ncm + "'");
			return false;
		} catch (Exception e) {
			throw new ViewException("Erro ao pesquisar NCM na tabela de NCMs: '" + ncm + "'", e);
		}

	}

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	public NotaFiscalFinder getNotaFiscalFinder() {
		return notaFiscalFinder;
	}

	public void setNotaFiscalFinder(NotaFiscalFinder notaFiscalFinder) {
		this.notaFiscalFinder = notaFiscalFinder;
	}

	public PessoaFinder getPessoaFinder() {
		return pessoaFinder;
	}

	public void setPessoaFinder(PessoaFinder pessoaFinder) {
		this.pessoaFinder = pessoaFinder;
	}

	public ClienteFinder getClienteFinder() {
		return clienteFinder;
	}

	public void setClienteFinder(ClienteFinder clienteFinder) {
		this.clienteFinder = clienteFinder;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	public NotaFiscalDataMapper getNotaFiscalDataMapper() {
		return notaFiscalDataMapper;
	}

	public void setNotaFiscalDataMapper(NotaFiscalDataMapper notaFiscalDataMapper) {
		this.notaFiscalDataMapper = notaFiscalDataMapper;
	}

}
