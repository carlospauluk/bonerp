/**
 * TItemParcelaAcordo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemParcelaAcordo  implements java.io.Serializable {
    private int nrParcela;

    private int dtVencimento;

    private float vlCapital;

    private float vlJuro;

    private float vlMulta;

    private float vlPmt;

    private float vlTotal;

    public TItemParcelaAcordo() {
    }

    public TItemParcelaAcordo(
           int nrParcela,
           int dtVencimento,
           float vlCapital,
           float vlJuro,
           float vlMulta,
           float vlPmt,
           float vlTotal) {
           this.nrParcela = nrParcela;
           this.dtVencimento = dtVencimento;
           this.vlCapital = vlCapital;
           this.vlJuro = vlJuro;
           this.vlMulta = vlMulta;
           this.vlPmt = vlPmt;
           this.vlTotal = vlTotal;
    }


    /**
     * Gets the nrParcela value for this TItemParcelaAcordo.
     * 
     * @return nrParcela
     */
    public int getNrParcela() {
        return nrParcela;
    }


    /**
     * Sets the nrParcela value for this TItemParcelaAcordo.
     * 
     * @param nrParcela
     */
    public void setNrParcela(int nrParcela) {
        this.nrParcela = nrParcela;
    }


    /**
     * Gets the dtVencimento value for this TItemParcelaAcordo.
     * 
     * @return dtVencimento
     */
    public int getDtVencimento() {
        return dtVencimento;
    }


    /**
     * Sets the dtVencimento value for this TItemParcelaAcordo.
     * 
     * @param dtVencimento
     */
    public void setDtVencimento(int dtVencimento) {
        this.dtVencimento = dtVencimento;
    }


    /**
     * Gets the vlCapital value for this TItemParcelaAcordo.
     * 
     * @return vlCapital
     */
    public float getVlCapital() {
        return vlCapital;
    }


    /**
     * Sets the vlCapital value for this TItemParcelaAcordo.
     * 
     * @param vlCapital
     */
    public void setVlCapital(float vlCapital) {
        this.vlCapital = vlCapital;
    }


    /**
     * Gets the vlJuro value for this TItemParcelaAcordo.
     * 
     * @return vlJuro
     */
    public float getVlJuro() {
        return vlJuro;
    }


    /**
     * Sets the vlJuro value for this TItemParcelaAcordo.
     * 
     * @param vlJuro
     */
    public void setVlJuro(float vlJuro) {
        this.vlJuro = vlJuro;
    }


    /**
     * Gets the vlMulta value for this TItemParcelaAcordo.
     * 
     * @return vlMulta
     */
    public float getVlMulta() {
        return vlMulta;
    }


    /**
     * Sets the vlMulta value for this TItemParcelaAcordo.
     * 
     * @param vlMulta
     */
    public void setVlMulta(float vlMulta) {
        this.vlMulta = vlMulta;
    }


    /**
     * Gets the vlPmt value for this TItemParcelaAcordo.
     * 
     * @return vlPmt
     */
    public float getVlPmt() {
        return vlPmt;
    }


    /**
     * Sets the vlPmt value for this TItemParcelaAcordo.
     * 
     * @param vlPmt
     */
    public void setVlPmt(float vlPmt) {
        this.vlPmt = vlPmt;
    }


    /**
     * Gets the vlTotal value for this TItemParcelaAcordo.
     * 
     * @return vlTotal
     */
    public float getVlTotal() {
        return vlTotal;
    }


    /**
     * Sets the vlTotal value for this TItemParcelaAcordo.
     * 
     * @param vlTotal
     */
    public void setVlTotal(float vlTotal) {
        this.vlTotal = vlTotal;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemParcelaAcordo)) return false;
        TItemParcelaAcordo other = (TItemParcelaAcordo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.nrParcela == other.getNrParcela() &&
            this.dtVencimento == other.getDtVencimento() &&
            this.vlCapital == other.getVlCapital() &&
            this.vlJuro == other.getVlJuro() &&
            this.vlMulta == other.getVlMulta() &&
            this.vlPmt == other.getVlPmt() &&
            this.vlTotal == other.getVlTotal();
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
        _hashCode += getNrParcela();
        _hashCode += getDtVencimento();
        _hashCode += new Float(getVlCapital()).hashCode();
        _hashCode += new Float(getVlJuro()).hashCode();
        _hashCode += new Float(getVlMulta()).hashCode();
        _hashCode += new Float(getVlPmt()).hashCode();
        _hashCode += new Float(getVlTotal()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TItemParcelaAcordo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemParcelaAcordo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrParcela");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrParcela"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtVencimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dtVencimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlCapital");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlCapital"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlJuro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlJuro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlMulta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlMulta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlPmt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlPmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlTotal"));
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
