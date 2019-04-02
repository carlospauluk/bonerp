/**
 * TRetConsultaSituacao.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetConsultaSituacao  implements java.io.Serializable {
    private int codigo;

    private java.lang.String mensagem;

    private com.bonsucesso.servipa.ws.TRetResumoSituacao resumo;

    private com.bonsucesso.servipa.ws.TRetResumoSituacao resumoGrupo;

    private com.bonsucesso.servipa.ws.TItemParcela[] parcelasVencidas;

    private com.bonsucesso.servipa.ws.TItemParcela[] parcelasAberta;

    private com.bonsucesso.servipa.ws.TItemParcela[] parcelasPaga;

    public TRetConsultaSituacao() {
    }

    public TRetConsultaSituacao(
           int codigo,
           java.lang.String mensagem,
           com.bonsucesso.servipa.ws.TRetResumoSituacao resumo,
           com.bonsucesso.servipa.ws.TRetResumoSituacao resumoGrupo,
           com.bonsucesso.servipa.ws.TItemParcela[] parcelasVencidas,
           com.bonsucesso.servipa.ws.TItemParcela[] parcelasAberta,
           com.bonsucesso.servipa.ws.TItemParcela[] parcelasPaga) {
           this.codigo = codigo;
           this.mensagem = mensagem;
           this.resumo = resumo;
           this.resumoGrupo = resumoGrupo;
           this.parcelasVencidas = parcelasVencidas;
           this.parcelasAberta = parcelasAberta;
           this.parcelasPaga = parcelasPaga;
    }


    /**
     * Gets the codigo value for this TRetConsultaSituacao.
     * 
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this TRetConsultaSituacao.
     * 
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the mensagem value for this TRetConsultaSituacao.
     * 
     * @return mensagem
     */
    public java.lang.String getMensagem() {
        return mensagem;
    }


    /**
     * Sets the mensagem value for this TRetConsultaSituacao.
     * 
     * @param mensagem
     */
    public void setMensagem(java.lang.String mensagem) {
        this.mensagem = mensagem;
    }


    /**
     * Gets the resumo value for this TRetConsultaSituacao.
     * 
     * @return resumo
     */
    public com.bonsucesso.servipa.ws.TRetResumoSituacao getResumo() {
        return resumo;
    }


    /**
     * Sets the resumo value for this TRetConsultaSituacao.
     * 
     * @param resumo
     */
    public void setResumo(com.bonsucesso.servipa.ws.TRetResumoSituacao resumo) {
        this.resumo = resumo;
    }


    /**
     * Gets the resumoGrupo value for this TRetConsultaSituacao.
     * 
     * @return resumoGrupo
     */
    public com.bonsucesso.servipa.ws.TRetResumoSituacao getResumoGrupo() {
        return resumoGrupo;
    }


    /**
     * Sets the resumoGrupo value for this TRetConsultaSituacao.
     * 
     * @param resumoGrupo
     */
    public void setResumoGrupo(com.bonsucesso.servipa.ws.TRetResumoSituacao resumoGrupo) {
        this.resumoGrupo = resumoGrupo;
    }


    /**
     * Gets the parcelasVencidas value for this TRetConsultaSituacao.
     * 
     * @return parcelasVencidas
     */
    public com.bonsucesso.servipa.ws.TItemParcela[] getParcelasVencidas() {
        return parcelasVencidas;
    }


    /**
     * Sets the parcelasVencidas value for this TRetConsultaSituacao.
     * 
     * @param parcelasVencidas
     */
    public void setParcelasVencidas(com.bonsucesso.servipa.ws.TItemParcela[] parcelasVencidas) {
        this.parcelasVencidas = parcelasVencidas;
    }


    /**
     * Gets the parcelasAberta value for this TRetConsultaSituacao.
     * 
     * @return parcelasAberta
     */
    public com.bonsucesso.servipa.ws.TItemParcela[] getParcelasAberta() {
        return parcelasAberta;
    }


    /**
     * Sets the parcelasAberta value for this TRetConsultaSituacao.
     * 
     * @param parcelasAberta
     */
    public void setParcelasAberta(com.bonsucesso.servipa.ws.TItemParcela[] parcelasAberta) {
        this.parcelasAberta = parcelasAberta;
    }


    /**
     * Gets the parcelasPaga value for this TRetConsultaSituacao.
     * 
     * @return parcelasPaga
     */
    public com.bonsucesso.servipa.ws.TItemParcela[] getParcelasPaga() {
        return parcelasPaga;
    }


    /**
     * Sets the parcelasPaga value for this TRetConsultaSituacao.
     * 
     * @param parcelasPaga
     */
    public void setParcelasPaga(com.bonsucesso.servipa.ws.TItemParcela[] parcelasPaga) {
        this.parcelasPaga = parcelasPaga;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetConsultaSituacao)) return false;
        TRetConsultaSituacao other = (TRetConsultaSituacao) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigo == other.getCodigo() &&
            ((this.mensagem==null && other.getMensagem()==null) || 
             (this.mensagem!=null &&
              this.mensagem.equals(other.getMensagem()))) &&
            ((this.resumo==null && other.getResumo()==null) || 
             (this.resumo!=null &&
              this.resumo.equals(other.getResumo()))) &&
            ((this.resumoGrupo==null && other.getResumoGrupo()==null) || 
             (this.resumoGrupo!=null &&
              this.resumoGrupo.equals(other.getResumoGrupo()))) &&
            ((this.parcelasVencidas==null && other.getParcelasVencidas()==null) || 
             (this.parcelasVencidas!=null &&
              java.util.Arrays.equals(this.parcelasVencidas, other.getParcelasVencidas()))) &&
            ((this.parcelasAberta==null && other.getParcelasAberta()==null) || 
             (this.parcelasAberta!=null &&
              java.util.Arrays.equals(this.parcelasAberta, other.getParcelasAberta()))) &&
            ((this.parcelasPaga==null && other.getParcelasPaga()==null) || 
             (this.parcelasPaga!=null &&
              java.util.Arrays.equals(this.parcelasPaga, other.getParcelasPaga())));
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
        _hashCode += getCodigo();
        if (getMensagem() != null) {
            _hashCode += getMensagem().hashCode();
        }
        if (getResumo() != null) {
            _hashCode += getResumo().hashCode();
        }
        if (getResumoGrupo() != null) {
            _hashCode += getResumoGrupo().hashCode();
        }
        if (getParcelasVencidas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParcelasVencidas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParcelasVencidas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getParcelasAberta() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParcelasAberta());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParcelasAberta(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getParcelasPaga() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getParcelasPaga());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getParcelasPaga(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TRetConsultaSituacao.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetConsultaSituacao"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensagem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mensagem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resumo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Resumo"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetResumoSituacao"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resumoGrupo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ResumoGrupo"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetResumoSituacao"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parcelasVencidas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ParcelasVencidas"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemParcela"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parcelasAberta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ParcelasAberta"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemParcela"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parcelasPaga");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ParcelasPaga"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemParcela"));
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
