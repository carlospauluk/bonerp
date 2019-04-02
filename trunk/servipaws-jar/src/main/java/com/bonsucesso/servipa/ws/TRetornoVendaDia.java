/**
 * TRetornoVendaDia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetornoVendaDia  implements java.io.Serializable {
    private int codigo;

    private java.lang.String mensagem;

    private com.bonsucesso.servipa.ws.TItemRetornoVendaDia[] listaVenda;

    public TRetornoVendaDia() {
    }

    public TRetornoVendaDia(
           int codigo,
           java.lang.String mensagem,
           com.bonsucesso.servipa.ws.TItemRetornoVendaDia[] listaVenda) {
           this.codigo = codigo;
           this.mensagem = mensagem;
           this.listaVenda = listaVenda;
    }


    /**
     * Gets the codigo value for this TRetornoVendaDia.
     * 
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this TRetornoVendaDia.
     * 
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the mensagem value for this TRetornoVendaDia.
     * 
     * @return mensagem
     */
    public java.lang.String getMensagem() {
        return mensagem;
    }


    /**
     * Sets the mensagem value for this TRetornoVendaDia.
     * 
     * @param mensagem
     */
    public void setMensagem(java.lang.String mensagem) {
        this.mensagem = mensagem;
    }


    /**
     * Gets the listaVenda value for this TRetornoVendaDia.
     * 
     * @return listaVenda
     */
    public com.bonsucesso.servipa.ws.TItemRetornoVendaDia[] getListaVenda() {
        return listaVenda;
    }


    /**
     * Sets the listaVenda value for this TRetornoVendaDia.
     * 
     * @param listaVenda
     */
    public void setListaVenda(com.bonsucesso.servipa.ws.TItemRetornoVendaDia[] listaVenda) {
        this.listaVenda = listaVenda;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetornoVendaDia)) return false;
        TRetornoVendaDia other = (TRetornoVendaDia) obj;
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
            ((this.listaVenda==null && other.getListaVenda()==null) || 
             (this.listaVenda!=null &&
              java.util.Arrays.equals(this.listaVenda, other.getListaVenda())));
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
        if (getListaVenda() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListaVenda());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListaVenda(), i);
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
        new org.apache.axis.description.TypeDesc(TRetornoVendaDia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetornoVendaDia"));
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
        elemField.setFieldName("listaVenda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ListaVenda"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRetornoVendaDia"));
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
