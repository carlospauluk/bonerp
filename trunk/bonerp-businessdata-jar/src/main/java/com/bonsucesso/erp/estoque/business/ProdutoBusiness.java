package com.bonsucesso.erp.estoque.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.cortinas.data.ArtigoCortinaFinder;
import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoSaldo;
import com.ocabit.base.view.exception.ViewException;


@Component("produtoBusiness")
public class ProdutoBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3586879036254268586L;

	protected static Logger logger = Logger.getLogger(ProdutoBusiness.class);

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private ArtigoCortinaFinder artigoCortinaFinder;

	public Map<Integer, ProdutoSaldo> buildMapGradeSaldos(Produto produto) throws ViewException {
		try {
			Map<Integer, ProdutoSaldo> map = new HashMap<Integer, ProdutoSaldo>();

			produto = getProdutoFinder().refresh(produto);

			for (GradeTamanho gt : produto.getGrade().getTamanhos()) {
				ProdutoSaldo ps = null;
				for (ProdutoSaldo _ps : produto.getSaldos()) {
					if (_ps.getGradeTamanho().equals(gt)) {
						ps = _ps;
						break;
					}
				}
				if (ps == null) {
					ps = new ProdutoSaldo();
					ps.setGradeTamanho(gt);
				}
				map.put(gt.getOrdem(), ps);
			}

			return map;
		} catch (Exception e) {
			throw new ViewException("Erro ao gerar mapa de grade/saldos.", e);
		}
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Produto updateQtdeTotal(Produto produto) throws ViewException {
		try {
			if (produto == null) {
				logger.info("PRODUTO == NULL ?????? ");
				return null;				
			}
			logger.info(" >>>> updateQtdeTotal(Produto produto = '" + produto.getId() + "')");
			Produto produtoRefreshed = getProdutoFinder().refresh(produto);
			if (produtoRefreshed != null) {
				produtoRefreshed.setQtdeTotal(BigDecimal.ZERO);
				if (produtoRefreshed.getSaldos() != null && !produtoRefreshed.getSaldos().isEmpty()) {
					if (produtoRefreshed.getQtdeTotal() == null) {
						produtoRefreshed.setQtdeTotal(BigDecimal.ZERO);
					}
					for (ProdutoSaldo ps : produtoRefreshed.getSaldos()) {
						if (ps.getQtde() != null) {
							produtoRefreshed.setQtdeTotal(produtoRefreshed.getQtdeTotal().add(ps.getQtde()));
						}
					}
				}
			} else {
				logger.info("PRODUTO == NULL ?????? ");
			}
			logger.info("OK");
			return produtoRefreshed;
		} catch (Exception e) {
			logger.error("Erro ao updateQtdeTotal() para " + produto.toStringToView(), e);
			throw new ViewException("Erro ao updateQtdeTotal() para " + produto.toStringToView(), e);
		}
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	public void loadLazies(List<Produto> produtos) throws ViewException {
		try {
			List<Produto> nova = new ArrayList<Produto>();
			for (Produto produto : produtos) {
				produto = getProdutoFinder().refresh(produto);
				// produto.getPrecos().size(); AGORA ESTÁ COMO EAGER
				produto.getSaldos().size();
				nova.add(produto);
			}
			produtos.clear();
			produtos.addAll(nova);
		} catch (ViewException e) {
			logger.error(e);
			throw new ViewException("Erro ao carregar lazies", e);
		}
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	public void loadLaziesAC(List<ArtigoCortina> artigosCortina) throws ViewException {
		List<Produto> produtos = new ArrayList<Produto>();
		for (ArtigoCortina ac : artigosCortina) {
			produtos.add(ac.getProduto());
		}
		loadLazies(produtos);
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

	public ArtigoCortinaFinder getArtigoCortinaFinder() {
		return artigoCortinaFinder;
	}

	public void setArtigoCortinaFinder(ArtigoCortinaFinder artigoCortinaFinder) {
		this.artigoCortinaFinder = artigoCortinaFinder;
	}

}
