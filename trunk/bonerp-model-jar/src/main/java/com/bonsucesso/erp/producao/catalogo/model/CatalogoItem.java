package com.bonsucesso.erp.producao.catalogo.model;



import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.producao.model.Instituicao;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade que representa um item de um catálogo (no caso, uma instituição).
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "prod_catalogo_item",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "catalogo_id", "instituicao_id" }),
				@UniqueConstraint(columnNames = { "catalogo_id", "ordem" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class CatalogoItem extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 2154558143914347470L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "catalogo_id", nullable = false)
	@NotNull(message = "O campo 'Catálogo' deve ser informado")
	private Catalogo catalogo;

	@ManyToOne(optional = false)
	@JoinColumn(name = "instituicao_id", nullable = false)
	@NotNull(message = "O campo 'Instituição' deve ser informado")
	private Instituicao instituicao;

	/**
	 * A princípio obtida da instituicao.
	 */
	@Column(nullable = false, length = 200)
	@Size(min = 2, max = 200, message = "'Descrição' deve possuir entre 2 e 200 caracteres")
	private String descricao;

	@Column(nullable = false)
	@Min(value = 1, message = "'Ordem' não pode ser menor que 1")
	private Integer ordem;

	@OneToMany(mappedBy = "catalogoItem", fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("descricao")
	private List<CatalogoItemArtigo> artigos;

	@OneToMany(mappedBy = "catalogoItem", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<CatalogoItemFoto> fotos;

	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public List<CatalogoItemArtigo> getArtigos() {
		return artigos;
	}

	public void setArtigos(List<CatalogoItemArtigo> artigos) {
		this.artigos = artigos;
	}

	public List<CatalogoItemFoto> getFotos() {
		return fotos;
	}

	public void setFotos(List<CatalogoItemFoto> fotos) {
		this.fotos = fotos;
	}

	@Override
	public int hashCode() {
		final int prime = 661;
		final int result = 673;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(catalogo).append(instituicao).toHashCode();
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
		final CatalogoItem iObj = (CatalogoItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(catalogo, iObj.catalogo)
				.append(instituicao, iObj.instituicao)
				.isEquals();
	}

}
