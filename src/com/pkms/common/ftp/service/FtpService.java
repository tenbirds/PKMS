package com.pkms.common.ftp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.wings.properties.service.PropertyServiceIf;

@Service("FtpService")
public class FtpService implements FtpServiceIf {
	static Logger logger = Logger.getLogger(FtpService.class);
	
	private static final String sServer = "220.86.29.35"; // 서버 아이피
	private static final int iPort = 21;
	private static final String sId = "insoft"; // 사용자 아이디
	private static final String sPassword = "in-soft"; // 비밀번호
	
	//파일의 업로드 다운로드 위치
	private static final String sUpDir = "Globals.fileStorePath";
	private static final String sDownDir = "Globals.fileStorePath";
	private static final String sLogDir = "/download/log";
	
	FTPClient ftpClient;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	public FtpService() throws Exception {
		ftpClient = new FTPClient();
	}
	
	// 서버로 연결
	@Override
	public void connect() throws Exception {

		try {
			ftpClient.connect(sServer, iPort);
			int reply;
			// 연결 시도후, 성공했는지 응답 코드 확인
			reply = ftpClient.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				System.out
						.println("--------------------------------------------------------------------------------");
				System.out.println("FTP 서버 연결 거부");
				System.out.println("--------------------------------------------------------------------------------\n\n");
			} else {
				System.out
						.println("****************************************************************");
				System.out.println("FTP 서버 연결");
				System.out.println("****************************************************************\n\n");
			}

		} catch (IOException ioe) {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException f) {
					//
				}
			}
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("FTP 서버 연결에 연결 실패");
			System.out.println("--------------------------------------------------------------------------------\n\n");
		}
	}

	// 계정과 패스워드로 로그인
	@Override
	public boolean login() throws Exception {

		try {
			this.connect();

			System.out.println("****************************************************************");
			System.out.println("FTP 서버 로그인");
			System.out.println("****************************************************************\n\n");
			return ftpClient.login(sId, sPassword);
		} catch (IOException ioe) {
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("FTP 서버에 로그인 실패");
			System.out.println("--------------------------------------------------------------------------------\n\n");
		}
		return false;
	}

	// 서버로부터 로그아웃
	@Override
	public boolean logout() throws Exception {

		try {
			System.out.println("****************************************************************");
			System.out.println("FTP 서버 로그아웃");
			System.out.println("****************************************************************\n\n");
			return ftpClient.logout();
		} catch (IOException ioe) {
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("FTP 서버에 로그아웃 실패");
			System.out.println("--------------------------------------------------------------------------------\n\n");
		}
		return false;
	}

	// FTP의 ls 명령, 모든 파일 리스트를 가져온다
	@Override
	public FTPFile[] list() throws Exception {

		FTPFile[] files = null;
		try {
			files = this.ftpClient.listFiles();
			System.out.println("****************************************************************");
			System.out.println("파일 리스트 가져오기");
			System.out.println("****************************************************************\n\n");
			return files;
		} catch (IOException ioe) {
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("파일 리스트 가져오기 실패");
			System.out.println("--------------------------------------------------------------------------------\n\n");
		}
		return null;
	}

	// 파일을 전송 받는다
	@Override
	public boolean get(String source, String target, String name) throws Exception {

		boolean flag = false;

		OutputStream output = null;
		try {
			// 받는 파일 생성 이 위치에 이 이름으로 파일 생성된다
			File local = new File(propertyService.getString(sDownDir), name);
			output = new FileOutputStream(local);
			System.out.println("****************************************************************");
			System.out.println("다운로드할 디렉토리 확인");
			System.out.println("****************************************************************\n\n");
		} catch (FileNotFoundException fnfe) {
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("다운로드할 디렉토리 없음");
			System.out.println("--------------------------------------------------------------------------------\n\n");
			return flag;
		}

		File file = new File(source);
		try {
			if (ftpClient.retrieveFile(source, output)) {
				flag = true;
			}
			System.out.println("****************************************************************");
			System.out.println("파일 다운로드 완료");
			System.out.println("****************************************************************\n\n");
		} catch (IOException ioe) {
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("파일 다운로드 실패");
			System.out.println("--------------------------------------------------------------------------------\n\n");
		}
		return flag;
	}

	// 파일을 전송 받는다 위의 method 와 return 값이 달라서 하나 더 만듬
	@Override
	public File getFile(String source, String name) throws Exception {

		OutputStream output = null;
		File local = null;
		try {
			// 받는 파일 생성
			local = new File(propertyService.getString(sDownDir), name);
			output = new FileOutputStream(local);
		} catch (FileNotFoundException fnfe) {
			System.out.println("다운로드할 디렉토리가 없습니다");
		}

		File file = new File(source);
		try {
			if (ftpClient.retrieveFile(source, output)) {
				//
			}
		} catch (IOException ioe) {
			System.out.println("파일을 다운로드 하지 못했습니다");
		}
		return local;
	}

	// 파일을 전송 한다
	@Override
	public boolean put(String fileName, String targetName) throws Exception {

		boolean flag = false;
		InputStream input = null;
		File local = null;

		try {
			local = new File(propertyService.getString(sUpDir), fileName);
			input = new FileInputStream(local);
		} catch (FileNotFoundException e) {
			return flag;
		}

		try {

			// targetName 으로 파일이 올라간다
			if (ftpClient.storeFile(targetName, input)) {
				flag = true;
			}
		} catch (IOException e) {
			System.out.println("파일을 전송하지 못했습니다");
			return flag;
		}
		return flag;

	}

	// 서버 디렉토리 이동
	@Override
	public void cd(String path) throws Exception {

		try {
			ftpClient.changeWorkingDirectory(path);
		} catch (IOException ioe) {
			System.out.println("폴더를 이동하지 못했습니다");
		}
	}

	// 서버로부터 연결을 닫는다
	@Override
	public void disconnect() throws Exception {

		try {
			ftpClient.disconnect();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Override
	public void setFileType(int iFileType) throws Exception {
		try {
			ftpClient.setFileType(iFileType);
		} catch (Exception e) {
			System.out.println("파일 타입을 설정하지 못했습니다");
		}
	}
}
