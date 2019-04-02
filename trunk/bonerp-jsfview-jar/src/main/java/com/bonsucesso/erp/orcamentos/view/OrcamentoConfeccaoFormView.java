package com.bonsucesso.erp.orcamentos.view;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ReorderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.Endereco;
import com.bonsucesso.erp.crm.data.ClienteFinder;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.estoque.business.CalculoPreco;
import com.bonsucesso.erp.orcamentos.data.OrcamentoDataMapper;
import com.bonsucesso.erp.orcamentos.data.OrcamentoGrupoItemDataMapper;
import com.bonsucesso.erp.orcamentos.data.OrcamentoItemDataMapper;
import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoGrupoItem;
import com.bonsucesso.erp.orcamentos.model.OrcamentoItem;
import com.bonsucesso.erp.orcamentos.model.StatusOrcamento;
import com.bonsucesso.erp.orcamentos.model.TipoOrcamento;
import com.bonsucesso.erp.producao.model.ConfeccaoPreco;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.security.spring.SecurityUtils;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * View para a entidade Orcamento (de Cortinas).
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("orcamentoConfeccaoFormView")
@Scope("view")
public class OrcamentoConfeccaoFormView extends AbstractEntityFormView<Orcamento, OrcamentoDataMapper> {

	/**
	 *
	 */
	private static final long serialVersionUID = 4122984223583988256L;

	protected static Logger logger = Logger.getLogger(OrcamentoConfeccaoFormView.class);

	@Autowired
	private ClienteFinder clienteFinder;

	@Autowired
	private OrcamentoGrupoItemDataMapper grupoDataMapper;

	@Autowired
	private OrcamentoItemDataMapper itemDataMapper;

	@Autowired
	private ConfigFinder configFinder;

	private OrcamentoGrupoItem grupo;

	private OrcamentoItem item;

	public ClienteFinder getClienteFinder() {
		return clienteFinder;
	}

	public void setClienteFinder(ClienteFinder clienteFinder) {
		this.clienteFinder = clienteFinder;
	}

	public OrcamentoGrupoItemDataMapper getGrupoDataMapper() {
		return grupoDataMapper;
	}

	public void setGrupoDataMapper(OrcamentoGrupoItemDataMapper grupoDataMapper) {
		this.grupoDataMapper = grupoDataMapper;
	}

	public OrcamentoItemDataMapper getItemDataMapper() {
		return itemDataMapper;
	}

	public void setItemDataMapper(OrcamentoItemDataMapper itemDataMapper) {
		this.itemDataMapper = itemDataMapper;
	}

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	@Override
	public void afterNovo() {
		Orcamento orcamento = new Orcamento();
		orcamento.setDtPreenchimento(new Date());
		Integer prazoValidade = 30;
		try {
			prazoValidade = Integer.valueOf(configFinder
					.findConfigByChave("bonsucesso.cortinas.prazoValidadeOrcamento").getValor());
		} catch (Exception e) {
			logger.debug("Erro ao buscar a chave: bonsucesso.cortinas.prazoValidadeOrcamento");
		}
		orcamento.setStatus(StatusOrcamento.NOVO);
		orcamento.setPreenchidoPor(SecurityUtils.getUsername());
		orcamento.setDtValidade(CalendarUtil.incDias(new Date(), prazoValidade));
		orcamento.setTipoOrcamento(TipoOrcamento.CONFECCOES);
		setE(orcamento);
	}

	public OrcamentoItem getItem() {
		if (item == null) {
			item = new OrcamentoItem();
		}
		return item;
	}

	public void setItem(OrcamentoItem item) {
		this.item = item;
	}

	public OrcamentoGrupoItem getGrupo() {
		return grupo;
	}

	public void setGrupo(OrcamentoGrupoItem grupoItem) {
		grupo = grupoItem;
	}

	public void novoGrupo() {
		OrcamentoGrupoItem grupo = new OrcamentoGrupoItem();
		setGrupo(grupo);
	}

	public void saveGrupo() {
		RequestContext context = RequestContext.getCurrentInstance();
		try {
			refreshE();
			getGrupo().setOrcamento(getE());
			setGrupo(getGrupoDataMapper().save(getGrupo()));
			refreshE();
			context.addCallbackParam("saved", "true");
		} catch (Exception e) {
			if (getGrupo().getId() == null) {
				getE().getGrupos().remove(getGrupo());
			}
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao salvar item.");
		}
		context.addCallbackParam("dlgId", "dlgGrupo");
	}

	public void deleteGrupo(OrcamentoGrupoItem grupo) {
		try {
			getE().getGrupos().remove(grupo);
			save();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void novoItem(OrcamentoGrupoItem grupo) {
		try {
			setGrupo(getGrupoDataMapper().refresh(grupo));
			OrcamentoItem item = new OrcamentoItem();
			item.setGrupo(getGrupo());
			setItem(item);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void saveItem() {
		RequestContext context = RequestContext.getCurrentInstance();
		try {
			setGrupo(getGrupoDataMapper().refresh(getItem().getGrupo()));
			getItem().setGrupo(getGrupo());
			getItemDataMapper().save(getItem());
			setGrupo(getGrupoDataMapper().refresh(getGrupo()));
			refreshE();
			context.addCallbackParam("saved", "true");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao salvar item.");
		}
		context.addCallbackParam("dlgId", "dlgItem");
	}

	public void deleteItem(OrcamentoItem item) {
		try {
			// Como é uma hierarquia, preciso dar um refresh em toda ela para poder excluir o elemento lá de baixo...
			//refreshE();
			setGrupo(getGrupoDataMapper().refresh(item.getGrupo()));
			setItem(getItemDataMapper().refresh(item));
			getGrupo().getItens().remove(getItem());
			getItemDataMapper().delete(getItem());
			setItem(null);
			setGrupo(getGrupoDataMapper().refresh(getGrupo()));
			refreshE();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void selCliente(Cliente cliente) {
		try {
			getE().setCliente(cliente);
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao selecionar o cliente.");
		}
	}

	public void selConfeccaoPreco(ConfeccaoPreco confeccaoPreco) {
		try {
			getItem()
					.setDescricao(confeccaoPreco.getConfeccao().getDescricao() + " - " + confeccaoPreco.getDescricao());
			getItem().setValorUnit(confeccaoPreco.getPrecoPrazo());
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao selecionar o cliente.");
		}
	}

	public void imprimirOrcamento() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orcamentoId", getE().getId());
			getReportBuilder().imprimir(params, "orcamentos/rpOrcamento", "Orcamento");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void gerarCabecalho() {
		StringBuilder sb = new StringBuilder("A/C ");
		try {
			if ((getE().getCliente() != null) && (getE().getCliente().getId() != null)) {
				
				Cliente cliente = getClienteFinder().refresh(getE().getCliente());
				
				if (!StringUtils.isBlank(cliente.getContato())) {
					sb.append(cliente.getContato());
					sb.append(" - ");
					sb.append(cliente.getPessoa().getNome());
				} else {
					sb.append(cliente.getPessoa().getNome());
				}
				

				if ((cliente.getEnderecos() != null) && (cliente.getEnderecos().size() > 0)) {
					Endereco endereco = cliente.getEnderecos().get(0);
					String enderecoMontado = endereco.getEnderecoMontado(true);
					if (!StringUtils.isBlank(enderecoMontado)) {
						sb.append("\r\n").append(enderecoMontado);
					}
				}
				if (!StringUtils.isBlank(cliente.getEmail())) {
					sb.append("\r\n").append(cliente.getEmail().toLowerCase());
				}
				if (!StringUtils.isBlank(cliente.getFone1())) {
					sb.append("\r\n").append(cliente.getFone1());
					if (!StringUtils.isBlank(cliente.getFone2())) {
						sb.append(" / ").append(cliente.getFone2());
					}
					if (!StringUtils.isBlank(cliente.getFone3())) {
						sb.append(" / ").append(cliente.getFone3());
					}
				}
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		getE().setCabecalho(sb.toString());

	}

	public void gerarObsPadrao() {
		try {
			getE().setObs(getConfigFinder()
					.findConfigByChave("bonsucesso.orcamentos.obs_padrao").getValor());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao setar a obs. padrão.");
		}
	}

	public void arredondarPreco(OrcamentoItem item) {
		try {
			item.setValorUnit(CalculoPreco.arredondar(item.getValorUnit()));
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao arredondar valor.");
			logger.error(e);
		}
	}

	public void clonarItem(OrcamentoItem item) {
		try {
			getItemDataMapper().save(item.clone());
			refreshE();
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao arredondar valor.");
			logger.error(e);
		}
	}

	public void onRowReorder(ReorderEvent event) {
		try {
			getGrupoDataMapper().reorderSave(getE());
			refreshE();
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao reordenar lista.");
			logger.error(e);
		}
	}

	public void clonarOrcamento() {
		try {
			Orcamento clone = getE().clonar();
			setReloadAfterSave(true);
			setE(clone);
			save();
		} catch (Exception e) {
			logger.error("Erro ao clonar o orçamento", e);
			JSFUtils.addErrorMsg("Erro ao clonar o orçamento");
			JSFUtils.addHandledException(e);
		}
	}

}
