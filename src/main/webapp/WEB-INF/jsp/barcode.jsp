<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%@ page import ="java.util.*" %>
	<%-- <%@page import="java.io.ByteArrayOutputStream"%>  --%> 
<%-- 	<%@page import="net.glxn.qrgen.QRCode" %>
	<%@page import="net.glxn.qrgen.image.ImageType" %>
	<%@page import="home.test.googauth.GoogleAuthenticator" %> --%>
 <%-- 	<%@page import="java.io.OutputStream;" %>  --%>


<%@include file="../layout/taglib.jsp"%>

<style>
#mask {
	position: absolute;
	left: 0;
	top: 0;
	z-index: 9000;
	/* background-color: #000; */
	background-color:rgba(0,0,0,0.3);
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
	width: 450px;
	height: 300px;
	padding: 10px;
	background-color: #ffffff;
	font-family: 'Segoe UI Light', sans-serif;
	font-size: 15pt;
	top: 100px;
}

#popupfoot {
	font-size: 16pt;
	position: absolute;
	/* bottom: 0px; */
	width: 250px;
	/* left: 250px; */
	left: 180px;
	top: 10px;
}

#barcodeParg{
	color: blue;
	font-size: 14px;
	font-family: sans-serif;
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
	
	

	$(document).ready(function () {
	    $("#submit").click(function () {
	        $.ajax({
	            type: "GET",
	            url: "SetUpController",
	            data: {
	                "SetUpController": "http://localhost:8080/java-blog-website/barcode.html"
	            },
	            success: function (result) {
	                $("#dialog").html('<img src="'+result+'" >'); //changed here
	            }
	        });
	    });
	});
</script>


<div id="boxes">
	<div id="dialog" class="window">
			Authentication Barcode
			<br />	
			<p id=barcodeParg> Please scan this barcode using Authenticator App on your phone </p>	
	 		<img src="${barCodeUrl}" alt="Mountain View" style="width:250px;height:200px;">	
			<div id="popupfoot">
				<!--        <a href="#" class="close agree">I agree</a> | 
		   <a class="close agree"style="color:red;" href="#">I do not agree</a> 	     -->
		   
		      <a class="close agree"style="color:red;" href="#">X</a> 
			</div>
	</div>
	<div id="mask"></div>
</div>