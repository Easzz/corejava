package com.easzz.corejava.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by easzz on 2017/9/5 9:16
 */
public class SqlServerConn {
	public static void main(String[] args) {
	//	String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String driverName="com.mysql.jdbc.Driver";
		//String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=qxrh";
		String dbURL = "jdbc:mysql://localhost:3306/iskyshop";
		String userName = "root";
		String userPwd = "123456";
		try {
			Class.forName(driverName);
			Connection dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			PreparedStatement preparedStatement = dbConn.prepareStatement("SELECT * FROM iskyshop_accessory ");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				String string = resultSet.getString(2);
				System.out.println(string);
			}
			System.out.println("连接数据库成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("连接失败");
		}
	}
}
