package com.bonsucesso.erp.fiscal.view;



import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.data.PessoaFinder;
import com.bonsucesso.erp.base.model.IPessoa;
import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.base.model.PessoaCadastro;
import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.crm.data.ClienteDataMapper;
import com.bonsucesso.erp.crm.data.ClienteFinder;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.estoque.data.FornecedorDataMapper;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.fiscal.business.NotaFiscalBusiness;
import com.bonsucesso.erp.fiscal.data.NotaFiscalDataMapper;
import com.bonsucesso.erp.fiscal.model.ModalidadeFrete;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.NotaFiscalItem;
import com.bonsucesso.erp.fiscal.model.TipoNotaFiscal;
import com.bonsucesso.erp.vendas.spartacus.business.ISpartacusHandler;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;

import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;


/**
 * View para trabalhar com notas fiscais de saída.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("notaFiscalSaidaFormView")
@Scope("view")
public class NotaFiscalSaidaFormView extends AbstractEntityFormView<NotaFiscal, NotaFiscalDataMapper> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8092853640068326295L;

	protected static Logger logger = Logger.getLogger(NotaFiscalSaidaFormView.class);

	@Autowired
	private ISpartacusHandler spartacusHandler;

	@Autowired
	private PessoaFinder pessoaFinder;

	@Autowired
	private ClienteFinder clienteFinder;

	@Autowired
	private ClienteDataMapper clienteDataMapper;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private FornecedorDataMapper fornecedorDataMapper;

	@Autowired
	private ConfigFinder configFinder;

	@Autowired
	private NotaFiscalBusiness notaFiscalBusiness;

	private NotaFiscalItem item;

	/**
	 * Uma nota pode ser para um cliente ou para um fornecedor.
	 * -------------------------------
	 */
	//	private Cliente cliente;
	//
	//	private Fornecedor fornecedor;

	// Para poder referenciar no .xhtml
	private IPessoa pessoaDestinatario;

	private StreamedContent arquivoXML;

	/**
	 * -------------------------------
	 */

	@PostConstruct
	public void init() {

	}

	@Override
	public void afterNovo() {
		// Por padrão, para um registro novo, é um Cliente Pessoa Jurídica
		getE().setPessoaCadastro(PessoaCadastro.CLIENTE);
		setPessoaDestinatario(new Cliente(TipoPessoa.PESSOA_JURIDICA));
		getE().setPessoaDestinatario(new Pessoa(TipoPessoa.PESSOA_JURIDICA));

		getE().setTipoNotaFiscal(TipoNotaFiscal.NFE);
		getE().setDtEmissao(new Date());
		getE().setValorTotal(BigDecimal.ZERO);
	}

	@Override
	public void afterSetE(NotaFiscal e) {
		try {
			if (getE().getId() != null) {
				this.e = getSpartacusHandler().atualizarStatus(getE());
			}
		} catch (ViewException e1) {
			JSFUtils.addHandledException("Erro ao atualizar status da NFe", e1);
		}
		updateDoc();
		// Como o campo e.pessoaDestinatario pode ser null, instancio uma nova Pessoa para poder abrir a tela sem dar erro de "Target Unreachable"
		if (e.getPessoaDestinatario() == null) {
			getE().setPessoaCadastro(PessoaCadastro.CLIENTE);
			setPessoaDestinatario(new Cliente(TipoPessoa.PESSOA_JURIDICA));
			getE().setPessoaDestinatario(new Pessoa(TipoPessoa.PESSOA_JURIDICA));
			getDataMapper().detach(getE()); // detach para não querer dar update
		} else {
			try {
				this.e = getNotaFiscalBusiness().corrigirPessoaDestinatario(this.e);
			} catch (ViewException e1) {
				JSFUtils.addHandledException(e1);
			}
		}
		getE().getItens().size();

	}

	public void onRowReorder(ReorderEvent event) {

		int ultimaOrdem = 0;
		
		
		for (NotaFiscalItem item : getE().getItens()) {
			if (item.getOrdem() > ultimaOrdem) {
				ultimaOrdem = item.getOrdem();
			}
		}
		for (NotaFiscalItem item : getE().getItens()) {
			item.setOrdem(++ultimaOrdem);
			this.item = item;
			saveItem();
		}
		
		
		save();
	}

	@Override
	public void validate() throws ViewException {
		Pessoa pessoa;
		if (PessoaCadastro.CLIENTE.equals(getE().getPessoaCadastro())) {
			Cliente cliente = (Cliente) getPessoaDestinatario();
			cliente = getClienteDataMapper().save(cliente);
			pessoa = cliente.getPessoa();
			setPessoaDestinatario(cliente);
		} else {
			Fornecedor fornecedor = (Fornecedor) getPessoaDestinatario();
			fornecedor = getFornecedorDataMapper().save(fornecedor);
			pessoa = fornecedor.getPessoa();
			setPessoaDestinatario(fornecedor);
		}
		getE().setPessoaDestinatario(pessoa);
		// getE().setEntrada(false); // não, pq pode ser nota de devolução

		if (getE().getId() == null) {
			getNotaFiscalBusiness().handleNotaSaida(getE());
		}

		try {
			if (getItem() != null) {
				getDataMapper().getEntityIdHandler().validate(getItem());
			}
		} catch (Exception e) {
			JSFUtils.addHandledException(e);
			throw new ViewException(e);
		}
	}

	/**
	 * Alterar o CPF/CNPJ.
	 */
	public void updateDoc() {
		try {
			Pessoa pessoa = getE().getPessoaDestinatario();

			if (pessoa == null)
				return;

			String documento = pessoa.getDocumento();

			if (StringUtils.isNotEmpty(documento)) {

				if (PessoaCadastro.CLIENTE.equals(getE().getPessoaCadastro())) {
					Cliente cliente = getClienteFinder().findClienteByDoc(documento);
					if (cliente != null) {
						cliente.getEnderecos().size();
						cliente.getPessoa().hashCode();
					} else {
						cliente = new Cliente(pessoa.getTipoPessoa());
						cliente.getPessoa().setDocumento(documento);
					}
					setPessoaDestinatario(cliente);
				} else {
					Fornecedor fornecedor = getFornecedorFinder().findByDoc(documento);
					if (fornecedor != null) {
						fornecedor.getEnderecos().size();
						fornecedor.getPessoa().hashCode();
					} else {
						fornecedor = new Fornecedor(pessoa.getTipoPessoa());
						fornecedor.getPessoa().setDocumento(documento);
					}
					setPessoaDestinatario(fornecedor);
				}

				if (pessoa.getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA)) {
					try {
						new CPFValidator().assertValid(new CPFFormatter().format(documento));
					} catch (Exception e) {
						JSFUtils.addWarnMsg("CPF inválido");
					}
				} else {
					try {
						new CNPJValidator().assertValid(new CNPJFormatter().format(documento));
					} catch (Exception e) {
						JSFUtils.addWarnMsg("CNPJ inválido");
					}
				}
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void limparPessoaDestinatario() {
		TipoPessoa tipoPessoa = getE().getPessoaDestinatario().getTipoPessoa();
		Pessoa pessoaDestinatario = new Pessoa(tipoPessoa);
		getE().setPessoaDestinatario(pessoaDestinatario);
		//		setCliente(null);
		//		setFornecedor(null);

		if (PessoaCadastro.CLIENTE.equals(getE().getPessoaCadastro())) {
			setPessoaDestinatario(new Cliente(tipoPessoa));
		} else {
			setPessoaDestinatario(new Fornecedor(tipoPessoa));
		}

	}

	public void novoItem() {
		NotaFiscalItem item = new NotaFiscalItem();
		item.setNotaFiscal(getE());
		getE().getItens().add(item);
		setItem(item);
		item.setCfop("5102");
		item.setUnidade("UN");
	}

	public void updateItem(NotaFiscalItem item) {
		refreshE();
		setItem(getE().getItens().get(getE().getItens().indexOf(item)));
	}

	public void saveItem() {
		Long itemId = getItem().getId();
		save();
		refreshE();
		try {
			getNotaFiscalBusiness().calcularTotais(getE());
		} catch (ViewException e) {
			JSFUtils.addHandledException("Erro ao calcular totais", e);
		}
		save();
		refreshE();
		if (itemId != null) {
			setItem(getDataMapper().getEntityManager().find(NotaFiscalItem.class, itemId));
		}
	}

	public void clonarItem(NotaFiscalItem item) {
		try {
			updateItem(item);
			NotaFiscalItem novoItem = getItem().clone();
			novoItem.setOrdem(999999); // depois vai arrumar isso no beforeSave
			getE().getItens().add(novoItem);
			save();
			refreshE();
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao arredondar valor.");
			logger.error(e);
		}
	}

	public void clonar() {
		try {
			NotaFiscal clone = getE().clonar();
			setReloadAfterSave(true);
			setE(clone);
			save();
		} catch (Exception e) {
			logger.error("Erro ao clonar a Nota Fiscal", e);
			JSFUtils.addErrorMsg("Erro ao clonar a Nota Fiscal");
			JSFUtils.addHandledException(e);
		}
	}

	public void atualizarTotais() {
		try {
			getNotaFiscalBusiness().calcularTotais(getE());
			save();
		} catch (ViewException e) {
			JSFUtils.addHandledException("Erro ao calcular totais", e);
		}
	}

	public void updatePessoa(Pessoa pessoa) {
		// Como o dlgBuscaPessoa retorna apenas a Pessoa, tem que procurar de novo
		try {
			if (PessoaCadastro.CLIENTE.equals(getE().getPessoaCadastro())) {
				Cliente cliente = getClienteFinder().findByPessoa(pessoa);
				cliente.getEnderecos().size();
				getE().setPessoaDestinatario(cliente.getPessoa());
				setPessoaDestinatario(cliente);
			} else {
				Fornecedor fornecedor = getFornecedorFinder().findBy(pessoa);
				fornecedor.getEnderecos().size();
				getE().setPessoaDestinatario(fornecedor.getPessoa());
				setPessoaDestinatario(fornecedor);
			}
		} catch (ViewException e) {
			JSFUtils.addErrorMsg(e);
		}
	}

	@Override
	public String getDlgId() {
		return "dlgItem";
	}

	public void deleteItem(NotaFiscalItem item) {
		try {
			// gambiarra pra poder excluir usando a lógica da reordenação dos campos 'ordem'
			updateItem(item);
			getItem().setOrdem(9999);
			save();
			updateItem(getItem());
			getE().getItens().remove(getE().getItens().indexOf(getItem()));
			save();
			refreshE();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void faturar() {
		try {
			logger.info(">>>>>>>>>>>>>>> INICIANDO FATURAMENTO DE PV...");
			logger.info(">>>>>>>>>>>>>>> SALVANDO A VENDA...");
			save();
			getSpartacusHandler().faturar(getE());
			logger.info(">>>>>>>>>>>>>>> FATURADO COM SUCESSO.");
			refreshE();
			logger.info("Faturado com sucesso.");
			JSFUtils.addInfoMsg("Faturado com sucesso.");
			JSFUtils.addInfoMsg("Aguarde a impressão!");
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao faturar PV");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void reimprimir() {
		try {
			getSpartacusHandler().reimprimir(getE());
			JSFUtils.addInfoMsg("Reimpressão enviada com sucesso.");
			JSFUtils.addInfoMsg("Aguarde a impressão!");
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void cancelar() {
		try {
			// Manda cancela a nota fiscal pelo Spartacus (a alteração do status da Venda.class só é feita depois na releitura dos status do Spartacus).
			logger.info("Cancelando a nnf " + getE().getNumero());
			getSpartacusHandler().cancelar(getE());
			JSFUtils.addInfoMsg("Cancelamento solicitado com sucesso.");
			JSFUtils.addInfoMsg("Aguarde para consultar o status em alguns instantes...");
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void updateFrete() {
		if (!ModalidadeFrete.SEM_FRETE.equals(getE().getTranspModalidadeFrete())) {
			getE().setTranspFornecedor(new Fornecedor(TipoPessoa.PESSOA_JURIDICA));
		}
	}

	public void selTransportadora(Fornecedor fornecedor) {
		getE().setTranspFornecedor(fornecedor);
	}

	public StreamedContent getArquivoXML() {
		return arquivoXML;
	}

	public void setArquivoXML(StreamedContent arquivoXML) {
		this.arquivoXML = arquivoXML;
	}

	public void gerarXML() {

		try {

			BufferedInputStream origin = null;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte data[] = new byte[2048];
			Map<String, Object> r = getSpartacusHandler().getXML(getE().getSpartacusId());

			// Integer statusNfe = (Integer) r.get("status_nfe");
			Integer statusCanc = (Integer) r.get("status_canc");
			String nomeArquivo = r.get("a03_id") + (statusCanc == 101 ? "-CANCELADA" : "") + ".xml";
			String conteudo = (String) r.get("xml_nfe");

			System.out.println("Adding: " + nomeArquivo);
			InputStream is = new ByteArrayInputStream(conteudo.getBytes());
			origin = new BufferedInputStream(is, 2048);
			int count;
			while ((count = origin.read(data, 0, 2048)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
			out.close();

			InputStream stream = new ByteArrayInputStream(out.toByteArray());
			setArquivoXML(new DefaultStreamedContent(stream, "text/xml, application/xml", nomeArquivo));
			System.out.println("Done");

		} catch (Exception e) {
			logger.error("Erro ao gerar arquivo XML", e);
			JSFUtils.addErrorMsg("Erro ao gerar arquivo XML");
		}
	}

	//	public Cliente getCliente() {
	//		if (cliente == null) {
	//			cliente = new Cliente();
	//		}
	//		return cliente;
	//	}
	//
	//	public void setCliente(Cliente cliente) {
	//		this.cliente = cliente;
	//	}
	//
	//	public Fornecedor getFornecedor() {
	//		if (fornecedor == null) {
	//			fornecedor = new Fornecedor();
	//		}
	//		return fornecedor;
	//	}
	//
	//	public void setFornecedor(Fornecedor fornecedor) {
	//		this.fornecedor = fornecedor;
	//	}

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	public NotaFiscalItem getItem() {
		return item;
	}

	public void setItem(NotaFiscalItem item) {
		this.item = getE().getItens().get(getE().getItens().indexOf(item));
	}

	public PessoaCadastro[] getPessoasCadastroPesquisar() {
		return new PessoaCadastro[] { getE().getPessoaCadastro() };
	}

	public IPessoa getPessoaDestinatario() {
		return pessoaDestinatario;
	}

	public void setPessoaDestinatario(IPessoa pessoaDestinatario) {
		if (!(pessoaDestinatario instanceof Cliente) && !(pessoaDestinatario instanceof Fornecedor)) {
			JSFUtils.addErrorMsg("Pessoa Destinatário deve ser um Cliente ou um Fornecedor.");
		} else {
			Pessoa pessoa;
			if (pessoaDestinatario instanceof Cliente) {
				pessoa = ((Cliente) pessoaDestinatario).getPessoa();
			} else {
				pessoa = ((Fornecedor) pessoaDestinatario).getPessoa();
			}

			try {
				if (pessoa.getId() != null) {
					pessoa = getPessoaFinder().refresh(pessoa);
					getE().setPessoaDestinatario(pessoa);
				}
			} catch (ViewException e) {
				JSFUtils.addHandledException(e);
			}
			this.pessoaDestinatario = pessoaDestinatario;
		}
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

	public ISpartacusHandler getSpartacusHandler() {
		return spartacusHandler;
	}

	public void setSpartacusHandler(ISpartacusHandler spartacusHandler) {
		this.spartacusHandler = spartacusHandler;
	}

	public ClienteDataMapper getClienteDataMapper() {
		return clienteDataMapper;
	}

	public void setClienteDataMapper(ClienteDataMapper clienteDataMapper) {
		this.clienteDataMapper = clienteDataMapper;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	public FornecedorDataMapper getFornecedorDataMapper() {
		return fornecedorDataMapper;
	}

	public void setFornecedorDataMapper(FornecedorDataMapper fornecedorDataMapper) {
		this.fornecedorDataMapper = fornecedorDataMapper;
	}

	public NotaFiscalBusiness getNotaFiscalBusiness() {
		return notaFiscalBusiness;
	}

	public void setNotaFiscalBusiness(NotaFiscalBusiness notaFiscalBusiness) {
		this.notaFiscalBusiness = notaFiscalBusiness;
	}

}
