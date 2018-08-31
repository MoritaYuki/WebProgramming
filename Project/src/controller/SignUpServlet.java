package controller;

import java.io.IOException;

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
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
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

		// signUp.jspにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/signUp.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserDao userDao = new UserDao();

		// リクエストパラメータの文字コードを指定
        request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの入力項目を取得
		String loginId = request.getParameter("inputId");
		String password = request.getParameter("inputPassword");
		String passwordRe = request.getParameter("inputPassword_re");
		String userName = request.getParameter("inputUserName");
		String birthday = request.getParameter("inputBirthday");

		// 入力フォームに不備がある場合は再度入力フォームに戻る
		if(userDao.formCheck(loginId, userName, birthday, password, passwordRe)) {

			//エラーメッセージをリクエストスコープに保管
        	request.setAttribute("errMsg", "入力された内容は正しくありません");

        	//フォームの入力内容をリクエストスコープに保存
        	request.setAttribute("loginId", loginId);
        	request.setAttribute("userName", userName);
        	request.setAttribute("birthday", birthday);

        	//signUp.jspにフォワード
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/signUp.jsp");
            dispatcher.forward(request, response);
            return;
		}

        // signUpメソッドを使って、DB上に入力された情報を登録
        int insertNum = userDao.signUp(loginId, userName, birthday, password);
        // 確認用
        System.out.println(insertNum);

        // 登録が成功した場合はユーザ一覧へリダイレクト
        response.sendRedirect("UserListServlet");
	}

}
