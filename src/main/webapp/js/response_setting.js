$(function() {
	$("button[name=delete]").click(function() {
		var responseCode = $(this).parent().prev().prev().html();
		var responseContent = $(this).parent().prev().html();
		var curButton = $(this);
		$("#dialog-confirm").dialog({
			resizable : false,
			height : 140,
			modal : true,
			buttons : {
				"Delete the item" : function() {
					$(this).dialog("close");
					$.ajax({
						url : "setting/removeResponse",
						async : true,
						type : "DELETE",
						data : {
							responseCode : responseCode,
							responseContent : responseContent
						},
						success : function(result) {
							alert("success");
							curButton.parent().parent().remove();
						},
						error : function(xhr, status, error) {
							alert("false");
						}
					});
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
	});

	$("button[name=add]")
			.click(
					function() {
						$("table tr:last")
								.after(
										"<tr><td><input type=\"text\" name=\"responseCode\"></td>"
												+ "<td><input type=\"text\" name=\"responseContent\"></td>"
												+ "<td><button type=\"button\" name=\"addType\">add</button></td></tr>");
					});

	$("table")
			.on(
					"click",
					"button[name=addType]",
					function() {
						var responseCode = $(this).parent().prev().prev()
								.children("input").val();
						var responseContent = $(this).parent().prev().children(
								"input").val();
						var button = $(this);
						$
								.ajax({
									url : "setting/createResponse",
									async : true,
									type : "POST",
									data : {
										responseCode : responseCode,
										responseContent : responseContent
									},
									// dataType: "json",
									success : function(result) {
										alert("success");
										button
												.parent()
												.parent()
												.html(
														"<td>"
																+ responseCode
																+ "</td>"
																+ "<td>"
																+ responseContent
																+ "</td>"
																+ "<td><button type=\"button\" name=\"add\">+</button>"
																+ "<button type=\"button\" name=\"delete\">-</button></td>");

									},
									error : function(xhr, status, error) {
										alert("false");
									}
								});
					});
});