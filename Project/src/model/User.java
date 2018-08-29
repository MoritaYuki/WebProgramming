package model;

//JavaBeans作成条件（java.io.Serializableのインポートと実装）
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

	//インスタンスフィールド
	private int id;
	private String loginId;
	private String name;
	private String password;
	private Date birthDate;
	private String createDate;
	private String updateDate;

	//JavaBeans作成条件（引数を持たないコンストラクタ）
	public User(){

	}

	//ログインで検索を掛ける際に使用するコンストラクタ
	public User(String loginId, String name) {
		super();
		this.loginId = loginId;
		this.name = name;
	}

	//ユーザ一覧表示用のユーザデータ取得に使用するコンストラクタ
	public User(int id, String loginId, String name, Date birthDate, String password, String createDate,
			String updateDate) {
		super();
		this.id = id;
		this.loginId = loginId;
		this.name = name;
		this.password = password;
		this.birthDate = birthDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	//ゲッターとセッター
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}



}
