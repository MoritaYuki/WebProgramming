package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;

public class UserDao {

	//ログインIDとパスワードでwebpro内のレコードに検索をかける。
	public User findByLoginInfo(String loginId, String password) {
		//コネクション取得
		Connection conn = null;
		try {
			//DBに接続
			conn = DBManager.getConnection();
			//SELECT文準備
			String sql = "SELECT * FROM user WHERE login_id = ? and password = ? ";

			/*
			 * ・createStatement()は入力した文章をそのままステートメントにするだけだから、最後の
			 * executeQuery()の引数にsql分を指定する。
			 * ・preparedStatement()を用いる場合は、最初に？で代入部分を置き換えてから、再度setメソッド
			 * にて代入する。代入した際にインスタンスstmtには既に代入後のsql分が保存されているため、最後
			 * のexecuteQuery()で引数を指定する必要はなし
			 */

			//ステートメントの準備
			PreparedStatement stmt = conn.prepareStatement(sql);
			//それぞれの入力項目を代入
	        stmt.setString(1, loginId);
	        stmt.setString(2, password);
	        //ステートメントの中身をResultSet型の変数に代入
	        ResultSet rs = stmt.executeQuery();

	        //検索したレコードは1件だけだから繰り返し文は不要
	        if(!rs.next()){
	        	return null;
	        }

	        //取得したIDとパスワードを返す
	        String loginIdData = rs.getString("login_id");
	        String nameData = rs.getString("name");

	        return new User(loginIdData, nameData);

		}catch(SQLException e){
			//例外時は値を取得しない
			e.printStackTrace();
            return null;
		}finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                  //例外時は値を取得しない
                    return null;
                }
            }
        }
	}

	//ユーザ一覧を表示するために管理者を除く全ユーザの情報を取得する
	public List<User> findAll() {
		Connection con = null;
		//ユーザ情報保管用のリストを準備
		List<User> userList = new ArrayList<User>();
		try {
			con = DBManager.getConnection();

			String sql = "SELECT * FROM user WHERE id != 1";

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			//取得したユーザデータの表から１レコードずつ値を取得して、リストに代入していく
			while (rs.next()) {
                int id = rs.getInt("id");
                String loginId = rs.getString("login_id");
                String name = rs.getString("name");
                Date birthDate = rs.getDate("birth_date");
                String password = rs.getString("password");
                String createDate = rs.getString("create_date");
                String updateDate = rs.getString("update_date");
                User user = new User(id, loginId, name, birthDate, password, createDate, updateDate);

                userList.add(user);
            }

		}catch(SQLException e) {
			e.printStackTrace();
            return null;
		}finally{
			// データベース切断
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
		}

		return userList;
	}
}
