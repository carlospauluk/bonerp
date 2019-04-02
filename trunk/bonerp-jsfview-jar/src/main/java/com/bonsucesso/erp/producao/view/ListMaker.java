package com.bonsucesso.erp.producao.view;



import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.data.InstituicaoFinder;
import com.bonsucesso.erp.producao.data.TipoArtigoFinder;
import com.bonsucesso.erp.producao.data.TipoInsumoFinder;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.bonsucesso.erp.producao.model.ModoCalculoPrecoConfeccao;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.view.AbstractListMaker;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListMaker para as entidades do pacote com.bonsucesso.erp.estoque.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("lmProducao")
@Scope("view")
public class ListMaker implements AbstractListMaker {

	/**
	 *
	 */
	private static final long serialVersionUID = 8888147063726037019L;

	protected static Logger logger = Logger.getLogger(ListMaker.class);

	@Autowired
	private TipoArtigoFinder tipoArtigoFinder;

	@Autowired
	private TipoInsumoFinder tipoInsumoFinder;

	@Autowired
	private InstituicaoFinder instituicaoFinder;

	private List<TipoArtigo> tiposArtigo;

	private List<TipoInsumo> tiposInsumo;

	private List<Instituicao> instituicoes;

	private List<ModoCalculoPrecoConfeccao> modoCalculoPrecoConfeccaos;

	public TipoArtigoFinder getTipoArtigoFinder() {
		return tipoArtigoFinder;
	}

	public void setTipoArtigoFinder(TipoArtigoFinder tipoArtigoFinder) {
		this.tipoArtigoFinder = tipoArtigoFinder;
	}

	public TipoInsumoFinder getTipoInsumoFinder() {
		return tipoInsumoFinder;
	}

	public void setTipoInsumoFinder(TipoInsumoFinder tipoInsumoFinder) {
		this.tipoInsumoFinder = tipoInsumoFinder;
	}

	public List<TipoInsumo> getTiposInsumo() {
		try {
			if (tiposInsumo == null) {
				tiposInsumo = getTipoInsumoFinder().findAll("descricao");
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return tiposInsumo;
	}

	public void setTiposInsumo(List<TipoInsumo> tiposInsumo) {
		this.tiposInsumo = tiposInsumo;
	}

	public List<TipoArtigo> getTiposArtigo() {
		try {
			if (tiposArtigo == null) {
				tiposArtigo = getTipoArtigoFinder().findAll("descricao");
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return tiposArtigo;
	}

	public void setTiposArtigo(List<TipoArtigo> tiposArtigo) {
		this.tiposArtigo = tiposArtigo;
	}

	public InstituicaoFinder getInstituicaoFinder() {
		return instituicaoFinder;
	}

	public void setInstituicaoFinder(InstituicaoFinder instituicaoFinder) {
		this.instituicaoFinder = instituicaoFinder;
	}

	public List<Instituicao> getInstituicoes() {
		try {
			if (instituicoes == null) {
				instituicoes = getInstituicaoFinder().findAll("nome");
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return instituicoes;
	}

	public void setInstituicoes(List<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}

	public List<ModoCalculoPrecoConfeccao> getModoCalculoPrecoConfeccaos() {
		modoCalculoPrecoConfeccaos = Arrays.asList(ModoCalculoPrecoConfeccao.values());
		return modoCalculoPrecoConfeccaos;
	}

	public void setModoCalculoPrecoConfeccaos(List<ModoCalculoPrecoConfeccao> modoCalculoPrecoConfeccaos) {
		this.modoCalculoPrecoConfeccaos = modoCalculoPrecoConfeccaos;
	}

}
