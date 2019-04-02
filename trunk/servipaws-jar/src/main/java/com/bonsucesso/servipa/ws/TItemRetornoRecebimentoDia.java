/**
 * TItemRetornoRecebimentoDia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemRetornoRecebimentoDia  implements java.io.Serializable {
    private int nrLoja;

    private java.lang.String nrContrato;

    private int nrPrestacao;

    private java.lang.String nrDocumento;

    private boolean recebidoLoja;

    private java.lang.String nomeCliente;

    private java.lang.String CPFCliente;

    private java.lang.String usuarioBaixa;

    private com.bonsucesso.servipa.ws.TPrestacao dadosPrestacao;

    public TItemRetornoRecebimentoDia() {
    }

    public TItemRetornoRecebimentoDia(
           int nrLoja,
           java.lang.String nrContrato,
           int nrPrestacao,
           java.lang.String nrDocumento,
           boolean recebidoLoja,
           java.lang.String nomeCliente,
           java.lang.String CPFCliente,
           java.lang.String usuarioBaixa,
           com.bonsucesso.servipa.ws.TPrestacao dadosPrestacao) {
           this.nrLoja = nrLoja;
           this.nrContrato = nrContrato;
           this.nrPrestacao = nrPrestacao;
           this.nrDocumento = nrDocumento;
           this.recebidoLoja = recebidoLoja;
           this.nomeCliente = nomeCliente;
           this.CPFCliente = CPFCliente;
           this.usuarioBaixa = usuarioBaixa;
           this.dadosPrestacao = dadosPrestacao;
    }


    /**
     * Gets the nrLoja value for this TItemRetornoRecebimentoDia.
     * 
     * @return nrLoja
     */
    public int getNrLoja() {
        return nrLoja;
    }


    /**
     * Sets the nrLoja value for this TItemRetornoRecebimentoDia.
     * 
     * @param nrLoja
     */
    public void setNrLoja(int nrLoja) {
        this.nrLoja = nrLoja;
    }


    /**
     * Gets the nrContrato value for this TItemRetornoRecebimentoDia.
     * 
     * @return nrContrato
     */
    public java.lang.String getNrContrato() {
        return nrContrato;
    }


    /**
     * Sets the nrContrato value for this TItemRetornoRecebimentoDia.
     * 
     * @param nrContrato
     */
    public void setNrContrato(java.lang.String nrContrato) {
        this.nrContrato = nrContrato;
    }


    /**
     * Gets the nrPrestacao value for this TItemRetornoRecebimentoDia.
     * 
     * @return nrPrestacao
     */
    public int getNrPrestacao() {
        return nrPrestacao;
    }


    /**
     * Sets the nrPrestacao value for this TItemRetornoRecebimentoDia.
     * 
     * @param nrPrestacao
     */
    public void setNrPrestacao(int nrPrestacao) {
        this.nrPrestacao = nrPrestacao;
    }


    /**
     * Gets the nrDocumento value for this TItemRetornoRecebimentoDia.
     * 
     * @return nrDocumento
     */
    public java.lang.String getNrDocumento() {
        return nrDocumento;
    }


    /**
     * Sets the nrDocumento value for this TItemRetornoRecebimentoDia.
     * 
     * @param nrDocumento
     */
    public void setNrDocumento(java.lang.String nrDocumento) {
        this.nrDocumento = nrDocumento;
    }


    /**
     * Gets the recebidoLoja value for this TItemRetornoRecebimentoDia.
     * 
     * @return recebidoLoja
     */
    public boolean isRecebidoLoja() {
        return recebidoLoja;
    }


    /**
     * Sets the recebidoLoja value for this TItemRetornoRecebimentoDia.
     * 
     * @param recebidoLoja
     */
    public void setRecebidoLoja(boolean recebidoLoja) {
        this.recebidoLoja = recebidoLoja;
    }


    /**
     * Gets the nomeCliente value for this TItemRetornoRecebimentoDia.
     * 
     * @return nomeCliente
     */
    public java.lang.String getNomeCliente() {
        return nomeCliente;
    }


    /**
     * Sets the nomeCliente value for this TItemRetornoRecebimentoDia.
     * 
     * @param nomeCliente
     */
    public void setNomeCliente(java.lang.String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }


    /**
     * Gets the CPFCliente value for this TItemRetornoRecebimentoDia.
     * 
     * @return CPFCliente
     */
    public java.lang.String getCPFCliente() {
        return CPFCliente;
    }


    /**
     * Sets the CPFCliente value for this TItemRetornoRecebimentoDia.
     * 
     * @param CPFCliente
     */
    public void setCPFCliente(java.lang.String CPFCliente) {
        this.CPFCliente = CPFCliente;
    }


    /**
     * Gets the usuarioBaixa value for this TItemRetornoRecebimentoDia.
     * 
     * @return usuarioBaixa
     */
    public java.lang.String getUsuarioBaixa() {
        return usuarioBaixa;
    }


    /**
     * Sets the usuarioBaixa value for this TItemRetornoRecebimentoDia.
     * 
     * @param usuarioBaixa
     */
    public void setUsuarioBaixa(java.lang.String usuarioBaixa) {
        this.usuarioBaixa = usuarioBaixa;
    }


    /**
     * Gets the dadosPrestacao value for this TItemRetornoRecebimentoDia.
     * 
     * @return dadosPrestacao
     */
    public com.bonsucesso.servipa.ws.TPrestacao getDadosPrestacao() {
        return dadosPrestacao;
    }


    /**
     * Sets the dadosPrestacao value for this TItemRetornoRecebimentoDia.
     * 
     * @param dadosPrestacao
     */
    public void setDadosPrestacao(com.bonsucesso.servipa.ws.TPrestacao dadosPrestacao) {
        this.dadosPrestacao = dadosPrestacao;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemRetornoRecebimentoDia)) return false;
        TItemRetornoRecebimentoDia other = (TItemRetornoRecebimentoDia) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.nrLoja == other.getNrLoja() &&
            ((this.nrContrato==null && other.getNrContrato()==null) || 
             (this.nrContrato!=null &&
              this.nrContrato.equals(other.getNrContrato()))) &&
            this.nrPrestacao == other.getNrPrestacao() &&
            ((this.nrDocumento==null && other.getNrDocumento()==null) || 
             (this.nrDocumento!=null &&
              this.nrDocumento.equals(other.getNrDocumento()))) &&
            this.recebidoLoja == other.isRecebidoLoja() &&
            ((this.nomeCliente==null && other.getNomeCliente()==null) || 
             (this.nomeCliente!=null &&
              this.nomeCliente.equals(other.getNomeCliente()))) &&
            ((this.CPFCliente==null && other.getCPFCliente()==null) || 
             (this.CPFCliente!=null &&
              this.CPFCliente.equals(other.getCPFCliente()))) &&
            ((this.usuarioBaixa==null && other.getUsuarioBaixa()==null) || 
             (this.usuarioBaixa!=null &&
              this.usuarioBaixa.equals(other.getUsuarioBaixa()))) &&
            ((this.dadosPrestacao==null && other.getDadosPrestacao()==null) || 
             (this.dadosPrestacao!=null &&
              this.dadosPrestacao.equals(other.getDadosPrestacao())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getNrLoja();
        if (getNrContrato() != null) {
            _hashCode += getNrContrato().hashCode();
        }
        _hashCode += getNrPrestacao();
        if (getNrDocumento() != null) {
            _hashCode += getNrDocumento().hashCode();
        }
        _hashCode += (isRecebidoLoja() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getNomeCliente() != null) {
            _hashCode += getNomeCliente().hashCode();
        }
        if (getCPFCliente() != null) {
            _hashCode += getCPFCliente().hashCode();
        }
        if (getUsuarioBaixa() != null) {
            _hashCode += getUsuarioBaixa().hashCode();
        }
        if (getDadosPrestacao() != null) {
            _hashCode += getDadosPrestacao().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TItemRetornoRecebimentoDia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRetornoRecebimentoDia"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrLoja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrLoja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrContrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrPrestacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrPrestacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recebidoLoja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RecebidoLoja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NomeCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CPFCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CPFCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuarioBaixa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UsuarioBaixa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dadosPrestacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DadosPrestacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TPrestacao"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
