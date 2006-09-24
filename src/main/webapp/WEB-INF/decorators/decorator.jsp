<jsp:root
  xmlns:jsp="http://java.sun.com/JSP/Page"
  xmlns:ww="/webwork"
  xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
  xmlns:c="http://java.sun.com/jsp/jstl/core"
  version="2.0">

  <jsp:output doctype-root-element="html" omit-xml-declaration="false"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
    doctype-system="http://www.w3c.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

  <jsp:directive.page contentType="text/html" />
  <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  
  <head>
    <title>ROKTA - <decorator:title/></title>
    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
  
    <!-- **** Layout Stylesheet **** -->
    <link rel="stylesheet" type="text/css" href="style/style104_left.css" />
  
    <!-- **** Colour Scheme Stylesheet **** -->
    <link rel="stylesheet" type="text/css" href="style/colour2.css" />
  
    <!-- **** rokta specific Stylesheet **** -->
    <link rel="stylesheet" type="text/css" href="style/rokta.css" />

  </head>
  
  <body>
    <div id="main">
      <!--
      <div id="links">
        <a href="#">another link</a> | <a href="#">another link</a> | <a href="#">another link</a> | <a href="#">another link</a>
      </div>
      -->
      <div id="logo"><h1>ROKTA - <decorator:title/></h1></div>
      <div id="content">
        <div id="column1">
          <div id="menu">
            <h1>Navigate</h1>
            <ul>
              <li><a href="initialise.html">New game</a></li>
              <li><a href="league.html">Leagues</a></li>
              <li><a href="statistics.html">Statistics</a></li>
              <li><a href="profiles.html">Profiles</a></li>
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

          <div id="addlinks" style="visibility: ${linksVisibility}">
            <h1>Choose</h1>
            <div id="leagueLinks" style="visibility: ${leagueLinksVisibility}">
              <ul>
                <li>Choose a league:</li>
                <li>
                  <a href="league.html">Full league</a>
                </li>
                <li>
                  <ww:form action="league" method="post">
                    <ww:select name="selectedWeek" list="selectableWeeks"/>
                    <ww:submit value="Go"/>
                  </ww:form>
                </li>
                <li>
                  <ww:form action="league" method="post">
                    <ww:select name="selectedMonth" list="selectableMonths"/>
                    <ww:submit value="Go"/>
                  </ww:form>
                </li>
                <li>
                  <ww:form action="league" method="post">
                    <ww:select name="selectedYear" list="selectableYears"/>
                    <ww:submit value="Go"/>
                  </ww:form>
                </li>
              </ul>
            </div>
            <div id="profileLinks">
              <ul>
                <li>Choose a player:</li>
                <ww:iterator id="player" value="players">
                  <li>
                    <c:url var="link" value="profile.html">
                      <c:param name="id" value="${player.id}"/>
                    </c:url>
                    <a href="${link}"><ww:property value="player.name"/></a>
                  </li>
                </ww:iterator>
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
  </body>
  </html>
</jsp:root>