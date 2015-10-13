<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<%@include file="../layout/taglib.jsp"%>

<title>Code Verification JSP</title>


<style>
.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}

.form-signin .form-signin-heading, .form-signin .checkbox {
  margin-bottom: 10px;
}

.form-signin .checkbox {
  font-weight: normal;
}

.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}

.form-signin .form-control:focus {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

h1{
	color:red;
}

h4{
  color:blue;
}
</style>




<h1>Error: </h1>
<h3>${error}</h3>

<br /><br />

<form method="post" action="/java-blog-website/ErrorController/Reset">
    <h4>Click the button below to Reset Two Factor Authentication </h4> 
    <input class="btn btn-lg btn-primary " type="submit" value="Reset">
</form>