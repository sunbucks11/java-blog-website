<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%@ page import ="java.util.*" %>
<%-- 	<%@page import="java.io.ByteArrayOutputStream"%>
	<%@page import="net.glxn.qrgen.QRCode" %>
	<%@page import="net.glxn.qrgen.image.ImageType" %>
	<%@page import="home.test.googauth.GoogleAuthenticator" %>
	<%@page import="java.io.OutputStream;" %> --%>


<%@include file="../layout/taglib.jsp"%>

<style>
#mask {
	position: absolute;
	left: 0;
	top: 0;
	z-index: 9000;
	background-color: #000;
	display: none;
}

#boxes .window {
	position: absolute;
	left: 0;
	top: 0;
	width: 440px;
	height: 200px;
	display: none;
	z-index: 9999;
	padding: 20px;
	border-radius: 15px;
	text-align: center;
}

#boxes #dialog {
	width: 750px;
	height: 300px;
	padding: 10px;
	background-color: #ffffff;
	font-family: 'Segoe UI Light', sans-serif;
	font-size: 15pt;
}

#popupfoot {
	font-size: 16pt;
	position: absolute;
	bottom: 0px;
	width: 250px;
	left: 250px;
}
</style>



<script type="text/javascript">
	$(document).ready(function() {

		var id = '#dialog';

		//Get the screen height and width
		var maskHeight = $(document).height();
		var maskWidth = $(window).width();

		//Set heigth and width to mask to fill up the whole screen
		$('#mask').css({
			'width' : maskWidth,
			'height' : maskHeight
		});

		//transition effect
		$('#mask').fadeIn(500);
		$('#mask').fadeTo("slow", 0.9);

		//Get the window height and width
		var winH = $(window).height();
		var winW = $(window).width();

		//Set the popup window to center
		$(id).css('top', winH / 2 - $(id).height() / 2);
		$(id).css('left', winW / 2 - $(id).width() / 2);

		//transition effect
		$(id).fadeIn(2000);

		//if close button is clicked
		$('.window .close').click(function(e) {
			//Cancel the link behavior
			e.preventDefault();

			$('#mask').hide();
			$('.window').hide();
		});

		//if mask is clicked
		$('#mask').click(function() {
			$(this).hide();
			$('.window').hide();
		});

	});
</script>



<div id="boxes">


	<div id="dialog" class="window">
		Authentication Barcode
		
		<br />


	        <%
	        
	        // Set refresh, autoload time as 5 seconds
	        response.setIntHeader("Refresh", 5);
	        // Get current time
	        Calendar calendar = new GregorianCalendar();
	        String am_pm;
	        int hour = calendar.get(Calendar.HOUR);
	        int minute = calendar.get(Calendar.MINUTE);
	        int second = calendar.get(Calendar.SECOND);
	        if(calendar.get(Calendar.AM_PM) == 0)
	           am_pm = "AM";
	        else
	           am_pm = "PM";
	        String CT = hour+":"+ minute +":"+ second +" "+ am_pm;
	        out.println("Current Time is: " + CT + "\n");
	        
	        
	        
	        
	        //byte[] imageBytes = new byte[1024];
	        //response.getOutputStream().write(imageBytes);
	        //response.getOutputStream().flush();
		
	        //response.getOutputStream();
				        
	  
	        
	        
	     %>

      <img src="images${response.getOutputStream()}.png" />

<%--  		<form class="form-signin" role="form" action="<spring:url value="AuthController" />" method="POST">
				<input type="text" name="j_username" class="form-control" placeholder="User Name" required autofocus>
				<input type="password" name="j_password" class="form-control" placeholder="Password" required>
			 	<input type="checkbox" name="setup" value="true" checked="checked" hidden="true"> <br/>

 				<input class="btn btn-danger" type="submit" value="Generate Code"> 
		</form>
 --%>















		<div id="popupfoot">
			<!--        <a href="#" class="close agree">I agree</a> | 
	   <a class="close agree"style="color:red;" href="#">I do not agree</a> 	     -->
	   
		</div>
	</div>
	<div id="mask"></div>

</div>







	<div id="dialog" class="window">
		Your Content Goes Here
		<div class="modal fade" id="modalRemove" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Authentication</h4>
					</div>
					<div class="modal-body">

						<form class="form-signin" role="form"
							action="<spring:url value="AuthController" />" method="POST">
							<input type="text" name="j_username" class="form-control"
								placeholder="User Name" required autofocus> <input
								type="password" name="j_password" class="form-control"
								placeholder="Password" required> <input type="checkbox"
								name="setup" value="true" checked="checked" hidden="true">
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
		</div>
	</div>