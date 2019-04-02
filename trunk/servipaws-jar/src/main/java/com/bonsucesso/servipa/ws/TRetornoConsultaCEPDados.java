/**
 * TRetornoConsultaCEPDados.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetornoConsultaCEPDados  implements java.io.Serializable {
    private java.lang.String dsCEP;

    private java.lang.String dsTipo;

    private java.lang.String dsEndereco;

    private int nrNumeroIni;

    private java.lang.String nrNumeroFim;

    private java.lang.String dsLadoRua;

    private java.lang.String dsBairro;

    private java.lang.String dsCidade;

    private java.lang.String dsEstado;

    public TRetornoConsultaCEPDados() {
    }

    public TRetornoConsultaCEPDados(
           java.lang.String dsCEP,
           java.lang.String dsTipo,
           java.lang.String dsEndereco,
           int nrNumeroIni,
           java.lang.String nrNumeroFim,
           java.lang.String dsLadoRua,
           java.lang.String dsBairro,
           java.lang.String dsCidade,
           java.lang.String dsEstado) {
           this.dsCEP = dsCEP;
           this.dsTipo = dsTipo;
           this.dsEndereco = dsEndereco;
           this.nrNumeroIni = nrNumeroIni;
           this.nrNumeroFim = nrNumeroFim;
           this.dsLadoRua = dsLadoRua;
           this.dsBairro = dsBairro;
           this.dsCidade = dsCidade;
           this.dsEstado = dsEstado;
    }


    /**
     * Gets the dsCEP value for this TRetornoConsultaCEPDados.
     * 
     * @return dsCEP
     */
    public java.lang.String getDsCEP() {
        return dsCEP;
    }


    /**
     * Sets the dsCEP value for this TRetornoConsultaCEPDados.
     * 
     * @param dsCEP
     */
    public void setDsCEP(java.lang.String dsCEP) {
        this.dsCEP = dsCEP;
    }


    /**
     * Gets the dsTipo value for this TRetornoConsultaCEPDados.
     * 
     * @return dsTipo
     */
    public java.lang.String getDsTipo() {
        return dsTipo;
    }


    /**
     * Sets the dsTipo value for this TRetornoConsultaCEPDados.
     * 
     * @param dsTipo
     */
    public void setDsTipo(java.lang.String dsTipo) {
        this.dsTipo = dsTipo;
    }


    /**
     * Gets the dsEndereco value for this TRetornoConsultaCEPDados.
     * 
     * @return dsEndereco
     */
    public java.lang.String getDsEndereco() {
        return dsEndereco;
    }


    /**
     * Sets the dsEndereco value for this TRetornoConsultaCEPDados.
     * 
     * @param dsEndereco
     */
    public void setDsEndereco(java.lang.String dsEndereco) {
        this.dsEndereco = dsEndereco;
    }


    /**
     * Gets the nrNumeroIni value for this TRetornoConsultaCEPDados.
     * 
     * @return nrNumeroIni
     */
    public int getNrNumeroIni() {
        return nrNumeroIni;
    }


    /**
     * Sets the nrNumeroIni value for this TRetornoConsultaCEPDados.
     * 
     * @param nrNumeroIni
     */
    public void setNrNumeroIni(int nrNumeroIni) {
        this.nrNumeroIni = nrNumeroIni;
    }


    /**
     * Gets the nrNumeroFim value for this TRetornoConsultaCEPDados.
     * 
     * @return nrNumeroFim
     */
    public java.lang.String getNrNumeroFim() {
        return nrNumeroFim;
    }


    /**
     * Sets the nrNumeroFim value for this TRetornoConsultaCEPDados.
     * 
     * @param nrNumeroFim
     */
    public void setNrNumeroFim(java.lang.String nrNumeroFim) {
        this.nrNumeroFim = nrNumeroFim;
    }


    /**
     * Gets the dsLadoRua value for this TRetornoConsultaCEPDados.
     * 
     * @return dsLadoRua
     */
    public java.lang.String getDsLadoRua() {
        return dsLadoRua;
    }


    /**
     * Sets the dsLadoRua value for this TRetornoConsultaCEPDados.
     * 
     * @param dsLadoRua
     */
    public void setDsLadoRua(java.lang.String dsLadoRua) {
        this.dsLadoRua = dsLadoRua;
    }


    /**
     * Gets the dsBairro value for this TRetornoConsultaCEPDados.
     * 
     * @return dsBairro
     */
    public java.lang.String getDsBairro() {
        return dsBairro;
    }


    /**
     * Sets the dsBairro value for this TRetornoConsultaCEPDados.
     * 
     * @param dsBairro
     */
    public void setDsBairro(java.lang.String dsBairro) {
        this.dsBairro = dsBairro;
    }


    /**
     * Gets the dsCidade value for this TRetornoConsultaCEPDados.
     * 
     * @return dsCidade
     */
    public java.lang.String getDsCidade() {
        return dsCidade;
    }


    /**
     * Sets the dsCidade value for this TRetornoConsultaCEPDados.
     * 
     * @param dsCidade
     */
    public void setDsCidade(java.lang.String dsCidade) {
        this.dsCidade = dsCidade;
    }


    /**
     * Gets the dsEstado value for this TRetornoConsultaCEPDados.
     * 
     * @return dsEstado
     */
    public java.lang.String getDsEstado() {
        return dsEstado;
    }


    /**
     * Sets the dsEstado value for this TRetornoConsultaCEPDados.
     * 
     * @param dsEstado
     */
    public void setDsEstado(java.lang.String dsEstado) {
        this.dsEstado = dsEstado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetornoConsultaCEPDados)) return false;
        TRetornoConsultaCEPDados other = (TRetornoConsultaCEPDados) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dsCEP==null && other.getDsCEP()==null) || 
             (this.dsCEP!=null &&
              this.dsCEP.equals(other.getDsCEP()))) &&
            ((this.dsTipo==null && other.getDsTipo()==null) || 
             (this.dsTipo!=null &&
              this.dsTipo.equals(other.getDsTipo()))) &&
            ((this.dsEndereco==null && other.getDsEndereco()==null) || 
             (this.dsEndereco!=null &&
              this.dsEndereco.equals(other.getDsEndereco()))) &&
            this.nrNumeroIni == other.getNrNumeroIni() &&
            ((this.nrNumeroFim==null && other.getNrNumeroFim()==null) || 
             (this.nrNumeroFim!=null &&
              this.nrNumeroFim.equals(other.getNrNumeroFim()))) &&
            ((this.dsLadoRua==null && other.getDsLadoRua()==null) || 
             (this.dsLadoRua!=null &&
              this.dsLadoRua.equals(other.getDsLadoRua()))) &&
            ((this.dsBairro==null && other.getDsBairro()==null) || 
             (this.dsBairro!=null &&
              this.dsBairro.equals(other.getDsBairro()))) &&
            ((this.dsCidade==null && other.getDsCidade()==null) || 
             (this.dsCidade!=null &&
              this.dsCidade.equals(other.getDsCidade()))) &&
            ((this.dsEstado==null && other.getDsEstado()==null) || 
             (this.dsEstado!=null &&
              this.dsEstado.equals(other.getDsEstado())));
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
        if (getDsCEP() != null) {
            _hashCode += getDsCEP().hashCode();
        }
        if (getDsTipo() != null) {
            _hashCode += getDsTipo().hashCode();
        }
        if (getDsEndereco() != null) {
            _hashCode += getDsEndereco().hashCode();
        }
        _hashCode += getNrNumeroIni();
        if (getNrNumeroFim() != null) {
            _hashCode += getNrNumeroFim().hashCode();
        }
        if (getDsLadoRua() != null) {
            _hashCode += getDsLadoRua().hashCode();
        }
        if (getDsBairro() != null) {
            _hashCode += getDsBairro().hashCode();
        }
        if (getDsCidade() != null) {
            _hashCode += getDsCidade().hashCode();
        }
        if (getDsEstado() != null) {
            _hashCode += getDsEstado().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TRetornoConsultaCEPDados.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetornoConsultaCEPDados"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsCEP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsCEP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsTipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsTipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsEndereco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsEndereco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrNumeroIni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrNumeroIni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrNumeroFim");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrNumeroFim"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsLadoRua");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsLadoRua"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsBairro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsBairro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsCidade");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsCidade"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsEstado"));
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
