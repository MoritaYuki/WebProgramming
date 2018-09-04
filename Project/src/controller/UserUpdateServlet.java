package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.User;

/**
 * Servlet implementation class UserUpdateServlet
 */
@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログインセッションがない場合、ログイン画面にリダイレクトさせる
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("userInfo");
		if(u == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		// リクエストパラメータの文字コードを指定
        request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの入力項目を取得
		String loginId = request.getParameter("loginId");
        session.setAttribute("loginId", loginId);

		// 取得したログインIDと一致するユーザ情報をリクエストスコープに保存
        // パスワードは更新フォームで入力がされなかった際に使用するため、セッションスコープに保存
		List<User> userList = new UserDao().findAll();
		for(User user: userList) {
			if(user.getLoginId().equals(loginId)) {
				request.setAttribute("userName", user.getName());
				request.setAttribute("birthday", user.getBirthDate());
				session.setAttribute("password", user.getPassword());
				break;
			}
		}

		// ユーザ情報更新のjspにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 全体で使用するインスタンスの生成
		UserDao userDao = new UserDao();
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("loginId");

		// リクエストパラメータの文字コードを指定
        request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの入力項目を取得
		String password = request.getParameter("inputPassword");
		String passwordRe = request.getParameter("inputPassword_re");
		String userName = request.getParameter("inputUserName");
		String birthday = request.getParameter("inputBirthday");

		// ユーザ名と生年月日が空欄 or パスワードが不一致の場合
		if(userDao.strCheck(userName) || userDao.strCheck(birthday) ||
				(!userDao.strCheck(passwordRe) && !password.equals(passwordRe))) {

			//エラーメッセージをリクエストスコープに保管
        	request.setAttribute("errMsg", "入力された内容は正しくありません");

        	//フォームの入力内容をリクエストスコープに保存
        	request.setAttribute("userName", userName);
        	request.setAttribute("birthday", birthday);

        	//userUpdate.jspにフォワード
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
            dispatcher.forward(request, response);
            return;
		}

		// 更新フォームにパスワードが入力されなかった際は、更新前のパスワードを代入
		// 再度、userUpdateメソッドで暗号しないようにFgを設定
		int passFg = 0;

		if(userDao.strCheck(password)) {
			password = (String) session.getAttribute("password");
			passFg = 1;
		}

        // updateメソッドを使って、DB上に入力された情報を更新
        int updateNum = userDao.userUpdate(loginId, userName, birthday, password, passFg);
        // 確認用
        System.out.println(updateNum);

        // 更新が成功した場合はユーザ一覧へリダイレクト
        response.sendRedirect("UserListServlet");

		// セッションスコープのloginIdを削除
		session.removeAttribute("loginId");
	}

}
