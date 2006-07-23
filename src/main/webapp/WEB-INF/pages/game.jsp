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
    <title>Rounds</title>
  </head>

  <body>
    <form action="round.action" method="post">
      <ww:hidden name="currentRound" value="%{currentRound}"/>
      <table>
        <tr>
          <td colspan="2">Round <ww:property value="currentRound"/></td>
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
  </body>
  </html>
</jsp:root>