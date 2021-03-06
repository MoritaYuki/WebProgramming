<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="user_info.css">
</head>
<body>
	<div class="header">
		<a class="title-frase">ユーザ管理システム</a>
		<a class="logout" href="LogoutServlet">ログアウト</a>
		<p class="user_name">${userInfo.name}　さん</p>
	</div>
	<div class="user_list">
		<h1 class="title"> ユーザ情報詳細参照 </h1>
		<div class="container">
		  <div class="row">
		    <div class="col">
		      <a class="label">ログインID</a>
		    </div>
		    <div class="col">
		      <a>${getUserInfo.loginId}</a>
		    </div>
		  </div>
		  <div class="row">
		    <div class="col">
		      <a class="label">ユーザ名</a>
		    </div>
		    <div class="col">
		      <a>${getUserInfo.name}</a>
		    </div>
		  </div>
		  <div class="row">
		    <div class="col">
		      <a class="label">生年月日</a>
		    </div>
		    <div class="col">
		      <a>${getUserInfo.birthDate}</a>
		    </div>
		  </div>
		  <div class="row">
		    <div class="col">
		      <a class="label">登録日時</a>
		    </div>
		    <div class="col">
		      <a>${getUserInfo.createDate}</a>
		    </div>
		  </div>
		  <div class="row">
		    <div class="col">
		      <a class="label">更新日時</a>
		    </div>
		    <div class="col">
		      <a>${getUserInfo.updateDate}</a>
		    </div>
		  </div>
		</div>
		<a class="back" href="UserListServlet">戻る</a>
	</div>
</body>
</html>