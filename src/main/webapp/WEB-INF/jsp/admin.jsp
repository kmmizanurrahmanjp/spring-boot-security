<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Spring Boot JSP CRUD</title>
		<link href="../../webjars/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" />
		 <script src="../../webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
		 <script src="../../webjars/jquery/3.0.0/js/jquery.min.js"></script>
	</head>
	<body>
		<div class="container">
			<h1>Admin</h1>
			<div>
				<c:if test="${pageContext.request.userPrincipal.name != null}">
					${pageContext.request.userPrincipal.name} | <a href="javascript:document.getElementById('logout').submit()">Logout</a>
				</c:if>
				<c:url value="/logout" var="logoutUrl" />
				<form id="logout" action="${logoutUrl}" method="post" >
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
			</div>
		</div>
	</body>
</html>