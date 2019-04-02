package com.bonsucesso.erp.crm.view;



import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crm.data.CupomDataMapper;
import com.bonsucesso.erp.crm.data.CupomFilterFinder;
import com.bonsucesso.erp.crm.data.CupomFinder;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.crm.model.Cupom;
import com.bonsucesso.erp.crm.model.StatusCupom;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * FormListView para a entidade Cupom.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("cupomFormListView")
@Scope("view")
public class CupomFormListView extends
		AbstractEntityFormListView<Cupom, CupomDataMapper, CupomFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -8545775754929956975L;

	protected static Logger logger = Logger.getLogger(CupomFormListView.class);

	private boolean vinculavel = false;

	@Autowired
	private ClienteFormListView clienteFormListView;

	@Autowired
	private CupomFilterFinder filterFinder;

	@PostConstruct
	public void init() {
		getFiltros().put("status", new ArrayList<StatusCupom>());
		JSFUtils.execute("loadList()");
	}

	@Override
	public CupomFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(CupomFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	@Override
	protected void handleFilterDatas() {

		getFilterDatas().add(new FilterData("status", FilterType.IN));
		getFilterDatas().add(new FilterData("loteCupom.campanhaPromo.descricao", FilterType.LIKE_ANYWHERE));
		getFilterDatas().add(new FilterData("cliente.pessoa.nome", FilterType.LIKE_ANYWHERE));

	}

	public ClienteFormListView getClienteFormListView() {
		return clienteFormListView;
	}

	public void setClienteFormListView(ClienteFormListView clienteFormListView) {
		this.clienteFormListView = clienteFormListView;
	}

	public boolean isVinculavel() {
		return vinculavel;
	}

	public void setVinculavel(boolean vinculavel) {
		this.vinculavel = vinculavel;
	}

	public void vincular() {
		getE().setStatus(StatusCupom.VINCULADO);
		getE().setDtVinculacao(new Date());
		save();
	}

	public void utilizar() {
		getE().setStatus(StatusCupom.UTILIZADO);
		getE().setDtUtilizacao(new Date());
		save();
	}

	public void cancelar() {
		getE().setStatus(StatusCupom.CANCELADO);
		getE().setDtCancelamento(new Date());
		save();
	}

	public void updateCupomVincular() {
		setVinculavel(false);
		JSFUtils.setSaved(true);
		try {
			Cupom cupom = getFinder().findBy(getE().getCodigo());
			if (cupom == null) {
				JSFUtils.addWarnMsg("Nenhum cupom encontrado com este código.");
				JSFUtils.setFocus("frm:iCupomCodigo");
				getE().setCodigo("");
			} else {
				setE(cupom);
				if ((cupom.getCliente() != null) && (cupom.getCliente().getId() != null)) {
					JSFUtils.addErrorMsg("Este cupom já está vinculado a um cliente.");
					JSFUtils.setFocus("frm:iCupomCodigo");
					getE().setCodigo("");
				} else {
					JSFUtils.addInfoMsg("Selecione o cliente a ser vinculado.");
					JSFUtils.setFocus("frm:iCpf");
					setVinculavel(true);
				}
			}
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		} catch (Throwable t) {
			System.out.println(t);
		}
	}

	public void updateCupomUtilizar() {
		setVinculavel(false);
		JSFUtils.setSaved(true);
		try {
			Cupom cupom = getFinder().findBy(getE().getCodigo());
			if (cupom == null) {
				JSFUtils.addWarnMsg("Nenhum cupom encontrado com este código.");
				JSFUtils.setFocus("frm:iCupomCodigo");
				getE().setCodigo("");
			} else {
				setE(cupom);
				if (!cupom.getStatus().equals(StatusCupom.VINCULADO)) {
					JSFUtils.addErrorMsg("Cupom não pode ser utilizado. Verifique o status.");
					JSFUtils.setFocus("frm\\\\:iCupomCodigo");
					getE().setCodigo("");
				} else {
					//JSFUtils.setFocus("frm:btnUtilizar");
					JSFUtils.addCallbackParam("focusOn", "btnUtilizar");
					//JSFUtils.execute("PF('btnUtilizar').setFocus();");
				}
			}
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void selCliente(Cliente cliente) {
		try {
			getE().setCliente(cliente);
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao selecionar o cliente.");
		}
	}

	public void updateCpf() {
		try {
			Cliente cliente = getClienteFormListView().getFinder().findClienteByDoc(getE().getCliente().getPessoa()
					.getDocumento());
			if (cliente == null) {
				JSFUtils.addInfoMsg("Nenhum cliente encontrado");
			} else {
				getE().setCliente(cliente);
			}
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao pesquisar cliente pelo CPF");
			logger.error(e);
		}
	}

	@Override
	public void afterSave() {
		// TODO Auto-generated method stub
		super.afterSave();
	}

	public void saveCliente() {
		getClienteFormListView().save();
		getE().setCliente(getClienteFormListView().getE());
	}

	@Override
	public void afterSetE(Cupom e) {
		if (getE().getCliente() == null) {
			getE().setCliente(new Cliente());
		}
	}

}
