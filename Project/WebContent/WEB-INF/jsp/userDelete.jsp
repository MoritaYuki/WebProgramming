<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="user_delete.css">
</head>
<body>
	<div class="header">
		<a class="title-frase">ユーザ管理システム</a>
		<a class="logout" href="LogoutServlet">ログアウト</a>
		<a class="user_name">${userInfo.name}　さん</a>
	</div>
	<div class="user_list">
		<h1 class="title"> ユーザ削除確認 </h1>
		<p class="del-message">ログインID：${loginId}<br>
		   を本当に削除してよろしいでしょうか。</p>
		<div class= "subm">
			<a href="UserListServlet" class="search btn btn-secondary btn-lg"> OK </a>
			<a href="UserDeleteServlet?delFg=1&loginId=${loginId}" class="ok search btn btn-secondary btn-lg"> キャンセル </a>
		</div>
	</div>
</body>
</html>