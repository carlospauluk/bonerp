package com.bonsucesso.erp.crediario.business;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.NumberFormat;
import java.util.Calendar;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.crm.data.ClienteDataMapper;
import com.bonsucesso.erp.crm.data.ClienteFinder;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.servipa.ws.model.RecebimentoDia;
import com.bonsucesso.servipa.ws.model.VendaParcela;
import com.ocabit.base.view.exception.ViewException;


@Component("atualizaPrev2000")
public class Prev2000Handler {

	protected static Logger logger = Logger.getLogger(Prev2000Handler.class);

	private final String consultaParcela = "SELECT "
			+
			"C.COD_CLIENTE, C.NOME, C.CPF, "
			+
			"V.COD_VENDA, V.PV, V.ANO, V.CARTAO, V.FATURA, V.DT_COMPRA, V.TOTAL, "
			+
			"P.NUM_PARCELA, P.DT_VENCTO, P.VALOR, P.DT_PAGTO, P.JUROS, P.DESCONTO, P.VALOR_PAGO, P.BAIXA_DATA, P.BAIXA_HORA, P.BAIXA_MOTIVO "
			+
			"FROM CRD_CLIENTES C, CRD_VENDAS V, CRD_VENDAS_PARCELAS P " +
			"WHERE C.COD_CLIENTE = V.COD_CLIENTE AND V.COD_VENDA = P.COD_VENDA AND " +
			"DT_VENCTO = ? AND P.NUM_PARCELA = ? AND C.CPF = ? AND P.VALOR = ?";

	private final String baixaParcela = "UPDATE CRD_VENDAS_PARCELAS SET BAIXA_DATA = ?, BAIXA_HORA = ?, BAIXA_COD_USUARIO = 99, BAIXA_MOTIVO = ? WHERE COD_VENDA = ? AND NUM_PARCELA = ?";

	private DataSource dataSourceCrediarioFB;

	@Autowired
	private ClienteFinder clienteFinder;

	@Autowired
	private ClienteDataMapper clienteDataMapper;

	@Autowired
	private BeanFactory beanFactory;

	private Connection conn;

	private PreparedStatement pst;

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public ClienteFinder getClienteFinder() {
		return clienteFinder;
	}

	public void setClienteFinder(ClienteFinder clienteFinder) {
		this.clienteFinder = clienteFinder;
	}

	public ClienteDataMapper getClienteDataMapper() {
		return clienteDataMapper;
	}

	public void setClienteDataMapper(ClienteDataMapper clienteDataMapper) {
		this.clienteDataMapper = clienteDataMapper;
	}

	public Connection getConn() {
		try {
			if ((conn == null) || conn.isClosed()) {
				conn = getDataSourceCrediarioFB().getConnection();
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public PreparedStatement getPst() {
		try {
			if ((pst == null) || (pst.getConnection() == null) || pst.getConnection().isClosed()) {
				setPst(getConn().prepareStatement(consultaParcela));
			}
		} catch (SQLException e) {
			throw new IllegalStateException(e);
		}
		return pst;
	}

	public void setPst(PreparedStatement pst) {
		this.pst = pst;
	}

	public DataSource getDataSourceCrediarioFB() {
		if (dataSourceCrediarioFB == null) {
			dataSourceCrediarioFB = (DataSource) getBeanFactory().getBean("dataSourceCrediarioFB");
		}
		return dataSourceCrediarioFB;
	}

	public void setDataSourceCrediarioFB(DataSource dataSourceFB) {
		dataSourceCrediarioFB = dataSourceFB;
	}

	public void findVendaParcela(RecebimentoDia recebimentoDia) throws ViewException {
		try {
			getPst().setDate(1, new Date(recebimentoDia.getDtVencto().getTime()));
			getPst().setInt(2, recebimentoDia.getNumPrestacao());
			getPst().setString(3, recebimentoDia.getCpf().replaceAll("\\D+", ""));
			getPst().setDouble(4, recebimentoDia.getVlrPrestacao());

			ResultSet rs = getPst().executeQuery();
			if (rs.next()) {
				/*
				 * private Integer codVenda;
				private Integer codCliente;
				private String nome;
				private String cpf;
				private Integer pv;
				private Integer ano;
				private Boolean cartao;
				private Integer fatura;
				private Date dtCompra;
				private Double vlrTotalCompra;
				
				// campos da tabela CRD_VENDAS_PARCELAS
				private Integer numParcela;
				private Date dtVencto;
				private Double vlrParcela;
				private Date dtPagto;
				private Double juros;
				private Double desconto;
				private Double vlrPago;
				private Date dtBaixa;
				private String motivoBaixa;
				 */
				VendaParcela vendaParcela = new VendaParcela();
				vendaParcela.setCodVenda(rs.getInt("COD_VENDA"));
				vendaParcela.setNome(rs.getString("NOME"));
				vendaParcela.setCodCliente(rs.getInt("COD_CLIENTE"));
				vendaParcela.setCpf(rs.getString("CPF"));
				vendaParcela.setPv(rs.getInt("PV"));
				vendaParcela.setAno(Integer.parseInt(rs.getString("ANO")));
				vendaParcela.setCartao(rs.getBoolean("CARTAO"));
				vendaParcela.setFatura(rs.getInt("FATURA"));
				vendaParcela.setDtCompra(rs.getDate("DT_COMPRA"));
				vendaParcela.setVlrTotalCompra(rs.getDouble("TOTAL"));

				vendaParcela.setNumParcela(rs.getInt("NUM_PARCELA"));
				vendaParcela.setDtVencto(rs.getDate("DT_VENCTO"));
				vendaParcela.setVlrParcela(rs.getDouble("VALOR"));
				vendaParcela.setDtPagto(rs.getDate("DT_PAGTO"));
				vendaParcela.setJuros(rs.getDouble("JUROS"));
				vendaParcela.setDesconto(rs.getDouble("DESCONTO"));
				vendaParcela.setVlrPago(rs.getDouble("VALOR_PAGO"));

				java.sql.Time baixaHora = rs.getTime("BAIXA_HORA");
				Date baixaData = rs.getDate("BAIXA_DATA");

				if ((baixaHora != null) && (baixaData != null)) {
					Calendar calBaixaHora = Calendar.getInstance();
					calBaixaHora.setTimeInMillis(baixaHora.getTime());

					Calendar calBaixaData = Calendar.getInstance();
					calBaixaData.setTime(baixaData);
					calBaixaData.set(Calendar.HOUR, calBaixaHora.get(Calendar.HOUR));
					calBaixaData.set(Calendar.MINUTE, calBaixaHora.get(Calendar.MINUTE));
					calBaixaData.set(Calendar.SECOND, calBaixaHora.get(Calendar.SECOND));
					calBaixaData.set(Calendar.MILLISECOND, calBaixaHora.get(Calendar.MILLISECOND));

					vendaParcela.setDtBaixa(calBaixaData.getTime());
				}

				vendaParcela.setMotivoBaixa(rs.getString("BAIXA_MOTIVO"));

				recebimentoDia.setVendaParcela(vendaParcela);

			}

		} catch (Exception e) {
			throw new ViewException("Erro ao buscar detalhes da parcela.");
		}

		//		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSourceFB());
		//		jdbcTemplate.execute(arg0)
	}

	public boolean baixarParcela(RecebimentoDia recebimentoDia) throws ViewException {
		try {
			PreparedStatement pst = getConn().prepareStatement(baixaParcela);
			java.util.Date agora = new java.util.Date();
			pst.setDate(1, new Date(agora.getTime()));
			pst.setTime(2, new Time(agora.getTime()));

			final NumberFormat nf = NumberFormat.getInstance();
			// sempre terá 2 casas decimais
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);

			String motivo = "PAGTO REALIZADO EXTERNAMENTE (SERVIPA).\r\n" +
					"CONTRATO/DOCUMENTO: " + recebimentoDia.getContrato() + "/" + recebimentoDia.getDocumento()
					+ ".\r\n" +
					"ATRASO: " + recebimentoDia.getAtraso() + ".\r\n" +
					"JUROS: " + nf.format(recebimentoDia.getJuros()) + ".\r\n" +
					"VLR PRESTAÇÃO: " + nf.format(recebimentoDia.getVlrPrestacao()) + ".\r\n" +
					"TOTAL PAGO: " + nf.format(recebimentoDia.getTotal()) + ".";

			pst.setString(3, motivo);

			pst.setInt(4, recebimentoDia.getVendaParcela().getCodVenda());
			pst.setInt(5, recebimentoDia.getVendaParcela().getNumParcela());

			//pst.getConnection().commit();

			return pst.executeUpdate() > 0;

		} catch (SQLException e) {
			throw new ViewException("Erro ao baixar parcela.");
		}
	}

	public void done() throws ViewException {
		try {
			getPst().close();
			getConn().close();
		} catch (SQLException e) {
			throw new ViewException("Erro ao fechar as conexões.");
		}
	}

	public void importarClientes() throws Exception {
		try {
			String selectClientes = "SELECT CPF, RG, NOME, DT_NASCIMENTO, FONE1, FONE2 FROM CRD_CLIENTES";
			PreparedStatement pst = getConn().prepareStatement(selectClientes);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String cpf = rs.getString("CPF");
				if (getClienteFinder().findClienteByDoc(cpf) == null) {
					Cliente cliente = new Cliente();
					cliente.getPessoa().setTipoPessoa(TipoPessoa.PESSOA_FISICA);
					cliente.getPessoa().setDocumento(cpf);
					cliente.getPessoa().setNome(rs.getString("NOME"));
					cliente.setRg(rs.getString("RG"));
					cliente.setDtNascimento(rs.getDate("DT_NASCIMENTO"));
					cliente.setFone1(rs.getString("FONE1"));
					cliente.setFone2(rs.getString("FONE2"));
					try {
						cliente = getClienteDataMapper().save(cliente);
					} catch (Exception e) {
						logger.error(e);
						logger.error(">>>>>>>>>>>>>>>>>>>>>> ERRO AO SALVAR");
					}
					logger.info("Cliente salvo com sucesso. >>> " + cliente.getPessoa().getNomeFantasia());
				} else {
					logger.info("Cliente já encontrado. >>> " + cpf);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		Prev2000Handler t = (Prev2000Handler) context.getBean("atualizaPrev2000");

		t.importarClientes();

	}
}
