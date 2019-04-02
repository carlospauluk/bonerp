package com.bonsucesso.erp.config.view;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.config.data.ConfigDataMapper;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.config.model.Config;


@Component("configFormListView")
@Scope("view")
public class ConfigFormListView extends
		AbstractEntityFormListView<Config, ConfigDataMapper, ConfigFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -1691021035894808751L;

}
