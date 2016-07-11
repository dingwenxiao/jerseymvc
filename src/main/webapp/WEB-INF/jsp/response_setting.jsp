<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/exception.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css">
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/response_setting.js"></script>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>response code</th>
				<th>response content</th>
				<th>create or remove</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${it.responseList}" var="element">
				<tr>
					<td>${element.responseCode}</td>
					<td>${element.responseContent}</td>
					<td><button type="button" name="add">+</button>
						<button type="button" name="delete">-</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="dialog-confirm" title="Remove the response type?" style="display:none">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are you sure?</p>
</div>
</body>
<html>