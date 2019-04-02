/**
 * TDadosRendaFamiliar.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TDadosRendaFamiliar  implements java.io.Serializable {
    private java.lang.String nmRenda;

    private java.lang.String dsCPF;

    private int cdRelacao;

    private int nrDDDCom;

    private int nrTelCom;

    private int nrDDDCel;

    private int nrTelCel;

    private int cdCargo;

    private float vlSalario;

    public TDadosRendaFamiliar() {
    }

    public TDadosRendaFamiliar(
           java.lang.String nmRenda,
           java.lang.String dsCPF,
           int cdRelacao,
           int nrDDDCom,
           int nrTelCom,
           int nrDDDCel,
           int nrTelCel,
           int cdCargo,
           float vlSalario) {
           this.nmRenda = nmRenda;
           this.dsCPF = dsCPF;
           this.cdRelacao = cdRelacao;
           this.nrDDDCom = nrDDDCom;
           this.nrTelCom = nrTelCom;
           this.nrDDDCel = nrDDDCel;
           this.nrTelCel = nrTelCel;
           this.cdCargo = cdCargo;
           this.vlSalario = vlSalario;
    }


    /**
     * Gets the nmRenda value for this TDadosRendaFamiliar.
     * 
     * @return nmRenda
     */
    public java.lang.String getNmRenda() {
        return nmRenda;
    }


    /**
     * Sets the nmRenda value for this TDadosRendaFamiliar.
     * 
     * @param nmRenda
     */
    public void setNmRenda(java.lang.String nmRenda) {
        this.nmRenda = nmRenda;
    }


    /**
     * Gets the dsCPF value for this TDadosRendaFamiliar.
     * 
     * @return dsCPF
     */
    public java.lang.String getDsCPF() {
        return dsCPF;
    }


    /**
     * Sets the dsCPF value for this TDadosRendaFamiliar.
     * 
     * @param dsCPF
     */
    public void setDsCPF(java.lang.String dsCPF) {
        this.dsCPF = dsCPF;
    }


    /**
     * Gets the cdRelacao value for this TDadosRendaFamiliar.
     * 
     * @return cdRelacao
     */
    public int getCdRelacao() {
        return cdRelacao;
    }


    /**
     * Sets the cdRelacao value for this TDadosRendaFamiliar.
     * 
     * @param cdRelacao
     */
    public void setCdRelacao(int cdRelacao) {
        this.cdRelacao = cdRelacao;
    }


    /**
     * Gets the nrDDDCom value for this TDadosRendaFamiliar.
     * 
     * @return nrDDDCom
     */
    public int getNrDDDCom() {
        return nrDDDCom;
    }


    /**
     * Sets the nrDDDCom value for this TDadosRendaFamiliar.
     * 
     * @param nrDDDCom
     */
    public void setNrDDDCom(int nrDDDCom) {
        this.nrDDDCom = nrDDDCom;
    }


    /**
     * Gets the nrTelCom value for this TDadosRendaFamiliar.
     * 
     * @return nrTelCom
     */
    public int getNrTelCom() {
        return nrTelCom;
    }


    /**
     * Sets the nrTelCom value for this TDadosRendaFamiliar.
     * 
     * @param nrTelCom
     */
    public void setNrTelCom(int nrTelCom) {
        this.nrTelCom = nrTelCom;
    }


    /**
     * Gets the nrDDDCel value for this TDadosRendaFamiliar.
     * 
     * @return nrDDDCel
     */
    public int getNrDDDCel() {
        return nrDDDCel;
    }


    /**
     * Sets the nrDDDCel value for this TDadosRendaFamiliar.
     * 
     * @param nrDDDCel
     */
    public void setNrDDDCel(int nrDDDCel) {
        this.nrDDDCel = nrDDDCel;
    }


    /**
     * Gets the nrTelCel value for this TDadosRendaFamiliar.
     * 
     * @return nrTelCel
     */
    public int getNrTelCel() {
        return nrTelCel;
    }


    /**
     * Sets the nrTelCel value for this TDadosRendaFamiliar.
     * 
     * @param nrTelCel
     */
    public void setNrTelCel(int nrTelCel) {
        this.nrTelCel = nrTelCel;
    }


    /**
     * Gets the cdCargo value for this TDadosRendaFamiliar.
     * 
     * @return cdCargo
     */
    public int getCdCargo() {
        return cdCargo;
    }


    /**
     * Sets the cdCargo value for this TDadosRendaFamiliar.
     * 
     * @param cdCargo
     */
    public void setCdCargo(int cdCargo) {
        this.cdCargo = cdCargo;
    }


    /**
     * Gets the vlSalario value for this TDadosRendaFamiliar.
     * 
     * @return vlSalario
     */
    public float getVlSalario() {
        return vlSalario;
    }


    /**
     * Sets the vlSalario value for this TDadosRendaFamiliar.
     * 
     * @param vlSalario
     */
    public void setVlSalario(float vlSalario) {
        this.vlSalario = vlSalario;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TDadosRendaFamiliar)) return false;
        TDadosRendaFamiliar other = (TDadosRendaFamiliar) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nmRenda==null && other.getNmRenda()==null) || 
             (this.nmRenda!=null &&
              this.nmRenda.equals(other.getNmRenda()))) &&
            ((this.dsCPF==null && other.getDsCPF()==null) || 
             (this.dsCPF!=null &&
              this.dsCPF.equals(other.getDsCPF()))) &&
            this.cdRelacao == other.getCdRelacao() &&
            this.nrDDDCom == other.getNrDDDCom() &&
            this.nrTelCom == other.getNrTelCom() &&
            this.nrDDDCel == other.getNrDDDCel() &&
            this.nrTelCel == other.getNrTelCel() &&
            this.cdCargo == other.getCdCargo() &&
            this.vlSalario == other.getVlSalario();
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
        if (getNmRenda() != null) {
            _hashCode += getNmRenda().hashCode();
        }
        if (getDsCPF() != null) {
            _hashCode += getDsCPF().hashCode();
        }
        _hashCode += getCdRelacao();
        _hashCode += getNrDDDCom();
        _hashCode += getNrTelCom();
        _hashCode += getNrDDDCel();
        _hashCode += getNrTelCel();
        _hashCode += getCdCargo();
        _hashCode += new Float(getVlSalario()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TDadosRendaFamiliar.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TDadosRendaFamiliar"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nmRenda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nmRenda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsCPF");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsCPF"));
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
        elemField.setFieldName("cdCargo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cdCargo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlSalario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlSalario"));
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
