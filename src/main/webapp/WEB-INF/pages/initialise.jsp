<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/webwork" prefix="ww" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="rokta.css" />
	<title>ROKTA</title>
</head>
<body>
	<div id="header"></div>
	<div id="content">
		<form action="start.action" method="post">
			<table>
				<ww:select
						label="Instigator"
	      				name="instigator"
				       	list="everybody"
				        listKey="name"
						listValue="name"
						multiple="false"
				        required="false"/>
				<ww:select
						label="Participants"
	      					name="participants"
				       	list="everybody"
				        listKey="name"
						listValue="name"
						multiple="true"
				        required="false"/>
				<tr>
					<td/>
					<td><input type="submit" value="Go!"/></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>