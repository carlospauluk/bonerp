/**
 * TRetornoConsultaListaCidadeDados.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetornoConsultaListaCidadeDados  implements java.io.Serializable {
    private java.lang.String dsCidade;

    private int nrCodigo;

    private java.lang.String dsCEP;

    public TRetornoConsultaListaCidadeDados() {
    }

    public TRetornoConsultaListaCidadeDados(
           java.lang.String dsCidade,
           int nrCodigo,
           java.lang.String dsCEP) {
           this.dsCidade = dsCidade;
           this.nrCodigo = nrCodigo;
           this.dsCEP = dsCEP;
    }


    /**
     * Gets the dsCidade value for this TRetornoConsultaListaCidadeDados.
     * 
     * @return dsCidade
     */
    public java.lang.String getDsCidade() {
        return dsCidade;
    }


    /**
     * Sets the dsCidade value for this TRetornoConsultaListaCidadeDados.
     * 
     * @param dsCidade
     */
    public void setDsCidade(java.lang.String dsCidade) {
        this.dsCidade = dsCidade;
    }


    /**
     * Gets the nrCodigo value for this TRetornoConsultaListaCidadeDados.
     * 
     * @return nrCodigo
     */
    public int getNrCodigo() {
        return nrCodigo;
    }


    /**
     * Sets the nrCodigo value for this TRetornoConsultaListaCidadeDados.
     * 
     * @param nrCodigo
     */
    public void setNrCodigo(int nrCodigo) {
        this.nrCodigo = nrCodigo;
    }


    /**
     * Gets the dsCEP value for this TRetornoConsultaListaCidadeDados.
     * 
     * @return dsCEP
     */
    public java.lang.String getDsCEP() {
        return dsCEP;
    }


    /**
     * Sets the dsCEP value for this TRetornoConsultaListaCidadeDados.
     * 
     * @param dsCEP
     */
    public void setDsCEP(java.lang.String dsCEP) {
        this.dsCEP = dsCEP;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetornoConsultaListaCidadeDados)) return false;
        TRetornoConsultaListaCidadeDados other = (TRetornoConsultaListaCidadeDados) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dsCidade==null && other.getDsCidade()==null) || 
             (this.dsCidade!=null &&
              this.dsCidade.equals(other.getDsCidade()))) &&
            this.nrCodigo == other.getNrCodigo() &&
            ((this.dsCEP==null && other.getDsCEP()==null) || 
             (this.dsCEP!=null &&
              this.dsCEP.equals(other.getDsCEP())));
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
        if (getDsCidade() != null) {
            _hashCode += getDsCidade().hashCode();
        }
        _hashCode += getNrCodigo();
        if (getDsCEP() != null) {
            _hashCode += getDsCEP().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TRetornoConsultaListaCidadeDados.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetornoConsultaListaCidadeDados"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsCidade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsCidade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrCodigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrCodigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsCEP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsCEP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
