<jsp:root
  xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:ww="/ww"
  xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
  version="2.0">

  <jsp:output doctype-root-element="html" omit-xml-declaration="false"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
    doctype-system="http://www.w3c.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

  <jsp:directive.page contentType="text/html" />
  <html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="rokta.css" />
    <title>ROKTA <decorator:title/></title>
  </head>

  <body>
    <div id="header">&#160;</div>
    <div id="content">
      <decorator:body/>
    </div>
    <div></div>
  </body>

  </html>
</jsp:root>
