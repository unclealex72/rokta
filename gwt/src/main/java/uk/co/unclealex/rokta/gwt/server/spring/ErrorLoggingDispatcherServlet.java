package uk.co.unclealex.rokta.gwt.server.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.DispatcherServlet;

public class ErrorLoggingDispatcherServlet extends DispatcherServlet {

	private static final Logger log = Logger.getLogger(ErrorLoggingDispatcherServlet.class);
	
	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			super.doService(request, response);
		}
		catch (Throwable t) {
			log.error(t.getMessage(), t);
			if (t instanceof RuntimeException) {
				throw (RuntimeException) t;
			}
			else {
				throw (Exception) t;
			}
		}
	}
}
