<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<jsp:useBean id="user" scope="session" class="usermanagement.UserInfo" />
<jsp:useBean id="message" scope="session" class="usermanagement.UserManagementMessage" />
	<%
		String id = user.getId();
		String password = user.getPassword();
		String name = user.getName();
		String phoneNumber = user.getPhoneNumber();
		String memo = user.getMemo();
	%>
<html>
<head>
<meta charset="UTF-8">
<title>User Management</title>
</head>
<body>
    <h1>User Management</h1>
    <form method="POST" action="UserDataHandler">
    	<table style="width: 100%;"  border="1">
     		<tr>
     			<th style="width: 20%; text-align: left;">ID</th>
    			<td style="width: 40%;"><jsp:getProperty name="user" property="id" /></td>
    			<td style="width: 40%;"><INPUT type="text" name="id" value="<%= id %>" readonly></td>
    		</tr>
    		<tr>
     			<th style="width: 20%; text-align: left;">Password</th>
    			<td style="width: 40%;"><jsp:getProperty name="user" property="password" /></td>
    			<td style="width: 40%;"><INPUT type="text" name="password" value="<%= password %>" maxlength="16"></td>
    		</tr>
    		<tr>
    			<th style="width: 20%; text-align: left;">Name</th>
    			<td style="width: 40%;"><jsp:getProperty name="user" property="name" /></td>
    			<td style="width: 40%;"><INPUT type="text" name="name"  value="<%= name %>" maxlength="32"></td>
    		</tr>
    		<tr>
     			<th style="width: 20%; text-align: left;">Phone Number</th>
    			<td style="width: 40%;"><jsp:getProperty name="user" property="phoneNumber" /></td>
    			<td style="width: 40%;"><INPUT type="text" name="phoneNumber" value="<%= phoneNumber %>" maxlength="16"></td>
    		</tr>
     		<tr>
    			<th style="width: 20%; text-align: left;">Memo</th>
    			<td style="width: 40%;"><jsp:getProperty name="user" property="memo" /></td>
    			<td style="width: 40%;"><INPUT type="text" name="memo" value="<%= memo %>" maxlength="256"></td>
    		</tr>
    	</table>
    	<INPUT type="submit" value="UPDATE">
    	<button type="button" onclick="location.href='./login.jsp'">BACK</button><br /><br />
    	<jsp:getProperty name="message" property="message" />
	</form>
</body>
</html>