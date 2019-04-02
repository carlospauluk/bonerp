/**
 * TItemParcela.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemParcela  implements java.io.Serializable {
    private java.lang.String dsContrato;

    private int nrParcela;

    private java.lang.String dsLoja;

    private int dtVenda;

    private float vlVenda;

    private int dtVencimento;

    private float vlParcela;

    private int dtPago;

    private float vlPago;

    private int dtParcial;

    private float vlParcial;

    private java.lang.String dsDocumento;

    private float vlQuitacao;

    private java.lang.String linkBoleto;

    private com.bonsucesso.servipa.ws.TPrestacao prestacao;

    public TItemParcela() {
    }

    public TItemParcela(
           java.lang.String dsContrato,
           int nrParcela,
           java.lang.String dsLoja,
           int dtVenda,
           float vlVenda,
           int dtVencimento,
           float vlParcela,
           int dtPago,
           float vlPago,
           int dtParcial,
           float vlParcial,
           java.lang.String dsDocumento,
           float vlQuitacao,
           java.lang.String linkBoleto,
           com.bonsucesso.servipa.ws.TPrestacao prestacao) {
           this.dsContrato = dsContrato;
           this.nrParcela = nrParcela;
           this.dsLoja = dsLoja;
           this.dtVenda = dtVenda;
           this.vlVenda = vlVenda;
           this.dtVencimento = dtVencimento;
           this.vlParcela = vlParcela;
           this.dtPago = dtPago;
           this.vlPago = vlPago;
           this.dtParcial = dtParcial;
           this.vlParcial = vlParcial;
           this.dsDocumento = dsDocumento;
           this.vlQuitacao = vlQuitacao;
           this.linkBoleto = linkBoleto;
           this.prestacao = prestacao;
    }


    /**
     * Gets the dsContrato value for this TItemParcela.
     * 
     * @return dsContrato
     */
    public java.lang.String getDsContrato() {
        return dsContrato;
    }


    /**
     * Sets the dsContrato value for this TItemParcela.
     * 
     * @param dsContrato
     */
    public void setDsContrato(java.lang.String dsContrato) {
        this.dsContrato = dsContrato;
    }


    /**
     * Gets the nrParcela value for this TItemParcela.
     * 
     * @return nrParcela
     */
    public int getNrParcela() {
        return nrParcela;
    }


    /**
     * Sets the nrParcela value for this TItemParcela.
     * 
     * @param nrParcela
     */
    public void setNrParcela(int nrParcela) {
        this.nrParcela = nrParcela;
    }


    /**
     * Gets the dsLoja value for this TItemParcela.
     * 
     * @return dsLoja
     */
    public java.lang.String getDsLoja() {
        return dsLoja;
    }


    /**
     * Sets the dsLoja value for this TItemParcela.
     * 
     * @param dsLoja
     */
    public void setDsLoja(java.lang.String dsLoja) {
        this.dsLoja = dsLoja;
    }


    /**
     * Gets the dtVenda value for this TItemParcela.
     * 
     * @return dtVenda
     */
    public int getDtVenda() {
        return dtVenda;
    }


    /**
     * Sets the dtVenda value for this TItemParcela.
     * 
     * @param dtVenda
     */
    public void setDtVenda(int dtVenda) {
        this.dtVenda = dtVenda;
    }


    /**
     * Gets the vlVenda value for this TItemParcela.
     * 
     * @return vlVenda
     */
    public float getVlVenda() {
        return vlVenda;
    }


    /**
     * Sets the vlVenda value for this TItemParcela.
     * 
     * @param vlVenda
     */
    public void setVlVenda(float vlVenda) {
        this.vlVenda = vlVenda;
    }


    /**
     * Gets the dtVencimento value for this TItemParcela.
     * 
     * @return dtVencimento
     */
    public int getDtVencimento() {
        return dtVencimento;
    }


    /**
     * Sets the dtVencimento value for this TItemParcela.
     * 
     * @param dtVencimento
     */
    public void setDtVencimento(int dtVencimento) {
        this.dtVencimento = dtVencimento;
    }


    /**
     * Gets the vlParcela value for this TItemParcela.
     * 
     * @return vlParcela
     */
    public float getVlParcela() {
        return vlParcela;
    }


    /**
     * Sets the vlParcela value for this TItemParcela.
     * 
     * @param vlParcela
     */
    public void setVlParcela(float vlParcela) {
        this.vlParcela = vlParcela;
    }


    /**
     * Gets the dtPago value for this TItemParcela.
     * 
     * @return dtPago
     */
    public int getDtPago() {
        return dtPago;
    }


    /**
     * Sets the dtPago value for this TItemParcela.
     * 
     * @param dtPago
     */
    public void setDtPago(int dtPago) {
        this.dtPago = dtPago;
    }


    /**
     * Gets the vlPago value for this TItemParcela.
     * 
     * @return vlPago
     */
    public float getVlPago() {
        return vlPago;
    }


    /**
     * Sets the vlPago value for this TItemParcela.
     * 
     * @param vlPago
     */
    public void setVlPago(float vlPago) {
        this.vlPago = vlPago;
    }


    /**
     * Gets the dtParcial value for this TItemParcela.
     * 
     * @return dtParcial
     */
    public int getDtParcial() {
        return dtParcial;
    }


    /**
     * Sets the dtParcial value for this TItemParcela.
     * 
     * @param dtParcial
     */
    public void setDtParcial(int dtParcial) {
        this.dtParcial = dtParcial;
    }


    /**
     * Gets the vlParcial value for this TItemParcela.
     * 
     * @return vlParcial
     */
    public float getVlParcial() {
        return vlParcial;
    }


    /**
     * Sets the vlParcial value for this TItemParcela.
     * 
     * @param vlParcial
     */
    public void setVlParcial(float vlParcial) {
        this.vlParcial = vlParcial;
    }


    /**
     * Gets the dsDocumento value for this TItemParcela.
     * 
     * @return dsDocumento
     */
    public java.lang.String getDsDocumento() {
        return dsDocumento;
    }


    /**
     * Sets the dsDocumento value for this TItemParcela.
     * 
     * @param dsDocumento
     */
    public void setDsDocumento(java.lang.String dsDocumento) {
        this.dsDocumento = dsDocumento;
    }


    /**
     * Gets the vlQuitacao value for this TItemParcela.
     * 
     * @return vlQuitacao
     */
    public float getVlQuitacao() {
        return vlQuitacao;
    }


    /**
     * Sets the vlQuitacao value for this TItemParcela.
     * 
     * @param vlQuitacao
     */
    public void setVlQuitacao(float vlQuitacao) {
        this.vlQuitacao = vlQuitacao;
    }


    /**
     * Gets the linkBoleto value for this TItemParcela.
     * 
     * @return linkBoleto
     */
    public java.lang.String getLinkBoleto() {
        return linkBoleto;
    }


    /**
     * Sets the linkBoleto value for this TItemParcela.
     * 
     * @param linkBoleto
     */
    public void setLinkBoleto(java.lang.String linkBoleto) {
        this.linkBoleto = linkBoleto;
    }


    /**
     * Gets the prestacao value for this TItemParcela.
     * 
     * @return prestacao
     */
    public com.bonsucesso.servipa.ws.TPrestacao getPrestacao() {
        return prestacao;
    }


    /**
     * Sets the prestacao value for this TItemParcela.
     * 
     * @param prestacao
     */
    public void setPrestacao(com.bonsucesso.servipa.ws.TPrestacao prestacao) {
        this.prestacao = prestacao;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemParcela)) return false;
        TItemParcela other = (TItemParcela) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dsContrato==null && other.getDsContrato()==null) || 
             (this.dsContrato!=null &&
              this.dsContrato.equals(other.getDsContrato()))) &&
            this.nrParcela == other.getNrParcela() &&
            ((this.dsLoja==null && other.getDsLoja()==null) || 
             (this.dsLoja!=null &&
              this.dsLoja.equals(other.getDsLoja()))) &&
            this.dtVenda == other.getDtVenda() &&
            this.vlVenda == other.getVlVenda() &&
            this.dtVencimento == other.getDtVencimento() &&
            this.vlParcela == other.getVlParcela() &&
            this.dtPago == other.getDtPago() &&
            this.vlPago == other.getVlPago() &&
            this.dtParcial == other.getDtParcial() &&
            this.vlParcial == other.getVlParcial() &&
            ((this.dsDocumento==null && other.getDsDocumento()==null) || 
             (this.dsDocumento!=null &&
              this.dsDocumento.equals(other.getDsDocumento()))) &&
            this.vlQuitacao == other.getVlQuitacao() &&
            ((this.linkBoleto==null && other.getLinkBoleto()==null) || 
             (this.linkBoleto!=null &&
              this.linkBoleto.equals(other.getLinkBoleto()))) &&
            ((this.prestacao==null && other.getPrestacao()==null) || 
             (this.prestacao!=null &&
              this.prestacao.equals(other.getPrestacao())));
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
        if (getDsContrato() != null) {
            _hashCode += getDsContrato().hashCode();
        }
        _hashCode += getNrParcela();
        if (getDsLoja() != null) {
            _hashCode += getDsLoja().hashCode();
        }
        _hashCode += getDtVenda();
        _hashCode += new Float(getVlVenda()).hashCode();
        _hashCode += getDtVencimento();
        _hashCode += new Float(getVlParcela()).hashCode();
        _hashCode += getDtPago();
        _hashCode += new Float(getVlPago()).hashCode();
        _hashCode += getDtParcial();
        _hashCode += new Float(getVlParcial()).hashCode();
        if (getDsDocumento() != null) {
            _hashCode += getDsDocumento().hashCode();
        }
        _hashCode += new Float(getVlQuitacao()).hashCode();
        if (getLinkBoleto() != null) {
            _hashCode += getLinkBoleto().hashCode();
        }
        if (getPrestacao() != null) {
            _hashCode += getPrestacao().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TItemParcela.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemParcela"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsContrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrParcela");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrParcela"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsLoja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsLoja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtVenda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dtVenda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlVenda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlVenda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtVencimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dtVencimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlParcela");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlParcela"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dtPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtParcial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dtParcial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlParcial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlParcial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsDocumento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsDocumento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlQuitacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlQuitacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linkBoleto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "linkBoleto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prestacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prestacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TPrestacao"));
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
