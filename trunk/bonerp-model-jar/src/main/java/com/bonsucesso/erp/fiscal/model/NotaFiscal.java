package com.bonsucesso.erp.fiscal.model;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.base.model.PessoaCadastro;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade NotaFiscal.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "fis_nf",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "numero", "serie", "tipo", "pessoa_emitente_id", "ambiente" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class NotaFiscal extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6267021280719204970L;

	@Column(name = "numero", nullable = false, length = 18)
	@NotNull(message = "'Número' deve ser informado")
	private Integer numero;

	@Column(name = "ambiente", nullable = false, length = 4)
	@NotNull(message = "'Ambiente' deve ser informado")
	private String ambiente;

	@Column(name = "natureza_operacao", nullable = false, length = 60)
	@NotNull(message = "'Natureza Operação' deve ser informado")
	private String naturezaOperacao = "VENDA";

	@Column(name = "serie", nullable = false)
	@NotNull(message = "'Série' deve ser informado")
	private Integer serie;

	@Column(name = "tipo", nullable = true, length = 30)
	@Enumerated(EnumType.STRING)
	private TipoNotaFiscal tipoNotaFiscal;

	@Column(name = "entrada_saida", nullable = false)
	@NotNull(message = "Campo 'Entrada/Saída' precisa ser informado")
	private Boolean entrada = true;

	@Column(name = "indicador_forma_pagto", nullable = false, length = 30)
	@NotNull(message = "Campo 'Forma Pagto' precisa ser informado")
	@Enumerated(EnumType.STRING)
	private IndicadorFormaPagto indicadorFormaPagto = IndicadorFormaPagto.VISTA;

	/**
	 * Se for de saída, é a loja, se for de entrada é o fornecedor.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "pessoa_emitente_id", nullable = false)
	@NotNull(message = "O campo 'Emitente' deve ser informado")
	private Pessoa pessoaEmitente;

	/**
	 * Aqui pode ser null, pois pode ser uma NFCe anônima.
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "pessoa_destinatario_id", nullable = true)
	private Pessoa pessoaDestinatario;

	/**
	 * Informa se a pessoaDestinatario é um Cliente, Fornecedor ou Funcionário (gambiarra horrível).
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "pessoa_cadastro", nullable = true, length = 30)
	private PessoaCadastro pessoaCadastro;

	@Column(name = "dt_emissao", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtEmissao;

	@Column(name = "subtotal", nullable = false)
	@NotNull(message = "Campo 'Sub Total' precisa ser informado")
	@Min(value = 0, message = "Campo 'Sub Total' precisa ser informado")
	private BigDecimal subTotal = BigDecimal.ZERO;

	@Column(name = "total_descontos", nullable = false)
	@NotNull(message = "Campo 'Total Descontos' precisa ser informado")
	@Min(value = 0, message = "Campo 'Total Descontos' precisa ser informado")
	private BigDecimal totalDescontos = BigDecimal.ZERO;

	@Column(name = "valor_total", nullable = false)
	@NotNull(message = "Campo 'Valor Total' precisa ser informado")
	@Min(value = 0, message = "Campo 'Valor Total' precisa ser informado")
	private BigDecimal valorTotal;

	@Column(name = "info_compl", nullable = true, length = 9000)
	@Size(max = 3000, message = "'Obs' deve possuir até 3000 caracteres")
	private String informacoesComplementares;

	@OneToMany(mappedBy = "notaFiscal", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy("ordem")
	private List<NotaFiscalItem> itens;

	@Column(name = "finalidade_nf", nullable = false, length = 30)
	@NotNull(message = "Campo 'Finalidade' precisa ser informado")
	@Enumerated(EnumType.STRING)
	private FinalidadeNF finalidadeNF = FinalidadeNF.NORMAL;

	@Column(name = "transp_modalidade_frete", nullable = false, length = 30)
	@NotNull(message = "Campo 'Modalidade Frete' precisa ser informado")
	@Enumerated(EnumType.STRING)
	private ModalidadeFrete transpModalidadeFrete = ModalidadeFrete.SEM_FRETE;

	@ManyToOne(optional = true)
	@JoinColumn(name = "transp_fornecedor_id", nullable = true)
	private Fornecedor transpFornecedor;

	@Column(name = "transp_qtde_volumes", nullable = true)
	private BigDecimal transpQtdeVolumes;

	@Column(name = "transp_especie_volumes", nullable = true, length = 200)
	private String transpEspecieVolumes;

	@Column(name = "transp_marca_volumes", nullable = true, length = 200)
	private String transpMarcaVolumes;

	@Column(name = "transp_numeracao_volumes", nullable = true, length = 200)
	private String transpNumeracaoVolumes;

	@Column(name = "transp_peso_liquido", nullable = true)
	private BigDecimal transpPesoLiquido;

	@Column(name = "transp_peso_bruto", nullable = true)
	private BigDecimal transpPesoBruto;

	@Column(name = "transp_valor_total_frete", nullable = true)
	private BigDecimal transpValorTotalFrete;

	@Lob
	@Column(name = "xml_nota")
	private String xmlNota;

	@Column(name = "motivo_cancelamento", nullable = true, length = 3000)
	@Size(max = 3000, message = "'Motivo cancelamento' deve possuir até 3000 caracteres")
	private String motivoCancelamento;

	@Column(name = "spartacus_id_nota", nullable = true)
	private Integer spartacusId;

	/**
	 * Pode ser obtido de dois lugares. Se tiver registro na mensretorno, consulta de lá. Senão, consulta pelo nfevv.status_nfe.
	 */
	@Column(name = "spartacus_status")
	private Integer spartacusStatus;

	/**
	 * Marca em que momento o status foi atualizado.
	 */
	@Column(name = "dt_spartacus_status", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtSpartacusStatus;

	/**
	 * É obtido do campo mensretorno.mens_desc
	 */
	@Lob
	@Column(name = "spartacus_mensretorno")
	private String spartacusMensRetorno;

	/**
	 * Descrição da mensagem obtida na tabela bonerp.fis_msg_retorno_rf.
	 */
	@Column(name = "spartacus_mensretorno_receita", length = 2000)
	private String spartacusMensRetornoReceita;

	@Column(name = "a03id_nf_referenciada", length = 100)
	private String a03idNfReferenciada;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getNaturezaOperacao() {
		return naturezaOperacao;
	}

	public void setNaturezaOperacao(String naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public Integer getSerie() {
		return serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	public Integer getSpartacusStatus() {
		return spartacusStatus;
	}

	public void setSpartacusStatus(Integer spartacusStatus) {
		this.spartacusStatus = spartacusStatus;
	}

	public Date getDtSpartacusStatus() {
		return dtSpartacusStatus;
	}

	public void setDtSpartacusStatus(Date dtSpartacusStatus) {
		this.dtSpartacusStatus = dtSpartacusStatus;
	}

	public TipoNotaFiscal getTipoNotaFiscal() {
		return tipoNotaFiscal;
	}

	public void setTipoNotaFiscal(TipoNotaFiscal tipoNotaFiscal) {
		this.tipoNotaFiscal = tipoNotaFiscal;
	}

	public IndicadorFormaPagto getIndicadorFormaPagto() {
		return indicadorFormaPagto;
	}

	public void setIndicadorFormaPagto(IndicadorFormaPagto indicadorFormaPagto) {
		this.indicadorFormaPagto = indicadorFormaPagto;
	}

	public Boolean getEntrada() {
		return entrada;
	}

	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}

	public Pessoa getPessoaEmitente() {
		return pessoaEmitente;
	}

	public void setPessoaEmitente(Pessoa pessoaEmitente) {
		this.pessoaEmitente = pessoaEmitente;
	}

	public Pessoa getPessoaDestinatario() {
		return pessoaDestinatario;
	}

	public void setPessoaDestinatario(Pessoa pessoaDestinatario) {
		this.pessoaDestinatario = pessoaDestinatario;
	}

	public PessoaCadastro getPessoaCadastro() {
		return pessoaCadastro;
	}

	public void setPessoaCadastro(PessoaCadastro pessoaCadastro) {
		this.pessoaCadastro = pessoaCadastro;
	}

	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTotalDescontos() {
		return totalDescontos;
	}

	public void setTotalDescontos(BigDecimal totalDescontos) {
		this.totalDescontos = totalDescontos;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getInformacoesComplementares() {
		return informacoesComplementares;
	}

	public void setInformacoesComplementares(String informacoesComplementares) {
		this.informacoesComplementares = informacoesComplementares;
	}

	public String getXmlNota() {
		return xmlNota;
	}

	public void setXmlNota(String xmlNota) {
		this.xmlNota = xmlNota;
	}

	public List<NotaFiscalItem> getItens() {
		if (itens == null) {
			itens = new ArrayList<NotaFiscalItem>();
		}
		return itens;
	}

	public void setItens(List<NotaFiscalItem> itens) {
		this.itens = itens;
	}

	public ModalidadeFrete getTranspModalidadeFrete() {
		return transpModalidadeFrete;
	}

	public FinalidadeNF getFinalidadeNF() {
		return finalidadeNF;
	}

	public void setFinalidadeNF(FinalidadeNF finalidadeNF) {
		this.finalidadeNF = finalidadeNF;
	}

	public void setTranspModalidadeFrete(ModalidadeFrete transpModalidadeFrete) {
		this.transpModalidadeFrete = transpModalidadeFrete;
	}

	public Fornecedor getTranspFornecedor() {
		return transpFornecedor;
	}

	public void setTranspFornecedor(Fornecedor transpFornecedor) {
		this.transpFornecedor = transpFornecedor;
	}

	public BigDecimal getTranspQtdeVolumes() {
		return transpQtdeVolumes;
	}

	public void setTranspQtdeVolumes(BigDecimal transpQtdeVolumes) {
		this.transpQtdeVolumes = transpQtdeVolumes;
	}

	public String getTranspEspecieVolumes() {
		return transpEspecieVolumes;
	}

	public void setTranspEspecieVolumes(String transpEspecieVolumes) {
		this.transpEspecieVolumes = transpEspecieVolumes;
	}

	public String getTranspMarcaVolumes() {
		return transpMarcaVolumes;
	}

	public void setTranspMarcaVolumes(String transpMarcaVolumes) {
		this.transpMarcaVolumes = transpMarcaVolumes;
	}

	public String getTranspNumeracaoVolumes() {
		return transpNumeracaoVolumes;
	}

	public void setTranspNumeracaoVolumes(String transpNumeracaoVolumes) {
		this.transpNumeracaoVolumes = transpNumeracaoVolumes;
	}

	public BigDecimal getTranspPesoLiquido() {
		return transpPesoLiquido;
	}

	public void setTranspPesoLiquido(BigDecimal transpPesoLiquido) {
		this.transpPesoLiquido = transpPesoLiquido;
	}

	public BigDecimal getTranspPesoBruto() {
		return transpPesoBruto;
	}

	public void setTranspPesoBruto(BigDecimal transpPesoBruto) {
		this.transpPesoBruto = transpPesoBruto;
	}

	public BigDecimal getTranspValorTotalFrete() {
		return transpValorTotalFrete;
	}

	public void setTranspValorTotalFrete(BigDecimal transpValorTotalFrete) {
		this.transpValorTotalFrete = transpValorTotalFrete;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public Integer getSpartacusId() {
		return spartacusId;
	}

	public void setSpartacusId(Integer spartacusId) {
		this.spartacusId = spartacusId;
	}

	public String getSpartacusMensRetorno() {
		return spartacusMensRetorno;
	}

	public void setSpartacusMensRetorno(String spartacusMensRetorno) {
		this.spartacusMensRetorno = spartacusMensRetorno;
	}

	public String getSpartacusMensRetornoReceita() {
		return spartacusMensRetornoReceita;
	}

	public void setSpartacusMensRetornoReceita(String spartacusMensRetornoReceita) {
		this.spartacusMensRetornoReceita = spartacusMensRetornoReceita;
	}

	public String getA03idNfReferenciada() {
		return a03idNfReferenciada;
	}

	public void setA03idNfReferenciada(String a03idNfReferenciada) {
		this.a03idNfReferenciada = a03idNfReferenciada;
	}

	//@Override
	@Override
	public int hashCode() {
		final int prime = 641;
		final int result = 281;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(numero)
				.append(pessoaEmitente)
				.toHashCode();
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
		final NotaFiscal iObj = (NotaFiscal) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(numero, iObj.numero)
				.append(pessoaEmitente, iObj.pessoaEmitente)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

	public NotaFiscal clonar() {
		NotaFiscal clone = SerializationUtils.clone(this);
		clone.setId(null);
		clone.setItens(null);

		clone.setMotivoCancelamento(null);
		clone.setSpartacusId(null);
		clone.setSpartacusMensRetorno(null);
		clone.setSpartacusMensRetornoReceita(null);
		clone.setSpartacusStatus(null);
		clone.setXmlNota(null);
		clone.setNumero(null);

		for (NotaFiscalItem item : getItens()) {
			NotaFiscalItem itemClone = SerializationUtils.clone(item);
			itemClone.setNotaFiscal(clone);
			itemClone.setId(null);
			clone.getItens().add(itemClone);

		}

		return clone;
	}

}
