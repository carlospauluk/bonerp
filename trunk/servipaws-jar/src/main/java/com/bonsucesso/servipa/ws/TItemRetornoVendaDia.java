/**
 * TItemRetornoVendaDia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemRetornoVendaDia  implements java.io.Serializable {
    private java.lang.String nrContrato;

    private int nrLoja;

    private int nrQtdPrestacao;

    private int dtVenda;

    private float vlrVenda;

    private float vlrFinanciado;

    private java.lang.String dsCPF;

    private java.lang.String nrDoc;

    private com.bonsucesso.servipa.ws.TPrestacao[] listPrestacao;

    public TItemRetornoVendaDia() {
    }

    public TItemRetornoVendaDia(
           java.lang.String nrContrato,
           int nrLoja,
           int nrQtdPrestacao,
           int dtVenda,
           float vlrVenda,
           float vlrFinanciado,
           java.lang.String dsCPF,
           java.lang.String nrDoc,
           com.bonsucesso.servipa.ws.TPrestacao[] listPrestacao) {
           this.nrContrato = nrContrato;
           this.nrLoja = nrLoja;
           this.nrQtdPrestacao = nrQtdPrestacao;
           this.dtVenda = dtVenda;
           this.vlrVenda = vlrVenda;
           this.vlrFinanciado = vlrFinanciado;
           this.dsCPF = dsCPF;
           this.nrDoc = nrDoc;
           this.listPrestacao = listPrestacao;
    }


    /**
     * Gets the nrContrato value for this TItemRetornoVendaDia.
     * 
     * @return nrContrato
     */
    public java.lang.String getNrContrato() {
        return nrContrato;
    }


    /**
     * Sets the nrContrato value for this TItemRetornoVendaDia.
     * 
     * @param nrContrato
     */
    public void setNrContrato(java.lang.String nrContrato) {
        this.nrContrato = nrContrato;
    }


    /**
     * Gets the nrLoja value for this TItemRetornoVendaDia.
     * 
     * @return nrLoja
     */
    public int getNrLoja() {
        return nrLoja;
    }


    /**
     * Sets the nrLoja value for this TItemRetornoVendaDia.
     * 
     * @param nrLoja
     */
    public void setNrLoja(int nrLoja) {
        this.nrLoja = nrLoja;
    }


    /**
     * Gets the nrQtdPrestacao value for this TItemRetornoVendaDia.
     * 
     * @return nrQtdPrestacao
     */
    public int getNrQtdPrestacao() {
        return nrQtdPrestacao;
    }


    /**
     * Sets the nrQtdPrestacao value for this TItemRetornoVendaDia.
     * 
     * @param nrQtdPrestacao
     */
    public void setNrQtdPrestacao(int nrQtdPrestacao) {
        this.nrQtdPrestacao = nrQtdPrestacao;
    }


    /**
     * Gets the dtVenda value for this TItemRetornoVendaDia.
     * 
     * @return dtVenda
     */
    public int getDtVenda() {
        return dtVenda;
    }


    /**
     * Sets the dtVenda value for this TItemRetornoVendaDia.
     * 
     * @param dtVenda
     */
    public void setDtVenda(int dtVenda) {
        this.dtVenda = dtVenda;
    }


    /**
     * Gets the vlrVenda value for this TItemRetornoVendaDia.
     * 
     * @return vlrVenda
     */
    public float getVlrVenda() {
        return vlrVenda;
    }


    /**
     * Sets the vlrVenda value for this TItemRetornoVendaDia.
     * 
     * @param vlrVenda
     */
    public void setVlrVenda(float vlrVenda) {
        this.vlrVenda = vlrVenda;
    }


    /**
     * Gets the vlrFinanciado value for this TItemRetornoVendaDia.
     * 
     * @return vlrFinanciado
     */
    public float getVlrFinanciado() {
        return vlrFinanciado;
    }


    /**
     * Sets the vlrFinanciado value for this TItemRetornoVendaDia.
     * 
     * @param vlrFinanciado
     */
    public void setVlrFinanciado(float vlrFinanciado) {
        this.vlrFinanciado = vlrFinanciado;
    }


    /**
     * Gets the dsCPF value for this TItemRetornoVendaDia.
     * 
     * @return dsCPF
     */
    public java.lang.String getDsCPF() {
        return dsCPF;
    }


    /**
     * Sets the dsCPF value for this TItemRetornoVendaDia.
     * 
     * @param dsCPF
     */
    public void setDsCPF(java.lang.String dsCPF) {
        this.dsCPF = dsCPF;
    }


    /**
     * Gets the nrDoc value for this TItemRetornoVendaDia.
     * 
     * @return nrDoc
     */
    public java.lang.String getNrDoc() {
        return nrDoc;
    }


    /**
     * Sets the nrDoc value for this TItemRetornoVendaDia.
     * 
     * @param nrDoc
     */
    public void setNrDoc(java.lang.String nrDoc) {
        this.nrDoc = nrDoc;
    }


    /**
     * Gets the listPrestacao value for this TItemRetornoVendaDia.
     * 
     * @return listPrestacao
     */
    public com.bonsucesso.servipa.ws.TPrestacao[] getListPrestacao() {
        return listPrestacao;
    }


    /**
     * Sets the listPrestacao value for this TItemRetornoVendaDia.
     * 
     * @param listPrestacao
     */
    public void setListPrestacao(com.bonsucesso.servipa.ws.TPrestacao[] listPrestacao) {
        this.listPrestacao = listPrestacao;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemRetornoVendaDia)) return false;
        TItemRetornoVendaDia other = (TItemRetornoVendaDia) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.nrContrato==null && other.getNrContrato()==null) || 
             (this.nrContrato!=null &&
              this.nrContrato.equals(other.getNrContrato()))) &&
            this.nrLoja == other.getNrLoja() &&
            this.nrQtdPrestacao == other.getNrQtdPrestacao() &&
            this.dtVenda == other.getDtVenda() &&
            this.vlrVenda == other.getVlrVenda() &&
            this.vlrFinanciado == other.getVlrFinanciado() &&
            ((this.dsCPF==null && other.getDsCPF()==null) || 
             (this.dsCPF!=null &&
              this.dsCPF.equals(other.getDsCPF()))) &&
            ((this.nrDoc==null && other.getNrDoc()==null) || 
             (this.nrDoc!=null &&
              this.nrDoc.equals(other.getNrDoc()))) &&
            ((this.listPrestacao==null && other.getListPrestacao()==null) || 
             (this.listPrestacao!=null &&
              java.util.Arrays.equals(this.listPrestacao, other.getListPrestacao())));
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
        if (getNrContrato() != null) {
            _hashCode += getNrContrato().hashCode();
        }
        _hashCode += getNrLoja();
        _hashCode += getNrQtdPrestacao();
        _hashCode += getDtVenda();
        _hashCode += new Float(getVlrVenda()).hashCode();
        _hashCode += new Float(getVlrFinanciado()).hashCode();
        if (getDsCPF() != null) {
            _hashCode += getDsCPF().hashCode();
        }
        if (getNrDoc() != null) {
            _hashCode += getNrDoc().hashCode();
        }
        if (getListPrestacao() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListPrestacao());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListPrestacao(), i);
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
        new org.apache.axis.description.TypeDesc(TItemRetornoVendaDia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRetornoVendaDia"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrContrato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrContrato"));
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
        elemField.setFieldName("nrQtdPrestacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NrQtdPrestacao"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtVenda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DtVenda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlrVenda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VlrVenda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlrFinanciado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VlrFinanciado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsCPF");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dsCPF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrDoc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrDoc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listPrestacao");
        elemField.setXmlName(new javax.xml.namespace.QName("", "listPrestacao"));
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
