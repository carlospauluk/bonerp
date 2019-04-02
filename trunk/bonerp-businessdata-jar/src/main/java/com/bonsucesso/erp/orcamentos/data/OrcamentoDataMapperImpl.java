package com.bonsucesso.erp.orcamentos.data;



import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.cortinas.data.CortinaDataMapper;
import com.bonsucesso.erp.cortinas.data.CortinaFinder;
import com.bonsucesso.erp.cortinas.model.Cortina;
import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoGrupoItem;
import com.bonsucesso.erp.orcamentos.model.TipoOrcamento;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade Orcamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("orcamentoDataMapper")
public class OrcamentoDataMapperImpl extends DataMapperImpl<Orcamento> implements OrcamentoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -6327578921600005327L;

	@Autowired
	private CortinaFinder cortinaFinder;

	@Autowired
	private CortinaDataMapper cortinaDataMapper;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Orcamento beforeSave(Orcamento orcamento) throws ViewException {

		// Se não é para conter grupos, e realmente não tem nenhum salvo ainda, cria o grupo global
		if ((orcamento.getGrupos() == null) || (orcamento.getGrupos().size() == 0)) {
			OrcamentoGrupoItem grupo = new OrcamentoGrupoItem();
			grupo.setOrcamento(orcamento);
			grupo.setTitulo("ITENS DO ORÇAMENTO");
			grupo.setOrdem(1);
			orcamento.getGrupos().add(grupo);
		}

		// se este orçamento tem itens, então ordena-os e preenche as ordens faltantes
		if ((orcamento.getGrupos() != null) && (orcamento.getGrupos().size() > 1)) {
			// gambiarra para setar a ordem dos itens automaticamente
			// na verdade, não precisa disso se for pra salvar só um item de cada vez. Aí seria mais fácil simplesmente setar a ordem do último + 1
			Collections.sort(orcamento.getGrupos(), new Comparator<OrcamentoGrupoItem>() {

				@Override
				public int compare(OrcamentoGrupoItem o1, OrcamentoGrupoItem o2) {
					return new CompareToBuilder()
							.append(o1.getOrdem(), o2.getOrdem())
							.toComparison();
				}
			});

			int ultimaOrdem = 0;
			for (OrcamentoGrupoItem grupo : orcamento.getGrupos()) {
				if (grupo.getOrdem() != null) {
					ultimaOrdem = grupo.getOrdem();
				}
			}
			ultimaOrdem++;
			for (OrcamentoGrupoItem grupo : orcamento.getGrupos()) {
				if ((grupo.getOrdem() == null) || (grupo.getOrdem() == 0)) {
					grupo.setOrdem(ultimaOrdem);
				}
				ultimaOrdem = grupo.getOrdem() + 1;
			}

		}

		if (orcamento.getGrupos() != null) {
			getEntityIdHandler().handleEntityIdFilhos(orcamento.getGrupos());
			for (OrcamentoGrupoItem grupo : orcamento.getGrupos()) {
				getEntityIdHandler().handleEntityIdFilhos(grupo.getItens());
			}

		}

		return orcamento;

	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	@Override
	public void delete(Orcamento orcamento) throws ViewException {
		try {
			if (orcamento.getTipoOrcamento().equals(TipoOrcamento.CORTINAS)) {
				List<Cortina> cortinas = getCortinaFinder().findBy(orcamento);
				for (Cortina cortina : cortinas) {
					getCortinaDataMapper().delete(cortina);
				}
			}
		} catch (Exception e) {
			throw new ViewException("Erro ao deletar cortinas do orçamento.", e);
		}
		super.delete(orcamento);
	}

	public CortinaFinder getCortinaFinder() {
		return cortinaFinder;
	}

	public void setCortinaFinder(CortinaFinder cortinaFinder) {
		this.cortinaFinder = cortinaFinder;
	}

	public CortinaDataMapper getCortinaDataMapper() {
		return cortinaDataMapper;
	}

	public void setCortinaDataMapper(CortinaDataMapper cortinaDataMapper) {
		this.cortinaDataMapper = cortinaDataMapper;
	}

}
