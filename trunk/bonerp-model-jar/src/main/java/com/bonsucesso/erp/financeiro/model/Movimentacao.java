package com.bonsucesso.erp.financeiro.model;



import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotEmpty;

import com.bonsucesso.erp.base.model.Pessoa;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Entidade Movimentação. Informa uma Movimentação financeira, que pode já ter
 * sido realizada ou não (conta a pagar/receber).
 *
 * @author Carlos Eduardo Pauluk
 */
@Entity
@Table(name = "fin_movimentacao",
		uniqueConstraints = { @UniqueConstraint(name = "unq_controle", columnNames = { "unq_controle" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Movimentacao extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -6176182917856907926L;

	@Column(name = "id_sistema_antigo", nullable = true)
	private Integer idSistemaAntigo;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 50)
	@NotNull(message = "Campo 'Status' precisa ser informado")
	private Status status;

	// Tipo de lançamento que originou esta movimentação.
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_lancto", nullable = false, length = 50)
	@NotNull(message = "Campo 'Tipo Lancto' precisa ser informado")
	private TipoLancto tipoLancto;

	@ManyToOne(optional = false)
	@JoinColumn(name = "carteira_id", nullable = false)
	@NotNull(message = "Campo 'Carteira' precisa ser informado")
	@Valid
	private Carteira carteira;

	/**
	 * Carteira informada em casos de TRANSF_PROPRIA.
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "carteira_destino_id", nullable = true)
	private Carteira carteiraDestino;

	@ManyToOne(optional = false)
	@JoinColumn(name = "centrocusto_id", nullable = false)
	@NotNull(message = "Campo 'Centro de Custo' precisa ser informado")
	@Valid
	private CentroCusto centroCusto;

	@ManyToOne(optional = false)
	@JoinColumn(name = "modo_id", nullable = false)
	@NotNull(message = "Campo 'Modo' precisa ser informado")
	@Valid
	private Modo modo;

	@ManyToOne(optional = true)
	@JoinColumn(name = "bandeira_cartao_id", nullable = true)
	private BandeiraCartao bandeiraCartao;

	@ManyToOne(optional = true)
	@JoinColumn(name = "operadora_cartao_id", nullable = true)
	private OperadoraCartao operadoraCartao;

	// Tipo de lançamento que originou esta movimentação.
	@Enumerated(EnumType.STRING)
	@Column(name = "plano_pagto_cartao", nullable = false, length = 50)
	@NotNull(message = "Campo 'Plano Pagto Cartão' precisa ser informado")
	private PlanoPagtoCartao planoPagtoCartao = PlanoPagtoCartao.N_A;

	@Column(name = "descricao", nullable = false, length = 500)
	@NotEmpty(message = "Campo 'Descrição' precisa ser informado (entre 3 e 500 caracteres)")
	@Size(min = 3, max = 500, message = "Campo 'Descrição' precisa ser informado (entre 3 e 500 caracteres)")
	private String descricao;

	/**
	 * Campo para manter como único uma movimentação que tenha todas as características de outra movimentação do mesmo
	 * dia/carteira/valor/categoria etc.
	 */
	@Column(name = "unq_controle", nullable = false, length = 15)
	@NotEmpty(message = "Campo 'unq_controle' precisa ser informado (15 caracteres)")
	@Size(min = 1, max = 15, message = "Campo 'unq_controle' precisa ser informado (15 caracteres)")
	private String unqControle;

	/**
	 * Inclui dados de cheque, caso existam.
	 */
	@Transient
	private String descricaoMontada;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min = 0, max = 5000, message = "Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@ManyToOne(optional = true, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			fetch = FetchType.EAGER)
	@JoinColumn(name = "parcelamento_id", nullable = true)
	private Parcelamento parcelamento;

	/**
	 * Caso a movimentação seja uma parcela, informa qual.
	 */
	@Column(name = "num_parcela", nullable = true)
	private Integer numParcela;

	/**
	 * Inclui aqui para não precisar dar um SELECT na tabela de parcelamentos toda hora que quiser a qtde de parcelas.
	 */
	@Column(name = "qtde_parcelas", nullable = true)
	private Integer qtdeParcelas;

	// @ManyToOne(optional = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@ManyToOne(optional = true, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "cadeia_id", nullable = true)
	private Cadeia cadeia;

	/**
	 * Caso a movimentação faça parte de uma cadeia, informa em qual posição.
	 */
	@Column(name = "cadeia_ordem", nullable = true)
	private Integer cadeiaOrdem;

	/**
	 * Para casos onde existe um documento numerado representando a movimentação
	 * (boleto, código transferência bancária, nota fiscal, etc).
	 */
	@Column(name = "documento_num", nullable = true, length = 100)
	private String documentoNum;

	/**
	 * Código do Banco (conforme códigos oficiais).
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "documento_banco_id", nullable = true)
	private Banco documentoBanco;

	/**
	 * Informa o número do documento (nota, etc) fiscal.
	 */
	@Column(name = "documento_fiscal", nullable = true)
	private String documentoFiscal;

	/**
	 * Informa o número do código do pedido.
	 */
	@Column(name = "codigo_pedido", nullable = true)
	private String codigoPedido;

	/**
	 * Caso seja uma movimentação agrupada em um Grupo de Movimentação (item).
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "grupo_item_id", nullable = true)
	private GrupoItem grupoItem;

	@ManyToOne(optional = false)
	@JoinColumn(name = "categoria_id", nullable = false)
	@NotNull(message = "Campo 'Categoria' precisa ser informado")
	@Valid
	private Categoria categoria;

	/**
	 * A qual pessoa se refere.
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "pessoa_id", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Pessoa pessoa;

	/**
	 * Data em que a movimentação efetivamente aconteceu.
	 */
	@Column(name = "dt_moviment", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt Moviment' precisa ser informado")
	private Date dtMoviment;

	/**
	 * Data prevista para pagamento.
	 */
	@Column(name = "dt_vencto", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt Vencto' precisa ser informado")
	private Date dtVencto;

	/**
	 * Data prevista (postergando para dia útil) para pagamento.
	 */
	@Column(name = "dt_vencto_efetiva", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt Vencto Efetiva' precisa ser informado")
	private Date dtVenctoEfetiva;

	/**
	 * Data em que a movimentação foi paga.
	 */
	@Column(name = "dt_pagto", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dtPagto;

	/**
	 * Se dtPagto != null ? dtPagto : dtVencto.
	 */
	@Column(name = "dt_util", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt ' precisa ser informado")
	private Date dtUtil;

	/**
	 * Valor bruto da movimentação.
	 */
	@Column(name = "valor", nullable = false, precision = 15, scale = 2)
	@NotNull(message = "Campo 'Valor' precisa ser informado")
	private BigDecimal valor;

	/**
	 * Possíveis descontos (sempre negativo).
	 */
	@Column(name = "descontos", nullable = true, precision = 15, scale = 2)
	private BigDecimal descontos;

	/**
	 * Possíveis acréscimos (sempre positivo).
	 */
	@Column(name = "acrescimos", nullable = true, precision = 15, scale = 2)
	private BigDecimal acrescimos;

	/**
	 * Valor total informado no campo e que é salvo no banco (pode divergir da
	 * conta por algum motivo)
	 */
	@Column(name = "valor_total", nullable = false, precision = 15, scale = 2)
	@NotNull(message = "Campo 'Valor Total' precisa ser informado")
	@Min(value = 0, message = "Campos 'Valor Total' deve ser maior que zero")
	private BigDecimal valorTotal;

	/**
	 * Caso tenha sido paga/recebida via cheque, informa os dados.
	 */
	@Embedded
	@Valid
	private Cheque cheque;

	@Embedded
	@Valid
	private Recorrencia recorrencia;

	public Movimentacao() {
		recorrencia = new Recorrencia();
	}

	public Integer getIdSistemaAntigo() {
		return idSistemaAntigo;
	}

	public void setIdSistemaAntigo(Integer idSistemaAntigo) {
		this.idSistemaAntigo = idSistemaAntigo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public TipoLancto getTipoLancto() {
		return tipoLancto;
	}

	public void setTipoLancto(TipoLancto tipoLancto) {
		this.tipoLancto = tipoLancto;
	}

	public Banco getDocumentoBanco() {
		return documentoBanco;
	}

	public void setDocumentoBanco(Banco documentoBanco) {
		this.documentoBanco = documentoBanco;
	}

	public String getDocumentoFiscal() {
		return documentoFiscal;
	}

	public void setDocumentoFiscal(String documentoFiscal) {
		this.documentoFiscal = documentoFiscal;
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}

	public void setCodigoPedido(String codigoPedido) {
		this.codigoPedido = codigoPedido;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnqControle() {
		return unqControle;
	}

	public void setUnqControle(String unqControle) {
		this.unqControle = unqControle;
	}

	public String getDescricaoMontada() {
		return descricaoMontada;
	}

	public void setDescricaoMontada(String descricaoMontada) {
		this.descricaoMontada = descricaoMontada;
	}

	public String getDocumentoNum() {
		return documentoNum;
	}

	public void setDocumentoNum(String documento) {
		documentoNum = documento;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	/**
	 * Retorna com instanciação para null.
	 *
	 * @return
	 */
	public GrupoItem getGrupoItem() {
		return grupoItem;
	}

	public void setGrupoItem(GrupoItem grupoItem) {
		this.grupoItem = grupoItem;
	}

	/**
	 * Retorna com instanciação para null.
	 *
	 * @return
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria centroCusto) {
		categoria = centroCusto;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public BandeiraCartao getBandeiraCartao() {
		return bandeiraCartao;
	}

	public void setBandeiraCartao(BandeiraCartao bandeiraCartao) {
		this.bandeiraCartao = bandeiraCartao;
	}

	public OperadoraCartao getOperadoraCartao() {
		return operadoraCartao;
	}

	public void setOperadoraCartao(OperadoraCartao operadoraCartao) {
		this.operadoraCartao = operadoraCartao;
	}

	public PlanoPagtoCartao getPlanoPagtoCartao() {
		return planoPagtoCartao;
	}

	public void setPlanoPagtoCartao(PlanoPagtoCartao planoPagtoCartao) {
		this.planoPagtoCartao = planoPagtoCartao;
	}

	/**
	 * getter de dtMoviment
	 *
	 * @return
	 */
	public Date getDtMoviment() {
		// nunca usa o Date interno (por questões de segurança - não perder
		// controle para fora do objeto)
		return dtMoviment;
	}

	/**
	 * setter para dtMoviment (criando novo objeto por questões de segurança)
	 *
	 * @param dtMoviment
	 */
	public void setDtMoviment(final Date dtMoviment) {
		// nunca usa o Date passado (por questões de segurança - não perder
		// controle para fora do objeto)
		this.dtMoviment = dtMoviment == null ? null : new Date(dtMoviment.getTime());
	}

	/**
	 * getter de dtVencto.
	 *
	 * @return
	 */
	public Date getDtVencto() {
		return dtVencto;
	}

	/**
	 * setter para dtVencto (criando novo objeto por questões de segurança)
	 *
	 * @param dtVencto
	 */
	public void setDtVencto(final Date dtVencto) {
		// nunca usa o Date passado (por questões de segurança - não perder
		// controle para fora do objeto)
		this.dtVencto = dtVencto == null ? null : new Date(dtVencto.getTime());
	}

	public Date getDtVenctoEfetiva() {
		return dtVenctoEfetiva;
	}

	public void setDtVenctoEfetiva(Date dtVenctoEfetiva) {
		this.dtVenctoEfetiva = dtVenctoEfetiva == null ? null : new Date(dtVenctoEfetiva.getTime());
	}

	/**
	 * getter de dtPagto.
	 *
	 * @return
	 */
	public Date getDtPagto() {
		return dtPagto;
	}

	/**
	 * setter para dtPagto (criando novo objeto por questões de segurança)
	 *
	 * @param dtPagto
	 */
	public void setDtPagto(final Date dtPagto) {
		// nunca usa o Date passado (por questões de segurança - não perder
		// controle para fora do objeto)
		this.dtPagto = dtPagto == null ? null : new Date(dtPagto.getTime());
	}

	public Date getDtUtil() {
		dtUtil = dtPagto == null ? getDtVenctoEfetiva() : getDtPagto();
		return dtUtil;
	}

	public void setDtUtil(Date dtUtil) {
		this.dtUtil = dtUtil == null ? null : new Date(dtUtil.getTime());
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getNumParcela() {
		return numParcela;
	}

	public void setNumParcela(Integer parcela) {
		numParcela = parcela;
	}

	public Integer getQtdeParcelas() {
		return qtdeParcelas;
	}

	public void setQtdeParcelas(Integer qtdeParcelas) {
		this.qtdeParcelas = qtdeParcelas;
	}

	public Parcelamento getParcelamento() {
		return parcelamento;
	}

	public void setParcelamento(Parcelamento parcelamento) {
		this.parcelamento = parcelamento;
	}

	public Cadeia getCadeia() {
		return cadeia;
	}

	public void setCadeia(Cadeia cadeia) {
		this.cadeia = cadeia;
	}

	public Integer getCadeiaOrdem() {
		return cadeiaOrdem;
	}

	public void setCadeiaOrdem(Integer cadeiaOrdem) {
		this.cadeiaOrdem = cadeiaOrdem;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public Carteira getCarteiraDestino() {
		return carteiraDestino;
	}

	public void setCarteiraDestino(Carteira carteiraDestino) {
		this.carteiraDestino = carteiraDestino;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public BigDecimal getDescontos() {
		return descontos;
	}

	public void setDescontos(BigDecimal descontos) {
		this.descontos = descontos;
	}

	public BigDecimal getAcrescimos() {
		return acrescimos;
	}

	public void setAcrescimos(BigDecimal acrescimos) {
		this.acrescimos = acrescimos;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorPago) {
		valorTotal = valorPago;
	}

	public Cheque getCheque() {
		if (cheque == null) {
			cheque = new Cheque();
		}
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public Recorrencia getRecorrencia() {
		return recorrencia;
	}

	public void setRecorrencia(Recorrencia recorrencia) {
		this.recorrencia = recorrencia;
	}

	@Override
	public int hashCode() {
		// Usar somente números primos.
		final int prime = 293;
		final int result = 157;
		// usa API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(carteira)
				.append(categoria)
				.append(descricao)
				.append(dtVencto)
				.append(unqControle)
				.append(valor).toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		// verificação padrão
		if (obj == null) {
			return false;
		}
		// verificação padrão
		if (obj == this) {
			return true;
		}
		// verificação padrão
		if (obj.getClass() != getClass()) {
			return false;
		}
		final Movimentacao iObj = (Movimentacao) obj;
		// usa API do apache-commons
		return new EqualsBuilder().append(carteira, iObj.carteira)
				.append(categoria, iObj.categoria)
				.append(descricao, iObj.descricao)
				.append(dtVencto, iObj.dtVencto)
				.append(valor, iObj.valor)
				.append(unqControle, iObj.unqControle)
				.isEquals();
	}

	@Override
	public String toString() {
		// FIXME: testar problemas com rowKey do p:dataTable (de algum modo está usando o toString())
		return super.toString();
		// return "Movimentacao [id=" + id + ", descricaoMontada=" + descricaoMontada + ", numParcela=" + numParcela + ", dtVencto=" + dtVencto + ", valorTotal=" + valorTotal + "]";
	}

	@Override
	public String toStringToView() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// PAGTO A FORNECEDOR (02/02) - (NF: 237) (Vencto.: 03/08/2015, 918,00)
		String dtVenctoEfetiva = getDtVenctoEfetiva() != null ? sdf.format(getDtVenctoEfetiva()) : "";
		String valorTotal = getValorTotal() != null ? nf.format(getValorTotal()) : "";
		return "'" + getDescricaoMontada() + "' (Vencto: " + dtVenctoEfetiva + ", "
				+ valorTotal + ")";
	}

	/**
	 * Utilitário para pegar as informações mais rapidamente via JSF.
	 * 
	 * @return
	 */
	public String infoDatas() {
		StringBuilder str = new StringBuilder("");
		if (getDtMoviment() != null) {
			str.append("Dt Moviment: " + CalendarUtil.sdfDate.format(getDtMoviment()));
		}
		if (getDtVencto() != null) {
			if (str.toString() != "")
				str.append("\n");
			str.append("Dt Vencto: " + CalendarUtil.sdfDate.format(getDtVencto()));
		}
		if (getDtVenctoEfetiva() != null) {
			if (str.toString() != "")
				str.append("\n");
			str.append("Dt Vencto Efet: " + CalendarUtil.sdfDate.format(getDtVenctoEfetiva()));
		}
		if (getDtPagto() != null) {
			if (str.toString() != "")
				str.append("\n");
			str.append("Dt Pagto: " + CalendarUtil.sdfDate.format(getDtPagto()));
		}
		return str.toString();
	}

	/**
	 * Utilitário para pegar as informações mais rapidamente via JSF.
	 * 
	 * @return
	 */
	public String infoValores() {
		StringBuilder str = new StringBuilder("");
		if (getValor() != null) {
			str.append("Valor: " + NumberFormat.getCurrencyInstance().format(getValor()));
		}
		if (getAcrescimos() != null) {
			if (str.toString() != "")
				str.append("\n");
			str.append("Acréscimos: " + NumberFormat.getCurrencyInstance().format(getAcrescimos()));
		}
		if (getDescontos() != null) {
			if (str.toString() != "")
				str.append("\n");
			str.append("Descontos: " + NumberFormat.getCurrencyInstance().format(getDescontos()));
		}
		if (getValorTotal() != null) {
			if (str.toString() != "")
				str.append("\n");
			str.append("Total: " + NumberFormat.getCurrencyInstance().format(getValorTotal()));
		}
		return str.toString();
	}

}
