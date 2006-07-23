<jsp:root
  xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:ww="/webwork"
  xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
  version="2.0">

  <jsp:output doctype-root-element="html" omit-xml-declaration="false"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
    doctype-system="http://www.w3c.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

  <jsp:directive.page contentType="text/html" />
  <html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>League</title>
  </head>

  <body>
    <p>
      <ww:if test="selectedWeek != null">
		League for <ww:property value="selectedWeek" />.
		<a href="league.action">(Full league)</a>
      </ww:if>
      <ww:elseif test="selectedMonth != null">
		League for <ww:property value="selectedMonth" />.
		<a href="league.action">(Full league)</a>
      </ww:elseif>
      <ww:else>
		Full league.
      </ww:else>
    </p>
    <table>
      <tr>
        <th />
        <th>Player</th>
        <th>Games</th>
        <th>Rounds</th>
        <th>Lost</th>
        <th>R/G</th>
        <th>L/G</th>
        <ww:if test="league.current">
          <th>Gap</th>
        </ww:if>
      </tr>
      <ww:iterator id="row" value="league.rows">
        <ww:if test="exempt">
          <ww:set name="class" value="%{'exempt'}" scope="page"/>
        </ww:if>
        <ww:elseif test="!playingToday">
          <ww:set name="class" value="%{'notPlaying'}" scope="page"/>
        </ww:elseif>
        <ww:else>
          <ww:set name="class" value="%{''}" scope="page"/>
        </ww:else>
        <tr class="${class}">
          <td><ww:property value="delta" /></td>
          <td><ww:property value="person" /></td>
          <td><ww:property value="gamesPlayed" /></td>
          <td><ww:property value="roundsPlayed" /></td>
          <td><ww:property value="gamesLost" /></td>
          <td>
            <ww:text name="format.rounded">
              <ww:param value="roundsPerGame" />
            </ww:text>
          </td>
          <td>
            <ww:text name="format.percent">
              <ww:param value="lossesPerGame * 100" />
            </ww:text>
          </td>
          <ww:if test="league.current">
            <td><ww:property value="gap"/></td>
          </ww:if>
        </tr>
      </ww:iterator>
    </table>
    
    <form action="league.action">
      <p>
        Show league for
        <select name="selectedWeek">
          <ww:iterator id="selection" value="selectableWeeks">
            <option><ww:property /></option>
          </ww:iterator>
        </select>
        <input type="submit" value="Select Week" />
      </p>
    </form>
    <form action="league.action">
      <p>
        Show league for
        <select name="selectedMonth">
          <ww:iterator id="selection" value="selectableMonths">
            <option><ww:property /></option>
          </ww:iterator>
        </select>
        <input type="submit" value="Select Month" />
      </p>
    </form>
  </body>
  </html>
</jsp:root>