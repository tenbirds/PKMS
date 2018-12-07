import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;


import skt.soa.notification.webservice.MailSender;
import skt.soa.notification.webservice.MailSenderServiceLocator;


public class _MailTest {

	public static void main(String[] args) {

		try{
			
			MailSenderServiceLocator locator = new MailSenderServiceLocator();
//			locator.setEndpointAddress("MailSenderSoapPort", "http://esb.sktelecom.com:80/MailService");
			locator.setEndpointAddress("MailSenderSoapPort", "http://220.103.249.69:8080/MailService");
			
		
			MailSender service = locator.getMailSenderSoapPort();
			Stub stub = (Stub)service;
//			stub._setProperty(Stub.USERNAME_PROPERTY, "new_pkms");
//			stub._setProperty(Stub.PASSWORD_PROPERTY, "new_pkmspwd1");
			stub._setProperty(Stub.USERNAME_PROPERTY, "soatest");
			stub._setProperty(Stub.PASSWORD_PROPERTY, "soatest1");

			String senderEmail		= "hayunlee@in-soft.co.kr";			
			String receiverEmail 	= "hayunlee@in-soft.co.kr";

			
			String subject			= "EmailTest테스트";
			String content	 		= 	"<span style='color: red; font-weight: bold; font-size: 15px;'>SK&nbsp;</span>";
					content +=   "<span style='color: orange; font-weight: bold; font-size: 15px;'>PKMS</span>";
					content += "<span style='font-size: 10px; color: orange;'>2.0</span><br>";
					content += "<table cellpadding='0' cellspacing='0' border='0'><tr>";
					content += "<td style='width:80px;'><hr style='width:80px;height:10px;' color='black'></td>";
					content += "<td style='width:115px;'><hr style='width:115px;height:10px;' color='red' ></td>";
					content += "<td style='width:250px;'><hr style='width:250px;height:3px;' color='gray' ></td>";
					content += "</tr></table>";
					content += "<table style='width: 450px; background: #D9D9D9;'><tr><td>1</td></tr><tr><td>1</td></tr><tr><td>1</td></tr></table>";
					
						
			String _return = "";
			
			_return = service.send(senderEmail, receiverEmail, subject, content);
			System.out.println("_return:"+_return);
			
			
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	
	
	}
}
