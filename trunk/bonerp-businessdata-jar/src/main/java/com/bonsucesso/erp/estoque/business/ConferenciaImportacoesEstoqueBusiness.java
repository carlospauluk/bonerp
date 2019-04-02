package com.bonsucesso.erp.estoque.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.RegistroConferenciaDataMapper;
import com.bonsucesso.erp.financeiro.data.RegistroConferenciaFinder;
import com.bonsucesso.erp.financeiro.model.RegistroConferencia;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


@Component("conferenciaImportacoesEstoqueBusiness")
public class ConferenciaImportacoesEstoqueBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -541145646925164061L;

	protected static Logger logger = Logger.getLogger(ConferenciaImportacoesEstoqueBusiness.class);

	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private RegistroConferenciaDataMapper registroConferenciaDataMapper;

	@Autowired
	private RegistroConferenciaFinder registroConferenciaFinder;

	@Autowired
	private VendaFinder vendaFinder;

	/**
	 * Constrói a List com os ConferenciaImportacoesEstoqueRecord para exibir no datatable da conferenciaImportacoesEstoque.xhtml
	 * 
	 * @param mesesAnos
	 * @return
	 * @throws ViewException
	 */
	public List<ConferenciaImportacoesEstoqueRecord> buildRecords(List<Date> mesesAnos) throws ViewException {

		List<ConferenciaImportacoesEstoqueRecord> list = new ArrayList<ConferenciaImportacoesEstoqueRecord>();

		for (Date mesano : mesesAnos) {

			logger.debug(new SimpleDateFormat("MM/yyyy").format(mesano));

			mesano = CalendarUtil.zeroDate(CalendarUtil.ultimoDiaNoMesAno(mesano));

			ConferenciaImportacoesEstoqueRecord r = new ConferenciaImportacoesEstoqueRecord();
			r.setMesAno(mesano);

			RegistroConferencia totalCustoInformado = getThiz().handleRegistroConferencia("INVENT CUSTO", mesano, null);
			r.setTotalCustoInformado(totalCustoInformado);
			RegistroConferencia totalCustoImportado = getThiz()
					.handleRegistroConferencia("INVENT CUSTO (IMPORTADO)", mesano, null);
			r.setTotalCustoImportado(totalCustoImportado);

			RegistroConferencia totalPecasInformado = getThiz().handleRegistroConferencia("INVENT PECAS", mesano, null);
			r.setTotalPecasInformado(totalPecasInformado);
			RegistroConferencia totalPecasImportado = getThiz()
					.handleRegistroConferencia("INVENT PECAS (IMPORTADO)", mesano, null);
			r.setTotalPecasImportado(totalPecasImportado);

			RegistroConferencia totalVendaPrazoInformado = getThiz()
					.handleRegistroConferencia("INVENT VENDA", mesano, null);
			r.setTotalPrecoPrazoInformado(totalVendaPrazoInformado);
			RegistroConferencia totalVendaPrazoImportado = getThiz()
					.handleRegistroConferencia("INVENT VENDA (IMPORTADO)", mesano, null);
			r.setTotalPrecoPrazoImportado(totalVendaPrazoImportado);

			RegistroConferencia totalVendidoInformado = getThiz()
					.handleRegistroConferencia("TOTAL VENDAS", mesano, null);
			r.setTotalVendidoInformado(totalVendidoInformado);
			RegistroConferencia totalVendidoImportado = getThiz()
					.handleRegistroConferencia("TOTAL VENDAS (IMPORTADO)", mesano, null);
			r.setTotalVendidoImportado(totalVendidoImportado);

			list.add(r);

		}

		return list;
	}

	/**
	 * Aqui 3 coisas podem acontecer:
	 * - apenas retornar o RegistroConferencia (caso exista e o 'valor' seja passado como null);
	 * - criar um novo RegistroConferencia (caso não exista e o 'valor' não seja passado como null);
	 * - alterar o RegistroConferencia (caso exista e o 'valor' não seja passado como null).
	 * 
	 * @param descricao
	 * @param mesano
	 * @param valor
	 * @return
	 * @throws ViewException
	 */
	public RegistroConferencia handleRegistroConferencia(String descricao, Date mesano, BigDecimal valor)
			throws ViewException {

		RegistroConferencia rc = null;

		try {
			// Primeiro verifica se já existe
			rc = getRegistroConferenciaFinder().findBy(descricao, mesano);
		} catch (ViewException e) {
			throw new ViewException("Erro ao pesquisar registro de conferência: '" + descricao + "', mesano: '" + mesano
					+ "'");
		}

		// Se não foi passado valor, então é porque só quer o valor que deve existir.
		if (rc != null && valor == null) {
			return rc;
		}

		// Se não existir, cria
		if (rc == null) {
			rc = new RegistroConferencia();
			rc.setCarteira(null);
			rc.setDescricao(descricao);
			rc.setDtRegistro(mesano);
		}

		rc.setValor(valor);
		try {
			rc = getRegistroConferenciaDataMapper().save(rc);
		} catch (ViewException e) {
			throw new ViewException("Erro ao salvar	registro de conferência: '" + descricao + "', mesano: '"
					+ mesano + "'");
		}

		return rc;
	}

	/**
	 * Chama novamente a sp_total_inventario e o SELECT para calcular total de venda e salva novamente os RegistroConferencia.
	 * 
	 * @param mesano
	 * @throws ViewException
	 */
	public void regerarRegistrosConferencia(Date mesano) throws ViewException {

		mesano = CalendarUtil.zeroDate(CalendarUtil.ultimoDiaNoMesAno(mesano));

		StoredProcedureQuery spQuery;

		if (CalendarUtil.isMesmoMesAno(mesano, new Date())) {
			
			// CALL sp_total_inventario_mesatual2('201610',@a,@b,@c);
			
			spQuery = getRegistroConferenciaDataMapper().getEntityManager()
					.createStoredProcedureQuery("sp_total_inventario_mesatual2");
			spQuery
					.registerStoredProcedureParameter("total_pecas", BigDecimal.class, ParameterMode.OUT)
					.registerStoredProcedureParameter("total_custos", BigDecimal.class, ParameterMode.OUT)
					.registerStoredProcedureParameter("total_precos_prazo", BigDecimal.class, ParameterMode.OUT);
		} else {
			
			// CALL sp_total_inventario('201610',@a,@b,@c);
			
			spQuery = getRegistroConferenciaDataMapper().getEntityManager()
					.createStoredProcedureQuery("sp_total_inventario");
			spQuery.registerStoredProcedureParameter("p_mesano", String.class, ParameterMode.IN)
					.registerStoredProcedureParameter("total_pecas", BigDecimal.class, ParameterMode.OUT)
					.registerStoredProcedureParameter("total_custos", BigDecimal.class, ParameterMode.OUT)
					.registerStoredProcedureParameter("total_precos_prazo", BigDecimal.class, ParameterMode.OUT);
			spQuery.setParameter("p_mesano", new SimpleDateFormat("yyyyMM").format(mesano));
		}

		Object[] r = (Object[]) spQuery.getSingleResult();

		// BigDecimal totalPecas = (BigDecimal) spQuery.getOutputParameterValue("total_pecas");
		BigDecimal totalPecas = (BigDecimal) r[0];
		getThiz().handleRegistroConferencia("INVENT PECAS (IMPORTADO)", mesano, totalPecas);

		// BigDecimal totalCusto = (BigDecimal) spQuery.getOutputParameterValue("total_custos");
		BigDecimal totalCustos = (BigDecimal) r[1];
		getThiz().handleRegistroConferencia("INVENT CUSTO (IMPORTADO)", mesano, totalCustos);

		// BigDecimal totalVendaPrazo = (BigDecimal) spQuery.getOutputParameterValue("total_precos_prazo");
		BigDecimal totalPrecosPrazo = (BigDecimal) r[2];
		getThiz().handleRegistroConferencia("INVENT VENDA (IMPORTADO)", mesano, totalPrecosPrazo);

		logger.debug("Pesquisando o totalVendas... getVendaFinder().findTotalBy(mesano)");
		BigDecimal totalVendas = getVendaFinder().findTotalBy(mesano);
		logger.debug("Total vendas retornado: " + totalVendas.doubleValue());
		getThiz().handleRegistroConferencia("TOTAL VENDAS (IMPORTADO)", mesano, totalVendas);

	}

	public RegistroConferenciaDataMapper getRegistroConferenciaDataMapper() {
		return registroConferenciaDataMapper;
	}

	public void setRegistroConferenciaDataMapper(RegistroConferenciaDataMapper registroConferenciaDataMapper) {
		this.registroConferenciaDataMapper = registroConferenciaDataMapper;
	}

	public RegistroConferenciaFinder getRegistroConferenciaFinder() {
		return registroConferenciaFinder;
	}

	public void setRegistroConferenciaFinder(RegistroConferenciaFinder registroConferenciaFinder) {
		this.registroConferenciaFinder = registroConferenciaFinder;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public ConferenciaImportacoesEstoqueBusiness getThiz() {
		return (ConferenciaImportacoesEstoqueBusiness) getBeanFactory()
				.getBean("conferenciaImportacoesEstoqueBusiness");
	}

}
