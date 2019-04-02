package com.bonsucesso.erp.crm.view;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crm.data.CampanhaPromoDataMapper;
import com.bonsucesso.erp.crm.data.CampanhaPromoFinder;
import com.bonsucesso.erp.crm.data.CupomDataMapper;
import com.bonsucesso.erp.crm.data.LoteCupomDataMapper;
import com.bonsucesso.erp.crm.model.CampanhaPromo;
import com.bonsucesso.erp.crm.model.LoteCupom;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.security.spring.SecurityUtils;
import com.ocabit.utils.reports.ReportBuilder;
import com.ocabit.utils.reports.ReportBuilder.TipoImpressao;


/**
 * FormListView para a entidade CampanhaPromo.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("campanhaPromoFormListView")
@Scope("view")
public class CampanhaPromoFormListView extends
		AbstractEntityFormListView<CampanhaPromo, CampanhaPromoDataMapper, CampanhaPromoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4597815077598949480L;

	protected static Logger logger = Logger.getLogger(CampanhaPromoFormListView.class);

	@Autowired
	private LoteCupomDataMapper loteCupomDataMapper;

	@Autowired
	private CupomDataMapper cupomDataMapper;

	@Autowired
	private ReportBuilder reportBuilder;

	private LoteCupom lote;

	private Integer numCuponsGerar;

	public LoteCupomDataMapper getLoteCupomDataMapper() {
		return loteCupomDataMapper;
	}

	public void setLoteCupomDataMapper(LoteCupomDataMapper loteCupomDataMapper) {
		this.loteCupomDataMapper = loteCupomDataMapper;
	}

	public CupomDataMapper getCupomDataMapper() {
		return cupomDataMapper;
	}

	public void setCupomDataMapper(CupomDataMapper cupomDataMapper) {
		this.cupomDataMapper = cupomDataMapper;
	}

	@Override
	public ReportBuilder getReportBuilder() {
		return reportBuilder;
	}

	@Override
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}

	public LoteCupom getLote() {
		return lote;
	}

	public void setLote(LoteCupom lote) {
		this.lote = lote;
	}

	public Integer getNumCuponsGerar() {
		return numCuponsGerar;
	}

	public void setNumCuponsGerar(Integer numCuponsGerar) {
		this.numCuponsGerar = numCuponsGerar;
	}

	public void novoLote() {
		LoteCupom lote = new LoteCupom();
		lote.setCampanhaPromo(getE());
		lote.setDtEmissao(new Date());
		lote.setEmitidoPor(SecurityUtils.getUsername());

		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
		String numLote = sdf.format(new Date());

		lote.setNumLote(numLote);
		setLote(lote);
		setNumCuponsGerar(null);
	}

	public void saveLote() {
		JSFUtils.setDlgId("dlgLote");
		try {
			setLote(getLoteCupomDataMapper().save(getLote()));
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
		try {
			getCupomDataMapper().gerarCupons(getLote(), getNumCuponsGerar());
			refreshE();
			// touchs
			for (LoteCupom lote : getE().getLotes()) {
				getLoteCupomDataMapper().refresh(lote);
				lote.getCupons().size();
			}
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
		JSFUtils.setSaved(true);
	}

	public void deleteLote(LoteCupom lote) {
		try {
			setLote(getLoteCupomDataMapper().refresh(lote));
			getLoteCupomDataMapper().delete(getLote());
			refreshE();
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void imprimirLote(LoteCupom lote) {
		setLote(lote);
	}

	public void submitFormRelatorio(LoteCupom lote) {
		setLote(lote);
		JSFUtils.submitFormRelatorio();
	}

	private TipoImpressao tipoImpressao;

	public TipoImpressao getTipoImpressao() {
		return tipoImpressao;
	}

	public void setTipoImpressao(TipoImpressao tipoImpressao) {
		this.tipoImpressao = tipoImpressao;
	}

	public void submitFormCupomExemplo(CampanhaPromo campanha, String strTipoImpressao) {
		setE(campanha);
		setTipoImpressao(TipoImpressao.valueOf(strTipoImpressao));
		JSFUtils.submitFormRelatorio("btnCupomExemplo");
	}

	public void imprimir() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("loteId", getLote().getId());
			getReportBuilder().imprimir(params, "crm/rpCupomPromo", "Cupom");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void imprimirCupomExemplo() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dt_inicio", getE().getDtInicio());
			params.put("dt_fim", getE().getDtFim());
			params.put("msg_cupom", getE().getMsgCupom());
			params.put("num_lote", "LOTE-EXEMPLO");
			params.put("codigo", "1234567812345678");
			getReportBuilder().imprimir(params, "crm/rpCupomPromo-Exemplo", "CupomExemplo");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

}
