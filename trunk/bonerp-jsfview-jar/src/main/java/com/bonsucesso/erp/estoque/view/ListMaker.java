package com.bonsucesso.erp.estoque.view;



import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.data.DeptoFinder;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.data.TipoFornecedorFinder;
import com.bonsucesso.erp.estoque.data.UnidadeProdutoFinder;
import com.bonsucesso.erp.estoque.model.Departamento;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.estoque.model.TipoFornecedor;
import com.bonsucesso.erp.estoque.model.UnidadeProduto;
import com.ocabit.base.view.AbstractListMaker;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListMaker para as entidades do pacote com.bonsucesso.erp.estoque.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("lmEstoque")
@Scope("view")
public class ListMaker implements AbstractListMaker {

	/**
	 *
	 */
	private static final long serialVersionUID = -8951450898460378808L;

	protected static Logger logger = Logger.getLogger(ListMaker.class);

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private TipoFornecedorFinder tipoFornecedorFinder;

	@Autowired
	private DeptoFinder deptoFinder;

	@Autowired
	private SubdeptoFinder subdeptoFinder;

	@Autowired
	private GradeFinder gradeFinder;

	@Autowired
	private UnidadeProdutoFinder unidadeProdutoFinder;

	private List<UnidadeProduto> unidadesProduto;

	private List<Grade> grades;

	private List<Departamento> deptos;

	private List<Subdepartamento> subdeptos;

	private List<TipoFornecedor> tiposFornecedor;

	private Departamento depto;

	private UnidadeProduto unidadeProduto;

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	public TipoFornecedorFinder getTipoFornecedorFinder() {
		return tipoFornecedorFinder;
	}

	public void setTipoFornecedorFinder(TipoFornecedorFinder tipoFornecedorFinder) {
		this.tipoFornecedorFinder = tipoFornecedorFinder;
	}

	public List<Grade> getGrades() {
		try {
			grades = gradeFinder.findAll("codigo");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW,
			rollbackFor = { RuntimeException.class, ViewException.class })
	public List<Departamento> getDeptos() {
		try {
			deptos = deptoFinder.findAll("nome");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return deptos;
	}

	public void setDeptos(List<Departamento> deptos) {
		this.deptos = deptos;
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW,
			rollbackFor = { RuntimeException.class, ViewException.class })
	public List<Subdepartamento> getSubdeptos() {
		try {
			if (subdeptos == null) {
				subdeptos = subdeptoFinder.findAll("codigo");
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return subdeptos;
	}

	public void setSubdeptos(List<Subdepartamento> subdeptos) {
		this.subdeptos = subdeptos;
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW,
			rollbackFor = { RuntimeException.class, ViewException.class })
	public List<TipoFornecedor> getTiposFornecedor() {
		try {
			if (tiposFornecedor == null) {
				tiposFornecedor = getTipoFornecedorFinder().findAll("descricao");
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return tiposFornecedor;
	}

	public void setTiposFornecedor(List<TipoFornecedor> tiposFornecedor) {
		this.tiposFornecedor = tiposFornecedor;
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<UnidadeProduto> getUnidadesProduto() {
		try {
			if (unidadesProduto == null) {
				unidadesProduto = unidadeProdutoFinder.findAll("label");
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return unidadesProduto;
	}

	public UnidadeProduto getUnidadeProduto() {
		return unidadeProduto;
	}

	public void setUnidadeProduto(UnidadeProduto unidadeProduto) {
		this.unidadeProduto = unidadeProduto;
	}

	public DeptoFinder getDeptoFinder() {
		return deptoFinder;
	}

	public void setDeptoFinder(DeptoFinder deptoFinder) {
		this.deptoFinder = deptoFinder;
	}

	public SubdeptoFinder getSubdeptoFinder() {
		return subdeptoFinder;
	}

	public void setSubdeptoFinder(SubdeptoFinder subdeptoFinder) {
		this.subdeptoFinder = subdeptoFinder;
	}

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

	public void setUnidadesProduto(List<UnidadeProduto> unidadesProduto) {
		this.unidadesProduto = unidadesProduto;
	}

	public UnidadeProdutoFinder getUnidadeProdutoFinder() {
		return unidadeProdutoFinder;
	}

	public void setUnidadeProdutoFinder(UnidadeProdutoFinder unidadeProdutoFinder) {
		this.unidadeProdutoFinder = unidadeProdutoFinder;
	}

	public List<Fornecedor> acFornecedoresPorNome(String nome) {
		try {
			return getFornecedorFinder().findByStr(nome);
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			return null;
		}
	}

	public Departamento getDepto() {
		return depto;
	}

	public void setDepto(Departamento depto) {
		try {
			if ((depto != null) && (depto.getId() != null)) {
				depto = getDeptoFinder().refresh(depto);
				// touch para lazy
				depto.getSubdeptos().size();
			}
			this.depto = depto;
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public List<Fornecedor> findFornecedoresBy(String str) {
		try {
			return getFornecedorFinder().findByStr(str);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar pessoa.");
			return null;
		}
	}

}
