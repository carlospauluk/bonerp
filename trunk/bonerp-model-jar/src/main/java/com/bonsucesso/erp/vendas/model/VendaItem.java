package com.bonsucesso.erp.vendas.model;



import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Produto;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * Classe para a entidade Venda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ven_venda_item")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@DynamicUpdate(true)
@DynamicInsert(true)
public class VendaItem extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9015005037479149624L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "venda_id", nullable = false)
	@NotNull(message = "O campo 'Venda' deve ser informado")
	private Venda venda;

	@Column(nullable = true)
	private Integer ordem;

	/**
	 * Pode ser nulo, pois o item pode ser um "NC (88888)".
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "produto_id", nullable = true)
	private Produto produto;

	@ManyToOne(optional = false)
	@JoinColumn(name = "grade_tamanho_id", nullable = false)
	@NotNull(message = "Campo 'Grade/Tamanho' precisa ser informado")
	private GradeTamanho gradeTamanho;

	/**
	 * Caso seja um NC (88888)
	 */

	@Column(name = "nc_reduzido", nullable = true)
	private Long ncReduzido; // geralmente vai ser um 88888

	@Column(name = "nc_descricao", nullable = true, length = 200)
	@Size(max = 200, message = "'nc_descricao' deve possuir no máximo 200 caracteres")
	private String ncDescricao;

	@Column(name = "nc_grade_tamanho", nullable = true, length = 5)
	@Size(max = 200, message = "'nc_grade_tamanho' deve possuir no máximo 5 caracteres")
	private String ncGradeTamanho;

	/**
	 * -----------------------
	 */

	@Column(name = "qtde", nullable = false)
	@NotNull(message = "Campo 'Qtde' precisa ser informado")
	private BigDecimal qtde;

	/**
	 * Aqui pode ter sido o PRECO_PRAZO ou PRECO_PROMO (ou ainda outro preço alterado na hora da compra).
	 */
	@Column(name = "preco_venda", nullable = false)
	@NotNull(message = "Campo 'Preço Venda' precisa ser informado")
	private BigDecimal precoVenda;

	@Transient
	private BigDecimal totalItem;

	@Column(name = "alteracao_preco", nullable = false)
	@NotNull(message = "Campo 'Alteração Preço' precisa ser informado")
	private Boolean alteracaoPreco = false;

	@Column(name = "ncm", nullable = true, length = 20)
	@Size(max = 20, message = "'ncm' deve possuir no máximo 20 caracteres")
	private String ncm;

	@Column(name = "ncm_existente", nullable = true)
	private Boolean ncmExistente;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min = 0, max = 5000, message = "Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@Transient
	private String descricao;

	@Transient
	private Long reduzido;

	@Transient
	private String tamanho;

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public GradeTamanho getGradeTamanho() {
		return gradeTamanho;
	}

	public void setGradeTamanho(GradeTamanho gradeTamanho) {
		this.gradeTamanho = gradeTamanho;
	}

	public Long getNcReduzido() {
		return ncReduzido;
	}

	public void setNcReduzido(Long ncReduzido) {
		this.ncReduzido = ncReduzido;
	}

	public String getNcDescricao() {
		return ncDescricao;
	}

	public void setNcDescricao(String ncDescricao) {
		this.ncDescricao = ncDescricao;
	}

	public String getNcGradeTamanho() {
		return ncGradeTamanho;
	}

	public void setNcGradeTamanho(String ncGradeTamanho) {
		this.ncGradeTamanho = ncGradeTamanho;
	}

	public BigDecimal getQtde() {
		return qtde;
	}

	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public BigDecimal getTotalItem() {
		BigDecimal bdQtde = getQtde() != null ? getQtde() : BigDecimal.ZERO;
		BigDecimal bdPrecoVenda = getPrecoVenda() != null ? getPrecoVenda() : BigDecimal.ZERO;
		totalItem = new BigDecimal(CurrencyUtils.multiplica(bdQtde.doubleValue(), bdPrecoVenda.doubleValue())
				.toString());
		return totalItem;
	}

	public void setTotalItem(BigDecimal totalItem) {
		this.totalItem = totalItem;
	}

	public Boolean getAlteracaoPreco() {
		return alteracaoPreco;
	}

	public void setAlteracaoPreco(Boolean alteracaoPreco) {
		this.alteracaoPreco = alteracaoPreco;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public Boolean getNcmExistente() {
		return ncmExistente;
	}

	public void setNcmExistente(Boolean ncmExistente) {
		this.ncmExistente = ncmExistente;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public int hashCode() {
		final int prime = 47;
		final int result = 683;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(venda).append(produto).append(gradeTamanho).toHashCode();
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
		final VendaItem iObj = (VendaItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(venda, iObj.venda)
				.append(produto, iObj.produto)
				.append(gradeTamanho, iObj.gradeTamanho)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescricao() {
		if (produto != null && produto.getReduzido() != 88888) {
			descricao = produto.getDescricao();
		} else {
			descricao = getNcDescricao();
		}
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getReduzido() {
		if (produto != null) {
			reduzido = produto.getReduzidoEkt().longValue();
		} else {
			reduzido = getNcReduzido();
		}
		return reduzido;
	}

	public void setReduzido(Long reduzido) {
		this.reduzido = reduzido;
	}

	public String getTamanho() {
		if (produto != null) {
			tamanho = getGradeTamanho().getTamanho();
		} else {
			tamanho = getNcGradeTamanho();
		}
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

}
