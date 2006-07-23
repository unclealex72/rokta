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
    <title>Get Ready To Play</title>
  </head>

  <body>
    <form action="start.action" method="post">
      <p>
        <ww:if test="exempt != null">
          <ww:property value="exempt"/>
        </ww:if>
        <ww:else>
          No-one
        </ww:else>
        is exempt.
      </p>
      <table>
        <ww:select label="Instigator" name="instigator" list="everybody"
            listKey="name" listValue="name" multiple="false" required="false" />
        <ww:select label="Participants" name="participants" list="availablePlayers"
            listKey="name" listValue="name" multiple="true" required="false" />
        <tr>
          <td />
          <td><input type="submit" /></td>
        </tr>
      </table>
    </form>
  </body>
  </html>
</jsp:root>