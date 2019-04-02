package com.bonsucesso.erp.financeiro.data;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bonsucesso.erp.financeiro.data.MovimentacaoFinderImpl.TipoSaldo;
import com.bonsucesso.erp.financeiro.model.BandeiraCartao;
import com.bonsucesso.erp.financeiro.model.Cadeia;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.bonsucesso.erp.financeiro.model.Modo;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.OperadoraCartao;
import com.bonsucesso.erp.financeiro.model.Status;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Movimentacao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface MovimentacaoFinder extends BasicEntityFinder<Movimentacao> {

	public Movimentacao findBy(Cadeia cadeia, Categoria categoria) throws ViewException;

	public Movimentacao findBy(Cadeia cadeia, Integer cadeiaOrdem) throws ViewException;

	public Movimentacao findBy(BigDecimal valor, Carteira carteira, String numChequeProprio) throws ViewException;

	public List<Movimentacao> findBy(Date dtMoviment, BigDecimal valor, GrupoItem grupoItem) throws ViewException;

	public List<Movimentacao> findBy(Categoria categoria, Date dtPagtoIni, Date dtPagtoFim) throws ViewException;

	public List<Movimentacao> findBy(Date dtUtil, BigDecimal valorTotal, Long entradaOuSaida,
			Modo modo, List<Status> statuss, List<Carteira> carteiras, String numCheque)
			throws ViewException;

	public List<Movimentacao> findBy(Date dtVenctoEfetiva, BigDecimal valor, Carteira carteira,
			BandeiraCartao bandeiraCartao, Categoria categoria)
			throws ViewException;

	public List<Movimentacao> findBy(Carteira carteira, Date dtPagto, Modo modo, Categoria categoria)
			throws ViewException;

	public List<Movimentacao> findAnterioresExtrato(Date dtIni, Carteira carteira) throws ViewException;

	public List<Movimentacao> findAbertasAnteriores(Date dtIni, List<Carteira> carteiras) throws ViewException;

	public List<String> findDescricaoBy(Carteira carteira, String descricaoIni) throws ViewException;

	public BigDecimal findSaldo(Date dtPagto, List<Carteira> carteira, TipoSaldo tipoSaldo) throws ViewException;

	public BigDecimal findSaldo(Date dtPagto, Carteira carteira, TipoSaldo tipoSaldo)
			throws ViewException;

	public BigDecimal findSaldo(GrupoItem grupoItem) throws ViewException;

	public BigDecimal findTotal(Date dtIni, Date dtFim, Carteira carteira, Integer entradaOuSaida) throws ViewException;

	public BigDecimal findTotalMovimentacoesSaldoComCheques(Date dtPagto, Carteira carteira, TipoSaldo tipoSaldo)
			throws ViewException;

	public BigDecimal findTotal(Categoria categoria, Date dtIni, Date dtFim) throws ViewException;

	public BigDecimal findTotal(Carteira carteira, Categoria categoria, Date dtIni, Date dtFim) throws ViewException;

	public BigDecimal findTotal(Carteira carteira, Categoria categoria, OperadoraCartao operadoraCartao, Modo modo,
			Date dtIni, Date dtFim)
			throws ViewException;

	public BigDecimal findTotal(Categoria categoria, Date dtIni, Date dtFim, boolean incluirSubCategs,
			boolean somenteTotalizaveis, String compararPorQualCampoDt) throws ViewException;

	public List<Movimentacao> listarErrosTransferenciaEntreCarteiras(Date dtIni, Date dtFim) throws ViewException;

	public List<Movimentacao> findBy(Carteira carteira, Categoria categoria, Date dtUtilIni, Date dtUtilFim)
			throws ViewException;

	public List<Movimentacao> findBy(Cadeia cadeia) throws ViewException;

	public List<Movimentacao> findBy(Date dtMoviment, BigDecimal valorTotal, Carteira carteira, Carteira carteiraDestino,
			BandeiraCartao bandeiraCartao, Categoria categoria) throws ViewException;

}
