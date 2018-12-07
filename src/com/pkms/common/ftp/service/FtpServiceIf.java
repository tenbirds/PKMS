package com.pkms.common.ftp.service;

import java.io.File;

import org.apache.commons.net.ftp.FTPFile;

public interface FtpServiceIf {

	public void connect() throws Exception;

	public boolean login() throws Exception;

	public boolean logout() throws Exception;

	public FTPFile[] list() throws Exception;

	public boolean get(String source, String target, String name) throws Exception;

	public File getFile(String source, String name) throws Exception;

	public boolean put(String fileName, String targetName) throws Exception;

	public void cd(String path) throws Exception;

	public void disconnect() throws Exception;

	public void setFileType(int iFileType) throws Exception;

}
