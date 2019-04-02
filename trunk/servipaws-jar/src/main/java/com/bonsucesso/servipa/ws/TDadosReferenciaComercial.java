/**
 * TDadosReferenciaComercial.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TDadosReferenciaComercial  implements java.io.Serializable {
    private java.lang.String nmComercial;

    private int nrDDD;

    private int nrTelefone;

    private int dtCliDesde;

    private int nrParcAberta;

    private int nrParcPaga;

    private float vlTotalPago;

    private int dtInformado;

    public TDadosReferenciaComercial() {
    }

    public TDadosReferenciaComercial(
           java.lang.String nmComercial,
           int nrDDD,
           int nrTelefone,
           int dtCliDesde,
           int nrParcAberta,
           int nrParcPaga,
           float vlTotalPago,
           int dtInformado) {
           this.nmComercial = nmComercial;
           this.nrDDD = nrDDD;
           this.nrTelefone = nrTelefone;
           this.dtCliDesde = dtCliDesde;
           this.nrParcAberta = nrParcAberta;
           this.nrParcPaga = nrParcPaga;
           this.vlTotalPago = vlTotalPago;
           this.dtInformado = dtInformado;
    }


    /**
     * Gets the nmComercial value for this TDadosReferenciaComercial.
     * 
     * @return nmComercial
     */
    public java.lang.String getNmComercial() {
        return nmComercial;
    }


    /**
     * Sets the nmComercial value for this TDadosReferenciaComercial.
     * 
     * @param nmComercial
     */
    public void setNmComercial(java.lang.String nmComercial) {
        this.nmComercial = nmComercial;
    }


    /**
     * Gets the nrDDD value for this TDadosReferenciaComercial.
     * 
     * @return nrDDD
     */
    public int getNrDDD() {
        return nrDDD;
    }


    /**
     * Sets the nrDDD value for this TDadosReferenciaComercial.
     * 
     * @param nrDDD
     */
    public void setNrDDD(int nrDDD) {
        this.nrDDD = nrDDD;
    }


    /**
     * Gets the nrTelefone value for this TDadosReferenciaComercial.
     * 
     * @return nrTelefone
     */
    public int getNrTelefone() {
        return nrTelefone;
    }


    /**
     * Sets the nrTelefone value for this TDadosReferenciaComercial.
     * 
     * @param nrTelefone
     */
    public void setNrTelefone(int nrTelefone) {
        this.nrTelefone = nrTelefone;
    }


    /**
     * Gets the dtCliDesde value for this TDadosReferenciaComercial.
     * 
     * @return dtCliDesde
     */
    public int getDtCliDesde() {
        return dtCliDesde;
    }


    /**
     * Sets the dtCliDesde value for this TDadosReferenciaComercial.
     * 
     * @param dtCliDesde
     */
    public void setDtCliDesde(int dtCliDesde) {
        this.dtCliDesde = dtCliDesde;
    }


    /**
     * Gets the nrParcAberta value for this TDadosReferenciaComercial.
     * 
     * @return nrParcAberta
     */
    public int getNrParcAberta() {
        return nrParcAberta;
    }


    /**
     * Sets the nrParcAberta value for this TDadosReferenciaComercial.
     * 
     * @param nrParcAberta
     */
    public void setNrParcAberta(int nrParcAberta) {
        this.nrParcAberta = nrParcAberta;
    }


    /**
     * Gets the nrParcPaga value for this TDadosReferenciaComercial.
     * 
     * @return nrParcPaga
     */
    public int getNrParcPaga() {
        return nrParcPaga;
    }


    /**
     * Sets the nrParcPaga value for this TDadosReferenciaComercial.
     * 
     * @param nrParcPaga
     */
    public void setNrParcPaga(int nrParcPaga) {
        this.nrParcPaga = nrParcPaga;
    }


    /**
     * Gets the vlTotalPago value for this TDadosReferenciaComercial.
     * 
     * @return vlTotalPago
     */
    public float getVlTotalPago() {
        return vlTotalPago;
    }


    /**
     * Sets the vlTotalPago value for this TDadosReferenciaComercial.
     * 
     * @param vlTotalPago
     */
    public void setVlTotalPago(float vlTotalPago) {
        this.vlTotalPago = vlTotalPago;
    }


    /**
     * Gets the dtInformado value for this TDadosReferenciaComercial.
     * 
     * @return dtInformado
     */
    public int getDtInformado() {
        return dtInformado;
    }


    /**
     * Sets the dtInformado value for this TDadosReferenciaComercial.
     * 
     * @param dtInformado
     */
    public void setDtInformado(int dtInformado) {
        this.dtInformado = dtInformado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TDadosReferenciaComercial)) return false;
        TDadosReferenciaComercial other = (TDadosReferenciaComercial) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nmComercial==null && other.getNmComercial()==null) || 
             (this.nmComercial!=null &&
              this.nmComercial.equals(other.getNmComercial()))) &&
            this.nrDDD == other.getNrDDD() &&
            this.nrTelefone == other.getNrTelefone() &&
            this.dtCliDesde == other.getDtCliDesde() &&
            this.nrParcAberta == other.getNrParcAberta() &&
            this.nrParcPaga == other.getNrParcPaga() &&
            this.vlTotalPago == other.getVlTotalPago() &&
            this.dtInformado == other.getDtInformado();
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
        if (getNmComercial() != null) {
            _hashCode += getNmComercial().hashCode();
        }
        _hashCode += getNrDDD();
        _hashCode += getNrTelefone();
        _hashCode += getDtCliDesde();
        _hashCode += getNrParcAberta();
        _hashCode += getNrParcPaga();
        _hashCode += new Float(getVlTotalPago()).hashCode();
        _hashCode += getDtInformado();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TDadosReferenciaComercial.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TDadosReferenciaComercial"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nmComercial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nmComercial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrDDD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrDDD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrTelefone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrTelefone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtCliDesde");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DtCliDesde"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrParcAberta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrParcAberta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrParcPaga");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrParcPaga"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlTotalPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlTotalPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtInformado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DtInformado"));
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
