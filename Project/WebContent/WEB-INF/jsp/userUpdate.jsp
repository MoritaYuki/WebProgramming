<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="user_update.css">
</head>
<body>
	<div class="header">
		<a class="title-frase">ユーザ管理システム</a>
		<a class="logout" href="LogoutServlet">ログアウト</a>
		<p class="user_name">${userInfo.name}　さん</p>
	</div>
	<div class="user_list">
		<h1 class="title"> ユーザ情報更新 </h1>
		<div class="error">
			<c:if test="${errMsg != null}" >
				${errMsg}
			</c:if>
		</div>
		<form class="form" method="post" action="UserUpdateServlet">
			<div class="txarea">
				<div class="form-group row">
				   <label class="col-sm-2 col-form-label">ログインID</label>
				   <div class="col-sm-10 ">
					   <p class= "id"> 　　　　　　　　　${loginId} </p>
				   </div>
				</div>
				<div class="form-group row">
				   <label for="inputPassword" class="col-sm-2 col-form-label">パスワード</label>
				   <div class="col-sm-10 ">
					   <input type="password" class="form-control" name="inputPassword" id="inputPassword">
				   </div>
				</div>
				<div class="form-group row">
				   <label for="inputPassword_re" class="col-sm-4 col-form-label">パスワード(確認)</label>
				   <div class="col-sm-8">
					   <input type="password" class="form-control" name="inputPassword_re" id="inputPassword_re">
				   </div>
				</div>
				<div class="form-group row">
				   <label for="inputUserName" class="col-sm-2 col-form-label">ユーザ名</label>
				   <div class="col-sm-10">
					   <input type="text" class="form-control" value="${userName}" name="inputUserName" id="inputUserName">
				   </div>
				</div>
				<div class="form-group row">
				   <label for="inputBirthday" class="col-sm-2 col-form-label">生年月日</label>
				   <div class="col-sm-10">
					   <input type="text" class="form-control" value="${birthday}"  name="inputBirthday" id="inputBirthday">
				   </div>
				</div>
			</div>
			<input class="search" type="submit" value="更新">
		</form>
		<a class="back" href="UserListServlet">戻る</a>
	</div>

</body>
</html>