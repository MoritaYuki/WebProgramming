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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//ログインセッションがある場合、ユーザ一覧画面にリダイレクトさせる。
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("userInfo");
		if(!(u == null)) {
			response.sendRedirect("UserListServlet");
			return;
		}

		//login.jspにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを指定
        request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの入力項目を取得
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

        //UserDaoに引数を渡して、DB(sm_db)上に合致するものがあるかどうかを評価
        User user = new UserDao().findByLoginInfo(loginId, password);

        //合致するものがなかった場合
        if(user == null) { //←.equals()は使わない。userがnullの場合はnullインスタンスのメソッドを使用してぬるぽが起こるため
        	//エラーメッセージをリクエストスコープに保管
        	request.setAttribute("errorMessage", "ログインIDまたはパスワードが異なります");

        	//login.jspにフォワード
        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        //合致するものがあった場合はユーザの情報をセッションスコープに保管して
        //ユーザ一覧へ遷移
        request.getSession().setAttribute("userInfo", user);
        response.sendRedirect("UserListServlet");
	}

}
