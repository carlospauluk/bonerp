/**
 * TItemRetornoAcordo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bonsucesso.servipa.ws;

public class TItemRetornoAcordo  implements java.io.Serializable {
    private java.lang.String linhadigitavel;

    private java.lang.String codigobarras;

    private java.lang.String nossonumero;

    private int dtvencto;

    private float vlrvencto;

    private int acocodigo;

    public TItemRetornoAcordo() {
    }

    public TItemRetornoAcordo(
           java.lang.String linhadigitavel,
           java.lang.String codigobarras,
           java.lang.String nossonumero,
           int dtvencto,
           float vlrvencto,
           int acocodigo) {
           this.linhadigitavel = linhadigitavel;
           this.codigobarras = codigobarras;
           this.nossonumero = nossonumero;
           this.dtvencto = dtvencto;
           this.vlrvencto = vlrvencto;
           this.acocodigo = acocodigo;
    }


    /**
     * Gets the linhadigitavel value for this TItemRetornoAcordo.
     * 
     * @return linhadigitavel
     */
    public java.lang.String getLinhadigitavel() {
        return linhadigitavel;
    }


    /**
     * Sets the linhadigitavel value for this TItemRetornoAcordo.
     * 
     * @param linhadigitavel
     */
    public void setLinhadigitavel(java.lang.String linhadigitavel) {
        this.linhadigitavel = linhadigitavel;
    }


    /**
     * Gets the codigobarras value for this TItemRetornoAcordo.
     * 
     * @return codigobarras
     */
    public java.lang.String getCodigobarras() {
        return codigobarras;
    }


    /**
     * Sets the codigobarras value for this TItemRetornoAcordo.
     * 
     * @param codigobarras
     */
    public void setCodigobarras(java.lang.String codigobarras) {
        this.codigobarras = codigobarras;
    }


    /**
     * Gets the nossonumero value for this TItemRetornoAcordo.
     * 
     * @return nossonumero
     */
    public java.lang.String getNossonumero() {
        return nossonumero;
    }


    /**
     * Sets the nossonumero value for this TItemRetornoAcordo.
     * 
     * @param nossonumero
     */
    public void setNossonumero(java.lang.String nossonumero) {
        this.nossonumero = nossonumero;
    }


    /**
     * Gets the dtvencto value for this TItemRetornoAcordo.
     * 
     * @return dtvencto
     */
    public int getDtvencto() {
        return dtvencto;
    }


    /**
     * Sets the dtvencto value for this TItemRetornoAcordo.
     * 
     * @param dtvencto
     */
    public void setDtvencto(int dtvencto) {
        this.dtvencto = dtvencto;
    }


    /**
     * Gets the vlrvencto value for this TItemRetornoAcordo.
     * 
     * @return vlrvencto
     */
    public float getVlrvencto() {
        return vlrvencto;
    }


    /**
     * Sets the vlrvencto value for this TItemRetornoAcordo.
     * 
     * @param vlrvencto
     */
    public void setVlrvencto(float vlrvencto) {
        this.vlrvencto = vlrvencto;
    }


    /**
     * Gets the acocodigo value for this TItemRetornoAcordo.
     * 
     * @return acocodigo
     */
    public int getAcocodigo() {
        return acocodigo;
    }


    /**
     * Sets the acocodigo value for this TItemRetornoAcordo.
     * 
     * @param acocodigo
     */
    public void setAcocodigo(int acocodigo) {
        this.acocodigo = acocodigo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TItemRetornoAcordo)) return false;
        TItemRetornoAcordo other = (TItemRetornoAcordo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.linhadigitavel==null && other.getLinhadigitavel()==null) || 
             (this.linhadigitavel!=null &&
              this.linhadigitavel.equals(other.getLinhadigitavel()))) &&
            ((this.codigobarras==null && other.getCodigobarras()==null) || 
             (this.codigobarras!=null &&
              this.codigobarras.equals(other.getCodigobarras()))) &&
            ((this.nossonumero==null && other.getNossonumero()==null) || 
             (this.nossonumero!=null &&
              this.nossonumero.equals(other.getNossonumero()))) &&
            this.dtvencto == other.getDtvencto() &&
            this.vlrvencto == other.getVlrvencto() &&
            this.acocodigo == other.getAcocodigo();
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
        if (getLinhadigitavel() != null) {
            _hashCode += getLinhadigitavel().hashCode();
        }
        if (getCodigobarras() != null) {
            _hashCode += getCodigobarras().hashCode();
        }
        if (getNossonumero() != null) {
            _hashCode += getNossonumero().hashCode();
        }
        _hashCode += getDtvencto();
        _hashCode += new Float(getVlrvencto()).hashCode();
        _hashCode += getAcocodigo();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TItemRetornoAcordo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:wsrcs", "TItemRetornoAcordo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("linhadigitavel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "linhadigitavel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigobarras");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigobarras"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nossonumero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nossonumero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dtvencto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dtvencto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlrvencto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vlrvencto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acocodigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acocodigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
