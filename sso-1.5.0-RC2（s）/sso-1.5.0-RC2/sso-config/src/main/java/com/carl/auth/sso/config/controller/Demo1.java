package com.carl.auth.sso.config.controller;


import javax.naming.spi.DirStateFactory;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.SimpleFormatter;


public class Demo1 {

    //数据库地址
    private static String dbUrl="jdbc:mysql://localhost:3306/sso";
    private static String dbUrl1="jdbc:mysql://localhost:3306/sso?characterEncoding=utf8&useSSL=false";
    //用户名
    private static String dbUserName="root";
    //密码
    private static String dbPassword="root";
    //驱动名称
    private static String jdbcName = "com.mysql.jdbc.Driver";

    public static Boolean changPassword(String id,String passWord) {
        Boolean rb=true;
        try {
            Class.forName(jdbcName);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            rb=false;
        }

        Connection con = null;
        try {
            //获取数据库连接
            con = DriverManager.getConnection(dbUrl1, dbUserName, dbPassword);

            String sql = "UPDATE sys_user SET password='"+passWord+"' where id='"+id+"'";
            PreparedStatement pstmt = null;
            try {
                pstmt=con.prepareCall(sql);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                rb=false;
            }

            } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            rb=false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
                rb=false;
            }
        }
        return rb;
    }




    public static boolean savePassWord(String userName,String passWord,String email){
        try {
            Class.forName(jdbcName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        Connection con = null;
        try {
            //获取数据库连接
            con = DriverManager.getConnection(dbUrl1, dbUserName, dbPassword);
            String uuid=UUID.randomUUID().toString().replaceAll("-","");
            SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //HH表示24小时制；
            String formatDate = dFormat.format(new Date());
            String sql="INSERT INTO `sys_user` VALUES ('"+uuid+"', '"+userName+"', '"+passWord+"', '"+formatDate+"', '0', '0', '"+email+"', null);";
            PreparedStatement pstmt = null;
            try {
                pstmt=con.prepareCall(sql);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
                return false;
            }
        }


        return true;

    }


    public static boolean checkEmali(String email){
        Boolean rb=true;
        try {
            Class.forName(jdbcName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        Connection con = null;

        try {
            //获取数据库连接
            con = DriverManager.getConnection(dbUrl1, dbUserName, dbPassword);
            String sql="select * from sys_user where email='"+email+"'";
            PreparedStatement pstmt = null;
            try {
                pstmt=con.prepareCall(sql);
                ResultSet resultSet= pstmt.executeQuery();
                if(resultSet.next()){
                    rb= false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                rb= false;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            rb= false;
        }finally{
            try {
                con.close();

            } catch (SQLException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
                rb= false;
            }
        }
           return rb;
    }


    public static boolean checkUserName(String userNamel){
        Boolean rb=true;
        try {
            Class.forName(jdbcName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        Connection con = null;

        try {
            //获取数据库连接
            con = DriverManager.getConnection(dbUrl1, dbUserName, dbPassword);
            String sql="select * from sys_user where username='"+userNamel+"'";
            PreparedStatement pstmt = null;
            try {
                pstmt=con.prepareCall(sql);
                ResultSet resultSet= pstmt.executeQuery();
                if(resultSet.next()){
                    rb= false;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                rb= false;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            rb= false;
        }finally{
            try {
                con.close();

            } catch (SQLException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
                rb= false;
            }
        }

        return rb;

    }





}