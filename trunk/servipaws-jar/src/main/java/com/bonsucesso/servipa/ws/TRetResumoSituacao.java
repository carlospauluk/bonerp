/**
 * TRetResumoSituacao.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetResumoSituacao  implements java.io.Serializable {
    private java.lang.String dsNomeCliente;

    private float vlMediaCompra;

    private float vlMediaAtraso;

    private int nrPagoQuantidade;

    private float vlPagoValor;

    private int nrAbertoQuantidade;

    private float vlAbertoValor;

    private int nrVencidoQuantidade;

    private float vlVencidoValor;

    public TRetResumoSituacao() {
    }

    public TRetResumoSituacao(
           java.lang.String dsNomeCliente,
           float vlMediaCompra,
           float vlMediaAtraso,
           int nrPagoQuantidade,
           float vlPagoValor,
           int nrAbertoQuantidade,
           float vlAbertoValor,
           int nrVencidoQuantidade,
           float vlVencidoValor) {
           this.dsNomeCliente = dsNomeCliente;
           this.vlMediaCompra = vlMediaCompra;
           this.vlMediaAtraso = vlMediaAtraso;
           this.nrPagoQuantidade = nrPagoQuantidade;
           this.vlPagoValor = vlPagoValor;
           this.nrAbertoQuantidade = nrAbertoQuantidade;
           this.vlAbertoValor = vlAbertoValor;
           this.nrVencidoQuantidade = nrVencidoQuantidade;
           this.vlVencidoValor = vlVencidoValor;
    }


    /**
     * Gets the dsNomeCliente value for this TRetResumoSituacao.
     * 
     * @return dsNomeCliente
     */
    public java.lang.String getDsNomeCliente() {
        return dsNomeCliente;
    }


    /**
     * Sets the dsNomeCliente value for this TRetResumoSituacao.
     * 
     * @param dsNomeCliente
     */
    public void setDsNomeCliente(java.lang.String dsNomeCliente) {
        this.dsNomeCliente = dsNomeCliente;
    }


    /**
     * Gets the vlMediaCompra value for this TRetResumoSituacao.
     * 
     * @return vlMediaCompra
     */
    public float getVlMediaCompra() {
        return vlMediaCompra;
    }


    /**
     * Sets the vlMediaCompra value for this TRetResumoSituacao.
     * 
     * @param vlMediaCompra
     */
    public void setVlMediaCompra(float vlMediaCompra) {
        this.vlMediaCompra = vlMediaCompra;
    }


    /**
     * Gets the vlMediaAtraso value for this TRetResumoSituacao.
     * 
     * @return vlMediaAtraso
     */
    public float getVlMediaAtraso() {
        return vlMediaAtraso;
    }


    /**
     * Sets the vlMediaAtraso value for this TRetResumoSituacao.
     * 
     * @param vlMediaAtraso
     */
    public void setVlMediaAtraso(float vlMediaAtraso) {
        this.vlMediaAtraso = vlMediaAtraso;
    }


    /**
     * Gets the nrPagoQuantidade value for this TRetResumoSituacao.
     * 
     * @return nrPagoQuantidade
     */
    public int getNrPagoQuantidade() {
        return nrPagoQuantidade;
    }


    /**
     * Sets the nrPagoQuantidade value for this TRetResumoSituacao.
     * 
     * @param nrPagoQuantidade
     */
    public void setNrPagoQuantidade(int nrPagoQuantidade) {
        this.nrPagoQuantidade = nrPagoQuantidade;
    }


    /**
     * Gets the vlPagoValor value for this TRetResumoSituacao.
     * 
     * @return vlPagoValor
     */
    public float getVlPagoValor() {
        return vlPagoValor;
    }


    /**
     * Sets the vlPagoValor value for this TRetResumoSituacao.
     * 
     * @param vlPagoValor
     */
    public void setVlPagoValor(float vlPagoValor) {
        this.vlPagoValor = vlPagoValor;
    }


    /**
     * Gets the nrAbertoQuantidade value for this TRetResumoSituacao.
     * 
     * @return nrAbertoQuantidade
     */
    public int getNrAbertoQuantidade() {
        return nrAbertoQuantidade;
    }


    /**
     * Sets the nrAbertoQuantidade value for this TRetResumoSituacao.
     * 
     * @param nrAbertoQuantidade
     */
    public void setNrAbertoQuantidade(int nrAbertoQuantidade) {
        this.nrAbertoQuantidade = nrAbertoQuantidade;
    }


    /**
     * Gets the vlAbertoValor value for this TRetResumoSituacao.
     * 
     * @return vlAbertoValor
     */
    public float getVlAbertoValor() {
        return vlAbertoValor;
    }


    /**
     * Sets the vlAbertoValor value for this TRetResumoSituacao.
     * 
     * @param vlAbertoValor
     */
    public void setVlAbertoValor(float vlAbertoValor) {
        this.vlAbertoValor = vlAbertoValor;
    }


    /**
     * Gets the nrVencidoQuantidade value for this TRetResumoSituacao.
     * 
     * @return nrVencidoQuantidade
     */
    public int getNrVencidoQuantidade() {
        return nrVencidoQuantidade;
    }


    /**
     * Sets the nrVencidoQuantidade value for this TRetResumoSituacao.
     * 
     * @param nrVencidoQuantidade
     */
    public void setNrVencidoQuantidade(int nrVencidoQuantidade) {
        this.nrVencidoQuantidade = nrVencidoQuantidade;
    }


    /**
     * Gets the vlVencidoValor value for this TRetResumoSituacao.
     * 
     * @return vlVencidoValor
     */
    public float getVlVencidoValor() {
        return vlVencidoValor;
    }


    /**
     * Sets the vlVencidoValor value for this TRetResumoSituacao.
     * 
     * @param vlVencidoValor
     */
    public void setVlVencidoValor(float vlVencidoValor) {
        this.vlVencidoValor = vlVencidoValor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetResumoSituacao)) return false;
        TRetResumoSituacao other = (TRetResumoSituacao) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dsNomeCliente==null && other.getDsNomeCliente()==null) || 
             (this.dsNomeCliente!=null &&
              this.dsNomeCliente.equals(other.getDsNomeCliente()))) &&
            this.vlMediaCompra == other.getVlMediaCompra() &&
            this.vlMediaAtraso == other.getVlMediaAtraso() &&
            this.nrPagoQuantidade == other.getNrPagoQuantidade() &&
            this.vlPagoValor == other.getVlPagoValor() &&
            this.nrAbertoQuantidade == other.getNrAbertoQuantidade() &&
            this.vlAbertoValor == other.getVlAbertoValor() &&
            this.nrVencidoQuantidade == other.getNrVencidoQuantidade() &&
            this.vlVencidoValor == other.getVlVencidoValor();
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
        if (getDsNomeCliente() != null) {
            _hashCode += getDsNomeCliente().hashCode();
        }
        _hashCode += new Float(getVlMediaCompra()).hashCode();
        _hashCode += new Float(getVlMediaAtraso()).hashCode();
        _hashCode += getNrPagoQuantidade();
        _hashCode += new Float(getVlPagoValor()).hashCode();
        _hashCode += getNrAbertoQuantidade();
        _hashCode += new Float(getVlAbertoValor()).hashCode();
        _hashCode += getNrVencidoQuantidade();
        _hashCode += new Float(getVlVencidoValor()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TRetResumoSituacao.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetResumoSituacao"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsNomeCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsNomeCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlMediaCompra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlMediaCompra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlMediaAtraso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlMediaAtraso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrPagoQuantidade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrPagoQuantidade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlPagoValor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlPagoValor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrAbertoQuantidade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrAbertoQuantidade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlAbertoValor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlAbertoValor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrVencidoQuantidade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrVencidoQuantidade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlVencidoValor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlVencidoValor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
