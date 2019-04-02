/**
 * TItemArquivo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemArquivo  implements java.io.Serializable {
    private int nrCodigo;

    private int nrTipo;

    private java.lang.String dsTipo;

    private java.lang.String dsLink;

    public TItemArquivo() {
    }

    public TItemArquivo(
           int nrCodigo,
           int nrTipo,
           java.lang.String dsTipo,
           java.lang.String dsLink) {
           this.nrCodigo = nrCodigo;
           this.nrTipo = nrTipo;
           this.dsTipo = dsTipo;
           this.dsLink = dsLink;
    }


    /**
     * Gets the nrCodigo value for this TItemArquivo.
     * 
     * @return nrCodigo
     */
    public int getNrCodigo() {
        return nrCodigo;
    }


    /**
     * Sets the nrCodigo value for this TItemArquivo.
     * 
     * @param nrCodigo
     */
    public void setNrCodigo(int nrCodigo) {
        this.nrCodigo = nrCodigo;
    }


    /**
     * Gets the nrTipo value for this TItemArquivo.
     * 
     * @return nrTipo
     */
    public int getNrTipo() {
        return nrTipo;
    }


    /**
     * Sets the nrTipo value for this TItemArquivo.
     * 
     * @param nrTipo
     */
    public void setNrTipo(int nrTipo) {
        this.nrTipo = nrTipo;
    }


    /**
     * Gets the dsTipo value for this TItemArquivo.
     * 
     * @return dsTipo
     */
    public java.lang.String getDsTipo() {
        return dsTipo;
    }


    /**
     * Sets the dsTipo value for this TItemArquivo.
     * 
     * @param dsTipo
     */
    public void setDsTipo(java.lang.String dsTipo) {
        this.dsTipo = dsTipo;
    }


    /**
     * Gets the dsLink value for this TItemArquivo.
     * 
     * @return dsLink
     */
    public java.lang.String getDsLink() {
        return dsLink;
    }


    /**
     * Sets the dsLink value for this TItemArquivo.
     * 
     * @param dsLink
     */
    public void setDsLink(java.lang.String dsLink) {
        this.dsLink = dsLink;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemArquivo)) return false;
        TItemArquivo other = (TItemArquivo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.nrCodigo == other.getNrCodigo() &&
            this.nrTipo == other.getNrTipo() &&
            ((this.dsTipo==null && other.getDsTipo()==null) || 
             (this.dsTipo!=null &&
              this.dsTipo.equals(other.getDsTipo()))) &&
            ((this.dsLink==null && other.getDsLink()==null) || 
             (this.dsLink!=null &&
              this.dsLink.equals(other.getDsLink())));
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
        _hashCode += getNrCodigo();
        _hashCode += getNrTipo();
        if (getDsTipo() != null) {
            _hashCode += getDsTipo().hashCode();
        }
        if (getDsLink() != null) {
            _hashCode += getDsLink().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TItemArquivo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemArquivo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrCodigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrCodigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrTipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrTipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsTipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsTipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsLink");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsLink"));
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
