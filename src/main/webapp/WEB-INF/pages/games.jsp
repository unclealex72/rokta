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
		<ww:iterator id="game" value="gameViews">
			<h1>
				<ww:text name="format.date"> 
				    <ww:param value="datePlayed"/>
				</ww:text>			
			</h1>
			<h2>Instigated by <ww:property value="instigator"/></h2>
			<table>
				<tr>
					<th>Counter</th>
					<ww:iterator id="participant" value="participants">
						<th><ww:property value="name"/></th>
					</ww:iterator>
				</tr>
				<ww:iterator id="round" value="rounds">
					<tr>
						<td><ww:property value="counter"/></td>
						<ww:iterator id="hand" value="hands">
							<td><ww:property value="hand"/></td>
						</ww:iterator>
					</tr>
				</ww:iterator>
			</table>
		</ww:iterator>
	</div>
</body>
</html>