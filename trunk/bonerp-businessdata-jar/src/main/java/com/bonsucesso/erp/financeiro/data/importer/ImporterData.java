package com.bonsucesso.erp.financeiro.data.importer;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.data.DiaUtilFinder;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.financeiro.data.BancoFinder;
import com.bonsucesso.erp.financeiro.data.CarteiraFinder;
import com.bonsucesso.erp.financeiro.data.CategoriaFinder;
import com.bonsucesso.erp.financeiro.data.CentroCustoFinder;
import com.bonsucesso.erp.financeiro.data.ModoFinder;
import com.bonsucesso.erp.financeiro.data.MovimentacaoDataMapper;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFinder;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.CentroCusto;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.Recorrencia;
import com.bonsucesso.erp.financeiro.model.Recorrencia.Frequencia;
import com.bonsucesso.erp.financeiro.model.Recorrencia.TipoRepeticao;
import com.bonsucesso.erp.financeiro.model.Status;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


@Component("importerData")
public class ImporterData {

	private DataSource dataSourceDataFB;

	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private MovimentacaoFinder movimentacaoFinder;

	@Autowired
	private MovimentacaoDataMapper movimentacaoDataMapper;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private BancoFinder bancoFinder;

	@Autowired
	private CarteiraFinder carteiraFinder;

	@Autowired
	private CentroCustoFinder centroCustoFinder;

	@Autowired
	private CategoriaFinder categoriaFinder;

	@Autowired
	private ModoFinder modoFinder;

	@Autowired
	private DiaUtilFinder diaUtilFinder;

	protected static Logger logger = Logger.getLogger(ImporterData.class);

	private Connection conn;

	public static void main(String... args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

			ImporterData t = (ImporterData) context.getBean("importerData");

			t.importar();

		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}

	public Connection getConn() {
		try {
			if ((conn == null) || conn.isClosed()) {
				conn = getDataSourceDataFB().getConnection();
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public DataSource getDataSourceDataFB() {
		if (dataSourceDataFB == null) {
			dataSourceDataFB = (DataSource) getBeanFactory().getBean("dataSourceDataFB");
		}
		return dataSourceDataFB;
	}

	public void setDataSourceDataFB(DataSource dataSourceDataFB) {
		this.dataSourceDataFB = dataSourceDataFB;
	}

	public MovimentacaoDataMapper getMovimentacaoDataMapper() {
		return movimentacaoDataMapper;
	}

	public void setMovimentacaoDataMapper(MovimentacaoDataMapper movimentacaoDataMapper) {
		this.movimentacaoDataMapper = movimentacaoDataMapper;
	}

	public MovimentacaoFinder getMovimentacaoFinder() {
		return movimentacaoFinder;
	}

	public void setMovimentacaoFinder(MovimentacaoFinder movimentacaoFinder) {
		this.movimentacaoFinder = movimentacaoFinder;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	public BancoFinder getBancoFinder() {
		return bancoFinder;
	}

	public void setBancoFinder(BancoFinder bancoFinder) {
		this.bancoFinder = bancoFinder;
	}

	public CarteiraFinder getCarteiraFinder() {
		return carteiraFinder;
	}

	public void setCarteiraFinder(CarteiraFinder carteiraFinder) {
		this.carteiraFinder = carteiraFinder;
	}

	public CentroCustoFinder getCentroCustoFinder() {
		return centroCustoFinder;
	}

	public void setCentroCustoFinder(CentroCustoFinder centroCustoFinder) {
		this.centroCustoFinder = centroCustoFinder;
	}

	public CategoriaFinder getCategoriaFinder() {
		return categoriaFinder;
	}

	public void setCategoriaFinder(CategoriaFinder categoriaFinder) {
		this.categoriaFinder = categoriaFinder;
	}

	public ModoFinder getModoFinder() {
		return modoFinder;
	}

	public void setModoFinder(ModoFinder modoFinder) {
		this.modoFinder = modoFinder;
	}

	public DiaUtilFinder getDiaUtilFinder() {
		return diaUtilFinder;
	}

	public void setDiaUtilFinder(DiaUtilFinder diaUtilFinder) {
		this.diaUtilFinder = diaUtilFinder;
	}

	private static Map<Integer, Long> mapaGrupos2Categorias = new HashMap<Integer, Long>();

	private static Map<Integer, Integer> mapaLocalizador2Banco = new HashMap<Integer, Integer>();

	static {
		// FORNECEDORES 			2.02.001		CUSTOS DE MERCADORIAS
		ImporterData.mapaGrupos2Categorias.put(1, 202001L);
		// IMPOSTOS E TAXAS			2.01.013		TAXAS E IMPOSTOS DIVERSOS
		ImporterData.mapaGrupos2Categorias.put(2, 201013L);
		// DESPESAS OPERACIONAIS	2.01.099		CUSTOS DIVERSOS
		ImporterData.mapaGrupos2Categorias.put(3, 201099L);
		// EMPRÉSTIMOS BANCÁRIOS	2.90			EMPRÉSTIMOS
		ImporterData.mapaGrupos2Categorias.put(4, 290L);
		// DESPESAS PESSOAIS		2.02.003		PRÓ-LABORE
		ImporterData.mapaGrupos2Categorias.put(5, 202003L);
		// ENCARGOS SOCIAIS			2.01.100.999	ENCARGOS SOCIAIS (GERAIS)
		ImporterData.mapaGrupos2Categorias.put(6, 201100999L);
		// DESCONTO CHEQUE			2.90			EMPRÉSTIMOS
		ImporterData.mapaGrupos2Categorias.put(7, 290L);
		// DESPESAS MAO DE OBRA		2.01.100.007	MÃO-DE-OBRA
		ImporterData.mapaGrupos2Categorias.put(8, 201100007L);
		// CONVÊNIOS				2.01.100.008	CONVÊNIOS
		ImporterData.mapaGrupos2Categorias.put(9, 201100008L);
		// DESPESA EVENTUAL CTPL	2.01.099		CUSTOS DIVERSOS
		ImporterData.mapaGrupos2Categorias.put(10, 201099L);
		// DESPESA EVENTUAL FAP		2.02.003		PRÓ-LABORE >>>>>>>>>>>> CENTRO DE CUSTO - FAP
		ImporterData.mapaGrupos2Categorias.put(11, 202003L);
		// DESPESA EVENTUAL CEP		2.02.003		PRÓ-LABORE >>>>>>>>>>>> CENTRO DE CUSTO - CEP
		ImporterData.mapaGrupos2Categorias.put(12, 202003L);
		// DESPESA EVENTUAL LSP		2.90			EMPRÉSTIMOS
		ImporterData.mapaGrupos2Categorias.put(13, 290L);

		ImporterData.mapaLocalizador2Banco.put(0, null); // S/BOLETO/FATURA
		ImporterData.mapaLocalizador2Banco.put(1, null); // CARTEIRA
		ImporterData.mapaLocalizador2Banco.put(2, 1); // BRASIL
		ImporterData.mapaLocalizador2Banco.put(3, 237); // BRADESCO
		ImporterData.mapaLocalizador2Banco.put(4, 341); // ITAÚ
		ImporterData.mapaLocalizador2Banco.put(5, 399); // HSBC
		ImporterData.mapaLocalizador2Banco.put(6, null); // REAL
		ImporterData.mapaLocalizador2Banco.put(7, 104); // CAIXA
		ImporterData.mapaLocalizador2Banco.put(8, 33); // SANTANDER
		ImporterData.mapaLocalizador2Banco.put(9, null); // MERCANTIL DO BRASIL
		ImporterData.mapaLocalizador2Banco.put(10, 756); // SICOOB
		ImporterData.mapaLocalizador2Banco.put(11, null);// CHEQUE PRE
		ImporterData.mapaLocalizador2Banco.put(12, null);// DEPOSITO CC
		ImporterData.mapaLocalizador2Banco.put(13, 748); // SICREDI
		ImporterData.mapaLocalizador2Banco.put(14, null);// A VISTA
		ImporterData.mapaLocalizador2Banco.put(15, null);// APRESENTACAO
		ImporterData.mapaLocalizador2Banco.put(16, null);// CITI
		ImporterData.mapaLocalizador2Banco.put(17, null);// BANCOOB

	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public void importar() throws ViewException {
		PreparedStatement pst = null;

		//		String jpql = "FROM Movimentacao WHERE " +
		//				"valorTotal = :valorTotal AND " +
		//				"dtVencto = :dtVencto AND " +
		//				"dtMoviment = :dtMoviment AND " +
		//				"categoria = :categoria";

		String jpql = "FROM Movimentacao WHERE idSistemaAntigo = :idSistemaAntigo";

		//		List<Movimentacao> ids = new ArrayList<Movimentacao>();

		try {
			String sql = "SELECT D.*, P.ID AS ID_PARCELA, P.*, G.MENSAL, F.RAZAO FROM " +
					"CTS_DOCUMENTO D, CTS_PARCELA P, CTS_GRUPOS G, CTS_FORNECEDORES F " +
					"WHERE " +
					"P.ID_DOCUMENTO = D.ID AND D.GRUPO = G.ID AND D.FORNECEDOR = F.ID AND " +
					"P.DATA_VENCTO >= ? " +
					"ORDER BY P.DATA_VENCTO";
			pst = getConn().prepareStatement(sql);

			// Date hoje = new Date(CalendarUtil.zeroDate(new java.util.Date()).getTime());
			Date hoje = new Date(CalendarUtil.getDate(16, 03, 2015, 00, 00, 00).getTime());

			pst.setDate(1, hoje);

			ResultSet rs = pst.executeQuery();
			int salvos = 0;
			int existentes = 0;

			while (rs.next()) {

				Movimentacao m = new Movimentacao();

				Fornecedor fornecedor = getFornecedorFinder().findByCodigo(rs.getInt("FORNECEDOR"));
				m.setPessoa(fornecedor == null ? null : fornecedor.getPessoa());

				int grupo = rs.getInt("GRUPO");
				Categoria categoria = getCategoriaFinder().findBy(ImporterData.mapaGrupos2Categorias.get(grupo));
				if (categoria == null) {
					throw new IllegalStateException("categoria == null");
				}
				m.setCategoria(categoria);

				CentroCusto centroCusto = getCentroCustoFinder().findBy(1);
				if (grupo == 11) {
					centroCusto = getCentroCustoFinder().findBy(2);
				} else if (grupo == 12) {
					centroCusto = getCentroCustoFinder().findBy(3);
				}
				m.setCentroCusto(centroCusto);

				Integer codigoBanco = ImporterData.mapaLocalizador2Banco.get(rs.getInt("LOCALIZADOR"));

				m.setDocumentoBanco(codigoBanco == null ? null : getBancoFinder().findBy(codigoBanco));

				m.setDtMoviment(rs.getDate("DATA_NOTA"));
				m.setDtVencto(rs.getDate("DATA_VENCTO"));
				m.setDtVenctoEfetiva(getDiaUtilFinder().findProximoDiaUtilFinanceiro(rs.getDate("DATA_VENCTO")));

				Recorrencia recorrencia = new Recorrencia();
				if (rs.getString("MENSAL").equals("S")) {
					recorrencia.setFrequencia(Frequencia.MENSAL);
					recorrencia.setRecorrente(true);
					recorrencia.setTipoRepeticao(TipoRepeticao.DIA_FIXO);
					recorrencia.setDia(m.getDtVencto().getDate());
				}

				m.setRecorrencia(recorrencia);
				m.setStatus(Status.ABERTA);
				m.setValor(rs.getBigDecimal("VALOR"));
				m.setValorTotal(rs.getBigDecimal("VALOR"));

				String nota = rs.getString("NOTA");

				m.setCarteira(getCarteiraFinder().findBy("INDEFINIDA"));
				// Se tem "CH " na descrição, é CHEQUE PRÓPRIO
				if (nota.contains("CH ")) {
					m.setModo(getModoFinder().findBy("CHEQUE PRÓPRIO"));
					// Já marca a carteira correspondente em que vai cair o cheque
					// 4 - ITAÚ				341
					// 5 - BRADESCO			237
					// 6 - BANCO DO BRASIL	001
					if (codigoBanco != null) {
						switch (codigoBanco) {
							case 341:
								m.setCarteira(getCarteiraFinder().findById(4l));
								break;
							case 237:
								m.setCarteira(getCarteiraFinder().findById(5l));
								break;
							case 1:
								m.setCarteira(getCarteiraFinder().findById(1l));
								break;
						}
					}

				} else if (codigoBanco != null) {
					// Se não é CHEQUE PRÓPRIO, mas achou o banco, então marca como boleto
					m.setModo(getModoFinder().findBy("BOLETO/GUIA/DDA"));

				} else {
					m.setModo(getModoFinder().findBy("INDEFINIDO"));
				}

				String descricao = nota;

				if (fornecedor == null) {
					descricao += " (" + rs.getString("RAZAO") + ")";
				}

				m.setDescricao(descricao);

				// qry.setParameter("descricao", m.getDescricao());
				// qry.setParameter("modo", m.getModo());
				// qry.setParameter("carteira", m.getCarteira());
				// qry.setParameter("status", m.getStatus());
				//				qry.setParameter("valorTotal", m.getValorTotal());
				//				qry.setParameter("dtVencto", m.getDtVencto());
				//				qry.setParameter("dtMoviment", m.getDtMoviment());
				//				qry.setParameter("categoria", m.getCategoria());
				// qry.setParameter("pessoa", m.getPessoa());

				Integer idSistemaAntigo = rs.getInt("ID_PARCELA");

				m.setIdSistemaAntigo(idSistemaAntigo);

				Query qry = getMovimentacaoFinder().getEntityManager().createQuery(jpql);
				qry.setParameter("idSistemaAntigo", idSistemaAntigo);

				try {
					// tenta encontrar a movimentação do FIREBIRD no MySQL
					Movimentacao mAtualizar = (Movimentacao) qry.getSingleResult();

					if (mAtualizar.getDtVencto().getTime() != m.getDtVencto().getTime()) {
						logger.info("Mudou a dtVencto: ANTES >> " + mAtualizar.getDtVencto()
								+ ". DEPOIS >>"
								+ m.getDtVencto() + ".");
						mAtualizar.setDtVencto(m.getDtVencto());
						mAtualizar.setDtVenctoEfetiva(getDiaUtilFinder().findProximoDiaUtilFinanceiro(m.getDtVencto()));
					}
					if (mAtualizar.getValor().compareTo(m.getValor()) != 0) {
						logger.info("Mudou o valor: ANTES >> " + mAtualizar.getValor() + ". DEPOIS >>"
								+ m.getValor()
								+ ".");
						mAtualizar.setValor(m.getValor());
						mAtualizar.setValorTotal(m.getValorTotal());
					}

					// getMovimentacaoDataMapper().save(mAtualizar);

					logger.info(">>>>>>>>>>>> ATUALIZANDERSON: " + (++existentes));
				} catch (NoResultException e) {
					logger.info(">>>>>>>>>>>>>>> SALVANDERSON: " + (++salvos));
					// getMovimentacaoDataMapper().save(m);

				} catch (Exception e) {
					logger.error("ERRO.", e);
				}

			}

			//			Collections.sort(ids, new Comparator<Movimentacao>() {
			//
			//				@Override
			//				public int compare(Movimentacao arg0, Movimentacao arg1) {
			//					return new CompareToBuilder()
			//							.append(arg0.getIdSistemaAntigo(), arg1.getIdSistemaAntigo())
			//							.toComparison();
			//				}
			//
			//			});
			//
			//			for (Movimentacao m : ids) {
			//				logger.info(m.getId() + " >>> " + m.getIdSistemaAntigo());
			//			}

			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>> ATUALIZANDERSON: " + (existentes));
			logger.info(">>>>>>>>>>>> SALVANDERSON: " + (salvos));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ViewException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

}
