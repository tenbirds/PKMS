package com.pkms.common.scheduler.job.sms.GIP;


public class GIPHeader {

	int msgVerId;
	byte srcCId[];
	int srcCallNo;
	int srcAddrRsv;
	byte destCId[];
	int destCallNo;
	int destAddrRsv;
	short msgCode;
	short msgSubCode;
	int teleServiceID;
	short msgCodeRsv;
	int bodyDataLen;
	int msgSeqNo;
	byte termType;
	byte dataType;
	byte reserved[];
	int reservel;
	byte MTDlvTime[];
	byte Rsv4Protocol[];

	public GIPHeader() {
		msgVerId = 4;
		srcCId = new byte[16];
		srcCallNo = 0;
		srcAddrRsv = 0;
		destCId = new byte[16];
		destCallNo = 0;
		destAddrRsv = 0;
		msgCode = 0;
		msgSubCode = 0;
		teleServiceID = 0;
		msgCodeRsv = 0;
		bodyDataLen = 0;
		msgSeqNo = 0;
		termType = 49;
		dataType = 1;
		reserved = new byte[2];
		reservel = 0;
		MTDlvTime = new byte[11];
		Rsv4Protocol = new byte[29];
	}

	public GIPHeader(byte data[]) {
		msgVerId = 4;
		srcCId = new byte[16];
		srcCallNo = 0;
		srcAddrRsv = 0;
		destCId = new byte[16];
		destCallNo = 0;
		destAddrRsv = 0;
		msgCode = 0;
		msgSubCode = 0;
		teleServiceID = 0;
		msgCodeRsv = 0;
		bodyDataLen = 0;
		msgSeqNo = 0;
		termType = 49;
		dataType = 1;
		reserved = new byte[2];
		reservel = 0;
		MTDlvTime = new byte[11];
		Rsv4Protocol = new byte[29];
		decodeHeader(data);
	}

	public byte[] getBytes() {
		int index = 0;
		byte headers[] = new byte[120];
		headers[index++] = (byte)(msgVerId >>> 24);
		headers[index++] = (byte)(msgVerId >>> 16);
		headers[index++] = (byte)(msgVerId >>> 8);
		headers[index++] = (byte)msgVerId;
		
		for(int i = 0; i < 16; i++) {
			if(i < srcCId.length) {
				headers[index++] = srcCId[i];
			} else {
				headers[index++] = 0;
			}
		}
		
		headers[index++] = (byte)(srcCallNo >>> 24);
		headers[index++] = (byte)(srcCallNo >>> 16);
		headers[index++] = (byte)(srcCallNo >>> 8);
		headers[index++] = (byte)srcCallNo;
		headers[index++] = (byte)(srcAddrRsv >>> 24);
		headers[index++] = (byte)(srcAddrRsv >>> 16);
		headers[index++] = (byte)(srcAddrRsv >>> 8);
		headers[index++] = (byte)srcAddrRsv;
		
		for(int i = 0; i < 16; i++) {
			if(i < destCId.length) {
				headers[index++] = destCId[i];
			} else {
				headers[index++] = 0;
			}
		}
		
		headers[index++] = (byte)(destCallNo >>> 24);
		headers[index++] = (byte)(destCallNo >>> 16);
		headers[index++] = (byte)(destCallNo >>> 8);
		headers[index++] = (byte)destCallNo;
		headers[index++] = (byte)(destAddrRsv >>> 24);
		headers[index++] = (byte)(destAddrRsv >>> 16);
		headers[index++] = (byte)(destAddrRsv >>> 8);
		headers[index++] = (byte)destAddrRsv;
		headers[index++] = (byte)(msgCode >>> 8);
		headers[index++] = (byte)msgCode;
		headers[index++] = (byte)(msgSubCode >>> 8);
		headers[index++] = (byte)msgSubCode;
		headers[index++] = (byte)(teleServiceID >>> 8);
		headers[index++] = (byte)teleServiceID;
		headers[index++] = (byte)(msgCodeRsv >>> 8);
		headers[index++] = (byte)msgCodeRsv;
		headers[index++] = (byte)(bodyDataLen >>> 24);
		headers[index++] = (byte)(bodyDataLen >>> 16);
		headers[index++] = (byte)(bodyDataLen >>> 8);
		headers[index++] = (byte)bodyDataLen;
		headers[index++] = (byte)(msgSeqNo >>> 24);
		headers[index++] = (byte)(msgSeqNo >>> 16);
		headers[index++] = (byte)(msgSeqNo >>> 8);
		headers[index++] = (byte)msgSeqNo;
		headers[index++] = termType;
		headers[index++] = dataType;
		headers[index++] = 0;
		headers[index++] = 0;
		
		for(int i = 0; i < 8; i++) {
			headers[index++] = 0;
		}
		
		for(int i = 0; i < 11; i++) {
			if(i < MTDlvTime.length) {
				headers[index++] = MTDlvTime[i];
			} else {
				headers[index++] = 0;
			}
		}
		
		for(int i = 0; i < 29; i++) {
			if(i < Rsv4Protocol.length) {
				headers[index++] = Rsv4Protocol[i];
			}else{
				headers[index++] = 0;
			}
		}
		
		if(index != 120) {
			System.out.println("error length");
		}
		return headers;
	}

	public void decodeHeader(byte data[]) {
		int index = 0;
		msgVerId = ((data[index++] & 255) << 24) + ((data[index++] & 255) << 16) + ((data[index++] & 255) << 8) + (data[index++] & 255);
		for(int i = 0; i < 16; i++)
		srcCId[i] = data[index++];
		
		srcCallNo = ((data[index++] & 255) << 24) + ((data[index++] & 255) << 16) + ((data[index++] & 255) << 8) + (data[index++] & 255);
		srcAddrRsv = ((data[index++] & 255) << 24) + ((data[index++] & 255) << 16) + ((data[index++] & 255) << 8) + (data[index++] & 255);
		for(int i = 0; i < 16; i++)
		destCId[i] = data[index++];
		
		destCallNo = ((data[index++] & 255) << 24) + ((data[index++] & 255) << 16) + ((data[index++] & 255) << 8) + (data[index++] & 255);
		destAddrRsv = ((data[index++] & 255) << 24) + ((data[index++] & 255) << 16) + ((data[index++] & 255) << 8) + (data[index++] & 255);
		msgCode = (short)(((data[index++] & 255) << 8) + (data[index++] & 255));
		msgSubCode = (short)(((data[index++] & 255) << 8) + (data[index++] & 255));
		teleServiceID = ((data[index++] & 255) << 8) + (data[index++] & 255);
		msgCodeRsv = (short)(((data[index++] & 255) << 8) + (data[index++] & 255));
		bodyDataLen = ((data[index++] & 255) << 24) + ((data[index++] & 255) << 16) + ((data[index++] & 255) << 8) + (data[index++] & 255);
		msgSeqNo = ((data[index++] & 255) << 24) + ((data[index++] & 255) << 16) + ((data[index++] & 255) << 8) + (data[index++] & 255);
		termType = data[index++];
		dataType = data[index++];
		for(int i = 0; i < 2; i++)
		reserved[i] = data[index++];
		
		for(int i = 0; i < 8; i++)
		index++;
		
		reservel = 0;
		for(int i = 0; i < 11; i++)
		MTDlvTime[i] = data[index++];
		
		for(int i = 0; i < 29; i++)
		Rsv4Protocol[i] = data[index++];
	}

	public void printValues() {
		System.out.println("--------------- HEADER ---------------------");
		System.out.println("msgVerId = " + msgVerId);
		System.out.println("srCId= " + new String(srcCId));
		System.out.println("srcCallNo= " + srcCallNo);
		System.out.println("srcAddrRsv = " + srcAddrRsv);
		System.out.println("destCId= " + new String(destCId));
		System.out.println("destCallNo = " + destCallNo);
		System.out.println("destAddrRsv= " + destAddrRsv);
		System.out.println("msgCode= " + msgCode);
		System.out.println("msgSubCode = " + msgSubCode);
		System.out.println("teleServiceID= " + teleServiceID);
		System.out.println("msgCodeRsv = " + msgCodeRsv);
		System.out.println("bodyDataLen= " + bodyDataLen);
		System.out.println("msgSeqNo = " + msgSeqNo);
		System.out.println("termType = " + termType);
		System.out.println("dataType = " + dataType);
		System.out.println("reserved = " + new String(reserved));
		System.out.println("reservel = " + reservel);
		System.out.println("MTDlvTime= " + new String(MTDlvTime));
		System.out.println("Rsv4Protocol = " + new String(Rsv4Protocol));
		System.out.println("-------------------------------------------");
		System.out.println("");
	}
}
