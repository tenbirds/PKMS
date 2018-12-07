/**
 * SMSSender.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package skt.soa.notification.webservice;

public interface SMSSender extends java.rmi.Remote {
    public void send(java.lang.String CONSUMER_ID, java.lang.String RPLY_PHON_NUM, java.lang.String TITLE, java.lang.String PHONE, java.lang.String URL, java.lang.String START_DT_HMS, java.lang.String END_DT_HMS, javax.xml.rpc.holders.StringHolder _return, javax.xml.rpc.holders.StringHolder uuid) throws java.rmi.RemoteException;
    public java.lang.String cancel(java.lang.String UUID) throws java.rmi.RemoteException;
}
