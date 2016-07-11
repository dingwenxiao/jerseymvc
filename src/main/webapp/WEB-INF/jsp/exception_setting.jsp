<%@ page contentType="text/html" pageEncoding="UTF-8" language="java"
	import="java.util.List,java.util.Map,dingwen.simulator.entity.Operation,dingwen.simulator.entity.ResponseSetting,dingwen.simulator.entity.Setting"%>
<%
    Map<String,Object> it = (Map<String,Object>) request.getAttribute("it");
%>
<%
	List<Operation> operationList = (List<Operation>)it.get("operationList");
%>
<%
	List<ResponseSetting> responseList = (List<ResponseSetting>) it.get("responseList");
%>
<%
	Map<String, Setting> settingMap = (Map<String, Setting>) it.get("settingMap");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/exception.css">
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/setting.js"></script>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>api</th>
				<th>response code</th>
				<th>delay(milliseconds)</th>
				<th>operation</th>
			</tr>
		</thead>
		<tbody>
			<%for (Operation operation : operationList) {%>
			<%Setting setting = null;
			if(settingMap != null)
				setting = settingMap.get(operation.getOperationName());
			//String responseContent = null;%>
			
			<tr>
				<td><strong><%=operation.getOperationName()%></strong></td>
				<td><div>
						<select>
							<%for (ResponseSetting responseSetting : responseList) {%>
							<%if (setting != null && setting.getResponseCode() == responseSetting.getResponseCode()) {%>
					    	<%// responseContent = responseSetting.getResponseContent(); %>
							<option value="<%=responseSetting.getResponseCode()%>" selected><%=responseSetting.getResponseCode()%></option>
							<%} else {%>
							<option value="<%=responseSetting.getResponseCode()%>"><%=responseSetting.getResponseCode()%></option>
							<%}}%>
						</select>
					</div></td>
					<td>
					<%if(setting != null) {%>
					<input type="text" name="delay" value="<%=setting.getDelay()==null? 0:setting.getDelay()%>">
					<% } else {%>
					<input type="text" name="delay" value="0">
					<% }%>
					</td>
				<td><div>
						<button type="button">Apply</button>
					</div></td>
			</tr>
			<%}%>
		</tbody>
	</table>
</body>
<html>