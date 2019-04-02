/**
 * EnviaArquivoOfflineParam.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class EnviaArquivoOfflineParam  implements java.io.Serializable {
    private java.math.BigInteger lojcodigo;

    private java.lang.String nomearquivo;

    private java.lang.String arquivo;

    public EnviaArquivoOfflineParam() {
    }

    public EnviaArquivoOfflineParam(
           java.math.BigInteger lojcodigo,
           java.lang.String nomearquivo,
           java.lang.String arquivo) {
           this.lojcodigo = lojcodigo;
           this.nomearquivo = nomearquivo;
           this.arquivo = arquivo;
    }


    /**
     * Gets the lojcodigo value for this EnviaArquivoOfflineParam.
     * 
     * @return lojcodigo
     */
    public java.math.BigInteger getLojcodigo() {
        return lojcodigo;
    }


    /**
     * Sets the lojcodigo value for this EnviaArquivoOfflineParam.
     * 
     * @param lojcodigo
     */
    public void setLojcodigo(java.math.BigInteger lojcodigo) {
        this.lojcodigo = lojcodigo;
    }


    /**
     * Gets the nomearquivo value for this EnviaArquivoOfflineParam.
     * 
     * @return nomearquivo
     */
    public java.lang.String getNomearquivo() {
        return nomearquivo;
    }


    /**
     * Sets the nomearquivo value for this EnviaArquivoOfflineParam.
     * 
     * @param nomearquivo
     */
    public void setNomearquivo(java.lang.String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }


    /**
     * Gets the arquivo value for this EnviaArquivoOfflineParam.
     * 
     * @return arquivo
     */
    public java.lang.String getArquivo() {
        return arquivo;
    }


    /**
     * Sets the arquivo value for this EnviaArquivoOfflineParam.
     * 
     * @param arquivo
     */
    public void setArquivo(java.lang.String arquivo) {
        this.arquivo = arquivo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EnviaArquivoOfflineParam)) return false;
        EnviaArquivoOfflineParam other = (EnviaArquivoOfflineParam) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lojcodigo==null && other.getLojcodigo()==null) || 
             (this.lojcodigo!=null &&
              this.lojcodigo.equals(other.getLojcodigo()))) &&
            ((this.nomearquivo==null && other.getNomearquivo()==null) || 
             (this.nomearquivo!=null &&
              this.nomearquivo.equals(other.getNomearquivo()))) &&
            ((this.arquivo==null && other.getArquivo()==null) || 
             (this.arquivo!=null &&
              this.arquivo.equals(other.getArquivo())));
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
        if (getLojcodigo() != null) {
            _hashCode += getLojcodigo().hashCode();
        }
        if (getNomearquivo() != null) {
            _hashCode += getNomearquivo().hashCode();
        }
        if (getArquivo() != null) {
            _hashCode += getArquivo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EnviaArquivoOfflineParam.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "enviaArquivoOfflineParam"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lojcodigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lojcodigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomearquivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomearquivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arquivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "arquivo"));
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
