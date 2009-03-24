package uk.co.unclealex.rokta.result;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.util.OgnlValueStack;

public class JaxbResult implements Result {

	private String i_variable;
	private String i_filename;
	
	public void execute(ActionInvocation invocation) throws JAXBException, IOException {
		// The string finalLocation is actually the OGNL name of the object we wish to marshall.
		OgnlValueStack stack = invocation.getStack();
		Object obj = stack.findValue(getVariable());
		String filename = stack.findString(getFilename());
		JAXBContext context = JAXBContext.newInstance(new Class[] { obj.getClass() });
		Marshaller marshaller = context.createMarshaller();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition","attachment; filename=\"" + filename + ".xml\"");
		Writer writer = response.getWriter();
		marshaller.marshal(obj, writer);
		writer.close();
	}

	public String getFilename() {
		return i_filename;
	}

	public void setFilename(String filename) {
		i_filename = filename;
	}

	public String getVariable() {
		return i_variable;
	}

	public void setVariable(String variable) {
		i_variable = variable;
	}

}
