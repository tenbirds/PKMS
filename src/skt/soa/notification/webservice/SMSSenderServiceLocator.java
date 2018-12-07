/**
 * SMSSenderServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package skt.soa.notification.webservice;

public class SMSSenderServiceLocator extends org.apache.axis.client.Service implements skt.soa.notification.webservice.SMSSenderService {

    public SMSSenderServiceLocator() {
    }


    public SMSSenderServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SMSSenderServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SMSSenderSoapPort
    private java.lang.String SMSSenderSoapPort_address = "http://testesb.sktelecom.com:8080/SMSService";

    public java.lang.String getSMSSenderSoapPortAddress() {
        return SMSSenderSoapPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SMSSenderSoapPortWSDDServiceName = "SMSSenderSoapPort";

    public java.lang.String getSMSSenderSoapPortWSDDServiceName() {
        return SMSSenderSoapPortWSDDServiceName;
    }

    public void setSMSSenderSoapPortWSDDServiceName(java.lang.String name) {
        SMSSenderSoapPortWSDDServiceName = name;
    }

    public skt.soa.notification.webservice.SMSSender getSMSSenderSoapPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SMSSenderSoapPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSMSSenderSoapPort(endpoint);
    }

    public skt.soa.notification.webservice.SMSSender getSMSSenderSoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            skt.soa.notification.webservice.SMSSenderServiceSoapBindingStub _stub = new skt.soa.notification.webservice.SMSSenderServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getSMSSenderSoapPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSMSSenderSoapPortEndpointAddress(java.lang.String address) {
        SMSSenderSoapPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (skt.soa.notification.webservice.SMSSender.class.isAssignableFrom(serviceEndpointInterface)) {
                skt.soa.notification.webservice.SMSSenderServiceSoapBindingStub _stub = new skt.soa.notification.webservice.SMSSenderServiceSoapBindingStub(new java.net.URL(SMSSenderSoapPort_address), this);
                _stub.setPortName(getSMSSenderSoapPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SMSSenderSoapPort".equals(inputPortName)) {
            return getSMSSenderSoapPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://skt/soa/notification/webservice", "SMSSenderService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://skt/soa/notification/webservice", "SMSSenderSoapPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SMSSenderSoapPort".equals(portName)) {
            setSMSSenderSoapPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
