package com.bonsucesso.erp.financeiro.view;



import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.CategoriaDataMapper;
import com.bonsucesso.erp.financeiro.data.CategoriaFinder;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.exception.ViewException;


/**
 * View para a entidade Categoria.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("categoriaFormListView")
@Scope("view")
public final class CategoriaFormListView extends
		AbstractEntityFormView<Categoria, CategoriaDataMapper> {

	/**
	 *
	 */
	private static final long serialVersionUID = 7432266686301900444L;

	private String codigoSufixo;

	@Autowired
	private CategoriaFinder finder;

	private TreeNode arvoreCategorias = new DefaultTreeNode();

	public CategoriaFinder getFinder() {
		return finder;
	}

	public void setFinder(CategoriaFinder finder) {
		this.finder = finder;
	}

	public TreeNode getArvoreCategorias() {
		return arvoreCategorias;
	}

	public void setArvoreCategorias(TreeNode root) {
		arvoreCategorias = root;
	}

	public void carregarArvore() {
		try {
			setArvoreCategorias(new DefaultTreeNode());
			addFilhos(null, getArvoreCategorias());
		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addFilhos(Categoria pai, TreeNode nodePai) throws ViewException {
		List<Categoria> pais = getFinder().findBy(pai);
		for (Categoria p : pais) {
			TreeNode filho = new DefaultTreeNode(p, nodePai);
			filho.setExpanded(true);
			if ((p.getSubCategs() != null) && (p.getSubCategs().size() > 0)) {
				addFilhos(p, filho);
			}
		}
	}

	public String getCodigoSufixo() {
		return codigoSufixo;
	}

	public void setCodigoSufixo(String codigoSufixo) {
		this.codigoSufixo = codigoSufixo;
	}

	@Override
	public void afterSetE(Categoria e) {
		setCodigoSufixo(e.getCodigoSufixo());
	}

	public void novoFilho(Categoria e) {
		novo();
		getE().setPai(e);
	}

	@Override
	public void validate() throws ViewException {
		try {
			Long codigoMontado = Long.parseLong(getE().getPai().getCodigo().toString() + getCodigoSufixo());
			getE().setCodigo(codigoMontado);
			getE().getCodigoM(); // tenta montar pra ver se está no padrão correto
		} catch (NumberFormatException e) {
			throw new ViewException("Erro ao montar código da subcategoria");
		}
	}

	@Override
	public void afterDelete() {
		setArvoreCategorias(null); // seta null para recarregar
	}

}
