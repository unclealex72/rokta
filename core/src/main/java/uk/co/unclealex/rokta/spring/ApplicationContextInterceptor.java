package uk.co.unclealex.rokta.spring;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

public class ApplicationContextInterceptor extends AroundInterceptor implements WebWorkStatics {

	@Override
	protected void after(ActionInvocation dispatcher, String result) {
		// Nothing to do.
	}

	@Override
	protected void before(ActionInvocation invocation) {
		Object action = invocation.getAction();
		if (action instanceof ApplicationContextAware) {
			ServletContext servletContext =
				(ServletContext) invocation.getInvocationContext().get(SERVLET_CONTEXT);
			ApplicationContext applicationContext =
				WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			((ApplicationContextAware) action).setApplicationContext(applicationContext);
		}
	}

}
