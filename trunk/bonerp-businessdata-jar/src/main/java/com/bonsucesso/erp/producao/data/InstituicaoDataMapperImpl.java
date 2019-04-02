package com.bonsucesso.erp.producao.data;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.crm.data.ClienteDataMapper;
import com.bonsucesso.erp.estoque.data.FornecedorDataMapper;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade Instituicao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("instituicaoDataMapper")
public class InstituicaoDataMapperImpl extends DataMapperImpl<Instituicao> implements InstituicaoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 6081074907904803666L;

	@Autowired
	private InstituicaoFinder instituicaoFinder;

	@Autowired
	private ClienteDataMapper clienteDataMapper;

	@Autowired
	private FornecedorDataMapper fornecedorDataMapper;

	public InstituicaoFinder getInstituicaoFinder() {
		return instituicaoFinder;
	}

	public void setInstituicaoFinder(InstituicaoFinder instituicaoFinder) {
		this.instituicaoFinder = instituicaoFinder;
	}

	public ClienteDataMapper getClienteDataMapper() {
		return clienteDataMapper;
	}

	public void setClienteDataMapper(ClienteDataMapper clienteDataMapper) {
		this.clienteDataMapper = clienteDataMapper;
	}

	public FornecedorDataMapper getFornecedorDataMapper() {
		return fornecedorDataMapper;
	}

	public void setFornecedorDataMapper(FornecedorDataMapper fornecedorDataMapper) {
		this.fornecedorDataMapper = fornecedorDataMapper;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Instituicao beforeSave(Instituicao instituicao) throws ViewException {
		if (instituicao.getCodigo() == null) {
			instituicao.setCodigo(getInstituicaoFinder().findNextCodigo());
		}
		if ((instituicao.getCliente() != null) && (instituicao.getCliente().getCodigo() == null)) {
			instituicao.getCliente().setCodigo(getClienteDataMapper().getProximoCodigo());
			getEntityIdHandler().handleEntityId(instituicao.getCliente());
			getEntityIdHandler().handleEntityId(instituicao.getCliente().getPessoa());
		}
		if ((instituicao.getFornecedor() != null) && (instituicao.getFornecedor().getCodigo() == null)) {
			instituicao.getFornecedor().setCodigo(getFornecedorDataMapper().getProximoCodigo());
			getEntityIdHandler().handleEntityId(instituicao.getFornecedor());
			getEntityIdHandler().handleEntityId(instituicao.getFornecedor().getPessoa());
		}
		return instituicao;
	}

}
