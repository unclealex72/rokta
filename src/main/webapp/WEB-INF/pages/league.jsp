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
		<p>
			<ww:if test="selectedWeek != null">
				League for <ww:property value="selectedWeek"/>.
				<a href="league.action">(Full league)</a>
			</ww:if>
			<ww:elseif test="selectedMonth != null">
				League for <ww:property value="selectedMonth"/>.
				<a href="league.action">(Full league)</a>
			</ww:elseif>
			<ww:else>
				Full league.
			</ww:else>
		</p>
		<table>
			<tr>
				<th/>
				<th>Player</th>
				<th>Games</th>
				<th>Rounds</th>
				<th>Lost</th>
				<th>L/R</th>
				<th>L/G</th>
			</tr>
			<ww:iterator id="row" value="league">
				<tr>
					<td><ww:property value="delta"/></td>
					<td><ww:property value="person"/></td>
					<td><ww:property value="gamesPlayed"/></td>
					<td><ww:property value="roundsPlayed"/></td>
					<td><ww:property value="gamesLost"/></td>
					<td>
						<ww:text name="format.percent"> 
						    <ww:param value="lossesPerRound * 100"/>
						</ww:text>
					</td>
					<td>
						<ww:text name="format.percent"> 
						    <ww:param value="lossesPerGame * 100"/>
						</ww:text>
					</td>
				</tr>
			</ww:iterator>
		</table>
		<form action="league.action">
			<p>
				Show league for
				<select name="selectedWeek">
					<ww:iterator id="selection" value="selectableWeeks">
						<option><ww:property/></option>
					</ww:iterator>
				</select>
				<input type="submit" value="Select Week"/>
			</p>
		</form>
		<form action="league.action">
			<p>
				Show league for
				<select name="selectedMonth">
					<ww:iterator id="selection" value="selectableMonths">
						<option><ww:property/></option>
					</ww:iterator>
				</select>
				<input type="submit" value="Select Month"/>
			</p>
		</form>
	</div>
</body>
</html>