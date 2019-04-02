package com.bonsucesso.erp.estoque.data;



import java.security.InvalidParameterException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade Produto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("produtoDataMapper")
public class ProdutoDataMapperImpl extends DataMapperImpl<Produto> implements ProdutoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -6969748498264384076L;

	protected static Logger logger = Logger.getLogger(ProdutoDataMapperImpl.class);

	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Produto saveComPreco(Produto produto, ProdutoPreco produtoPreco) throws ViewException {
		if ((produto == null) || (produtoPreco == null)) {
			throw new InvalidParameterException("parâmetros não podem ser null");
		}

		if (produtoPreco.getDtPrecoVenda() == null) {
			produtoPreco.setDtPrecoVenda(new Date());
		}

		produtoPreco.setProduto(produto);
		if (produto.getId() == null) {
			// se é INSERT, então é somente um preço
			produto.getPrecos().add(produtoPreco);
		} else {
			// se é UPDATE, então verifica o que fazer com o preço
			if (produto.getPrecoAtual().equals(produtoPreco)) {
				// ok, está somente alterando o preço atual
				logger.debug("alterando o preço atual");
				produto.getPrecoAtual().setCoeficiente(produtoPreco.getCoeficiente());
				produto.getPrecoAtual().setCustoOperacional(produtoPreco.getCustoOperacional());
				produto.getPrecoAtual().setDtCusto(produtoPreco.getDtCusto());
				produto.getPrecoAtual().setMargem(produtoPreco.getMargem());
				produto.getPrecoAtual().setPrazo(produtoPreco.getPrazo());
				produto.getPrecoAtual().setPrecoCusto(produtoPreco.getPrecoCusto());
				produto.getPrecoAtual().setPrecoPrazo(produtoPreco.getPrecoPrazo());
				produto.getPrecoAtual().setPrecoPromo(produtoPreco.getPrecoPromo());
				produto.getPrecoAtual().setPrecoVista(produtoPreco.getPrecoVista());
				produto.getPrecoAtual().setDtPrecoVenda(produtoPreco.getDtPrecoVenda());
			}
			// Se não está editando o preço atual (ou seja, é com uma data diferente), mas se já existe um preço nesta data, retorna erro
			else if (!produto.getPrecoAtual().equals(produtoPreco) && produto.getPrecos().contains(produtoPreco)) {
				throw new IllegalStateException("Preço já existente");
			} else {
				produtoPreco.setId(null);
				produto.getPrecos().add(produtoPreco);
			}
		}

		return getThiz().save(produto);

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Produto beforeSave(Produto produto) throws ViewException {
		produto.setNcm(produto.getNcm() != null ? produto.getNcm() : "62179000");
		produto.setCst(produto.getCst() != null ? produto.getCst() : "102");
		produto.setIcms(0); // ektProduto.getIcms().intValue());
		produto.setFracionado(produto.getFracionado() != null ? produto.getFracionado() : false);
		produto.setTipoTributacao("T"); // ektProduto.getTipoTrib());

		if (produto.getReduzido() == null) {
			Long proximoReduzido = getProdutoFinder().findProximoReduzido();
			produto.setReduzido(proximoReduzido);
		}
		
		if (produto.getReferencia() == null || "".equals(produto.getReferencia().trim())) {
			String proximoReferencia = getProdutoFinder().findProximaReferencia(produto);
			produto.setReferencia(proximoReferencia);
		}
		
		getEntityIdHandler().handleEntityIdFilhos(produto.getPrecos());
		getEntityIdHandler().handleEntityIdFilhos(produto.getSaldos());

		return produto;
	}

	@Override
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

}
