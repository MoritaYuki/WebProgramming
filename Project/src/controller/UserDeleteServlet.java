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
 * Servlet implementation class UserDeletservlete
 */
@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDeleteServlet() {
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

		// ユーザ情報削除を確認後の処理
		// リクエストスコープの"delFg"に値がある場合のみユーザ情報を削除する
		if(request.getParameter("delFg") != null) {
			String loginId = request.getParameter("loginId");
			System.out.println(loginId);

			// ユーザ情報を削除して、削除したレコード数をリクエストスコープに保存
			int deleteNum = new UserDao().userDelete(loginId);
			// 確認用
			System.out.println(deleteNum);

			// ユーザ一覧画面へ遷移
			response.sendRedirect("UserListServlet");
			return;
		}

		// URL上の"id"をリクエストスコープに取得
		String loginId = request.getParameter("loginId");
		request.setAttribute("loginId", loginId);

		// userDelete.jspにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userDelete.jsp");
        dispatcher.forward(request, response);
	}
}

