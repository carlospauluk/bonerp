package com.bonsucesso.erp.estoque.data;



import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade Fornecedor.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("fornecedorDataMapper")
public class FornecedorDataMapperImpl extends DataMapperImpl<Fornecedor>
		implements FornecedorDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 9023374211721656060L;

	protected static Logger logger = Logger
			.getLogger(FornecedorDataMapperImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Fornecedor beforeSave(Fornecedor fornecedor) throws ViewException {

		fornecedor.getPessoa().setTipoPessoa(TipoPessoa.PESSOA_JURIDICA);
		if ((fornecedor.getPessoa().getDocumento() != null)
				&& !"".equals(fornecedor.getPessoa().getDocumento())) {
			if (fornecedor.getPessoa().getDocumento().length() == 11) {
				fornecedor.getPessoa().setTipoPessoa(TipoPessoa.PESSOA_FISICA);
			}
			String documento = fornecedor.getPessoa().getDocumento();
			documento = documento.replaceAll("[^\\d.]", "");
			fornecedor.getPessoa().setDocumento(documento);
		}
		if (fornecedor.getCodigo() == null) {
			fornecedor.setCodigo(getProximoCodigo());
		}

		if (fornecedor.getEndereco() != null && fornecedor.getEndereco().getId() == null) {
			if (!fornecedor.getEnderecos().contains(fornecedor.getEndereco())) {
				fornecedor.getEnderecos().add(fornecedor.getEndereco());
			} else {
				logger.info("Já tem, não precisa adicionar novamente");
			}
		}

		getEntityIdHandler().handleEntityIdFilhos(fornecedor.getEnderecos());
		getEntityIdHandler().handleEntityId(fornecedor.getPessoa());

		return fornecedor;

	}

	@Override
	public Integer getProximoCodigo() {
		try {
			String hql = "SELECT max(codigo) From Fornecedor";
			Query qry = getEntityManager().createQuery(hql);
			Integer proximoCodigo = (Integer) qry.getSingleResult();
			if (proximoCodigo == null) {
				return 1;
			} else {
				return proximoCodigo + 1;
			}
		} catch (NoResultException e) {
			return 99900001;
		} catch (Exception e) {
			logger.error("Erro ao buscar próximo código", e);
			throw new IllegalStateException("Erro ao buscar próximo código");
		}
	}

}
