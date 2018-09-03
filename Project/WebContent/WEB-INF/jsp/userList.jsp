<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="model.User" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="user_list.css">
</head>
<body>
	<div class="header">
		<a class="title-frase">ユーザ管理システム</a>
		<a class="logout" href="LogoutServlet">ログアウト</a>
		<p class="user_name">${userInfo.name}　さん</p>
	</div>
	<div class="user_list">
		<h1 class="title"> ユーザ一覧 </h1>
		<c:if test="${userInfo.loginId == 'admin'}">
			<a class="new_account" href="SignUpServlet">新規登録</a>
		</c:if>
		<form class="form" method="post" action="UserListServlet">
			<div class="txarea">
				<div class="form-group row">
				   <label for="inputId" class="col-sm-2 col-form-label">ログインID</label>
				   <div class="col-sm-10">
					   <input type="text" class="form-control" name="inputId" id="inputId">
				   </div>
				</div>
				<div class="form-group row">
				   <label for="inputUserName" class="col-sm-2 col-form-label">ユーザ名</label>
				   <div class="col-sm-10">
					   <input type="text" class="form-control" name="inputUserName" id="inputUserName">
				   </div>
				</div>
				<div class="form-group row">
				   <label for="inputBirthday" class="col-sm-2 col-form-label">生年月日</label>
				   <div class="col-sm-10">
					   <input type="text" class="form-control"  name="inputStartBirthday" id="inputBirthday" placeholder="年/月/日">
					   <a class="symbol">～</a>
					   <input type="text" class="form-control"  name="inputEndBirthday" id="inputBirthday" placeholder="年/月/日">
				   </div>
				</div>
			</div>
			<input class="search" type="submit" value="検索">
		</form>
		<div class="clear"></div>
	</div>

	<table class="table table-bordered">
	  <thead class="thead-light">
	    <tr>
	      <th scope="col">ログインID</th>
	      <th scope="col">ユーザ名</th>
	      <th scope="col">生年月日</th>
	      <th scope="col"></th>
	    </tr>
	  </thead>
	  <tbody>

	  	<%-- 管理者ログイン時は全てのボタンを表示 --%>
	  	<c:if test="${userInfo.loginId == 'admin'}">
		  <c:forEach var="user" items="${userList}">
			    <tr>
			      <th scope="row">${user.loginId}</th>
			      <td>${user.name}</td>
			      <td>${user.birthDate}</td>
			      <td class="btn-box">
				  	<a type="button" class="btn btn-primary" href="UserDetailServlet?id=${user.id}">詳細</a>
					<a type="button" class="btn btn-success" href="UserUpdateServlet?loginId=${user.loginId}">更新</a>
				    <a type="button" class="btn btn-warning" href="UserDeleteServlet?loginId=${user.loginId}">削除</a>
				  </td>
			    </tr>
		   </c:forEach>
		</c:if>

		<%-- 通常ユーザの場合は詳細のみ全て表示し、更新と削除は自分の分のみ表示 --%>
		<c:if test="${userInfo.loginId != 'admin'}">
		  <c:forEach var="user" items="${userList}">
			    <tr>
			      <th scope="row">${user.loginId}</th>
			      <td>${user.name}</td>
			      <td>${user.birthDate}</td>
			      <td class="btn-box">
				  	<a type="button" class="btn btn-primary" href="UserDetailServlet?id=${user.id}">詳細</a>
				  	<c:if test="${user.loginId == userInfo.loginId}">
						<a type="button" class="btn btn-success" href="UserUpdateServlet?loginId=${user.loginId}">更新</a>
				  	</c:if>
			      </td>
			    </tr>
		   </c:forEach>
		</c:if>
	  </tbody>
	</table>
</body>
</html>