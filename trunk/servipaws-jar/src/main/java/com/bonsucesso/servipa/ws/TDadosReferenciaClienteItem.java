/**
 * TDadosReferenciaClienteItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TDadosReferenciaClienteItem  implements java.io.Serializable {
    private java.lang.String nmReferencia;

    private int cdRelacao;

    private int nrDDDRes;

    private int nrTelRes;

    private int nrDDDCel;

    private int nrTelCel;

    private int nrDDDCom;

    private int nrTelCom;

    public TDadosReferenciaClienteItem() {
    }

    public TDadosReferenciaClienteItem(
           java.lang.String nmReferencia,
           int cdRelacao,
           int nrDDDRes,
           int nrTelRes,
           int nrDDDCel,
           int nrTelCel,
           int nrDDDCom,
           int nrTelCom) {
           this.nmReferencia = nmReferencia;
           this.cdRelacao = cdRelacao;
           this.nrDDDRes = nrDDDRes;
           this.nrTelRes = nrTelRes;
           this.nrDDDCel = nrDDDCel;
           this.nrTelCel = nrTelCel;
           this.nrDDDCom = nrDDDCom;
           this.nrTelCom = nrTelCom;
    }


    /**
     * Gets the nmReferencia value for this TDadosReferenciaClienteItem.
     * 
     * @return nmReferencia
     */
    public java.lang.String getNmReferencia() {
        return nmReferencia;
    }


    /**
     * Sets the nmReferencia value for this TDadosReferenciaClienteItem.
     * 
     * @param nmReferencia
     */
    public void setNmReferencia(java.lang.String nmReferencia) {
        this.nmReferencia = nmReferencia;
    }


    /**
     * Gets the cdRelacao value for this TDadosReferenciaClienteItem.
     * 
     * @return cdRelacao
     */
    public int getCdRelacao() {
        return cdRelacao;
    }


    /**
     * Sets the cdRelacao value for this TDadosReferenciaClienteItem.
     * 
     * @param cdRelacao
     */
    public void setCdRelacao(int cdRelacao) {
        this.cdRelacao = cdRelacao;
    }


    /**
     * Gets the nrDDDRes value for this TDadosReferenciaClienteItem.
     * 
     * @return nrDDDRes
     */
    public int getNrDDDRes() {
        return nrDDDRes;
    }


    /**
     * Sets the nrDDDRes value for this TDadosReferenciaClienteItem.
     * 
     * @param nrDDDRes
     */
    public void setNrDDDRes(int nrDDDRes) {
        this.nrDDDRes = nrDDDRes;
    }


    /**
     * Gets the nrTelRes value for this TDadosReferenciaClienteItem.
     * 
     * @return nrTelRes
     */
    public int getNrTelRes() {
        return nrTelRes;
    }


    /**
     * Sets the nrTelRes value for this TDadosReferenciaClienteItem.
     * 
     * @param nrTelRes
     */
    public void setNrTelRes(int nrTelRes) {
        this.nrTelRes = nrTelRes;
    }


    /**
     * Gets the nrDDDCel value for this TDadosReferenciaClienteItem.
     * 
     * @return nrDDDCel
     */
    public int getNrDDDCel() {
        return nrDDDCel;
    }


    /**
     * Sets the nrDDDCel value for this TDadosReferenciaClienteItem.
     * 
     * @param nrDDDCel
     */
    public void setNrDDDCel(int nrDDDCel) {
        this.nrDDDCel = nrDDDCel;
    }


    /**
     * Gets the nrTelCel value for this TDadosReferenciaClienteItem.
     * 
     * @return nrTelCel
     */
    public int getNrTelCel() {
        return nrTelCel;
    }


    /**
     * Sets the nrTelCel value for this TDadosReferenciaClienteItem.
     * 
     * @param nrTelCel
     */
    public void setNrTelCel(int nrTelCel) {
        this.nrTelCel = nrTelCel;
    }


    /**
     * Gets the nrDDDCom value for this TDadosReferenciaClienteItem.
     * 
     * @return nrDDDCom
     */
    public int getNrDDDCom() {
        return nrDDDCom;
    }


    /**
     * Sets the nrDDDCom value for this TDadosReferenciaClienteItem.
     * 
     * @param nrDDDCom
     */
    public void setNrDDDCom(int nrDDDCom) {
        this.nrDDDCom = nrDDDCom;
    }


    /**
     * Gets the nrTelCom value for this TDadosReferenciaClienteItem.
     * 
     * @return nrTelCom
     */
    public int getNrTelCom() {
        return nrTelCom;
    }


    /**
     * Sets the nrTelCom value for this TDadosReferenciaClienteItem.
     * 
     * @param nrTelCom
     */
    public void setNrTelCom(int nrTelCom) {
        this.nrTelCom = nrTelCom;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TDadosReferenciaClienteItem)) return false;
        TDadosReferenciaClienteItem other = (TDadosReferenciaClienteItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nmReferencia==null && other.getNmReferencia()==null) || 
             (this.nmReferencia!=null &&
              this.nmReferencia.equals(other.getNmReferencia()))) &&
            this.cdRelacao == other.getCdRelacao() &&
            this.nrDDDRes == other.getNrDDDRes() &&
            this.nrTelRes == other.getNrTelRes() &&
            this.nrDDDCel == other.getNrDDDCel() &&
            this.nrTelCel == other.getNrTelCel() &&
            this.nrDDDCom == other.getNrDDDCom() &&
            this.nrTelCom == other.getNrTelCom();
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
        if (getNmReferencia() != null) {
            _hashCode += getNmReferencia().hashCode();
        }
        _hashCode += getCdRelacao();
        _hashCode += getNrDDDRes();
        _hashCode += getNrTelRes();
        _hashCode += getNrDDDCel();
        _hashCode += getNrTelCel();
        _hashCode += getNrDDDCom();
        _hashCode += getNrTelCom();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TDadosReferenciaClienteItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TDadosReferenciaClienteItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nmReferencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nmReferencia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cdRelacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cdRelacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrDDDRes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrDDDRes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrTelRes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrTelRes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrDDDCel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrDDDCel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrTelCel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrTelCel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrDDDCom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrDDDCom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrTelCom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrTelCom"));
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
