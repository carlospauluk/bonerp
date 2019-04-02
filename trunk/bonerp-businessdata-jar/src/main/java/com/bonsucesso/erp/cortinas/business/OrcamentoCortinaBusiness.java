package com.bonsucesso.erp.cortinas.business;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.cortinas.data.CortinaDataMapper;
import com.bonsucesso.erp.cortinas.data.CortinaFinder;
import com.bonsucesso.erp.cortinas.model.Cortina;
import com.bonsucesso.erp.cortinas.model.CortinaItem;
import com.bonsucesso.erp.cortinas.model.CortinaLado;
import com.bonsucesso.erp.orcamentos.data.OrcamentoDataMapper;
import com.bonsucesso.erp.orcamentos.data.OrcamentoItemDataMapper;
import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoGrupoItem;
import com.bonsucesso.erp.orcamentos.model.OrcamentoItem;
import com.ocabit.base.data.EntityIdHandler;
import com.ocabit.base.view.exception.ViewException;


@Component("orcamentoCortinaBusiness")
public class OrcamentoCortinaBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2949503069395981447L;

	@Autowired
	private CortinaFinder cortinaFinder;

	@Autowired
	private EntityIdHandler entityIdHandler;

	@Autowired
	private OrcamentoDataMapper orcamentoDataMapper;

	@Autowired
	private OrcamentoItemDataMapper orcamentoItemDataMapper;

	@Autowired
	private CortinaDataMapper cortinaDataMapper;

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public Orcamento clonarOrcamentoCortina(Orcamento orcamentoCortina) throws ViewException {

		Orcamento clone = SerializationUtils.clone(orcamentoCortina);
		clone.setId(null);
		clone.setGrupos(null);
		clone.setIudt(null);
		clone.setUserInfo(null);
		getEntityIdHandler().handleEntityIdInserting(clone);

		Map<OrcamentoItem, OrcamentoItem> mapOi2Oi = new HashMap<OrcamentoItem, OrcamentoItem>();

		for (OrcamentoGrupoItem grupo : orcamentoCortina.getGrupos()) {

			OrcamentoGrupoItem grupoClone = SerializationUtils.clone(grupo);
			grupoClone.setOrcamento(clone);
			grupoClone.setId(null);
			grupoClone.setIudt(null);
			grupoClone.setUserInfo(null);
			getEntityIdHandler().handleEntityIdInserting(grupoClone);

			clone.getGrupos().add(grupoClone);

			grupoClone.setItens(null);

			for (OrcamentoItem item : grupo.getItens()) {
				OrcamentoItem itemClone = SerializationUtils.clone(item);
				mapOi2Oi.put(itemClone, item);
				itemClone.setIudt(null);
				itemClone.setUserInfo(null);
				getEntityIdHandler().handleEntityIdInserting(itemClone);

				itemClone.setGrupo(grupoClone);
				itemClone.setId(null);
				grupoClone.getItens().add(itemClone);
			}
		}

		clone = getOrcamentoDataMapper().save(clone);

		for (OrcamentoGrupoItem grupo : clone.getGrupos()) {

			for (OrcamentoItem oi : grupo.getItens()) {

				Cortina cortina;
				try {
					cortina = getCortinaFinder().findBy(mapOi2Oi.get(oi));
					cortina.getItens().size(); // touch
					cortina.getLargurasLados().size(); // touch
				} catch (Exception e) {
					throw new ViewException("Erro ao pesquisar cortina pelo item do orçamento.", e);
				}

				Cortina cortinaClone = SerializationUtils.clone(cortina);

				cortinaClone.setItens(null);

				for (CortinaItem ci : cortina.getItens()) {
					CortinaItem ciClone = SerializationUtils.clone(ci);
					ciClone.setCortina(cortinaClone);
					ciClone.setId(null);
					ciClone.setIudt(null);
					ciClone.setUserInfo(null);
					getEntityIdHandler().handleEntityIdInserting(ciClone);
					cortinaClone.getItens().add(ciClone);
				}

				cortinaClone.setLargurasLados(null);
				for (CortinaLado lado : cortina.getLargurasLados()) {
					CortinaLado ladoClone = SerializationUtils.clone(lado);
					ladoClone.setCortina(cortinaClone);
					ladoClone.setId(null);
					ladoClone.setIudt(null);
					ladoClone.setUserInfo(null);
					getEntityIdHandler().handleEntityIdInserting(ladoClone);
					cortinaClone.getLargurasLados().add(ladoClone);
				}

				cortinaClone.getLargurasLados().size();
				cortinaClone.setId(null);
				cortinaClone.setIudt(null);
				cortinaClone.setUserInfo(null);
				cortinaClone.setOrcamentoItem(oi);
				getEntityIdHandler().handleEntityIdInserting(cortinaClone);

				cortinaClone = getCortinaDataMapper().save(cortinaClone);

			}
		}
		return clone;

	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public Cortina clonarCortina(Cortina cortina) throws ViewException {

		try {
			OrcamentoItem oiClone = SerializationUtils.clone(cortina.getOrcamentoItem());
			oiClone.setId(null);
			oiClone.setIudt(null);
			oiClone.setUserInfo(null);
			getEntityIdHandler().handleEntityIdInserting(oiClone);

			oiClone = getOrcamentoItemDataMapper().save(oiClone);

			Cortina cortinaClone = SerializationUtils.clone(cortina);

			cortinaClone.setItens(null);

			for (CortinaItem ci : cortina.getItens()) {
				CortinaItem ciClone = SerializationUtils.clone(ci);
				ciClone.setCortina(cortinaClone);
				ciClone.setId(null);
				ciClone.setIudt(null);
				ciClone.setUserInfo(null);
				getEntityIdHandler().handleEntityIdInserting(ciClone);
				cortinaClone.getItens().add(ciClone);
			}

			cortinaClone.setLargurasLados(null);
			for (CortinaLado lado : cortina.getLargurasLados()) {
				CortinaLado ladoClone = SerializationUtils.clone(lado);
				ladoClone.setCortina(cortinaClone);
				ladoClone.setId(null);
				ladoClone.setIudt(null);
				ladoClone.setUserInfo(null);
				getEntityIdHandler().handleEntityIdInserting(ladoClone);
				cortinaClone.getLargurasLados().add(ladoClone);
			}

			cortinaClone.getLargurasLados().size();
			cortinaClone.setId(null);
			cortinaClone.setIudt(null);
			cortinaClone.setUserInfo(null);
			cortinaClone.setOrcamentoItem(oiClone);
			getEntityIdHandler().handleEntityIdInserting(cortinaClone);

			cortinaClone = getCortinaDataMapper().save(cortinaClone);

			return cortinaClone;
		} catch (Exception e) {
			throw new ViewException("Erro ao clonar cortina", e);
		}

	}

	public CortinaFinder getCortinaFinder() {
		return cortinaFinder;
	}

	public void setCortinaFinder(CortinaFinder cortinaFinder) {
		this.cortinaFinder = cortinaFinder;
	}

	public EntityIdHandler getEntityIdHandler() {
		return entityIdHandler;
	}

	public void setEntityIdHandler(EntityIdHandler entityIdHandler) {
		this.entityIdHandler = entityIdHandler;
	}

	public OrcamentoDataMapper getOrcamentoDataMapper() {
		return orcamentoDataMapper;
	}

	public void setOrcamentoDataMapper(OrcamentoDataMapper orcamentoDataMapper) {
		this.orcamentoDataMapper = orcamentoDataMapper;
	}

	public CortinaDataMapper getCortinaDataMapper() {
		return cortinaDataMapper;
	}

	public void setCortinaDataMapper(CortinaDataMapper cortinaDataMapper) {
		this.cortinaDataMapper = cortinaDataMapper;
	}

	public OrcamentoItemDataMapper getOrcamentoItemDataMapper() {
		return orcamentoItemDataMapper;
	}

	public void setOrcamentoItemDataMapper(OrcamentoItemDataMapper orcamentoItemDataMapper) {
		this.orcamentoItemDataMapper = orcamentoItemDataMapper;
	}

}
