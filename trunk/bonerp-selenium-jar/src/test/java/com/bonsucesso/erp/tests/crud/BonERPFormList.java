package com.bonsucesso.erp.tests.crud;

import java.io.File;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "formLists")
@XmlAccessorType(XmlAccessType.FIELD)
public class BonERPFormList {

	@XmlElement(name = "formList")
	private List<FormList> formLists;

	public List<FormList> getFormLists() {
		return formLists;
	}

	public void setFormLists(List<FormList> formLists) {
		this.formLists = formLists;
	}

	public static void main(String[] args) {
		try {
			JAXBContext context = JAXBContext.newInstance(BonERPFormList.class);
			Unmarshaller u = context.createUnmarshaller();
			File file = new File(
					Thread.currentThread().getContextClassLoader().getResource("xml/bonerp_formlists.xml").getFile());
			u.unmarshal(file);
		} catch (JAXBException e) {
			throw new IllegalStateException("Erro ao buscar caminhos em /bonerpmenumap.xml");
		}
	}
}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class FormList {
	private String url;

	@XmlElementWrapper(name = "camposCadastro")
	@XmlElement(name = "campoCadastro")
	private List<CampoCadastro> camposCadastro;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<CampoCadastro> getCamposCadastro() {
		return camposCadastro;
	}

	public void setCamposCadastro(List<CampoCadastro> camposCadastro) {
		this.camposCadastro = camposCadastro;
	}
}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class CampoCadastro {
	@XmlAttribute
	private String id;

	@XmlAttribute
	private String tipo;

	@XmlValue
	private String valor;
	
	/**
	 * Se o valor randomico já foi gerado
	 */
	@XmlTransient
	private boolean rand_gerado = false;
	@XmlTransient
	private int rand_tamanho = 5;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void geraRand() {
		Random random = new Random();
		setValor(String.valueOf(random.nextInt((10^rand_tamanho)-1)));
		rand_gerado = true;
	}
	
	public String getValor() {
		if (this.getTipo().equals("inteiroRand") && (! rand_gerado)) {
			if (! this.valor.isEmpty())
				rand_tamanho = Integer.parseInt(this.valor);
			this.geraRand();
		}
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}