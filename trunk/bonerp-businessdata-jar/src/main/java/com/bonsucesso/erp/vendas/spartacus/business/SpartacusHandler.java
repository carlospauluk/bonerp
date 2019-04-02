package com.bonsucesso.erp.vendas.spartacus.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.data.PessoaFinder;
import com.bonsucesso.erp.base.model.IPessoa;
import com.bonsucesso.erp.base.model.PessoaCadastro;
import com.bonsucesso.erp.base.model.PessoaJuridica;
import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.crm.data.ClienteFinder;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.fiscal.data.MensagemRetornoRFFinder;
import com.bonsucesso.erp.fiscal.data.NotaFiscalDataMapper;
import com.bonsucesso.erp.fiscal.data.NotaFiscalFinder;
import com.bonsucesso.erp.fiscal.model.FinalidadeNF;
import com.bonsucesso.erp.fiscal.model.MensagemRetornoRF;
import com.bonsucesso.erp.fiscal.model.ModalidadeFrete;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.NotaFiscalItem;
import com.bonsucesso.erp.fiscal.model.TipoNotaFiscal;
import com.bonsucesso.erp.vendas.data.VendaDataMapper;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.StatusVenda;
import com.bonsucesso.erp.vendas.model.Venda;
import com.bonsucesso.erp.vendas.model.VendaItem;
import com.ocabit.base.data.MunicipioFinder;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.strings.StringUtils;


/**
 * Componente que faz a interface com o banco de dados Postgres para emissão de NFe/NFCe via Spartacus.
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("spartacusHandler")
public class SpartacusHandler implements Serializable, ISpartacusHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8616654658230029278L;

	protected static Logger logger = Logger.getLogger(SpartacusHandler.class);

	@Autowired
	private DataSource dataSourcePostgresSpartacus;

	@Autowired
	private MunicipioFinder municipioFinder;

	@Autowired
	private ClienteFinder clienteFinder;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private VendaDataMapper vendaDataMapper;

	@Autowired
	private NotaFiscalFinder notaFiscalFinder;

	@Autowired
	private NotaFiscalDataMapper notaFiscalDataMapper;

	@Autowired
	private PessoaFinder pessoaFinder;

	@Autowired
	private MensagemRetornoRFFinder mensagemRetornoRFFinder;

	@Autowired
	private ConfigFinder configFinder;

	private JdbcTemplate jdbcTemplate;

	static final String FORMATO_DATA = "yyyy-MM-dd";
	static final String FORMATO_HORA = "HH:mm:ss";

	static final String TABELA_CABECALHO = "nfevv";
	static final String TABELA_ITENS = "infvv";

	@Override
	public ISpartacusHandler getThiz() {
		return (ISpartacusHandler) getVendaFinder().getBeanFactory().getBean("spartacusHandler");
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			this.jdbcTemplate = new JdbcTemplate(dataSourcePostgresSpartacus);
		}
		return jdbcTemplate;
	}

	@Override
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void checkPV(Venda pv) throws ViewException {
		if (pv.getNotaFiscal() == null || pv.getNotaFiscal().getId() == null) {
			throw new ViewException("Faltando dados da nota fiscal no pré-venda");
		}

		if (pv.getCliente() == null && pv.getNotaFiscal().getTipoNotaFiscal().equals(TipoNotaFiscal.NFE)) {
			throw new ViewException("Faltando dados do cliente para emissão de NFe (modelo 55).");
		}

		for (VendaItem item : pv.getItens()) {
			if (item.getNcm() == null || "".equals(item.getNcm())) {
				throw new ViewException("NCM faltando para o item: '" + item.getDescricao() + "'");
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "txManagerSpartacus")
	public void apagarMensagens(NotaFiscal nf) throws ViewException {
		try {
			String sqlDeleteMensagens = "DELETE FROM mensretorno WHERE mens_id_nota = ?";
			getJdbcTemplate().update(sqlDeleteMensagens, nf.getSpartacusId());
		} catch (EmptyResultDataAccessException e) {
			logger.info("Não existe ainda.");
		} catch (Exception e) {
			throw new ViewException("Erro ao deletar mensagens.", e);
		}

	}

	/**
	 * Não declarei como transacional pois serão criadas transações nos métodos chamados.
	 */
	@Override
	public void faturarPV(Venda pv) throws ViewException {
		try {
			getThiz().checkPV(pv);
			getThiz().faturar(pv.getNotaFiscal()); // txManagerSpartacus

			pv.setStatus(StatusVenda.VENDA);
			getVendaDataMapper().save(pv); // txManager
		} catch (Exception e) {
			throw new ViewException("Erro ao faturar PV", e);
		}

	}

	/**
	 * Aqui terá duas transações. A própria do método, executando pela txManagerSpartacus e outra que será criada no
	 * NotaFiscalDataMapper.save()
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "txManagerSpartacus")
	public void faturar(NotaFiscal nf) throws ViewException {
		try {
			if (nf.getSpartacusId() != null) {
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> apagarMensagens()");
				getThiz().apagarMensagens(nf);
			}

			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> saveCabecalho()");
			Integer id = getThiz().saveCabecalho(nf);
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> id = '" + id + "'");
			getThiz().saveItens(id, nf);

			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> SALVANDO A NOTA FISCAL");
			nf.setSpartacusId(id);

			// Aqui deverá abrir outra transação pelo txManager
			nf.setDtSpartacusStatus(new Date());
			getNotaFiscalDataMapper().save(nf);
			logger.info(">>>>>>>>>>>>>>"
					+ ">>>>>>>>>>>>>>> OK");
		} catch (Exception e) {
			String err = JSFUtils.handleSaveException(e);
			throw new ViewException("Erro ao faturar PV (" + err + ")", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "txManagerSpartacus")
	public Integer saveCabecalho(NotaFiscal nf) throws ViewException {

		Integer r = null;

		// Tenta achar o Cliente ou Fornecedor desta nota

		IPessoa pessoa = null;

		// Tenta achar a pessoa (cliente ou fornecedor) desta nota. Mas só se for NFE...
		if (nf.getTipoNotaFiscal().equals(TipoNotaFiscal.NFE)) {
			// Tenta achar pelo tipo (cliente ou fornecedor) que foi informado.
			if (nf.getPessoaDestinatario() != null) {
				if (PessoaCadastro.CLIENTE.equals(nf.getPessoaDestinatario())) {
					pessoa = getClienteFinder().findByPessoa(nf.getPessoaDestinatario());
				} else {
					pessoa = getFornecedorFinder().findBy(nf.getPessoaDestinatario());
				}
			}
			// Se não achou ou se não foi informado se era Cliente ou Fornecedor...
			if (nf.getPessoaDestinatario() == null || pessoa == null) {
				pessoa = getClienteFinder().findByPessoa(nf.getPessoaDestinatario());
				if (pessoa == null) {
					pessoa = getFornecedorFinder().findBy(nf.getPessoaDestinatario());
				}
			}
		}

		TipoNotaFiscal tipoNota = nf.getTipoNotaFiscal();

		List<Campo> camposCabecalho = buildCamposCabecalho();

		// Se for um INSERT
		Integer id = null;
		if (nf.getSpartacusId() == null) {
			String sqlNextVal = "SELECT nextval('nfevv_id_seq')";
			id = getJdbcTemplate().queryForObject(sqlNextVal, Integer.class);
			findCampoByNome("id", camposCabecalho).setValor(id);
		} else {
			id = nf.getSpartacusId();
		}

		findCampoByNome("b08_nnf", camposCabecalho).setValor(nf.getNumero());

		findCampoByNome("b05_indpag", camposCabecalho).setValor(nf.getIndicadorFormaPagto().getCodigo());

		findCampoByNome("b04_natop", camposCabecalho).setValor(nf.getNaturezaOperacao());

		findCampoByNome("b25_finnfe", camposCabecalho).setValor(nf.getFinalidadeNF().getCodigo());

		findCampoByNome("b06_mod", camposCabecalho).setValor(nf.getTipoNotaFiscal().getModelo());

		findCampoByNome("b07_serie", camposCabecalho).setValor(nf.getSerie());

		findCampoByNome("b09_demi", camposCabecalho)
				.setValor(new SimpleDateFormat(SpartacusHandler.FORMATO_DATA)
						.format(nf.getDtEmissao()));
		findCampoByNome("b10_dsaient", camposCabecalho)
				.setValor(new SimpleDateFormat(SpartacusHandler.FORMATO_DATA)
						.format(nf.getDtEmissao()));
		findCampoByNome("b10a_hsaient", camposCabecalho)
				.setValor(new SimpleDateFormat(SpartacusHandler.FORMATO_HORA)
						.format(nf.getDtEmissao()));

		findCampoByNome("b11_tpnf", camposCabecalho).setValor(nf.getEntrada() ? "0" : "1");

		// Pode ser null no caso da NFCe (modelo 65)
		if (nf.getPessoaDestinatario() != null) {

			if (nf.getTipoNotaFiscal().equals(TipoNotaFiscal.NFE) && pessoa != null && pessoa.getEndereco() != null) {
				Integer iddest = "PR".equals(pessoa.getEndereco().getEstado()) ? 1 : 2;
				findCampoByNome("iddest", camposCabecalho).setValor(iddest);
			}

			if (nf.getPessoaDestinatario().getTipoPessoa().equals(TipoPessoa.PESSOA_JURIDICA)) {
				findCampoByNome("e02_cnpj", camposCabecalho).setValor(nf.getPessoaDestinatario().getDocumento());
			} else {
				findCampoByNome("e03_cpf", camposCabecalho).setValor(nf.getPessoaDestinatario().getDocumento());
			}

			// Dados do cliente
			findCampoByNome("e04_xnome", camposCabecalho).setValor(nf.getPessoaDestinatario().getNome());
		}

		if (tipoNota.equals(TipoNotaFiscal.NFE)) {
			// Se lá em cima não achou...
			if (pessoa == null) {
				throw new ViewException("É necessário informar a pessoa para emitir uma NFE.");
			}
			// Informações complementares
			findCampoByNome("nfev_comp_mens", camposCabecalho).setValor(nf.getInformacoesComplementares());

			findCampoByNome("e06_xlgr", camposCabecalho).setValor(pessoa.getEndereco().getLogradouro());
			findCampoByNome("e07_nro", camposCabecalho).setValor(pessoa.getEndereco().getNumero());
			findCampoByNome("e08_xcpl", camposCabecalho).setValor(pessoa.getEndereco().getComplemento());
			findCampoByNome("e09_xbairro", camposCabecalho).setValor(pessoa.getEndereco().getBairro());

			try {
				String municipioNome = pessoa.getEndereco().getCidade().trim();
				String uf = pessoa.getEndereco().getEstado();
				Integer municipioCodigo = getMunicipioFinder().findBy(municipioNome, uf).getMunicipioCodigo();
				findCampoByNome("e10_cmun", camposCabecalho).setValor(municipioCodigo);
				findCampoByNome("e11_xmun", camposCabecalho).setValor(municipioNome);
			} catch (Exception e) {
				throw new ViewException("Erro ao buscar código do município pelo nome", e);
			}
			findCampoByNome("e12_uf", camposCabecalho).setValor(pessoa.getEndereco().getEstado());
			findCampoByNome("e13_cep", camposCabecalho).setValor(pessoa.getEndereco().getCep());

			findCampoByNome("e16_fone", camposCabecalho).setValor(pessoa.getFone1());

			if (pessoa.getPessoa().getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA)) {
				findCampoByNome("indiedest", camposCabecalho).setValor(9);
			} else {
				if (pessoa instanceof PessoaJuridica) {
					PessoaJuridica pj = (PessoaJuridica) pessoa;
					if ("ISENTO".equals(pj.getInscricaoEstadual())) {
						findCampoByNome("indiedest", camposCabecalho).setValor(2);
					} else {
						findCampoByNome("indiedest", camposCabecalho).setValor(1);
						findCampoByNome("e17_ie", camposCabecalho).setValor(pj.getInscricaoEstadual());
					}
				}
			}

			findCampoByNome("e19_email", camposCabecalho).setValor(pessoa.getEmail());

		}

		findCampoByNome("w07_vprod_tota", camposCabecalho).setValor(nf.getSubTotal().doubleValue());
		findCampoByNome("w10_vdesc_tota", camposCabecalho).setValor(nf.getTotalDescontos().doubleValue());
		findCampoByNome("w16_vnf_tota", camposCabecalho).setValor(nf.getValorTotal().doubleValue());

		BigDecimal totalBcICMS = BigDecimal.ZERO;
		BigDecimal totalICMS = BigDecimal.ZERO;

		for (NotaFiscalItem item : nf.getItens()) {
			totalBcICMS = totalBcICMS.add(item.getIcmsValorBC() != null ? item.getIcmsValorBC() : BigDecimal.ZERO);
			totalICMS = totalICMS.add(item.getIcmsValor() != null ? item.getIcmsValor() : BigDecimal.ZERO);
		}
		findCampoByNome("w03_vbc_tota", camposCabecalho).setValor(totalBcICMS);
		findCampoByNome("w04_vicms_tota", camposCabecalho).setValor(totalICMS);

		findCampoByNome("x02_modfrete", camposCabecalho).setValor(nf.getTranspModalidadeFrete().getCodigo());

		if (!nf.getTranspModalidadeFrete().equals(ModalidadeFrete.SEM_FRETE)) {

			if (nf.getTranspValorTotalFrete() != null) {
				findCampoByNome("w08_vfret_tota", camposCabecalho).setValor(nf.getTranspValorTotalFrete());
			}

			
			
			findCampoByNome("x04_cnpj_transp", camposCabecalho).setValor(nf.getTranspFornecedor().getPessoa().getDocumento());
			findCampoByNome("x06_xnome_transp", camposCabecalho).setValor(nf.getTranspFornecedor().getPessoa().getNome());
			findCampoByNome("x07_ie_transp", camposCabecalho).setValor(nf.getTranspFornecedor().getInscricaoEstadual());
			findCampoByNome("x08_xender_transp", camposCabecalho).setValor(nf.getTranspFornecedor().getEndereco().getLogradouroMontado());
			findCampoByNome("x09_xmun_transp", camposCabecalho).setValor(nf.getTranspFornecedor().getEndereco().getCidade());
			findCampoByNome("x10_uf_transp", camposCabecalho).setValor(nf.getTranspFornecedor().getEndereco().getEstado());

			try {

				String sqlVolumes;

				if (nf.getSpartacusId() == null) {

					sqlVolumes = "INSERT INTO "
							+ "volumesv(id,seq,x27_qvol,x28_esp,x30_nvol,x31_pesol,x32_pesob) "
							+ "VALUES(?,"
							+ "?,"
							+ "?,"
							+ "?,"
							+ "?,"
							+ "?,"
							+ "?)";
					getJdbcTemplate()
							.update(sqlVolumes, id, 1, nf.getTranspQtdeVolumes(), nf.getTranspEspecieVolumes(), nf
									.getTranspNumeracaoVolumes(), nf.getTranspPesoLiquido(), nf.getTranspPesoBruto());
				} else {
					sqlVolumes = "UPDATE volumesv SET "
							+ "x27_qvol = ?, "
							+ "x28_esp = ?,"
							+ "x30_nvol = ?,"
							+ "x31_pesol = ?,"
							+ "x32_pesob = ?"
							+ " WHERE id = ? AND seq = ?";
					getJdbcTemplate().update(sqlVolumes, nf.getTranspQtdeVolumes(), nf.getTranspEspecieVolumes(), nf
							.getTranspNumeracaoVolumes(), nf.getTranspPesoLiquido(), nf.getTranspPesoBruto(), id, 1);
				}

			} catch (Exception e) {
				throw new ViewException("Erro ao atualizar os itens da nota", e);
			}

		}

		// Se não tem id do Spartacus ainda, é um faturamento novo
		if (nf.getSpartacusId() == null) {
			try {
				String sqlInsert = buildInsert(SpartacusHandler.TABELA_CABECALHO, camposCabecalho);
				getJdbcTemplate().update(sqlInsert);
				r = id;
			} catch (Exception e) {
				throw new ViewException("Erro ao salvar cabeçalho da nota", e);
			}
			// Se já tem id do Spartacus, é um refaturamento.
		} else {
			try {
				Map<String, Object> chavePrimaria = new HashMap<String, Object>();
				chavePrimaria.put("id", nf.getSpartacusId());
				String sqlUpdate = buildUpdate(SpartacusHandler.TABELA_CABECALHO, camposCabecalho, chavePrimaria);

				getJdbcTemplate().update(sqlUpdate);

				r = nf.getSpartacusId();
			} catch (Exception e) {
				throw new ViewException("Erro ao atualizar cabeçalho da nota", e);
			}
		}

		nf.setSpartacusId(r);
		if (nf.getA03idNfReferenciada() != null && !"".equals(nf.getA03idNfReferenciada().trim())) {
			getThiz().saveNfReferenciada(nf);
		} else {
			getJdbcTemplate().update("DELETE FROM nfrefv WHERE idnf = " + nf.getSpartacusId());
		}

		return r;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "txManagerSpartacus")
	public void saveNfReferenciada(NotaFiscal nf) throws ViewException {
		try {
			getJdbcTemplate().update("DELETE FROM nfrefv WHERE idnf = " + nf.getSpartacusId());
			// ATENÇÃO QUE AQUI É ADICIONADO O PREFIXO "NFe"
			getJdbcTemplate().update("INSERT INTO nfrefv(id, idnf,b13_refnfe) VALUES(?,?,?)", nf.getSpartacusId(), nf
					.getSpartacusId(), "NFe" + nf
							.getA03idNfReferenciada());
		} catch (Exception e) {
			throw new ViewException("Erro ao deletar e inserir registro na 'nfrefv'");
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, transactionManager = "txManagerSpartacus")
	public void saveItens(Integer idNotaGerada, NotaFiscal nf) throws ViewException {

		int i = 1;

		// Adiciona todos em uma lista para poder fazer o cálculo do desconto no somaDescontos
		List<List<Campo>> l = new ArrayList<List<Campo>>();

		for (NotaFiscalItem item : nf.getItens()) {

			List<Campo> camposItem = buildCamposItem();

			// Se tiver frete, adiciona o valor total do frete ao primeiro item.
			if (nf.getTranspValorTotalFrete() != null) {
				if (i == 1) {
					findCampoByNome("i15_vfrete", camposItem).setValor(nf.getTranspValorTotalFrete());
				}
			}

			findCampoByNome("id", camposItem).setValor(idNotaGerada);
			findCampoByNome("nitem", camposItem).setValor(i++);

			findCampoByNome("i02_cprod", camposItem).setValor(item.getCodigo());

			String descricao = item.getDescricao();
			//			descricao = Normalizer
			//					.normalize(descricao, Normalizer.Form.NFD)
			//					.replaceAll("[^\\p{ASCII}]", "");
			descricao = StringUtils.toIso88591(descricao);

			findCampoByNome("i04_xprod", camposItem).setValor(descricao);
			findCampoByNome("i05_ncm", camposItem).setValor(item.getNcm());
			findCampoByNome("i09_ucom", camposItem).setValor(item.getUnidade());
			findCampoByNome("i13_utrib", camposItem).setValor(item.getUnidade());

			findCampoByNome("i08_cfop", camposItem).setValor(item.getCfop());

			findCampoByNome("i10_qcom", camposItem).setValor(item.getQtde());
			findCampoByNome("i10a_vuncom", camposItem).setValor(item.getValorUnit());

			findCampoByNome("i11_vprod", camposItem).setValor(item.getSubTotal());

			findCampoByNome("i14_qtrib", camposItem).setValor(item.getQtde());
			findCampoByNome("i14a_vuntrib", camposItem).setValor(item.getValorUnit());

			findCampoByNome("i17_vdesc", camposItem).setValor(item.getValorDesconto());

			if (nf.getFinalidadeNF().equals(FinalidadeNF.DEVOLUCAO)) {

				findCampoByNome("n12a_csosn", camposItem).setValor(900);
				findCampoByNome("n12_cst", camposItem).setValor(900);

				findCampoByNome("n13_modbc_simples", camposItem).setValor(3);

				findCampoByNome("n15_vbc_simples", camposItem).setValor(item.getIcmsValorBC());
				findCampoByNome("n16_picms_simples", camposItem).setValor(item.getIcmsAliquota());
				findCampoByNome("n17_vicms_simples", camposItem).setValor(item.getIcmsValor());

				findCampoByNome("n15_vbc", camposItem).setValor(item.getIcmsValorBC());
				findCampoByNome("n16_picms", camposItem).setValor(item.getIcmsAliquota());
				findCampoByNome("n17_vicms", camposItem).setValor(item.getIcmsValor());
			} else {
				findCampoByNome("n12a_csosn", camposItem).setValor(103);
				findCampoByNome("n13_modbc_simples", camposItem).setValor(null);
				findCampoByNome("n15_vbc_simples", camposItem).setValor(null);
				findCampoByNome("n16_picms_simples", camposItem).setValor(null);
				findCampoByNome("n17_vicms_simples", camposItem).setValor(null);
			}

			l.add(camposItem);
		}

		for (List<Campo> camposItem : l) {

			boolean itemSalvo;
			if (nf.getSpartacusId() == null) {
				itemSalvo = false;
			} else {
				String sqlSelectItem = "SELECT * FROM infvv WHERE id = ? AND nitem = ?";
				Integer nItem = Integer.parseInt(findCampoByNome("nitem", camposItem).getValor());
				itemSalvo = getJdbcTemplate().queryForList(sqlSelectItem, nf.getSpartacusId(), nItem).size() > 0;
			}

			if (!itemSalvo) {

				String sqlInsert = buildInsert(SpartacusHandler.TABELA_ITENS, camposItem);
				try {
					getJdbcTemplate().update(sqlInsert);
				} catch (Exception e) {
					throw new ViewException("Erro ao salvar o item '"
							+ findCampoByNome("i04_xprod", camposItem).getValor()
							+ "'");
				}

			} else {
				try {
					Map<String, Object> chavePrimaria = new HashMap<String, Object>();
					chavePrimaria.put("id", nf.getSpartacusId());
					chavePrimaria.put("nitem", findCampoByNome("nitem", camposItem).getValor());
					String sqlUpdate = buildUpdate(SpartacusHandler.TABELA_ITENS, camposItem, chavePrimaria);
					getJdbcTemplate().update(sqlUpdate);
				} catch (Exception e) {
					throw new ViewException("Erro ao atualizar os itens da nota", e);
				}

			}

		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "txManagerSpartacus")
	public void reimprimir(NotaFiscal nf) throws ViewException {
		try {
			try {
				Integer id = nf.getSpartacusId();
				String sqlInsert = "INSERT INTO mensentrada(mens_acao, mens_id_nota, mens_mens) VALUES(?,?,?)";
				logger.info(sqlInsert);
				logger.info("mens_acao: " + "P");
				logger.info("id: " + id);
				logger.info("mens_mens: " + "REIMPRIMIR");

				getJdbcTemplate().update(sqlInsert, "P", id, "REIMPRIMIR");
			} catch (EmptyResultDataAccessException e) {
				logger.debug("Nenhum resultado encontrado.");
			}

		} catch (Exception e) {
			throw new ViewException("Erro ao reimprimir a nota", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "txManagerSpartacus")
	public void cancelar(NotaFiscal nf) throws ViewException {

		try {
			Integer id = nf.getSpartacusId();
			if (id == null) {
				throw new ViewException("Id Spartacus não encontrado.");
			}
			String sqlInsert = "INSERT INTO mensentrada(mens_acao, mens_id_nota, mens_mens) VALUES(?,?,?)";
			getJdbcTemplate().update(sqlInsert, "C", id, nf.getMotivoCancelamento());
		} catch (EmptyResultDataAccessException e) {
			logger.debug("Nenhum resultado encontrado.");
		} catch (Exception e) {
			throw new ViewException("Erro ao cancelar a nota", e);
		}

	}

	@Override
	public Campo findCampoByNome(String nome, List<Campo> campos) {
		for (Campo campo : campos) {
			if (nome.equals(campo.getNome())) {
				return campo;
			}
		}
		return null;
	}

	@Override
	public String buildInsert(String nomeTabela, List<Campo> campos) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(nomeTabela).append("(");
		for (Campo campo : campos) {
			sb.append(campo.getNome()).append(",").append("\n");
		}
		sb.deleteCharAt(sb.length() - 2);

		sb.append(") VALUES (");

		for (Campo campo : campos) {

			if (campo.getValor() == null || "".equals(campo.getValor().trim())) {
				if (campo.getValorPadrao() != null && !"".equals(campo.getValorPadrao())) {
					sb.append("'").append(campo.getValorPadrao()).append("'");
				} else {
					sb.append("null");
				}
			} else {
				if ("NUMERIC".equals(campo.getTipo())) {
					try {
						Double v = Double.valueOf(campo.getValor().replaceAll("[^\\d.,]", ""));
						sb.append(v.toString());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("INTEGER".equals(campo.getTipo())) {
					try {
						Integer v = Integer.valueOf(campo.getValor().replaceAll("[^\\d.,]", ""));
						sb.append(v.toString());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					sb.append("'").append(campo.getValor()).append("'");
				}
			}
			sb.append(", ").append("\n");
		}

		sb.delete(sb.length() - 3, sb.length());
		sb.append(")");
		logger.info("\n");
		logger.info(sb.toString());
		return sb.toString();
	}

	@Override
	public String buildUpdate(String nomeTabela, List<Campo> campos, Map<String, Object> chavesPrimarias)
			throws ViewException {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ").append(nomeTabela).append(" SET ");
		for (Campo campo : campos) {

			// pula os campos de chave primária
			if (chavesPrimarias.keySet().contains(campo.getNome())) {
				continue;
			}

			sb.append(campo.getNome()).append("=");

			if (campo.getValor() == null || "".equals(campo.getValor().trim())) {
				if (campo.getValorPadrao() != null && !"".equals(campo.getValorPadrao())) {
					sb.append("'").append(campo.getValorPadrao()).append("'");
				} else {
					sb.append("null");
				}
			} else {
				if ("NUMERIC".equals(campo.getTipo())) {
					try {
						Double v = Double.valueOf(campo.getValor().replaceAll("[^\\d.,]", ""));
						sb.append(v.toString());
					} catch (NumberFormatException e) {
						throw new ViewException("Erro ao formatar como double.");
					}
				} else if ("INTEGER".equals(campo.getTipo())) {
					try {
						Integer v = Integer.valueOf(campo.getValor().replaceAll("[^\\d.,]", ""));
						sb.append(v.toString());
					} catch (NumberFormatException e) {
						throw new ViewException("Erro ao formatar como integer.");
					}
				} else {
					sb.append("'").append(campo.getValor()).append("'");
				}
			}
			sb.append(", ").append("\n");
		}
		sb.delete(sb.length() - 3, sb.length());

		sb.append(" WHERE ");

		for (Map.Entry<String, Object> e : chavesPrimarias.entrySet()) {
			sb.append(e.getKey()).append("=").append(e.getValue()).append(" AND ");

		}

		sb.delete(sb.length() - 5, sb.length());

		logger.info("\n");
		logger.info(sb.toString());
		return sb.toString();
	}

	/**
	 * Aqui o transactionManager é do BonERP, pois só altera dados nele.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "txManager")
	public void atualizarStatus(Date dtVenda) throws ViewException {

		try {
			List<Venda> vendas = getVendaFinder().findBy(dtVenda);
			for (Venda v : vendas) {
				getThiz().atualizarStatus(v.getNotaFiscal());
				// getVendaFinder().getEntityManager().detach(v); // detach para não tentar salvar depois por algum motivo
			}
		} catch (Exception e) {
			throw new ViewException("Erro ao atualizarStatusNotaFiscalVenda");
		}

	}

	/**
	 * Aqui o transactionManager é do BonERP, pois só altera dados nele.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "txManager", readOnly = false)
	public NotaFiscal atualizarStatus(NotaFiscal nf) throws ViewException {
		try {
			if (nf == null) {
				return null;
			}
			nf = getNotaFiscalFinder().refresh(nf);
			
			nf.getItens().size();
			nf.getPessoaEmitente().hashCode();

			String serie = nf.getSerie().toString();
			Integer nnf = nf.getNumero();
			String mod = nf.getTipoNotaFiscal().getModelo().toString();

			// Busca o Id da Nota
			Integer idNota = null;

			try {
				final String sqlConsultaId = "SELECT id FROM " + SpartacusHandler.TABELA_CABECALHO
						+ " WHERE b07_serie = ? AND b08_nnf = ? AND b06_mod = ? ORDER BY id DESC LIMIT 1";
				idNota = getJdbcTemplate()
						.queryForObject(sqlConsultaId, Integer.class, serie, nnf, mod);

				nf.setSpartacusId(idNota);

			} catch (EmptyResultDataAccessException e) {
				logger.info("Nota não encontrada na base do Spartacus (serie: '" + serie + "', Nnf: '" + nnf
						+ "', mod: '" + mod + "'");
				if (nf.getSpartacusId() != null && !nf.getSpartacusId().equals(idNota)) {
					nf = getNotaFiscalDataMapper().save(nf);
					nf.setSpartacusId(null);
				}
				return nf;
			} catch (Exception e) {
				throw new ViewException("Erro ao buscar id da nota na base Spartacus.", e);
			}

			// Primeiro verifica se existe registro na mensretorno.
			try {
				String sqlConsultaMensRetorno = "SELECT mens_codi, mens_desc FROM mensretorno WHERE mens_id_nota = ?";

				Map<String, Object> r = getJdbcTemplate()
						.queryForMap(sqlConsultaMensRetorno, idNota);

				Integer codigo = (Integer) r.get("mens_codi");
				String msg = (String) r.get("mens_desc");

				if (nf.getSpartacusStatus() != codigo || nf.getDtSpartacusStatus() == null) {
					nf.setDtSpartacusStatus(new Date());
				}
				nf.setSpartacusStatus(codigo);
				nf.setSpartacusMensRetorno(msg);

				if (codigo != null && codigo > 0) {
					MensagemRetornoRF msgRF = getMensagemRetornoRFFinder().findBy(codigo);
					String strMsg = msgRF != null ? msgRF.getMensagem() : null;
					nf.setSpartacusMensRetornoReceita(strMsg);
				}

			} catch (EmptyResultDataAccessException e) {
				// Se não encontrou registro na tabela mensretorno...

				logger.debug("Nenhum resultado encontrado.");
				nf.setSpartacusMensRetorno(null);

				try {
					final String sqlConsultaStatus = "SELECT status_nfe, status_canc FROM "
							+ SpartacusHandler.TABELA_CABECALHO
							+ " WHERE id = ?";

					Map<String, Object> statuss = getJdbcTemplate()
							.queryForMap(sqlConsultaStatus, idNota);

					Integer statusCanc = (Integer) statuss.get("status_canc");
					Integer statusNfe = (Integer) statuss.get("status_nfe");

					Integer status = (statusCanc != 0) ? statusCanc : statusNfe;
					if (nf.getSpartacusStatus() != status || nf.getDtSpartacusStatus() == null) {
						nf.setDtSpartacusStatus(new Date());
					}
					nf.setSpartacusStatus(status);

					if (status != null) {
						if (status > 0) {

							MensagemRetornoRF msgRF = getMensagemRetornoRFFinder().findBy(status);
							String strMsg = msgRF != null ? msgRF.getMensagem() : null;
							nf.setSpartacusMensRetornoReceita(strMsg);
						} else {
							nf.setSpartacusMensRetornoReceita("AGUARDANDO FATURAMENTO");
						}
					}

				} catch (EmptyResultDataAccessException e2) {
					// Aqui só cai se não encontrar nada na mensretorno nem resultado pro nfevv.status_nfe
					logger.debug("Nenhum resultado encontrado.", e2);
					nf.setSpartacusMensRetornoReceita(null);
					if (nf.getSpartacusStatus() != null || nf.getDtSpartacusStatus() == null) {
						nf.setDtSpartacusStatus(new Date());
					}
					nf.setSpartacusStatus(null);

				} catch (Exception e2) {
					logger.error("Erro ao consultar mensagens de retorno para a nota.", e2);
				}

			} catch (Exception e) {
				logger.error("Erro ao consultar mensagens de retorno para a nota.", e);
			}

			nf = getNotaFiscalDataMapper().save(nf);

			return nf;

		} catch (Exception e) {
			throw new ViewException("Erro ao atualizarStatusNotaFiscalVenda");
		}

	}

	@Override
	public List<Campo> buildCamposCabecalho() throws ViewException {

		List<Campo> camposCabecalho = new ArrayList<Campo>();

		camposCabecalho.add(new Campo("id", "INTEGER", "", null));
		camposCabecalho.add(new Campo("a02_versao", "NUMERIC", "3.1", null));
		camposCabecalho.add(new Campo("b02_cuf_emi", "INTEGER", "41", null));
		camposCabecalho.add(new Campo("b03_cnf", "INTEGER", "", null));
		camposCabecalho.add(new Campo("b04_natop", "VARCHAR", "VENDA", null));

		camposCabecalho.add(new Campo("iddest", "INTEGER", "1", null));

		camposCabecalho.add(new Campo("b05_indpag", "INTEGER", "", null));
		camposCabecalho.add(new Campo("b06_mod", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("b07_serie", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("b08_nnf", "INTEGER", "", null));
		camposCabecalho.add(new Campo("b09_demi", "DATE", "", null));
		camposCabecalho.add(new Campo("b10_dsaient", "DATE", "", null));
		camposCabecalho.add(new Campo("b10a_hsaient", "TIME", "", null));
		camposCabecalho.add(new Campo("b11_tpnf", "INTEGER", "", null));
		camposCabecalho.add(new Campo("b12_cmunfg", "INTEGER", "4119905", null));
		camposCabecalho.add(new Campo("b21_tplmp", "INTEGER", "1", null));
		camposCabecalho.add(new Campo("b22_tpemis", "INTEGER", "1", null));

		Integer ambiente = "PROD".equals(getConfigFinder().findValorByChave("bonsucesso.fiscal.ambiente")) ? 1 : 2;
		// ambiente = 2;
		camposCabecalho.add(new Campo("b24_tpamb", "INTEGER", ambiente.toString(), null));

		camposCabecalho.add(new Campo("b25_finnfe", "INTEGER", "1", null));
		camposCabecalho.add(new Campo("b26_procemi", "INTEGER", "0", null));
		camposCabecalho.add(new Campo("c02_cnpj", "VARCHAR", "77498442000134", null));
		camposCabecalho.add(new Campo("c03_xnome", "VARCHAR", "COMERCIAL DE TECIDOS PAULUK LTDA", null));
		camposCabecalho.add(new Campo("c04_xfant", "VARCHAR", "CASA BONSUCESSO", null));
		camposCabecalho.add(new Campo("c06_xlgr", "VARCHAR", "AV DOM PEDRO II", null));
		camposCabecalho.add(new Campo("c07_nro", "VARCHAR", "337", null));
		camposCabecalho.add(new Campo("c08_xcpl", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("c09_xbairro", "VARCHAR", "NOVA RÚSSIA", null));
		camposCabecalho.add(new Campo("c10_cmun", "INTEGER", "4119905", null));
		camposCabecalho.add(new Campo("c11_xmun", "VARCHAR", "PONTA GROSSA", null));
		camposCabecalho.add(new Campo("c12_uf", "VARCHAR", "PR", null));
		camposCabecalho.add(new Campo("c13_cep", "INTEGER", "84053000", null));
		camposCabecalho.add(new Campo("c14_cpais", "INTEGER", "1058", null));
		camposCabecalho.add(new Campo("c15_xpais", "VARCHAR", "BRASIL", null));
		camposCabecalho.add(new Campo("c16_fone", "VARCHAR", "4232276657", null));
		camposCabecalho.add(new Campo("c17_ie", "VARCHAR", "2010302423", null));
		camposCabecalho.add(new Campo("c19_im", "VARCHAR", "20917", null));
		camposCabecalho.add(new Campo("c21_crt", "INTEGER", "1", null));
		camposCabecalho.add(new Campo("e02_cnpj", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e03_cpf", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e04_xnome", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e06_xlgr", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e07_nro", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e08_xcpl", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e09_xbairro", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e10_cmun", "INTEGER", "", null));
		camposCabecalho.add(new Campo("e11_xmun", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e12_uf", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e13_cep", "INTEGER", "", null));
		camposCabecalho.add(new Campo("e14_cpais", "INTEGER", "", null));
		camposCabecalho.add(new Campo("e15_xpais", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e16_fone", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e17_ie", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("e19_email", "VARCHAR", "", null));

		camposCabecalho.add(new Campo("w03_vbc_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w04_vicms_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w05_vbcst_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w06_vst_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w07_vprod_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w08_vfret_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w09_vseg_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w10_vdesc_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w11_vii_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w12_vipi_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w13_vpis_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w14_vcofins_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w15_voutro_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w16_vnf_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w18_vserv_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w19_vbc_iss_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w20_viss_tota", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w21_vpis_serv", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w22_vcofins_serv", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w24_vretpis", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w25_vretcofins", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w26_vretcsll", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w27_vbcirrf", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w28_virrf", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w29_vbcretprev", "NUMERIC", "0", null));
		camposCabecalho.add(new Campo("w30_vretprev", "NUMERIC", "0", null));

		camposCabecalho.add(new Campo("x02_modfrete", "INTEGER", "", null));
		camposCabecalho.add(new Campo("x04_cnpj_transp", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("x05_cpf_transp", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("x06_xnome_transp", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("x07_ie_transp", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("x08_xender_transp", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("x09_xmun_transp", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("x10_uf_transp", "VARCHAR", "", null));
		camposCabecalho.add(new Campo("empresa", "INTEGER", "77498442000134", null));
		camposCabecalho.add(new Campo("filial", "INTEGER", "77498442000134", null));
		camposCabecalho.add(new Campo("status_nfe", "INTEGER", "0", null));

		camposCabecalho.add(new Campo("indiedest", "INTEGER", "9", null));
		camposCabecalho.add(new Campo("indfinal", "INTEGER", "1", null));

		camposCabecalho.add(new Campo("prot_nfe", "VARCHAR", "", null));

		camposCabecalho.add(new Campo("nfev_comp_mens", "VARCHAR", "", null));

		return camposCabecalho;
	}

	@Override
	public List<Campo> buildCamposItem() {

		List<Campo> camposItem = new ArrayList<Campo>();

		// -----------------------------------

		camposItem.add(new Campo("id", "INTEGER", "", null));
		camposItem.add(new Campo("nitem", "INTEGER", "", null));
		camposItem.add(new Campo("i02_cprod", "VARCHAR", "", null));
		camposItem.add(new Campo("i04_xprod", "VARCHAR", "", null));
		camposItem.add(new Campo("i05_ncm", "VARCHAR", "", null));
		camposItem.add(new Campo("i08_cfop", "VARCHAR", "", null));
		camposItem.add(new Campo("i09_ucom", "VARCHAR", "", null));
		camposItem.add(new Campo("i10_qcom", "NUMERIC", "", null));
		camposItem.add(new Campo("i10a_vuncom", "NUMERIC", "", null));
		camposItem.add(new Campo("i11_vprod", "NUMERIC", "", null));
		camposItem.add(new Campo("i13_utrib", "VARCHAR", "", null));
		camposItem.add(new Campo("i14_qtrib", "NUMERIC", "", null));
		camposItem.add(new Campo("i14a_vuntrib", "NUMERIC", "", null));

		camposItem.add(new Campo("i15_vfrete", "NUMERIC", "0", null));
		camposItem.add(new Campo("i16_vseg", "NUMERIC", "0", null));
		camposItem.add(new Campo("i17_vdesc", "NUMERIC", "0", null)); // Valor do desconto. Padrão = '0'.
		camposItem.add(new Campo("i17a_voutro", "NUMERIC", "0", null));
		camposItem.add(new Campo("i17b_indtot", "INTEGER", "1", null));

		// Grupo CRT=1 – Simples Nacional e CSOSN=102,103,300 ou 400
		camposItem.add(new Campo("n11_orig_simples", "VARCHAR", "0", null));

		camposItem.add(new Campo("n12a_csosn", "VARCHAR", "103", null)); // se for devolução = 900		
		camposItem.add(new Campo("n12_cst", "VARCHAR", "103", null)); // se for devolução = 900

		camposItem.add(new Campo("n13_modbc_simples", "VARCHAR", "", null)); // se for devolução = 3

		camposItem.add(new Campo("n15_vbc_simples", "NUMERIC", "", null));
		camposItem.add(new Campo("n16_picms_simples", "NUMERIC", "", null));
		camposItem.add(new Campo("n17_vicms_simples", "NUMERIC", "", null));

		camposItem.add(new Campo("n15_vbc", "NUMERIC", "", null));
		camposItem.add(new Campo("n16_picms", "NUMERIC", "", null));
		camposItem.add(new Campo("n17_vicms", "NUMERIC", "", null));

		camposItem.add(new Campo("q06_cst_pis", "VARCHAR", "07", null));
		camposItem.add(new Campo("s06_cst_cofins", "VARCHAR", "07", null));

		camposItem.add(new Campo("empresa", "INTEGER", "1", null));
		camposItem.add(new Campo("filial", "INTEGER", "1", null));

		return camposItem;

	}

	/**
	 * Obtém todos os XMLs de um determinado mês (para enviar para contabilidade).
	 * 
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	@Override
	public List<Map<String, Object>> getXMLs(Date mesAno) throws ViewException {
		try {
			String sql = "SELECT a03_id, xml_nfe, status_nfe, status_canc FROM nfevv WHERE (b09_demi BETWEEN ? AND ?) AND (b24_tpamb = 1)";
			List<Map<String, Object>> r = getJdbcTemplate()
					.queryForList(sql, CalendarUtil.primeiroDiaNoMesAno(mesAno), CalendarUtil
							.ultimoDiaNoMesAno(mesAno));
			return r;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new ViewException("Erro ao obter xmls", e);
		}
	}

	/**
	 * Obtém todos os XMLs de um determinado mês (para enviar para contabilidade).
	 * 
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	@Override
	public Map<String, Object> getXML(Integer spartacusId) throws ViewException {
		try {
			String sql = "SELECT a03_id, xml_nfe, status_nfe, status_canc FROM nfevv WHERE id = ?";
			Map<String, Object> r = getJdbcTemplate()
					.queryForMap(sql, spartacusId);
			return r;
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new ViewException("Erro ao obter xml", e);
		}
	}

	public NotaFiscalFinder getNotaFiscalFinder() {
		return notaFiscalFinder;
	}

	public void setNotaFiscalFinder(NotaFiscalFinder notaFiscalFinder) {
		this.notaFiscalFinder = notaFiscalFinder;
	}

	public PessoaFinder getPessoaFinder() {
		return pessoaFinder;
	}

	public void setPessoaFinder(PessoaFinder pessoaFinder) {
		this.pessoaFinder = pessoaFinder;
	}

	public NotaFiscalDataMapper getNotaFiscalDataMapper() {
		return notaFiscalDataMapper;
	}

	public void setNotaFiscalDataMapper(NotaFiscalDataMapper notaFiscalDataMapper) {
		this.notaFiscalDataMapper = notaFiscalDataMapper;
	}

	public VendaDataMapper getVendaDataMapper() {
		return vendaDataMapper;
	}

	public void setVendaDataMapper(VendaDataMapper vendaDataMapper) {
		this.vendaDataMapper = vendaDataMapper;
	}

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	public MensagemRetornoRFFinder getMensagemRetornoRFFinder() {
		return mensagemRetornoRFFinder;
	}

	public void setMensagemRetornoRFFinder(MensagemRetornoRFFinder mensagemRetornoRFFinder) {
		this.mensagemRetornoRFFinder = mensagemRetornoRFFinder;
	}

	public MunicipioFinder getMunicipioFinder() {
		return municipioFinder;
	}

	public void setMunicipioFinder(MunicipioFinder municipioFinder) {
		this.municipioFinder = municipioFinder;
	}

	public ClienteFinder getClienteFinder() {
		return clienteFinder;
	}

	public void setClienteFinder(ClienteFinder clienteFinder) {
		this.clienteFinder = clienteFinder;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

}



class Campo {

	private String nome;

	private String tipo;

	private String valorPadrao;

	private String valor;

	public Campo(String nome, String tipo, String valorPadrao, String valor) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.valorPadrao = valorPadrao;
		this.valor = valor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValorPadrao() {
		return valorPadrao;
	}

	public void setValorPadrao(String valorPadrao) {
		this.valorPadrao = valorPadrao;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setValor(Object obj) {
		this.valor = obj != null ? obj.toString() : null;
	}

}
