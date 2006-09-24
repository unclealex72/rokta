<jsp:root
  xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:ww="/webwork"
  xmlns:decorator="http://www.opensymphony.com/sitemesh/decorator"
  xmlns:cewolf="http://cewolf.sourceforge.net/taglib/cewolf.tld"
  version="2.0">

  <jsp:output doctype-root-element="html" omit-xml-declaration="false"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
    doctype-system="http://www.w3c.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

  <jsp:directive.page contentType="text/html" />
  <html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
    <title>Weekly results</title>
  </head>

  <body>
    <h1>Weekly results</h1>
    <ww:set name="leagueGraphProducer" scope="page" value="leagueGraphDatasetProducer"/>
    
    <cewolf:chart 
      id="leagueGraph"
      title="Weekly Results" 
      type="line">
      <cewolf:data>
          <cewolf:producer id="leagueGraphProducer"/>
      </cewolf:data>
    </cewolf:chart>

    <cewolf:img chartid="leagueGraph" renderer="cewolf" width="500" height="300"/>
  </body>
  </html>
</jsp:root>