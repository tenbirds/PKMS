package com.pkms.batch.sktdb.service;

import java.sql.Connection;
import java.sql.SQLException;

public class Starter {

	private String fromCon = "";
	private String fromId = "";
	private String fromPw = "";
	private String toCon = "";
	private String toId = "";
	private String toPw = "";
	
	public Starter(String fromCon, String fromId, String fromPw, String toCon, String toId, String toPw) {
		this.fromCon = fromCon;
		this.fromId = fromId;
		this.fromPw = fromPw;
		this.toCon = toCon;
		this.toId = toId;
		this.toPw = toPw;
	}
	
	public void create4Dept()
	{
		// 연결 인스턴트 초기화
		Connection connto = null;
		Connection connfrom= null;
		try 
		{
			// DB에 연결하고 연결 객체를 가져옴
			connfrom = SktDBCommon.getDBFromConnection(fromCon, fromId, fromPw);
			connto = SktDBCommon.getDBToConnection(toCon, toId, toPw);
			
			PKMS_DEPT tm = new PKMS_DEPT();
			
//			writeLog (" 부서 개발 정보 DB처리를 시작합니다.!!! ");
			tm.execute(connfrom, connto);
//			writeLog (" 부서 개발 정보 DB처리를 완료 했습니다.!!! ");
			
			connto.close();
			connfrom.close();
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			try 
			{
				connto.rollback();
				connfrom.rollback();
				connto.close();
				connfrom.close();
			} 
			catch (SQLException e2) 
			{
			}
		}
	}

	public void create4Person() 
	{
		// 연결 인스턴트 초기화
		Connection connto = null;
		Connection connfrom= null;
		try 
		{
			// DB에 연결하고 연결 객체를 가져옴
			connfrom = SktDBCommon.getDBFromConnection(fromCon, fromId, fromPw);
			connto = SktDBCommon.getDBToConnection(toCon, toId, toPw);
			PKMS_PERSON tm = new PKMS_PERSON ();
//			writeLog (" 사원 개발 정보 DB처리를 시작합니다.!!! ");
			tm.execute(connfrom, connto);
//			writeLog (" 사원 개발 정보 DB처리를 완료 했습니다.!!! ");
			connto.close();
			connfrom.close();
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			try 
			{
				connto.rollback();
				connfrom.rollback();
				connto.close();
				connfrom.close();
			} 
			catch (SQLException e2) 
			{
			}
		}
	}


}
