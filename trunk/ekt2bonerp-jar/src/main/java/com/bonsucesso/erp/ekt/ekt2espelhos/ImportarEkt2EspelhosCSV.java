package com.bonsucesso.erp.ekt.ekt2espelhos;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.data.EKTDeptoDataMapper;
import com.bonsucesso.erp.ekt.data.EKTEncomendaDataMapper;
import com.bonsucesso.erp.ekt.data.EKTEncomendaFinder;
import com.bonsucesso.erp.ekt.data.EKTEncomendaItemDataMapper;
import com.bonsucesso.erp.ekt.data.EKTEncomendaItemFinder;
import com.bonsucesso.erp.ekt.data.EKTFornecedorDataMapper;
import com.bonsucesso.erp.ekt.data.EKTGradeDataMapper;
import com.bonsucesso.erp.ekt.data.EKTPedidoDataMapper;
import com.bonsucesso.erp.ekt.data.EKTPedidoFinder;
import com.bonsucesso.erp.ekt.data.EKTPlanoCrediarioDataMapper;
import com.bonsucesso.erp.ekt.data.EKTPlanoPagtoDataMapper;
import com.bonsucesso.erp.ekt.data.EKTProdutoDataMapper;
import com.bonsucesso.erp.ekt.data.EKTSubDeptoDataMapper;
import com.bonsucesso.erp.ekt.data.EKTVendaDataMapper;
import com.bonsucesso.erp.ekt.data.EKTVendaFinder;
import com.bonsucesso.erp.ekt.data.EKTVendaItemDataMapper;
import com.bonsucesso.erp.ekt.data.EKTVendaItemFinder;
import com.bonsucesso.erp.ekt.data.EKTVendedorDataMapper;
import com.bonsucesso.erp.ekt.model.EKTDepto;
import com.bonsucesso.erp.ekt.model.EKTEncomenda;
import com.bonsucesso.erp.ekt.model.EKTEncomendaItem;
import com.bonsucesso.erp.ekt.model.EKTFornecedor;
import com.bonsucesso.erp.ekt.model.EKTPedido;
import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.bonsucesso.erp.ekt.model.EKTSubDepto;
import com.bonsucesso.erp.ekt.model.EKTVenda;
import com.bonsucesso.erp.ekt.model.EKTVendaItem;
import com.bonsucesso.erp.ekt.model.EKTVendedor;
import com.ocabit.base.model.EntityId;
import com.ocabit.base.model.InsertUpdateDateTime;
import com.ocabit.base.model.UserInfo;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.model.Estabelecimento;
import com.ocabit.security.model.User;
import com.ocabit.security.spring.SecurityUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.strings.Mailer;
import com.ocabit.utils.strings.StringUtils;


/**
 * Importa dados do EKT (dos arquivos CSVs gerados pelos scripts /opt/bon-lnx/exporta_***) para as tabelas espelho (prefixo ekt_).
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("importarEkt2EspelhosCSV")
@Scope("prototype") // para poder rodar com thread (não retorna sempre o mesmo bean)
public class ImportarEkt2EspelhosCSV implements IImportarEkt2Espelhos {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5571680205648591553L;

	private static boolean depoisDeSetembro2015;
	private static boolean depoisDeOutubro2015;
	private static boolean depoisDeDezembro2015;

	private static String MESANOIMPORT;

	/**
	 * P ou D
	 */
	private static String AMBIENTE = "P";

	private String path;

	public static SimpleDateFormat sdfLog = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

	private static String dirLogs;

	public static String logFile;

	public static PrintWriter w;

	protected static Logger logger = Logger.getLogger(ImportarEkt2EspelhosCSV.class);

	private IImportarEkt2Espelhos thiz;

	private static Map<TipoImportacao, StringBuffer> results = new HashMap<TipoImportacao, StringBuffer>();

	private TipoImportacao tipoImportacao;

	public final static String nl = System.getProperty("line.separator");

	private Connection conn;

	public Double handleDouble(String d) {

		if (d == null) {
			return 0.0;
		} else {
			try {
				return Double.parseDouble(d);
			} catch (NumberFormatException e) {
				return 0.0;
			}
		}
	}

	/**
	 * Importar os registros do arquivo EST_D002 para a tabela ekt_deptos.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarFornecedores() throws ViewException {

		SecurityUtils.mockUser();

		int i = 0;

		try {

			//			getEktFornecedorDataMapper().truncateTable();
			getEktFornecedorDataMapper().deleteByMesano(MESANOIMPORT);

			// 1	RECORD_NUMBER			4	INTEGER
			// 2	CODIGO					3	DECIMAL
			// 3	RAZAO					12	VARCHAR
			// 4	NOME_FANTASIA			12	VARCHAR
			// 5	CGC						12	VARCHAR
			// 6	INSC					12	VARCHAR
			// 7	DATA_CAD				9	DATE
			// 8	ENDERECO				12	VARCHAR
			// 9	BAIRRO					12	VARCHAR
			// 10	MUNICIPIO				12	VARCHAR
			// 11	UF						12	VARCHAR
			// 12	CEP						12	VARCHAR
			// 13	DDD_FONE				12	VARCHAR
			// 14	FONE					12	VARCHAR
			// 15	DDD_FAX					12	VARCHAR
			// 16	FAX						12	VARCHAR
			// 17	CONTATO					12	VARCHAR
			// 18	NOME_REPRES				12	VARCHAR
			// 19	DDD_REPRES				12	VARCHAR
			// 20	FONE_REPRES				12	VARCHAR
			// 21	COMPRAS_AC				3	DECIMAL
			// 22	DATA_ULT_COMP			9	DATE
			// 23	PECAS_AC				3	DECIMAL
			// 24	TIPO					12	VARCHAR
			// 25	DT_ULTALT				6	DATE

			try (BufferedReader br = new BufferedReader(new FileReader(getPath() + "est_d002.csv"))) {

				String linha;
				while ((linha = br.readLine()) != null) {
					i++;
					String[] campos = linha.split(";", -1);
					if (campos.length < 23) {
						throw new ViewException("Campos faltando para a linha [" + linha + "]");
					}
					for (int c = 0; c < campos.length; c++)
						campos[c] = campos[c] != null ? campos[c].trim() : null;

					EKTFornecedor ektFornecedor = new EKTFornecedor();

					ektFornecedor.setRecordNumber(i);
					ektFornecedor.setCodigo(handleDouble(campos[1]));
					ektFornecedor.setRazao(campos[2]);
					ektFornecedor.setNomeFantasia(campos[3]);
					ektFornecedor.setCgc(campos[4]);
					ektFornecedor.setInsc(campos[5]);
					ektFornecedor.setDataCad(CalendarUtil.getDate(campos[6], false));
					ektFornecedor.setEndereco(campos[7]);
					ektFornecedor.setBairro(campos[8]);
					ektFornecedor.setMunicipio(campos[9]);
					ektFornecedor.setUf(campos[10]);
					ektFornecedor.setCep(campos[11]);
					ektFornecedor.setDddFone(campos[12]);
					ektFornecedor.setFone(campos[13]);
					ektFornecedor.setDddFax(campos[14]);
					ektFornecedor.setFax(campos[15]);
					ektFornecedor.setContato(campos[16]);
					ektFornecedor.setNomeRepres(campos[17]);
					ektFornecedor.setDddRepres(campos[18]);
					ektFornecedor.setFoneRepres(campos[19]);
					ektFornecedor.setComprasAc(handleDouble(campos[20]));
					ektFornecedor.setDataUltComp(CalendarUtil.getDate(campos[21], false));
					ektFornecedor.setPecasAc(handleDouble(campos[22]));
					ektFornecedor.setTipo(campos[23]);

					ektFornecedor.setMesano(MESANOIMPORT);

					if (depoisDeDezembro2015) {
						ektFornecedor.setDtUltAlt(CalendarUtil.getDate(campos[24], false));
					}

					handleIudtUserInfo(ektFornecedor);

					getEktFornecedorDataMapper().save(ektFornecedor);
					logger.info(i + " (FORNECEDOR) importado(s)");

				}

			} catch (IOException e) {
				throw new ViewException("IOException importarFornecedores()");
			}
		} catch (Exception e) {
			results.get(getTipoImportacao()).append("ERRO AO IMPORTAR FORNECEDORES: " + e.getMessage() + nl);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new ViewException(e);
		}

		results.get(getTipoImportacao()).append(i + " FORNECEDOR(ES) IMPORTADO(S)" + nl);

	}

	/**
	 * Importar os registros do arquivo EST_D003 para a tabela ekt_deptos.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarDeptos() throws ViewException {

		SecurityUtils.mockUser();

		int i = 0;

		try {
			try (BufferedReader br = new BufferedReader(new FileReader(getPath() + "est_d003.csv"))) {

				String linha;
				while ((linha = br.readLine()) != null) {
					i++;
					String[] campos = linha.split(";", -1);
					for (int c = 0; c < campos.length; c++)
						campos[c] = campos[c] != null ? campos[c].trim() : null;

					//			RECORD_NUMBER	4	INTEGER
					//			CODIGO	3	DECIMAL
					//			DESCRICAO	12	VARCHAR
					//			MARGEM	3	DECIMAL
					//			PECAS_AC	3	DECIMAL
					//			VENDAS_AC	3	DECIMAL

					EKTDepto ektDepto = new EKTDepto();

					ektDepto.setRecordNumber(i);
					ektDepto.setCodigo(handleDouble(campos[1]));
					ektDepto.setDescricao(campos[2]);
					ektDepto.setMargem(handleDouble(campos[3]));
					ektDepto.setPecasAc(handleDouble(campos[4]));
					ektDepto.setVendasAc(handleDouble(campos[5]));

					handleIudtUserInfo(ektDepto);

					try {
						getEktDeptoDataMapper().save(ektDepto);
					} catch (ViewException e) {
						logger.error("ERRO AO SALVAR DEPTO: " + campos[1], e);
						logger.error("Linha: [" + linha + "]");
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
					}

					logger.info(i + " (DEPTO) importado(s)");

				}

			} catch (IOException e) {
				throw new ViewException("IOException importarDeptos()");
			}
		} catch (Exception e) {
			results.get(getTipoImportacao()).append("ERRO AO IMPORTAR DEPTOS: " + e.getMessage() + nl);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new ViewException(e);
		}
		results.get(getTipoImportacao()).append(i + " DEPTO(S) IMPORTADO(S)" + nl);

	}

	/**
	 * Importar os registros do arquivo EST_D004 para a tabela ekt_subdeptos.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarSubDeptos() throws ViewException {

		SecurityUtils.mockUser();

		int i = 0;

		try {

			getEktSubDeptoDataMapper().truncateTable();

			try (BufferedReader br = new BufferedReader(new FileReader(getPath() + "est_d004.csv"))) {

				String linha;
				while ((linha = br.readLine()) != null) {
					i++;
					String[] campos = linha.split(";", -1);
					for (int c = 0; c < campos.length; c++)
						campos[c] = campos[c] != null ? campos[c].trim() : null;

					//			RECORD_NUMBER	4	INTEGER
					//			CODIGO	3	DECIMAL
					//			DESCRICAO	12	VARCHAR
					//			MARGEM	3	DECIMAL
					//			PECAS_AC01	3	DECIMAL
					//			PECAS_AC02	3	DECIMAL
					//			PECAS_AC03	3	DECIMAL
					//			PECAS_AC04	3	DECIMAL
					//			PECAS_AC05	3	DECIMAL
					//			PECAS_AC06	3	DECIMAL
					//			PECAS_AC07	3	DECIMAL
					//			PECAS_AC08	3	DECIMAL
					//			PECAS_AC09	3	DECIMAL
					//			PECAS_AC10	3	DECIMAL
					//			PECAS_AC11	3	DECIMAL
					//			PECAS_AC12	3	DECIMAL
					//			VENDAS_AC01	3	DECIMAL
					//			VENDAS_AC02	3	DECIMAL
					//			VENDAS_AC03	3	DECIMAL
					//			VENDAS_AC04	3	DECIMAL
					//			VENDAS_AC05	3	DECIMAL
					//			VENDAS_AC06	3	DECIMAL
					//			VENDAS_AC07	3	DECIMAL
					//			VENDAS_AC08	3	DECIMAL
					//			VENDAS_AC09	3	DECIMAL
					//			VENDAS_AC10	3	DECIMAL
					//			VENDAS_AC11	3	DECIMAL
					//			VENDAS_AC12	3	DECIMAL
					//			SAZON	12	VARCHAR

					EKTSubDepto ektSubDepto = new EKTSubDepto();

					ektSubDepto.setRecordNumber(i);
					ektSubDepto.setCodigo(handleDouble(campos[0]));
					ektSubDepto.setDescricao(campos[1]);
					ektSubDepto.setMargem(handleDouble(campos[2]));
					ektSubDepto.setPecasAc01(handleDouble(campos[3]));
					ektSubDepto.setPecasAc02(handleDouble(campos[4]));
					ektSubDepto.setPecasAc03(handleDouble(campos[5]));
					ektSubDepto.setPecasAc04(handleDouble(campos[6]));
					ektSubDepto.setPecasAc05(handleDouble(campos[7]));
					ektSubDepto.setPecasAc06(handleDouble(campos[8]));
					ektSubDepto.setPecasAc07(handleDouble(campos[9]));
					ektSubDepto.setPecasAc08(handleDouble(campos[10]));
					ektSubDepto.setPecasAc09(handleDouble(campos[11]));
					ektSubDepto.setPecasAc10(handleDouble(campos[12]));
					ektSubDepto.setPecasAc11(handleDouble(campos[13]));
					ektSubDepto.setPecasAc12(handleDouble(campos[14]));
					ektSubDepto.setVendasAc01(handleDouble(campos[15]));
					ektSubDepto.setVendasAc02(handleDouble(campos[16]));
					ektSubDepto.setVendasAc03(handleDouble(campos[17]));
					ektSubDepto.setVendasAc04(handleDouble(campos[18]));
					ektSubDepto.setVendasAc05(handleDouble(campos[19]));
					ektSubDepto.setVendasAc06(handleDouble(campos[20]));
					ektSubDepto.setVendasAc07(handleDouble(campos[21]));
					ektSubDepto.setVendasAc08(handleDouble(campos[22]));
					ektSubDepto.setVendasAc09(handleDouble(campos[23]));
					ektSubDepto.setVendasAc10(handleDouble(campos[24]));
					ektSubDepto.setVendasAc11(handleDouble(campos[25]));
					ektSubDepto.setVendasAc12(handleDouble(campos[26]));
					ektSubDepto.setSazon(campos[27]);

					handleIudtUserInfo(ektSubDepto);

					try {
						getEktSubDeptoDataMapper().save(ektSubDepto);
					} catch (ViewException e) {
						logger.error("ERRO AO SALVAR SUBDEPTO: " + campos[0], e);
						logger.error("Linha: [" + linha + "]");
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
					}

					logger.info(i + " (SUBDEPTO) importado(s)");
				}

			} catch (IOException e) {
				throw new ViewException("IOException importarSubdeptos()");
			}
		} catch (Exception e) {
			results.get(getTipoImportacao()).append("ERRO AO IMPORTAR SUBDEPTOS: " + e.getMessage() + nl);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new ViewException(e);
		}

		results.get(getTipoImportacao()).append(i + " SUBDEPTO(S) IMPORTADO(S)" + nl);

	}

	/**
	 * Importar os registros do arquivo EST_D005 para a tabela ekt_grade.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarGrades() throws ViewException {
		// fazer manualmente se precisar
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void deletarProdutosMesAno() throws ViewException {
		getEktProdutoDataMapper().getEntityManager()
				.createNativeQuery("DELETE FROM ekt_produto WHERE mesano = '" + MESANOIMPORT + "'").executeUpdate();
		getEktProdutoDataMapper().getEntityManager().flush();
	}

	/**
	 * Importar os registros do arquivo EST_D006 para a tabela ekt_produtos.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarProdutos() throws ViewException {

		SecurityUtils.mockUser();

		int i = 0;

		// Apaga tudo do mesano que está sendo importado e reinsere		
		getThiz().deletarProdutosMesAno();

		try {

			try (BufferedReader br = new BufferedReader(new FileReader(getPath() + "est_d006.csv"))) {

				String linha;
				while ((linha = br.readLine()) != null) {
					i++;
					String[] campos = linha.split(";", -1);
					for (int c = 0; c < campos.length; c++)
						campos[c] = campos[c] != null ? campos[c].trim() : null;

					EKTProduto ektProduto = new EKTProduto();

					ektProduto.setMesano(MESANOIMPORT);

					ektProduto.setRecordNumber(i);
					ektProduto.setOvlProd(campos[1]);
					ektProduto.setFornec(handleDouble(campos[2]));
					ektProduto.setReferencia(campos[3]);
					ektProduto.setGrade(handleDouble(campos[4]));
					ektProduto.setDepto(handleDouble(campos[5]));
					ektProduto.setSubdepto(handleDouble(campos[6]));
					ektProduto.setReduzido(handleDouble(campos[7]));
					ektProduto.setDescricao(campos[8]);
					ektProduto.setDataPCusto(CalendarUtil.getDate(campos[9], false));
					ektProduto.setpCusto(handleDouble(campos[10]));
					ektProduto.setDataPVenda(CalendarUtil.getDate(campos[11], false));
					ektProduto.setpVista(handleDouble(campos[12]));
					ektProduto.setpPrazo(handleDouble(campos[13]));
					ektProduto.setpPromo(handleDouble(campos[14]));
					ektProduto.setDataUltVenda(CalendarUtil.getDate(campos[15], false));
					ektProduto.setPrazo(handleDouble(campos[16]));
					ektProduto.setMargem(handleDouble(campos[17]));
					ektProduto.setMargemC(handleDouble(campos[18]));
					ektProduto.setCoef(handleDouble(campos[19]));
					ektProduto.setQt01(handleDouble(campos[20]));
					ektProduto.setQt02(handleDouble(campos[21]));
					ektProduto.setQt03(handleDouble(campos[22]));
					ektProduto.setQt04(handleDouble(campos[23]));
					ektProduto.setQt05(handleDouble(campos[24]));
					ektProduto.setQt06(handleDouble(campos[25]));
					ektProduto.setQt07(handleDouble(campos[26]));
					ektProduto.setQt08(handleDouble(campos[27]));
					ektProduto.setQt09(handleDouble(campos[28]));
					ektProduto.setQt10(handleDouble(campos[29]));
					ektProduto.setQt11(handleDouble(campos[30]));
					ektProduto.setQt12(handleDouble(campos[31]));
					ektProduto.setQt13(handleDouble(campos[32]));
					ektProduto.setQt14(handleDouble(campos[33]));
					ektProduto.setQt15(handleDouble(campos[34]));
					ektProduto.setAc01(handleDouble(campos[35]));
					ektProduto.setAc02(handleDouble(campos[36]));
					ektProduto.setAc03(handleDouble(campos[37]));
					ektProduto.setAc04(handleDouble(campos[38]));
					ektProduto.setAc05(handleDouble(campos[39]));
					ektProduto.setAc06(handleDouble(campos[40]));
					ektProduto.setAc07(handleDouble(campos[41]));
					ektProduto.setAc08(handleDouble(campos[42]));
					ektProduto.setAc09(handleDouble(campos[43]));
					ektProduto.setAc10(handleDouble(campos[44]));
					ektProduto.setAc11(handleDouble(campos[45]));
					ektProduto.setAc12(handleDouble(campos[46]));
					ektProduto.setStatus(campos[47]);
					ektProduto.setUnidade(campos[48]);
					ektProduto.setDataCad(CalendarUtil.getDate(campos[49], false));
					ektProduto.setModelo(campos[50]);
					ektProduto.setQtdeMes(handleDouble(campos[51]));
					ektProduto.setF1(campos[52]);
					ektProduto.setF2(campos[53]);
					ektProduto.setF3(campos[54]);
					ektProduto.setF4(campos[55]);
					ektProduto.setF5(campos[56]);
					ektProduto.setF6(campos[57]);
					ektProduto.setF7(campos[58]);
					ektProduto.setF8(campos[59]);
					ektProduto.setF9(campos[60]);
					ektProduto.setF10(campos[61]);
					ektProduto.setF11(campos[62]);
					ektProduto.setF12(campos[63]);
					ektProduto.setUltVender(handleDouble(campos[64]));

					if (depoisDeSetembro2015) {
						try {
							ektProduto.setIcms(handleDouble(campos[65]));
							ektProduto.setNcm(campos[66]);
						} catch (Exception e1) {
							logger.info(">>>>>>>>>>>>>>>>>> ERRO COLUNAS NFCE");
							results.get(getTipoImportacao()).append("ERRO COLUNAS NFCE: ICMS+NCM" + nl);
							results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e1) + nl);
						}
					}
					if (depoisDeOutubro2015) {
						try {
							ektProduto.setFracionado(campos[67]);
							ektProduto.setCst(campos[68]);
							ektProduto.setTipoTrib(campos[69]);
						} catch (Exception e1) {
							logger.info(">>>>>>>>>>>>>>>>>> ERRO COLUNAS NFCE");
							results.get(getTipoImportacao()).append("ERRO COLUNAS NFCE: FRACIONADO+CST+TIPO_TRIB" + nl);
							results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e1) + nl);
						}
					}

					if (depoisDeDezembro2015) {
						ektProduto.setDtUltAlt(CalendarUtil.getDate(campos[70], false));
					}

					handleIudtUserInfo(ektProduto);

					try {
						getEktProdutoDataMapper().save(ektProduto);
					} catch (ViewException e) {
						logger.error("ERRO AO SALVAR PRODUTO: " + campos[7], e);
						logger.error("Linha: [" + linha + "]");
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
					}

					logger.info(i + " (PRODUTO) importado(s)");
				}

			} catch (IOException e) {
				throw new ViewException("IOException importarProdutos()");
			}
		} catch (Exception e) {
			results.get(getTipoImportacao()).append("ERRO AO IMPORTAR PRODUTOS: " + e.getMessage() + nl);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new ViewException(e);
		}

		results.get(getTipoImportacao()).append(i + " PRODUTO(S) IMPORTADO(S)" + nl);
	}

	/**
	 * Importar os registros do arquivo EST_D008 para a tabela ekt_vendedor.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarVendedores() throws ViewException {

		SecurityUtils.mockUser();

		int i = 0;

		try {
			getEktVendedorDataMapper().truncateTable();

			try (BufferedReader br = new BufferedReader(new FileReader(getPath() + "est_d008.csv"))) {

				String linha;
				while ((linha = br.readLine()) != null) {
					i++;
					String[] campos = linha.split(";", -1);
					for (int c = 0; c < campos.length; c++)
						campos[c] = campos[c] != null ? campos[c].trim() : null;

					//	RECORD_NUMBER	INTEGER
					//	CODIGO			DECIMAL
					//	NOME			VARCHAR
					//	COMIS_VIS		DECIMAL
					//	COMIS_PRA		DECIMAL
					//	FLAG_GER		DECIMAL
					//	SENHA			DECIMAL

					EKTVendedor ektVendedor = new EKTVendedor();

					ektVendedor.setRecordNumber(i);
					ektVendedor.setCodigo(handleDouble(campos[1]));
					ektVendedor.setNome(campos[2]);
					ektVendedor.setComisVis(handleDouble(campos[3]));
					ektVendedor.setComisPra(handleDouble(campos[4]));
					ektVendedor.setFlagGer(campos[5]);
					ektVendedor.setSenha(campos[6]);

					handleIudtUserInfo(ektVendedor);

					try {
						getEktVendedorDataMapper().save(ektVendedor);
					} catch (ViewException e) {
						logger.error("ERRO AO SALVAR VENDEDOR: " + campos[1], e);
						logger.error("Linha: [" + linha + "]");
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
					}

					logger.info(i + " (VENDEDOR) importado(s)");
				}

			} catch (IOException e) {
				throw new ViewException("IOException importarVendedores()");
			}
		} catch (Exception e) {
			results.get(getTipoImportacao()).append("ERRO AO IMPORTAR VENDEDORES: " + e.getMessage() + nl);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new ViewException(e);
		}

		results.get(getTipoImportacao()).append(i + " VENDEDORES(S) IMPORTADO(S)" + nl);

	}

	/**
	 * Importar os registros do arquivo PED_D020 para a tabela ekt_pedidos.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarPedidos() throws ViewException {

		SecurityUtils.mockUser();

		int i = 0;

		// Só importa pedidos de 1 ano atrás pra cá
		List<Double> naFonte;
		Date hoje = new Date();
		Calendar cUmAnoAtras = Calendar.getInstance();
		cUmAnoAtras.setTime(hoje);
		cUmAnoAtras.add(Calendar.MONTH, -12);

		try {

			try (BufferedReader br = new BufferedReader(new FileReader(getPath() + "ped_d020.csv"))) {

				String linha;
				naFonte = new ArrayList<Double>();

				while ((linha = br.readLine()) != null) {
					i++;
					String[] campos = linha.split(";", -1);
					for (int c = 0; c < campos.length; c++)
						campos[c] = campos[c] != null ? campos[c].trim() : null;

					Date dtEmissao = CalendarUtil.getDate(campos[2], false);

					try {
						if (CalendarUtil.dataMenorIgualQueData(dtEmissao, CalendarUtil.getDate(01, 01, 2014))) {
							continue;
						}
					} catch (Exception e) {
						logger.error(e);
						continue;
					}

					Double numPedido = handleDouble(campos[1]);

					try {
						naFonte.add(numPedido);
					} catch (Exception e2) {
						logger.error("ERRO AO OBTER NUMERO DO PEDIDO", e2);
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e2) + nl);
						continue;
					}

					try {
						// Verifica se esta venda já foi importada.
						List<EKTPedido> existentes = getEktPedidoFinder().findBy(numPedido);
						if (existentes != null && !existentes.isEmpty()) {
							logger.info("PEDIDO " + numPedido + " já existente na base");
							continue;
						}
					} catch (ViewException e1) {
						logger.error("ERRO AO PESQUISAR PEDIDO EXISTENTE", e1);
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e1) + nl);
						throw new IllegalStateException("ERRO AO PESQUISAR PEDIDO EXISTENTE", e1);
					}

					EKTPedido ektPedido = new EKTPedido();

					ektPedido.setRecordNumber(i);
					ektPedido.setPedido(numPedido);
					ektPedido.setEmissao(dtEmissao);
					ektPedido.setFornec(handleDouble(campos[3]));
					ektPedido.setDd1(handleDouble(campos[4]));
					ektPedido.setDd2(handleDouble(campos[5]));
					ektPedido.setDd3(handleDouble(campos[6]));
					ektPedido.setDd4(handleDouble(campos[7]));
					ektPedido.setDd5(handleDouble(campos[8]));
					ektPedido.setEntrega(CalendarUtil.getDate(campos[9], false));
					ektPedido.setTotal(handleDouble(campos[10]));
					ektPedido.setSubDepto(handleDouble(campos[11]));
					ektPedido.setQtde(handleDouble(campos[12]));
					ektPedido.setDescNf(handleDouble(campos[13]));
					ektPedido.setDescDp(handleDouble(campos[14]));
					ektPedido.setMesEnt(handleDouble(campos[15]));
					ektPedido.setAnoEnt(handleDouble(campos[16]));
					ektPedido.setpUnit(handleDouble(campos[17]));
					ektPedido.setpTotal(handleDouble(campos[18]));
					ektPedido.setPrazo(handleDouble(campos[19]));
					ektPedido.setQtdeBx(handleDouble(campos[20]));
					ektPedido.setpTotalBx(handleDouble(campos[21]));

					handleIudtUserInfo(ektPedido);

					try {
						getEktPedidoDataMapper().save(ektPedido);
					} catch (ViewException e) {
						logger.error("ERRO AO SALVAR PEDIDO: " + numPedido, e);
						logger.error("Linha: [" + linha + "]");
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
					}

					logger.info(i + " (PEDIDO) importado(s)");
				}

			} catch (IOException e) {
				throw new ViewException("IOException importarPedidos()");
			}
		} catch (Exception e) {
			results.get(getTipoImportacao()).append("ERRO AO IMPORTAR PEDIDOS: " + e.getMessage() + nl);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new ViewException(e);
		}
		results.get(getTipoImportacao()).append(i + " PEDIDO(S) IMPORTADO(S)" + nl);
	}

	/**
	 * Importar os registros do arquivo PED_D070 para a tabela ekt_encomendas.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarEncomendas() throws ViewException {

		SecurityUtils.mockUser();

		int i = 0;

		List<Double> naFonte = new ArrayList<Double>();

		try {

			getEktEncomendaDataMapper().truncateTable();

			try (BufferedReader br = new BufferedReader(new FileReader(getPath() + "ped_d070.csv"))) {

				String linha;
				while ((linha = br.readLine()) != null) {
					i++;
					String[] campos = linha.split(";", -1);
					for (int c = 0; c < campos.length; c++)
						campos[c] = campos[c] != null ? campos[c].trim() : null;

					Double numEncomenda = handleDouble(campos[0]);

					naFonte.add(numEncomenda);

					EKTEncomenda ektEncomenda = new EKTEncomenda();

					ektEncomenda.setRecordNumber(i);
					ektEncomenda.setNumero(numEncomenda);
					ektEncomenda.setSerie(campos[1]);
					ektEncomenda.setEmissao(CalendarUtil.getDate(campos[2], false));
					ektEncomenda.setVendedor(handleDouble(campos[3]));
					ektEncomenda.setCodPlano(handleDouble(campos[4]));
					ektEncomenda.setPlano(campos[5]);
					ektEncomenda.setMensagem(campos[6]);
					ektEncomenda.setHistDesc(campos[7]);
					ektEncomenda.setSubTotal(handleDouble(campos[8]));
					ektEncomenda.setDescAcres(handleDouble(campos[9]));
					ektEncomenda.setDescEspecial(handleDouble(campos[10]));
					ektEncomenda.setTotal(handleDouble(campos[11]));
					ektEncomenda.setNomeCliente(campos[12]);
					ektEncomenda.setCondPag(campos[13]);
					ektEncomenda.setFlagDV(campos[14]);
					ektEncomenda.setEmitido(campos[15]);
					ektEncomenda.setV1(CalendarUtil.getDate(campos[16], false));
					ektEncomenda.setV2(CalendarUtil.getDate(campos[17], false));
					ektEncomenda.setV3(CalendarUtil.getDate(campos[18], false));
					ektEncomenda.setV4(CalendarUtil.getDate(campos[19], false));
					ektEncomenda.setV5(CalendarUtil.getDate(campos[20], false));
					ektEncomenda.setV6(CalendarUtil.getDate(campos[21], false));
					ektEncomenda.setP1(handleDouble(campos[22]));
					ektEncomenda.setP2(handleDouble(campos[23]));
					ektEncomenda.setP3(handleDouble(campos[24]));
					ektEncomenda.setP4(handleDouble(campos[25]));
					ektEncomenda.setP5(handleDouble(campos[26]));
					ektEncomenda.setP6(handleDouble(campos[27]));
					ektEncomenda.setCliente(handleDouble(campos[28]));
					ektEncomenda.setFone(campos[29]);
					ektEncomenda.setPrazo(campos[30]);
					ektEncomenda.setSdoPagar(handleDouble(campos[31]));

					handleIudtUserInfo(ektEncomenda);

					try {
						getEktEncomendaDataMapper().save(ektEncomenda);
					} catch (ViewException e) {
						logger.error("ERRO AO SALVAR ENCOMENDA: " + numEncomenda, e);
						logger.error("Linha: [" + linha + "]");
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
					}

					logger.info(i + " (ENCOMENDA) importada(s)");
				}

			} catch (IOException e) {
				throw new ViewException("IOException importarEncomendas()");
			}
		} catch (Exception e) {
			results.get(getTipoImportacao()).append("ERRO AO IMPORTAR ENCOMENDAS: " + e.getMessage() + nl);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new ViewException(e);
		}

		results.get(getTipoImportacao()).append(i + " ENCOMENDA(S) IMPORTADO(S)" + nl);

		int d = 0;
		try {
			// Verificação de deletados
			List<EKTEncomenda> ektEncomendas = getEktEncomendaFinder().findAllValidas(0, 1000000);
			for (EKTEncomenda ektEncomenda : ektEncomendas) {
				if (!naFonte.contains(ektEncomenda.getNumero())) {
					getEktEncomendaDataMapper().delete(ektEncomenda);
					// deleta também os itens
					List<EKTEncomendaItem> itens = getEktEncomendaItemFinder().findBy(ektEncomenda.getNumero());
					for (EKTEncomendaItem item : itens) {
						getEktEncomendaItemDataMapper().delete(item);
					}
					logger.info(++d + " encomenda(s) deletada(s)");
				}
			}
		} catch (ViewException e) {
			logger.error("ERRO AO LIDAR COM ENCOMENDAS DELETADAS", e);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new IllegalStateException("ERRO AO LIDAR COM ENCOMENDAS DELETADAS", e);
		}

		results.get(getTipoImportacao()).append(d + " ENCOMENDA(S) DELETADA(S)" + nl);

	}

	/**
	 * Importar os registros do arquivo PED_D071 para a tabela ekt_encomend.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarEncomendaItem() throws ViewException {

		SecurityUtils.mockUser();

		int i = 0;

		try {

			getEktEncomendaItemDataMapper().truncateTable();

			try (BufferedReader br = new BufferedReader(new FileReader(getPath() + "ped_d071.csv"))) {

				String linha;

				while ((linha = br.readLine()) != null) {
					i++;
					String[] campos = linha.split(";", -1);
					for (int c = 0; c < campos.length; c++)
						campos[c] = campos[c] != null ? campos[c].trim() : null;

					Double numEncomenda = handleDouble(campos[1]);
					Double reduzido = handleDouble(campos[4]);

					EKTEncomendaItem ektEncomendaItem = new EKTEncomendaItem();

					ektEncomendaItem.setRecordNumber(i);
					ektEncomendaItem.setNumeroNF(numEncomenda);
					ektEncomendaItem.setSerie(campos[2]);
					ektEncomendaItem.setTela(handleDouble(campos[3]));
					ektEncomendaItem.setProduto(reduzido);
					ektEncomendaItem.setQtde(handleDouble(campos[5]));
					ektEncomendaItem.setUnidade(campos[6]);
					ektEncomendaItem.setDescricao(campos[7]);
					ektEncomendaItem.setTamanho(campos[8]);
					ektEncomendaItem.setVlrUnit(handleDouble(campos[9]));
					ektEncomendaItem.setVlrTotal(handleDouble(campos[10]));
					ektEncomendaItem.setWin(handleDouble(campos[11]));
					ektEncomendaItem.setPrecoCusto(handleDouble(campos[12]));
					ektEncomendaItem.setPrecoVista(handleDouble(campos[13]));
					ektEncomendaItem.setObs(campos[14]);
					ektEncomendaItem.setFlag(campos[15]);
					ektEncomendaItem.setFornec(handleDouble(campos[16]));
					ektEncomendaItem.setReferencia(campos[17]);
					ektEncomendaItem.setGrade(handleDouble(campos[18]));
					ektEncomendaItem.setDepto(handleDouble(campos[19]));
					ektEncomendaItem.setSubDepto(handleDouble(campos[20]));
					ektEncomendaItem.setEmissao(CalendarUtil.getDate(campos[21], false));
					ektEncomendaItem.setFlagInt(campos[22]);

					handleIudtUserInfo(ektEncomendaItem);

					try {
						getEktEncomendaItemDataMapper().save(ektEncomendaItem);
					} catch (ViewException e) {
						logger.error("ERRO AO SALVAR ITEM DA ENCOMENDA: " + numEncomenda + " - "
								+ reduzido, e);
						logger.error("Linha: [" + linha + "]");
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
					}

					logger.info(i + " (EncomendaItem) importado(s)");
				}

			} catch (IOException e) {
				throw new ViewException("IOException importarEncomendaItem()");
			}
		} catch (Exception e) {
			results.get(getTipoImportacao()).append("ERRO AO IMPORTAR ITENS DE ENCOMENDAS: " + e.getMessage() + nl);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new ViewException(e);
		}

		results.get(getTipoImportacao()).append(i + " ITENS DE ENCOMENDAS IMPORTADOS" + nl);

		int d = 0;
		try {
			// Verificação de deletados
			List<EKTEncomendaItem> itens = getEktEncomendaItemFinder().findAll();
			for (EKTEncomendaItem item : itens) {
				if (getEktEncomendaFinder().findBy(item.getNumeroNF()) == null) {
					getEktEncomendaItemDataMapper().delete(item);
					logger.info(++d + " itens de encomendas deletados");
				}
			}
		} catch (ViewException e) {
			String err = "ERRO AO LIDAR COM ITENS DE ENCOMENDAS DELETADOS";
			logger.error(err, e);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new IllegalStateException(err, e);
		}

		results.get(getTipoImportacao()).append(d + " ITENS DE ENCOMENDAS DELETADOS" + nl);

	}

	/**
	 * Importar os registros do arquivo VEN_D053 para a tabela ekt_plano_crediario.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarPlanosCrediario() throws ViewException {
		// verificar manualmente se precisar
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void deletarVendasDoMesAno() throws ViewException {
		getEktVendaDataMapper().getEntityManager()
				.createNativeQuery("DELETE FROM ekt_venda WHERE mesano = '" + MESANOIMPORT + "'").executeUpdate();
		getEktVendaDataMapper().getEntityManager().flush();
	}

	/**
	 * Importar os registros do arquivo VEN_D060 para a tabela ekt_venda.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarVendas() throws ViewException {

		SecurityUtils.mockUser();

		int i = 0;

		try {

			getThiz().deletarVendasDoMesAno();

			try (BufferedReader br = new BufferedReader(new FileReader(getPath() + "ven_d060.csv"))) {

				String linha;

				while ((linha = br.readLine()) != null) {
					i++;
					String[] campos = linha.split(";", -1);
					for (int c = 0; c < campos.length; c++)
						campos[c] = campos[c] != null ? campos[c].trim() : null;

					if (campos.length < 44) {
						throw new ViewException("Erro ao importar linha com menos de 44 campos: '" + linha + "'");
					}

					Double pv = handleDouble(campos[1]);

					EKTVenda ektVenda = new EKTVenda();

					ektVenda.setMesano(MESANOIMPORT);

					ektVenda.setRecordNumber(i);
					ektVenda.setNumero(pv);
					ektVenda.setSerie(campos[2]);
					ektVenda.setEmissao(CalendarUtil.getDate(campos[3], false));
					ektVenda.setVendedor(handleDouble(campos[4]));
					ektVenda.setCodPlano(handleDouble(campos[5]));
					ektVenda.setPlano(campos[6]);
					ektVenda.setMensagem(campos[7]);
					ektVenda.setHistDesc(campos[8]);
					ektVenda.setSubTotal(handleDouble(campos[9]));
					ektVenda.setDescAcres(handleDouble(campos[10]));
					ektVenda.setDescEspecial(handleDouble(campos[11]));
					ektVenda.setTotal(handleDouble(campos[12]));
					ektVenda.setNomeCliente(campos[13]);
					ektVenda.setCondPag(campos[14]);
					ektVenda.setFlagDV(campos[15]);
					ektVenda.setEmitido(campos[16]);
					ektVenda.setV1(CalendarUtil.getDate(campos[17], false));
					ektVenda.setV2(CalendarUtil.getDate(campos[18], false));
					ektVenda.setV3(CalendarUtil.getDate(campos[19], false));
					ektVenda.setV4(CalendarUtil.getDate(campos[20], false));
					ektVenda.setV5(CalendarUtil.getDate(campos[21], false));
					ektVenda.setV6(CalendarUtil.getDate(campos[22], false));
					ektVenda.setV7(CalendarUtil.getDate(campos[23], false));
					ektVenda.setP1(handleDouble(campos[24]));
					ektVenda.setP2(handleDouble(campos[25]));
					ektVenda.setP3(handleDouble(campos[26]));
					ektVenda.setP4(handleDouble(campos[27]));
					ektVenda.setP5(handleDouble(campos[28]));
					ektVenda.setP6(handleDouble(campos[29]));
					ektVenda.setP7(handleDouble(campos[30]));
					ektVenda.setCliente(handleDouble(campos[31]));
					ektVenda.setV8(CalendarUtil.getDate(campos[32], false));
					ektVenda.setV9(CalendarUtil.getDate(campos[33], false));
					ektVenda.setV10(CalendarUtil.getDate(campos[34], false));
					ektVenda.setV11(CalendarUtil.getDate(campos[35], false));
					ektVenda.setV12(CalendarUtil.getDate(campos[36], false));
					ektVenda.setV13(CalendarUtil.getDate(campos[37], false));
					ektVenda.setP8(handleDouble(campos[38]));
					ektVenda.setP9(handleDouble(campos[39]));
					ektVenda.setP10(handleDouble(campos[40]));
					ektVenda.setP11(handleDouble(campos[41]));
					ektVenda.setP12(handleDouble(campos[42]));
					ektVenda.setP13(handleDouble(campos[43]));

					handleIudtUserInfo(ektVenda);

					try {
						getEktVendaDataMapper().save(ektVenda);
					} catch (ViewException e) {
						logger.error("ERRO AO SALVAR VENDA: " + pv, e);
						logger.error("Linha: [" + linha + "]");
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
					}

					logger.info(i + " (VENDA) importado(s)");
				}

			} catch (IOException e) {
				throw new ViewException("IOException importarVendas()");
			}
		} catch (Exception e) {
			results.get(getTipoImportacao()).append("ERRO AO IMPORTAR VENDAS: " + e.getMessage() + nl);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new ViewException(e);
		}

		try {
			getEktVendaDataMapper().getEntityManager().flush();
		} catch (Exception e) {
			logger.error("ERRO AO DAR O FLUSH para vendas");
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
		}

		results.get(getTipoImportacao()).append(i + " VENDA(S) IMPORTADO(S)" + nl);

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void deletarVendasItensDoMesAno() throws ViewException {
		getEktVendaItemDataMapper().getEntityManager()
				.createNativeQuery("DELETE FROM ekt_venda_item WHERE mesano = '" + MESANOIMPORT + "'").executeUpdate();
		getEktVendaItemDataMapper().getEntityManager().flush();
	}

	/**
	 * Importar os registros do arquivo VEN_D061 para a tabela ekt_venda_item.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarVendasItens() throws ViewException {

		SecurityUtils.mockUser();

		int i = 0;

		try {

			getThiz().deletarVendasItensDoMesAno();

			try (BufferedReader br = new BufferedReader(new FileReader(getPath() + "ven_d061.csv"))) {

				String linha;

				while ((linha = br.readLine()) != null) {
					i++;
					String[] campos = linha.split(";", -1);
					for (int c = 0; c < campos.length; c++)
						campos[c] = campos[c] != null ? campos[c].trim() : null;

					Double pv = handleDouble(campos[1]);
					Double reduzido = handleDouble(campos[4]);

					EKTVendaItem ektVendaItem = new EKTVendaItem();

					ektVendaItem.setMesano(MESANOIMPORT);

					ektVendaItem.setRecordNumber(i);
					ektVendaItem.setNumeroNF(pv);
					ektVendaItem.setSerie(campos[2]);
					ektVendaItem.setTela(handleDouble(campos[3]));
					ektVendaItem.setProduto(reduzido);
					ektVendaItem.setQtde(handleDouble(campos[5]));
					ektVendaItem.setUnidade(campos[6]);
					ektVendaItem.setDescricao(campos[7]);

					String tamanho = campos[8] != null ? campos[8] : "";
					if (tamanho.length() > 3) {
						tamanho = tamanho.substring(0, 2);
					}

					ektVendaItem.setTamanho(tamanho);
					ektVendaItem.setVlrUnit(handleDouble(campos[9]));
					ektVendaItem.setVlrTotal(handleDouble(campos[10]));
					ektVendaItem.setWin(handleDouble(campos[11]));
					ektVendaItem.setPrecoCusto(handleDouble(campos[12]));
					ektVendaItem.setPrecoVista(handleDouble(campos[13]));

					handleIudtUserInfo(ektVendaItem);

					try {
						getEktVendaItemDataMapper().save(ektVendaItem);
						getEktVendaItemDataMapper().getEntityManager().flush();
					} catch (ViewException e) {
						logger.error("ERRO AO SALVAR ITEM DE VENDA: " + pv + " - " + reduzido, e);
						logger.error("Linha: [" + linha + "]");
						results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
					}

					logger.info(i + " (VendaItem) importado(s)");
				}

			} catch (IOException e) {
				throw new ViewException("IOException importarVendasItens()");
			}
		} catch (Exception e) {
			results.get(getTipoImportacao()).append("ERRO AO IMPORTAR ITENS DE VENDAS: " + e.getMessage() + nl);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new ViewException(e);
		}

		try {
			getEktVendaItemDataMapper().getEntityManager().flush();
		} catch (Exception e) {
			logger.error("ERRO AO DAR O FLUSH para vendasitens");
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
		}

		results.get(getTipoImportacao()).append(i + " ITENS DE VENDAS IMPORTADO(S)" + nl);

	}

	/**
	 * Importar os registros do arquivo VEN_D062 para a tabela ekt_plano_pagto.
	 *
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarPlanosPagto() throws ViewException {
		// verificar manualmente (nunca é alterado)
	}

	public void handleIudtUserInfo(EntityId e) {
		Estabelecimento estabelecimento = getEktDeptoDataMapper().getEntityManager().find(Estabelecimento.class, 1l);
		User user = getEktDeptoDataMapper().getEntityManager().find(User.class, 1l);
		e.setUserInfo(new UserInfo(estabelecimento, user, user));
		e.setIudt(new InsertUpdateDateTime(new Date(), new Date()));
	}

	@Autowired
	BasicDataSource ds;

	public boolean checkColumns(String table, Set<String> colunasEsperadas) throws ViewException {
		try {
			String query = "sp_columns " + table;
			Statement stmt = getThiz().getConn().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			Set<String> colunasAtuais = new HashSet<String>();
			while (rs.next()) {
				colunasAtuais.add(rs.getString("COLUMNNAME"));
			}

			rs.close();
			stmt.close();

			return CollectionUtils.disjunction(colunasEsperadas, colunasAtuais).isEmpty();
		} catch (SQLException e) {
			logger.error("Erro ao verificar as colunas da tabela " + table, e);
			results.get(getTipoImportacao()).append(StringUtils.stackTraceToString(e) + nl);
			throw new IllegalStateException("Erro ao verificar as colunas da tabela " + table, e);
		}
	}

	@Autowired
	private EKTFornecedorDataMapper ektFornecedorDataMapper;

	@Autowired
	private EKTDeptoDataMapper ektDeptoDataMapper;

	@Autowired
	private EKTSubDeptoDataMapper ektSubDeptoDataMapper;

	@Autowired
	private EKTGradeDataMapper ektGradeDataMapper;

	@Autowired
	private EKTProdutoDataMapper ektProdutoDataMapper;

	@Autowired
	private EKTPedidoDataMapper ektPedidoDataMapper;

	@Autowired
	private EKTPedidoFinder ektPedidoFinder;

	@Autowired
	private EKTEncomendaDataMapper ektEncomendaDataMapper;

	@Autowired
	private EKTEncomendaFinder ektEncomendaFinder;

	@Autowired
	private EKTEncomendaItemDataMapper ektEncomendaItemDataMapper;

	@Autowired
	private EKTEncomendaItemFinder ektEncomendaItemFinder;

	@Autowired
	private EKTVendedorDataMapper ektVendedorDataMapper;

	@Autowired
	private EKTPlanoCrediarioDataMapper ektPlanoCrediarioDataMapper;

	@Autowired
	private EKTVendaDataMapper ektVendaDataMapper;

	@Autowired
	private EKTVendaFinder ektVendaFinder;

	@Autowired
	private EKTVendaItemDataMapper ektVendaItemDataMapper;

	@Autowired
	private EKTVendaItemFinder ektVendaItemFinder;

	@Autowired
	private EKTPlanoPagtoDataMapper ektPlanoPagtoDataMapper;

	@Autowired
	private BeanFactory beanFactory;

	@Override
	public Connection getConn() {
		if (conn == null) {
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				conn = DriverManager.getConnection("jdbc:odbc:dfodbc_atual");
			} catch (Exception e) {
				logger.error("ERRO AO OBTER CONEXÃO COM DATAFLEX", e);
				throw new IllegalStateException("ERRO AO OBTER CONEXÃO COM DATAFLEX", e);
			}
		}
		if (conn == null) {
			throw new IllegalStateException("Sem conexão");
		}
		return conn;
	}

	public String getPath() {
		if (path == null) {
			path = AMBIENTE.equals("P") ? "/mnt/10.1.1.100-export/" : "\\\\10.1.1.100\\export\\";
		}
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public IImportarEkt2Espelhos getThiz() {
		return thiz;
	}

	public void setThiz(IImportarEkt2Espelhos thiz) {
		this.thiz = thiz;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public EKTFornecedorDataMapper getEktFornecedorDataMapper() {
		return ektFornecedorDataMapper;
	}

	public void setEktFornecedorDataMapper(EKTFornecedorDataMapper ektFornecedorDataMapper) {
		this.ektFornecedorDataMapper = ektFornecedorDataMapper;
	}

	public EKTDeptoDataMapper getEktDeptoDataMapper() {
		return ektDeptoDataMapper;
	}

	public void setEktDeptoDataMapper(EKTDeptoDataMapper ektDeptoDataMapper) {
		this.ektDeptoDataMapper = ektDeptoDataMapper;
	}

	public EKTSubDeptoDataMapper getEktSubDeptoDataMapper() {
		return ektSubDeptoDataMapper;
	}

	public void setEktSubDeptoDataMapper(EKTSubDeptoDataMapper ektSubDeptoDataMapper) {
		this.ektSubDeptoDataMapper = ektSubDeptoDataMapper;
	}

	public EKTGradeDataMapper getEktGradeDataMapper() {
		return ektGradeDataMapper;
	}

	public void setEktGradeDataMapper(EKTGradeDataMapper ektGradeDataMapper) {
		this.ektGradeDataMapper = ektGradeDataMapper;
	}

	public EKTProdutoDataMapper getEktProdutoDataMapper() {
		return ektProdutoDataMapper;
	}

	public void setEktProdutoDataMapper(EKTProdutoDataMapper ektProdutoDataMapper) {
		this.ektProdutoDataMapper = ektProdutoDataMapper;
	}

	public EKTPedidoDataMapper getEktPedidoDataMapper() {
		return ektPedidoDataMapper;
	}

	public void setEktPedidoDataMapper(EKTPedidoDataMapper ektPedidoDataMapper) {
		this.ektPedidoDataMapper = ektPedidoDataMapper;
	}

	public EKTPedidoFinder getEktPedidoFinder() {
		return ektPedidoFinder;
	}

	public void setEktPedidoFinder(EKTPedidoFinder ektPedidoFinder) {
		this.ektPedidoFinder = ektPedidoFinder;
	}

	public EKTEncomendaDataMapper getEktEncomendaDataMapper() {
		return ektEncomendaDataMapper;
	}

	public void setEktEncomendaDataMapper(EKTEncomendaDataMapper ektEncomendaDataMapper) {
		this.ektEncomendaDataMapper = ektEncomendaDataMapper;
	}

	public EKTEncomendaFinder getEktEncomendaFinder() {
		return ektEncomendaFinder;
	}

	public void setEktEncomendaFinder(EKTEncomendaFinder ektEncomendaFinder) {
		this.ektEncomendaFinder = ektEncomendaFinder;
	}

	public EKTEncomendaItemDataMapper getEktEncomendaItemDataMapper() {
		return ektEncomendaItemDataMapper;
	}

	public void setEktEncomendaItemDataMapper(EKTEncomendaItemDataMapper ektEncomendaItemDataMapper) {
		this.ektEncomendaItemDataMapper = ektEncomendaItemDataMapper;
	}

	public EKTEncomendaItemFinder getEktEncomendaItemFinder() {
		return ektEncomendaItemFinder;
	}

	public void setEktEncomendaItemFinder(EKTEncomendaItemFinder ektEncomendaItemFinder) {
		this.ektEncomendaItemFinder = ektEncomendaItemFinder;
	}

	public EKTVendedorDataMapper getEktVendedorDataMapper() {
		return ektVendedorDataMapper;
	}

	public void setEktVendedorDataMapper(EKTVendedorDataMapper ektVendedorDataMapper) {
		this.ektVendedorDataMapper = ektVendedorDataMapper;
	}

	public EKTPlanoCrediarioDataMapper getEktPlanoCrediarioDataMapper() {
		return ektPlanoCrediarioDataMapper;
	}

	public void setEktPlanoCrediarioDataMapper(EKTPlanoCrediarioDataMapper ektPlanoCrediarioDataMapper) {
		this.ektPlanoCrediarioDataMapper = ektPlanoCrediarioDataMapper;
	}

	public EKTVendaDataMapper getEktVendaDataMapper() {
		return ektVendaDataMapper;
	}

	public void setEktVendaDataMapper(EKTVendaDataMapper ektVendaDataMapper) {
		this.ektVendaDataMapper = ektVendaDataMapper;
	}

	public EKTVendaFinder getEktVendaFinder() {
		return ektVendaFinder;
	}

	public void setEktVendaFinder(EKTVendaFinder ektVendaFinder) {
		this.ektVendaFinder = ektVendaFinder;
	}

	public EKTVendaItemDataMapper getEktVendaItemDataMapper() {
		return ektVendaItemDataMapper;
	}

	public void setEktVendaItemDataMapper(EKTVendaItemDataMapper ektVendaItemDataMapper) {
		this.ektVendaItemDataMapper = ektVendaItemDataMapper;
	}

	public EKTVendaItemFinder getEktVendaItemFinder() {
		return ektVendaItemFinder;
	}

	public void setEktVendaItemFinder(EKTVendaItemFinder ektVendaItemFinder) {
		this.ektVendaItemFinder = ektVendaItemFinder;
	}

	public EKTPlanoPagtoDataMapper getEktPlanoPagtoDataMapper() {
		return ektPlanoPagtoDataMapper;
	}

	public void setEktPlanoPagtoDataMapper(EKTPlanoPagtoDataMapper ektPlanoPagtoDataMapper) {
		this.ektPlanoPagtoDataMapper = ektPlanoPagtoDataMapper;
	}

	public TipoImportacao getTipoImportacao() {
		return tipoImportacao;
	}

	public void setTipoImportacao(TipoImportacao tipoImportacao) {
		this.tipoImportacao = tipoImportacao;
	}

	public static void escreverResultados() {
		for (Map.Entry<TipoImportacao, StringBuffer> e : results.entrySet()) {
			w.println(e.getKey());
			w.print(e.getValue().toString());
			w.println();
		}
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		long ini = System.currentTimeMillis();

		System.out.println("Informe o mês import. Formato: YYYYMM");

		// Pode ter de 1 a 3 parâmetros
		if (args.length < 1 || args.length > 3) {
			erroDeParametros();
			System.exit(-1);
		}

		// args[0] - tipo de importação
		// args[1] - ambiente
		// args[2] - mesano

		// Se só tem 1, deve ser o [FOR,PROD,PED,VEN,ENC]
		if (args.length == 1) {
			// default: mesano atual
			MESANOIMPORT = new SimpleDateFormat("yyyyMM").format(new Date());
		} else if (args.length == 2) {
			if (!("P".equals(args[1])) && (!("D".equals(args[1])))) {
				erroDeParametros();
				System.exit(-1);
			}
			AMBIENTE = args[1];
		} else if (args.length == 3) {

			if (!("P".equals(args[1])) && (!("D".equals(args[1])))) {
				erroDeParametros();
				System.exit(-1);
			}
			AMBIENTE = args[1];

			MESANOIMPORT = args[2];
			try {
				if (MESANOIMPORT.length() != 6) {
					throw new IllegalStateException();
				}
				Integer.parseInt(args[2]);
			} catch (Exception e1) {
				System.out.println("Erro ao parsear o mesImport");
				erroDeParametros();
				System.exit(-1);
			}
		}

		try {
			Date dtMesAnoImport = new SimpleDateFormat("yyyyMM").parse(MESANOIMPORT);

			depoisDeSetembro2015 = CalendarUtil.dataMaiorQueData(dtMesAnoImport, CalendarUtil.getDate(30, 9, 2015));
			depoisDeOutubro2015 = CalendarUtil.dataMaiorQueData(dtMesAnoImport, CalendarUtil.getDate(31, 10, 2015));
			depoisDeDezembro2015 = CalendarUtil.dataMaiorQueData(dtMesAnoImport, CalendarUtil.getDate(31, 12, 2015));
		} catch (ParseException e2) {
			e2.printStackTrace();
			System.exit(-1);
		}

		dirLogs = AMBIENTE.equals("P") ? "/home/ocabit/bonerp/logs/" : "D:/home/ocabit/bonerp/logs/";

		logFile = dirLogs + org.apache.commons.lang3.StringUtils.join(args) + sdfLog.format(new Date()) + ".txt";

		try {
			w = new PrintWriter(logFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.out.println("Erro ao printar no log");
			w.println("Erro ao printar no log");
			System.exit(-1);
		}

		w.println("<<< IMPORTAÇÃO >>>");
		w.println(logFile);
		w.println("Importando para: " + MESANOIMPORT);

		System.out.println("Importando para: " + MESANOIMPORT);

		String[] tiposImportacoes = args[0].split(",");

		// se só passou 1 parâmetro, então deve ser o dos tipos de importações: [FOR,PROD,PED,VEN,ENC]
		try {
			if (tiposImportacoes.length < 1) {
				erroDeParametros();
				System.exit(-1);
			}
			for (String tipo : tiposImportacoes) {
				if (!tipo.equals("GERAIS") &&
						!tipo.equals("FOR") &&
						!tipo.equals("PROD") &&
						!tipo.equals("PED") &&
						!tipo.equals("VEN") &&
						!tipo.equals("ENC")) {
					erroDeParametros();
					System.exit(-1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			w.println("Erro nos parametros");
			System.exit(-1);
		}

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		results.put(TipoImportacao.FORNECEDORES, new StringBuffer());
		results.put(TipoImportacao.PRODUTOS, new StringBuffer());
		results.put(TipoImportacao.PEDIDOS, new StringBuffer());
		results.put(TipoImportacao.VENDAS, new StringBuffer());
		results.put(TipoImportacao.ENCOMENDAS, new StringBuffer());
		results.put(TipoImportacao.GERAIS, new StringBuffer());

		IImportarEkt2Espelhos t = (IImportarEkt2Espelhos) context.getBean("importarEkt2EspelhosCSV");
		t.setThiz(t);

		Arrays.sort(tiposImportacoes);

		try {
			if (Arrays.binarySearch(tiposImportacoes, "GERAIS") >= 0) {
				t.setTipoImportacao(TipoImportacao.GERAIS);
				t.importarGrades(); // EST_D005 
				t.importarDeptos(); // EST_D003
				t.importarSubDeptos(); // EST_D004
				t.importarVendedores(); // EST_D008
				t.importarPlanosCrediario(); // VEN_D053
				t.importarPlanosPagto(); // VEN_D061
			}
			if (Arrays.binarySearch(tiposImportacoes, "FOR") >= 0) {
				t.setTipoImportacao(TipoImportacao.FORNECEDORES);
				t.importarFornecedores();
			}
			if (Arrays.binarySearch(tiposImportacoes, "PROD") >= 0) {
				t.setTipoImportacao(TipoImportacao.PRODUTOS);
				t.importarProdutos();
			}
			if (Arrays.binarySearch(tiposImportacoes, "PED") >= 0) {
				t.setTipoImportacao(TipoImportacao.PEDIDOS);
				t.importarPedidos();
			}
			if (Arrays.binarySearch(tiposImportacoes, "VEN") >= 0) {
				t.setTipoImportacao(TipoImportacao.VENDAS);
				t.importarVendedores();
				t.importarVendas();
				t.importarVendasItens();
			}
			if (Arrays.binarySearch(tiposImportacoes, "ENC") >= 0) {
				t.setTipoImportacao(TipoImportacao.ENCOMENDAS);
				t.importarEncomendas();
				t.importarEncomendaItem();
			}
		} catch (ViewException e1) {
			e1.printStackTrace();
			System.out.println("Erro na importação");
			w.println("Erro na importação");
			System.exit(-1);
		}

		long fim = System.currentTimeMillis();
		double tempo = (fim - ini) / 1000;

		String tempoExec = "tempo de exec.: " + tempo + " segundos";

		escreverResultados();

		logger.info(tempoExec);
		logger.info("Log: " + logFile);

		w.println(tempoExec);
		w.close();

		String sufixo = "(Importando CSVs EKT) " + sdfLog.format(new Date()) + " [" + args[0] + "]";
		try {
			Mailer.sendMail("casabonsucesso@gmail.com", sufixo, StringUtils
					.inputStreamToString(new FileInputStream(new File(logFile))));
		} catch (Exception e) {
			w.println("Erro ao enviar o e-mail para 'carlospauluk@gmail.com' (" + sufixo + ")");
			System.exit(-1);
		}

		System.exit(0);
	}

	public static void erroDeParametros() {
		System.out.println("Parâmetros inválidos.");
		System.out.println("Informe o tipo de importação: [GERAIS,FOR,PROD,PED,VEN,ENC]");
		System.out.println("Informe o ambiente (D ou P).");
		System.out.println("Informe o mês import. Formato: YYYYMM");
	}

}
