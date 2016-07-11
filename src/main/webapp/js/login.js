$(function(){
	$("#submit").click(function() {
		var username = $("#username").val();
		var password = $("#password").val();

		if (username == '' || password == '') {
			alert("Please fill both user name and password!");
		} else {
			$("#loginForm").submit();
		}
	});
});
