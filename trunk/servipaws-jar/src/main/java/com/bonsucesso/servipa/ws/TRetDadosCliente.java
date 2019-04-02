/**
 * TRetDadosCliente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetDadosCliente  implements java.io.Serializable {
    private int codigo;

    private java.lang.String mensagem;

    private com.bonsucesso.servipa.ws.TDadosPessoaisCliente dadosPessoaisCliente;

    private com.bonsucesso.servipa.ws.TEnderecoCliente enderecoCliente;

    private com.bonsucesso.servipa.ws.TDadosTrabalhoCliente dadosTrabalhoCliente;

    private com.bonsucesso.servipa.ws.TDadosConjugeCliente dadosConjugeCliente;

    private com.bonsucesso.servipa.ws.TDadosReferenciaCliente dadosReferenciaCliente;

    private com.bonsucesso.servipa.ws.TDadosReferenciaClienteItem[] dadosReferenciaLista;

    private com.bonsucesso.servipa.ws.TDadosRendaFamiliar[] dadosRendaFamiliar;

    private com.bonsucesso.servipa.ws.TDadosReferenciaComercial[] dadosReferenciaComercial;

    public TRetDadosCliente() {
    }

    public TRetDadosCliente(
           int codigo,
           java.lang.String mensagem,
           com.bonsucesso.servipa.ws.TDadosPessoaisCliente dadosPessoaisCliente,
           com.bonsucesso.servipa.ws.TEnderecoCliente enderecoCliente,
           com.bonsucesso.servipa.ws.TDadosTrabalhoCliente dadosTrabalhoCliente,
           com.bonsucesso.servipa.ws.TDadosConjugeCliente dadosConjugeCliente,
           com.bonsucesso.servipa.ws.TDadosReferenciaCliente dadosReferenciaCliente,
           com.bonsucesso.servipa.ws.TDadosReferenciaClienteItem[] dadosReferenciaLista,
           com.bonsucesso.servipa.ws.TDadosRendaFamiliar[] dadosRendaFamiliar,
           com.bonsucesso.servipa.ws.TDadosReferenciaComercial[] dadosReferenciaComercial) {
           this.codigo = codigo;
           this.mensagem = mensagem;
           this.dadosPessoaisCliente = dadosPessoaisCliente;
           this.enderecoCliente = enderecoCliente;
           this.dadosTrabalhoCliente = dadosTrabalhoCliente;
           this.dadosConjugeCliente = dadosConjugeCliente;
           this.dadosReferenciaCliente = dadosReferenciaCliente;
           this.dadosReferenciaLista = dadosReferenciaLista;
           this.dadosRendaFamiliar = dadosRendaFamiliar;
           this.dadosReferenciaComercial = dadosReferenciaComercial;
    }


    /**
     * Gets the codigo value for this TRetDadosCliente.
     * 
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this TRetDadosCliente.
     * 
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the mensagem value for this TRetDadosCliente.
     * 
     * @return mensagem
     */
    public java.lang.String getMensagem() {
        return mensagem;
    }


    /**
     * Sets the mensagem value for this TRetDadosCliente.
     * 
     * @param mensagem
     */
    public void setMensagem(java.lang.String mensagem) {
        this.mensagem = mensagem;
    }


    /**
     * Gets the dadosPessoaisCliente value for this TRetDadosCliente.
     * 
     * @return dadosPessoaisCliente
     */
    public com.bonsucesso.servipa.ws.TDadosPessoaisCliente getDadosPessoaisCliente() {
        return dadosPessoaisCliente;
    }


    /**
     * Sets the dadosPessoaisCliente value for this TRetDadosCliente.
     * 
     * @param dadosPessoaisCliente
     */
    public void setDadosPessoaisCliente(com.bonsucesso.servipa.ws.TDadosPessoaisCliente dadosPessoaisCliente) {
        this.dadosPessoaisCliente = dadosPessoaisCliente;
    }


    /**
     * Gets the enderecoCliente value for this TRetDadosCliente.
     * 
     * @return enderecoCliente
     */
    public com.bonsucesso.servipa.ws.TEnderecoCliente getEnderecoCliente() {
        return enderecoCliente;
    }


    /**
     * Sets the enderecoCliente value for this TRetDadosCliente.
     * 
     * @param enderecoCliente
     */
    public void setEnderecoCliente(com.bonsucesso.servipa.ws.TEnderecoCliente enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }


    /**
     * Gets the dadosTrabalhoCliente value for this TRetDadosCliente.
     * 
     * @return dadosTrabalhoCliente
     */
    public com.bonsucesso.servipa.ws.TDadosTrabalhoCliente getDadosTrabalhoCliente() {
        return dadosTrabalhoCliente;
    }


    /**
     * Sets the dadosTrabalhoCliente value for this TRetDadosCliente.
     * 
     * @param dadosTrabalhoCliente
     */
    public void setDadosTrabalhoCliente(com.bonsucesso.servipa.ws.TDadosTrabalhoCliente dadosTrabalhoCliente) {
        this.dadosTrabalhoCliente = dadosTrabalhoCliente;
    }


    /**
     * Gets the dadosConjugeCliente value for this TRetDadosCliente.
     * 
     * @return dadosConjugeCliente
     */
    public com.bonsucesso.servipa.ws.TDadosConjugeCliente getDadosConjugeCliente() {
        return dadosConjugeCliente;
    }


    /**
     * Sets the dadosConjugeCliente value for this TRetDadosCliente.
     * 
     * @param dadosConjugeCliente
     */
    public void setDadosConjugeCliente(com.bonsucesso.servipa.ws.TDadosConjugeCliente dadosConjugeCliente) {
        this.dadosConjugeCliente = dadosConjugeCliente;
    }


    /**
     * Gets the dadosReferenciaCliente value for this TRetDadosCliente.
     * 
     * @return dadosReferenciaCliente
     */
    public com.bonsucesso.servipa.ws.TDadosReferenciaCliente getDadosReferenciaCliente() {
        return dadosReferenciaCliente;
    }


    /**
     * Sets the dadosReferenciaCliente value for this TRetDadosCliente.
     * 
     * @param dadosReferenciaCliente
     */
    public void setDadosReferenciaCliente(com.bonsucesso.servipa.ws.TDadosReferenciaCliente dadosReferenciaCliente) {
        this.dadosReferenciaCliente = dadosReferenciaCliente;
    }


    /**
     * Gets the dadosReferenciaLista value for this TRetDadosCliente.
     * 
     * @return dadosReferenciaLista
     */
    public com.bonsucesso.servipa.ws.TDadosReferenciaClienteItem[] getDadosReferenciaLista() {
        return dadosReferenciaLista;
    }


    /**
     * Sets the dadosReferenciaLista value for this TRetDadosCliente.
     * 
     * @param dadosReferenciaLista
     */
    public void setDadosReferenciaLista(com.bonsucesso.servipa.ws.TDadosReferenciaClienteItem[] dadosReferenciaLista) {
        this.dadosReferenciaLista = dadosReferenciaLista;
    }


    /**
     * Gets the dadosRendaFamiliar value for this TRetDadosCliente.
     * 
     * @return dadosRendaFamiliar
     */
    public com.bonsucesso.servipa.ws.TDadosRendaFamiliar[] getDadosRendaFamiliar() {
        return dadosRendaFamiliar;
    }


    /**
     * Sets the dadosRendaFamiliar value for this TRetDadosCliente.
     * 
     * @param dadosRendaFamiliar
     */
    public void setDadosRendaFamiliar(com.bonsucesso.servipa.ws.TDadosRendaFamiliar[] dadosRendaFamiliar) {
        this.dadosRendaFamiliar = dadosRendaFamiliar;
    }


    /**
     * Gets the dadosReferenciaComercial value for this TRetDadosCliente.
     * 
     * @return dadosReferenciaComercial
     */
    public com.bonsucesso.servipa.ws.TDadosReferenciaComercial[] getDadosReferenciaComercial() {
        return dadosReferenciaComercial;
    }


    /**
     * Sets the dadosReferenciaComercial value for this TRetDadosCliente.
     * 
     * @param dadosReferenciaComercial
     */
    public void setDadosReferenciaComercial(com.bonsucesso.servipa.ws.TDadosReferenciaComercial[] dadosReferenciaComercial) {
        this.dadosReferenciaComercial = dadosReferenciaComercial;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetDadosCliente)) return false;
        TRetDadosCliente other = (TRetDadosCliente) obj;
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
            ((this.dadosPessoaisCliente==null && other.getDadosPessoaisCliente()==null) || 
             (this.dadosPessoaisCliente!=null &&
              this.dadosPessoaisCliente.equals(other.getDadosPessoaisCliente()))) &&
            ((this.enderecoCliente==null && other.getEnderecoCliente()==null) || 
             (this.enderecoCliente!=null &&
              this.enderecoCliente.equals(other.getEnderecoCliente()))) &&
            ((this.dadosTrabalhoCliente==null && other.getDadosTrabalhoCliente()==null) || 
             (this.dadosTrabalhoCliente!=null &&
              this.dadosTrabalhoCliente.equals(other.getDadosTrabalhoCliente()))) &&
            ((this.dadosConjugeCliente==null && other.getDadosConjugeCliente()==null) || 
             (this.dadosConjugeCliente!=null &&
              this.dadosConjugeCliente.equals(other.getDadosConjugeCliente()))) &&
            ((this.dadosReferenciaCliente==null && other.getDadosReferenciaCliente()==null) || 
             (this.dadosReferenciaCliente!=null &&
              this.dadosReferenciaCliente.equals(other.getDadosReferenciaCliente()))) &&
            ((this.dadosReferenciaLista==null && other.getDadosReferenciaLista()==null) || 
             (this.dadosReferenciaLista!=null &&
              java.util.Arrays.equals(this.dadosReferenciaLista, other.getDadosReferenciaLista()))) &&
            ((this.dadosRendaFamiliar==null && other.getDadosRendaFamiliar()==null) || 
             (this.dadosRendaFamiliar!=null &&
              java.util.Arrays.equals(this.dadosRendaFamiliar, other.getDadosRendaFamiliar()))) &&
            ((this.dadosReferenciaComercial==null && other.getDadosReferenciaComercial()==null) || 
             (this.dadosReferenciaComercial!=null &&
              java.util.Arrays.equals(this.dadosReferenciaComercial, other.getDadosReferenciaComercial())));
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
        if (getDadosPessoaisCliente() != null) {
            _hashCode += getDadosPessoaisCliente().hashCode();
        }
        if (getEnderecoCliente() != null) {
            _hashCode += getEnderecoCliente().hashCode();
        }
        if (getDadosTrabalhoCliente() != null) {
            _hashCode += getDadosTrabalhoCliente().hashCode();
        }
        if (getDadosConjugeCliente() != null) {
            _hashCode += getDadosConjugeCliente().hashCode();
        }
        if (getDadosReferenciaCliente() != null) {
            _hashCode += getDadosReferenciaCliente().hashCode();
        }
        if (getDadosReferenciaLista() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDadosReferenciaLista());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDadosReferenciaLista(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDadosRendaFamiliar() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDadosRendaFamiliar());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDadosRendaFamiliar(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDadosReferenciaComercial() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDadosReferenciaComercial());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDadosReferenciaComercial(), i);
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
        new org.apache.axis.description.TypeDesc(TRetDadosCliente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetDadosCliente"));
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
        elemField.setFieldName("dadosPessoaisCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DadosPessoaisCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TDadosPessoaisCliente"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enderecoCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EnderecoCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TEnderecoCliente"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dadosTrabalhoCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DadosTrabalhoCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TDadosTrabalhoCliente"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dadosConjugeCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DadosConjugeCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TDadosConjugeCliente"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dadosReferenciaCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DadosReferenciaCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TDadosReferenciaCliente"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dadosReferenciaLista");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DadosReferenciaLista"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TDadosReferenciaClienteItem"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dadosRendaFamiliar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DadosRendaFamiliar"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TDadosRendaFamiliar"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dadosReferenciaComercial");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DadosReferenciaComercial"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TDadosReferenciaComercial"));
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
