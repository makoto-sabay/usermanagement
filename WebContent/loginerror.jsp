<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<jsp:useBean id="message" scope="session" class="usermanagement.UserManagementMessage" />

<html>
<head>
<meta charset="UTF-8">
<title>Login Error</title>
</head>
<body>
	<h1>Login Error</h1><br />
	<jsp:getProperty name="message" property="message" />
</body>
</html>