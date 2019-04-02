package com.bonsucesso.erp.vendas.view;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.crm.data.ClienteDataMapper;
import com.bonsucesso.erp.crm.data.ClienteFinder;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.fiscal.business.NotaFiscalBusiness;
import com.bonsucesso.erp.fiscal.data.NotaFiscalFinder;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.TipoNotaFiscal;
import com.bonsucesso.erp.vendas.business.VendaBusiness;
import com.bonsucesso.erp.vendas.data.VendaDataMapper;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.StatusVenda;
import com.bonsucesso.erp.vendas.model.Venda;
import com.bonsucesso.erp.vendas.model.VendaItem;
import com.bonsucesso.erp.vendas.spartacus.business.ISpartacusHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;

import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;


/**
 * View responsável pela emissão de NFe e NFCe utilizando a plataforma Spartacus.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("emiteFiscalSpartacusView")
@Scope("view")
public final class EmiteFiscalSpartacusView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1388955130878060943L;

	protected static Logger logger = Logger.getLogger(EmiteFiscalSpartacusView.class);

	private List<Venda> list;

	private Venda venda;

	private VendaItem vendaItem;

	@Autowired
	private ClienteFinder clienteFinder;

	@Autowired
	private ClienteDataMapper clienteMapper;

	@Autowired
	private NotaFiscalFinder notaFiscalFinder;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private VendaDataMapper vendaDataMapper;

	@Autowired
	private VendaBusiness vendaBusiness;

	@Autowired
	private NotaFiscalBusiness notaFiscalBusiness;

	@Autowired
	private ISpartacusHandler spartacusHandler;

	private TipoNotaFiscal tipoNota = TipoNotaFiscal.NFCE;

	private String documento;

	private Date data = new Date();

	/**
	 * PV: Vendas a serem faturadas (pré-vendas).
	 * VF: Vendas já faturadas.
	 * FIN: Finalizadas.
	 */
	private String filtros = "PV";

	@PostConstruct
	public void init() {
		// processar();
	}

	/**
	 * Chamado pelo botão atualizar.
	 */
	public void processar() {
		logger.info(">>>>>>>>>> INICIANDO processar()");
		try {
			getVendaBusiness().processarTXTsEKTeApagarArquivos();
			logger.info(">>>>>>>>>> processarTXTsEKTeApagarArquivos()");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao processar arquivos do EKT.");
			JSFUtils.addHandledException(e);
		}

		try {
			getSpartacusHandler().atualizarStatus(getData());
			logger.info(">>>>>>>>>> atualizarStatus()");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao atualizar status das notas.");
			JSFUtils.addHandledException(e);
		}

		try {
			buscarVendas();
			logger.info(">>>>>>>>>> buscarVendas()");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao buscar vendas.");
			JSFUtils.addHandledException(e);
		}
	}

	/**
	 * 
	 */
	public void processarPV() {
		logger.info(">>>>>>>>>> INICIANDO processar()");
		try {
			getVendaBusiness().processarTXTsEKTeApagarArquivos();
			logger.info(">>>>>>>>>> processarTXTsEKTeApagarArquivos()");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao processar arquivos do EKT.");
			JSFUtils.addHandledException(e);
		}

		try {
			Venda venda = getVendaFinder().findBy(getVenda().getPv(), new Date());
			if (venda == null) {
				JSFUtils.addErrorMsg("PV não encontrado. Favor reenviar pelo EKT!");
			}
			updateVenda(venda);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar PV pelo número e mês/ano.");
			JSFUtils.addHandledException(e);
		}

		try {
			getSpartacusHandler().atualizarStatus(getVenda().getNotaFiscal());
			logger.info(">>>>>>>>>> atualizarStatus()");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao atualizar status da nota.");
			JSFUtils.addHandledException(e);
		}
	}
	
	public void setNCMGenerico(VendaItem item) {
		item.setNcm("62179000"); // Vestuário e seus acessórios, exceto de malha
		item.setNcmExistente(true);
	}

	public void buscarVendas() {
		try {
			// busca todas as vendas que não possuam notaFiscal vinculada ou que já tenham porém com status = null
			switch (getFiltros()) {
				case "PV":
					setList(getVendaFinder().findVendasASeremFaturadas(getData()));
					break;
				case "VF":
					setList(getVendaFinder().findBy(getData(), StatusVenda.VENDA));
					break;
				case "FIN":
					setList(getVendaFinder().findBy(getData(), StatusVenda.FINALIZADA));
					break;
				default:
					break; // TODO: o que retornar?
			}

		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao pesquisar notas fiscais já salvas.");
			JSFUtils.addHandledException(e);
		}
	}

	/**
	 * Marca venda como "FINALIZADA".
	 * 
	 * @param venda
	 */
	public void updateVendaFinalizada(Venda venda) {
		try {
			getVendaBusiness().finalizarVenda(venda);
			processar();
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao finalizar pré-venda.");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void updateVenda(Venda venda) {
		try {
			if (venda == null) {
				setVenda(null);
				return;
			}
			// Recalcula o total pois em alguns casos a soma dos centavos no EKT estava errada, e aí não permite o faturamento.
			venda = getVendaBusiness().recalcularTotal(venda);
			venda = getVendaFinder().refresh(venda);
			venda.getItens().size();

			if (venda.getNotaFiscal() != null) {
				setTipoNota(venda.getNotaFiscal().getTipoNotaFiscal());
				venda.getNotaFiscal().getItens().size();
			}

			if (venda.getCliente() == null) {
				venda.setCliente(new Cliente());
				venda.getCliente().getPessoa().setTipoPessoa(TipoPessoa.PESSOA_FISICA);
			} else {
				venda.getCliente().getEnderecos().size();
			}

			setDocumento(venda.getCliente().getPessoa().getDocumento());

			// como mexi no setCliente, tenho que detachar pra não tentar salvar em alguma outra transação
			getVendaFinder().getEntityManager().detach(venda);
			setVenda(venda);
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao setar venda.");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void updateTipoNota() {
		//		UIInput iCnpj = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("frm:iCnpj");
		//		UIInput iNomePJ = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("frm:iNomePJ");
		//		UIInput iCpf = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("frm:iCpf");
		//		UIInput iNomePF = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("frm:iNomePF");
		//
		//		iCnpj.resetValue();
		//		iNomePJ.resetValue();
		//		iNomePJ.setValid(true);
		//		iCpf.resetValue();
		//		iNomePF.resetValue();
		//		iNomePF.setValid(true);
		//
		//		if (getVenda().getCliente() != null && getVenda().getCliente().getId() == null) {
		//			// getVenda().setCliente(new Cliente(get));
		//		}
	}

	public void limparPessoaDestinatario() {
		TipoPessoa tipoPessoa = getVenda().getCliente().getPessoa().getTipoPessoa();
		getVenda().setCliente(new Cliente());
		getVenda().getCliente().getPessoa().setTipoPessoa(tipoPessoa);
	}

	public void incDia(int qtde) {
		setData(CalendarUtil.incDias(getData(), qtde));
		processar();
	}

	/**
	 * Alterar o CPF/CNPJ.
	 */
	public void updateDoc() {
		Cliente cliente = null;

		try {
			// Se o documento do setId for diferente do inputado (mudou o CPF/CNPJ)
			if ((getVenda().getCliente().getPessoa().getDocumento() != null)) {
				cliente = getClienteFinder().findClienteByDoc(getVenda().getCliente().getPessoa().getDocumento());

				String documento = getVenda().getCliente().getPessoa().getDocumento();
				TipoPessoa tipoPessoa = getVenda().getCliente().getPessoa().getTipoPessoa();

				if (cliente != null) {
					cliente.getEnderecos().size();
					getVenda().setCliente(cliente);
				} else {
					Cliente novoCliente = new Cliente(tipoPessoa);
					novoCliente.getPessoa().setDocumento(documento);
					getVenda().setCliente(novoCliente);
				}

				if (!"".equals(documento)) {
					if (getVenda().getCliente().getPessoa().getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA)) {
						try {
							new CPFValidator()
									.assertValid(new CPFFormatter()
											.format(getVenda().getCliente().getPessoa().getDocumento()));
						} catch (Exception e) {
							JSFUtils.addWarnMsg("CPF inválido");
						}
					} else {
						try {
							new CNPJValidator().assertValid(new CNPJFormatter()
									.format(getVenda().getCliente().getPessoa().getDocumento()));
						} catch (Exception e) {
							JSFUtils.addWarnMsg("CNPJ inválido");
						}

					}
				}

			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}

	}

	/**
	 * Salva o PV novamente (agora com os dados do cliente), salva a nota fiscal (BonERP) vinculada a venda, e manda o spartacus faturar a
	 * venda.
	 */
	public void faturarPV(boolean processar) {
		try {
			logger.info(">>>>>>>>>>>>>>> INICIANDO FATURAMENTO DE PV...");
			if (!StringUtils.isEmpty(getVenda().getCliente().getPessoa().getDocumento())) {
				// Se o endereço é novo...
				if (getVenda().getCliente() != null &&
						getVenda().getCliente().getEndereco() != null
						&& getVenda().getCliente().getEndereco().getId() == null
						&& !StringUtils.isEmpty(getVenda().getCliente().getEndereco().getLogradouro())) {
					getVenda().getCliente().getEnderecos().add(getVenda().getCliente().getEndereco());
				}
				logger.info(">>>>>>>>>>>>>>> SALVANDO O CLIENTE...");
				Cliente cliente = getClienteMapper().save(getVenda().getCliente());
				getVenda().setCliente(cliente);
			}
			logger.info(">>>>>>>>>>>>>>> SALVANDO A VENDA...");
			setVenda(getVendaDataMapper().save(getVenda()));
			logger.info(">>>>>>>>>>>>>>> SALVANDO A NF...");
			NotaFiscal nf = getVendaBusiness().saveNotaFiscalVenda(getVenda(), getTipoNota());
			getVenda().setNotaFiscal(nf);
			logger.info(">>>>>>>>>>>>>>> SALVANDO A VENDA NOVAMENTE...");
			setVenda(getVendaDataMapper().save(getVenda()));

		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao salvar PV");
			JSFUtils.addHandledException(e);
			logger.error(e);
			return;
		}

		try {
			logger.info(">>>>>>>>>>>>>>> FATURANDO PELO SPARTACUS...");
			getSpartacusHandler().faturarPV(getVenda());
			logger.info(">>>>>>>>>>>>>>> FATURADO COM SUCESSO.");
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao faturar PV");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}

		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("saved", true);
		context.addCallbackParam("dlgId", "dlgFaturar");

		setFiltros("VF");

		// Este MB é usado pela emiteFiscalSpartacus.xhtml e emiteFiscalSpartacusPV.xhtml (nesta última não manda processar).
		if (processar) {
			processar();
		}

		logger.info("Faturado com sucesso.");
		JSFUtils.addInfoMsg("Faturado com sucesso.");
		JSFUtils.addInfoMsg("Aguarde a impressão!");

		updateVenda(null);
	}

	/**
	 * Só exibe o botão faturar se tiver nestas condições.
	 * Lembrando que o botão "Faturar" serve tanto para faturar a primeira vez, como para tentar faturar novamente nos casos de erros.
	 * 
	 * @param venda
	 * @return
	 */
	public boolean permiteFaturamento(Venda venda) {

		if (venda.getNotaFiscal() != null) {
			return getNotaFiscalBusiness().permiteFaturamento(venda.getNotaFiscal());
		}

		return true;
	}
	
	
	public boolean permiteReimpressao(Venda venda) {
		return getVendaBusiness().permiteReimpressao(venda);
	}

	/**
	 * Salva uma venda (por exemplo, quando se altera o NCM de um item).
	 */
	public void saveVenda() {
		try {
			setVenda(getVendaDataMapper().save(getVenda()));
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao salvar PV");
			JSFUtils.addHandledException(e);
			logger.error(e);
			return;
		}
	}

	public void onRowEditVendaItem(RowEditEvent event) {
		// ((VendaItem) event.getObject()).getId();
		saveVenda();
	}

	public void reimprimir(Venda venda) {
		try {
			venda = getVendaFinder().refresh(venda);
			getSpartacusHandler().reimprimir(venda.getNotaFiscal());
			updateVenda(null);
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
			logger.info("Cancelando a nnf " + getVenda().getNotaFiscal().getNumero());
			getSpartacusHandler().cancelar(getVenda().getNotaFiscal());
			updateVenda(null);
			JSFUtils.addInfoMsg("Cancelamento solicitado com sucesso.");
			JSFUtils.addInfoMsg("Aguarde para consultar o status em alguns instantes...");
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public List<Venda> getList() {
		if (list == null) {
			list = new ArrayList<Venda>();
		}
		return list;
	}

	public void setList(List<Venda> list) {
		this.list = list;
	}

	public Venda getVenda() {
		if (venda == null) {
			venda = new Venda();
			venda.setCliente(new Cliente());
		}
		return venda;
	}

	public void setVenda(Venda e) {
		this.venda = e;
	}

	public VendaItem getVendaItem() {
		return vendaItem;
	}

	public void setVendaItem(VendaItem vendaItem) {
		this.vendaItem = vendaItem;
	}

	public ClienteFinder getClienteFinder() {
		return clienteFinder;
	}

	public void setClienteFinder(ClienteFinder clienteFinder) {
		this.clienteFinder = clienteFinder;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public TipoNotaFiscal getTipoNota() {
		return tipoNota;
	}

	public void setTipoNota(TipoNotaFiscal tipoNota) {
		this.tipoNota = tipoNota;
	}

	public VendaBusiness getVendaBusiness() {
		return vendaBusiness;
	}

	public void setVendaBusiness(VendaBusiness vendaBusiness) {
		this.vendaBusiness = vendaBusiness;
	}

	public NotaFiscalFinder getNotaFiscalFinder() {
		return notaFiscalFinder;
	}

	public void setNotaFiscalFinder(NotaFiscalFinder notaFiscalFinder) {
		this.notaFiscalFinder = notaFiscalFinder;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public VendaDataMapper getVendaDataMapper() {
		return vendaDataMapper;
	}

	public void setVendaDataMapper(VendaDataMapper vendaDataMapper) {
		this.vendaDataMapper = vendaDataMapper;
	}

	public ISpartacusHandler getSpartacusHandler() {
		return spartacusHandler;
	}

	public void setSpartacusHandler(ISpartacusHandler spartacusHandler) {
		this.spartacusHandler = spartacusHandler;
	}

	public String getFiltros() {
		return filtros;
	}

	public void setFiltros(String filtros) {
		this.filtros = filtros;
	}

	public ClienteDataMapper getClienteMapper() {
		return clienteMapper;
	}

	public void setClienteMapper(ClienteDataMapper clienteMapper) {
		this.clienteMapper = clienteMapper;
	}

	public NotaFiscalBusiness getNotaFiscalBusiness() {
		return notaFiscalBusiness;
	}

	public void setNotaFiscalBusiness(NotaFiscalBusiness notaFiscalBusiness) {
		this.notaFiscalBusiness = notaFiscalBusiness;
	}

}
