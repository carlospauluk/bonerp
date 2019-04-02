package com.bonsucesso.erp.producao.data;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.ConfeccaoItem;
import com.bonsucesso.erp.producao.model.ConfeccaoItemQtde;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade Confeccao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("confeccaoDataMapper")
public class ConfeccaoDataMapperImpl extends DataMapperImpl<Confeccao> implements ConfeccaoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 3015068831767594924L;

	@Autowired
	private GradeFinder gradeFinder;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Confeccao beforeSave(Confeccao confeccao) throws ViewException {
		// se não for informado o modo de cálculo, pega por padrão o do tipoArtigo
		if (confeccao.getModoCalculo() == null) {
			confeccao.setModoCalculo(confeccao.getTipoArtigo().getModoCalculo());
		}

		if (confeccao.getId() != null) {

			// Alteração de grade
			Confeccao old = getEntityManager()
					.createQuery("FROM Confeccao WHERE id = " + confeccao.getId(), Confeccao.class).getSingleResult();

			// Se alterou a grade
			if (!old.getGrade().equals(confeccao.getGrade())) {
				logger.debug("Alterando a grade");

				for (ConfeccaoItem ci : confeccao.getItens()) {
					// Lista para adicionar os ConfeccaoItemQtde que não encontrar correspondente na outra grade
					List<ConfeccaoItemQtde> aSeremRemovidos = new ArrayList<ConfeccaoItemQtde>();

					for (ConfeccaoItemQtde ciq : ci.getQtdesGrade()) {
						GradeTamanho possivelNovaGT = getGradeFinder().findByCodigoGradeAndTamanho(confeccao.getGrade()
								.getCodigo(), ciq.getGradeTamanho().getTamanho());

						// Se encontrou outro tamanho igual na outra grade, só troca
						if (possivelNovaGT != null) {
							ciq.setGradeTamanho(possivelNovaGT);
						} else {
							// se não encontrou, marca para remover
							aSeremRemovidos.add(ciq);
						}
					}
					// remove em outro for
					for (ConfeccaoItemQtde ciq : aSeremRemovidos) {
						ci.getQtdesGrade().remove(ciq);
					}

				}
			}

			// Remove ConfeccaoItemQtde's que são de outra grade
			for (ConfeccaoItem ci : confeccao.getItens()) {
				List<ConfeccaoItemQtde> aSeremRemovidos = new ArrayList<ConfeccaoItemQtde>();
				for (ConfeccaoItemQtde ciq : ci.getQtdesGrade()) {
					if (!ciq.getGradeTamanho().getGrade().equals(confeccao.getGrade())) {
						aSeremRemovidos.add(ciq);
					}
				}
				// remove em outro for
				for (ConfeccaoItemQtde ciq : aSeremRemovidos) {
					ci.getQtdesGrade().remove(ciq);
				}
			}

		}

		return super.beforeSave(confeccao);
	}

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

}
