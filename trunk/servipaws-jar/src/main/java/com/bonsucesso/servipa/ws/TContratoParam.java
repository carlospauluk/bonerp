/**
 * TContratoParam.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TContratoParam  implements java.io.Serializable {
    private java.lang.String nrContrato;

    private int nrLoja;

    private int nrPrestacao;

    public TContratoParam() {
    }

    public TContratoParam(
           java.lang.String nrContrato,
           int nrLoja,
           int nrPrestacao) {
           this.nrContrato = nrContrato;
           this.nrLoja = nrLoja;
           this.nrPrestacao = nrPrestacao;
    }


    /**
     * Gets the nrContrato value for this TContratoParam.
     * 
     * @return nrContrato
     */
    public java.lang.String getNrContrato() {
        return nrContrato;
    }


    /**
     * Sets the nrContrato value for this TContratoParam.
     * 
     * @param nrContrato
     */
    public void setNrContrato(java.lang.String nrContrato) {
        this.nrContrato = nrContrato;
    }


    /**
     * Gets the nrLoja value for this TContratoParam.
     * 
     * @return nrLoja
     */
    public int getNrLoja() {
        return nrLoja;
    }


    /**
     * Sets the nrLoja value for this TContratoParam.
     * 
     * @param nrLoja
     */
    public void setNrLoja(int nrLoja) {
        this.nrLoja = nrLoja;
    }


    /**
     * Gets the nrPrestacao value for this TContratoParam.
     * 
     * @return nrPrestacao
     */
    public int getNrPrestacao() {
        return nrPrestacao;
    }


    /**
     * Sets the nrPrestacao value for this TContratoParam.
     * 
     * @param nrPrestacao
     */
    public void setNrPrestacao(int nrPrestacao) {
        this.nrPrestacao = nrPrestacao;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TContratoParam)) return false;
        TContratoParam other = (TContratoParam) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nrContrato==null && other.getNrContrato()==null) || 
             (this.nrContrato!=null &&
              this.nrContrato.equals(other.getNrContrato()))) &&
            this.nrLoja == other.getNrLoja() &&
            this.nrPrestacao == other.getNrPrestacao();
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
        if (getNrContrato() != null) {
            _hashCode += getNrContrato().hashCode();
        }
        _hashCode += getNrLoja();
        _hashCode += getNrPrestacao();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TContratoParam.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TContratoParam"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrContrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrLoja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrLoja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrPrestacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrPrestacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
