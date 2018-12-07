/**
 * Error_Info.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Common;

public class Error_Info  implements java.io.Serializable {
    private java.lang.String ACTOR;

    private java.lang.String ERROR_CD;

    private java.lang.String ERROR_MESG;

    public Error_Info() {
    }

    public Error_Info(
           java.lang.String ACTOR,
           java.lang.String ERROR_CD,
           java.lang.String ERROR_MESG) {
           this.ACTOR = ACTOR;
           this.ERROR_CD = ERROR_CD;
           this.ERROR_MESG = ERROR_MESG;
    }


    /**
     * Gets the ACTOR value for this Error_Info.
     * 
     * @return ACTOR
     */
    public java.lang.String getACTOR() {
        return ACTOR;
    }


    /**
     * Sets the ACTOR value for this Error_Info.
     * 
     * @param ACTOR
     */
    public void setACTOR(java.lang.String ACTOR) {
        this.ACTOR = ACTOR;
    }


    /**
     * Gets the ERROR_CD value for this Error_Info.
     * 
     * @return ERROR_CD
     */
    public java.lang.String getERROR_CD() {
        return ERROR_CD;
    }


    /**
     * Sets the ERROR_CD value for this Error_Info.
     * 
     * @param ERROR_CD
     */
    public void setERROR_CD(java.lang.String ERROR_CD) {
        this.ERROR_CD = ERROR_CD;
    }


    /**
     * Gets the ERROR_MESG value for this Error_Info.
     * 
     * @return ERROR_MESG
     */
    public java.lang.String getERROR_MESG() {
        return ERROR_MESG;
    }


    /**
     * Sets the ERROR_MESG value for this Error_Info.
     * 
     * @param ERROR_MESG
     */
    public void setERROR_MESG(java.lang.String ERROR_MESG) {
        this.ERROR_MESG = ERROR_MESG;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Error_Info)) return false;
        Error_Info other = (Error_Info) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ACTOR==null && other.getACTOR()==null) || 
             (this.ACTOR!=null &&
              this.ACTOR.equals(other.getACTOR()))) &&
            ((this.ERROR_CD==null && other.getERROR_CD()==null) || 
             (this.ERROR_CD!=null &&
              this.ERROR_CD.equals(other.getERROR_CD()))) &&
            ((this.ERROR_MESG==null && other.getERROR_MESG()==null) || 
             (this.ERROR_MESG!=null &&
              this.ERROR_MESG.equals(other.getERROR_MESG())));
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
        if (getACTOR() != null) {
            _hashCode += getACTOR().hashCode();
        }
        if (getERROR_CD() != null) {
            _hashCode += getERROR_CD().hashCode();
        }
        if (getERROR_MESG() != null) {
            _hashCode += getERROR_MESG().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Error_Info.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("java:Common", "Error_Info"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ACTOR");
        elemField.setXmlName(new javax.xml.namespace.QName("java:Common", "ACTOR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERROR_CD");
        elemField.setXmlName(new javax.xml.namespace.QName("java:Common", "ERROR_CD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ERROR_MESG");
        elemField.setXmlName(new javax.xml.namespace.QName("java:Common", "ERROR_MESG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
