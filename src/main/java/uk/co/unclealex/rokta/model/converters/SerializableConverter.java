package uk.co.unclealex.rokta.model.converters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.util.WebWorkTypeConverter;

public class SerializableConverter extends WebWorkTypeConverter implements WebWorkStatics {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		byte[] data = new Base64().decode(values[0].getBytes());
		try {
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
			Object obj = in.readObject();
			// Now see if we can autowire this object
			ServletContext servletContext = (ServletContext) context.get(SERVLET_CONTEXT);
			if (servletContext != null) {
				ApplicationContext applicationContext =
					WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
				if (applicationContext != null) {
					AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
					//TODO: Find out how to use the default dependency check and autowire type.
					factory.autowireBeanProperties(obj, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
				}
			}
			return obj;
		}
		catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public String convertToString(Map context, Object o) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			new ObjectOutputStream(out).writeObject(o);
			byte[] encoded = new Base64().encode(out.toByteArray());
			return new String(encoded, "utf-8");
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
