<jsp:root
  xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:ww="/webwork"
  xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
  xmlns:fmt="http://java.sun.com/jstl/fmt"
  version="2.0">

  <jsp:output doctype-root-element="html" omit-xml-declaration="true"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
    doctype-system="http://www.w3c.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

  <jsp:directive.page contentType="text/html" />
  <html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
    <title>
      <ww:if test="selectedWeek != null">
        League for <ww:property value="selectedWeek" />.
      </ww:if>
      <ww:elseif test="selectedMonth != null">
        League for <ww:property value="selectedMonth" />.
      </ww:elseif>
      <ww:else>
        Full league.
      </ww:else>    
    </title>
  </head>

  <body>
    <h1>
      <ww:if test="selectedWeek != null">
		League for <ww:property value="selectedWeek" />.
      </ww:if>
      <ww:elseif test="selectedMonth != null">
		League for <ww:property value="selectedMonth" />.
      </ww:elseif>
      <ww:else>
		Full league.
      </ww:else>
    </h1>
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
            <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
              <ww:property value="roundsPerGame"/>
            </fmt:formatNumber>
          </td>
          <td>
            <fmt:formatNumber type="percent" minFractionDigits="2" maxFractionDigits="2">
              <ww:property value="lossesPerGame"/>
            </fmt:formatNumber>
          </td>
          <ww:if test="league.current">
            <td>
              <ww:if test="gap != null">
                <ww:push value="gap">
                  <ww:if test="infinite">
                    &#8734;
                  </ww:if>
                  <ww:else>                
                    <ww:property value="value"/>
                  </ww:else>
                </ww:push>
              </ww:if>
            </td>
          </ww:if>
        </tr>
      </ww:iterator>
    </table>    
  </body>
  </html>
</jsp:root>