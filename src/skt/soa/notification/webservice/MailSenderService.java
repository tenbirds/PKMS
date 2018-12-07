/**
 * MailSenderService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package skt.soa.notification.webservice;

public interface MailSenderService extends javax.xml.rpc.Service {
    public java.lang.String getMailSenderSoapPortAddress();

    public skt.soa.notification.webservice.MailSender getMailSenderSoapPort() throws javax.xml.rpc.ServiceException;

    public skt.soa.notification.webservice.MailSender getMailSenderSoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
