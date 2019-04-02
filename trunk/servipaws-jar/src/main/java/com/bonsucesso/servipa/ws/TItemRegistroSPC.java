/**
 * TItemRegistroSPC.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemRegistroSPC  implements java.io.Serializable {
    private java.lang.String dsNome;

    private java.lang.String dsCidade;

    private float vlValor;

    private int dtRegistro;

    private int dtVencimento;

    private java.lang.String dsContrato;

    private java.lang.String dsOcorrencia;

    public TItemRegistroSPC() {
    }

    public TItemRegistroSPC(
           java.lang.String dsNome,
           java.lang.String dsCidade,
           float vlValor,
           int dtRegistro,
           int dtVencimento,
           java.lang.String dsContrato,
           java.lang.String dsOcorrencia) {
           this.dsNome = dsNome;
           this.dsCidade = dsCidade;
           this.vlValor = vlValor;
           this.dtRegistro = dtRegistro;
           this.dtVencimento = dtVencimento;
           this.dsContrato = dsContrato;
           this.dsOcorrencia = dsOcorrencia;
    }


    /**
     * Gets the dsNome value for this TItemRegistroSPC.
     * 
     * @return dsNome
     */
    public java.lang.String getDsNome() {
        return dsNome;
    }


    /**
     * Sets the dsNome value for this TItemRegistroSPC.
     * 
     * @param dsNome
     */
    public void setDsNome(java.lang.String dsNome) {
        this.dsNome = dsNome;
    }


    /**
     * Gets the dsCidade value for this TItemRegistroSPC.
     * 
     * @return dsCidade
     */
    public java.lang.String getDsCidade() {
        return dsCidade;
    }


    /**
     * Sets the dsCidade value for this TItemRegistroSPC.
     * 
     * @param dsCidade
     */
    public void setDsCidade(java.lang.String dsCidade) {
        this.dsCidade = dsCidade;
    }


    /**
     * Gets the vlValor value for this TItemRegistroSPC.
     * 
     * @return vlValor
     */
    public float getVlValor() {
        return vlValor;
    }


    /**
     * Sets the vlValor value for this TItemRegistroSPC.
     * 
     * @param vlValor
     */
    public void setVlValor(float vlValor) {
        this.vlValor = vlValor;
    }


    /**
     * Gets the dtRegistro value for this TItemRegistroSPC.
     * 
     * @return dtRegistro
     */
    public int getDtRegistro() {
        return dtRegistro;
    }


    /**
     * Sets the dtRegistro value for this TItemRegistroSPC.
     * 
     * @param dtRegistro
     */
    public void setDtRegistro(int dtRegistro) {
        this.dtRegistro = dtRegistro;
    }


    /**
     * Gets the dtVencimento value for this TItemRegistroSPC.
     * 
     * @return dtVencimento
     */
    public int getDtVencimento() {
        return dtVencimento;
    }


    /**
     * Sets the dtVencimento value for this TItemRegistroSPC.
     * 
     * @param dtVencimento
     */
    public void setDtVencimento(int dtVencimento) {
        this.dtVencimento = dtVencimento;
    }


    /**
     * Gets the dsContrato value for this TItemRegistroSPC.
     * 
     * @return dsContrato
     */
    public java.lang.String getDsContrato() {
        return dsContrato;
    }


    /**
     * Sets the dsContrato value for this TItemRegistroSPC.
     * 
     * @param dsContrato
     */
    public void setDsContrato(java.lang.String dsContrato) {
        this.dsContrato = dsContrato;
    }


    /**
     * Gets the dsOcorrencia value for this TItemRegistroSPC.
     * 
     * @return dsOcorrencia
     */
    public java.lang.String getDsOcorrencia() {
        return dsOcorrencia;
    }


    /**
     * Sets the dsOcorrencia value for this TItemRegistroSPC.
     * 
     * @param dsOcorrencia
     */
    public void setDsOcorrencia(java.lang.String dsOcorrencia) {
        this.dsOcorrencia = dsOcorrencia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemRegistroSPC)) return false;
        TItemRegistroSPC other = (TItemRegistroSPC) obj;
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
            ((this.dsCidade==null && other.getDsCidade()==null) || 
             (this.dsCidade!=null &&
              this.dsCidade.equals(other.getDsCidade()))) &&
            this.vlValor == other.getVlValor() &&
            this.dtRegistro == other.getDtRegistro() &&
            this.dtVencimento == other.getDtVencimento() &&
            ((this.dsContrato==null && other.getDsContrato()==null) || 
             (this.dsContrato!=null &&
              this.dsContrato.equals(other.getDsContrato()))) &&
            ((this.dsOcorrencia==null && other.getDsOcorrencia()==null) || 
             (this.dsOcorrencia!=null &&
              this.dsOcorrencia.equals(other.getDsOcorrencia())));
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
        if (getDsCidade() != null) {
            _hashCode += getDsCidade().hashCode();
        }
        _hashCode += new Float(getVlValor()).hashCode();
        _hashCode += getDtRegistro();
        _hashCode += getDtVencimento();
        if (getDsContrato() != null) {
            _hashCode += getDsContrato().hashCode();
        }
        if (getDsOcorrencia() != null) {
            _hashCode += getDsOcorrencia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TItemRegistroSPC.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRegistroSPC"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsNome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsNome"));
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
        elemField.setFieldName("vlValor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlValor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dtRegistro"));
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
        elemField.setFieldName("dsContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsContrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsOcorrencia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsOcorrencia"));
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
