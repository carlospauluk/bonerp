package com.bonsucesso.erp.rh.view;



import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.data.AvaliacaoVendaFinder;
import com.bonsucesso.erp.rh.data.CargoFinder;
import com.bonsucesso.erp.rh.model.AvaliacaoTipoResposta;
import com.bonsucesso.erp.rh.model.AvaliacaoVenda;
import com.bonsucesso.erp.rh.model.Cargo;
import com.ocabit.base.view.AbstractListMaker;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListMaker para as entidades do pacote com.bonsucesso.erp.rh.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("lmRH")
@Scope("view")
public class ListMaker implements AbstractListMaker {

	/**
	 *
	 */
	private static final long serialVersionUID = 7937618090802314740L;

	protected static Logger logger = Logger.getLogger(ListMaker.class);

	@Autowired
	private CargoFinder cargoFinder;

	@Autowired
	private AvaliacaoVendaFinder avaliacaoVendaFinder;

	private List<Cargo> cargos;

	private List<AvaliacaoTipoResposta> avaliacaoTipoRespostaValues;

	private List<AvaliacaoVenda> avaliacaoVendaValues;

	public CargoFinder getCargoFinder() {
		return cargoFinder;
	}

	public void setCargoFinder(CargoFinder cargoFinder) {
		this.cargoFinder = cargoFinder;
	}

	public List<Cargo> getCargos() {
		try {
			cargos = getCargoFinder().findAll("descricao");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public List<AvaliacaoTipoResposta> getAvaliacaoTipoRespostaValues() {
		avaliacaoTipoRespostaValues = Arrays.asList(AvaliacaoTipoResposta.values());
		return avaliacaoTipoRespostaValues;
	}

	public void setAvaliacaoTipoRespostaValues(List<AvaliacaoTipoResposta> avaliacaoTipoRespostaValues) {
		this.avaliacaoTipoRespostaValues = avaliacaoTipoRespostaValues;
	}

	public AvaliacaoVendaFinder getAvaliacaoVendaFinder() {
		return avaliacaoVendaFinder;
	}

	public void setAvaliacaoVendaFinder(AvaliacaoVendaFinder avaliacaoVendaFinder) {
		this.avaliacaoVendaFinder = avaliacaoVendaFinder;
	}

	public List<AvaliacaoVenda> getAvaliacaoVendaValues() {
		try {
			if (avaliacaoVendaValues == null) {
				avaliacaoVendaValues = getAvaliacaoVendaFinder().findAll("descricao");
			}
			return avaliacaoVendaValues;
		} catch (ViewException e) {
			logger.error("Erro no ListMaker RH - getAvaliacaoVendaValues");
			return null;
		}
	}

	public void setAvaliacaoVendaValues(List<AvaliacaoVenda> avaliacaoVendaValues) {
		this.avaliacaoVendaValues = avaliacaoVendaValues;
	}

}
