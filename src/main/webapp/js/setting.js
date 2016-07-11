$(function() {
	$("button").click(
			function() {
				var delay = $(this).parent().parent().prev().children(
				"input").val();
				
				var response_code = $(this).parent().parent().prev().prev().children(
						"div").children("select").children(":selected").text();
				
				// alert(response_code);
				var api = $(this).parent().parent().prev().prev().prev().children(
						"strong").html();
				
				
				// alert(api);
				// var setting = {settingId:"0", userId: "123", operationName:
				// api, responseCode:response_code};
				// alert(setting);JSON.stringify(setting)
				// setting/setResponseCode

				$.ajax({
					url : "setting/setResponseCode",
					async : true,
					type : "POST",
					data : {
						operationName : api,
						responseCode : response_code,
						delay: delay
					},
					// dataType: "json",
					success : function(result) {
						alert("success");
					},
					error : function(xhr, status, error) {
						alert("false");
					}
				});
			});
});