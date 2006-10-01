<jsp:root
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:ww="/webwork"
  xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
  xmlns:c="http://java.sun.com/jsp/jstl/core"
  xmlns:rokta="/rokta"
  version="2.0">

  <jsp:output doctype-root-element="html" omit-xml-declaration="true"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
    doctype-system="http://www.w3c.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

  <jsp:directive.page contentType="text/html" />
  <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  
  <head>
    <title>ROKTA - <decorator:title/></title>
    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
  
    <!-- **** Layout Stylesheet **** -->
    <c:url var="link" value="/style/style104_left.css" />
    <link rel="stylesheet" type="text/css" href="${link}" />
  
    <!-- **** Colour Scheme Stylesheet **** -->
    <rokta:theme parameterName="colour" cookieName="colour" var="colour" defaultValue="2" validValues="[1-8]"/>
    <c:url var="link" value="/style/colour${colour}.css"/>
    <link rel="stylesheet" type="text/css" href="${link}" />
  
    <!-- **** rokta specific Stylesheet **** -->
    <c:url var="link" value="/style/rokta.css" />
    <link rel="stylesheet" type="text/css" href="${link}" />

    <script type="text/javascript">
      var links = new Array();
      links[0] = 'leaguelinks';
      links[1] = 'profilelinks';
      links[2] = 'statisticslinks';
    
      function showLinks(divId) {
        var idx;

        for (idx in links) {
          document.getElementById(links[idx]).style.display = (links[idx] == divId)?'block':'none';
        }
        document.getElementById('addlinks').style.visibility = "visible";
        return false;
      }
      
      function showLeagueLinks() {
        showLinks('leaguelinks');
      }
      
      function showProfileLinks() {
        showLinks('profilelinks');
      }
      
      function showStatisticsLinks() {
        showLinks('statisticslinks');
      }
    </script>
  </head>
  
  <body>  
    <div id="main">
      <div id="links">
        <!--
          <a href="#">another link</a> | <a href="#">another link</a> | <a href="#">another link</a> | <a href="#">another link</a>
        -->
      </div>
      <div id="logo"><h1>ROKTA - <decorator:title/></h1></div>
      <div id="content">
        <div id="column1">
          <div id="menu">
            <h1>Navigate</h1>
            <ul>
              <li>
                <c:set var="link">
                  <ww:url action="initialise"/>
                </c:set>
                <a href="${link}">New game</a>
              </li>
              <li><a href="javascript:showLeagueLinks()">Leagues</a></li>
              <li><a href="javascript:showStatisticsLinks()">Statistics</a></li>
              <li><a href="javascript:showProfileLinks()">Profiles</a></li>
            </ul>
          </div>
          <!--
          <div class="sidebaritem">
            <h1>news_</h1>
            <h2>1st January 2006</h2>
            <p>The company announces the launch of it's new website.</p>
            <h2>1st January 2006</h2>
            <p>The company announces the launch of it's new website.</p>
            <p>NOTES: This area can be used for news or any other info.</p>
          </div>
          -->

          <div id="addlinks">
            <h1>Choose</h1>
            <div id="leaguelinks">
              <ul>
                <li>
                  <a href="league.html">Full league</a>
                </li>
                <li>
                  <ww:form name="weeklyForm" action="league" method="post">
                    <ww:select name="selectedWeek" list="selectableWeeks"/>
                    <a href="#" onclick="document.forms['weeklyForm'].submit();return false;">Go</a>
                  </ww:form>
                </li>
                <li>
                  <ww:form name="monthlyForm" action="league" method="post">
                    <ww:select name="selectedMonth" list="selectableMonths"/>
                    <a href="#" onclick="document.forms['monthlyForm'].submit();return false;">Go</a>
                  </ww:form>
                </li>
                <li>
                  <ww:form name="yearlyForm" action="league" method="post">
                    <ww:select name="selectedYear" list="selectableYears"/>
                    <a href="#" onclick="document.forms['yearlyForm'].submit();return false;">Go</a>
                  </ww:form>
                </li>
              </ul>
            </div>
            <div id="profilelinks">
              <ul>
                <ww:iterator id="player" value="players">
                  <li>
                    <c:set var="link">
                      <ww:url action="profile">
                        <ww:param name="name" value="name"/>
                      </ww:url>
                    </c:set>
                    <a href="${link}"><ww:property value="name"/></a>
                  </li>
                </ww:iterator>
              </ul>
            </div>
            <div id="statisticslinks">
              <ul>
                <li>
                  <c:set var="link">
                    <ww:url action="headtoheads"/>
                  </c:set>
                  <a href="${link}">Head to Heads</a>
                </li>
                <li>
                  <c:set var="link">
                    <ww:url action="winningstreaks"/>
                  </c:set>
                  <a href="${link}">Winning Streaks</a>
                </li>
                <li>
                  <c:set var="link">
                    <ww:url action="losingstreaks"/>
                  </c:set>
                  <a href="${link}">Losing Streaks</a>
                </li>
                <li>
                  <c:set var="link">
                    <ww:url action="leaguegraph"/>
                  </c:set>
                  <a href="${link}">League Graph</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div id="column2">
          <decorator:body/>
        </div>
      </div>
      <div id="footer">
        copyright 2006 Alex Jones | alex.jones at unclealex.co.uk | <a href="http://validator.w3.org/check?uri=referer">XHTML 1.1</a> | <a href="http://jigsaw.w3.org/css-validator/check/referer">CSS</a> | <a href="http://www.dcarter.co.uk">design by dcarter</a>
      </div>
    </div>
    <script type="text/javascript">
      <ww:if test="showLeague != null">
        showLeagueLinks();
      </ww:if>
      <ww:if test="showProfile != null">
        showProfileLinks();
      </ww:if>
      <ww:if test="showStatistics != null">
        showStatisticsLinks();
      </ww:if>
    </script>
  </body>
  </html>
</jsp:root>