<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				${pageContext.request.userPrincipal.name} | <a href="javascript:document.getElementById('logout').submit()">Logout</a>
			</c:if>
			<c:url value="/logout" var="logoutUrl" />
			<form id="logout" action="${logoutUrl}" method="post" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
		<div class="container">
			  <spring:url value="/employee/insert" var="saveURL" />
			  <h2>Employee</h2>
			  <form:form modelAttribute="employeeForm" method="post" action="${saveURL }" cssClass="form" >
				  <form:hidden path="id"/>
				   <div class="form-group">
				    	<label>Name</label>
				    	<form:input path="name" cssClass="form-control" id="name" />
				   </div>
				   <div class="form-group">
				    		<label>Address</label>
				    <form:input path="address" cssClass="form-control" id="address" />
				   </div>
				   <div class="form-group">
				    	<label>Phone</label>
				    	<form:input path="phone" cssClass="form-control" id="phone" />
				   </div>
				   <button type="submit" class="btn btn-primary">Save</button>
			  </form:form>
		  </div>
	  	  <br/><br/>
	
		 <div class="container">
		  	<h2>Employee List</h2>
			  <table class="table table-striped">
				   <thead>
					    <th scope="row">ID</th>
					    <th scope="row">Name</th>
					    <th scope="row">Address</th>
					    <th scope="row">Phone</th>
					    <th scope="row">Update</th>
					    <th scope="row">Delete</th>
				   </thead>
				   <tbody>
					    <c:forEach items="${employees }" var="employee" >
						     <tr>
							      <td>${employee.id }</td>
							      <td>${employee.name }</td>
							      <td>${employee.address }</td>
							      <td>${employee.phone }</td>
							      <td>
							       	<spring:url value="/employee/update/${employee.id }" var="updateURL" />
							       	<a class="btn btn-primary" href="${updateURL }" role="button" >Update</a>
							      </td>
							      <td>
							       	<spring:url value="/employee/delete/${employee.id }" var="deleteURL" />
							       	<a class="btn btn-primary" href="${deleteURL }" role="button" >Delete</a>
							      </td>
						     </tr>
					    </c:forEach>
				   </tbody>
			  </table>
		  	  <spring:url value="/employee/insertPage/" var="addURL" />
		  	  <a class="btn btn-primary" href="${addURL}" role="button" >Add New Employee </a>
		 </div>
	</body>
</html>