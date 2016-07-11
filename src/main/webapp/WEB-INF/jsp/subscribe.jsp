<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/exception.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.min.css">
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/subscribe.js"></script>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>pin</th>
				<th>ecoId</th>
				<th>create or remove</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${it.subscribeList}" var="element">
				<tr>
					<td>${element.pin}</td>
					<td>${element.ecoId}</td>
					<td><button type="button" name="add">+</button>
						<button type="button" name="delete">-</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="dialog-confirm" title="Remove the ecoId and pin association?" style="display:none">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These items will be permanently deleted and cannot be recovered. Are you sure?</p>
</div>
</body>
<html>