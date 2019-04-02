/**
 * TItemRetornoRecebimentoCobradoraSemComissao.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemRetornoRecebimentoCobradoraSemComissao  implements java.io.Serializable {
    private java.lang.String dsCPF;

    private int nrLoja;

    private java.lang.String nrContrato;

    private int nrPrestacao;

    private int dtvct;

    private java.lang.String situacao;

    private float vlrPago;

    private float vlrAberto;

    public TItemRetornoRecebimentoCobradoraSemComissao() {
    }

    public TItemRetornoRecebimentoCobradoraSemComissao(
           java.lang.String dsCPF,
           int nrLoja,
           java.lang.String nrContrato,
           int nrPrestacao,
           int dtvct,
           java.lang.String situacao,
           float vlrPago,
           float vlrAberto) {
           this.dsCPF = dsCPF;
           this.nrLoja = nrLoja;
           this.nrContrato = nrContrato;
           this.nrPrestacao = nrPrestacao;
           this.dtvct = dtvct;
           this.situacao = situacao;
           this.vlrPago = vlrPago;
           this.vlrAberto = vlrAberto;
    }


    /**
     * Gets the dsCPF value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @return dsCPF
     */
    public java.lang.String getDsCPF() {
        return dsCPF;
    }


    /**
     * Sets the dsCPF value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @param dsCPF
     */
    public void setDsCPF(java.lang.String dsCPF) {
        this.dsCPF = dsCPF;
    }


    /**
     * Gets the nrLoja value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @return nrLoja
     */
    public int getNrLoja() {
        return nrLoja;
    }


    /**
     * Sets the nrLoja value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @param nrLoja
     */
    public void setNrLoja(int nrLoja) {
        this.nrLoja = nrLoja;
    }


    /**
     * Gets the nrContrato value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @return nrContrato
     */
    public java.lang.String getNrContrato() {
        return nrContrato;
    }


    /**
     * Sets the nrContrato value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @param nrContrato
     */
    public void setNrContrato(java.lang.String nrContrato) {
        this.nrContrato = nrContrato;
    }


    /**
     * Gets the nrPrestacao value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @return nrPrestacao
     */
    public int getNrPrestacao() {
        return nrPrestacao;
    }


    /**
     * Sets the nrPrestacao value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @param nrPrestacao
     */
    public void setNrPrestacao(int nrPrestacao) {
        this.nrPrestacao = nrPrestacao;
    }


    /**
     * Gets the dtvct value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @return dtvct
     */
    public int getDtvct() {
        return dtvct;
    }


    /**
     * Sets the dtvct value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @param dtvct
     */
    public void setDtvct(int dtvct) {
        this.dtvct = dtvct;
    }


    /**
     * Gets the situacao value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @return situacao
     */
    public java.lang.String getSituacao() {
        return situacao;
    }


    /**
     * Sets the situacao value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @param situacao
     */
    public void setSituacao(java.lang.String situacao) {
        this.situacao = situacao;
    }


    /**
     * Gets the vlrPago value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @return vlrPago
     */
    public float getVlrPago() {
        return vlrPago;
    }


    /**
     * Sets the vlrPago value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @param vlrPago
     */
    public void setVlrPago(float vlrPago) {
        this.vlrPago = vlrPago;
    }


    /**
     * Gets the vlrAberto value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @return vlrAberto
     */
    public float getVlrAberto() {
        return vlrAberto;
    }


    /**
     * Sets the vlrAberto value for this TItemRetornoRecebimentoCobradoraSemComissao.
     * 
     * @param vlrAberto
     */
    public void setVlrAberto(float vlrAberto) {
        this.vlrAberto = vlrAberto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemRetornoRecebimentoCobradoraSemComissao)) return false;
        TItemRetornoRecebimentoCobradoraSemComissao other = (TItemRetornoRecebimentoCobradoraSemComissao) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dsCPF==null && other.getDsCPF()==null) || 
             (this.dsCPF!=null &&
              this.dsCPF.equals(other.getDsCPF()))) &&
            this.nrLoja == other.getNrLoja() &&
            ((this.nrContrato==null && other.getNrContrato()==null) || 
             (this.nrContrato!=null &&
              this.nrContrato.equals(other.getNrContrato()))) &&
            this.nrPrestacao == other.getNrPrestacao() &&
            this.dtvct == other.getDtvct() &&
            ((this.situacao==null && other.getSituacao()==null) || 
             (this.situacao!=null &&
              this.situacao.equals(other.getSituacao()))) &&
            this.vlrPago == other.getVlrPago() &&
            this.vlrAberto == other.getVlrAberto();
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
        if (getDsCPF() != null) {
            _hashCode += getDsCPF().hashCode();
        }
        _hashCode += getNrLoja();
        if (getNrContrato() != null) {
            _hashCode += getNrContrato().hashCode();
        }
        _hashCode += getNrPrestacao();
        _hashCode += getDtvct();
        if (getSituacao() != null) {
            _hashCode += getSituacao().hashCode();
        }
        _hashCode += new Float(getVlrPago()).hashCode();
        _hashCode += new Float(getVlrAberto()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TItemRetornoRecebimentoCobradoraSemComissao.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRetornoRecebimentoCobradoraSemComissao"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsCPF");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsCPF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrLoja");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrLoja"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrContrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrPrestacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrPrestacao"));
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
        elemField.setFieldName("situacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "situacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlrPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlrPago"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlrAberto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlrAberto"));
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
