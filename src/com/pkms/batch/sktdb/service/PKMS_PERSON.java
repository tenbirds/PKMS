package com.pkms.batch.sktdb.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

public class PKMS_PERSON {
	/** 생성자 */
	public PKMS_PERSON  ()
	{
	}

	// 오늘 일자 가져오기
	 public static String getDay() {
		 
		    Date day= new Date();
	        SimpleDateFormat  df = new SimpleDateFormat ("yyyyMMdd");
	        String today = df.format(day);

	        return today;
	 }
	 
	 
	/** 사원정보 처리 시작  */
	public synchronized void execute(Connection connfrom,  Connection connto) 
	{
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		ResultSetMetaData rsmt = null;
		try 
		{
			connto.setAutoCommit(false);
			connfrom.setAutoCommit(false);

			StringBuffer sql  =  new StringBuffer();

			/**  사원정보 테이블을 검사하여 복사본 삭제  */
			if ( SktDBCommon.checkTable(connto, "PAMAS.INF_PERSON_INFO_RCV_CPP"))
			{
				sql.append("DROP TABLE PAMAS.INF_PERSON_INFO_RCV_CPP ");
				pstmt = connto.prepareStatement(sql.toString());
				//writeLog ("1 - ");
				pstmt.executeUpdate();
				pstmt.close();
			}

			/** 테이블 복사본 생성, 처리 중 오류 생기면 복사본을 이용하여 원복시킴 */
			sql  =  new StringBuffer();
			sql.append("  CREATE TABLE PAMAS.INF_PERSON_INFO_RCV_CPP   ");
			sql.append("  AS   ");
			sql.append("  SELECT * FROM PAMAS.INF_PERSON_INFO_RCV   ");
			pstmt = connto.prepareStatement(sql.toString());
			//writeLog ("2 - ");
			//writeLog(sql.toString());
			pstmt.executeUpdate();
			pstmt.close();

			/** 사원 정보 테이블이 존제하면 대상 테이블을 삭제   */
			sql  =  new StringBuffer();
			if ( SktDBCommon.checkTable(connto, "PAMAS.INF_PERSON_INFO_RCV"))
			{
				sql.append("DROP TABLE PAMAS.INF_PERSON_INFO_RCV  ");
				pstmt = connto.prepareStatement(sql.toString());
				//writeLog ("3 - ");
				//writeLog(sql.toString());
				pstmt.executeUpdate();
				pstmt.close();
			}

            /** 사원 테이블 생성  */
            sql = new StringBuffer();
            sql.append("CREATE TABLE PAMAS.INF_PERSON_INFO_RCV        " );
            sql.append("(                    " );
            sql.append("EMPNO         CHAR(7 BYTE)   NOT NULL,                    " );
            sql.append("HNAME         VARCHAR2(40 BYTE),                    " );
            sql.append("ENAME         VARCHAR2(40 BYTE),                    " );
            sql.append("REGNO         CHAR(13 BYTE),                    " );
            sql.append("INDEPT        VARCHAR2(8 BYTE),                    " );
            sql.append("BOOSER        VARCHAR2(40 BYTE),                    " );
            sql.append("SOSOK         VARCHAR2(120 BYTE),                    " );
            sql.append("TSOSOK        VARCHAR2(200 BYTE),                    " );
            sql.append("JBGRP         VARCHAR2(3 BYTE),                    " );
            sql.append("JBRANK        VARCHAR2(2 BYTE),                    " );
            sql.append("JBCHARGE      VARCHAR2(3 BYTE),                    " );
            sql.append("HOLDOFFIDIVI  VARCHAR2(1 BYTE),                    " );
            sql.append("PLACE         VARCHAR2(4 BYTE),                    " );
            sql.append("JOINCOMDD     VARCHAR2(8 BYTE),                    " );
            sql.append("PRODATE       CHAR(8 BYTE),                    " );
            sql.append("RETIREDD      CHAR(8 BYTE),                    " );
            sql.append("JOB           CHAR(8 BYTE),                    " );
            sql.append("JOBNM         VARCHAR2(40 BYTE),                    " );
            sql.append("STARTDATE     CHAR(8 BYTE),                    " );
            sql.append("CLOSEDATE     CHAR(8 BYTE),                    " );
            sql.append("MOREPOYN      CHAR(1 BYTE),                    " );
            sql.append("INTELNO       VARCHAR2(30 BYTE),                    " );
            sql.append("MOVETELNO     VARCHAR2(30 BYTE),                    " );
            sql.append("TELNO         VARCHAR2(30 BYTE),                    " );
            sql.append("FAX           VARCHAR2(30 BYTE),                    " );
            sql.append("EMAIL         VARCHAR2(50 BYTE),                    " );
            sql.append("EMAIL2        VARCHAR2(50 BYTE),                    " );
            sql.append("IDSTARTDATE   CHAR(8 BYTE),                    " );
            sql.append("PHOTOURL      VARCHAR2(255 BYTE),                    " );
            sql.append("T_FLAG        CHAR(1 BYTE),                    " );
            sql.append("SENDDT        DATE,                    " );
			sql.append(" \n");
			sql.append("	CONSTRAINT INF_PERSON_INFO_RCV_PK PRIMARY KEY (EMPNO) \n");
			sql.append("	USING INDEX  \n");
			sql.append("	PCTFREE 10  \n");
			sql.append("	STORAGE (INITIAL 10M  \n");
			sql.append("			 NEXT	 10M  \n");
			sql.append("			 PCTINCREASE 0 ) \n");
			sql.append("	TABLESPACE PSX \n");
			sql.append(")  \n");
			sql.append(" \n");
			sql.append("PCTFREE 10 \n");
			sql.append("PCTUSED 90 \n");
			sql.append("STORAGE(INITIAL 10M \n");
			sql.append("		NEXT 	10M  \n");
			sql.append("		PCTINCREASE 0) \n");
			sql.append("TABLESPACE PS \n");
			
            pstmt = connto.prepareStatement(sql.toString());
            //writeLog ("4 - ");
            //writeLog(sql.toString());
            pstmt.executeUpdate();
            pstmt.close();

            /**  원본데이터를 가져옴 */
            StringBuffer query = new StringBuffer();
            query.append("  SELECT * FROM IDMIS.INF_PERSON_INFO_RCV  " );
            pstmt = connfrom.prepareStatement(query.toString());
            rs = pstmt.executeQuery();

            //writeLog ("5 - ");

			/** Vector에 레코드를 저장  */
			while(rs.next())
			{
				addRecord(new Record(
					rs.getString("empno"),
					rs.getString("hname"),
					rs.getString("ename"),
					rs.getString("regno"),
					rs.getString("indept"),
					rs.getString("booser"),
					rs.getString("sosok"),
					rs.getString("tsosok"),
					rs.getString("jbgrp"),
					rs.getString("jbrank"),
					rs.getString("jbcharge"),
					rs.getString("holdoffidivi"),
					rs.getString("place"),
					rs.getString("joincomdd"),
					rs.getString("prodate"),
					rs.getString("retiredd"),
					rs.getString("job"),
					rs.getString("jobnm"),
					rs.getString("startdate"),
					rs.getString("closedate"),
					rs.getString("morepoyn"),
					rs.getString("intelno"),
					rs.getString("movetelno"),
					rs.getString("telno"),
					rs.getString("fax"),
					rs.getString("email"),
					rs.getString("email2"),
					rs.getString("idstartdate"),
					rs.getString("photourl"),
					rs.getString("t_flag"),
					rs.getString("senddt")
				));
			}

            //writeLog ("5 5 - ");

			/**  저장된 벡터의 내용을 대상  테이블에 등록  */
			sql = new StringBuffer();
			sql.append("INSERT INTO PAMAS.INF_PERSON_INFO_RCV  (  " );
			sql.append("EMPNO,            " );
			sql.append("HNAME,   " );
			sql.append("ENAME,  " );
			sql.append("REGNO,         " );
			sql.append("INDEPT,          " );
			sql.append("BOOSER,          " );
			sql.append("SOSOK,            " );
			sql.append("TSOSOK,          " );
			sql.append("JBGRP,         " );
			sql.append("JBRANK, " );
			sql.append("JBCHARGE,  " );
			sql.append("HOLDOFFIDIVI,               " );
			sql.append("PLACE,         " );
			sql.append("JOINCOMDD,             " );
			sql.append("PRODATE,                 " );
			sql.append("RETIREDD,     " );
			sql.append("JOB,     " );
			sql.append("JOBNM,     " );
			sql.append("STARTDATE,     " );
			sql.append("CLOSEDATE,     " );
			sql.append("MOREPOYN,     " );
			sql.append("INTELNO,     " );
			sql.append("MOVETELNO,     " );
			sql.append("TELNO,     " );
			sql.append("FAX,     " );
			sql.append("EMAIL,     " );
			sql.append("EMAIL2,     " );
			sql.append("IDSTARTDATE,     " );
			sql.append("PHOTOURL,     " );
			sql.append("T_FLAG,     " );
			sql.append("SENDDT     " );
			
			sql.append(") VALUES (  " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?,    " );
			sql.append(" ?  )  " );
			
			//pstmt = new VOLog(connto, sql.toString());
			pstmt = connto.prepareStatement(sql.toString());
			//writeLog("6 -");

			/** 벡터 데이터를 대상 테이블에 등록  */
			for (int i = 0; i < records.size(); i++) 
			{
				//writeLog("6 666 -"+i);

				pstmt.setString(1,  ((Record)records.elementAt(i)).empno );
				pstmt.setString(2,  ((Record)records.elementAt(i)).hname );
				pstmt.setString(3,  ((Record)records.elementAt(i)).ename );
				pstmt.setString(4,  ((Record)records.elementAt(i)).regno );
				pstmt.setString(5,  ((Record)records.elementAt(i)).indept );
				pstmt.setString(6,  ((Record)records.elementAt(i)).booser );
				pstmt.setString(7,  ((Record)records.elementAt(i)).sosok );
				pstmt.setString(8,  ((Record)records.elementAt(i)).tsosok );
				pstmt.setString(9,  ((Record)records.elementAt(i)).jbgrp);
				pstmt.setString(10,  ((Record)records.elementAt(i)).jbrank );
				pstmt.setString(11,  ((Record)records.elementAt(i)).jbcharge );
				pstmt.setString(12,  ((Record)records.elementAt(i)).holdoffidivi );
				pstmt.setString(13,  ((Record)records.elementAt(i)).place );
				pstmt.setString(14,  ((Record)records.elementAt(i)).joincomdd );
				pstmt.setString(15,  ((Record)records.elementAt(i)).prodate);
				pstmt.setString(16,  ((Record)records.elementAt(i)).retiredd);
				pstmt.setString(17,  ((Record)records.elementAt(i)).job);
				pstmt.setString(18,  ((Record)records.elementAt(i)).jobnm);
				pstmt.setString(19,  ((Record)records.elementAt(i)).startdate);
				pstmt.setString(20,  ((Record)records.elementAt(i)).closedate);
				pstmt.setString(21,  ((Record)records.elementAt(i)).morepoyn);
				pstmt.setString(22,  ((Record)records.elementAt(i)).intelno);
				pstmt.setString(23,  ((Record)records.elementAt(i)).movetelno);
				pstmt.setString(24,  ((Record)records.elementAt(i)).telno);
				pstmt.setString(25,  ((Record)records.elementAt(i)).fax);
				pstmt.setString(26,  ((Record)records.elementAt(i)).email);
				pstmt.setString(27,  ((Record)records.elementAt(i)).email2);
				pstmt.setString(28,  ((Record)records.elementAt(i)).idstartdate);
				pstmt.setString(29,  ((Record)records.elementAt(i)).photourl);
				pstmt.setString(30,  ((Record)records.elementAt(i)).t_flag);
				//pstmt.setString(31,  ((Record)records.elementAt(i)).senddt);
				pstmt.setString(31, getDay());
				pstmt.executeUpdate();
			}

			//writeLog(((VOLog)pstmt).getQueryString());
			//writeLog(sql.toString());
			query = new StringBuffer();
			query.append("  SELECT COUNT(*)  FROM IDMIS.INF_PERSON_INFO_RCV     " );
			pstmt = connfrom.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			rs.next();
			
			query = new StringBuffer();
			query.append("  SELECT COUNT(*)  FROM PAMAS.INF_PERSON_INFO_RCV     ") ;
			pstmt = connto.prepareStatement(query.toString());
			rs2 = pstmt.executeQuery();
			rs2.next();
			
//			writeLog("원본자료 건수 / 등록자료 건수:"  + rs.getInt(1)+ "/"  + rs2.getInt(1)         );

			//System.out.println();
			
			connto.commit();
			connfrom.commit();
			pstmt.close();
			connto.close();
            connfrom.close();

        } 
        catch (Exception e) 
        {
			System.out.println(e.getMessage());
        	try 
        	{
                pstmt.close();
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
/*** 사원정보 처리 끝  */

/////////////////////////////////////////////////////////////////////////////////////////////

    /** MEMBER */
    ArrayList recordss = new ArrayList();         /**CodeRecord 레코드를 담을 목록 */
    Vector records = new Vector();                  /** 레코드 목록 */
    Hashtable hash = new Hashtable();           /** 레코드 목록 검색을 위하여 */

    /** 레코드 목록에 전체 Record 추가 */
    public void addRecord(Record record){
            records.add(record);                                    /** 레코드 목록에 레코드를 전체 추가 : 특정 레코드의 인덱스 파악이 어려움   */
            hash.put( record.empno, record);                  /**  레코드 클래스의  키와 레코드 클래스를 배열로 검색 테이블에 추가하고
                                                                                         입력 하는 레코드 클래스의 KEY를 사용하는 것임
                                                                                  */
    }

    /** 레코드 목록에서  전체 레코드 클래스의  삭제 */
    public void removeRecord(Record record) {
        records.remove(record);                              /** 레코드 목록에서 전체 레코드 클래스를 삭제 특정 레코드를 삭제하려면 배열을 사용해야 함  */
        hash.remove(record);                                   /** 해쉬 테이블에서 삭제 */
    }

    /** 레코드 목록에 저장된 Record 클래스 카운터를 반환 */
    public int getRecordCount() {
        return records.size();
    }

    /** 레코드 목록에 저장된 Record 클래스 카운터를 문자형 숫자로 반환  */
    public String getRecordsize() {
        return String.valueOf(records.size());
    }

    /**
     * 레코드 목록에서 인덱스 값으로 특정 Record 클래스의 반환
     * */
    public Record getRecord(int i) {
        //return (Record) records.get(i);   // ArrayList에서 사용
        Record record = null;

        /**
         * 반환하려는 레코드가 인덱스의 범주안에 있으면
         * */
        if( i >= 0  && i < records.size()) {
            record = (Record)records.elementAt( i);
        } else {
            System.out.println("Field index out of bound. " + i);
        }
            return record;
    }

    /** 해쉬 테이블에서 레코드 클래스의 키값으로 레코드를 반환 */
    public Record getRecord( String key) {
        Record record = (Record)hash.get( key);
        if( record == null) {
            System.out.println("Field " + key + "의 레코드가 존제하지 않습니다."  );
        }
        return record;
    }

    /**레코드 목록의 내용의 전체출력 */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("FIELD LIST" +"\n");

        /**목록 전체를 출력 */
        for (int i = 0; i < records.size(); i++) {
            sb.append("Index" + i + " : " + ((Record)records.elementAt(i)).key + "\n");
        }
        return sb.toString();
    }

    /** 키 값에 해당하는 인덱스의 레코드의 내용을 반환  */
    public String getString( String key) {
        return getRecord(key).getValue();
    }

    /** 입력 받은 키 값으로 레코드를 반환받고 그 레코드에 값을 변경 저장  */
    public void setString( String param, String value) {
        getRecord( param).setValue( value);
    }

    /** 키 값에 해당하는 인덱스의 레코드의 내용을 반환  */
    //public String getString( String key) {
        //return getRecord(key).getValue();
   // }

    /** 입력 받은 키 값으로 레코드를 반환받고 그 레코드에 값을 변경 저장  */
    //public void setString( String param, String value) {
        //getRecord( param).setValue( value);
    //}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/** 단위 레코드 클래스이고  여기에 핸들링 할 정보가 저장됨   */
public class Record{

	/** member */
	 String  key   = null;
	 String  empno = null;
	 String	hname = null;
	 String	ename = null;
	 String	regno = null;
	 String	indept = null;
	 String	booser = null;
	 String	sosok  = null;
	 String	tsosok = null;
	 String	jbgrp  = null;
	 String	jbrank = null;
	 String	jbcharge = null;
	 String	holdoffidivi = null;
	 String	place        = null;
	 String	joincomdd    = null;
	 String	prodate      = null;
	 String	retiredd     = null;
	 String	job          = null;
	 String	jobnm        = null;
	 String	startdate    = null;
	 String	closedate    = null;
	 String	morepoyn     = null;
	 String	intelno      = null;
	 String	movetelno    = null;
	 String	telno        = null;
	 String	fax          = null;
	 String	email        = null;
	 String	email2       = null;
	 String	idstartdate  = null;
	 String	photourl     = null;
	 String	t_flag       = null;
	 String	senddt       = null;

	    /** 생성자 */
	    public Record(){
	     }


	    public Record(
	    		String  empno        ,
	    		String	hname        ,
	    		String	ename        ,
	    		String	regno        ,
	    		String	indept       ,
	    		String	booser       ,
	    		String	sosok        ,
	    		String	tsosok       ,
	    		String	jbgrp        ,
	    		String	jbrank       ,
	    		String	jbcharge     ,
	    		String	holdoffidivi ,
	    		String	place        ,
	    		String	joincomdd    ,
	    		String	prodate      ,
	    		String	retiredd     ,
	    		String	job          ,
	    		String	jobnm        ,
	    		String	startdate    ,
	    		String	closedate    ,
	    		String	morepoyn     ,
	    		String	intelno      ,
	    		String	movetelno    ,
	    		String	telno        ,
	    		String	fax          ,
	    		String	email        ,
	    		String	email2       ,
	    		String	idstartdate  ,
	    		String	photourl     ,
	    		String	t_flag       ,
	    		String	senddt
	    		){
		            this.setEmpno(empno);
		            this.setHname(hname);
		            this.setEname(ename);
		            this.setRegno(regno);
		            this.setIndept(indept);
		            this.setBooser(booser);
		            this.setSosok(sosok);
		            this.setTsosok(tsosok);
		            this.setJbgrp(jbgrp);
		            this.setJbrank(jbrank);
		            this.setJbcharge(jbcharge);
		            this.setHoldoffidivi(holdoffidivi);
		            this.setPlace(place);
		            this.setJoincomdd(joincomdd);
		            this.setProdate(prodate );
		            this.setRetiredd(retiredd);
		            this.setJob(job);
		            this.setJobnm(jobnm);
		            this.setStartdate(startdate);
		            this.setClosedate(closedate);
		            this.setMorepoyn(morepoyn );
		            this.setIntelno(intelno);
		            this.setMovetelno(movetelno   );
		            this.setTelno(telno );
		            this.setFax(fax);
		            this.setEmail(email);
		            this.setEmail2(email2);
		            this.setIdstartdate(idstartdate);
		            this.setPhotourl(photourl);
		            this.setT_flag(t_flag);
		            this.setSenddt(senddt);
	    }





////////////////////////////////////////////////////////////////////////////////////

            public String getValue(){
              return key;
            }

            public void setValue(String value){
               key = value;
            }



	/**
	 * Returns the booser.
	 * @return String
	 */
	public String getBooser() {
		return booser;
	}

	/**
	 * Returns the closedate.
	 * @return String
	 */
	public String getClosedate() {
		return closedate;
	}

	/**
	 * Returns the email.
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the email2.
	 * @return String
	 */
	public String getEmail2() {
		return email2;
	}

	/**
	 * Returns the empno.
	 * @return String
	 */
	public String getEmpno() {
		return empno;
	}

	/**
	 * Returns the ename.
	 * @return String
	 */
	public String getEname() {
		return ename;
	}

	/**
	 * Returns the fax.
	 * @return String
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Returns the hname.
	 * @return String
	 */
	public String getHname() {
		return hname;
	}

	/**
	 * Returns the holdoffidivi.
	 * @return String
	 */
	public String getHoldoffidivi() {
		return holdoffidivi;
	}

	/**
	 * Returns the idstartdate.
	 * @return String
	 */
	public String getIdstartdate() {
		return idstartdate;
	}

	/**
	 * Returns the indept.
	 * @return String
	 */
	public String getIndept() {
		return indept;
	}

	/**
	 * Returns the intelno.
	 * @return String
	 */
	public String getIntelno() {
		return intelno;
	}

	/**
	 * Returns the jbcharge.
	 * @return String
	 */
	public String getJbcharge() {
		return jbcharge;
	}

	/**
	 * Returns the jbgrp.
	 * @return String
	 */
	public String getJbgrp() {
		return jbgrp;
	}

	/**
	 * Returns the jbrank.
	 * @return String
	 */
	public String getJbrank() {
		return jbrank;
	}

	/**
	 * Returns the job.
	 * @return String
	 */
	public String getJob() {
		return job;
	}

	/**
	 * Returns the jobnm.
	 * @return String
	 */
	public String getJobnm() {
		return jobnm;
	}

	/**
	 * Returns the joincomdd.
	 * @return String
	 */
	public String getJoincomdd() {
		return joincomdd;
	}

	/**
	 * Returns the key.
	 * @return String
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Returns the morepoyn.
	 * @return String
	 */
	public String getMorepoyn() {
		return morepoyn;
	}

	/**
	 * Returns the movetelno.
	 * @return String
	 */
	public String getMovetelno() {
		return movetelno;
	}

	/**
	 * Returns the photourl.
	 * @return String
	 */
	public String getPhotourl() {
		return photourl;
	}

	/**
	 * Returns the place.
	 * @return String
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Returns the prodate.
	 * @return String
	 */
	public String getProdate() {
		return prodate;
	}

	/**
	 * Returns the regno.
	 * @return String
	 */
	public String getRegno() {
		return regno;
	}

	/**
	 * Returns the retiredd.
	 * @return String
	 */
	public String getRetiredd() {
		return retiredd;
	}

	/**
	 * Returns the senddt.
	 * @return String
	 */
	public String getSenddt() {
		return senddt;
	}

	/**
	 * Returns the sosok.
	 * @return String
	 */
	public String getSosok() {
		return sosok;
	}

	/**
	 * Returns the startdate.
	 * @return String
	 */
	public String getStartdate() {
		return startdate;
	}

	/**
	 * Returns the t_flag.
	 * @return String
	 */
	public String getT_flag() {
		return t_flag;
	}

	/**
	 * Returns the telno.
	 * @return String
	 */
	public String getTelno() {
		return telno;
	}

	/**
	 * Returns the tsosok.
	 * @return String
	 */
	public String getTsosok() {
		return tsosok;
	}

	/**
	 * Sets the booser.
	 * @param booser The booser to set
	 */
	public void setBooser(String booser) {
		this.booser = booser;
	}

	/**
	 * Sets the closedate.
	 * @param closedate The closedate to set
	 */
	public void setClosedate(String closedate) {
		this.closedate = closedate;
	}

	/**
	 * Sets the email.
	 * @param email The email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the email2.
	 * @param email2 The email2 to set
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	/**
	 * Sets the empno.
	 * @param empno The empno to set
	 */
	public void setEmpno(String empno) {
		this.empno = empno;
	}

	/**
	 * Sets the ename.
	 * @param ename The ename to set
	 */
	public void setEname(String ename) {
		this.ename = ename;
	}

	/**
	 * Sets the fax.
	 * @param fax The fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Sets the hname.
	 * @param hname The hname to set
	 */
	public void setHname(String hname) {
		this.hname = hname;
	}

	/**
	 * Sets the holdoffidivi.
	 * @param holdoffidivi The holdoffidivi to set
	 */
	public void setHoldoffidivi(String holdoffidivi) {
		this.holdoffidivi = holdoffidivi;
	}

	/**
	 * Sets the idstartdate.
	 * @param idstartdate The idstartdate to set
	 */
	public void setIdstartdate(String idstartdate) {
		this.idstartdate = idstartdate;
	}

	/**
	 * Sets the indept.
	 * @param indept The indept to set
	 */
	public void setIndept(String indept) {
		this.indept = indept;
	}

	/**
	 * Sets the intelno.
	 * @param intelno The intelno to set
	 */
	public void setIntelno(String intelno) {
		this.intelno = intelno;
	}

	/**
	 * Sets the jbcharge.
	 * @param jbcharge The jbcharge to set
	 */
	public void setJbcharge(String jbcharge) {
		this.jbcharge = jbcharge;
	}

	/**
	 * Sets the jbgrp.
	 * @param jbgrp The jbgrp to set
	 */
	public void setJbgrp(String jbgrp) {
		this.jbgrp = jbgrp;
	}

	/**
	 * Sets the jbrank.
	 * @param jbrank The jbrank to set
	 */
	public void setJbrank(String jbrank) {
		this.jbrank = jbrank;
	}

	/**
	 * Sets the job.
	 * @param job The job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * Sets the jobnm.
	 * @param jobnm The jobnm to set
	 */
	public void setJobnm(String jobnm) {
		this.jobnm = jobnm;
	}

	/**
	 * Sets the joincomdd.
	 * @param joincomdd The joincomdd to set
	 */
	public void setJoincomdd(String joincomdd) {
		this.joincomdd = joincomdd;
	}

	/**
	 * Sets the key.
	 * @param key The key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Sets the morepoyn.
	 * @param morepyon The morepyon to set
	 */
	public void setMorepoyn(String morepoyn) {
		this.morepoyn = morepoyn;
	}

	/**
	 * Sets the movetelno.
	 * @param movetelno The movetelno to set
	 */
	public void setMovetelno(String movetelno) {
		this.movetelno = movetelno;
	}

	/**
	 * Sets the photourl.
	 * @param photourl The photourl to set
	 */
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	/**
	 * Sets the place.
	 * @param place The place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * Sets the prodate.
	 * @param prodate The prodate to set
	 */
	public void setProdate(String prodate) {
		this.prodate = prodate;
	}

	/**
	 * Sets the regno.
	 * @param regno The regno to set
	 */
	public void setRegno(String regno) {
		this.regno = regno;
	}

	/**
	 * Sets the retiredd.
	 * @param retiredd The retiredd to set
	 */
	public void setRetiredd(String retiredd) {
		this.retiredd = retiredd;
	}

	/**
	 * Sets the senddt.
	 * @param senddt The senddt to set
	 */
	public void setSenddt(String senddt) {
		this.senddt = senddt;
	}

	/**
	 * Sets the sosok.
	 * @param sosok The sosok to set
	 */
	public void setSosok(String sosok) {
		this.sosok = sosok;
	}

	/**
	 * Sets the startdate.
	 * @param startdate The startdate to set
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	/**
	 * Sets the t_flag.
	 * @param t_flag The t_flag to set
	 */
	public void setT_flag(String t_flag) {
		this.t_flag = t_flag;
	}

	/**
	 * Sets the telno.
	 * @param telno The telno to set
	 */
	public void setTelno(String telno) {
		this.telno = telno;
	}

	/**
	 * Sets the tsosok.
	 * @param tsosok The tsosok to set
	 */
	public void setTsosok(String tsosok) {
		this.tsosok = tsosok;
	}
  }
}
