package com.bonsucesso.erp.cortinas.view;



import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.cortinas.model.OrientacaoTecido;
import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;
import com.bonsucesso.erp.estoque.data.DeptoFinder;
import com.bonsucesso.erp.estoque.model.Departamento;
import com.ocabit.base.view.AbstractListMaker;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListMaker para as entidades do pacote com.bonsucesso.erp.cortinas.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("lmCortinas")
@Scope("view")
public class ListMaker implements AbstractListMaker {

	/**
	 *
	 */
	private static final long serialVersionUID = 1906647484721146451L;

	protected static Logger logger = Logger.getLogger(ListMaker.class);

	@Autowired
	private DeptoFinder deptoFinder;

	private List<OrientacaoTecido> orientacoesTecido;

	private List<TipoArtigoCortina> tiposArtigoCortina;

	private List<Departamento> deptosArtigosCortina;

	private Departamento depto;

	public Departamento getDepto() {
		return depto;
	}

	public void setDepto(Departamento depto) {
		try {
			if ((depto != null) && (depto.getId() != null)) {
				depto = getDeptoFinder().refresh(depto);
				// touch para lazy
				depto.getSubdeptos().size();
			}
			this.depto = depto;
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public DeptoFinder getDeptoFinder() {
		return deptoFinder;
	}

	public void setDeptoFinder(DeptoFinder deptoFinder) {
		this.deptoFinder = deptoFinder;
	}

	public List<OrientacaoTecido> getOrientacoesTecido() {
		orientacoesTecido = Arrays.asList(OrientacaoTecido.values());
		return orientacoesTecido;
	}

	public void setOrientacoesTecido(List<OrientacaoTecido> orientacoesTecido) {
		this.orientacoesTecido = orientacoesTecido;
	}

	public List<TipoArtigoCortina> getTiposArtigoCortina() {
		if (tiposArtigoCortina == null) {
			tiposArtigoCortina = Arrays.asList(TipoArtigoCortina.values());
			Collections.sort(tiposArtigoCortina, new Comparator<TipoArtigoCortina>() {

				@Override
				public int compare(TipoArtigoCortina o1, TipoArtigoCortina o2) {
					return new CompareToBuilder()
							.append(o1.getLabel(), o2.getLabel())
							.toComparison();
				}
			});
		}
		return tiposArtigoCortina;
	}

	public void setTiposArtigoCortina(List<TipoArtigoCortina> tiposArtigoCortina) {
		this.tiposArtigoCortina = tiposArtigoCortina;
	}

	public List<Departamento> getDeptosArtigosCortina() {
		if (deptosArtigosCortina == null) {
			try {
				deptosArtigosCortina = getDeptoFinder().findDeptosArtigosCortina();
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
		return deptosArtigosCortina;
	}

	public void setDeptosArtigosCortina(List<Departamento> deptosArtigosCortina) {
		this.deptosArtigosCortina = deptosArtigosCortina;
	}

}
