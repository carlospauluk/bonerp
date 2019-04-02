package com.bonsucesso.erp.base.view;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.TabChangeEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.Endereco;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a lista de endereços da entidade Endereço.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("enderecosFormView")
@Scope("view")
public class EnderecosFormView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Endereco> enderecos = new ArrayList<Endereco>();

	private Endereco endereco = new Endereco();

	private String enderecoRegLabel;

	private int enderecoPos;

	private boolean inserindo = false;

	public EnderecosFormView() {
		setInserindo(false);
	}

	public void onChange(TabChangeEvent event) {
		//Tab activeTab = event.getTab();
		//if ("".equals(activeTab.getId())) {
		setInserindo(false);
		//}
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public void updateEndereco(int pos) {
		enderecoPos = pos;
		updateEndereco();
	}

	public void updateEndereco() {
		if (getEnderecos().size() > enderecoPos) {
			setEndereco(getEnderecos().get(enderecoPos));
			setEnderecoRegLabel("Registro " + (enderecoPos + 1) + " de " + getEnderecos().size());
		} else {
			setEndereco(new Endereco());
			setEnderecoRegLabel("Nenhum registro");
		}
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getEnderecoRegLabel() {
		return enderecoRegLabel;
	}

	public void setEnderecoRegLabel(String enderecoRegLabel) {
		this.enderecoRegLabel = enderecoRegLabel;
	}

	public void enderecoAnterior() {
		enderecoPos = enderecoPos > 0 ? enderecoPos - 1 : enderecoPos;
		updateEndereco();
	}

	public void enderecoProximo() {
		enderecoPos = enderecoPos < (getEnderecos().size() - 1) ? enderecoPos + 1 : enderecoPos;
		updateEndereco();
	}

	public boolean isInserindo() {
		return inserindo;
	}

	public void setInserindo(boolean inserindo) {
		this.inserindo = inserindo;
	}

	public void enderecoNew() {
		setEndereco(new Endereco());
		setInserindo(true);
		setEnderecoRegLabel("Inserindo...");
	}

	public void cancelar() {
		updateEndereco(0);
		setInserindo(false);
	}

	public void deleteEndereco() {
		try {
			if (getEnderecos().contains(getEndereco())) {
				getEnderecos().remove(getEndereco());
				updateEndereco(0);
				JSFUtils.addInfoMsg("Para efetivar a deleção é necessário clicar no botão 'Salvar'");
			} else {
				throw new Exception("Erro ao deletar. Objeto não encontrado");
			}
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao processar.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}
