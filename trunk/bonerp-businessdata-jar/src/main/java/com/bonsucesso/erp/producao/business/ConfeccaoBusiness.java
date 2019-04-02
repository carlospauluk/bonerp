package com.bonsucesso.erp.producao.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.business.CalculoPreco;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.producao.data.ConfeccaoDataMapper;
import com.bonsucesso.erp.producao.data.ConfeccaoFinder;
import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.ConfeccaoItem;
import com.bonsucesso.erp.producao.model.ConfeccaoItemQtde;
import com.bonsucesso.erp.producao.model.ConfeccaoPreco;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.bonsucesso.erp.producao.model.ModoCalculoPrecoConfeccao;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.data.EntityIdHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.currency.CurrencyUtils;


@Component("confeccaoBusiness")
public class ConfeccaoBusiness implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5332954596932657080L;

	protected static Logger logger = Logger.getLogger(ConfeccaoBusiness.class);

	@Autowired
	private ConfeccaoFinder confeccaoFinder;

	@Autowired
	private ConfeccaoDataMapper confeccaoDataMapper;

	@Autowired
	private CalculoPreco calculoPreco;

	@Autowired
	private EntityIdHandler entityIdHandler;

	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private GradeFinder gradeFinder;

	public EntityIdHandler getEntityIdHandler() {
		return entityIdHandler;
	}

	public void setEntityIdHandler(EntityIdHandler entityIdHandler) {
		this.entityIdHandler = entityIdHandler;
	}

	public ConfeccaoFinder getConfeccaoFinder() {
		return confeccaoFinder;
	}

	public void setConfeccaoFinder(ConfeccaoFinder confeccaoFinder) {
		this.confeccaoFinder = confeccaoFinder;
	}

	public CalculoPreco getCalculoPreco() {
		return calculoPreco;
	}

	public void setCalculoPreco(CalculoPreco calculoPreco) {
		this.calculoPreco = calculoPreco;
	}

	/**
	 * Constrói as tabelas de mapaGrade para cada item da confecção.
	 *
	 * @param confeccao
	 * @throws ViewException
	 */
	public Confeccao buildConfeccaoTable(Confeccao confeccao) throws ViewException {
		if ((confeccao == null) || (confeccao.getId() == null)) {
			throw new ViewException("id da confecção não pode ser null");
		}
		confeccao = getConfeccaoFinder().refresh(confeccao);
		// limpo as variáveis transient
		confeccao.setTiposInsumoItem(null); // marca na confecção quais os tipos de insumos dos itens (LIST)
		confeccao.setItensPorTipoInsumo(null); // marca na confecção quais os itens para cada tipo de insumo (MAP)

		// Encontro todos os TipoInsumo que contém nos itens desta confecção
		for (ConfeccaoItem item : confeccao.getItens()) {
			if (confeccao.getTiposInsumoItem().indexOf(item.getInsumo().getTipoInsumo()) < 0) {
				confeccao.getTiposInsumoItem().add(item.getInsumo().getTipoInsumo());
			}
		}
		// ordeno pela descrição do TipoInsumo
		Collections.sort(confeccao.getTiposInsumoItem(), new Comparator<TipoInsumo>() {

			@Override
			public int compare(TipoInsumo o1, TipoInsumo o2) {
				return new CompareToBuilder()
						.append(o1.getDescricao(), o2.getDescricao())
						.toComparison();
			}
		});

		// Para cada TipoInsumo que existe na confecção...
		for (TipoInsumo tipoInsumo : confeccao.getTiposInsumoItem()) {
			// vejo quais são os ConfeccaoItem deste tipo...
			List<ConfeccaoItem> itensPorTipoInsumo = getConfeccaoFinder().findConfeccaoItemBy(confeccao, tipoInsumo);
			confeccao.getItensPorTipoInsumo().put(tipoInsumo, itensPorTipoInsumo);
		}

		// Totalizações...
		confeccao.setMapaGradeQtdes(null);
		confeccao.setMapaGradeValores(null);
		// Para cada ConfeccaoItem...
		for (ConfeccaoItem item : confeccao.getItens()) {
			buildMapsItem(item);
		}

		return confeccao;
	}

	/**
	 * Constrói os mapas "gradeQtdes" e "gradeValores" do ConfeccaoItem.
	 * 
	 * @param confeccao
	 * @param item
	 */
	public void buildMapsItem(ConfeccaoItem item) {
		item.setMapaGradeQtdes(null);
		item.setMapaGradeValores(null);

		if ((item.getQtdesGrade() != null) && (!item.getQtdesGrade().isEmpty())) {

			// para cada "qtde" (que na verdade é cada gradeTamanho separada)
			for (ConfeccaoItemQtde ciQtde : item.getQtdesGrade()) {

				GradeTamanho gt = ciQtde.getGradeTamanho();

				BigDecimal qtde = nullToZero(ciQtde.getQtde());
				BigDecimal valor = nullToZero(ciQtde.getValor());

				BigDecimal qtdeNoItem = nullToZero(item.getMapaGradeQtdes().get(gt));
				BigDecimal valorNoItem = nullToZero(item.getMapaGradeValores().get(gt));

				item.getMapaGradeQtdes().put(gt, qtdeNoItem.add(qtde));
				item.getMapaGradeValores().put(gt, valorNoItem.add(valor));

				BigDecimal qtdeNaConfeccao = nullToZero(item.getConfeccao()
						.getMapaGradeQtdes().get(gt));
				BigDecimal valorNaConfeccao = nullToZero(item.getConfeccao()
						.getMapaGradeValores().get(gt));

				item.getConfeccao().getMapaGradeQtdes().put(gt, qtdeNaConfeccao.add(qtde));
				item.getConfeccao().getMapaGradeValores().put(gt, valorNaConfeccao.add(valor));

			}
		}
	}

	private BigDecimal nullToZero(BigDecimal val) {
		return val == null ? BigDecimal.ZERO : val;
	}

	public Double totalPorTipoInsumo(Confeccao confeccao, TipoInsumo tipoInsumo, GradeTamanho gradeTamanho) {
		BigDecimal bdTotal = CurrencyUtils.getBigDecimalCurrency(0.0);
		if ((tipoInsumo != null) && (gradeTamanho != null)) {
			for (ConfeccaoItem confeccaoItem : confeccao.getItensPorTipoInsumo().get(tipoInsumo)) {
				for (Map.Entry<GradeTamanho, BigDecimal> entry : confeccaoItem.getMapaGradeValores().entrySet()) {
					if (entry.getKey().equals(gradeTamanho)) {
						BigDecimal bdValor = entry.getValue();
						bdTotal = CurrencyUtils.soma(bdTotal, bdValor);
					}
				}
			}
		}
		return bdTotal.doubleValue();
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void calcularPrecos(Instituicao instituicao) throws ViewException {
		List<Confeccao> confeccoes = getConfeccaoFinder().findBy(instituicao);
		for (Confeccao confeccao : confeccoes) {
			confeccao.limparPrecos();
			confeccao = getConfeccaoDataMapper().save(confeccao);
			getConfeccaoDataMapper().getEntityManager().flush();
			confeccao = getThiz().calcularPrecos(confeccao);
			getConfeccaoDataMapper().save(confeccao);
			getConfeccaoDataMapper().getEntityManager().flush();
		}
	}

	/**
	 * @param confeccao
	 * @return
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Confeccao calcularPrecos(Confeccao confeccao) throws ViewException {

		if (confeccao.getMapaGradeValores().isEmpty()) {
			confeccao = buildConfeccaoTable(confeccao);
		}

		logger.debug("Calculando preços para '" + confeccao.getDescricao() + "'");

		if (confeccao.getModoCalculo().equals(ModoCalculoPrecoConfeccao.MODO_1)) {
			confeccao = calcularModo1(confeccao);
		} else if (confeccao.getModoCalculo().equals(ModoCalculoPrecoConfeccao.MODO_2)) {
			confeccao = calcularModo2(confeccao);
		} else if (confeccao.getModoCalculo().equals(ModoCalculoPrecoConfeccao.MODO_3)) {
			confeccao = calcularModo3(confeccao);
		}

		for (ConfeccaoPreco preco : confeccao.getPrecos()) {
			preco.resetPadroesPreco();
			preco.setDtCusto(new Date());
			getCalculoPreco().calcularPrecos(preco);
		}

		return confeccao;
	}

	/**
	 * calças, jaquetas, bermudas, etc
	 * 02-04-06 >> 06
	 * 08-10 >> 10
	 * 12-14-16 >> 16
	 * P-M-G >> M
	 * XG >> XG
	 * SG >> SG
	 * SS >> SS
	 * 
	 * @param confeccao
	 * @return
	 */
	private Confeccao calcularModo1(Confeccao confeccao) {

		confeccao.limparPrecos();

		Map<GradeTamanho, BigDecimal> mValores = confeccao.getMapaGradeValores();

		GradeTamanho pos1 = confeccao.getGrade().byPosicao(1);
		GradeTamanho pos2 = confeccao.getGrade().byPosicao(2);
		GradeTamanho pos3 = confeccao.getGrade().byPosicao(3);
		GradeTamanho pos4 = confeccao.getGrade().byPosicao(4);
		GradeTamanho pos5 = confeccao.getGrade().byPosicao(5);
		GradeTamanho pos6 = confeccao.getGrade().byPosicao(6);
		GradeTamanho pos7 = confeccao.getGrade().byPosicao(7);
		GradeTamanho pos8 = confeccao.getGrade().byPosicao(8);
		GradeTamanho pos9 = confeccao.getGrade().byPosicao(9);
		GradeTamanho pos10 = confeccao.getGrade().byPosicao(10);
		GradeTamanho pos11 = confeccao.getGrade().byPosicao(11);
		GradeTamanho pos12 = confeccao.getGrade().byPosicao(12);
		GradeTamanho pos13 = confeccao.getGrade().byPosicao(13);
		GradeTamanho pos14 = confeccao.getGrade().byPosicao(14);

		BigDecimal vPos1 = mValores.get(pos1);
		BigDecimal vPos2 = mValores.get(pos2);
		BigDecimal vPos3 = mValores.get(pos3);
		BigDecimal vPos4 = mValores.get(pos4);
		BigDecimal vPos5 = mValores.get(pos5);
		BigDecimal vPos6 = mValores.get(pos6);
		BigDecimal vPos7 = mValores.get(pos7);
		BigDecimal vPos8 = mValores.get(pos8);
		BigDecimal vPos9 = mValores.get(pos9);
		BigDecimal vPos10 = mValores.get(pos10);
		BigDecimal vPos11 = mValores.get(pos11);
		BigDecimal vPos12 = mValores.get(pos12);
		BigDecimal vPos13 = mValores.get(pos13);
		BigDecimal vPos14 = mValores.get(pos14);

		if (notNullAndGt0(vPos1) || notNullAndGt0(vPos2) || notNullAndGt0(vPos3)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();

			getEntityIdHandler().handleEntityIdInserting(preco);

			preco.setConfeccao(confeccao);
			String tams = pos1.getTamanho() + "-" + pos2.getTamanho() + "-" + pos3.getTamanho();
			preco.setDescricao("TAM " + tams);

			BigDecimal precoMedio = ObjectUtils.firstNonNull(vPos3, vPos2, vPos1);

			preco.setPrecoCusto(precoMedio);

			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos4) || notNullAndGt0(vPos5)) {

			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);

			String tams = pos4.getTamanho() + "-" + pos5.getTamanho();
			preco.setDescricao("TAM " + tams);

			BigDecimal precoMedio = ObjectUtils.firstNonNull(vPos5, vPos4);
			preco.setPrecoCusto(precoMedio);

			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos6) || notNullAndGt0(vPos7) || notNullAndGt0(vPos8)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);

			String tams = pos6.getTamanho() + "-" + pos7.getTamanho() + "-" + pos8.getTamanho();
			preco.setDescricao("TAM " + tams);

			BigDecimal precoMedio = ObjectUtils.firstNonNull(vPos8, vPos7, vPos6);
			preco.setPrecoCusto(precoMedio);

			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos9) || notNullAndGt0(vPos10) || notNullAndGt0(vPos11)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);

			String tams = pos9.getTamanho() + "-" + pos10.getTamanho() + "-" + pos11.getTamanho();
			preco.setDescricao("TAM " + tams);

			// preferencialmente pega o M.. caso seja nulo, pega o G. Por último o P.
			BigDecimal precoMedio = ObjectUtils.firstNonNull(vPos10, vPos11, vPos9);

			preco.setPrecoCusto(precoMedio);
			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos12)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			preco.setDescricao("TAM " + pos12.getTamanho());
			preco.setPrecoCusto(vPos12);
			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos13)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			preco.setDescricao("TAM " + pos13.getTamanho());
			preco.setPrecoCusto(vPos13);
			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos14)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			preco.setDescricao("TAM " + pos14.getTamanho());
			preco.setPrecoCusto(vPos14);
			confeccao.getPrecos().add(preco);
		}

		return confeccao;
	}

	/**
	 * CAMISETAS
	 * 02-08 >> 08
	 * 10-16 >> 14
	 * P-M-G >> M
	 * XG >> XG
	 * SG >> SG
	 * SS >> SS
	 * 
	 * @param confeccao
	 * @return
	 */
	private Confeccao calcularModo2(Confeccao confeccao) {

		confeccao.limparPrecos();
		Map<GradeTamanho, BigDecimal> mValores = confeccao.getMapaGradeValores();

		GradeTamanho pos1 = confeccao.getGrade().byPosicao(1);
		GradeTamanho pos2 = confeccao.getGrade().byPosicao(2);
		GradeTamanho pos3 = confeccao.getGrade().byPosicao(3);
		GradeTamanho pos4 = confeccao.getGrade().byPosicao(4);
		GradeTamanho pos5 = confeccao.getGrade().byPosicao(5);
		GradeTamanho pos6 = confeccao.getGrade().byPosicao(6);
		GradeTamanho pos7 = confeccao.getGrade().byPosicao(7);
		GradeTamanho pos8 = confeccao.getGrade().byPosicao(8);
		GradeTamanho pos9 = confeccao.getGrade().byPosicao(9);
		GradeTamanho pos10 = confeccao.getGrade().byPosicao(10);
		GradeTamanho pos11 = confeccao.getGrade().byPosicao(11);
		GradeTamanho pos12 = confeccao.getGrade().byPosicao(12);
		GradeTamanho pos13 = confeccao.getGrade().byPosicao(13);
		GradeTamanho pos14 = confeccao.getGrade().byPosicao(14);

		BigDecimal vPos1 = mValores.get(pos1);
		BigDecimal vPos2 = mValores.get(pos2);
		BigDecimal vPos3 = mValores.get(pos3);
		BigDecimal vPos4 = mValores.get(pos4);
		BigDecimal vPos5 = mValores.get(pos5);
		BigDecimal vPos6 = mValores.get(pos6);
		BigDecimal vPos7 = mValores.get(pos7);
		BigDecimal vPos8 = mValores.get(pos8);
		BigDecimal vPos9 = mValores.get(pos9);
		BigDecimal vPos10 = mValores.get(pos10);
		BigDecimal vPos11 = mValores.get(pos11);
		BigDecimal vPos12 = mValores.get(pos12);
		BigDecimal vPos13 = mValores.get(pos13);
		BigDecimal vPos14 = mValores.get(pos14);

		if (notNullAndGt0(vPos1) ||
				notNullAndGt0(vPos2) ||
				notNullAndGt0(vPos3) ||
				notNullAndGt0(vPos4)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			String tams = pos1.getTamanho() + "-" + pos2.getTamanho() + "-" + pos3.getTamanho() + "-"
					+ pos4.getTamanho();
			preco.setDescricao("TAM " + tams);

			BigDecimal precoMedio = ObjectUtils
					.firstNonNull(vPos4, vPos3, vPos2, vPos1);
			preco.setPrecoCusto(precoMedio);

			confeccao.getPrecos().add(preco);
		}

		if (notNullAndGt0(vPos5) ||
				notNullAndGt0(vPos6) ||
				notNullAndGt0(vPos7) ||
				notNullAndGt0(vPos8)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			String tams = pos5.getTamanho() + "-" + pos6.getTamanho() + "-" + pos7.getTamanho() + "-"
					+ pos8.getTamanho();
			preco.setDescricao("TAM " + tams);

			BigDecimal precoMedio = ObjectUtils
					.firstNonNull(vPos8, vPos7, vPos6, vPos5);
			preco.setPrecoCusto(precoMedio);

			confeccao.getPrecos().add(preco);
		}

		if (notNullAndGt0(vPos9) ||
				notNullAndGt0(vPos10) ||
				notNullAndGt0(vPos11)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			String tams = pos9.getTamanho() + "-" + pos10.getTamanho() + "-" + pos11.getTamanho();
			preco.setDescricao("TAM " + tams);

			BigDecimal precoMedio = ObjectUtils
					.firstNonNull(vPos10, vPos11, vPos9);
			preco.setPrecoCusto(precoMedio);

			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos12)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			preco.setDescricao("TAM " + pos12.getTamanho());
			preco.setPrecoCusto(vPos12);
			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos13)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			preco.setDescricao("TAM " + pos13.getTamanho());
			preco.setPrecoCusto(vPos13);
			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos14)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			preco.setDescricao("TAM " + pos14.getTamanho());
			preco.setPrecoCusto(vPos14);
			confeccao.getPrecos().add(preco);
		}

		return confeccao;
	}

	/**
	 * "IPÊ"
	 * 08-06-04-02
	 * 14-12-10
	 * G-M-P-PP
	 * SS-EG-GG
	 * 
	 * @param confeccao
	 * @return
	 */
	private Confeccao calcularModo3(Confeccao confeccao) {

		confeccao.limparPrecos();

		Map<GradeTamanho, BigDecimal> mValores = confeccao.getMapaGradeValores();

		GradeTamanho pos1 = confeccao.getGrade().byPosicao(1); // 02
		GradeTamanho pos2 = confeccao.getGrade().byPosicao(2); // 04
		GradeTamanho pos3 = confeccao.getGrade().byPosicao(3); // 06
		GradeTamanho pos4 = confeccao.getGrade().byPosicao(4); // 08
		GradeTamanho pos5 = confeccao.getGrade().byPosicao(5); // 10
		GradeTamanho pos6 = confeccao.getGrade().byPosicao(6); // 12
		GradeTamanho pos7 = confeccao.getGrade().byPosicao(7); // 14
		GradeTamanho pos8 = confeccao.getGrade().byPosicao(8); // PP
		GradeTamanho pos9 = confeccao.getGrade().byPosicao(9); // P
		GradeTamanho pos10 = confeccao.getGrade().byPosicao(10); // M
		GradeTamanho pos11 = confeccao.getGrade().byPosicao(11); // G
		GradeTamanho pos12 = confeccao.getGrade().byPosicao(12); // GG
		GradeTamanho pos13 = confeccao.getGrade().byPosicao(13); // EG
		GradeTamanho pos14 = confeccao.getGrade().byPosicao(14); // SS

		BigDecimal vPos1 = mValores.get(pos1);
		BigDecimal vPos2 = mValores.get(pos2);
		BigDecimal vPos3 = mValores.get(pos3);
		BigDecimal vPos4 = mValores.get(pos4);
		BigDecimal vPos5 = mValores.get(pos5);
		BigDecimal vPos6 = mValores.get(pos6);
		BigDecimal vPos7 = mValores.get(pos7);
		BigDecimal vPos8 = mValores.get(pos8);
		BigDecimal vPos9 = mValores.get(pos9);
		BigDecimal vPos10 = mValores.get(pos10);
		BigDecimal vPos11 = mValores.get(pos11);
		BigDecimal vPos12 = mValores.get(pos12);
		BigDecimal vPos13 = mValores.get(pos13);
		BigDecimal vPos14 = mValores.get(pos14);

		if (notNullAndGt0(vPos1) ||
				notNullAndGt0(vPos2) ||
				notNullAndGt0(vPos3) ||
				notNullAndGt0(vPos4)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			String tams = pos1.getTamanho() + "-" + pos2.getTamanho() + "-" + pos3.getTamanho() + "-"
					+ pos4.getTamanho();
			preco.setDescricao("TAM " + tams);

			BigDecimal precoMedio = ObjectUtils
					.firstNonNull(vPos4, vPos3, vPos2, vPos1);
			preco.setPrecoCusto(precoMedio);

			confeccao.getPrecos().add(preco);
		}

		if (notNullAndGt0(vPos5) ||
				notNullAndGt0(vPos6) ||
				notNullAndGt0(vPos7)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			String tams = pos5.getTamanho() + "-" + pos6.getTamanho() + "-" + pos7.getTamanho();
			preco.setDescricao("TAM " + tams);

			BigDecimal precoMedio = ObjectUtils
					.firstNonNull(vPos7, vPos6, vPos5);
			preco.setPrecoCusto(precoMedio);

			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos8) ||
				notNullAndGt0(vPos9) ||
				notNullAndGt0(vPos10) ||
				notNullAndGt0(vPos11)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);
			String tams = pos8.getTamanho() + "-" + pos9.getTamanho() + "-" + pos10.getTamanho() + "-"
					+ pos11.getTamanho();
			preco.setDescricao("TAM " + tams);

			BigDecimal precoMedio = ObjectUtils
					.firstNonNull(vPos10, vPos11, vPos9, vPos8);
			preco.setPrecoCusto(precoMedio);

			confeccao.getPrecos().add(preco);
		}
		if (notNullAndGt0(vPos12) ||
				notNullAndGt0(vPos13) ||
				notNullAndGt0(vPos14)) {
			ConfeccaoPreco preco = new ConfeccaoPreco();
			getEntityIdHandler().handleEntityIdInserting(preco);
			preco.setConfeccao(confeccao);

			String tams = "";

			if (pos12 != null && !"".equals(pos12.getTamanho())) {
				tams += pos12.getTamanho() + "-";
			}
			if (pos13 != null && !"".equals(pos13.getTamanho())) {
				tams += pos13.getTamanho() + "-";
			}
			if (pos14 != null && !"".equals(pos14.getTamanho())) {
				tams += pos14.getTamanho() + "-";
			}
			
			tams = tams.substring(0,tams.length()-1);
			
			
			preco.setDescricao("TAM " + tams);

			BigDecimal precoMedio = ObjectUtils
					.firstNonNull(vPos14, vPos13, vPos12);
			preco.setPrecoCusto(precoMedio);

			confeccao.getPrecos().add(preco);
		}

		return confeccao;
	}

	/**
	 * Migra a grade.
	 * 
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public void migrarGrade(Confeccao confeccao, Grade novaGrade) throws ViewException {
		// Percorre todos os ConfeccaoItemQtde anteriores e cria novos para a novaGrade

		confeccao = getConfeccaoFinder().refresh(confeccao);

		for (ConfeccaoItem ci : confeccao.getItens()) {
			List<ConfeccaoItemQtde> novosCIQs = new ArrayList<ConfeccaoItemQtde>();
			for (ConfeccaoItemQtde ciq : ci.getQtdesGrade()) {
				try {
					ConfeccaoItemQtde novoCIQ = new ConfeccaoItemQtde();
					novoCIQ.setConfeccaoItem(ci);
					novoCIQ.setQtde(ciq.getQtde());
					novoCIQ.setValor(ciq.getValor());

					GradeTamanho novaGradeTamanho = getGradeFinder()
							.findByGradeAndPosicao(novaGrade, ciq.getGradeTamanho().getPosicao());

					if (novaGradeTamanho == null) {
						continue;
						// throw new ViewException("Não foi encontrada a posição " + ciq.getGradeTamanho().getPosicao() + " para a grade " + novaGrade.getObs());
					}

					novoCIQ.setGradeTamanho(novaGradeTamanho);
					getEntityIdHandler().handleEntityId(novoCIQ);
					novosCIQs.add(novoCIQ);
				} catch (Exception e) {
					throw new ViewException("Erro ao criar novo 'ciq'.", e);
				}
			}

			for (ConfeccaoItemQtde novoCIQ : novosCIQs) {
				logger.debug("Adicionando... " + novoCIQ.getConfeccaoItem().getId() + "-"
						+ novoCIQ.getConfeccaoItem().getInsumo().getDescricao() + " / "
						+ novoCIQ.getGradeTamanho().getId() + ":" + novoCIQ.getGradeTamanho().getTamanho());
				ci.getQtdesGrade().add(novoCIQ);
			}
		}

		try {
			confeccao.setGrade(novaGrade);
			// verificar se os cascades vão funcionar.
			getConfeccaoDataMapper().save(confeccao);
		} catch (Exception e) {
			JSFUtils.addErrorMsg(e);
		}

	}

	public boolean notNullAndGt0(BigDecimal valor) {
		return (valor != null) && (valor.doubleValue() > 0);
	}

	public BigDecimal findValorByTamanhoStr(String tamanho, Map<GradeTamanho, BigDecimal> mValores) {
		for (Map.Entry<GradeTamanho, BigDecimal> e : mValores.entrySet()) {
			if (e.getKey().getTamanho().equals(tamanho)) {
				return e.getValue();
			}
		}
		return null;
	}

	public BigDecimal findValorByPosicao(Integer posicao, Map<GradeTamanho, BigDecimal> mValores) {
		for (Map.Entry<GradeTamanho, BigDecimal> e : mValores.entrySet()) {
			if (e.getKey().getPosicao().equals(posicao)) {
				return e.getValue();
			}
		}
		return null;
	}

	public GradeTamanho findByPosicao(Integer posicao, Map<GradeTamanho, BigDecimal> mValores) {
		for (Map.Entry<GradeTamanho, BigDecimal> e : mValores.entrySet()) {
			if (e.getKey().getPosicao().equals(posicao)) {
				return e.getKey();
			}
		}
		return null;
	}

	public ConfeccaoDataMapper getConfeccaoDataMapper() {
		return confeccaoDataMapper;
	}

	public void setConfeccaoDataMapper(ConfeccaoDataMapper confeccaoDataMapper) {
		this.confeccaoDataMapper = confeccaoDataMapper;
	}

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public ConfeccaoBusiness getThiz() {
		return (ConfeccaoBusiness) getBeanFactory().getBean("confeccaoBusiness");
	}

}
