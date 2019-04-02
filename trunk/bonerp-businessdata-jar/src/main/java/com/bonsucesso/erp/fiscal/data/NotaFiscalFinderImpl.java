package com.bonsucesso.erp.fiscal.data;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.TipoNotaFiscal;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade NotaFiscal.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("notaFiscalFinder")
public class NotaFiscalFinderImpl extends BasicEntityFinderImpl<NotaFiscal> implements
		NotaFiscalFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1337666166200982032L;

	protected static Logger logger = Logger.getLogger(NotaFiscalFinderImpl.class);
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public NotaFiscal findBy(Integer numero, Integer serie, Pessoa pessoaEmitente) throws ViewException {
		final String jpql = "FROM NotaFiscal WHERE numero = :numero AND serie = :serie AND pessoaEmitente = :pessoaEmitente";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("numero", numero);
		params.put("serie", serie);
		params.put("pessoaEmitente", pessoaEmitente);
		return getThiz().findSingleResult(jpql, params, false);
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<NotaFiscal> findBy(Integer status) throws ViewException {
		final String jpql = "SELECT v.notaFiscal FROM Venda v WHERE (v.notaFiscal.status IS NULL OR v.notaFiscal.status = :status)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		return getThiz().findResults(jpql, params, false);
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public Integer findProxNumFiscal(boolean producao, int serie, TipoNotaFiscal tipo) throws ViewException {
		try {

			String chave = producao ? "bonsucesso.fiscal.prod" : "bonsucesso.fiscal.hom";
			chave +=  ".sequencia-" + tipo.toString().toLowerCase();
			chave += "." + serie;

			// FOR UPDATE para garantir que ninguém vai alterar este valor antes de terminar esta transação
			String sql = "SELECT valor FROM cfg_config WHERE chave = :chave FOR UPDATE";
			Query qry = getEntityManager().createNativeQuery(sql);
			qry.setParameter("chave", chave);

			Integer prox = null;
			try {
				String strProx = (String) qry.getSingleResult();
				prox = Integer.parseInt(strProx);
			} catch (Exception e) {
				throw new ViewException("Valor não encontrado para a chave '" + chave + "'");
			}
			
			// Verificação se por algum motivo a numeração na fis_nf já não está pra frente...
			Integer ultimoNaBase = null;
			try {
				// Verifica se por acaso é realmente o último número na base
				String sqlUltimo = "SELECT max(numero) FROM fis_nf WHERE ambiente = :ambiente AND serie = :serie AND tipo = :tipo FOR UPDATE";
				Query qryUltimo = getEntityManager().createNativeQuery(sqlUltimo);
				qryUltimo.setParameter("ambiente", producao ? "PROD" : "HOM");
				qryUltimo.setParameter("serie", serie);
				qryUltimo.setParameter("tipo", tipo.toString().toLowerCase());
				ultimoNaBase = (Integer) qryUltimo.getSingleResult();
			} catch (NoResultException e) {
				logger.info("Nenhuma nota encontrada na base para os parâmetros (prod='" + producao + "', serie='" + serie + "', tipo='" + tipo + "'");
				ultimoNaBase = 0;
			} catch (Exception e) {
				throw new ViewException("Erro ao verificar o último número de nota efetivo na base", e);
			}
			
			if (ultimoNaBase != null && ultimoNaBase != prox) {
				prox = ultimoNaBase;
			}
			
			prox++;

			String sqlUpdate = "UPDATE cfg_config SET valor = :valor WHERE chave = :chave";

			Query qryUpdate = getEntityManager().createNativeQuery(sqlUpdate);
			qryUpdate.setParameter("valor", prox.toString());
			qryUpdate.setParameter("chave", chave);
			qryUpdate.executeUpdate();

			return prox;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar próximo número de PV", e);
		}
	}

}
