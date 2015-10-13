<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
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

#formLbl {
	display: block;
	text-align: center;
	line-height: 150%;
	font-size: 16px;
}
</style>



<style>
#mask {
	position: absolute;
	left: 0;
	top: 0;
	z-index: 9000;
	/* background-color: #000; */
	background-color: rgba(0, 0, 0, 0.3);
	display: none;
}

#boxes .window {
	position: relative;
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

#verify.a {
	font-size: 16pt;
	position: absolute;
	bottom: 0px;
	width: 250px;
	/* left: 250px; */
	left: 180px;
	color: yellow;
}

#close-popup {
	font-size: 16pt;
	position: absolute;
	/* bottom: 0px; */
	width: 250px;
	/* left: 250px; */
	left: 180px;
	top: 10px;
}

#barcodeParg {
	color: blue;
	font-size: 14px;
	font-family: sans-serif;
}

.btn-verify {
	display: inline-block;
	margin-bottom: 0;
	font-weight: 400;
	text-align: center;
	vertical-align: middle;
	cursor: pointer;
	background-image: none;
	border: 1px solid transparent;
	white-space: nowrap;
	padding: 6px 12px;
	font-size: 14px;
	line-height: 1.42857143;
	border-radius: 4px;
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

<script>
	window.onload = function() {
		$("#modelBtn").trigger('click');
	}
</script>

<button hidden="true" id="modelBtn" data-toggle="modal"
	data-target="#myModal"></button>


<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Authentication Barcode</h4>
			</div>

			<div style="text-align: center;" class="modal-body">
				<!-- <p>Some text in the modal.</p> -->
				<p id=barcodeParg>Please scan this barcode using Authenticator
					App on your phone</p>
				<img src="${barCodeUrl}" alt="Mountain View"
					style="width: 250px; height: 200px;">
			</div>


			<div class="modal-footer">
				<form class="form-signin" role="form" method="POST">
					<label id="formLbl">Enter Your Key</label> <input type="text"
						name="code" class="form-control" placeholder="Verfication Code"
						required><br /> <input
						class="btn btn-lg btn-primary btn-block" type="submit"
						value="Verify">
				</form>

			</div>

		</div>
	</div>
</div>