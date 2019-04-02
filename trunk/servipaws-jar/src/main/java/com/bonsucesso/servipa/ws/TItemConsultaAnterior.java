/**
 * TItemConsultaAnterior.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemConsultaAnterior  implements java.io.Serializable {
    private java.lang.String dsNome;

    private int dtConsulta;

    public TItemConsultaAnterior() {
    }

    public TItemConsultaAnterior(
           java.lang.String dsNome,
           int dtConsulta) {
           this.dsNome = dsNome;
           this.dtConsulta = dtConsulta;
    }


    /**
     * Gets the dsNome value for this TItemConsultaAnterior.
     * 
     * @return dsNome
     */
    public java.lang.String getDsNome() {
        return dsNome;
    }


    /**
     * Sets the dsNome value for this TItemConsultaAnterior.
     * 
     * @param dsNome
     */
    public void setDsNome(java.lang.String dsNome) {
        this.dsNome = dsNome;
    }


    /**
     * Gets the dtConsulta value for this TItemConsultaAnterior.
     * 
     * @return dtConsulta
     */
    public int getDtConsulta() {
        return dtConsulta;
    }


    /**
     * Sets the dtConsulta value for this TItemConsultaAnterior.
     * 
     * @param dtConsulta
     */
    public void setDtConsulta(int dtConsulta) {
        this.dtConsulta = dtConsulta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemConsultaAnterior)) return false;
        TItemConsultaAnterior other = (TItemConsultaAnterior) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dsNome==null && other.getDsNome()==null) || 
             (this.dsNome!=null &&
              this.dsNome.equals(other.getDsNome()))) &&
            this.dtConsulta == other.getDtConsulta();
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
        if (getDsNome() != null) {
            _hashCode += getDsNome().hashCode();
        }
        _hashCode += getDtConsulta();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TItemConsultaAnterior.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemConsultaAnterior"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsNome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsNome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtConsulta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dtConsulta"));
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
