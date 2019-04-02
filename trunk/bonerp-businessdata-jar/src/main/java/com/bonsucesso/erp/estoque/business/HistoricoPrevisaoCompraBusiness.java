package com.bonsucesso.erp.estoque.business;



import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceProperty;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.data.PedidoCompraFinder;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.estoque.results.data.HistoricoPrevisaoCompraDataMapper;
import com.bonsucesso.erp.estoque.results.data.HistoricoPrevisaoCompraFinder;
import com.bonsucesso.erp.estoque.results.model.HistoricoPrevisaoCompra;
import com.bonsucesso.erp.estoque.results.model.HistoricoPrevisaoCompraItem;
import com.bonsucesso.erp.estoque.results.model.ListHistoricoPrevisaoCompra;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * 
 * @author JavaDev
 *
 */
@Component("gerarHistoricoPrevisaoCompra")
public class HistoricoPrevisaoCompraBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6295890895550256391L;

	protected static Logger logger = Logger.getLogger(HistoricoPrevisaoCompraBusiness.class);

	/**
	 * EntityManager
	 */
	@PersistenceContext(properties = @PersistenceProperty(name = "org.hibernate.flushMode", value = "COMMIT"))
	private EntityManager entityManager;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private SubdeptoFinder subdeptoFinder;

	@Autowired
	private PedidoCompraFinder pedidoCompraFinder;

	@Autowired
	private HistoricoPrevisaoCompraDataMapper historicoPrevisaoCompraDataMapper;

	@Autowired
	private HistoricoPrevisaoCompraFinder historicoPrevisaoCompraFinder;

	private Integer[] anos;

	@PostConstruct
	public void init() {
		int anoAtual = CalendarUtil.getCalendar(new Date()).get(Calendar.YEAR);
		int primeiroAno = 2014;
		anos = new Integer[anoAtual-primeiroAno];
		for (int i=0 ; i<anoAtual-primeiroAno ; i++) {
			anos[i] = primeiroAno+i;
		}
	}

	public Integer[] getAnos() {
		return anos;
	}

	public void setAnos(Integer[] anos) {
		this.anos = anos;
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW,
			rollbackFor = { RuntimeException.class, ViewException.class })
	public void gerar()
			throws ViewException {
		try {

			getHistoricoPrevisaoCompraDataMapper().truncate();

			long ini = System.currentTimeMillis();

			logger.info("Iniciando geração de HistoricoPrevisaoCompra");

			List<Object[]> ativos = getProdutoFinder().findFornecedoresSubdeptosDeProdutosAtivos();

			for (Object[] e : ativos) {

				Long fornecedorId = ((BigInteger) e[0]).longValue();
				Long subdeptoId = ((BigInteger) e[1]).longValue();

				//				if (fornecedorId != 161 || subdeptoId != 101) continue; // TESTANDO SÓ COM ALVER KLEIN

				Fornecedor fornecedor = getFornecedorFinder().findById(fornecedorId);
				//				if (!fornecedores.contains(fornecedor))
				//					continue;

				Subdepartamento subdepto = getSubdeptoFinder().findById((subdeptoId).longValue());
				//				if (!subdeptos.contains(subdepto))
				//					continue;

				HistoricoPrevisaoCompra hpc = new HistoricoPrevisaoCompra();
				hpc.setFornecedor(fornecedor);
				hpc.setSubdepto(subdepto);

				hpc.setQtdeEmPedidos(getPedidoCompraFinder().findQtdePedidaBy(fornecedor, subdepto));

				hpc.setSaldoAtual(getProdutoFinder().findQtdeBy(fornecedor, subdepto));

				// Chama outro método pra preencher 
				preencherValoresHistorico(hpc);

				hpc = getHistoricoPrevisaoCompraDataMapper().save(hpc);

			}

			logger.info("FINALIZANDO geração de HistoricoPrevisaoCompra");

			long fim = System.currentTimeMillis();
			double tempo = (fim - ini) / 1000;
			logger.info("Tempo de exec.: " + tempo + " segundos");

		} catch (Exception e) {
			throw new ViewException(e);
		}

	}

	/**
	 * Retorna todos os dados do BD já dentro da ListHistoricoPrevisaoCompra.
	 * 
	 * @return
	 * @throws ViewException
	 */
	public ListHistoricoPrevisaoCompra returnList(List<Fornecedor> fornecedores, List<Subdepartamento> subdeptos)
			throws ViewException {
		try {
			ListHistoricoPrevisaoCompra list = new ListHistoricoPrevisaoCompra();

			List<HistoricoPrevisaoCompra> l = getHistoricoPrevisaoCompraFinder().findAll();

			list.addAll(l, fornecedores, subdeptos);

			return list;
		} catch (ViewException e) {
			throw new ViewException(e);
		}

	}

	public void debugR(Map<Date, BigDecimal> r) {
		List<Date> s = new ArrayList<Date>();
		s.addAll(r.keySet());

		Collections.sort(s);

		for (Date d : s) {
			System.out.println(new SimpleDateFormat("MM/yyyy").format(d) + " >>> " + r.get(d));
		}
	}

	/**
	 * Preenche os campos anoXmesX, mediaMesX e saldoMesX usando Reflection.
	 * 
	 * @param hpc
	 */
	public void preencherValoresHistorico(HistoricoPrevisaoCompra hpc) {
		try {

			logger.debug("preencherValoresHistorico");
			logger.debug("FORNECEDOR: " + hpc.getFornecedor());
			logger.debug("SUBDEPTO: " + hpc.getSubdepto());

			Map<Date, BigDecimal> r = findVendasResultsBy(hpc.getFornecedor(), hpc.getSubdepto());
			debugR(r);

			for (Integer ano : getAnos()) {
				HistoricoPrevisaoCompraItem item = new HistoricoPrevisaoCompraItem();
				item.setHpc(hpc);
				
				item.setMes1(r.get(CalendarUtil.getDate(01, 1, ano)));
				item.setMes2(r.get(CalendarUtil.getDate(01, 2, ano)));
				item.setMes3(r.get(CalendarUtil.getDate(01, 3, ano)));
				item.setMes4(r.get(CalendarUtil.getDate(01, 4, ano)));
				item.setMes5(r.get(CalendarUtil.getDate(01, 5, ano)));
				item.setMes6(r.get(CalendarUtil.getDate(01, 6, ano)));
				item.setMes7(r.get(CalendarUtil.getDate(01, 7, ano)));
				item.setMes8(r.get(CalendarUtil.getDate(01, 8, ano)));
				item.setMes9(r.get(CalendarUtil.getDate(01, 9, ano)));
				item.setMes10(r.get(CalendarUtil.getDate(01, 10, ano)));
				item.setMes11(r.get(CalendarUtil.getDate(01, 11, ano)));
				item.setMes12(r.get(CalendarUtil.getDate(01, 12, ano)));
			}
			
			
			// Calcula as médias e os saldos
			for (int m = 1; m <= 12; m++) {
				int qtdeAConsiderar = 0;
				BigDecimal total = BigDecimal.ZERO;

				for (int a = 1; a <= 5; a++) {
					Method method = HistoricoPrevisaoCompra.class
							.getDeclaredMethod("getAno" + a + "mes" + m);
					BigDecimal v = (BigDecimal) method.invoke(hpc);
					if (v != null) {
						// Só considera o que não for nulo
						qtdeAConsiderar++;
						total = total.add(v);
					}
				}
				BigDecimal media;
				if (qtdeAConsiderar > 0) {
					media = total.divide(new BigDecimal(qtdeAConsiderar), 0, RoundingMode.UP);
				} else {
					media = BigDecimal.ZERO;
				}

				// Média do que foi vendido nos mesmos meses dos anos anteriores.

				Method method = HistoricoPrevisaoCompra.class
						.getDeclaredMethod("setMediaMes" + m, BigDecimal.class);
				method.invoke(hpc, media);

				// Se está no primeiro mês...
				if (m == 1) {
					Calendar calHoje = CalendarUtil.getCalendar(new Date());
					int diasRestantesMes = 30 - calHoje.get(Calendar.DAY_OF_MONTH);
					if (diasRestantesMes >= 1) {
						BigDecimal vendasRestantes = media.divide(new BigDecimal(diasRestantesMes), 0, RoundingMode.UP);
						hpc.setVendasRestantesMes(vendasRestantes);
					}
					hpc.setSaldoMes1(hpc.getSaldoAtual().subtract(hpc.getVendasRestantesMes()));
				} else {
					// Pego o saldo do mês anterior
					Method getSaldo = HistoricoPrevisaoCompra.class
							.getDeclaredMethod("getSaldoMes" + (m - 1));
					BigDecimal saldoAnterior = (BigDecimal) getSaldo.invoke(hpc);
					saldoAnterior = saldoAnterior != null ? saldoAnterior : BigDecimal.ZERO;

					BigDecimal saldo = saldoAnterior.subtract(media);
					Method setSaldo = HistoricoPrevisaoCompra.class
							.getDeclaredMethod("setSaldoMes" + m, BigDecimal.class);
					setSaldo.invoke(hpc, saldo);

				}
			}

		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Não criei Finder para a entidade ProdutoVendaResults, então faz a busca direto por aqui mesmo.
	 * 
	 * @param f
	 * @param sd
	 * @param mesano
	 * @return
	 * @throws ViewException
	 */
	public Map<Date, BigDecimal> findVendasResultsBy(Fornecedor f, Subdepartamento sd)
			throws ViewException {
		try {

			String sql = "SELECT mesano, sum(qtde) FROM ProdutoVendaResults pvr "
					+ "WHERE "
					+ "pvr.produto.fornecedor = :fornecedor AND "
					+ "pvr.produto.subdepto = :subdepto AND "
					+ "pvr.produto.reduzidoEktAte IS NULL "
					+ "GROUP BY mesano "
					+ "ORDER BY mesano";

			Query qry = entityManager.createQuery(sql);
			qry.setParameter("fornecedor", f);
			qry.setParameter("subdepto", sd);

			@SuppressWarnings("unchecked")
			List<Object[]> l = qry.getResultList();
			Map<Date, BigDecimal> map = new HashMap<Date, BigDecimal>();
			for (Object[] e : l) {
				map.put(new SimpleDateFormat("yyyyMMdd").parse(e[0] + "01"), (BigDecimal) e[1]);
			}
			return map;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new ViewException("Erro em findVendasResultsBy(Fornecedor f = '" + f.getId()
					+ "', Subdepartamento sd = '" + sd.getId() + "')");
		}
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	public SubdeptoFinder getSubdeptoFinder() {
		return subdeptoFinder;
	}

	public void setSubdeptoFinder(SubdeptoFinder subdeptoFinder) {
		this.subdeptoFinder = subdeptoFinder;
	}

	public PedidoCompraFinder getPedidoCompraFinder() {
		return pedidoCompraFinder;
	}

	public void setPedidoCompraFinder(PedidoCompraFinder pedidoCompraFinder) {
		this.pedidoCompraFinder = pedidoCompraFinder;
	}

	public HistoricoPrevisaoCompraDataMapper getHistoricoPrevisaoCompraDataMapper() {
		return historicoPrevisaoCompraDataMapper;
	}

	public void setHistoricoPrevisaoCompraDataMapper(
			HistoricoPrevisaoCompraDataMapper historicoPrevisaoCompraDataMapper) {
		this.historicoPrevisaoCompraDataMapper = historicoPrevisaoCompraDataMapper;
	}

	public HistoricoPrevisaoCompraFinder getHistoricoPrevisaoCompraFinder() {
		return historicoPrevisaoCompraFinder;
	}

	public void setHistoricoPrevisaoCompraFinder(HistoricoPrevisaoCompraFinder historicoPrevisaoCompraFinder) {
		this.historicoPrevisaoCompraFinder = historicoPrevisaoCompraFinder;
	}

	public static void main(String[] args) {

	}

}
