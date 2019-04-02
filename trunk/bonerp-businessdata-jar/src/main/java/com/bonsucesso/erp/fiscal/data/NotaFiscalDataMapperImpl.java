package com.bonsucesso.erp.fiscal.data;



import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.data.PessoaDataMapper;
import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.fiscal.model.FinalidadeNF;
import com.bonsucesso.erp.fiscal.model.ModalidadeFrete;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.NotaFiscalItem;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade <b>NotaFiscal.</b>
 * 
 * @author Carlos Eduardo Pauluk
 * 
 * 
 */
@Repository("notaFiscalDataMapper")
public class NotaFiscalDataMapperImpl extends DataMapperImpl<NotaFiscal> implements
		NotaFiscalDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -147402556678399972L;

	@Autowired
	private PessoaDataMapper PessoaDataMapper;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public NotaFiscal beforeSave(NotaFiscal nfe) throws ViewException {

		if (nfe.getFinalidadeNF() == null) {
			nfe.setFinalidadeNF(FinalidadeNF.NORMAL);
		}

		if (nfe.getId() == null) {
			nfe.setSpartacusStatus(0);
			nfe.setSpartacusMensRetornoReceita("NÃO FATURADO");
		}

		if (nfe.getNaturezaOperacao() == null) {
			nfe.setNaturezaOperacao("VENDA");
		}

		if (nfe.getTotalDescontos() == null) {
			nfe.setTotalDescontos(BigDecimal.ZERO);
		}

		if (nfe.getTranspModalidadeFrete().equals(ModalidadeFrete.SEM_FRETE)) {
			nfe.setTranspEspecieVolumes(null);
			nfe.setTranspFornecedor(null);
			nfe.setTranspMarcaVolumes(null);
			nfe.setTranspNumeracaoVolumes(null);
			nfe.setTranspPesoBruto(null);
			nfe.setTranspPesoLiquido(null);
			nfe.setTranspQtdeVolumes(null);
		}

		if (nfe.getPessoaEmitente().getId() == null) {
			getEntityIdHandler().handleEntityIdInserting(nfe.getPessoaEmitente());
		} else {
			getEntityIdHandler().handleEntityIdUpdating(nfe.getPessoaEmitente());
		}

		if (nfe.getId() == null) {
			getEntityIdHandler().handleEntityIdFilhosInserting(nfe.getItens());
		} else {
			getEntityIdHandler().handleEntityIdFilhosUpdating(nfe.getItens());
		}

		Pessoa f = getPessoaDataMapper().save(nfe.getPessoaEmitente());
		nfe.setPessoaEmitente(f);

		if (nfe.getItens() != null && !nfe.getItens().isEmpty()) {

			Collections.sort(nfe.getItens(), new Comparator<NotaFiscalItem>() {

				@Override
				public int compare(NotaFiscalItem o1, NotaFiscalItem o2) {
					return o1.getOrdem().compareTo(o2.getOrdem());
				}
			});

			Integer ultimaOrdem = 0;

			for (NotaFiscalItem item : nfe.getItens()) {
				item.setOrdem(++ultimaOrdem);
			}
		}

		return nfe;

	}

	public PessoaDataMapper getPessoaDataMapper() {
		return PessoaDataMapper;
	}

	public void setPessoaDataMapper(PessoaDataMapper PessoaDataMapper) {
		this.PessoaDataMapper = PessoaDataMapper;
	}

}
