<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../layout/taglib.jsp"%>

<title>Code Verification JSP</title>
<h1>Code Verification</h1>

<form action="VerifyController" method="post">
			<label>Enter Your Key</label>
			<input type="text" name="code">
			<input type="submit" value="submit">
</form>