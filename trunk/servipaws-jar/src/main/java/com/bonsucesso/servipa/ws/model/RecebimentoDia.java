package com.bonsucesso.servipa.ws.model;



import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.bonsucesso.servipa.ws.TItemRetornoRecebimentoDia;


/**
 * Model tradutor para retornos TRetornoRecebimentoDia.
 * 
 * @author Carlos Eduardo Pauluk
 * 
 */
public class RecebimentoDia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7669009720016172748L;

	private String cpf;
	private String nome;
	private String contrato;
	private String documento;
	private Integer numPrestacao;
	private Boolean recebidoLoja;
	private Integer atraso;
	private Date dtVencto;
	private Double juros;
	private Double multa;
	private String situacao;
	private Double desconto;
	private Double vlrPrestacao;
	private Double total;

	// Entidade com os dados do PREV2000
	private VendaParcela vendaParcela;

	// 
	private Long id;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getNumPrestacao() {
		return numPrestacao;
	}

	public void setNumPrestacao(Integer numPrestacao) {
		this.numPrestacao = numPrestacao;
	}

	public Boolean getRecebidoLoja() {
		return recebidoLoja;
	}

	public void setRecebidoLoja(Boolean recebidoLoja) {
		this.recebidoLoja = recebidoLoja;
	}

	public Integer getAtraso() {
		return atraso;
	}

	public void setAtraso(Integer atraso) {
		this.atraso = atraso;
	}

	public Date getDtVencto() {
		return dtVencto;
	}

	public void setDtVencto(Date dtVencto) {
		this.dtVencto = dtVencto;
	}

	public Double getJuros() {
		return juros;
	}

	public void setJuros(Double juros) {
		this.juros = juros;
	}

	public Double getMulta() {
		return multa;
	}

	public void setMulta(Double multa) {
		this.multa = multa;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Double getVlrPrestacao() {
		return vlrPrestacao;
	}

	public void setVlrPrestacao(Double vlrPrestacao) {
		this.vlrPrestacao = vlrPrestacao;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public final static RecebimentoDia convert(TItemRetornoRecebimentoDia t) throws ParseException {
		RecebimentoDia recebimentoDia = new RecebimentoDia();
		recebimentoDia.setCpf(t.getCPFCliente());
		recebimentoDia.setNome(t.getNomeCliente());
		recebimentoDia.setContrato(t.getNrContrato());
		recebimentoDia.setDocumento(t.getNrDocumento());
		recebimentoDia.setNumPrestacao(t.getNrPrestacao());
		recebimentoDia.setRecebidoLoja(t.isRecebidoLoja());
		recebimentoDia.setAtraso(t.getDadosPrestacao().getAtraso());
		recebimentoDia.setDtVencto(new SimpleDateFormat("yyyyMMdd").parse(Integer.toString(t.getDadosPrestacao()
				.getDtvct())));
		recebimentoDia.setJuros(Double.parseDouble(t.getDadosPrestacao().getJuros()));
		recebimentoDia.setMulta(Double.parseDouble(t.getDadosPrestacao().getMulta()));
		recebimentoDia.setSituacao(t.getDadosPrestacao().getSituacao());
		recebimentoDia.setDesconto(Double.parseDouble(t.getDadosPrestacao().getVlrdesc()));
		recebimentoDia.setVlrPrestacao(Double.parseDouble(t.getDadosPrestacao().getVlrprest()));
		recebimentoDia.setTotal(Double.parseDouble(t.getDadosPrestacao().getVlrVlrtotal()));
		return recebimentoDia;
	}

	public final static List<RecebimentoDia> convert(TItemRetornoRecebimentoDia... ts) throws ParseException {
		List<RecebimentoDia> list = new ArrayList<RecebimentoDia>();
		for (TItemRetornoRecebimentoDia t : ts) {
			list.add(convert(t));
		}
		return list;
	}

	public Long getId() {
		this.id = Long.parseLong(getContrato() + getDocumento() + getNumPrestacao());
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VendaParcela getVendaParcela() {
		return vendaParcela;
	}

	public void setVendaParcela(VendaParcela vendaParcela) {
		this.vendaParcela = vendaParcela;
	}

	@Override
	public int hashCode() {
		final int prime = 881;
		final int result = 811;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).
				append(contrato).
				append(documento).
				append(numPrestacao).
				toHashCode();
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
		final RecebimentoDia iObj = (RecebimentoDia) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(contrato, iObj.contrato)
				.append(documento, iObj.documento)
				.append(numPrestacao, iObj.numPrestacao)
				.isEquals();
	}
}
