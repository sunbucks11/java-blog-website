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


/* .button-link {
    padding: 10px 15px;
    background: #D61111;
    color: #FFF;
}
  
  
  
  .button-link {
    padding: 10px 100px;
    background: #D61111;
    color: #FFF;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    border: solid 1px #D61111;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.4);
    -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.4), 0 1px 1px rgba(0, 0, 0, 0.2);
    -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.4), 0 1px 1px rgba(0, 0, 0, 0.2);
    box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.4), 0 1px 1px rgba(0, 0, 0, 0.2);
    -webkit-transition-duration: 0.2s;
    -moz-transition-duration: 0.2s;
    transition-duration: 0.2s;
    -webkit-user-select:none;
    -moz-user-select:none;
    -ms-user-select:none;
    user-select:none;
}

.button-link:hover {
    background: #4479BA;
    border: solid 1px #2A4E77;
    text-decoration: none;
}
.button-link:active {
    -webkit-box-shadow: inset 0 1px 4px rgba(0, 0, 0, 0.6);
    -moz-box-shadow: inset 0 1px 4px rgba(0, 0, 0, 0.6);
    box-shadow: inset 0 1px 4px rgba(0, 0, 0, 0.6);
    background: #2E5481;
    border: solid 1px #D61111;
} */

</style>


<form class="form-signin" action="VerifyController" method="post">
  <h1>Verification Code</h1> <br />
<!--   <label>Enter Your Key</label> <input type="text" name="code" class="form-control" placeholder="Verification Code" required><br /> --> 
   <label>Enter Your Key</label>
    <input type="text" name="code" class="form-control" placeholder="Verification Code"><br /> 
    <input class="btn btn-lg btn-primary btn-block" type="submit" name="button" value="Verify">
    
    

   <input class="btn btn-lg btn-danger  btn-block" type="submit" name="exit-verification" value="Exit Verification"> 
   <!--  <input class="button-link" type="submit" value="Exit Verification"> -->

</form>  

