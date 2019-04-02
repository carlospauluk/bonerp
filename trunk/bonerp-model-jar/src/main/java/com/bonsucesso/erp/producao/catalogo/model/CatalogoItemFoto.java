package com.bonsucesso.erp.producao.catalogo.model;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade que guarda arquivos de orçamentos.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "prod_catalogo_item_foto",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "catalogo_item_id", "nome_arquivo" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class CatalogoItemFoto extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -4571619864223417676L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "catalogo_item_id", nullable = false)
	@NotNull(message = "O campo 'Item do Catálogo' deve ser informado")
	private CatalogoItem catalogoItem;

	@Column(nullable = false, length = 300, name = "nome_arquivo")
	@Size(min = 2, max = 100, message = "'Arquivo' deve ser informado")
	private String nomeArquivo;

	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinTable(name = "prod_catalogo_fotoitemartigo", joinColumns = { @JoinColumn(name = "foto_id") },
			inverseJoinColumns = {
					@JoinColumn(name = "item_artigo_id") })
	private List<CatalogoItemArtigo> artigos;

	public CatalogoItem getCatalogoItem() {
		return catalogoItem;
	}

	public void setCatalogoItem(CatalogoItem catalogoItem) {
		this.catalogoItem = catalogoItem;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public List<CatalogoItemArtigo> getArtigos() {
		if (artigos == null) {
			artigos = new ArrayList<CatalogoItemArtigo>();
		}
		return artigos;
	}

	public void setArtigos(List<CatalogoItemArtigo> artigos) {
		this.artigos = artigos;
	}

	@Override
	public int hashCode() {
		final int prime = 997;
		final int result = 991;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(catalogoItem).append(nomeArquivo).toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		final CatalogoItemFoto iObj = (CatalogoItemFoto) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(catalogoItem, iObj.catalogoItem)
				.append(nomeArquivo, iObj.nomeArquivo)
				.isEquals();
	}

}
