/**
 * TRetornoLimiteClienteValores.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TRetornoLimiteClienteValores  implements java.io.Serializable {
    private int nrCodCli;

    private java.lang.String dsNome;

    private java.lang.String dsCPF;

    private java.lang.String dsRG;

    private int nrCodCliAntigo;

    private float vlLimiteVendaSemEntrada;

    private float vlLimiteVendaComEntrada;

    private float vlLimiteParcela;

    public TRetornoLimiteClienteValores() {
    }

    public TRetornoLimiteClienteValores(
           int nrCodCli,
           java.lang.String dsNome,
           java.lang.String dsCPF,
           java.lang.String dsRG,
           int nrCodCliAntigo,
           float vlLimiteVendaSemEntrada,
           float vlLimiteVendaComEntrada,
           float vlLimiteParcela) {
           this.nrCodCli = nrCodCli;
           this.dsNome = dsNome;
           this.dsCPF = dsCPF;
           this.dsRG = dsRG;
           this.nrCodCliAntigo = nrCodCliAntigo;
           this.vlLimiteVendaSemEntrada = vlLimiteVendaSemEntrada;
           this.vlLimiteVendaComEntrada = vlLimiteVendaComEntrada;
           this.vlLimiteParcela = vlLimiteParcela;
    }


    /**
     * Gets the nrCodCli value for this TRetornoLimiteClienteValores.
     * 
     * @return nrCodCli
     */
    public int getNrCodCli() {
        return nrCodCli;
    }


    /**
     * Sets the nrCodCli value for this TRetornoLimiteClienteValores.
     * 
     * @param nrCodCli
     */
    public void setNrCodCli(int nrCodCli) {
        this.nrCodCli = nrCodCli;
    }


    /**
     * Gets the dsNome value for this TRetornoLimiteClienteValores.
     * 
     * @return dsNome
     */
    public java.lang.String getDsNome() {
        return dsNome;
    }


    /**
     * Sets the dsNome value for this TRetornoLimiteClienteValores.
     * 
     * @param dsNome
     */
    public void setDsNome(java.lang.String dsNome) {
        this.dsNome = dsNome;
    }


    /**
     * Gets the dsCPF value for this TRetornoLimiteClienteValores.
     * 
     * @return dsCPF
     */
    public java.lang.String getDsCPF() {
        return dsCPF;
    }


    /**
     * Sets the dsCPF value for this TRetornoLimiteClienteValores.
     * 
     * @param dsCPF
     */
    public void setDsCPF(java.lang.String dsCPF) {
        this.dsCPF = dsCPF;
    }


    /**
     * Gets the dsRG value for this TRetornoLimiteClienteValores.
     * 
     * @return dsRG
     */
    public java.lang.String getDsRG() {
        return dsRG;
    }


    /**
     * Sets the dsRG value for this TRetornoLimiteClienteValores.
     * 
     * @param dsRG
     */
    public void setDsRG(java.lang.String dsRG) {
        this.dsRG = dsRG;
    }


    /**
     * Gets the nrCodCliAntigo value for this TRetornoLimiteClienteValores.
     * 
     * @return nrCodCliAntigo
     */
    public int getNrCodCliAntigo() {
        return nrCodCliAntigo;
    }


    /**
     * Sets the nrCodCliAntigo value for this TRetornoLimiteClienteValores.
     * 
     * @param nrCodCliAntigo
     */
    public void setNrCodCliAntigo(int nrCodCliAntigo) {
        this.nrCodCliAntigo = nrCodCliAntigo;
    }


    /**
     * Gets the vlLimiteVendaSemEntrada value for this TRetornoLimiteClienteValores.
     * 
     * @return vlLimiteVendaSemEntrada
     */
    public float getVlLimiteVendaSemEntrada() {
        return vlLimiteVendaSemEntrada;
    }


    /**
     * Sets the vlLimiteVendaSemEntrada value for this TRetornoLimiteClienteValores.
     * 
     * @param vlLimiteVendaSemEntrada
     */
    public void setVlLimiteVendaSemEntrada(float vlLimiteVendaSemEntrada) {
        this.vlLimiteVendaSemEntrada = vlLimiteVendaSemEntrada;
    }


    /**
     * Gets the vlLimiteVendaComEntrada value for this TRetornoLimiteClienteValores.
     * 
     * @return vlLimiteVendaComEntrada
     */
    public float getVlLimiteVendaComEntrada() {
        return vlLimiteVendaComEntrada;
    }


    /**
     * Sets the vlLimiteVendaComEntrada value for this TRetornoLimiteClienteValores.
     * 
     * @param vlLimiteVendaComEntrada
     */
    public void setVlLimiteVendaComEntrada(float vlLimiteVendaComEntrada) {
        this.vlLimiteVendaComEntrada = vlLimiteVendaComEntrada;
    }


    /**
     * Gets the vlLimiteParcela value for this TRetornoLimiteClienteValores.
     * 
     * @return vlLimiteParcela
     */
    public float getVlLimiteParcela() {
        return vlLimiteParcela;
    }


    /**
     * Sets the vlLimiteParcela value for this TRetornoLimiteClienteValores.
     * 
     * @param vlLimiteParcela
     */
    public void setVlLimiteParcela(float vlLimiteParcela) {
        this.vlLimiteParcela = vlLimiteParcela;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TRetornoLimiteClienteValores)) return false;
        TRetornoLimiteClienteValores other = (TRetornoLimiteClienteValores) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.nrCodCli == other.getNrCodCli() &&
            ((this.dsNome==null && other.getDsNome()==null) || 
             (this.dsNome!=null &&
              this.dsNome.equals(other.getDsNome()))) &&
            ((this.dsCPF==null && other.getDsCPF()==null) || 
             (this.dsCPF!=null &&
              this.dsCPF.equals(other.getDsCPF()))) &&
            ((this.dsRG==null && other.getDsRG()==null) || 
             (this.dsRG!=null &&
              this.dsRG.equals(other.getDsRG()))) &&
            this.nrCodCliAntigo == other.getNrCodCliAntigo() &&
            this.vlLimiteVendaSemEntrada == other.getVlLimiteVendaSemEntrada() &&
            this.vlLimiteVendaComEntrada == other.getVlLimiteVendaComEntrada() &&
            this.vlLimiteParcela == other.getVlLimiteParcela();
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
        _hashCode += getNrCodCli();
        if (getDsNome() != null) {
            _hashCode += getDsNome().hashCode();
        }
        if (getDsCPF() != null) {
            _hashCode += getDsCPF().hashCode();
        }
        if (getDsRG() != null) {
            _hashCode += getDsRG().hashCode();
        }
        _hashCode += getNrCodCliAntigo();
        _hashCode += new Float(getVlLimiteVendaSemEntrada()).hashCode();
        _hashCode += new Float(getVlLimiteVendaComEntrada()).hashCode();
        _hashCode += new Float(getVlLimiteParcela()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TRetornoLimiteClienteValores.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TRetornoLimiteClienteValores"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nrCodCli");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrCodCli"));
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
        elemField.setFieldName("nrCodCliAntigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nrCodCliAntigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlLimiteVendaSemEntrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlLimiteVendaSemEntrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlLimiteVendaComEntrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlLimiteVendaComEntrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlLimiteParcela");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlLimiteParcela"));
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
