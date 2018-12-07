/**
 * Header.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Common;

public class Header  implements java.io.Serializable {
    private java.lang.String MESG_ID;

    private java.lang.String CP_ID;

    public Header() {
    }

    public Header(
           java.lang.String MESG_ID,
           java.lang.String CP_ID) {
           this.MESG_ID = MESG_ID;
           this.CP_ID = CP_ID;
    }


    /**
     * Gets the MESG_ID value for this Header.
     * 
     * @return MESG_ID
     */
    public java.lang.String getMESG_ID() {
        return MESG_ID;
    }


    /**
     * Sets the MESG_ID value for this Header.
     * 
     * @param MESG_ID
     */
    public void setMESG_ID(java.lang.String MESG_ID) {
        this.MESG_ID = MESG_ID;
    }


    /**
     * Gets the CP_ID value for this Header.
     * 
     * @return CP_ID
     */
    public java.lang.String getCP_ID() {
        return CP_ID;
    }


    /**
     * Sets the CP_ID value for this Header.
     * 
     * @param CP_ID
     */
    public void setCP_ID(java.lang.String CP_ID) {
        this.CP_ID = CP_ID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Header)) return false;
        Header other = (Header) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.MESG_ID==null && other.getMESG_ID()==null) || 
             (this.MESG_ID!=null &&
              this.MESG_ID.equals(other.getMESG_ID()))) &&
            ((this.CP_ID==null && other.getCP_ID()==null) || 
             (this.CP_ID!=null &&
              this.CP_ID.equals(other.getCP_ID())));
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
        if (getMESG_ID() != null) {
            _hashCode += getMESG_ID().hashCode();
        }
        if (getCP_ID() != null) {
            _hashCode += getCP_ID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Header.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("java:Common", "Header"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MESG_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("java:Common", "MESG_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CP_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("java:Common", "CP_ID"));
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
