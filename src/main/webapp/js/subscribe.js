$(function() {
	$("button[name=delete]").click(function() {
		var pin = $(this).parent().prev().prev().html();
		var ecoId = $(this).parent().prev().html();
		var curButton = $(this);
		$("#dialog-confirm").dialog({
			resizable : false,
			height : 140,
			modal : true,
			buttons : {
				"Delete the item" : function() {
					$(this).dialog("close");
					$.ajax({
						url : "subscribe",
						async : true,
						type : "DELETE",
						data : {
							pin : pin,
							ecoId : ecoId
						},
						// dataType: "json",
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
										"<tr><td><input type=\"text\" name=\"pin\"></td>"
												+ "<td><input type=\"text\" name=\"ecoId\"></td>"
												+ "<td><button type=\"button\" name=\"addType\">add</button></td></tr>");
					});

	$("table")
			.on(
					"click",
					"button[name=addType]",
					function() {
						var pin = $(this).parent().prev().prev().children(
								"input").val();
						var ecoId = $(this).parent().prev().children("input")
								.val();
						var button = $(this);
						$
								.ajax({
									url : "subscribe",
									async : true,
									type : "POST",
									data : {
										pin : pin,
										ecoId : ecoId
									},
									// dataType: "json",
									success : function(result) {
										alert("success");
										button
												.parent()
												.parent()
												.html(
														"<td>"
																+ pin
																+ "</td>"
																+ "<td>"
																+ ecoId
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