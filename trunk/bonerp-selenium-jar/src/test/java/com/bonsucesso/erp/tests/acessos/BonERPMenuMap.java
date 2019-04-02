package com.bonsucesso.erp.tests.acessos;



import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BonERPMenuMap {

	@XmlElement(name = "itemMenu")
	private List<ItemMenu> itensMenu;

	public List<ItemMenu> getItensMenu() {
		return itensMenu;
	}

	public void setItensMenu(List<ItemMenu> itensMenu) {
		this.itensMenu = itensMenu;
	}

}



@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class ItemMenu {

	@XmlElementWrapper(name = "caminhos")
	@XmlElement(name = "caminho")
	private String[] caminhos;

	private String result;

	/**
	 *
	 */
	public ItemMenu() {
		super();
	}

	/**
	 * @param caminhos
	 * @param result
	 */
	public ItemMenu(String[] caminhos, String result) {
		super();
		this.caminhos = caminhos;
		this.result = result;
	}

	public String[] getCaminhos() {
		return caminhos;
	}

	public void setCaminhos(String[] caminhos) {
		this.caminhos = caminhos;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
