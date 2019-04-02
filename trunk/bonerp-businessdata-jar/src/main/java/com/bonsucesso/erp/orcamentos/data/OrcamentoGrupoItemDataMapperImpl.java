package com.bonsucesso.erp.orcamentos.data;



import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoGrupoItem;
import com.bonsucesso.erp.orcamentos.model.OrcamentoItem;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade OrcamentoGrupoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("orcamentoGrupoItemDataMapper")
public class OrcamentoGrupoItemDataMapperImpl extends DataMapperImpl<OrcamentoGrupoItem> implements
		OrcamentoGrupoItemDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 3158136784212895674L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public OrcamentoGrupoItem beforeSave(OrcamentoGrupoItem grupo) throws ViewException {

		if ((grupo.getOrcamento().getGrupos() != null) && (grupo.getOrcamento().getGrupos().size() > 0)) {
			if (grupo.getOrdem() == null) {
				Collections.sort(grupo.getOrcamento().getGrupos(), new Comparator<OrcamentoGrupoItem>() {

					@Override
					public int compare(OrcamentoGrupoItem o1, OrcamentoGrupoItem o2) {
						return new CompareToBuilder()
								.append(o1.getOrdem(), o2.getOrdem())
								.toComparison();
					}
				});
				int ultimaOrdem = grupo.getOrcamento().getGrupos().get(grupo.getOrcamento().getGrupos().size() - 1)
						.getOrdem();
				grupo.setOrdem(ultimaOrdem + 1);
			}
		} else {
			grupo.setOrdem(1);
		}

		return grupo;
	}

	@Override
	//@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void reorderSave(Orcamento orcamento) throws ViewException {
		OrcamentoGrupoItemDataMapper thisProxy = (OrcamentoGrupoItemDataMapper) getBeanFactory()
				.getBean("orcamentoGrupoItemDataMapper");
		// GAMBIARRA DO SÉCULO! depois explico...
		for (OrcamentoGrupoItem grupo : orcamento.getGrupos()) {
			int ordem = 1;
			for (OrcamentoItem item : grupo.getItens()) {
				//item = refresh(item);
				item.setOrdem(ordem++ * -1);
			}
			grupo = thisProxy.save(grupo);
		}
		OrcamentoFinder orcamentoFinder = (OrcamentoFinder) getBeanFactory().getBean("orcamentoFinder");
		orcamento = orcamentoFinder.refresh(orcamento);
		for (OrcamentoGrupoItem grupo : orcamento.getGrupos()) {
			for (OrcamentoItem item : grupo.getItens()) {
				item.setOrdem(Math.abs(item.getOrdem()));
			}
			thisProxy.save(grupo);
		}
	}

}
