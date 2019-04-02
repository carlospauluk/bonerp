/**
 * TRetContrato.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetContrato  implements java.io.Serializable {
    private int codigo;

    private java.lang.String mensagem;

    private com.bonsucesso.servipa.ws.TContratoRetorno[] listContrato;

    private int tipoContrato;

    public TRetContrato() {
    }

    public TRetContrato(
           int codigo,
           java.lang.String mensagem,
           com.bonsucesso.servipa.ws.TContratoRetorno[] listContrato,
           int tipoContrato) {
           this.codigo = codigo;
           this.mensagem = mensagem;
           this.listContrato = listContrato;
           this.tipoContrato = tipoContrato;
    }


    /**
     * Gets the codigo value for this TRetContrato.
     * 
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this TRetContrato.
     * 
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the mensagem value for this TRetContrato.
     * 
     * @return mensagem
     */
    public java.lang.String getMensagem() {
        return mensagem;
    }


    /**
     * Sets the mensagem value for this TRetContrato.
     * 
     * @param mensagem
     */
    public void setMensagem(java.lang.String mensagem) {
        this.mensagem = mensagem;
    }


    /**
     * Gets the listContrato value for this TRetContrato.
     * 
     * @return listContrato
     */
    public com.bonsucesso.servipa.ws.TContratoRetorno[] getListContrato() {
        return listContrato;
    }


    /**
     * Sets the listContrato value for this TRetContrato.
     * 
     * @param listContrato
     */
    public void setListContrato(com.bonsucesso.servipa.ws.TContratoRetorno[] listContrato) {
        this.listContrato = listContrato;
    }


    /**
     * Gets the tipoContrato value for this TRetContrato.
     * 
     * @return tipoContrato
     */
    public int getTipoContrato() {
        return tipoContrato;
    }


    /**
     * Sets the tipoContrato value for this TRetContrato.
     * 
     * @param tipoContrato
     */
    public void setTipoContrato(int tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetContrato)) return false;
        TRetContrato other = (TRetContrato) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigo == other.getCodigo() &&
            ((this.mensagem==null && other.getMensagem()==null) || 
             (this.mensagem!=null &&
              this.mensagem.equals(other.getMensagem()))) &&
            ((this.listContrato==null && other.getListContrato()==null) || 
             (this.listContrato!=null &&
              java.util.Arrays.equals(this.listContrato, other.getListContrato()))) &&
            this.tipoContrato == other.getTipoContrato();
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
        _hashCode += getCodigo();
        if (getMensagem() != null) {
            _hashCode += getMensagem().hashCode();
        }
        if (getListContrato() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListContrato());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListContrato(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getTipoContrato();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TRetContrato.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetContrato"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensagem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Mensagem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listContrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TContratoRetorno"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoContrato"));
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
