package com.pkms.common.scheduler.job.sms.GIP;

import java.io.PrintStream;

public class GIPBody {

	int vIdPrd;
	byte rgtDlvFlg;
	byte callback[];
	byte msgLen;
	byte msg[];
	byte reserved;
	int result;
	
	public GIPBody() {
		vIdPrd = 0;
		rgtDlvFlg = 2;
		callback = new byte[21];
		msgLen = 0;
		msg = new byte[128];
		reserved = 0;
		result = 0;
	}
	
	public byte[] getBytes(int bodyLen) {
		int index = 0;
		byte body[] = new byte[bodyLen];
		body[index++] = (byte)(vIdPrd >>> 24);
		body[index++] = (byte)(vIdPrd >>> 16);
		body[index++] = (byte)(vIdPrd >>> 8);
		body[index++] = (byte)vIdPrd;
		body[index++] = rgtDlvFlg;
		for(int i = 0; i < 21; i++)
		if(i < callback.length)
		body[index++] = callback[i];
		else
		body[index++] = 0;
		
		body[index++] = msgLen;
		for(int i = 0; i < 128; i++)
		if(i < msg.length)
		body[index++] = msg[i];
		else
		body[index++] = 0;
		
		body[index++] = reserved;
		if(index != bodyLen)
		System.out.println("error length");
		return body;
	}

	public void decodeRspSimple(byte data[]) {
		int index = 0;
		vIdPrd = ((data[index++] & 255) << 24) + ((data[index++] & 255) << 16) + ((data[index++] & 255) << 8) + (data[index++] & 255);
		rgtDlvFlg = data[index++];
		for(int i = 0; i < 21; i++)
		callback[i] = data[index++];
		
		msgLen = data[index++];
		for(int i = 0; i < 128; i++)
		msg[i] = data[index++];
		
		reserved = data[index++];
	}
	
	public void decodeResLink(byte data[]) {
		int index = 0;
		result = ((data[index++] & 255) << 24) + ((data[index++] & 255) << 16) + ((data[index++] & 255) << 8) + (data[index++] & 255);
	}

	public void printValues() {
		System.out.println("--------------- BODY --------------");
		System.out.println("vIdPrd = " + vIdPrd);
		System.out.println("rgtDlvFlg= " + rgtDlvFlg);
		System.out.println("callback = " + new String(callback));
		System.out.println("msgLen = " + msgLen);
		System.out.println("msg= " + new String(msg));
		System.out.println("Reserved = " + reserved);
		System.out.println("result = " + result);
		System.out.println("-----------------------------------");
		System.out.println("");
	}

}
