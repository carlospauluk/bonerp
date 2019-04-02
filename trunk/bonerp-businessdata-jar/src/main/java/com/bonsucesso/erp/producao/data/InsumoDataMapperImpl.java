package com.bonsucesso.erp.producao.data;



import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.data.ProdutoDataMapperImpl;
import com.bonsucesso.erp.producao.model.Insumo;
import com.bonsucesso.erp.producao.model.InsumoPreco;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade Insumo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("insumoDataMapper")
public class InsumoDataMapperImpl extends DataMapperImpl<Insumo> implements InsumoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 5613561842689652141L;

	protected static Logger logger = Logger.getLogger(ProdutoDataMapperImpl.class);

	@Autowired
	private InsumoFinder insumoFinder;

	public InsumoFinder getInsumoFinder() {
		return insumoFinder;
	}

	public void setInsumoFinder(InsumoFinder insumoFinder) {
		this.insumoFinder = insumoFinder;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Insumo beforeSave(Insumo insumo) throws ViewException {
		if (insumo.getCodigo() == null) {
			insumo.setCodigo(getInsumoFinder().findNextCodigo());
		}
		getEntityIdHandler().handleEntityIdFilhos(insumo.getPrecos());	
		return insumo;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Insumo saveComPreco(Insumo insumo, InsumoPreco insumoPreco) throws ViewException {
		if ((insumo == null) || (insumoPreco == null)) {
			throw new InvalidParameterException("parâmetros não podem ser null");
		}

		insumoPreco.setInsumo(insumo);
		if (insumo.getId() == null) {
			// se é INSERT, então é somente um preço
			// Como (até então) não é feito cálculo de preços sobre insumos, só seta como zero para não dar erro de not null
			insumoPreco.setCoeficiente(BigDecimal.ZERO);
			insumoPreco.setCustoOperacional(BigDecimal.ZERO);
			insumoPreco.setCustoFinanceiro(BigDecimal.ZERO);
			insumoPreco.setDtCusto(new Date());
			insumoPreco.setFornecedor(null);
			insumoPreco.setInsumo(insumo);
			insumoPreco.setMargem(BigDecimal.ZERO);
			insumoPreco.setPrecoPrazo(BigDecimal.ZERO);
			insumoPreco.setPrecoVista(BigDecimal.ZERO);
			getEntityIdHandler().handleEntityIdInserting(insumoPreco);
			insumo.getPrecos().add(insumoPreco);
			
		} else {
			// se é UPDATE, então verifica o que fazer com o preço
			if (insumo.getPrecoAtual().equals(insumoPreco)) {
				// ok, está somente alterando o preço atual
				logger.debug("alterando o preço atual");
				insumo.getPrecoAtual().setCoeficiente(insumoPreco.getCoeficiente());
				insumo.getPrecoAtual().setCustoOperacional(insumoPreco.getCustoOperacional());
				insumo.getPrecoAtual().setDtCusto(insumoPreco.getDtCusto());
				insumo.getPrecoAtual().setMargem(insumoPreco.getMargem());
				insumo.getPrecoAtual().setPrazo(insumoPreco.getPrazo());
				insumo.getPrecoAtual().setPrecoCusto(insumoPreco.getPrecoCusto());
				insumo.getPrecoAtual().setPrecoPrazo(insumoPreco.getPrecoPrazo());
				insumo.getPrecoAtual().setPrecoVista(insumoPreco.getPrecoVista());
			}
			// Se não está editando o preço atual (ou seja, é com uma data diferente), mas se já existe um preço nesta data, retorna erro
			else if (!insumo.getPrecoAtual().equals(insumoPreco) && insumo.getPrecos().contains(insumoPreco)) {
				throw new IllegalStateException("Preço já existente");
			} else {
				insumoPreco.setId(null);
				insumo.getPrecos().add(insumoPreco);
			}
			getEntityIdHandler().handleEntityIdUpdating(insumoPreco);
		}

		return save(insumo);

	}

}
