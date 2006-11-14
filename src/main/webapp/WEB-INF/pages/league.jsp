<jsp:root
  xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:ww="/webwork"
  xmlns:fmt="http://java.sun.com/jstl/fmt"
  xmlns:c="http://java.sun.com/jstl/core"
  xmlns:cewolf="http://cewolf.sourceforge.net/taglib/cewolf.tld"
  xmlns:rokta="/rokta"
  version="2.0">

  <jsp:output doctype-root-element="html" omit-xml-declaration="true"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
    doctype-system="http://www.w3c.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

  <jsp:directive.page contentType="text/html" />
  <html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
    <title>
      <ww:text name="league.title">
        <ww:param value="selection"/>
      </ww:text>
    </title>
  </head>

  <body>
    <h1>
      <ww:text name="league.title">
        <ww:param value="selection"/>
      </ww:text>
    </h1>
    <table class="data">
      <tr>
        <th />
        <th>Player</th>
        <th>Games</th>
        <th>Rounds</th>
        <th>Lost</th>
        <th>R/WG</th>
        <th>R/LG</th>
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
            <ww:if test="%{gamesWon == 0}">
              -
            </ww:if>
            <ww:else>
              <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
                <ww:property value="roundsPerWonGames"/>
              </fmt:formatNumber>
            </ww:else>
          </td>
          <td>
            <ww:if test="%{gamesLost == 0}">
              -
            </ww:if>
            <ww:else>
              <fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2">
                <ww:property value="roundsPerLostGames"/>
              </fmt:formatNumber>
            </ww:else>
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
    
    <h1>
      <ww:text name="graph.title">
        <ww:param value="selection"/>
      </ww:text>
    </h1>
    
    <ww:set name="leagueGraphProducer" scope="page" value="leagueGraphDatasetProducer"/>
    <c:set var="graphTitle">
      <ww:property value="%{granularityPredicate.granularity.description}"/> results
    </c:set>
    
    <cewolf:chart 
      id="leagueGraph"
      title="${graphTitle}" 
      type="line">
      <cewolf:data>
          <cewolf:producer id="leagueGraphProducer"/>
      </cewolf:data>
    </cewolf:chart>

    <rokta:colourise chartid="leagueGraph"/>
    
    <cewolf:img chartid="leagueGraph" renderer="cewolf" width="480" height="300"/>
    
  </body>
  </html>
</jsp:root>