package com.bonsucesso.erp.financeiro.model;



import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;
import com.ocabit.utils.strings.StringUtils;


/**
 * Entidade Categoria.
 *
 * @author Carlos Eduardo Pauluk.
 *
 */
@Entity
@Table(name = "fin_categoria", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "descricao" }),
		@UniqueConstraint(columnNames = { "codigo" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Categoria extends EntityIdImpl implements Comparable<Categoria> {

	/**
	 *
	 */
	private static final long serialVersionUID = 2717983567813192891L;

	public static final String MASK = "0.00.000.000.0000.00000";

	@ManyToOne(optional = true)
	@JoinColumn(name = "pai_id", nullable = true)
	private Categoria pai;

	/**
	 * A hierarquia das Categorias é em modelo árvore.
	 */
	@OneToMany(mappedBy = "pai", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@OrderBy("codigo")
	private List<Categoria> subCategs;

	@Column(name = "descricao", nullable = false, length = 200)
	@NotEmpty(message = "Campo 'Descrição' precisa ser informado")
	private String descricao;

	@Column(name = "descricao_padrao_moviment", nullable = true, length = 200)
	private String descricaoPadraoMoviment;

	@Column(name = "codigo", nullable = false)
	@NotNull(message = "Campo 'Código' precisa ser informado")
	private Long codigo;

	/**
	 * Informa se esta categoria deve ser apresentada em relatórios de totalizações.
	 */
	@Column(name = "totalizavel", nullable = false)
	@NotNull(message = "Campo 'Totalizável' precisa ser informado")
	private Boolean totalizavel = false;

	/**
	 * Informa se esta categoria necessita que o CentroCusto seja informado (ou se ele será automático).
	 */
	@Column(name = "centro_custo_dif", nullable = false)
	@NotNull(message = "Campo 'Centro de Custo Dif' precisa ser informado")
	private Boolean centroCustoDif = false;

	/**
	 * Informa quais ROLES possuem acesso as informações (categoria.descricao e movimentacao.descricao).
	 * Para mais de uma, informar separado por vírgula.
	 */
	@Column(name = "roles_acess", nullable = true, length = 2000)
	private String rolesAcess;

	/**
	 * Caso o usuário logado não possua nenhuma das "rolesAcess", então a descrição alternativa deve ser exibida.
	 */
	@Column(name = "descricao_alternativa", nullable = true, length = 200)
	private String descricaoAlternativa;

	/**
	 * O código com a máscara aplicada.
	 */
	@Transient
	private String codigoM;

	@Column(name = "codigo_super", nullable = false)
	@NotNull(message = "Campo 'Código Super' precisa ser informado")
	private Long codigoSuper;

	/**
	 * Retorna somente a parte do código que é relativa a este.
	 */
	@Transient
	private String codigoSufixo;

	@Transient
	private String descricaoMontada;

	/**
	 * Default constructor.
	 */
	public Categoria() {
		super();
	}

	/**
	 * Constructor overload.
	 *
	 * @param id
	 * @param ccPai
	 * @param descricao
	 */
	public Categoria(Long id, Categoria ccPai, String descricao) {
		super();
		setId(id);
		pai = ccPai;
		this.descricao = descricao;
	}

	public Categoria getPai() {
		return pai;
	}

	public void setPai(Categoria ccPai) {
		pai = ccPai;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricaoPadraoMoviment() {
		return descricaoPadraoMoviment;
	}

	public void setDescricaoPadraoMoviment(String descricaoPadraoMoviment) {
		this.descricaoPadraoMoviment = descricaoPadraoMoviment;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
		codigoSuper = Long.valueOf(getCodigoM().substring(0, 1));
	}

	public Boolean getTotalizavel() {
		return totalizavel;
	}

	public void setTotalizavel(Boolean totalizavel) {
		this.totalizavel = totalizavel;
	}

	public Boolean getCentroCustoDif() {
		return centroCustoDif;
	}

	public void setCentroCustoDif(Boolean centroCustoDif) {
		this.centroCustoDif = centroCustoDif;
	}

	public String getRolesAcess() {
		return rolesAcess;
	}

	public void setRolesAcess(String rolesAcess) {
		this.rolesAcess = rolesAcess;
	}

	public String getDescricaoAlternativa() {
		return descricaoAlternativa;
	}

	public void setDescricaoAlternativa(String descricaoAlternativa) {
		this.descricaoAlternativa = descricaoAlternativa;
	}

	public Long getCodigoSuper() {
		codigoSuper = Long.valueOf(getCodigoM().substring(0, 1));
		return codigoSuper;
	}

	public void setCodigoSuper(Long codigoSuper) {
		this.codigoSuper = codigoSuper;
	}

	public void setSubCategs(List<Categoria> filhos) {
		subCategs = filhos;
	}

	public List<Categoria> getSubCategs() {
		return subCategs;
	}

	@Override
	public int hashCode() {
		// Utiliza números primos
		final int prime = 19;
		final int result = 277;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(descricao)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// Verificação padrão
		if (obj == null) {
			return false;
		}
		// Verificação padrão
		if (obj == this) {
			return true;
		}
		// Verificação padrão
		if (obj.getClass() != getClass()) {
			return false;
		}
		final Categoria iObj = (Categoria) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(descricao, iObj.descricao).isEquals();
	}

	public void setDescricaoMontada(String descricaoMontada) {
		this.descricaoMontada = descricaoMontada;
	}

	/**
	 * Retorna a descrição de uma Categoria no formato codigo + descricao (Ex.:
	 * 2.01 - DESPESAS PESSOAIS).
	 *
	 * @return
	 */
	public String getDescricaoMontada() {
		setDescricaoMontada((getCodigo() == null) || getCodigo().equals("") ? getDescricao()
				: getCodigo() + " - " + getDescricao());
		return descricaoMontada;
	}

	public String getCodigoM() {
		codigoM = StringUtils.mascarar(getCodigo(), Categoria.MASK);
		return codigoM;
	}

	public void setCodigoM(String codigoM) {
		this.codigoM = codigoM;
	}

	public String getCodigoSufixo() {
		if (getCodigo() != null) {
			if (getPai() == null) {
				codigoSufixo = codigo.toString();
			} else {
				// Se tem pai, é o restante do código, removendo a parte do pai:
				codigoSufixo = getCodigoM().substring(getPai().getCodigoM().length() + 1);
			}
		}
		return codigoSufixo;
	}

	public void setCodigoSufixo(String codigoSufixo) {
		this.codigoSufixo = codigoSufixo;
	}

	@Override
	public int compareTo(Categoria o) {
		return new CompareToBuilder().append(codigo, o.codigo)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
