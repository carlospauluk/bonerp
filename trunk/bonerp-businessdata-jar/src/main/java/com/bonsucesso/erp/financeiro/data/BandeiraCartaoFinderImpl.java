package com.bonsucesso.erp.financeiro.data;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.BandeiraCartao;
import com.bonsucesso.erp.financeiro.model.Modo;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Modo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("bandeiraCartaoFinder")
public class BandeiraCartaoFinderImpl extends BasicEntityFinderImpl<BandeiraCartao> implements
		BandeiraCartaoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 5080281579339039088L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public BandeiraCartao findBy(String descricao) throws ViewException {
		return getThiz()
				.findSingleResult("FROM BandeiraCartao WHERE descricao LIKE :descricao", "descricao", descricao);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public BandeiraCartao findByLabelsAndModo(String str, Modo modo) throws ViewException {
		str = str.toUpperCase();
		// Primeiro tenta achar por um LIKE no campo labels
		BandeiraCartao r = null;

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("str", "%" + str.toUpperCase() + "%");
			params.put("modo", modo);
			r = getThiz()
					.findSingleResult("FROM BandeiraCartao WHERE modo = :modo AND (descricao LIKE :str OR labels LIKE :str)", params);
		} catch (Exception e) {
			logger.info("Erro ao pesquisar no BandeiraCartao.labels.");
		}

		// Senão encontrar, então usa a comparação inversa
		if (r == null) {

			List<BandeiraCartao> todas = getThiz().findAll();

			for (BandeiraCartao bc : todas) {
				if (bc.getModo().equals(modo)) {
					String[] labels = bc.getLabels().split("\n");
					for (String label : labels) {
						if (str.contains(label.replace("\r", "").toUpperCase())) {
							return bc;
						}
					}
				}
			}
		}

		return r;

	}

	@Override
	public BandeiraCartaoFinder getThiz() {
		return (BandeiraCartaoFinder) super.getThiz();
	}

}
