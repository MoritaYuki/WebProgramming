<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="login.css">
</head>
<body>
	<div class="login_menu">
		<header>
			<h3>ユーザ管理システム</h3>
		</header>
		<h1 class="title"> ログイン画面 </h1>
		<div class="error">
			<c:if test="${errorMessage != null}" >
				${errorMessage}
			</c:if>
		</div>

		<form class="form" method="post" action="LoginServlet">
			<div class="input">
				<a>ログインID　　</a>
				<input type="text" name="loginId"><br>
			</div>
			<div class="input">
				<a>パスワード　　</a>
				<input class="pass" type="password" name="password"><br>
			</div>

			<input class="login" type="submit" value="ログイン">
		</form>
	</div>
</html>