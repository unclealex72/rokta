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
		<table>
			<tr>
				<th>Player</th>
				<th>Games</th>
				<th>Rounds</th>
				<th>Lost</th>
			</tr>
			<ww:iterator id="row" value="league">
				<tr>
					<td><ww:property value="person"/></td>
					<td><ww:property value="gamesPlayed"/></td>
					<td><ww:property value="roundsPlayed"/></td>
					<td><ww:property value="gamesLost"/></td>
			</ww:iterator>
		</table>
	</div>
</body>
</html>