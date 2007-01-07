package uk.co.unclealex.rokta.result;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;

public class JaxbResult extends WebWorkResultSupport {

	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws JAXBException, IOException {
		// The string finalLocation is actually the OGNL name of the object we wish to marshall.
		OgnlValueStack stack = invocation.getStack();
		Object obj = stack.findValue(finalLocation);
		JAXBContext context = JAXBContext.newInstance(new Class[] { obj.getClass() });
		Marshaller marshaller = context.createMarshaller();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		Writer writer = response.getWriter();
		marshaller.marshal(obj, writer);
		writer.close();
	}

}
