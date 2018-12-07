package skt.soa.notification.webservice;

public class SMSSenderProxy implements skt.soa.notification.webservice.SMSSender {
  private String _endpoint = null;
  private skt.soa.notification.webservice.SMSSender sMSSender = null;
  
  public SMSSenderProxy() {
    _initSMSSenderProxy();
  }
  
  public SMSSenderProxy(String endpoint) {
    _endpoint = endpoint;
    _initSMSSenderProxy();
  }
  
  private void _initSMSSenderProxy() {
    try {
      sMSSender = (new skt.soa.notification.webservice.SMSSenderServiceLocator()).getSMSSenderSoapPort();
      if (sMSSender != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sMSSender)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sMSSender)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sMSSender != null)
      ((javax.xml.rpc.Stub)sMSSender)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public skt.soa.notification.webservice.SMSSender getSMSSender() {
    if (sMSSender == null)
      _initSMSSenderProxy();
    return sMSSender;
  }
  
  public void send(java.lang.String CONSUMER_ID, java.lang.String RPLY_PHON_NUM, java.lang.String TITLE, java.lang.String PHONE, java.lang.String URL, java.lang.String START_DT_HMS, java.lang.String END_DT_HMS, javax.xml.rpc.holders.StringHolder _return, javax.xml.rpc.holders.StringHolder uuid) throws java.rmi.RemoteException{
    if (sMSSender == null)
      _initSMSSenderProxy();
    sMSSender.send(CONSUMER_ID, RPLY_PHON_NUM, TITLE, PHONE, URL, START_DT_HMS, END_DT_HMS, _return, uuid);
  }
  
  public java.lang.String cancel(java.lang.String UUID) throws java.rmi.RemoteException{
    if (sMSSender == null)
      _initSMSSenderProxy();
    return sMSSender.cancel(UUID);
  }
  
  
}