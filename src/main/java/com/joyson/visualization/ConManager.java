package com.joyson.visualization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by L on 2018/7/13.
 */
public class ConManager {
    final static String cfn = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    final static String url = "jdbc:sqlserver://60.28.150.163:11433;DatabaseName=TSData";

              public static void main(String[] args) {
                 Connection con = null;
                 PreparedStatement statement = null;
                 ResultSet res = null;
                 try {
                         Class.forName(cfn);
                         con = DriverManager.getConnection(url,"sa","4M123456!");

                         String sql = "select * from Role";//查询test表
                         statement = con.prepareStatement(sql);
                         res = statement.executeQuery();
                         while(res.next()){
                                 String title = res.getString("id");//获取test_name列的元素                                                                                                                                                    ;
                                 System.out.println("姓名："+title);
                             }

                     } catch (Exception e) {
                         // TODO: handle exception
                         e.printStackTrace();
                     }finally{
                         try {
                                 if(res != null) res.close();
                                 if(statement != null) statement.close();
                                 if(con != null) con.close();
                             } catch (Exception e2) {
                                 // TODO: handle exception
                                 e2.printStackTrace();
                             }
                     }
             }
}
