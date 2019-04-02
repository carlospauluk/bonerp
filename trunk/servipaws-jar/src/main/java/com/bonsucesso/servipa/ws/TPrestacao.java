/**
 * TPrestacao.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TPrestacao  implements java.io.Serializable {
    private int atraso;

    private int dtvct;

    private java.lang.String juros;

    private java.lang.String multa;

    private int nrPrestacao;

    private java.lang.String situacao;

    private java.lang.String vlrVlrtotal;

    private java.lang.String vlrdesc;

    private java.lang.String vlrprest;

    public TPrestacao() {
    }

    public TPrestacao(
           int atraso,
           int dtvct,
           java.lang.String juros,
           java.lang.String multa,
           int nrPrestacao,
           java.lang.String situacao,
           java.lang.String vlrVlrtotal,
           java.lang.String vlrdesc,
           java.lang.String vlrprest) {
           this.atraso = atraso;
           this.dtvct = dtvct;
           this.juros = juros;
           this.multa = multa;
           this.nrPrestacao = nrPrestacao;
           this.situacao = situacao;
           this.vlrVlrtotal = vlrVlrtotal;
           this.vlrdesc = vlrdesc;
           this.vlrprest = vlrprest;
    }


    /**
     * Gets the atraso value for this TPrestacao.
     * 
     * @return atraso
     */
    public int getAtraso() {
        return atraso;
    }


    /**
     * Sets the atraso value for this TPrestacao.
     * 
     * @param atraso
     */
    public void setAtraso(int atraso) {
        this.atraso = atraso;
    }


    /**
     * Gets the dtvct value for this TPrestacao.
     * 
     * @return dtvct
     */
    public int getDtvct() {
        return dtvct;
    }


    /**
     * Sets the dtvct value for this TPrestacao.
     * 
     * @param dtvct
     */
    public void setDtvct(int dtvct) {
        this.dtvct = dtvct;
    }


    /**
     * Gets the juros value for this TPrestacao.
     * 
     * @return juros
     */
    public java.lang.String getJuros() {
        return juros;
    }


    /**
     * Sets the juros value for this TPrestacao.
     * 
     * @param juros
     */
    public void setJuros(java.lang.String juros) {
        this.juros = juros;
    }


    /**
     * Gets the multa value for this TPrestacao.
     * 
     * @return multa
     */
    public java.lang.String getMulta() {
        return multa;
    }


    /**
     * Sets the multa value for this TPrestacao.
     * 
     * @param multa
     */
    public void setMulta(java.lang.String multa) {
        this.multa = multa;
    }


    /**
     * Gets the nrPrestacao value for this TPrestacao.
     * 
     * @return nrPrestacao
     */
    public int getNrPrestacao() {
        return nrPrestacao;
    }


    /**
     * Sets the nrPrestacao value for this TPrestacao.
     * 
     * @param nrPrestacao
     */
    public void setNrPrestacao(int nrPrestacao) {
        this.nrPrestacao = nrPrestacao;
    }


    /**
     * Gets the situacao value for this TPrestacao.
     * 
     * @return situacao
     */
    public java.lang.String getSituacao() {
        return situacao;
    }


    /**
     * Sets the situacao value for this TPrestacao.
     * 
     * @param situacao
     */
    public void setSituacao(java.lang.String situacao) {
        this.situacao = situacao;
    }


    /**
     * Gets the vlrVlrtotal value for this TPrestacao.
     * 
     * @return vlrVlrtotal
     */
    public java.lang.String getVlrVlrtotal() {
        return vlrVlrtotal;
    }


    /**
     * Sets the vlrVlrtotal value for this TPrestacao.
     * 
     * @param vlrVlrtotal
     */
    public void setVlrVlrtotal(java.lang.String vlrVlrtotal) {
        this.vlrVlrtotal = vlrVlrtotal;
    }


    /**
     * Gets the vlrdesc value for this TPrestacao.
     * 
     * @return vlrdesc
     */
    public java.lang.String getVlrdesc() {
        return vlrdesc;
    }


    /**
     * Sets the vlrdesc value for this TPrestacao.
     * 
     * @param vlrdesc
     */
    public void setVlrdesc(java.lang.String vlrdesc) {
        this.vlrdesc = vlrdesc;
    }


    /**
     * Gets the vlrprest value for this TPrestacao.
     * 
     * @return vlrprest
     */
    public java.lang.String getVlrprest() {
        return vlrprest;
    }


    /**
     * Sets the vlrprest value for this TPrestacao.
     * 
     * @param vlrprest
     */
    public void setVlrprest(java.lang.String vlrprest) {
        this.vlrprest = vlrprest;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TPrestacao)) return false;
        TPrestacao other = (TPrestacao) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.atraso == other.getAtraso() &&
            this.dtvct == other.getDtvct() &&
            ((this.juros==null && other.getJuros()==null) || 
             (this.juros!=null &&
              this.juros.equals(other.getJuros()))) &&
            ((this.multa==null && other.getMulta()==null) || 
             (this.multa!=null &&
              this.multa.equals(other.getMulta()))) &&
            this.nrPrestacao == other.getNrPrestacao() &&
            ((this.situacao==null && other.getSituacao()==null) || 
             (this.situacao!=null &&
              this.situacao.equals(other.getSituacao()))) &&
            ((this.vlrVlrtotal==null && other.getVlrVlrtotal()==null) || 
             (this.vlrVlrtotal!=null &&
              this.vlrVlrtotal.equals(other.getVlrVlrtotal()))) &&
            ((this.vlrdesc==null && other.getVlrdesc()==null) || 
             (this.vlrdesc!=null &&
              this.vlrdesc.equals(other.getVlrdesc()))) &&
            ((this.vlrprest==null && other.getVlrprest()==null) || 
             (this.vlrprest!=null &&
              this.vlrprest.equals(other.getVlrprest())));
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
        _hashCode += getAtraso();
        _hashCode += getDtvct();
        if (getJuros() != null) {
            _hashCode += getJuros().hashCode();
        }
        if (getMulta() != null) {
            _hashCode += getMulta().hashCode();
        }
        _hashCode += getNrPrestacao();
        if (getSituacao() != null) {
            _hashCode += getSituacao().hashCode();
        }
        if (getVlrVlrtotal() != null) {
            _hashCode += getVlrVlrtotal().hashCode();
        }
        if (getVlrdesc() != null) {
            _hashCode += getVlrdesc().hashCode();
        }
        if (getVlrprest() != null) {
            _hashCode += getVlrprest().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TPrestacao.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TPrestacao"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atraso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "atraso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtvct");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dtvct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("juros");
        elemField.setXmlName(new javax.xml.namespace.QName("", "juros"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "multa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrPrestacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrPrestacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("situacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "situacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlrVlrtotal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlrVlrtotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlrdesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlrdesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlrprest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlrprest"));
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
