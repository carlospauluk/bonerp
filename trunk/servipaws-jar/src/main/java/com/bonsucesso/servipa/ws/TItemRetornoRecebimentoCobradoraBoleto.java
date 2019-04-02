/**
 * TItemRetornoRecebimentoCobradoraBoleto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemRetornoRecebimentoCobradoraBoleto  implements java.io.Serializable {
    private java.lang.String nossonumero;

    private java.lang.String linhadigitavel;

    private java.lang.String codigobarras;

    private float vlrPago;

    public TItemRetornoRecebimentoCobradoraBoleto() {
    }

    public TItemRetornoRecebimentoCobradoraBoleto(
           java.lang.String nossonumero,
           java.lang.String linhadigitavel,
           java.lang.String codigobarras,
           float vlrPago) {
           this.nossonumero = nossonumero;
           this.linhadigitavel = linhadigitavel;
           this.codigobarras = codigobarras;
           this.vlrPago = vlrPago;
    }


    /**
     * Gets the nossonumero value for this TItemRetornoRecebimentoCobradoraBoleto.
     * 
     * @return nossonumero
     */
    public java.lang.String getNossonumero() {
        return nossonumero;
    }


    /**
     * Sets the nossonumero value for this TItemRetornoRecebimentoCobradoraBoleto.
     * 
     * @param nossonumero
     */
    public void setNossonumero(java.lang.String nossonumero) {
        this.nossonumero = nossonumero;
    }


    /**
     * Gets the linhadigitavel value for this TItemRetornoRecebimentoCobradoraBoleto.
     * 
     * @return linhadigitavel
     */
    public java.lang.String getLinhadigitavel() {
        return linhadigitavel;
    }


    /**
     * Sets the linhadigitavel value for this TItemRetornoRecebimentoCobradoraBoleto.
     * 
     * @param linhadigitavel
     */
    public void setLinhadigitavel(java.lang.String linhadigitavel) {
        this.linhadigitavel = linhadigitavel;
    }


    /**
     * Gets the codigobarras value for this TItemRetornoRecebimentoCobradoraBoleto.
     * 
     * @return codigobarras
     */
    public java.lang.String getCodigobarras() {
        return codigobarras;
    }


    /**
     * Sets the codigobarras value for this TItemRetornoRecebimentoCobradoraBoleto.
     * 
     * @param codigobarras
     */
    public void setCodigobarras(java.lang.String codigobarras) {
        this.codigobarras = codigobarras;
    }


    /**
     * Gets the vlrPago value for this TItemRetornoRecebimentoCobradoraBoleto.
     * 
     * @return vlrPago
     */
    public float getVlrPago() {
        return vlrPago;
    }


    /**
     * Sets the vlrPago value for this TItemRetornoRecebimentoCobradoraBoleto.
     * 
     * @param vlrPago
     */
    public void setVlrPago(float vlrPago) {
        this.vlrPago = vlrPago;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemRetornoRecebimentoCobradoraBoleto)) return false;
        TItemRetornoRecebimentoCobradoraBoleto other = (TItemRetornoRecebimentoCobradoraBoleto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nossonumero==null && other.getNossonumero()==null) || 
             (this.nossonumero!=null &&
              this.nossonumero.equals(other.getNossonumero()))) &&
            ((this.linhadigitavel==null && other.getLinhadigitavel()==null) || 
             (this.linhadigitavel!=null &&
              this.linhadigitavel.equals(other.getLinhadigitavel()))) &&
            ((this.codigobarras==null && other.getCodigobarras()==null) || 
             (this.codigobarras!=null &&
              this.codigobarras.equals(other.getCodigobarras()))) &&
            this.vlrPago == other.getVlrPago();
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
        if (getNossonumero() != null) {
            _hashCode += getNossonumero().hashCode();
        }
        if (getLinhadigitavel() != null) {
            _hashCode += getLinhadigitavel().hashCode();
        }
        if (getCodigobarras() != null) {
            _hashCode += getCodigobarras().hashCode();
        }
        _hashCode += new Float(getVlrPago()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TItemRetornoRecebimentoCobradoraBoleto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRetornoRecebimentoCobradoraBoleto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nossonumero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nossonumero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linhadigitavel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "linhadigitavel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigobarras");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigobarras"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlrPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlrPago"));
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
