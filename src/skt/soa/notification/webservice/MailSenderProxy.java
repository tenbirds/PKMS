package skt.soa.notification.webservice;

public class MailSenderProxy implements skt.soa.notification.webservice.MailSender {
  private String _endpoint = null;
  private skt.soa.notification.webservice.MailSender mailSender = null;
  
  public MailSenderProxy() {
    _initMailSenderProxy();
  }
  
  public MailSenderProxy(String endpoint) {
    _endpoint = endpoint;
    _initMailSenderProxy();
  }
  
  private void _initMailSenderProxy() {
    try {
      mailSender = (new skt.soa.notification.webservice.MailSenderServiceLocator()).getMailSenderSoapPort();
      if (mailSender != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mailSender)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mailSender)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mailSender != null)
      ((javax.xml.rpc.Stub)mailSender)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public skt.soa.notification.webservice.MailSender getMailSender() {
    if (mailSender == null)
      _initMailSenderProxy();
    return mailSender;
  }
  
  public java.lang.String send(java.lang.String senderEmail, java.lang.String receiverEmail, java.lang.String subject, java.lang.String content) throws java.rmi.RemoteException{
    if (mailSender == null)
      _initMailSenderProxy();
    return mailSender.send(senderEmail, receiverEmail, subject, content);
  }
  
  
}