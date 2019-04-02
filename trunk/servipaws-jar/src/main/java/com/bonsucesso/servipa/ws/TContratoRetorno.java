/**
 * TContratoRetorno.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TContratoRetorno  implements java.io.Serializable {
    private java.lang.String nrContrato;

    private com.bonsucesso.servipa.ws.TPrestacao[] listPrestacao;

    public TContratoRetorno() {
    }

    public TContratoRetorno(
           java.lang.String nrContrato,
           com.bonsucesso.servipa.ws.TPrestacao[] listPrestacao) {
           this.nrContrato = nrContrato;
           this.listPrestacao = listPrestacao;
    }


    /**
     * Gets the nrContrato value for this TContratoRetorno.
     * 
     * @return nrContrato
     */
    public java.lang.String getNrContrato() {
        return nrContrato;
    }


    /**
     * Sets the nrContrato value for this TContratoRetorno.
     * 
     * @param nrContrato
     */
    public void setNrContrato(java.lang.String nrContrato) {
        this.nrContrato = nrContrato;
    }


    /**
     * Gets the listPrestacao value for this TContratoRetorno.
     * 
     * @return listPrestacao
     */
    public com.bonsucesso.servipa.ws.TPrestacao[] getListPrestacao() {
        return listPrestacao;
    }


    /**
     * Sets the listPrestacao value for this TContratoRetorno.
     * 
     * @param listPrestacao
     */
    public void setListPrestacao(com.bonsucesso.servipa.ws.TPrestacao[] listPrestacao) {
        this.listPrestacao = listPrestacao;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TContratoRetorno)) return false;
        TContratoRetorno other = (TContratoRetorno) obj;
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
            ((this.listPrestacao==null && other.getListPrestacao()==null) || 
             (this.listPrestacao!=null &&
              java.util.Arrays.equals(this.listPrestacao, other.getListPrestacao())));
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
        if (getListPrestacao() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListPrestacao());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListPrestacao(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TContratoRetorno.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TContratoRetorno"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrContrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listPrestacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listPrestacao"));
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
