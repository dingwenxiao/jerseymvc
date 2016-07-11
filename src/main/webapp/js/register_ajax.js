$(function(){
	$("#submit").click(function() {
		var username = $("#username").val();
		var password = $("#password").val();
		var repeatPassword = $("#repeat_password").val();
		var email = $("#email").val();
		var ip = $("#ip").val();
		
		var user = {"userId":0, "userName": username, "password":password, "email":email, "ip":ip, "port":0};
		
		if(!validateEmail(email)){
			alert("please enter a vaild email address");
			e.preventDefault();
		}
		
		if(password != repeatPassword){
			alert("the passwords do not match");
			e.preventDefault();
		}
		
		// Checking for blank fields.
		if (username == '' || password == '') {
			alert("Please fill both user name and password");
			e.preventDefault();
		} 
			//$("#register_form").submit();
		$.ajax({
			url : "register",
			async : true,
			type : "POST",
			//accepts: "application/json; charset=utf-8",
			contentType:"application/json",
			data : JSON.stringify(user),
			cache: false,
			dataType: "json",
			success : function(result) {
				if(typeof result === 'undefined') {
					$("#shade").show();
					$("#password").val("");
					$("#repeat_password").val("");
				} else {
					alert("success");
					window.location.herf = "setting";
				}
			},
			error : function(xhr, status, error) {
				alert("Register failed");
			}
		});
		
			
	});
	
});

function validateEmail(sEmail) {
    var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    if (filter.test(sEmail)) {
        return true;
    }
    else {
        return false;
    }
}