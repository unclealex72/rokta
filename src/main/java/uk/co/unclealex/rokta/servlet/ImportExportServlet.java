package uk.co.unclealex.rokta.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import uk.co.unclealex.rokta.server.model.Rokta;
import uk.co.unclealex.rokta.server.process.ImportExportManager;

public class ImportExportServlet extends HttpServlet {

  private ImportExportManager importExportManager;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    WebApplicationContext ctxt = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
    setImportExportManager(ctxt.getBean(ImportExportManager.class));
  }
  
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	  response.setContentType("text/xml");
	  try {
      JAXBContext ctxt = JAXBContext.newInstance(Rokta.class);
      Rokta rokta = getImportExportManager().exportAll();
      ServletOutputStream out = response.getOutputStream();
      ctxt.createMarshaller().marshal(rokta, out);
      out.close();
    }
    catch (JAXBException e) {
      throw new ServletException(e);
    }
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    try {
      JAXBContext ctxt = JAXBContext.newInstance(Rokta.class);
      InputStream in = request.getInputStream();
      Rokta rokta = (Rokta) ctxt.createUnmarshaller().unmarshal(in);
      getImportExportManager().importAll(rokta);
      response.sendError(204);
    }
    catch (JAXBException e) {
      throw new ServletException(e);
    }
	}

  public ImportExportManager getImportExportManager() {
    return importExportManager;
  }

  public void setImportExportManager(ImportExportManager importExportManager) {
    this.importExportManager = importExportManager;
  }


}
