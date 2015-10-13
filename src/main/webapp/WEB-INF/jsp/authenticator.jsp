<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%@include file="../layout/taglib.jsp"%>

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
</style>



<script type="text/javascript">
$(document).ready(function(){
	$('.nav-tabs a:first').tab('show'); // Select first tab
	$(".triggerRemove").click(function(e){
		e.preventDefault();
		$("#modalRemove .removeBtn").attr("href", $(this).attr("href"));
		$("#modalRemove").modal();
	});
});
</script>



<form class="form-signin" role="form" action="<spring:url value="AuthController" />" method="POST">
	<input type="text" name="j_username" class="form-control" placeholder="User Name" required autofocus>
	<input type="password" name="j_password" class="form-control" placeholder="Password" required>
 	<input type="checkbox" name="setup" value="true" checked="checked">
 	<input class="btn btn-lg btn-primary btn-block" type="submit" value="Sign In">
</form>


				<p>
				<a href="<spring:url value=""/>" class="btn btn-danger triggerRemove">Authentication Code</a>		
				</p>


<!-- Modal -->
<div class="modal fade" id="modalRemove" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Authentication</h4>
      </div>
      <div class="modal-body">
 		
 		<form class="form-signin" role="form" action="<spring:url value="AuthController" />" method="POST">
				<input type="text" name="j_username" class="form-control" placeholder="User Name" required autofocus>
				<input type="password" name="j_password" class="form-control" placeholder="Password" required>
			 	<input type="checkbox" name="setup" value="true" checked="checked" hidden="true">
 				<!-- <input class="btn btn-lg btn-primary btn-block" type="submit" value="Sign In">  -->
 				<input class="btn btn-danger" type="submit" value="Generate Code"> 
			 	<!--  <a href="" class="btn btn-danger removeBtn">Generate Code</a> -->
		</form>
		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
   <!--      <a href="" class="btn btn-danger removeBtn">Generate Code</a> -->
      </div>
    </div>
  </div>
</div> <!-- /Modal -->

 --%>