package com.pkms.batch.sktdb.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SktDBCommon {

	   /**테이블 체크 */
    public static boolean checkTable(Connection connto, String tblName)
    {
        PreparedStatement pstmt= null;
        ResultSet rs = null;

        try {
            pstmt = connto.prepareStatement("SELECT * from " + tblName);
            rs = pstmt.executeQuery();
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /** ORACLE DB CONNECTION  원본  */
    public static synchronized Connection getDBFromConnection(String fromCon, String fromId, String fromPw) throws Exception{
         Class.forName( "oracle.jdbc.driver.OracleDriver");
         //인사DB
         return DriverManager.getConnection(fromCon, fromId, fromPw);
         //return DriverManager.getConnection("jdbc:oracle:thin:@150.204.15.91:1521:mrcs","pamas","pamas0");
         //return DriverManager.getConnection("jdbc:oracle:thin:@150.23.11.19:1521:ORCL","pamas","pamas0");
    }

    /** ORACLE DB CONNECTION  대상   */
    public static synchronized Connection getDBToConnection(String toCon, String toId, String toPw) throws Exception{
         /**  운영계
         Class.forName( "oracle.jdbc.driver.OracleDriver");
         return DriverManager.getConnection("jdbc:oracle:thin:@150.204.15.91:1521:mrcs","pamas","pamas0");
         return ConnMgr.getConnection();
         */
        Class.forName( "oracle.jdbc.driver.OracleDriver");
        // 운영계
        return DriverManager.getConnection(toCon, toId, toPw);
        // 개발계
        //return DriverManager.getConnection("jdbc:oracle:thin:@150.23.11.19:1521:ORCL","pamas","pamas0");
        // 테스트계
        //return DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:PAMAS","pamas","pamas0");
    }

}
