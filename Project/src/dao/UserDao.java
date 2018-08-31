package dao;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import model.User;

public class UserDao {

	//ログインIDとパスワードでwebpro内のレコードに検索をかける。
	public User findByLoginInfo(String loginId, String password) {
		//コネクション取得
		Connection conn = null;

		// ハッシュ生成前にバイト配列へ置き換える際のCharset
		Charset charset = StandardCharsets.UTF_8;

		// ハッシュ生成アルゴリズム
		String algorithm = "MD5";

		try {
			//ハッシュ生成処理
			byte[] bytes = MessageDigest.getInstance(algorithm).digest(password.getBytes(charset));
			String encPassword = DatatypeConverter.printHexBinary(bytes);

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
	        stmt.setString(2, encPassword);
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

		}catch(SQLException | NoSuchAlgorithmException e){
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

	// フォームからの情報を新規ユーザとして登録
	public int signUp(String loginId, String name, String birthday, String password) {
		//コネクション取得
		Connection conn = null;

		// ハッシュ生成前にバイト配列へ置き換える際のCharset
		Charset charset = StandardCharsets.UTF_8;

		// ハッシュ生成アルゴリズム
		String algorithm = "MD5";

		try {
			//ハッシュ生成処理
			byte[] bytes = MessageDigest.getInstance(algorithm).digest(password.getBytes(charset));
			String encPassword = DatatypeConverter.printHexBinary(bytes);

			//DBに接続
			conn = DBManager.getConnection();
			//SELECT文準備
			String column = "(login_id, name, birth_date, password, create_date, update_date)";
			String value = "(?, ?, ?, ?, now(), now())";

			String sql = "INSERT INTO user" + column + "VALUE" + value;
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
	        stmt.setString(2, name);
	        stmt.setString(3, birthday);
	        stmt.setString(4, encPassword);

	        // 追加したレコードの数を返す
	        return stmt.executeUpdate();

		}catch(SQLException | NoSuchAlgorithmException e){
			e.printStackTrace();
			return 0;
		}finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
	                return 0;
	            }
			}
		}
	}

	// 更新フォームからの情報を用いて、レコードを更新
	public int userUpdate(String loginId, String userName, String birthday, String password) {
		// コネクション取得
		Connection conn = null;

		// ハッシュ生成前にバイト配列へ置き換える際のCharset
		Charset charset = StandardCharsets.UTF_8;

		// ハッシュ生成アルゴリズム
		String algorithm = "MD5";

		try {
			//ハッシュ生成処理
			byte[] bytes = MessageDigest.getInstance(algorithm).digest(password.getBytes(charset));
			String encPassword = DatatypeConverter.printHexBinary(bytes);

			//DBに接続
			conn = DBManager.getConnection();
			//SELECT文準備
			String sql = "UPDATE user SET name = ?, birth_date = ?, password = ?, update_date = now() WHERE login_id = ?";

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
			stmt.setString(1, userName);
			stmt.setString(2, birthday);
			stmt.setString(3, encPassword);
			stmt.setString(4, loginId);

			// 更新したレコードの数を返す
			return stmt.executeUpdate();

		}catch(SQLException | NoSuchAlgorithmException e){
			e.printStackTrace();
			return 0;
		}finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				}catch (SQLException e) {
					e.printStackTrace();
					return 0;
				}
			}
		}
	}

	// ログインIDから検索してwebpro内の該当レコードを削除する
	public int userDelete(String loginId) {
		// コネクション取得
		Connection conn = null;
		try {
			//DBに接続
			conn = DBManager.getConnection();
			//SELECT文準備
			String sql = "DELETE FROM user WHERE login_id = ?";

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
	        // 削除したレコードの数を返す
	        return stmt.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
			return 0;
		}finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
	}

	// String型のstrが空白文字列や、nullでないかを判定
	public boolean strCheck(String str){
		if(str == null || str.isEmpty()) {
			return true;
		}
		return false;
	}

	// ログインIDの一意性、入力フォームの空欄、パスワードが確認用と一致しているかを判定
	public boolean formCheck(String loginId, String userName, String birthday, String password, String passwordRe) {

		// フラッグを定義
		boolean uniIdFg = false;
		boolean strFg = false;
		boolean passFg = false;

		// 同じログインIDがないか判定
		List<User> userList = findAll();
		for(User user: userList) {
			if(!strCheck(loginId) && user.getLoginId().equals(loginId)) {
				uniIdFg = true;
				break;
			}
		}

		// 入力フォームに空欄があるか(loginIDはログインIDの一意性を調べた際に確認済) 判定
		if(strCheck(password) || strCheck(passwordRe) || strCheck(userName) || strCheck(birthday)) {
			strFg = true;
		}

		// パスワードが確認用のものと一致しているか判定
		if(!password.equals(passwordRe)) {
			passFg = true;
		}

		// 条件に一つ以上当てはまる場合はtrueを返す
		if(uniIdFg || strFg || passFg){
			return true;
		}
		return false;
	}
}
