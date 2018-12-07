/**
 * MailSender.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package skt.soa.notification.webservice;

public interface MailSender extends java.rmi.Remote {
    public java.lang.String send(java.lang.String senderEmail, java.lang.String receiverEmail, java.lang.String subject, java.lang.String content) throws java.rmi.RemoteException;
}
