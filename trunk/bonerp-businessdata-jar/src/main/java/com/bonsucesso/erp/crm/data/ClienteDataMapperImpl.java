package com.bonsucesso.erp.crm.data;



import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.crm.model.Cliente;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade Cliente.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("clienteDataMapper")
public class ClienteDataMapperImpl extends DataMapperImpl<Cliente> implements ClienteDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -6083385810404670790L;

	protected static Logger logger = Logger.getLogger(ClienteDataMapperImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Cliente beforeSave(Cliente cliente) {
		
		if ((cliente.getPessoa().getDocumento() != null)
				&& !"".equals(cliente.getPessoa().getDocumento())) {
			if (cliente.getPessoa().getDocumento().length() == 11) {
				cliente.getPessoa().setTipoPessoa(TipoPessoa.PESSOA_FISICA);
			}
			String documento = cliente.getPessoa().getDocumento();
			documento = documento.replaceAll("[^\\d.]", "");
			cliente.getPessoa().setDocumento(documento);			
		}
		
		if (cliente.getCodigo() == null) {
			Integer proximoCodigo = getProximoCodigo();
			cliente.setCodigo(proximoCodigo);
		}
		
		if (cliente.getEndereco().getId() == null) {
			cliente.getEnderecos().add(cliente.getEndereco());
		}

		getEntityIdHandler().handleEntityIdFilhos(cliente.getEnderecos());
		getEntityIdHandler().handleEntityId(cliente.getPessoa());

		return cliente;
	}

	@Override
	public Integer getProximoCodigo() {
		try {
			String hql = "SELECT max(codigo) From Cliente WHERE codigo > 99900000";
			Query qry = getEntityManager().createQuery(hql);
			Integer proximoCodigo = (Integer) qry.getSingleResult();
			if (proximoCodigo == null) {
				return 99900001;
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

	@Override
	public String getRole() {
		return "CRM";
	}

}
