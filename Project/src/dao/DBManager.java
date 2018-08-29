package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	//Timezoneの設定を忘れずに！！
	private static String url = "jdbc:mysql://localhost:3306/webpro?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
	private static String user = "root";
	private static String pass = "password";

	//DBへ接続するコネクションを返す

	public static Connection getConnection(){
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
		}catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
			return con;
	}
}
