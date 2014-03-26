<%@page import="org.sogeti.bo.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="java.util.List" %>
<%@ page import="org.sogeti.bo.UserBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TODO</title>
</head>
<body>
 		<%
            List<UserBean> users = (List<UserBean>) request.getAttribute("users");
            for (UserBean user : users) {
        %>
        <p>
            <strong><%= user.getName() %></strong> - <%= user.getDescription() %>
        </p>
        <%
			}
		%>
</body>
</html>