package com.bonsucesso.erp.cortinas.data;



import java.security.InvalidParameterException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.data.ProdutoDataMapper;
import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.strings.StringUtils;


/**
 * Implementação Data Mapper para a entidade ArtigoCortina.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("artigoCortinaDataMapper")
public class ArtigoCortinaDataMapperImpl extends DataMapperImpl<ArtigoCortina> implements ArtigoCortinaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -3706862599005881359L;

	protected static Logger logger = Logger.getLogger(ArtigoCortinaDataMapperImpl.class);

	@Autowired
	private GradeFinder gradeFinder;

	@Autowired
	private ProdutoDataMapper produtoDataMapper;

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

	public ProdutoDataMapper getProdutoDataMapper() {
		return produtoDataMapper;
	}

	public void setProdutoDataMapper(ProdutoDataMapper produtoDataMapper) {
		this.produtoDataMapper = produtoDataMapper;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public ArtigoCortina beforeSave(ArtigoCortina artigoCortina) throws ViewException {
		
		artigoCortina.getProduto().getPrecos().size();
		artigoCortina.getProduto().getSaldos().size();
		
		Produto produto = getProdutoDataMapper().save(artigoCortina.getProduto());
		produto.getSaldos().size();
		produto.getPrecos().size();
		artigoCortina.setProduto(produto);
		
		getEntityIdHandler().handleEntityId(artigoCortina.getProduto());
		getEntityIdHandler().handleEntityId(artigoCortina.getTecido());
		return artigoCortina;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public ArtigoCortina saveComPreco(ArtigoCortina artigoCortina, ProdutoPreco produtoPreco) throws ViewException {
		if ((artigoCortina == null) || (produtoPreco == null)) {
			throw new InvalidParameterException("parâmetros não podem ser null");
		}

		if (artigoCortina.getProduto() == null) {
			throw new ViewException("Produto não informado.");
		}

		if (!TipoArtigoCortina.TECIDO.equals(artigoCortina.getTipoArtigoCortina())) {
			artigoCortina.setTecido(null);
		}

		if (artigoCortina.getProduto().getGrade() == null) {
			switch (artigoCortina.getTipoArtigoCortina()) {
				case ENTRETELA:
				case TECIDO:
				case TRILHO:
				case VARAO: {
					Grade grade = getGradeFinder().findByCodigo(10);
					artigoCortina.getProduto().setGrade(grade);
					artigoCortina.getProduto().setUnidadeProduto(grade.getUnidadeProdutoPadrao());
					break;
				}
				default:
					Grade grade = getGradeFinder().findByCodigo(11);
					artigoCortina.getProduto().setGrade(grade);
					artigoCortina.getProduto().setUnidadeProduto(grade.getUnidadeProdutoPadrao());
			}
		}
		if ((artigoCortina.getProduto().getReferencia() == null)
				|| "".equals(artigoCortina.getProduto().getReferencia())) {
			String referencia = StringUtils
					.zerofill(artigoCortina.getProduto().getFornecedor().getCodigo().toString(), 3) + "XXX";
			artigoCortina.getProduto().setReferencia(referencia);
		}

		Produto produto = getProdutoDataMapper().saveComPreco(artigoCortina.getProduto(), produtoPreco);
		artigoCortina.setProduto(produto);

		ArtigoCortina ac = getThiz().save(artigoCortina);
		getEntityManager().flush();
		return ac;

	}

}
