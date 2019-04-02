/**
 * TRetEfetuaPgto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetEfetuaPgto  implements java.io.Serializable {
    private int codigo;

    private java.lang.String mensagem;

    private boolean imprimir;

    private java.lang.String juros;

    private java.lang.String jurosAMenor;

    private java.lang.String multa;

    private java.lang.String nrContrato;

    private boolean pagtoParcial;

    private java.lang.String saida;

    private int tipoContrato;

    private java.lang.String urlRecibo;

    private java.lang.String valorDesc;

    private java.lang.String valorDevido;

    private java.lang.String valorPago;

    public TRetEfetuaPgto() {
    }

    public TRetEfetuaPgto(
           int codigo,
           java.lang.String mensagem,
           boolean imprimir,
           java.lang.String juros,
           java.lang.String jurosAMenor,
           java.lang.String multa,
           java.lang.String nrContrato,
           boolean pagtoParcial,
           java.lang.String saida,
           int tipoContrato,
           java.lang.String urlRecibo,
           java.lang.String valorDesc,
           java.lang.String valorDevido,
           java.lang.String valorPago) {
           this.codigo = codigo;
           this.mensagem = mensagem;
           this.imprimir = imprimir;
           this.juros = juros;
           this.jurosAMenor = jurosAMenor;
           this.multa = multa;
           this.nrContrato = nrContrato;
           this.pagtoParcial = pagtoParcial;
           this.saida = saida;
           this.tipoContrato = tipoContrato;
           this.urlRecibo = urlRecibo;
           this.valorDesc = valorDesc;
           this.valorDevido = valorDevido;
           this.valorPago = valorPago;
    }


    /**
     * Gets the codigo value for this TRetEfetuaPgto.
     * 
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this TRetEfetuaPgto.
     * 
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the mensagem value for this TRetEfetuaPgto.
     * 
     * @return mensagem
     */
    public java.lang.String getMensagem() {
        return mensagem;
    }


    /**
     * Sets the mensagem value for this TRetEfetuaPgto.
     * 
     * @param mensagem
     */
    public void setMensagem(java.lang.String mensagem) {
        this.mensagem = mensagem;
    }


    /**
     * Gets the imprimir value for this TRetEfetuaPgto.
     * 
     * @return imprimir
     */
    public boolean isImprimir() {
        return imprimir;
    }


    /**
     * Sets the imprimir value for this TRetEfetuaPgto.
     * 
     * @param imprimir
     */
    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }


    /**
     * Gets the juros value for this TRetEfetuaPgto.
     * 
     * @return juros
     */
    public java.lang.String getJuros() {
        return juros;
    }


    /**
     * Sets the juros value for this TRetEfetuaPgto.
     * 
     * @param juros
     */
    public void setJuros(java.lang.String juros) {
        this.juros = juros;
    }


    /**
     * Gets the jurosAMenor value for this TRetEfetuaPgto.
     * 
     * @return jurosAMenor
     */
    public java.lang.String getJurosAMenor() {
        return jurosAMenor;
    }


    /**
     * Sets the jurosAMenor value for this TRetEfetuaPgto.
     * 
     * @param jurosAMenor
     */
    public void setJurosAMenor(java.lang.String jurosAMenor) {
        this.jurosAMenor = jurosAMenor;
    }


    /**
     * Gets the multa value for this TRetEfetuaPgto.
     * 
     * @return multa
     */
    public java.lang.String getMulta() {
        return multa;
    }


    /**
     * Sets the multa value for this TRetEfetuaPgto.
     * 
     * @param multa
     */
    public void setMulta(java.lang.String multa) {
        this.multa = multa;
    }


    /**
     * Gets the nrContrato value for this TRetEfetuaPgto.
     * 
     * @return nrContrato
     */
    public java.lang.String getNrContrato() {
        return nrContrato;
    }


    /**
     * Sets the nrContrato value for this TRetEfetuaPgto.
     * 
     * @param nrContrato
     */
    public void setNrContrato(java.lang.String nrContrato) {
        this.nrContrato = nrContrato;
    }


    /**
     * Gets the pagtoParcial value for this TRetEfetuaPgto.
     * 
     * @return pagtoParcial
     */
    public boolean isPagtoParcial() {
        return pagtoParcial;
    }


    /**
     * Sets the pagtoParcial value for this TRetEfetuaPgto.
     * 
     * @param pagtoParcial
     */
    public void setPagtoParcial(boolean pagtoParcial) {
        this.pagtoParcial = pagtoParcial;
    }


    /**
     * Gets the saida value for this TRetEfetuaPgto.
     * 
     * @return saida
     */
    public java.lang.String getSaida() {
        return saida;
    }


    /**
     * Sets the saida value for this TRetEfetuaPgto.
     * 
     * @param saida
     */
    public void setSaida(java.lang.String saida) {
        this.saida = saida;
    }


    /**
     * Gets the tipoContrato value for this TRetEfetuaPgto.
     * 
     * @return tipoContrato
     */
    public int getTipoContrato() {
        return tipoContrato;
    }


    /**
     * Sets the tipoContrato value for this TRetEfetuaPgto.
     * 
     * @param tipoContrato
     */
    public void setTipoContrato(int tipoContrato) {
        this.tipoContrato = tipoContrato;
    }


    /**
     * Gets the urlRecibo value for this TRetEfetuaPgto.
     * 
     * @return urlRecibo
     */
    public java.lang.String getUrlRecibo() {
        return urlRecibo;
    }


    /**
     * Sets the urlRecibo value for this TRetEfetuaPgto.
     * 
     * @param urlRecibo
     */
    public void setUrlRecibo(java.lang.String urlRecibo) {
        this.urlRecibo = urlRecibo;
    }


    /**
     * Gets the valorDesc value for this TRetEfetuaPgto.
     * 
     * @return valorDesc
     */
    public java.lang.String getValorDesc() {
        return valorDesc;
    }


    /**
     * Sets the valorDesc value for this TRetEfetuaPgto.
     * 
     * @param valorDesc
     */
    public void setValorDesc(java.lang.String valorDesc) {
        this.valorDesc = valorDesc;
    }


    /**
     * Gets the valorDevido value for this TRetEfetuaPgto.
     * 
     * @return valorDevido
     */
    public java.lang.String getValorDevido() {
        return valorDevido;
    }


    /**
     * Sets the valorDevido value for this TRetEfetuaPgto.
     * 
     * @param valorDevido
     */
    public void setValorDevido(java.lang.String valorDevido) {
        this.valorDevido = valorDevido;
    }


    /**
     * Gets the valorPago value for this TRetEfetuaPgto.
     * 
     * @return valorPago
     */
    public java.lang.String getValorPago() {
        return valorPago;
    }


    /**
     * Sets the valorPago value for this TRetEfetuaPgto.
     * 
     * @param valorPago
     */
    public void setValorPago(java.lang.String valorPago) {
        this.valorPago = valorPago;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetEfetuaPgto)) return false;
        TRetEfetuaPgto other = (TRetEfetuaPgto) obj;
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
            this.imprimir == other.isImprimir() &&
            ((this.juros==null && other.getJuros()==null) || 
             (this.juros!=null &&
              this.juros.equals(other.getJuros()))) &&
            ((this.jurosAMenor==null && other.getJurosAMenor()==null) || 
             (this.jurosAMenor!=null &&
              this.jurosAMenor.equals(other.getJurosAMenor()))) &&
            ((this.multa==null && other.getMulta()==null) || 
             (this.multa!=null &&
              this.multa.equals(other.getMulta()))) &&
            ((this.nrContrato==null && other.getNrContrato()==null) || 
             (this.nrContrato!=null &&
              this.nrContrato.equals(other.getNrContrato()))) &&
            this.pagtoParcial == other.isPagtoParcial() &&
            ((this.saida==null && other.getSaida()==null) || 
             (this.saida!=null &&
              this.saida.equals(other.getSaida()))) &&
            this.tipoContrato == other.getTipoContrato() &&
            ((this.urlRecibo==null && other.getUrlRecibo()==null) || 
             (this.urlRecibo!=null &&
              this.urlRecibo.equals(other.getUrlRecibo()))) &&
            ((this.valorDesc==null && other.getValorDesc()==null) || 
             (this.valorDesc!=null &&
              this.valorDesc.equals(other.getValorDesc()))) &&
            ((this.valorDevido==null && other.getValorDevido()==null) || 
             (this.valorDevido!=null &&
              this.valorDevido.equals(other.getValorDevido()))) &&
            ((this.valorPago==null && other.getValorPago()==null) || 
             (this.valorPago!=null &&
              this.valorPago.equals(other.getValorPago())));
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
        _hashCode += (isImprimir() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getJuros() != null) {
            _hashCode += getJuros().hashCode();
        }
        if (getJurosAMenor() != null) {
            _hashCode += getJurosAMenor().hashCode();
        }
        if (getMulta() != null) {
            _hashCode += getMulta().hashCode();
        }
        if (getNrContrato() != null) {
            _hashCode += getNrContrato().hashCode();
        }
        _hashCode += (isPagtoParcial() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSaida() != null) {
            _hashCode += getSaida().hashCode();
        }
        _hashCode += getTipoContrato();
        if (getUrlRecibo() != null) {
            _hashCode += getUrlRecibo().hashCode();
        }
        if (getValorDesc() != null) {
            _hashCode += getValorDesc().hashCode();
        }
        if (getValorDevido() != null) {
            _hashCode += getValorDevido().hashCode();
        }
        if (getValorPago() != null) {
            _hashCode += getValorPago().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TRetEfetuaPgto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetEfetuaPgto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mensagem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Mensagem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imprimir");
        elemField.setXmlName(new javax.xml.namespace.QName("", "imprimir"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("juros");
        elemField.setXmlName(new javax.xml.namespace.QName("", "juros"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jurosAMenor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jurosAMenor"));
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
        elemField.setFieldName("nrContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrContrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pagtoParcial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pagtoParcial"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saida");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saida"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoContrato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("urlRecibo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "urlRecibo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorDevido");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorDevido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valorPago");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valorPago"));
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
