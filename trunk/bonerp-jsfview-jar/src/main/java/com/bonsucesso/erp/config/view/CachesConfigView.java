package com.bonsucesso.erp.config.view;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ocabit.base.data.DatatableDataMapper;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.StoredViewInfoDataMapper;
import com.ocabit.jsf.utils.JSFUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;


@Component("cachesConfigView")
@Scope("view")
public class CachesConfigView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1872754323860826724L;

	protected static Logger logger = Logger.getLogger(CachesConfigView.class);

	private List<Cache> cachesList;

	private List<String> selecteds;

	@Autowired
	private DatatableDataMapper datatableDataMapper;

	@Autowired
	private StoredViewInfoDataMapper storedViewInfoDataMapper;

	/**
	 * Atualiza a lista de caches.
	 */
	public void updateCachesList() {
		List<Cache> cachesList = new ArrayList<Cache>();

		CacheManager manager = CacheManager.getInstance();

		for (String cacheName : manager.getCacheNames()) {
			cachesList.add(manager.getCache(cacheName));
		}

		Collections.sort(cachesList, new Comparator<Cache>() {

			@Override
			public int compare(Cache o1, Cache o2) {
				return new CompareToBuilder()
						.append(o1.getName(), o2.getName())
						.toComparison();
			}
		});

		setCachesList(cachesList);
	}

	/**
	 *
	 */
	public void apagarCaches() {
		try {
			for (Cache cache : getCachesList()) {
				apagarCache(cache.getName());
			}
			JSFUtils.addInfoMsg("Cachês apagados com sucesso.");
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao apagar cachês.");
		}
	}

	public void apagarCache(String nome) {
		CacheManager manager = CacheManager.getInstance();
		manager.getCache(nome).removeAll();
		setCachesList(null);
	}

	public List<Cache> getCachesList() {
		if (cachesList == null) {
			updateCachesList();
		}
		return cachesList;
	}

	public void setCachesList(List<Cache> cachesList) {
		this.cachesList = cachesList;
	}

	public List<String> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<String> selecteds) {
		this.selecteds = selecteds;
	}

	public DatatableDataMapper getDatatableDataMapper() {
		return datatableDataMapper;
	}

	public void setDatatableDataMapper(DatatableDataMapper datatableDataMapper) {
		this.datatableDataMapper = datatableDataMapper;
	}

	public StoredViewInfoDataMapper getStoredViewInfoDataMapper() {
		return storedViewInfoDataMapper;
	}

	public void setStoredViewInfoDataMapper(StoredViewInfoDataMapper storedViewInfoDataMapper) {
		this.storedViewInfoDataMapper = storedViewInfoDataMapper;
	}

	public void apagarDatatables() {
		try {
			getDatatableDataMapper().deleteAllUserDatatables();
			JSFUtils.addInfoMsg("Registros deletados com sucesso.");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void apagarViewInfos() {
		try {
			getStoredViewInfoDataMapper().deleteAllUserViewInfos();
			JSFUtils.addInfoMsg("Registros deletados com sucesso.");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

}
