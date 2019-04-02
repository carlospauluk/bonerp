package com.bonsucesso.erp.cortinas.data;



import java.math.BigDecimal;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.cortinas.model.CortinaItem;
import com.bonsucesso.erp.cortinas.model.OrientacaoTecido;
import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade Cortina.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("cortinaItemDataMapper")
public class CortinaItemDataMapperImpl extends DataMapperImpl<CortinaItem> implements CortinaItemDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 2059649963764334952L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public CortinaItem beforeSave(CortinaItem cortinaItem) {
		
		
		if (cortinaItem.getQtde() == null) {
			cortinaItem.setQtde(BigDecimal.ZERO);
		}

		if (cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)) {
			if (cortinaItem.getArtigoCortina().getTecido() == null) {
				throw new IllegalStateException("Tecido deve ser informado.");
			}
			if (cortinaItem.getAlturaBarra() == null) {
				throw new IllegalStateException("O campo 'Altura Barra' deve ser informado.");
			}
			if (cortinaItem.getOrientacao() == null) {
				throw new IllegalStateException("O campo 'Tecido' deve ser informado.");
			}
			if (cortinaItem.getDrapeado() == false) {
				if ((cortinaItem.getTipoFixacao() == null) ||
						(!cortinaItem.getTipoFixacao().equals(TipoArtigoCortina.NULLED) &&
								!cortinaItem.getTipoFixacao().equals(TipoArtigoCortina.RODIZIO) &&
								!cortinaItem.getTipoFixacao().equals(TipoArtigoCortina.ARGOLA) &&
								!cortinaItem.getTipoFixacao().equals(TipoArtigoCortina.ILHOS))) {
					throw new IllegalStateException("O campo 'Tipo Fixação' deve ser informado (ARGOLA, ILHÓS ou RODÍZIO).");
				}
			}
			if (cortinaItem.getDrapeado() == false && cortinaItem.getBando() == false) {
				if ((cortinaItem.getFator() == null) || (cortinaItem.getFator().doubleValue() < 1)) {
					throw new IllegalStateException("O campo 'Fator' deve ser informado.");
				}
			}
		}

		if (cortinaItem.getArtigoCortina().getTipoArtigoCortina()
				.equals(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_TECIDO)
				||
				cortinaItem.getArtigoCortina().getTipoArtigoCortina()
						.equals(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_DETALHES)
				||
				cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.MAO_DE_OBRA_ILHOS)
				||
				cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.MAO_DE_OBRA_INSTALACAO)
				||
				cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.SUPORTE_SIMPLES) ||
				cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.SUPORTE_DUPLO) ||
				cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.SUPORTE_TRIPLO)) {
			cortinaItem.setCamada(999);
		}

		if (cortinaItem.getPrecoPrazo() == null) {
			cortinaItem.setPrecoPrazo(cortinaItem.getArtigoCortina().getProduto().getPrecoAtual().getPrecoPrazo());
		}
		if (cortinaItem.getPrecoPromo() == null) {
			cortinaItem.setPrecoPromo(cortinaItem.getArtigoCortina().getProduto().getPrecoAtual().getPrecoPromo());
		}
		if (cortinaItem.getPrecoVista() == null) {
			cortinaItem.setPrecoVista(cortinaItem.getArtigoCortina().getProduto().getPrecoAtual().getPrecoVista());
		}

		if (cortinaItem.getFatorReal() == null ||
				(cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO) &&
						cortinaItem.getOrientacao().equals(OrientacaoTecido.HORIZONTAL))) {
			// para tecidos utilizados na orientação horizontal o fatorReal é o mesmo do fator 'bruto' selecionado na tela.
			cortinaItem.setFatorReal(cortinaItem.getFator());
		}

		return cortinaItem;
	}

}
