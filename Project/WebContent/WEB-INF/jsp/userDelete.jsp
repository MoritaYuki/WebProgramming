<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="user_delete.css">
</head>
<body>
	<div class="header">
		<a class="title-frase">ユーザ管理システム</a>
		<a class="logout" href="LogoutServlet">ログアウト</a>
		<p class="user_name">${userInfo.name}　さん</p>
	</div>
	<div class="user_list">
		<h1 class="title"> ユーザ削除確認 </h1>
		<p class="del-message">ログインID：${loginId}<br>
		   を本当に削除してよろしいでしょうか。</p>
		<div class= "subm">
			<a class="search" href="UserListServlet"> キャンセル </a>
			<a class="search ok" href="UserDeleteServlet?delFg=1&loginId=${loginId}"> OK </a>
		</div>
	</div>
</body>
</html>