package com.bonsucesso.erp.vendas.data;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.model.StatusVenda;
import com.bonsucesso.erp.vendas.model.Venda;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Venda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface VendaFinder extends BasicEntityFinder<Venda> {

	public Venda findBy(Date dtVenda, Integer pv) throws ViewException;

	public BigDecimal findTotalBy(Date ini, Date fim, Funcionario vendedor) throws ViewException;

	public BigDecimal findTotalBy(Date ini, Date fim) throws ViewException;

	public BigDecimal findTotalBy(Date mesAno, Funcionario vendedor) throws ViewException;

	public BigDecimal findTotalBy(Date mesAno) throws ViewException;

	public List<Venda> findVendasBy(Date dia) throws ViewException;

	public Map<Funcionario, BigDecimal> findTotalVendasFuncionariosAtivos(Date mesAno) throws ViewException;
	
	public Map<Funcionario, BigDecimal> findTotalVendasFuncionariosTodos(Date mesAno) throws ViewException;

	public Integer findQtdeVendasBy(Date ini, Date fim, Funcionario vendedor) throws ViewException;

	public Integer findQtdeVendasBy(Date ini, Date fim) throws ViewException;

	public Integer findQtdeVendasBy(Date mesAno, Funcionario vendedor) throws ViewException;

	public Integer findQtdeVendasBy(Date mesAno) throws ViewException;

	public Integer findPosicaoBy(Date mesAno, Funcionario vendedor) throws ViewException;

	public Integer findQtdeDiasComVendasBy(Date mesAno, Funcionario vendedor) throws ViewException;

	public boolean findConsideraMes(Date mesAno, Funcionario vendedor) throws ViewException;

	public Integer findQtdeVendedoresConsideraMes(Date mesAno) throws ViewException;

	public Date findUltimoDiaComVendas() throws ViewException;

	public BigDecimal findTotalAVistaEKT(Date ini, Date fim, Boolean bonsucesso) throws ViewException;

	public List<Venda> findAllBy(String mesano) throws ViewException;
	
	public List<Venda> findVendasComProdutosBy(Fornecedor fornecedor, Subdepartamento subdepto, Date mesAno)
			throws ViewException;
	
	public Integer findProximoPV(String mesano) throws ViewException;
	
	public Map<Date, BigDecimal> findQtdeItensVendidosBy_direto(Fornecedor fornecedor, Subdepartamento subdepto,
			Date mesAnoIni, Date mesAnoFim) throws ViewException;

	public Map<Date, BigDecimal> findQtdeItensVendidosByFornecedor(Fornecedor fornecedor, Subdepartamento subdepto,
			Date mesAnoIni, Date mesAnoFim) throws ViewException;

	public Map<Subdepartamento, Map<Date, BigDecimal>> findQtdeItensVendidosBy(Fornecedor fornecedor, Date mesAnoIni,
			Date mesAnoFim) throws ViewException;

	public BigDecimal findQtdeItensVendidosBy(Produto produto, Date mesAno) throws ViewException;

	/**
	 * Retorna uma lista de Object[] sendo 0: Fornecedor, 1: Subdepto, 2: Map<Date mesano, BigDecimal qtdeVendida>.
	 * 
	 * @param mesAnoIni
	 * @param mesAnoFim
	 * @return
	 * @throws ViewException
	 */
	public List<Object[]> findQtdeItensVendidosBy(Date mesAnoIni, Date mesAnoFim) throws ViewException;

	/**
	 * Retorna todas as vendas que ainda não foram faturadas.
	 * 
	 * @return
	 * @throws ViewException
	 */
	public List<Venda> findVendasASeremFaturadas(Date dtVenda) throws ViewException;

	/**
	 * Busca todas as vendas que já tenham notaFiscal vinculada, porém com status = -1 ou null.
	 * @return
	 * @throws ViewException
	 */
	public List<Venda> findVendasAguardandoStatusNotaFiscalReceita() throws ViewException;

	
	/**
	 * 
	 * @param dtVenda
	 * @param status
	 * @return
	 * @throws ViewException
	 */
	public List<Venda> findBy(Date dtVenda, StatusVenda status) throws ViewException;

	/**
	 * Encontra uma venda pelo número do PV e o mesano.
	 * 
	 * @param pv
	 * @param mesano
	 * @return
	 * @throws ViewException
	 */
	public Venda findBy(Integer pv, Date mesano) throws ViewException;

	/**
	 * Encontra as vendas de determinado dia.
	 * 
	 * @param dtVenda
	 * @return
	 * @throws ViewException
	 */
	public List<Venda> findBy(Date dtVenda) throws ViewException;

	/**
	 * 
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	public List<Funcionario> findFuncionariosComVendaNoMesAno(Date mesAno) throws ViewException;

	/**
	 * 
	 * @param ini
	 * @param fim
	 * @return
	 * @throws ViewException
	 */
	public BigDecimal findTotalSomenteVendedoresInternosBy(Date mesAno) throws ViewException;

	
	public List<Venda> findVendasSomenteVendedoresInternosBy(Date mesAno) throws ViewException;

	public List<Venda> findVendasSomenteExternosBy(Date mesAno) throws ViewException;

	public List<Venda> findVendasBy(Date mesAno, Funcionario vendedor) throws ViewException;

	public BigDecimal findTotalSomenteVendedoresInternosBy(Date ini, Date fim) throws ViewException;


}
