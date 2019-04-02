/**
 * TItemRetornoAutorizado.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemRetornoAutorizado  implements java.io.Serializable {
    private int nrCodigo;

    private java.lang.String dsNome;

    private java.lang.String dsDDD;

    private java.lang.String dsTelefone;

    private java.lang.String dsCPF;

    private java.lang.String dsRG;

    private int dtLimite;

    private float vlLimite;

    private int nrAtivo;

    public TItemRetornoAutorizado() {
    }

    public TItemRetornoAutorizado(
           int nrCodigo,
           java.lang.String dsNome,
           java.lang.String dsDDD,
           java.lang.String dsTelefone,
           java.lang.String dsCPF,
           java.lang.String dsRG,
           int dtLimite,
           float vlLimite,
           int nrAtivo) {
           this.nrCodigo = nrCodigo;
           this.dsNome = dsNome;
           this.dsDDD = dsDDD;
           this.dsTelefone = dsTelefone;
           this.dsCPF = dsCPF;
           this.dsRG = dsRG;
           this.dtLimite = dtLimite;
           this.vlLimite = vlLimite;
           this.nrAtivo = nrAtivo;
    }


    /**
     * Gets the nrCodigo value for this TItemRetornoAutorizado.
     * 
     * @return nrCodigo
     */
    public int getNrCodigo() {
        return nrCodigo;
    }


    /**
     * Sets the nrCodigo value for this TItemRetornoAutorizado.
     * 
     * @param nrCodigo
     */
    public void setNrCodigo(int nrCodigo) {
        this.nrCodigo = nrCodigo;
    }


    /**
     * Gets the dsNome value for this TItemRetornoAutorizado.
     * 
     * @return dsNome
     */
    public java.lang.String getDsNome() {
        return dsNome;
    }


    /**
     * Sets the dsNome value for this TItemRetornoAutorizado.
     * 
     * @param dsNome
     */
    public void setDsNome(java.lang.String dsNome) {
        this.dsNome = dsNome;
    }


    /**
     * Gets the dsDDD value for this TItemRetornoAutorizado.
     * 
     * @return dsDDD
     */
    public java.lang.String getDsDDD() {
        return dsDDD;
    }


    /**
     * Sets the dsDDD value for this TItemRetornoAutorizado.
     * 
     * @param dsDDD
     */
    public void setDsDDD(java.lang.String dsDDD) {
        this.dsDDD = dsDDD;
    }


    /**
     * Gets the dsTelefone value for this TItemRetornoAutorizado.
     * 
     * @return dsTelefone
     */
    public java.lang.String getDsTelefone() {
        return dsTelefone;
    }


    /**
     * Sets the dsTelefone value for this TItemRetornoAutorizado.
     * 
     * @param dsTelefone
     */
    public void setDsTelefone(java.lang.String dsTelefone) {
        this.dsTelefone = dsTelefone;
    }


    /**
     * Gets the dsCPF value for this TItemRetornoAutorizado.
     * 
     * @return dsCPF
     */
    public java.lang.String getDsCPF() {
        return dsCPF;
    }


    /**
     * Sets the dsCPF value for this TItemRetornoAutorizado.
     * 
     * @param dsCPF
     */
    public void setDsCPF(java.lang.String dsCPF) {
        this.dsCPF = dsCPF;
    }


    /**
     * Gets the dsRG value for this TItemRetornoAutorizado.
     * 
     * @return dsRG
     */
    public java.lang.String getDsRG() {
        return dsRG;
    }


    /**
     * Sets the dsRG value for this TItemRetornoAutorizado.
     * 
     * @param dsRG
     */
    public void setDsRG(java.lang.String dsRG) {
        this.dsRG = dsRG;
    }


    /**
     * Gets the dtLimite value for this TItemRetornoAutorizado.
     * 
     * @return dtLimite
     */
    public int getDtLimite() {
        return dtLimite;
    }


    /**
     * Sets the dtLimite value for this TItemRetornoAutorizado.
     * 
     * @param dtLimite
     */
    public void setDtLimite(int dtLimite) {
        this.dtLimite = dtLimite;
    }


    /**
     * Gets the vlLimite value for this TItemRetornoAutorizado.
     * 
     * @return vlLimite
     */
    public float getVlLimite() {
        return vlLimite;
    }


    /**
     * Sets the vlLimite value for this TItemRetornoAutorizado.
     * 
     * @param vlLimite
     */
    public void setVlLimite(float vlLimite) {
        this.vlLimite = vlLimite;
    }


    /**
     * Gets the nrAtivo value for this TItemRetornoAutorizado.
     * 
     * @return nrAtivo
     */
    public int getNrAtivo() {
        return nrAtivo;
    }


    /**
     * Sets the nrAtivo value for this TItemRetornoAutorizado.
     * 
     * @param nrAtivo
     */
    public void setNrAtivo(int nrAtivo) {
        this.nrAtivo = nrAtivo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemRetornoAutorizado)) return false;
        TItemRetornoAutorizado other = (TItemRetornoAutorizado) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.nrCodigo == other.getNrCodigo() &&
            ((this.dsNome==null && other.getDsNome()==null) || 
             (this.dsNome!=null &&
              this.dsNome.equals(other.getDsNome()))) &&
            ((this.dsDDD==null && other.getDsDDD()==null) || 
             (this.dsDDD!=null &&
              this.dsDDD.equals(other.getDsDDD()))) &&
            ((this.dsTelefone==null && other.getDsTelefone()==null) || 
             (this.dsTelefone!=null &&
              this.dsTelefone.equals(other.getDsTelefone()))) &&
            ((this.dsCPF==null && other.getDsCPF()==null) || 
             (this.dsCPF!=null &&
              this.dsCPF.equals(other.getDsCPF()))) &&
            ((this.dsRG==null && other.getDsRG()==null) || 
             (this.dsRG!=null &&
              this.dsRG.equals(other.getDsRG()))) &&
            this.dtLimite == other.getDtLimite() &&
            this.vlLimite == other.getVlLimite() &&
            this.nrAtivo == other.getNrAtivo();
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
        if (getDsNome() != null) {
            _hashCode += getDsNome().hashCode();
        }
        if (getDsDDD() != null) {
            _hashCode += getDsDDD().hashCode();
        }
        if (getDsTelefone() != null) {
            _hashCode += getDsTelefone().hashCode();
        }
        if (getDsCPF() != null) {
            _hashCode += getDsCPF().hashCode();
        }
        if (getDsRG() != null) {
            _hashCode += getDsRG().hashCode();
        }
        _hashCode += getDtLimite();
        _hashCode += new Float(getVlLimite()).hashCode();
        _hashCode += getNrAtivo();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TItemRetornoAutorizado.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRetornoAutorizado"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrCodigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrCodigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsNome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsNome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsDDD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsDDD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsTelefone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsTelefone"));
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
        elemField.setFieldName("dsRG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsRG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtLimite");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DtLimite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlLimite");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VlLimite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrAtivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrAtivo"));
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
