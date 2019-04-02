package com.bonsucesso.erp.rh.data;



import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.rh.model.FuncionarioCargo;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação Data Mapper para a entidade Funcionario.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("funcionarioDataMapper")
public class FuncionarioDataMapperImpl extends DataMapperImpl<Funcionario> implements FuncionarioDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -3324130858727920261L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Funcionario beforeSave(Funcionario funcionario) {
		
		if ((funcionario.getPessoa().getDocumento() != null)
				&& !"".equals(funcionario.getPessoa().getDocumento())) {
			if (funcionario.getPessoa().getDocumento().length() == 11) {
				funcionario.getPessoa().setTipoPessoa(TipoPessoa.PESSOA_FISICA);
			}
			String documento = funcionario.getPessoa().getDocumento();
			documento = documento.replaceAll("[^\\d.]", "");
			funcionario.getPessoa().setDocumento(documento);			
		}

		// verifica se foi adicionado outro cargo/salário e corrige a data
		if ((funcionario.getCargos() != null) && (funcionario.getCargos().size() > 0)) {

			List<FuncionarioCargo> cargos = funcionario.getCargos();

			Collections.sort(cargos, new Comparator<FuncionarioCargo>() {

				@Override
				public int compare(FuncionarioCargo o1, FuncionarioCargo o2) {
					return new CompareToBuilder()
							.append(o1.getDtInicio(), o2.getDtInicio())
							.toComparison();
				}
			});


			FuncionarioCargo fcAnterior = null;
			for (FuncionarioCargo fc : cargos) {
				if (fcAnterior != null) {
					fcAnterior.setDtFim(CalendarUtil.incDias(fc.getDtInicio(), -1));
					fcAnterior.setAtual(false);
				}
				fcAnterior = fc;
			}
			
		}
		
		getEntityIdHandler().handleEntityIdFilhos(funcionario.getCargos());
		getEntityIdHandler().handleEntityId(funcionario.getEndereco());
		getEntityIdHandler().handleEntityId(funcionario.getPessoa());
		
		return funcionario;
	}

}
