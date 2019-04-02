package com.bonsucesso.erp.financeiro.data;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.data.DiaUtilFinder;
import com.bonsucesso.erp.financeiro.business.MovimentacaoBusiness;
import com.bonsucesso.erp.financeiro.model.Cadeia;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.Parcelamento;
import com.bonsucesso.erp.financeiro.model.PlanoPagtoCartao;
import com.bonsucesso.erp.financeiro.model.Recorrencia;
import com.bonsucesso.erp.financeiro.model.Recorrencia.Frequencia;
import com.bonsucesso.erp.financeiro.model.Recorrencia.TipoRepeticao;
import com.bonsucesso.erp.financeiro.model.Status;
import com.bonsucesso.erp.financeiro.model.TipoLancto;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.StringUtils;


/**
 * Implementação de DataMapper para a entidade Movimentacao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("movimentacaoDataMapper")
public class MovimentacaoDataMapperImpl extends DataMapperImpl<Movimentacao>
		implements MovimentacaoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -294722674162544206L;

	protected static Logger logger = Logger.getLogger(MovimentacaoDataMapperImpl.class);

	@Autowired
	private CategoriaFinder categoriaFinder;

	@Autowired
	private CarteiraFinder carteiraFinder;

	@Autowired
	private CentroCustoFinder centroCustoFinder;

	@Autowired
	private DiaUtilFinder diaUtilFinder;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Movimentacao beforeSave(Movimentacao movimentacao)
			throws ViewException {

		try {
			if (movimentacao.getUnqControle() == null || "".equals(movimentacao.getUnqControle())) {
				movimentacao.setUnqControle(StringUtils.generateRandomString(15));
			}

			// Salvando movimentação agrupada
			if (movimentacao.getTipoLancto().equals(TipoLancto.MOVIMENTACAO_AGRUPADA)) {
				if (movimentacao.getGrupoItem() == null) {
					throw new ViewException("GrupoItem deve ser informado.");
				} else {
					movimentacao.setDtVencto(movimentacao.getGrupoItem().getDtVencto());
					movimentacao.setDtVenctoEfetiva(movimentacao.getGrupoItem().getDtVencto());
					movimentacao.setDtPagto(movimentacao.getGrupoItem().getDtVencto());

					Carteira carteiraMovsAgrupadas = getCarteiraFinder().findBy(7);
					movimentacao.setCarteira(carteiraMovsAgrupadas);
				}
			}

			if (movimentacao.getCarteira() == null) {
				throw new ViewException("Campo 'Carteira' precisa ser informado (bs).");
			}

			if (movimentacao.getPlanoPagtoCartao() == null) {
				movimentacao.setPlanoPagtoCartao(PlanoPagtoCartao.N_A);
			}

			if (movimentacao.getCategoria() == null) {
				throw new ViewException("É necessário informar a categoria da movimentação.");
			} else if (movimentacao.getCategoria().getCentroCustoDif() == Boolean.FALSE) {
				movimentacao.setCentroCusto(getCentroCustoFinder().findBy(1));
			}

			// Se não passar descrição, tenta montá-la a partir da bandeira do cartão.
			if ((movimentacao.getDescricao() == null) || "".equals(movimentacao.getDescricao().trim())) {
				if (movimentacao.getBandeiraCartao() != null) {
					movimentacao.setDescricao(movimentacao.getBandeiraCartao().getDescricao());
				}
			}

			// Transferências próprias e recolhimentos de carteiras só passam a Dt Moviment na view
			if (movimentacao.getTipoLancto().equals(TipoLancto.TRANSF_CAIXA) ||
					movimentacao.getTipoLancto().equals(TipoLancto.TRANSF_PROPRIA)) {

				if (movimentacao.getDtVencto() == null) {
					movimentacao.setDtVencto(movimentacao.getDtMoviment());
				}
				if (movimentacao.getDtPagto() == null) {
					movimentacao.setDtPagto(movimentacao.getDtMoviment());
				}
			}

			// se não é do tipo cheque, então marca como null a entidade
			if (Boolean.FALSE.equals(movimentacao.getModo().getModoDeCheque())) {
				movimentacao.setCheque(null);
			} else {
				// se for com cheque mas estiver como ABERTA, muda para A_COMPENSAR (pq pode ter sido erro de importação).
				if (movimentacao.getStatus().equals(Status.ABERTA)) {
					movimentacao.setStatus(Status.A_COMPENSAR);
				}
			}

			if (movimentacao.getModo().getModoDeCartao().equals(Boolean.FALSE)) {
				movimentacao.setBandeiraCartao(null);
				movimentacao.setOperadoraCartao(null);
			}

			if (movimentacao.getDtPagto() == null) {
				if (movimentacao.getModo().getDescricao().contains("CHEQUE")) {
					movimentacao.setStatus(Status.A_COMPENSAR);
				} else {
					movimentacao.setStatus(Status.ABERTA);
				}
			} else if (movimentacao.getDtPagto() != null) {
				movimentacao.setStatus(Status.REALIZADA);
				if (movimentacao.getDtVencto() == null) {
					movimentacao.setDtVencto(movimentacao.getDtPagto());
				}
				if (movimentacao.getDtMoviment() == null) {
					movimentacao.setDtMoviment(movimentacao.getDtPagto());
				}
			}

			if (movimentacao.getRecorrencia() != null
					&& Boolean.FALSE.equals(movimentacao.getRecorrencia().getRecorrente())) {
				movimentacao.getRecorrencia().setDia(null);
				movimentacao.getRecorrencia().setFrequencia(Recorrencia.Frequencia.NENHUMA);
				movimentacao.getRecorrencia().setTipoRepeticao(Recorrencia.TipoRepeticao.NENHUMA);
			}

			if (movimentacao.getStatus().equals(Status.REALIZADA)) {
				if (movimentacao.getCarteira().getConcreta() == Boolean.FALSE) {
					throw new ViewException("Somente carteiras concretas podem conter movimentações realizadas.");
				}
				if (movimentacao.getModo().getCodigo().equals(99)) {
					throw new ViewException("Não é possível salvar uma movimentação realizada em modo 99 - INDEFINIDO");
				}
			}

			// Calcula a dtVenctoEfetiva (considera-se sempre o próximo dia útil)
			if ((movimentacao.getDtVenctoEfetiva() == null) && (movimentacao.getDtVencto() != null)) {
				movimentacao.setDtVenctoEfetiva(getDiaUtilFinder()
						.findProximoDiaUtilFinanceiro((movimentacao.getDtVencto())));
			}

			if ((movimentacao.getValor() == null) && (movimentacao.getValorTotal() != null)) {
				movimentacao.setValor(movimentacao.getValorTotal());
			}

			// O valor total é read-only
			BigDecimal valor = CurrencyUtils.getBigDecimalCurrency((movimentacao.getValor() == null ? BigDecimal.ZERO
					: movimentacao.getValor()).doubleValue()).abs();
			movimentacao.setValor(valor);

			BigDecimal descontos = CurrencyUtils
					.getBigDecimalCurrency((movimentacao.getDescontos() == null ? BigDecimal.ZERO : movimentacao
							.getDescontos()).doubleValue())
					.abs().negate();
			movimentacao.setDescontos(descontos);
			
			BigDecimal acrescimos = CurrencyUtils
					.getBigDecimalCurrency((movimentacao.getAcrescimos() == null ? BigDecimal.ZERO : movimentacao
							.getAcrescimos()).doubleValue())
					.abs();
			movimentacao.setAcrescimos(acrescimos);

			movimentacao.setValorTotal(CurrencyUtils.soma(valor, descontos, acrescimos));

			if (movimentacao.getParcelamento() != null) {
				if (movimentacao.getParcelamento().getId() != null) {
					movimentacao.setParcelamento(getEntityManager()
							.find(Parcelamento.class, movimentacao.getParcelamento().getId()));
				}
				movimentacao.getParcelamento().recalcularParcelas();
				movimentacao.setQtdeParcelas(movimentacao.getParcelamento().getQtdeParcelas());
			}

			if ((movimentacao.getPessoa() != null) && (movimentacao.getPessoa().getId() == null)) {
				movimentacao.setPessoa(null);
			}

			// ajusta a dt útil
			movimentacao.setDtUtil(movimentacao.getDtPagto() == null ? movimentacao.getDtVenctoEfetiva() : movimentacao
					.getDtPagto());

			if ((movimentacao.getStatus().equals(Status.ABERTA) || movimentacao.getStatus().equals(Status.A_COMPENSAR))
					&& (movimentacao.getCarteira().getAbertas().equals(Boolean.FALSE))) {
				throw new ViewException("Esta carteira não pode conter movimentações abertas.");
			}

			getEntityIdHandler().handleEntityId(movimentacao.getParcelamento());

			return movimentacao;
		} catch (ViewException e) {
			throw e;
		} catch (Exception e) {
			throw new ViewException("Erro no beforeSave()", e);
		}

	}

	/**
	 * Método principal para "save" que deve ser chamado pelos clientes ao invés de chamar diretamente o save().
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public Movimentacao processSaves(Movimentacao movimentacao)
			throws ViewException {

		try {
			logger.debug("Iniciando o processSaves()...");

			MovimentacaoDataMapper dataMapper = (MovimentacaoDataMapper) getBeanFactory()
					.getBean("movimentacaoDataMapper");

			// Se estiver inserindo, verifica os procedimentos para cada tipoLancto.
			if (movimentacao.getId() == null) {
				if (movimentacao.getTipoLancto() == null) {
					throw new ViewException("Tipo Lancto não informado para " + movimentacao.getDescricaoMontada());
				}
				TipoLancto tipoLancto = movimentacao.getTipoLancto();
				if (tipoLancto.equals(TipoLancto.TRANSF_PROPRIA)) {
					return dataMapper.saveTransfPropria(movimentacao);
				} else if (tipoLancto.equals(TipoLancto.TRANSF_CAIXA)) {
					return dataMapper.saveTransfCaixa(movimentacao);
				}
			}

			Movimentacao r = dataMapper.save(movimentacao);

			dataMapper.getEntityManager().flush();

			return r;
		} catch (ViewException ve) {
			throw ve; // ViewException devem subir até a view.
		} catch (Exception e) {
			throw new ViewException("Erro ao salvar movimentação (" + movimentacao.toStringToView() + ")", e);
		}

	}

	/**
	 * Salva uma transferência entre carteiras.
	 * Pode ser chamado a partir da saveTransfCaixa (que gera, então, 3 movimentações).
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public Movimentacao saveTransfPropria(Movimentacao movimentacao) throws ViewException {

		logger.debug("INICIANDO saveTransfPropria...");

		MovimentacaoDataMapper dataMapper = (MovimentacaoDataMapper) getBeanFactory()
				.getBean("movimentacaoDataMapper");

		Cadeia cadeia;

		Integer cadeiaOrdem = movimentacao.getCadeiaOrdem() == null ? 1 : movimentacao.getCadeiaOrdem();

		Movimentacao moviment299;

		// Se NÃO estiver passando uma 299 então é uma TRANSF_CAIXA (101 + 299 + 199).
		if (movimentacao.getCategoria().getCodigo() != 299) {
			cadeia = movimentacao.getCadeia();
			moviment299 = new Movimentacao();
			cadeiaOrdem = 2; // é a segunda movimentação das 3
			moviment299.setCadeiaOrdem(cadeiaOrdem);
			moviment299.setDescricao(movimentacao.getDescricao());
			logger.debug("Pesquisando a categoria 299...");
			moviment299.setCategoria(getCategoriaFinder().findBy(299l));
			moviment299.setModo(movimentacao.getModo());
			moviment299.setCarteira(movimentacao.getCarteira());
			moviment299.setStatus(Status.REALIZADA);
			moviment299.setValor(movimentacao.getValor());
			moviment299.setValorTotal(movimentacao.getValorTotal());
			moviment299.setCentroCusto(movimentacao.getCentroCusto());
			moviment299.setDtMoviment(movimentacao.getDtMoviment());
			moviment299.setDtVencto(movimentacao.getDtVencto());
			moviment299.setDtVenctoEfetiva(movimentacao.getDtVenctoEfetiva());
			moviment299.setDtPagto(movimentacao.getDtPagto());
			moviment299.setTipoLancto(movimentacao.getTipoLancto());

			moviment299.setBandeiraCartao(movimentacao.getBandeiraCartao());

			logger.debug("Salvando a 299...");
		} else {
			// caso contrário é só uma TRANSF_PROPRIA mesmo
			cadeia = new Cadeia();

			moviment299 = movimentacao;
			cadeiaOrdem = 1; // é a primeira movimentação das 2
			moviment299.setCadeiaOrdem(cadeiaOrdem);

		}

		cadeia.setVinculante(false);

		CadeiaDataMapper cadeiaDataMapper = (CadeiaDataMapper) getBeanFactory().getBean("cadeiaDataMapper");
		cadeia = cadeiaDataMapper.save(cadeia);
		cadeiaDataMapper.getEntityManager().flush();

		moviment299.setCadeia(cadeia);

		// Salvar a 199
		Movimentacao moviment199 = new Movimentacao();
		moviment199.setCadeiaOrdem(++cadeiaOrdem); // aqui incremento, pois não sei se é a 299 foi a 1 ou a 2.
		moviment199.setCadeia(cadeia);
		moviment199.setDescricao(movimentacao.getDescricao());
		moviment199.setStatus(Status.REALIZADA);
		logger.debug("Pesquisando a categoria 199...");
		moviment199.setCategoria(getCategoriaFinder().findBy(199l));
		moviment199.setCentroCusto(getCentroCustoFinder().findBy(1));

		moviment199.setCarteira(movimentacao.getCarteiraDestino()); // <<<<<

		moviment199.setCheque(moviment299.getCheque());
		moviment199.setDtMoviment(moviment299.getDtMoviment());
		moviment199.setDtVencto(moviment299.getDtVencto());
		moviment199.setDtVenctoEfetiva(moviment299.getDtVenctoEfetiva());
		moviment199.setDtPagto(moviment299.getDtPagto());
		moviment199.setModo(moviment299.getModo());
		moviment199.setValor(moviment299.getValor());
		moviment199.setValorTotal(moviment299.getValorTotal());
		moviment199.setTipoLancto(moviment299.getTipoLancto());

		moviment199.setBandeiraCartao(moviment299.getBandeiraCartao());

		logger.debug("Salvando a 199...");

		moviment299 = dataMapper.save(moviment299);
		// agora que já salvou a primeira, pode fechar a cadeia
		cadeia = moviment299.getCadeia();

		cadeia.setFechada(true);
		moviment199.setCadeia(cadeia);

		moviment199 = dataMapper.save(moviment199);

		// Tem que salvar a cadeia, pois foi removido os Cascades devido a outros problemas...
		cadeia.setVinculante(true);
		cadeia = cadeiaDataMapper.save(cadeia);

		cadeiaDataMapper.getEntityManager().flush();

		logger.debug("FINALIZANDO COM SUCESSO a saveTransfPropria...");

		return moviment299;
	}

	/**
	 * Salva uma TRANSFERÊNCIA DE ENTRADA DE CAIXA.
	 * São geradas 3 movimentações: a original do lançamento + 299 + 199.
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public Movimentacao saveTransfCaixa(Movimentacao movimentacao) throws ViewException {

		MovimentacaoDataMapper dataMapper = (MovimentacaoDataMapper) getBeanFactory()
				.getBean("movimentacaoDataMapper");

		// cria a cadeia destas movimentações
		Cadeia cadeia = new Cadeia();
		cadeia.setVinculante(true);

		movimentacao.setStatus(Status.REALIZADA);
		movimentacao.setCentroCusto(getCentroCustoFinder().findBy(1));
		movimentacao.setDtVencto(movimentacao.getDtMoviment());
		movimentacao.setDtVenctoEfetiva(movimentacao.getDtMoviment());
		movimentacao.setDtPagto(movimentacao.getDtMoviment());

		movimentacao.setCadeia(cadeia);

		movimentacao.setCadeiaOrdem(1);

		movimentacao = dataMapper.save(movimentacao);

		dataMapper.saveTransfPropria(movimentacao);

		return movimentacao;
	}

	/**
	 * Processa uma lista de movimentações recorrentes para gerar as movimentações posteriores.
	 *
	 * @return List de String com resultados.
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public List<String> processarRecorrentes(List<Movimentacao> movimentacoes) throws ViewException {
		// variável utilizada para marcar se deve ser salva a original (não mando salvar antes por causa dos problemas de flush).
		List<String> results = new ArrayList<String>();
		for (Movimentacao originante : movimentacoes) {
			try {
				results.add(getThiz().processarRecorrente(originante));
			} catch (Exception e) {
				results.add(e.getMessage());
			}
		}
		return results;
	}

	/**
	 * Processa uma movimentação para gerar/atualizar sua recorrente posterior.
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public String processarRecorrente(Movimentacao originante) throws ViewException {
		try {

			MovimentacaoDataMapper dataMapper = (MovimentacaoDataMapper) getBeanFactory()
					.getBean("movimentacaoDataMapper");

			MovimentacaoFinder finder = (MovimentacaoFinder) getBeanFactory()
					.getBean("movimentacaoFinder");

			boolean salvarOriginal = false;

			String result = "";

			if (originante.getRecorrencia().getRecorrente().equals(Boolean.FALSE)) {
				// Tem que ter sido passada uma List com movimentações que sejam recorrentes
				throw new ViewException("Movimentação não recorrente não pode ser processada.");
			}
			if (originante.getRecorrencia().getFrequencia().equals(Frequencia.NENHUMA)) {
				throw new ViewException("Recorrência com frequência = 'NENHUMA'.");
			}
			if (originante.getRecorrencia().getTipoRepeticao().equals(TipoRepeticao.NENHUMA)) {
				throw new ViewException("Recorrência com tipo de repetição = 'NENHUMA'.");
			}

			// atualizo
			originante = refresh(originante);
			if (originante.getPessoa() != null) {
				originante.getPessoa().hashCode(); // touch
			}
			originante.getCategoria().hashCode(); // touch

			// verifico se já existe a movimentação posterior
			if (originante.getCadeia() != null) {
				Movimentacao posterior = finder.findBy(originante.getCadeia(), originante.getCadeiaOrdem() + 1);
				if (posterior != null) {

					// verifico se teve alterações na originante
					if (originante.getIudt().getUpdated().getTime() > posterior.getIudt().getUpdated().getTime()) {

						posterior.setCarteira(originante.getCarteira());
						posterior.setCategoria(originante.getCategoria());
						posterior.setCentroCusto(originante.getCentroCusto());

						posterior.setModo(originante.getModo());
						posterior.setPessoa(originante.getPessoa());
						posterior.setRecorrencia(originante.getRecorrencia());
						posterior.setDescricao(originante.getDescricao());

						posterior.setValor(originante.getValor());
						posterior.setAcrescimos(originante.getAcrescimos());
						posterior.setDescontos(originante.getDescontos());
						posterior.setValorTotal(null); // null para recalcular no beforeSave

						calcularNovaDtVencto(originante, posterior);

						try {
							posterior = dataMapper.save(posterior);
							result = "<<SUCESSO>> ao atualizar movimentação: " + originante.getDescricao();
						} catch (Exception e) {
							logger.error(e);
							result = "<<ERRO>> ao atualizar movimentação: " + originante.getDescricao() + ". ("
									+ e.getMessage() + ")";
						}
					} else {
						result = "<<SUCESSO>> movimentação posterior já existente: " + originante.getDescricao()
								+ "\r\n";
					}

					return result;
				}
			}

			Movimentacao nova = SerializationUtils.clone(originante);
			nova.setUnqControle(StringUtils.generateRandomString(15));
			if (getEntityManager().contains(originante)) {
				getEntityManager().detach(originante); // removendo do entityManager para não dar problemas quando dá flush após consultas
			}

			nova.setId(null);
			nova.setDtPagto(null);

			Cadeia cadeia = originante.getCadeia();

			// Se ainda não possui uma cadeia...
			if (cadeia != null) {
				nova.setCadeiaOrdem(originante.getCadeiaOrdem() + 1);
			} else {
				cadeia = new Cadeia();

				// Como está sendo gerada uma cadeia nova, tenho que atualizar a movimentação original e mandar salva-la também.
				originante.setCadeiaOrdem(1);
				originante.setCadeia(cadeia);
				salvarOriginal = true; // tem que salvar a originante porque ela foi incluída na cadeia

				nova.setCadeiaOrdem(2);
			}

			cadeia.setVinculante(false);

			cadeia.setFechada(false);

			nova.setCadeia(cadeia);

			calcularNovaDtVencto(originante, nova);

			nova.setStatus(Status.ABERTA); // posso setar como ABERTA pois no beforeSave(), se for CHEQUE, ele altera para A_COMPENSAR.
			nova.setIdSistemaAntigo(null);
			nova.setTipoLancto(TipoLancto.A_PAGAR_RECEBER);

			// seto o número do cheque para ????, para que seja informado posteriormente.
			if (nova.getCheque() != null) {
				nova.getCheque().setNumCheque("????");
			}

			// Tem que salvar a cadeia, pois foi removido os Cascades devido a outros problemas...
			CadeiaDataMapper cadeiaDataMapper = (CadeiaDataMapper) getBeanFactory().getBean("cadeiaDataMapper");
			cadeia = cadeiaDataMapper.save(cadeia);
			cadeiaDataMapper.getEntityManager().flush();

			originante.setCadeia(cadeia);

			if (salvarOriginal) {
				try {
					originante = dataMapper.save(originante);
					result += "<<SUCESSO>> ao salvar movimentação originante: " + originante.getDescricao();
				} catch (Exception e) {
					logger.error(e);
					result += "<<ERRO>> ao salvar movimentação originante: " + originante.getDescricao() + ". ("
							+ e.getMessage() + ")";
				}
				nova.setCadeia(originante.getCadeia());
				salvarOriginal = false;
			}

			try {
				nova = dataMapper.save(nova);
				result += "<<SUCESSO>> ao gerar movimentação: " + nova.getDescricao();
			} catch (Exception e) {
				logger.error(e);
				result += "<<ERRO>> ao atualizar movimentação: " + originante.getDescricao() + ". ("
						+ e.getMessage() + ")";
			}

			return result;

		} catch (Exception e) {
			logger.error(e);
			return "<<ERRO>> ao processar movimentação: " + originante.getDescricao() + ". (" + e.getMessage()
					+ ")";
		}
	}

	/**
	 * @param originante
	 * @param nova
	 * @return
	 * @throws ViewException
	 */
	private void calcularNovaDtVencto(Movimentacao originante, Movimentacao nova) throws ViewException {
		Calendar novaDtVencto = CalendarUtil.getCalendar(originante.getDtVencto());

		if (nova.getRecorrencia().getFrequencia().equals(Frequencia.ANUAL)) {
			novaDtVencto.add(Calendar.YEAR, 1);
		} else if (nova.getRecorrencia().getFrequencia().equals(Frequencia.MENSAL)) {
			novaDtVencto.add(Calendar.MONTH, 1);
		}

		if (nova.getRecorrencia().getTipoRepeticao().equals(TipoRepeticao.DIA_FIXO)) {
			// se foi marcado com dia da recorrência maior ou igual a 31
			// ou se estiver processando fevereiro e a data de vencimento for maior ou igual a 29...
			// então sempre setará para o último dia do mês
			if ((nova.getRecorrencia().getDia() >= 31) ||
					((nova.getRecorrencia().getDia() >= 29)
							&& (novaDtVencto.get(Calendar.MONTH) == Calendar.FEBRUARY))) {

				// como já tinha adicionado +1 mês ali em cima, adicionada mais um, e volta pra trás com dia-1
				novaDtVencto.add(Calendar.MONTH, 1);
				novaDtVencto.set(Calendar.DAY_OF_MONTH, 1);
				novaDtVencto.add(Calendar.DAY_OF_MONTH, -1);
			} else {
				novaDtVencto.set(Calendar.DAY_OF_MONTH, nova.getRecorrencia().getDia());
			}
			nova.setDtVencto(novaDtVencto.getTime());
			nova.setDtVenctoEfetiva(getDiaUtilFinder().findProximoDiaUtilFinanceiro((new java.util.Date(nova
					.getDtVencto().getTime()))));
		} else if (nova.getRecorrencia().getTipoRepeticao().equals(TipoRepeticao.DIA_UTIL)) {
			// Procuro o dia útil ordinalmente...
			nova.setDtVencto(getDiaUtilFinder().findBy(novaDtVencto.getTime(), nova.getRecorrencia().getDia())
					.getDia());

			nova.setDtVenctoEfetiva(getDiaUtilFinder().findBy(novaDtVencto.getTime(), nova.getRecorrencia()
					.getDia()).getDia());
		}
	}

	/**
	 * Aqui a propagação tem que ser com REQUIRED por causa das deleções de parcelamentos, que devem acontecer em transação.
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(Movimentacao movimentacao) throws ViewException {
		movimentacao = refresh(movimentacao);
		// Se a movimentação faz parte de uma cadeia vinculante, exclui todas as movimentações da cadeia também.
		if ((movimentacao.getCadeia() != null) && movimentacao.getCadeia().getVinculante()) {
			Cadeia cadeia = movimentacao.getCadeia();
			for (Movimentacao m : cadeia.getMovimentacoes()) {
				super.delete(m);
			}
		} else {
			super.delete(movimentacao);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public void saveList(List<Movimentacao> lista) throws ViewException {
		try {
			logger.info("Iniciando save em lote ...");
			MovimentacaoDataMapper dataMapper = (MovimentacaoDataMapper) getBeanFactory()
					.getBean("movimentacaoDataMapper");
			for (Movimentacao m : lista) {
				logger.debug("Iniciando processSaves() de " + m.toStringToView());
				dataMapper.processSaves(m);
				// dataMapper.getEntityManager().flush();
			}
			logger.info("Finalizando save em lote com SUCESSO");
		} catch (Exception e) {
			logger.error("Erro ao salvar lista", e);
			throw new ViewException(e);
		}
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteList(List<Movimentacao> list) throws ViewException {
		logger.info("Iniciando delete em lote ...");
		MovimentacaoDataMapper dataMapper = (MovimentacaoDataMapper) getBeanFactory()
				.getBean("movimentacaoDataMapper");

		for (Movimentacao m : list) {
			dataMapper.delete(m);
		}
		logger.info("Finalizando delete em lote com SUCESSO");

	}

	@Override
	public Movimentacao beforeDelete(Movimentacao movimentacao) throws ViewException {
		if ((movimentacao.getParcelamento() != null) && (movimentacao.getNumParcela() != null)) {
			if (movimentacao.getNumParcela() < movimentacao.getParcelamento().getQtdeParcelas()) {
				throw new ViewException("Só é possível excluir a última parcela do parcelamento.");
			} else {
				movimentacao.getParcelamento().getParcelas()
						.remove(movimentacao.getParcelamento().getParcelas().indexOf(movimentacao));
				movimentacao.setParcelamento(null);
			}
		}
		return movimentacao;
	}

	@Override
	@Secured("ROLE_FINANCEIRO_LANCTOS")
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	public Movimentacao save(Movimentacao pEntity) throws ViewException {
		try {
			return super.save(pEntity);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	public void alterarLoteSave(List<Movimentacao> movimentacoes, Movimentacao movimentacao) throws ViewException {
		try {
			MovimentacaoBusiness buzz = (MovimentacaoBusiness) getBeanFactory().getBean("movimentacaoBusiness");
			movimentacoes = buzz.alterarEmLote(movimentacoes, movimentacao);
			getThiz().saveList(movimentacoes);
		} catch (Exception e) {
			throw new ViewException("Erro ao salvar lote de movimentações.", e);
		}
	}

	@Override
	public MovimentacaoDataMapper getThiz() {
		return (MovimentacaoDataMapper) super.getThiz();
	}

	public DiaUtilFinder getDiaUtilFinder() {
		return diaUtilFinder;
	}

	public void setDiaUtilFinder(DiaUtilFinder diaUtilFinder) {
		this.diaUtilFinder = diaUtilFinder;
	}

	public CategoriaFinder getCategoriaFinder() {
		return categoriaFinder;
	}

	public void setCategoriaFinder(CategoriaFinder categoriaFinder) {
		this.categoriaFinder = categoriaFinder;
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

}
