package com.pkms.batch.sktdb.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class PKMS_DEPT {
	/** 생성자 */
	public PKMS_DEPT ()
	{
	}

	/** 부서정보 처리 시작  */
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

			/** 부서정보 테이블 생성 */
			StringBuffer sql  =  new StringBuffer();

			/**  부서정보 테이블을 검사하여 복사본 삭제  */
			if (SktDBCommon.checkTable(connto, "PAMAS.INF_DEPT_INFO_RCV_CPP"))
			{
				sql.append("DROP TABLE PAMAS.INF_DEPT_INFO_RCV_CPP");
				pstmt = connto.prepareStatement(sql.toString());
				//writeLog(sql.toString());
				pstmt.executeUpdate();
				pstmt.close();
			}

			/** 테이블 복사본 생성, 처리 중 오류 생기면 복사본을 이용하여 원복시킴   */
			sql  =  new StringBuffer();
			sql.append("CREATE TABLE PAMAS.INF_DEPT_INFO_RCV_CPP \n");
			sql.append("AS \n");
			sql.append("SELECT * FROM PAMAS.INF_DEPT_INFO_RCV \n");
			pstmt = connto.prepareStatement(sql.toString());
			//writeLog(sql.toString());
			pstmt.executeUpdate();
			pstmt.close();

			/** 부서정보 테이블이 존제하면 대상 테이블을 삭제   */
			sql  =  new StringBuffer();
			if ( SktDBCommon.checkTable(connto, "PAMAS.INF_DEPT_INFO_RCV"))
			{
				sql.append("  DROP TABLE PAMAS.INF_DEPT_INFO_RCV ");
				pstmt = connto.prepareStatement(sql.toString());
				//writeLog(sql.toString());
				pstmt.executeUpdate();    // 나중에 주석 해제 할 것
				pstmt.close();
			}

            /** 대상 테이블 생성  */
			sql = new StringBuffer();
			sql.append("CREATE TABLE PAMAS.INF_DEPT_INFO_RCV (  \n");
			sql.append("INDEPT          VARCHAR2(8)  NOT NULL,	\n");																										
			sql.append("CRTYYMMDD       CHAR(8),            	\n");																										
			sql.append("DESTRUCTDD      CHAR(8),				\n");																										
			sql.append("OUTDEPT         VARCHAR2(6),          	\n");																										
			sql.append("DEPTNM          VARCHAR2(40),          	\n");																										
			sql.append("SOSOK           VARCHAR2(120),         	\n");																										
			sql.append("TSOSOK          VARCHAR2(200),         	\n");																										
			sql.append("LEVELCD         VARCHAR2(2),          	\n");																										
			sql.append("DEPTREDUCNM     VARCHAR2(12),         	\n");																										
			sql.append("HIGHPARTDEPT    VARCHAR2(8),          	\n");																										
			sql.append("LOWYN           CHAR(1),              	\n");																										
			sql.append("DIREMPNO        CHAR(7),              	\n");																										
			sql.append("WRKPLC          VARCHAR2(4),          	\n");																										
			sql.append("MPRS            CHAR(1),              	\n");																										
			sql.append("T_FLAG          CHAR(1),              	\n");																										
			sql.append("SENDDT			DATE,                   \n");																										;
			sql.append(" \n");
			sql.append("	CONSTRAINT INF_DEPT_INFO_RCV_PK PRIMARY KEY (INDEPT) \n");
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
			//writeLog(sql.toString());
			
            pstmt = connto.prepareStatement(sql.toString());
            pstmt.executeUpdate();
            pstmt.close();

			StringBuffer query = new StringBuffer();
			query.append("  SELECT * FROM IDMIS.INF_DEPT_INFO_RCV " );
			pstmt = connfrom.prepareStatement(query.toString());
			rs = pstmt.executeQuery();

			/** Vector에 레코드를 저장  */
			while(rs.next())
			{
				// System.out.println(rs.getRow());
				addRecord(new Record(
					rs.getString("indept"),
					rs.getString("crtyymmdd"),
					rs.getString("destructdd"),
					rs.getString("outdept"),
					rs.getString("deptnm"),
					rs.getString("sosok"),
					rs.getString("tsosok"),
					rs.getString("levelcd"),
					rs.getString("deptreducnm"),
					rs.getString("highpartdept"),
					rs.getString("lowyn"),
					rs.getString("dirempno"),
					rs.getString("wrkplc"),
					rs.getString("mprs"),
					rs.getString("t_flag"),
					rs.getString("senddt")
					));
			}

			/**  저장된 벡터의 내용을 테이블에 등록  */
			sql = new StringBuffer();
			sql.append("INSERT INTO PAMAS.INF_DEPT_INFO_RCV (  " );
			sql.append("INDEPT,         " );
			sql.append("CRTYYMMDD,   	" );
			sql.append("DESTRUCTDD,  	" );
			sql.append("OUTDEPT,        " );
			sql.append("DEPTNM,         " );
			sql.append("SOSOK,          " );
			sql.append("TSOSOK,         " );
			sql.append("LEVELCD,        " );
			sql.append("DEPTREDUCNM, 	" );
			sql.append("HIGHPARTDEPT,	" );
			sql.append("LOWYN,          " );
			sql.append("DIREMPNO,       " );
			sql.append("WRKPLC,         " );
			sql.append("MPRS,           " );
			sql.append("T_FLAG,         " );
			sql.append(" SENDDT         " );
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
			sql.append(" ?  )  " );

			//pstmt = new VOLog(connto, sql.toString());
			pstmt = connto.prepareStatement(sql.toString());

			/**벡터 자료를 테이블에 등록  */
			for (int i = 0; i < records.size(); i++) 
			{
				//sb.append("Index" + i + " : " + ((Record)records.elementAt(i)).key + "\n");
				pstmt.setString(1,  ((Record)records.elementAt(i)).indept );
				pstmt.setString(2,  ((Record)records.elementAt(i)).crtyymmdd );
				pstmt.setString(3,  ((Record)records.elementAt(i)).destructdd );

				// 외부 부서번호 값이 널값이면 안되는 데 임시로 처리함
				if (((Record)records.elementAt(i)).outdept == null)
					pstmt.setString(4, "1");
				else
					pstmt.setString(4,  ((Record)records.elementAt(i)).outdept );

				pstmt.setString(5,  ((Record)records.elementAt(i)).deptnm );
				pstmt.setString(6,  ((Record)records.elementAt(i)).sosok );
				pstmt.setString(7,  ((Record)records.elementAt(i)).tsosok );
				pstmt.setString(8,  ((Record)records.elementAt(i)).levelcd );
				pstmt.setString(9,  ((Record)records.elementAt(i)).deptreducnm );
				pstmt.setString(10,  ((Record)records.elementAt(i)).highpartdept );
				pstmt.setString(11,  ((Record)records.elementAt(i)).lowyn );
				pstmt.setString(12,  ((Record)records.elementAt(i)).dirempno );
				pstmt.setString(13,  ((Record)records.elementAt(i)).wrkplc );
				pstmt.setString(14,  ((Record)records.elementAt(i)).mprs );
				pstmt.setString(15,  ((Record)records.elementAt(i)).t_flag );
				// pstmt.setString(16,  ((Record)records.elementAt(i)).senddt );
				pstmt.setString(16,  null );
				//writeLog(((VOLog)pstmt).getQueryString());
				pstmt.executeUpdate();
			}
			
			query = new StringBuffer();
			query.append("  SELECT COUNT(*)  FROM IDMIS.INF_DEPT_INFO_RCV     " );
			pstmt = connfrom.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			rs.next();
			
			query = new StringBuffer();
			query.append("  SELECT COUNT(*)  FROM PAMAS.INF_DEPT_INFO_RCV ") ;
			pstmt = connto.prepareStatement(query.toString());
			rs2 = pstmt.executeQuery();
			rs2.next();
			
//			writeLog("원본자료 건수 / 등록자료 건수:"  + rs.getInt(1)+ "/"  + rs2.getInt(1)         );

			//System.out.println();
			connto.commit();
			connfrom.commit();

		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());

			try 
			{
				connto.rollback();
				connfrom.rollback();
				pstmt.close();
				connto.close();
				connfrom.close();
			} 
			catch (SQLException e2) 
			{
			}
		}
	}

	/*** 부서 테이블의 끝 */


/////////////////////////////////////////////////////////////////////////////////////////////
    /** MEMBER */
    ArrayList recordss = new ArrayList();         /**CodeRecord 레코드를 담을 목록 */
    Vector records = new Vector();                  /** 레코드 목록 */
    Hashtable hash = new Hashtable();           /** 레코드 목록 검색을 위하여 */

    /** 레코드 목록에 전체 Record 추가 */
    public void addRecord(Record record){
            records.add(record);                                    /** 레코드 목록에 레코드를 전체 추가 : 특정 레코드의 인덱스 파악이 어려움   */
            hash.put( record.indept, record);                  /**  레코드 클래스의  키와 레코드 클래스를 배열로 검색 테이블에 추가하고
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


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** 단위 레코드 클래스이고  여기에 핸들링 할 정보가 저장됨   */
	public class Record{

		/** member */
		String key = null;
		String indept  = null;        // 내부 부서코드  KEY
		String crtyymmdd = null;
		String destructdd = null;
		String outdept = null;
		String deptnm = null;
		String sosok  = null;
		String tsosok =  null;
		String levelcd  =  null;
		String deptreducnm = null;
		String highpartdept  = null;
		String lowyn            =  null;
		String dirempno      = null;
		String wrkplc           =  null;
		String mprs             =  null;
		String t_flag             =  null;
		String senddt          =  null;

	    /** 생성자 */
	    public Record(){
	     }

	    public Record(

	    		String indept,
	    		String crtyymmdd,
	    		String destructdd,
	    		String outdept,
	    		String deptnm,
	    		String sosok ,
	    		String tsosok,
	    		String levelcd,
	    		String deptreducnm,
	    		String highpartdept,
	    		String lowyn,
	    		String dirempno,
	    		String wrkplc,
	    		String mprs,
	    		String t_flag,
	    		String senddt
	    ){
                this.setIndept(indept);
                this.setCrtyymmdd(crtyymmdd);
                this.setDestructdd(destructdd);
                this.setOutdept(outdept);
                this.setDeptnm(deptnm);
                this.setSosok(sosok);
                this.setTsosok(tsosok);
                this.setLevelcd(levelcd );
                this.setHighpartdept(highpartdept);
                this.setLowyn(lowyn);
                this.setDirempno(dirempno);
                this.setWrkplc(wrkplc);
                this.setMprs(mprs);
                this.setT_flag(t_flag);
                this.setSenddt(senddt);

	    }

        /** 생성자 */
        public Record(String key){
            this.key = key;
         }

        public String getValue(){
          return key;
        }

        public void setValue(String value){
           key = value;
        }

		/**
		 * @return crtyymmdd을 리턴합니다.
		 */
		public String getCrtyymmdd() {
			return crtyymmdd;
		}


		/**
		 * @param crtyymmdd 설정하려는 crtyymmdd입니다.
		 */
		public void setCrtyymmdd(String crtyymmdd) {
			this.crtyymmdd = crtyymmdd;
		}


		/**
		 * @return deptnm을 리턴합니다.
		 */
		public String getDeptnm() {
			return deptnm;
		}


		/**
		 * @param deptnm 설정하려는 deptnm입니다.
		 */
		public void setDeptnm(String deptnm) {
			this.deptnm = deptnm;
		}


		/**
		 * @return deptreducnm을 리턴합니다.
		 */
		public String getDeptreducnm() {
			return deptreducnm;
		}


		/**
		 * @param deptreducnm 설정하려는 deptreducnm입니다.
		 */
		public void setDeptreducnm(String deptreducnm) {
			this.deptreducnm = deptreducnm;
		}


		/**
		 * @return destructdd을 리턴합니다.
		 */
		public String getDestructdd() {
			return destructdd;
		}


		/**
		 * @param destructdd 설정하려는 destructdd입니다.
		 */
		public void setDestructdd(String destructdd) {
			this.destructdd = destructdd;
		}


		/**
		 * @return dirempno을 리턴합니다.
		 */
		public String getDirempno() {
			return dirempno;
		}


		/**
		 * @param dirempno 설정하려는 dirempno입니다.
		 */
		public void setDirempno(String dirempno) {
			this.dirempno = dirempno;
		}


		/**
		 * @return highpartdept을 리턴합니다.
		 */
		public String getHighpartdept() {
			return highpartdept;
		}


		/**
		 * @param highpartdept 설정하려는 highpartdept입니다.
		 */
		public void setHighpartdept(String highpartdept) {
			this.highpartdept = highpartdept;
		}


		/**
		 * @return indept을 리턴합니다.
		 */
		public String getIndept() {
			return indept;
		}


		/**
		 * @param indept 설정하려는 indept입니다.
		 */
		public void setIndept(String indept) {
			this.indept = indept;
		}


		/**
		 * @return key을 리턴합니다.
		 */
		public String getKey() {
			return key;
		}


		/**
		 * @param key 설정하려는 key입니다.
		 */
		public void setKey(String key) {
			this.key = key;
		}


		/**
		 * @return levelcd을 리턴합니다.
		 */
		public String getLevelcd() {
			return levelcd;
		}


		/**
		 * @param levelcd 설정하려는 levelcd입니다.
		 */
		public void setLevelcd(String levelcd) {
			this.levelcd = levelcd;
		}


		/**
		 * @return lowyn을 리턴합니다.
		 */
		public String getLowyn() {
			return lowyn;
		}


		/**
		 * @param lowyn 설정하려는 lowyn입니다.
		 */
		public void setLowyn(String lowyn) {
			this.lowyn = lowyn;
		}


		/**
		 * @return mprs을 리턴합니다.
		 */
		public String getMprs() {
			return mprs;
		}


		/**
		 * @param mprs 설정하려는 mprs입니다.
		 */
		public void setMprs(String mprs) {
			this.mprs = mprs;
		}


		/**
		 * @return outdept을 리턴합니다.
		 */
		public String getOutdept() {
			return outdept;
		}


		/**
		 * @param outdept 설정하려는 outdept입니다.
		 */
		public void setOutdept(String outdept) {
			this.outdept = outdept;
		}


		/**
		 * @return senddt을 리턴합니다.
		 */
		public String getSenddt() {
			return senddt;
		}


		/**
		 * @param senddt 설정하려는 senddt입니다.
		 */
		public void setSenddt(String senddt) {
			this.senddt = senddt;
		}


		/**
		 * @return sosok을 리턴합니다.
		 */
		public String getSosok() {
			return sosok;
		}


		/**
		 * @param sosok 설정하려는 sosok입니다.
		 */
		public void setSosok(String sosok) {
			this.sosok = sosok;
		}


		/**
		 * @return t_flag을 리턴합니다.
		 */
		public String getT_flag() {
			return t_flag;
		}


		/**
		 * @param t_flag 설정하려는 t_flag입니다.
		 */
		public void setT_flag(String t_flag) {
			this.t_flag = t_flag;
		}


		/**
		 * @return tsosok을 리턴합니다.
		 */
		public String getTsosok() {
			return tsosok;
		}


		/**
		 * @param tsosok 설정하려는 tsosok입니다.
		 */
		public void setTsosok(String tsosok) {
			this.tsosok = tsosok;
		}


		/**
		 * @return wrkplc을 리턴합니다.
		 */
		public String getWrkplc() {
			return wrkplc;
		}


		/**
		 * @param wrkplc 설정하려는 wrkplc입니다.
		 */
		public void setWrkplc(String wrkplc) {
			this.wrkplc = wrkplc;
		}
	}


}
