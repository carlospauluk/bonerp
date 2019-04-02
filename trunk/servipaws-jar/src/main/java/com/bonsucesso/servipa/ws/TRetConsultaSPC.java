/**
 * TRetConsultaSPC.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetConsultaSPC  implements java.io.Serializable {
    private int codigo;

    private java.lang.String mensagem;

    private java.lang.String dsRetorno;

    private java.lang.String dsCor;

    private java.lang.String dsNome;

    private java.lang.String dsCPFCNPJ;

    private int dtConsulta;

    private com.bonsucesso.servipa.ws.TItemConsultaAnterior[] consultaAnterior;

    private com.bonsucesso.servipa.ws.TItemRegistroSPC[] registros;

    public TRetConsultaSPC() {
    }

    public TRetConsultaSPC(
           int codigo,
           java.lang.String mensagem,
           java.lang.String dsRetorno,
           java.lang.String dsCor,
           java.lang.String dsNome,
           java.lang.String dsCPFCNPJ,
           int dtConsulta,
           com.bonsucesso.servipa.ws.TItemConsultaAnterior[] consultaAnterior,
           com.bonsucesso.servipa.ws.TItemRegistroSPC[] registros) {
           this.codigo = codigo;
           this.mensagem = mensagem;
           this.dsRetorno = dsRetorno;
           this.dsCor = dsCor;
           this.dsNome = dsNome;
           this.dsCPFCNPJ = dsCPFCNPJ;
           this.dtConsulta = dtConsulta;
           this.consultaAnterior = consultaAnterior;
           this.registros = registros;
    }


    /**
     * Gets the codigo value for this TRetConsultaSPC.
     * 
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this TRetConsultaSPC.
     * 
     * @param codigo
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the mensagem value for this TRetConsultaSPC.
     * 
     * @return mensagem
     */
    public java.lang.String getMensagem() {
        return mensagem;
    }


    /**
     * Sets the mensagem value for this TRetConsultaSPC.
     * 
     * @param mensagem
     */
    public void setMensagem(java.lang.String mensagem) {
        this.mensagem = mensagem;
    }


    /**
     * Gets the dsRetorno value for this TRetConsultaSPC.
     * 
     * @return dsRetorno
     */
    public java.lang.String getDsRetorno() {
        return dsRetorno;
    }


    /**
     * Sets the dsRetorno value for this TRetConsultaSPC.
     * 
     * @param dsRetorno
     */
    public void setDsRetorno(java.lang.String dsRetorno) {
        this.dsRetorno = dsRetorno;
    }


    /**
     * Gets the dsCor value for this TRetConsultaSPC.
     * 
     * @return dsCor
     */
    public java.lang.String getDsCor() {
        return dsCor;
    }


    /**
     * Sets the dsCor value for this TRetConsultaSPC.
     * 
     * @param dsCor
     */
    public void setDsCor(java.lang.String dsCor) {
        this.dsCor = dsCor;
    }


    /**
     * Gets the dsNome value for this TRetConsultaSPC.
     * 
     * @return dsNome
     */
    public java.lang.String getDsNome() {
        return dsNome;
    }


    /**
     * Sets the dsNome value for this TRetConsultaSPC.
     * 
     * @param dsNome
     */
    public void setDsNome(java.lang.String dsNome) {
        this.dsNome = dsNome;
    }


    /**
     * Gets the dsCPFCNPJ value for this TRetConsultaSPC.
     * 
     * @return dsCPFCNPJ
     */
    public java.lang.String getDsCPFCNPJ() {
        return dsCPFCNPJ;
    }


    /**
     * Sets the dsCPFCNPJ value for this TRetConsultaSPC.
     * 
     * @param dsCPFCNPJ
     */
    public void setDsCPFCNPJ(java.lang.String dsCPFCNPJ) {
        this.dsCPFCNPJ = dsCPFCNPJ;
    }


    /**
     * Gets the dtConsulta value for this TRetConsultaSPC.
     * 
     * @return dtConsulta
     */
    public int getDtConsulta() {
        return dtConsulta;
    }


    /**
     * Sets the dtConsulta value for this TRetConsultaSPC.
     * 
     * @param dtConsulta
     */
    public void setDtConsulta(int dtConsulta) {
        this.dtConsulta = dtConsulta;
    }


    /**
     * Gets the consultaAnterior value for this TRetConsultaSPC.
     * 
     * @return consultaAnterior
     */
    public com.bonsucesso.servipa.ws.TItemConsultaAnterior[] getConsultaAnterior() {
        return consultaAnterior;
    }


    /**
     * Sets the consultaAnterior value for this TRetConsultaSPC.
     * 
     * @param consultaAnterior
     */
    public void setConsultaAnterior(com.bonsucesso.servipa.ws.TItemConsultaAnterior[] consultaAnterior) {
        this.consultaAnterior = consultaAnterior;
    }


    /**
     * Gets the registros value for this TRetConsultaSPC.
     * 
     * @return registros
     */
    public com.bonsucesso.servipa.ws.TItemRegistroSPC[] getRegistros() {
        return registros;
    }


    /**
     * Sets the registros value for this TRetConsultaSPC.
     * 
     * @param registros
     */
    public void setRegistros(com.bonsucesso.servipa.ws.TItemRegistroSPC[] registros) {
        this.registros = registros;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetConsultaSPC)) return false;
        TRetConsultaSPC other = (TRetConsultaSPC) obj;
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
            ((this.dsRetorno==null && other.getDsRetorno()==null) || 
             (this.dsRetorno!=null &&
              this.dsRetorno.equals(other.getDsRetorno()))) &&
            ((this.dsCor==null && other.getDsCor()==null) || 
             (this.dsCor!=null &&
              this.dsCor.equals(other.getDsCor()))) &&
            ((this.dsNome==null && other.getDsNome()==null) || 
             (this.dsNome!=null &&
              this.dsNome.equals(other.getDsNome()))) &&
            ((this.dsCPFCNPJ==null && other.getDsCPFCNPJ()==null) || 
             (this.dsCPFCNPJ!=null &&
              this.dsCPFCNPJ.equals(other.getDsCPFCNPJ()))) &&
            this.dtConsulta == other.getDtConsulta() &&
            ((this.consultaAnterior==null && other.getConsultaAnterior()==null) || 
             (this.consultaAnterior!=null &&
              java.util.Arrays.equals(this.consultaAnterior, other.getConsultaAnterior()))) &&
            ((this.registros==null && other.getRegistros()==null) || 
             (this.registros!=null &&
              java.util.Arrays.equals(this.registros, other.getRegistros())));
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
        if (getDsRetorno() != null) {
            _hashCode += getDsRetorno().hashCode();
        }
        if (getDsCor() != null) {
            _hashCode += getDsCor().hashCode();
        }
        if (getDsNome() != null) {
            _hashCode += getDsNome().hashCode();
        }
        if (getDsCPFCNPJ() != null) {
            _hashCode += getDsCPFCNPJ().hashCode();
        }
        _hashCode += getDtConsulta();
        if (getConsultaAnterior() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConsultaAnterior());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConsultaAnterior(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRegistros() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRegistros());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRegistros(), i);
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
        new org.apache.axis.description.TypeDesc(TRetConsultaSPC.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetConsultaSPC"));
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
        elemField.setFieldName("dsRetorno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsRetorno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsCor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsCor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsNome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsNome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsCPFCNPJ");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsCPFCNPJ"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtConsulta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dtConsulta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consultaAnterior");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ConsultaAnterior"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemConsultaAnterior"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registros");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Registros"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRegistroSPC"));
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
