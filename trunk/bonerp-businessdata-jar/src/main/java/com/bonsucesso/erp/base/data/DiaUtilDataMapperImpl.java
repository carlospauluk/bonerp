package com.bonsucesso.erp.base.data;



import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.DiaUtil;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade DiaUtil.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("diaUtilDataMapper")
public class DiaUtilDataMapperImpl extends DataMapperImpl<DiaUtil> implements DiaUtilDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -2561121490738182099L;

	protected static Logger logger = Logger.getLogger(DiaUtilDataMapperImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public void saveList_diasUteisComerciais(List<DiaUtil> lista, boolean comercial) throws ViewException {

		logger.info("Iniciando save em lote ...");

		for (DiaUtil d : lista) {
			d.setComercial(true);
			d.setFinanceiro(true);
			((DiaUtilDataMapper) getBeanFactory().getBean("diaUtilDataMapper")).save(d);
		}
		logger.info("Finalizando save em lote com SUCESSO");
	}

}
