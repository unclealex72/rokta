<jsp:root
  xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:ww="/webwork"
  xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
  xmlns:cewolf="http://cewolf.sourceforge.net/taglib/cewolf.tld"
  version="2.0">

  <jsp:output doctype-root-element="html" omit-xml-declaration="true"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
    doctype-system="http://www.w3c.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

  <jsp:directive.page contentType="text/html" />
  <html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
    <title>
      Head to head results
    </title>
  </head>

  <body>
    <h1>
      Head To head results
    </h1>
    <table>
      <tr>
        <th>Winner/Loser</th>
        <ww:iterator id="loser" value="players">
          <th><ww:property value="name"/></th>
        </ww:iterator>
      </tr>
      <ww:iterator id="winner" value="players">
        <tr>
          <th><ww:property value="name"/></th>
          <ww:iterator id="loser" value="players">
            <td>
              <ww:property value="headToHeadResultsByPerson"/>
            </td>
          </ww:iterator>
        </tr>
      </ww:iterator>
    </table>
  </body>
  </html>
</jsp:root>