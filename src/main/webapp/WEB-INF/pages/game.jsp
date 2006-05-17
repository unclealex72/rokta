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
		<form action="round.action" method="post">
			<ww:hidden name="currentRound" value="%{currentRound}"/>
			<table>
				<tr>
					<td colspan="2"/>
						Round <ww:property value="currentRound"/>
					</td>
				</tr>
				<ww:select
						label="Counter"
	      				name="counter"
	      				value="%{counter.name}"
				       	list="everybody"
				        listKey="name"
						listValue="name"
						multiple="false"
				        required="false"/>
				<ww:iterator id="participant" value="participants">
					<ww:select
							label="%{participant}"
	       					name="hands"
	       					value="ROCK"
					       	list="allHands"
							listValue="description"
							multiple="false"
					        required="false"/>
				</ww:iterator>
				<tr>
					<td/>
					<td><input type="submit"/></td>
				</tr>
			</table>
			<ww:iterator id="participant" value="participants">
				<ww:hidden name="participants" value="%{participant}"/>
			</ww:iterator>
		</form>
	</div>
</body>
</html>