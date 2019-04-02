package com.bonsucesso.erp.producao.data.importer;



import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import com.bonsucesso.erp.estoque.business.CalculoPreco;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.producao.data.ConfeccaoDataMapper;
import com.bonsucesso.erp.producao.data.ConfeccaoFinder;
import com.bonsucesso.erp.producao.data.ConfeccaoItemDataMapper;
import com.bonsucesso.erp.producao.data.InstituicaoDataMapper;
import com.bonsucesso.erp.producao.data.InstituicaoFinder;
import com.bonsucesso.erp.producao.data.InsumoDataMapper;
import com.bonsucesso.erp.producao.data.InsumoFinder;
import com.bonsucesso.erp.producao.data.LoteConfeccaoDataMapper;
import com.bonsucesso.erp.producao.data.LoteConfeccaoFinder;
import com.bonsucesso.erp.producao.data.LoteConfeccaoItemDataMapper;
import com.bonsucesso.erp.producao.data.TipoArtigoDataMapper;
import com.bonsucesso.erp.producao.data.TipoArtigoFinder;
import com.bonsucesso.erp.producao.data.TipoInsumoFinder;
import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.ConfeccaoItem;
import com.bonsucesso.erp.producao.model.ConfeccaoItemQtde;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.bonsucesso.erp.producao.model.Insumo;
import com.bonsucesso.erp.producao.model.InsumoPreco;
import com.bonsucesso.erp.producao.model.LoteConfeccao;
import com.bonsucesso.erp.producao.model.LoteConfeccaoItem;
import com.bonsucesso.erp.producao.model.LoteConfeccaoItemQtde;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.currency.CurrencyUtils;


@Component("importer")
public class Importer {

	private DataSource dataSourceDataFB;

	@Autowired
	private BeanFactory beanFactory;

	protected static Logger logger = Logger.getLogger(Importer.class);

	private Connection conn;

	public static void main(String... args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

			Importer t = (Importer) context.getBean("importer");

			// t.importarInstituicoes();
			// t.importarArtigos();
			// t.importarInsumos();
			// t.importarProdutos();
			// t.importarProdutosItens();
			// t.importarProdutosItensQtde();

			// t.importarLotes();
			// t.importarLotesItens();
			t.importarLotesItensQtdes();

		} catch (BeansException e) {
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

	private final static Map<Integer, Integer> mapaTipoArtigoSubdepto = new HashMap<Integer, Integer>();

	public void importarArtigos() {

		Importer.mapaTipoArtigoSubdepto.put(40, 13);
		Importer.mapaTipoArtigoSubdepto.put(42, 10);
		Importer.mapaTipoArtigoSubdepto.put(13, 22);
		Importer.mapaTipoArtigoSubdepto.put(113, 22);
		Importer.mapaTipoArtigoSubdepto.put(14, 22);
		Importer.mapaTipoArtigoSubdepto.put(11, 22);
		Importer.mapaTipoArtigoSubdepto.put(114, 22);
		Importer.mapaTipoArtigoSubdepto.put(8, 8);
		Importer.mapaTipoArtigoSubdepto.put(10, 8);
		Importer.mapaTipoArtigoSubdepto.put(30, 11);
		Importer.mapaTipoArtigoSubdepto.put(2, 11);
		Importer.mapaTipoArtigoSubdepto.put(80, 15);
		Importer.mapaTipoArtigoSubdepto.put(83, 17);
		Importer.mapaTipoArtigoSubdepto.put(81, 17);
		Importer.mapaTipoArtigoSubdepto.put(82, 17);
		Importer.mapaTipoArtigoSubdepto.put(75, 30);
		Importer.mapaTipoArtigoSubdepto.put(9, 9);
		Importer.mapaTipoArtigoSubdepto.put(1, 12);
		Importer.mapaTipoArtigoSubdepto.put(12, 22);
		Importer.mapaTipoArtigoSubdepto.put(56, 13);
		Importer.mapaTipoArtigoSubdepto.put(55, 13);

		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM AGA_ARTIGOS";
			pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				TipoArtigo tipoArtigo = new TipoArtigo();
				tipoArtigo.setCodigo(rs.getInt("ID"));

				Subdepartamento subdepto = getSubdeptoFinder()
						.findByCodigo(Importer.mapaTipoArtigoSubdepto.get(rs.getInt("ID")));

				tipoArtigo.setSubdepto(subdepto);
				tipoArtigo.setDescricao(rs.getString("DESCRICAO"));
				getTipoArtigoDataMapper().save(tipoArtigo);
			}
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

	public void importarInstituicoes() {
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM AGA_INSTITUICOES";
			pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Instituicao instituicao = new Instituicao();
				instituicao.setCodigo(rs.getInt("ID"));
				instituicao.setNome(rs.getString("DESCRICAO"));
				getInstituicaoDataMapper().save(instituicao);
			}
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

	public void importarInsumos() {

		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM AGA_INSUMOS";
			pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> ITERANDO: " + i);
				Insumo insumo = new Insumo();

				TipoInsumo tipoInsumo = getTipoInsumoFinder().findByCodigo(rs.getInt("TIPO"));
				if (tipoInsumo == null) {
					throw new IllegalStateException("tipoINsumo não encontrado.");
				}
				insumo.setTipoInsumo(tipoInsumo);
				insumo.setDescricao(rs.getString("DESCRICAO"));
				insumo.setCodigo(rs.getInt("ID"));
				insumo.setUnidadeProduto(tipoInsumo.getUnidadeProdutoPadrao());

				InsumoPreco preco = new InsumoPreco();
				preco.setCustoOperacional(CurrencyUtils.getBigDecimalScaled(35.0, 3));
				preco.setDtCusto(rs.getDate("DATA"));
				preco.setMargem(BigDecimal.ZERO);
				preco.setPrazo(rs.getInt("PRAZO"));
				preco.setPrecoCusto(CurrencyUtils.getBigDecimalScaled(rs.getDouble("PRECO"), 2));
				getCalculoPreco().calcularCoeficiente(preco);
				getCalculoPreco().calcularPrecos(preco);

				insumo.addPreco(preco);

				getInsumoDataMapper().save(insumo);
				logger.info(">>>>>> INSERIDOS " + i++ + " INSUMO");
			}
		} catch (Exception e) {
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

	public void importarLotes() {

		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM AGA_LOTES";
			pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				LoteConfeccao lote = new LoteConfeccao();
				lote.setDtLote(new Date());
				lote.setCodigo(rs.getInt("ID"));
				lote.setDescricao(rs.getString("DESCRICAO"));

				getLoteConfeccaoDataMapper().save(lote);
			}
		} catch (Throwable e) {
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

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public LoteConfeccao findLoteConfeccaoBy(Integer codigo) throws ViewException {
		LoteConfeccao _lote = getLoteConfeccaoFinder().findBy(codigo);
		if (_lote != null) {
			// touch
			_lote.getItens().size();
		}
		return _lote;
	}

	public void importarLotesItens() {

		// pela questão das transações
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Importer t = (Importer) context.getBean("importer");

		PreparedStatement pst = null;
		LoteConfeccao lote = null;

		try {

			String sql = "SELECT LOTE, ESCOLA, ARTIGO " +
					"FROM AGA_MARCACAO M, AGA_LOTES L, AGA_INSTITUICOES E, AGA_ARTIGOS A " +
					"WHERE M.LOTE = L.ID AND M.ESCOLA = E.ID AND M.ARTIGO = A.ID " +
					"GROUP BY LOTE, ESCOLA, ARTIGO " +
					"ORDER BY LOTE, ESCOLA, ARTIGO";
			pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				// gambi para não dar erro no lazy lá embaixo
				LoteConfeccao _lote = t.findLoteConfeccaoBy(rs.getInt("LOTE"));
				if (_lote == null) {
					continue;
				}
				// mudou o lote?
				if (!_lote.equals(lote)) {
					// não é null (já veio da iteração anterior)
					if (lote != null) {
						// salva a anterior
						try {
							getLoteConfeccaoDataMapper().save(lote);
						} catch (Throwable e) {
							e.printStackTrace();
						}
					}
					lote = _lote;
				} else {
					System.out.println("mesmo lote");
				}

				Instituicao instituicao = getInstituicaoFinder().findByCodigo(rs.getInt("ESCOLA"));
				TipoArtigo tipoArtigo = getTipoArtigoFinder().findByCodigo(rs.getInt("ARTIGO"));
				if ((tipoArtigo == null) || (instituicao == null)) {
					continue;
				}

				List<Confeccao> list = getConfeccaoFinder().findBy(instituicao, tipoArtigo);

				if ((list == null) || (list.size() == 0)) {
					continue;
				}

				Confeccao confeccao = list.get(0);

				LoteConfeccaoItem item = new LoteConfeccaoItem();
				item.setConfeccao(confeccao);
				lote.addItem(item);
			}
			// Tem que salvar o último
			try {
				getLoteConfeccaoDataMapper().save(lote);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Throwable e) {
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

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public LoteConfeccaoItem findLoteConfeccaoItemBy(Integer loteCodigo, Integer instituicaoCodigo,
			Integer tipoArtigoCodigo) throws ViewException {
		LoteConfeccaoItem _item = getLoteConfeccaoFinder()
				.findBy(loteCodigo, instituicaoCodigo, tipoArtigoCodigo);
		if (_item != null) {
			_item.getQtdesGrade().size();
		}
		return _item;
	}

	public void importarLotesItensQtdes() {

		// pela questão das transações
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Importer t = (Importer) context.getBean("importer");

		PreparedStatement pst = null;

		int naoEncontrados = 0;
		int erros = 0;

		try {
			GradeTamanho gt02 = getGradeFinder().findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("02")
					.longValue());
			GradeTamanho gt04 = getGradeFinder().findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("04")
					.longValue());
			GradeTamanho gt06 = getGradeFinder().findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("06")
					.longValue());
			GradeTamanho gt08 = getGradeFinder().findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("08")
					.longValue());
			GradeTamanho gt10 = getGradeFinder().findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("10")
					.longValue());
			GradeTamanho gt12 = getGradeFinder().findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("12")
					.longValue());
			GradeTamanho gt14 = getGradeFinder().findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("14")
					.longValue());
			GradeTamanho gt16 = getGradeFinder().findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("16")
					.longValue());
			GradeTamanho gtP = getGradeFinder()
					.findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("P").longValue());
			GradeTamanho gtM = getGradeFinder()
					.findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("M").longValue());
			GradeTamanho gtG = getGradeFinder()
					.findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("G").longValue());
			GradeTamanho gtXG = getGradeFinder().findGradeTamanhoById(Importer.tamanho2GradeTamanho.get("XG")
					.longValue());

			String sql = "SELECT * FROM AGA_MARCACAO ORDER BY LOTE, ESCOLA, ARTIGO";
			pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			LoteConfeccaoItem item = null;

			while (rs.next()) {

				Integer loteCodigo = rs.getInt("LOTE");
				Integer instituicaoCodigo = rs.getInt("ESCOLA");
				Integer tipoArtigoCodigo = rs.getInt("ARTIGO");

				LoteConfeccaoItem _item = t.findLoteConfeccaoItemBy(loteCodigo, instituicaoCodigo, tipoArtigoCodigo);

				if (_item == null) {
					System.out.println(">>>>>>>>>>>>>>>>>>>> não encontrados: " + ++naoEncontrados);
					continue;
				}
				// mudou a confeccao
				if (!_item.equals(item)) {
					// não é null (já veio da iteração anterior)
					if (item != null) {
						// salva a anterior
						try {
							getLoteConfeccaoItemDataMapper().save(item);
						} catch (Throwable e) {
							System.out.println(">>>>>>>>>>>>>>>>>>>> ERROS: " + ++erros);
							e.printStackTrace();
						}
					}
					item = _item;
				}

				Integer dois = rs.getInt("DOIS");
				Integer quatro = rs.getInt("QUATRO");
				Integer seis = rs.getInt("SEIS");
				Integer oito = rs.getInt("OITO");
				Integer dez = rs.getInt("DEZ");
				Integer doze = rs.getInt("DOZE");
				Integer quatorze = rs.getInt("QUATORZE");
				Integer dezesseis = rs.getInt("DEZESSEIS");
				Integer p = rs.getInt("P");
				Integer m = rs.getInt("M");
				Integer g = rs.getInt("G");
				Integer xg = rs.getInt("XG");

				if ((dois != null) && (dois > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gt02);
					qtde.setQtde(dois.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((quatro != null) && (quatro > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gt04);
					qtde.setQtde(quatro.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((seis != null) && (seis > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gt06);
					qtde.setQtde(seis.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((oito != null) && (oito > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gt08);
					qtde.setQtde(oito.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((dez != null) && (dez > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gt10);
					qtde.setQtde(dez.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((doze != null) && (doze > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gt12);
					qtde.setQtde(doze.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((quatorze != null) && (quatorze > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gt14);
					qtde.setQtde(quatorze.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((dezesseis != null) && (dezesseis > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gt16);
					qtde.setQtde(dezesseis.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((p != null) && (p > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gtP);
					qtde.setQtde(p.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((m != null) && (m > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gtM);
					qtde.setQtde(m.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((g != null) && (g > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gtG);
					qtde.setQtde(g.intValue());
					item.getQtdesGrade().add(qtde);
				}
				if ((xg != null) && (xg > 0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setLoteConfeccaoItem(item);
					qtde.setGradeTamanho(gtXG);
					qtde.setQtde(xg.intValue());
					item.getQtdesGrade().add(qtde);
				}

			}
			// salva o último
			try {
				getLoteConfeccaoItemDataMapper().save(item);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				System.out.println(">>>>>>>>>>>>>>>>>>>> ERROS: " + ++erros);
				e.printStackTrace();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			System.out.println(">>>>>>>>>>>>>>>>>>>> ERROS: " + erros);
			System.out.println(">>>>>>>>>>>>>>>>>>>> não encontrados: " + naoEncontrados);
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static final Map<String, Integer> tamanho2GradeTamanho = new HashMap<String, Integer>();

	static {
		Importer.tamanho2GradeTamanho.put("02", 20);
		Importer.tamanho2GradeTamanho.put("04", 22);
		Importer.tamanho2GradeTamanho.put("06", 23);
		Importer.tamanho2GradeTamanho.put("08", 24);
		Importer.tamanho2GradeTamanho.put("10", 25);
		Importer.tamanho2GradeTamanho.put("12", 26);
		Importer.tamanho2GradeTamanho.put("14", 27);
		Importer.tamanho2GradeTamanho.put("16", 28);

		Importer.tamanho2GradeTamanho.put("P", 2);
		Importer.tamanho2GradeTamanho.put("M", 3);
		Importer.tamanho2GradeTamanho.put("G", 4);
		Importer.tamanho2GradeTamanho.put("XG", 5);
		Importer.tamanho2GradeTamanho.put("XGG", 6);

		Importer.tamanho2GradeTamanho.put("UN", 99);
	}

	public void importarProdutos() {
		PreparedStatement pst = null;
		try {
			// no novo sistema, a tabela AGA_PRODUTOS foi normalizada como prod_confeccao e prod_confeccao_item
			String sql = "SELECT ESCOLA, ARTIGO FROM AGA_PRODUTOS " +
					"WHERE ESCOLA IS NOT NULL AND ARTIGO IS NOT NULL " +
					"GROUP BY ESCOLA, ARTIGO ORDER BY ESCOLA, ARTIGO";
			pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				Confeccao confeccao = new Confeccao();
				Instituicao instituicao;
				instituicao = getInstituicaoFinder().findByCodigo(rs.getInt("ESCOLA"));
				if (instituicao == null) {
					instituicao = getInstituicaoFinder().findByCodigo(-1);
				}

				confeccao.setInstituicao(instituicao);

				TipoArtigo tipoArtigo;
				tipoArtigo = getTipoArtigoFinder().findByCodigo(rs.getInt("ARTIGO"));
				if (tipoArtigo == null) {
					tipoArtigo = getTipoArtigoFinder().findByCodigo(-1);
				}
				confeccao.setTipoArtigo(tipoArtigo);
				String descricao = tipoArtigo.getDescricao() + " " + instituicao.getNome();
				confeccao.setDescricao(descricao);

				Confeccao busca = getConfeccaoFinder().findBy(instituicao, tipoArtigo, descricao);
				if (busca != null) {

					Random generator = new Random();
					int i = generator.nextInt(1000) + 1;

					confeccao.setDescricao(descricao + " (" + i + ")");
				}

				getConfeccaoDataMapper().save(confeccao);

			}
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

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Confeccao findConfeccaoBy(Instituicao instituicao, TipoArtigo tipoArtigo) throws ViewException {
		Confeccao _confeccao = null;

		List<Confeccao> results = getConfeccaoFinder().findBy(instituicao, tipoArtigo);
		if ((results != null) && (results.size() > 0)) {
			_confeccao = results.get(0);
		}
		if (_confeccao != null) {
			_confeccao.getItens().size();
		} else {
			// se não encontrou com este tipoArtigo, provável que exista com o tipo de artigo [ERRO DE IMPORTAÇÃO]
			tipoArtigo = getTipoArtigoFinder().findByCodigo(-1);
			_confeccao = getConfeccaoFinder().findBy(instituicao, tipoArtigo).get(0);
			if (_confeccao != null) {
				_confeccao.getItens().size();
			}
		}
		return _confeccao;
	}

	public void importarProdutosItens() {
		// pela questão das transações
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Importer t = (Importer) context.getBean("importer");

		PreparedStatement pst = null;
		int erros = 1;
		int insumosNaoEncontrados = 1;
		Confeccao confeccao = null;
		try {
			// no novo sistema, a tabela AGA_PRODUTOS foi normalizada como prod_confeccao e prod_confeccao_item
			//			String sql = "SELECT ESCOLA, ARTIGO, INSUMO " +
			//					"FROM AGA_PRODUTOS " +
			//					"WHERE ESCOLA IS NOT NULL AND ARTIGO IS NOT NULL AND INSUMO IS NOT NULL " +
			//					"GROUP BY ESCOLA, ARTIGO, INSUMO " +
			//					"ORDER BY ESCOLA, ARTIGO, INSUMO";
			String sql = "SELECT ESCOLA, ARTIGO, INSUMO FROM AGA_PRODUTOS P, AGA_ARTIGOS A, AGA_INSUMOS I, AGA_INSTITUICOES E  WHERE ESCOLA IS NOT NULL AND ARTIGO IS NOT NULL AND INSUMO IS NOT NULL AND  P.ARTIGO = A.ID AND P.INSUMO = I.ID AND P.ESCOLA = E.ID  GROUP BY ESCOLA, ARTIGO, INSUMO ORDER BY ESCOLA, ARTIGO, INSUMO";

			pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				Instituicao instituicao;
				instituicao = getInstituicaoFinder().findByCodigo(rs.getInt("ESCOLA"));
				if (instituicao == null) {
					System.out.println("Não encontrei ESCOLA: " + rs.getInt("ESCOLA"));
					instituicao = getInstituicaoFinder().findByCodigo(-1);
				}

				TipoArtigo tipoArtigo;
				tipoArtigo = getTipoArtigoFinder().findByCodigo(rs.getInt("ARTIGO"));
				if (tipoArtigo == null) {
					System.out.println("Não encontrei TIPO_ARTIGO: " + rs.getInt("ARTIGO"));
					tipoArtigo = getTipoArtigoFinder().findByCodigo(-1);
				}

				// procura a confeccao desta linha (pego o primeiro item que retorna, pois no programa do fábio é apenas uma confecção por instituição/tipoartigo
				Confeccao _confeccao = t.findConfeccaoBy(instituicao, tipoArtigo);
				if (_confeccao == null) {
					System.out.println(">>>>>>>>>>>>>>>>>>>>> ERROS: " + erros++);
					continue;
				}
				// mudou a confeccao
				if (!_confeccao.equals(confeccao)) {
					// não é null (já veio da iteração anterior)
					if (confeccao != null) {
						// salva a anterior
						try {
							getConfeccaoDataMapper().save(confeccao);
						} catch (Exception e) {
							System.out.println(">>>>>>>>>>>>>>>>>>>>> ERROS: " + erros++);
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					confeccao = _confeccao;
				}

				ConfeccaoItem item = new ConfeccaoItem();
				item.setConfeccao(confeccao);

				Insumo insumo;
				insumo = getInsumoFinder().findByCodigo(rs.getInt("INSUMO"));
				if (insumo == null) {
					System.out.println(">>>>>>>>>>>>>>>>>>>>> insumos não encontrados: " + insumosNaoEncontrados++);
					insumo = getInsumoFinder().findByCodigo(-1);
				}
				item.setInsumo(insumo);

				// verifica se já não foi inserido um item assim, pois como a base do Fábio está corrompida, pode ter registros duplicados que violem a chave UNIQUE
				//				if (getConfeccaoFinder().findItemBy(instituicao, tipoArtigo, insumo, gradeTamanho) != null) {
				//					continue;
				//				}

				boolean existe = false;
				if ((confeccao != null) && (confeccao.getItens() != null)) {
					for (ConfeccaoItem i : confeccao.getItens()) {
						if (i.equals(item)) {
							existe = true;
							break;
						}
					}
				}
				if (!existe) {
					confeccao.addItem(item);
				} else {
					System.out.println(">>>>>>>>>>>>>>>>>>>>> ERROS: " + erros++);
					System.out.println("já existe");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ViewException e) {
			e.printStackTrace();
		} finally {
			try {
				getConfeccaoDataMapper().save(confeccao);
			} catch (Exception e) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>> ERROS: " + erros++);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(">>>>>>>>>>>>>>>>>>>>> ERROS: " + erros);
			System.out.println(">>>>>>>>>>>>>>>>>>>>> insumos não encontrados: " + insumosNaoEncontrados);
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public ConfeccaoItem findConfeccaoItemBy(Integer instituicaoCodigo, Integer tipoArtigoCodigo, Integer insumoCodigo)
			throws ViewException {
		ConfeccaoItem _item = getConfeccaoFinder()
				.findConfeccaoItemBy(instituicaoCodigo, tipoArtigoCodigo, insumoCodigo);
		if (_item != null) {
			_item.getQtdesGrade().size();
		} else {

		}
		return _item;
	}

	public void importarProdutosItensQtde() {

		// pela questão das transações
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Importer t = (Importer) context.getBean("importer");

		PreparedStatement pst = null;
		int naoAchei = 1;
		int erros = 1;
		int naoSalvei = 1;
		try {
			// no novo sistema, a tabela AGA_PRODUTOS foi normalizada como prod_confeccao e prod_confeccao_item
			String sql = "SELECT ESCOLA, ARTIGO, INSUMO, TAMANHO, QTDE " +
					"FROM AGA_PRODUTOS " +
					"WHERE ESCOLA IS NOT NULL AND ARTIGO IS NOT NULL AND INSUMO IS NOT NULL " +
					"GROUP BY ESCOLA, ARTIGO, INSUMO, TAMANHO, QTDE " +
					"ORDER BY ESCOLA, ARTIGO, INSUMO, TAMANHO, QTDE";

			pst = getConn().prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			ConfeccaoItem item = null;
			ConfeccaoItem _item = null;

			Integer instituicaoCodigo = null;
			Integer tipoArtigoCodigo = null;
			Integer insumoCodigo = null;

			while (rs.next()) {

				Integer _instituicaoCodigo = rs.getInt("ESCOLA");
				Integer _tipoArtigoCodigo = rs.getInt("ARTIGO");
				Integer _insumoCodigo = rs.getInt("INSUMO");

				// se mudou ESCOLA, ARTIGO ou INSUMO
				if (!_instituicaoCodigo.equals(instituicaoCodigo) || !_tipoArtigoCodigo.equals(tipoArtigoCodigo)
						|| !_insumoCodigo.equals(insumoCodigo)) {

					instituicaoCodigo = _instituicaoCodigo;
					tipoArtigoCodigo = _tipoArtigoCodigo;
					insumoCodigo = _insumoCodigo;

					_item = t.findConfeccaoItemBy(instituicaoCodigo, tipoArtigoCodigo, insumoCodigo);
					if (_item == null) {
						erros++;
						System.out.println(">>>>>>>>>>>>> NÃO ACHEI: " + naoAchei++);
						instituicaoCodigo = null;
						tipoArtigoCodigo = null;
						insumoCodigo = null;
						continue;
					}
				}

				// mudou o item
				if (!_item.equals(item)) {
					// não é null (já veio da iteração anterior)
					if (item != null) {
						// salva a anterior
						try {
							getConfeccaoItemDataMapper().save(item);
						} catch (Exception e) {
							erros++;
							System.out.println(">>>>>>>>>>>>>>>>>>>>>> NÃO SALVEI: " + naoSalvei++);
							e.printStackTrace();
						}
					}
					item = _item;
				}

				ConfeccaoItemQtde ciQtde = new ConfeccaoItemQtde();
				ciQtde.setConfeccaoItem(_item);

				String tamanho = rs.getString("TAMANHO");
				// Padrão: UN
				GradeTamanho gradeTamanho = getGradeFinder().findById(11l).getTamanhos().get(0);
				if ((tamanho != null) && !"".equals(tamanho.trim())) {
					// Verifica se o tamanho retornado está no mapa que já retorna o id do GradeTamanho correspondente...
					Integer id = Importer.tamanho2GradeTamanho.get(tamanho);
					if (id != null) {
						gradeTamanho = getGradeFinder().findGradeTamanhoById(id.longValue());
					}
				}
				ciQtde.setGradeTamanho(gradeTamanho);

				// verifica se já não foi inserido um item assim, pois como a base do Fábio está corrompida, pode ter registros duplicados que violem a chave UNIQUE
				//				if (getConfeccaoFinder().findItemBy(instituicao, tipoArtigo, insumo, gradeTamanho) != null) {
				//					continue;
				//				}

				try {
					if ((rs.getString("QTDE") == null) || "".equals(rs.getString("QTDE").trim())) {
						ciQtde.setQtde(BigDecimal.ZERO);
					} else {
						Double qtde = Double.parseDouble(rs.getString("QTDE").replace(",", "."));
						ciQtde.setQtde(CurrencyUtils.getBigDecimalScaled(qtde, 3));
					}
				} catch (Exception e) {
					ciQtde.setQtde(BigDecimal.ZERO);
				}

				item.addQtdeGrade(ciQtde);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ViewException e) {
			e.printStackTrace();
		} finally {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>> NÃO SALVEI: " + naoSalvei);
			System.out.println(">>>>>>>>>>>>> NÃO ACHEI: " + naoAchei);
			System.out.println(">>>>>>> ERROS: " + erros);
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Autowired
	private InstituicaoDataMapper instituicaoDataMapper;

	@Autowired
	private TipoArtigoDataMapper tipoArtigoDataMapper;

	@Autowired
	private SubdeptoFinder subdeptoFinder;

	@Autowired
	private InstituicaoFinder instituicaoFinder;

	@Autowired
	private TipoArtigoFinder tipoArtigoFinder;

	@Autowired
	private TipoInsumoFinder tipoInsumoFinder;

	@Autowired
	private InsumoFinder insumoFinder;

	@Autowired
	private InsumoDataMapper insumoDataMapper;

	@Autowired
	private LoteConfeccaoDataMapper loteConfeccaoDataMapper;

	@Autowired
	private LoteConfeccaoItemDataMapper loteConfeccaoItemDataMapper;

	@Autowired
	private ConfeccaoDataMapper confeccaoDataMapper;

	@Autowired
	private ConfeccaoItemDataMapper confeccaoItemDataMapper;

	@Autowired
	private ConfeccaoFinder confeccaoFinder;

	@Autowired
	private CalculoPreco calculoPreco;

	@Autowired
	private GradeFinder gradeFinder;

	@Autowired
	private LoteConfeccaoFinder loteConfeccaoFinder;

	public InstituicaoDataMapper getInstituicaoDataMapper() {
		return instituicaoDataMapper;
	}

	public void setInstituicaoDataMapper(InstituicaoDataMapper instituicaoDataMapper) {
		this.instituicaoDataMapper = instituicaoDataMapper;
	}

	public TipoArtigoDataMapper getTipoArtigoDataMapper() {
		return tipoArtigoDataMapper;
	}

	public void setTipoArtigoDataMapper(TipoArtigoDataMapper tipoArtigoDataMapper) {
		this.tipoArtigoDataMapper = tipoArtigoDataMapper;
	}

	public SubdeptoFinder getSubdeptoFinder() {
		return subdeptoFinder;
	}

	public void setSubdeptoFinder(SubdeptoFinder subdeptoFinder) {
		this.subdeptoFinder = subdeptoFinder;
	}

	public InstituicaoFinder getInstituicaoFinder() {
		return instituicaoFinder;
	}

	public void setInstituicaoFinder(InstituicaoFinder instituicaoFinder) {
		this.instituicaoFinder = instituicaoFinder;
	}

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

	public InsumoFinder getInsumoFinder() {
		return insumoFinder;
	}

	public void setInsumoFinder(InsumoFinder insumoFinder) {
		this.insumoFinder = insumoFinder;
	}

	public InsumoDataMapper getInsumoDataMapper() {
		return insumoDataMapper;
	}

	public void setInsumoDataMapper(InsumoDataMapper insumoDataMapper) {
		this.insumoDataMapper = insumoDataMapper;
	}

	public ConfeccaoFinder getConfeccaoFinder() {
		return confeccaoFinder;
	}

	public void setConfeccaoFinder(ConfeccaoFinder confeccaoFinder) {
		this.confeccaoFinder = confeccaoFinder;
	}

	public ConfeccaoDataMapper getConfeccaoDataMapper() {
		return confeccaoDataMapper;
	}

	public void setConfeccaoDataMapper(ConfeccaoDataMapper confeccaoDataMapper) {
		this.confeccaoDataMapper = confeccaoDataMapper;
	}

	public ConfeccaoItemDataMapper getConfeccaoItemDataMapper() {
		return confeccaoItemDataMapper;
	}

	public void setConfeccaoItemDataMapper(ConfeccaoItemDataMapper confeccaoItemDataMapper) {
		this.confeccaoItemDataMapper = confeccaoItemDataMapper;
	}

	public CalculoPreco getCalculoPreco() {
		return calculoPreco;
	}

	public void setCalculoPreco(CalculoPreco calculoPreco) {
		this.calculoPreco = calculoPreco;
	}

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

	public LoteConfeccaoDataMapper getLoteConfeccaoDataMapper() {
		return loteConfeccaoDataMapper;
	}

	public void setLoteConfeccaoDataMapper(LoteConfeccaoDataMapper loteConfeccaoDataMapper) {
		this.loteConfeccaoDataMapper = loteConfeccaoDataMapper;
	}

	public LoteConfeccaoItemDataMapper getLoteConfeccaoItemDataMapper() {
		return loteConfeccaoItemDataMapper;
	}

	public void setLoteConfeccaoItemDataMapper(LoteConfeccaoItemDataMapper loteConfeccaoItemDataMapper) {
		this.loteConfeccaoItemDataMapper = loteConfeccaoItemDataMapper;
	}

	public LoteConfeccaoFinder getLoteConfeccaoFinder() {
		return loteConfeccaoFinder;
	}

	public void setLoteConfeccaoFinder(LoteConfeccaoFinder loteConfeccaoFinder) {
		this.loteConfeccaoFinder = loteConfeccaoFinder;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

}
