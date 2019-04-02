package com.bonsucesso.erp.producao.catalogo.model;



import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
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

import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade que representa os artigos de um item do catálogo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "prod_catalogo_item_artigo",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "catalogo_item_id", "tipo_artigo_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class CatalogoItemArtigo extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 2356611608296305843L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "catalogo_item_id", nullable = false)
	@NotNull(message = "O campo 'Item' deve ser informado")
	private CatalogoItem catalogoItem;

	@ManyToOne(optional = false)
	@JoinColumn(name = "tipo_artigo_id", nullable = false)
	@NotNull(message = "O campo 'Tipo Artigo' deve ser informado")
	private TipoArtigo tipoArtigo;

	@Column(nullable = false, length = 2000)
	@Size(min = 2, max = 2000, message = "'Descrição' deve possuir entre 2 e 2000 caracteres")
	private String descricao;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@ManyToMany(mappedBy = "artigos")
	private List<CatalogoItemFoto> fotos;

	public CatalogoItem getCatalogoItem() {
		return catalogoItem;
	}

	public void setCatalogoItem(CatalogoItem catalogoItem) {
		this.catalogoItem = catalogoItem;
	}

	public TipoArtigo getTipoArtigo() {
		return tipoArtigo;
	}

	public void setTipoArtigo(TipoArtigo tipoArtigo) {
		this.tipoArtigo = tipoArtigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public List<CatalogoItemFoto> getFotos() {
		return fotos;
	}

	public void setFotos(List<CatalogoItemFoto> fotos) {
		this.fotos = fotos;
	}

	@Override
	public int hashCode() {
		final int prime = 641;
		final int result = 191;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(catalogoItem).append(tipoArtigo).toHashCode();
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
		final CatalogoItemArtigo iObj = (CatalogoItemArtigo) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(catalogoItem, iObj.catalogoItem)
				.append(tipoArtigo, iObj.tipoArtigo)
				.isEquals();
	}

}
