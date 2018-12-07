package com.pkms.common.scheduler.job.sms.GIP;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GIPSender extends Thread {

	public static short MSG_CODE_SM_REQ = 11;
	public static short MSG_CODE_SM_RES = 12;
	public static short LINK = 3;
	public static short MDN = 10;
	public static short PORTED = 13;
	public static short TRANS_RESULT = 9;
	public static char MT = '\002';
	public static char MO = '\0';
	public static int sendSeqNo = 0;
	public static int receivedSeqNo = -1;
	public static int linkSeqNo = 0;
	private Socket socket;
	private InputStream input;
	private OutputStream output;
	private String vsmsAddress;
	private int vsmsPort;
	private String localAddress;
	private long lastTime;
	private boolean isReconnecting;

    public String HOME = System.getProperty("HOME", "..");
    public String VSMS_ADDRESS = "70.12.203.46";
    public int VSMS_PORT = 7020;
    public String CID = "1571618000";
    public String LOCAL_ADDRESS = "150.24.192.194";
    public int APPLY_SECOND = 10;
    public int APPLY_TRAFFIC = 20;
    public int VSMS_RECONNECT_INTERVAL = 5500;
    public int VSMS_HEALTHCHECK_INTERVAL = 30000;
    public int VSMS_SESSION_TIMEOUT = 3000;
    public int CLIENT_SESSION_TIMEOUT = 60000;
    public int LOG_KEEP_DAY = 30;

	public GIPSender() throws Exception {
		socket = null;
		input = null;
		output = null;
		this.vsmsAddress = "";
		this.vsmsPort = 0;
		this.localAddress = "";
		lastTime = 0L;
		isReconnecting = false;
		this.vsmsAddress = VSMS_ADDRESS;
		this.vsmsPort = VSMS_PORT;
		this.localAddress = LOCAL_ADDRESS;
		reconnection();
	}
	
	public GIPSender(String vsmsAddress, int vsmsPort, String localAddress) throws Exception {
		socket = null;
		input = null;
		output = null;
		this.vsmsAddress = "";
		this.vsmsPort = 0;
		this.localAddress = "";
		lastTime = 0L;
		isReconnecting = false;
		this.vsmsAddress = vsmsAddress;
		this.vsmsPort = vsmsPort;
		this.localAddress = localAddress;
		reconnection();
	}

	public void reconnection() {
		do {
			isReconnecting = true;
			
			try {
				Thread.sleep(VSMS_RECONNECT_INTERVAL);
				InetSocketAddress socketAddress = new InetSocketAddress(localAddress, 0);
				java.net.InetAddress localInetAddress = socketAddress.getAddress();
				socket = new Socket(vsmsAddress, vsmsPort, localInetAddress, 0);
				socket.setSoTimeout(VSMS_SESSION_TIMEOUT);
				socket.setSoLinger(true, 0);
				input = socket.getInputStream();
				output = socket.getOutputStream();
				lastTime = System.currentTimeMillis();
				System.out.println("connection established with VSMS");
				break;
			} catch(Exception e) {
				e.printStackTrace();
			}
		} while(true);
		
		isReconnecting = false;
	}
	
	public void run() {
		do {
			try {
				do {
					do {
						try {
							Thread.sleep(5000L);
						} catch(Exception exception) {
							;
						}
					} while(System.currentTimeMillis() - lastTime < (long)VSMS_HEALTHCHECK_INTERVAL || isReconnecting);
					
					linkTest();
					lastTime = System.currentTimeMillis();
				} while(true);
			} catch(Exception e) {
				e.printStackTrace();
			}
			reconnection();
		} while(true);
	}

	public int sendSMS(String srcCId, int srcCallNum, String destCId, int destCallNum, String message, String callBackUrl) throws Exception {
		int resultCode;
		boolean isPorted = isPortedOut(destCId + destCallNum);
		if(isPorted) {
			byte preSend[] = getReqSimple(srcCId, srcCallNum, destCId, destCallNum, message, callBackUrl, MDN);
			send(preSend);
		}
	
		byte packet[] = (byte[])null;
		if(isPorted) {
			packet = getReqSimple(srcCId, srcCallNum, destCId, destCallNum, message, callBackUrl, PORTED);
		} else {
			packet = getReqSimple(srcCId, srcCallNum, destCId, destCallNum, message, callBackUrl, MDN);
		}
	
		try {
			resultCode = send(packet);
			System.out.println("resultCode:" + resultCode);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			close();
			reconnection();
		}
		return resultCode;
	}
	
	private int send(byte data[]) throws Exception {
		int resultCode = -1;
		
		try {
			write(data);
		} catch(Exception e) {
			throw e;
		}
		
		try {
			byte rspHeader[] = new byte[120];
			read(rspHeader);
			GIPHeader rspSimpleHeader = new GIPHeader();
			rspSimpleHeader.decodeHeader(rspHeader);
			byte rspBody[] = new byte[13];
			read(rspBody);
			resultCode = ((rspBody[0] & 255) << 24) + ((rspBody[1] & 255) << 16) + ((rspBody[2] & 255) << 8) + (rspBody[3] & 255);
			byte msgIdByte[] = new byte[9];
			for(int i = 0; i < 9; i++)
			msgIdByte[i] = rspBody[i + 4];
			
			String msgId = new String(msgIdByte);
			
			try {
				byte resultHeader[] = new byte[120];
				read(resultHeader);
				GIPHeader gipHeader = new GIPHeader();
				gipHeader.decodeHeader(resultHeader);
				receivedSeqNo = gipHeader.msgSeqNo;
				byte resultBody[] = new byte[11];
				read(resultBody);
				char msgStatus = (char)resultBody[0];
				char rsv = (char)resultBody[1];
				byte receivedMsgIdByte[] = new byte[9];
				for(int i = 0; i < 9; i++)
				receivedMsgIdByte[i] = resultBody[i + 2];
				
				String receivedMsgId = new String(receivedMsgIdByte);
				write(getRspTransResult(resultHeader));
			} catch(SocketTimeoutException e) {
				e.printStackTrace();
				throw e;
			}
		} catch(Exception e) {
			resultCode = -1;
			throw e;
		}
		return resultCode;
	}

	private synchronized void write(byte data[]) throws Exception {
		output.write(data);
		lastTime = System.currentTimeMillis();
	}

	private synchronized void read(byte dest[]) throws Exception {
		input.read(dest);
	}

	public byte[] getReqSimple(String srcCId, int srcCallNum, String destCId, int destCallNum, String message, String callBackUrl, short msgsubcode) throws Exception {
		GIPHeader header = new GIPHeader();
		header.srcCId = srcCId.getBytes();
		header.srcCallNo = srcCallNum;
		header.destCId = destCId.getBytes();
		header.destCallNo = destCallNum;
		header.msgCode = MSG_CODE_SM_REQ;
		header.msgSubCode = msgsubcode;
		header.msgSeqNo = getNextSeqNo();
		header.bodyDataLen = 156;
		header.MTDlvTime = getTime().getBytes();
		header.teleServiceID = Integer.parseInt(callBackUrl);
		GIPBody body = new GIPBody();
		body.vIdPrd = 86400;
		body.rgtDlvFlg = (byte)MT;
		body.callback = callBackUrl.getBytes();
		body.msgLen = (byte)message.getBytes().length;
		body.msg = message.getBytes();
		byte headerBinary[] = header.getBytes();
		byte bodyBinary[] = body.getBytes(156);
		byte reqBinary[] = new byte[276];
		System.arraycopy(headerBinary, 0, reqBinary, 0, 120);
		System.arraycopy(bodyBinary, 0, reqBinary, 120, 156);
		System.out.println("---------- REQ/SIMPLE ---------");
		header.printValues();
		body.printValues();
		return reqBinary;
	}

	public byte[] getRspTransResult(byte b[]) {
		GIPHeader transHeader = new GIPHeader();
		transHeader.decodeHeader(b);
		transHeader.bodyDataLen = 4;
		transHeader.msgCode = MSG_CODE_SM_RES;
		transHeader.msgSubCode = TRANS_RESULT;
		byte rspTransBinary[] = new byte[124];
		byte headerBinary[] = transHeader.getBytes();
		System.arraycopy(headerBinary, 0, rspTransBinary, 0, headerBinary.length);
		rspTransBinary[120] = 0;
		rspTransBinary[121] = 0;
		rspTransBinary[122] = 0;
		rspTransBinary[123] = 0;
		System.out.println("--------------- RES/TRANS_RESULT --------------");
		transHeader.printValues();
		return rspTransBinary;
	}

	public byte[] getReqLink() {
		GIPHeader header = new GIPHeader();
		header.srcCId = CID.getBytes();
		header.destCId = "011".getBytes();
		header.destCallNo = 0;
		header.msgCode = MSG_CODE_SM_REQ;
		header.msgSubCode = LINK;
		header.msgSeqNo = getNextLinkSeqNo();
		header.bodyDataLen = 0;
		header.MTDlvTime = getTime().getBytes();
		header.printValues();
		return header.getBytes();
	}

	public void linkTest() throws Exception {
		try {
			System.out.println("---------- LINK Start (" + getDateTime() + ") ---------");
			write(getReqLink());
			byte resLink[] = new byte[124];
			read(resLink);
			byte headerData[] = new byte[120];
			System.arraycopy(resLink, 0, headerData, 0, 120);
			(new GIPHeader()).decodeHeader(headerData);
			byte bodyData[] = new byte[4];
			System.arraycopy(resLink, 120, bodyData, 0, 4);
			GIPBody body = new GIPBody();
			body.decodeResLink(bodyData);
		} catch(Exception e) {
			close();
			throw e;
		}
	}

	public int getReceivedSeqNo() {
		return receivedSeqNo;
	}
	
	public int getCurSeqNo() {
		return sendSeqNo;
	}
	
	public int getNextSeqNo() {
		if(sendSeqNo > 10000)
		sendSeqNo = 0;
		sendSeqNo++;
		return sendSeqNo;
	}
	
	public int getNextLinkSeqNo() {
		if(linkSeqNo > 10000)
		linkSeqNo = 0;
		linkSeqNo++;
		return linkSeqNo;
	}
	
	public void close() {
		try {
			if(socket != null)
			socket.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar cal = Calendar.getInstance();
		return formatter.format(cal.getTime());
	}
	
	public String getDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		return formatter.format(cal.getTime());
	}
	
	private static boolean isPortedOut(String phone_num) {
		boolean result = false;
		
		try {
			if(phone_num.startsWith("016") || phone_num.startsWith("018") || phone_num.startsWith("019"))
			result = true;
			else
			if(phone_num.startsWith("010"))
			{
			int num1 = Integer.parseInt(phone_num.substring(3, 4));
			int num2 = Integer.parseInt(phone_num.substring(4, 5));
			if(num1 == 2)
			{
			if(num2 >= 1 && num2 <= 9)
			result = true;
			} else
			if(num1 == 3)
			{
			if(num2 == 0 || num2 >= 2 && num2 <= 4 || num2 == 9)
			result = true;
			} else
			if(num1 == 4)
			{
			if(num2 >= 2 && num2 <= 4)
			result = true;
			} else
			if(num1 == 5)
			{
			if(num2 >= 5 && num2 <= 8)
			result = true;
			} else
			if(num1 == 6)
			{
			if(num2 >= 5 && num2 <= 8)
			result = true;
			} else
			if(num1 == 7)
			{
			if(num2 >= 2 && num2 <= 7 || num2 == 9)
			result = true;
			} else
			if(num1 == 8)
			{
			if(num2 >= 0 && num2 <= 4)
			result = true;
			} else
			if(num1 == 9)
			{
			if(num2 >= 5 && num2 <= 9)
			result = true;
			} else
			{
			result = false;
			}
			} else
			if(phone_num.startsWith("011") || phone_num.startsWith("017"))
			result = false;
		} catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}


}
