/**
 * TRetornoRecebimentoCobradora.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetornoRecebimentoCobradora  implements java.io.Serializable {
    private int codigo;

    private java.lang.String mensagem;

    private com.bonsucesso.servipa.ws.TItemRetornoRecebimentoCobradora[] listaRecebimentoCobradora;

    private com.bonsucesso.servipa.ws.TItemRetornoRecebimentoCobradoraBoleto[] listaRecebimentoCobradoraBoleto;

    public TRetornoRecebimentoCobradora() {
    }

    public TRetornoRecebimentoCobradora(
           int codigo,
           java.lang.String mensagem,
           com.bonsucesso.servipa.ws.TItemRetornoRecebimentoCobradora[] listaRecebimentoCobradora,
           com.bonsucesso.servipa.ws.TItemRetornoRecebimentoCobradoraBoleto[] listaRecebimentoCobradoraBoleto) {
           this.codigo = codigo;
           this.mensagem = mensagem;
           this.listaRecebimentoCobradora = listaRecebimentoCobradora;
           this.listaRecebimentoCobradoraBoleto = listaRecebimentoCobradoraBoleto;
    }


    /**
     * Gets the codigo value for this TRetornoRecebimentoCobradora.
     * 
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this TRetornoRecebimentoCobradora.
     * 
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the mensagem value for this TRetornoRecebimentoCobradora.
     * 
     * @return mensagem
     */
    public java.lang.String getMensagem() {
        return mensagem;
    }


    /**
     * Sets the mensagem value for this TRetornoRecebimentoCobradora.
     * 
     * @param mensagem
     */
    public void setMensagem(java.lang.String mensagem) {
        this.mensagem = mensagem;
    }


    /**
     * Gets the listaRecebimentoCobradora value for this TRetornoRecebimentoCobradora.
     * 
     * @return listaRecebimentoCobradora
     */
    public com.bonsucesso.servipa.ws.TItemRetornoRecebimentoCobradora[] getListaRecebimentoCobradora() {
        return listaRecebimentoCobradora;
    }


    /**
     * Sets the listaRecebimentoCobradora value for this TRetornoRecebimentoCobradora.
     * 
     * @param listaRecebimentoCobradora
     */
    public void setListaRecebimentoCobradora(com.bonsucesso.servipa.ws.TItemRetornoRecebimentoCobradora[] listaRecebimentoCobradora) {
        this.listaRecebimentoCobradora = listaRecebimentoCobradora;
    }


    /**
     * Gets the listaRecebimentoCobradoraBoleto value for this TRetornoRecebimentoCobradora.
     * 
     * @return listaRecebimentoCobradoraBoleto
     */
    public com.bonsucesso.servipa.ws.TItemRetornoRecebimentoCobradoraBoleto[] getListaRecebimentoCobradoraBoleto() {
        return listaRecebimentoCobradoraBoleto;
    }


    /**
     * Sets the listaRecebimentoCobradoraBoleto value for this TRetornoRecebimentoCobradora.
     * 
     * @param listaRecebimentoCobradoraBoleto
     */
    public void setListaRecebimentoCobradoraBoleto(com.bonsucesso.servipa.ws.TItemRetornoRecebimentoCobradoraBoleto[] listaRecebimentoCobradoraBoleto) {
        this.listaRecebimentoCobradoraBoleto = listaRecebimentoCobradoraBoleto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetornoRecebimentoCobradora)) return false;
        TRetornoRecebimentoCobradora other = (TRetornoRecebimentoCobradora) obj;
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
            ((this.listaRecebimentoCobradora==null && other.getListaRecebimentoCobradora()==null) || 
             (this.listaRecebimentoCobradora!=null &&
              java.util.Arrays.equals(this.listaRecebimentoCobradora, other.getListaRecebimentoCobradora()))) &&
            ((this.listaRecebimentoCobradoraBoleto==null && other.getListaRecebimentoCobradoraBoleto()==null) || 
             (this.listaRecebimentoCobradoraBoleto!=null &&
              java.util.Arrays.equals(this.listaRecebimentoCobradoraBoleto, other.getListaRecebimentoCobradoraBoleto())));
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
        if (getListaRecebimentoCobradora() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaRecebimentoCobradora());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaRecebimentoCobradora(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getListaRecebimentoCobradoraBoleto() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaRecebimentoCobradoraBoleto());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaRecebimentoCobradoraBoleto(), i);
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
        new org.apache.axis.description.TypeDesc(TRetornoRecebimentoCobradora.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetornoRecebimentoCobradora"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensagem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensagem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaRecebimentoCobradora");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ListaRecebimentoCobradora"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRetornoRecebimentoCobradora"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listaRecebimentoCobradoraBoleto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ListaRecebimentoCobradoraBoleto"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRetornoRecebimentoCobradoraBoleto"));
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
