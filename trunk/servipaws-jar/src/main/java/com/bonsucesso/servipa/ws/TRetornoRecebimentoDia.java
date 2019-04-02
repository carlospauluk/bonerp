/**
 * TRetornoRecebimentoDia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetornoRecebimentoDia  implements java.io.Serializable {
    private int codigo;

    private java.lang.String mensagem;

    private com.bonsucesso.servipa.ws.TItemRetornoRecebimentoDia[] listaRecebimento;

    public TRetornoRecebimentoDia() {
    }

    public TRetornoRecebimentoDia(
           int codigo,
           java.lang.String mensagem,
           com.bonsucesso.servipa.ws.TItemRetornoRecebimentoDia[] listaRecebimento) {
           this.codigo = codigo;
           this.mensagem = mensagem;
           this.listaRecebimento = listaRecebimento;
    }


    /**
     * Gets the codigo value for this TRetornoRecebimentoDia.
     * 
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this TRetornoRecebimentoDia.
     * 
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the mensagem value for this TRetornoRecebimentoDia.
     * 
     * @return mensagem
     */
    public java.lang.String getMensagem() {
        return mensagem;
    }


    /**
     * Sets the mensagem value for this TRetornoRecebimentoDia.
     * 
     * @param mensagem
     */
    public void setMensagem(java.lang.String mensagem) {
        this.mensagem = mensagem;
    }


    /**
     * Gets the listaRecebimento value for this TRetornoRecebimentoDia.
     * 
     * @return listaRecebimento
     */
    public com.bonsucesso.servipa.ws.TItemRetornoRecebimentoDia[] getListaRecebimento() {
        return listaRecebimento;
    }


    /**
     * Sets the listaRecebimento value for this TRetornoRecebimentoDia.
     * 
     * @param listaRecebimento
     */
    public void setListaRecebimento(com.bonsucesso.servipa.ws.TItemRetornoRecebimentoDia[] listaRecebimento) {
        this.listaRecebimento = listaRecebimento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetornoRecebimentoDia)) return false;
        TRetornoRecebimentoDia other = (TRetornoRecebimentoDia) obj;
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
            ((this.listaRecebimento==null && other.getListaRecebimento()==null) || 
             (this.listaRecebimento!=null &&
              java.util.Arrays.equals(this.listaRecebimento, other.getListaRecebimento())));
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
        if (getListaRecebimento() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaRecebimento());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaRecebimento(), i);
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
        new org.apache.axis.description.TypeDesc(TRetornoRecebimentoDia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetornoRecebimentoDia"));
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
        elemField.setFieldName("listaRecebimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ListaRecebimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRetornoRecebimentoDia"));
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
