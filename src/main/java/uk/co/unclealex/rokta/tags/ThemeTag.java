/**
 * 
 */
package uk.co.unclealex.rokta.tags;

import java.util.Arrays;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;

/**
 * @author alex
 *
 */
public class ThemeTag extends TagSupport {

	private String i_parameterName;
	private String i_cookieName;
	private String i_defaultValue;
	private String i_validValues;
	private String i_var;
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
		
		String colour;
		String colourFromParameter;
		String colourFromCookie;
		if (isValid(colourFromParameter = request.getParameter(getParameterName()))) {
			colour = colourFromParameter;
			Cookie cookie = new Cookie(getCookieName(), colour);
			cookie.setMaxAge(Integer.MAX_VALUE);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		else if (isValid(colourFromCookie = findColourFromCookies())) {
			colour = colourFromCookie;
		}
		else {
			colour = getDefaultValue();
		}
		pageContext.setAttribute(getVar(), colour);
		return EVAL_PAGE;
	}
	
	/**
	 * @return
	 */
	private String findColourFromCookies() {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Cookie cookie =
			CollectionUtils.find(
					Arrays.asList(request.getCookies()),
					new Predicate<Cookie>() {
						public boolean evaluate(Cookie ck) {
							return getCookieName().equals(ck.getName());
						}
					}
			);
		return cookie==null?null:cookie.getValue();
	}

	private boolean isValid(String value) {
		if (value == null) {
			return false;
		}
		if (getValidValues() == null) {
			return true;
		}
		return Pattern.matches(getValidValues(), value);
	}
	
	/**
	 * @return the cookieName
	 */
	public String getCookieName() {
		return i_cookieName;
	}
	/**
	 * @param cookieName the cookieName to set
	 */
	public void setCookieName(String cookieName) {
		i_cookieName = cookieName;
	}
	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return i_defaultValue;
	}
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		i_defaultValue = defaultValue;
	}
	/**
	 * @return the parameterName
	 */
	public String getParameterName() {
		return i_parameterName;
	}
	/**
	 * @param parameterName the parameterName to set
	 */
	public void setParameterName(String parameterName) {
		i_parameterName = parameterName;
	}
	/**
	 * @return the validValues
	 */
	public String getValidValues() {
		return i_validValues;
	}
	/**
	 * @param validValues the validValues to set
	 */
	public void setValidValues(String validValues) {
		i_validValues = validValues;
	}

	/**
	 * @return the var
	 */
	public String getVar() {
		return i_var;
	}

	/**
	 * @param var the var to set
	 */
	public void setVar(String var) {
		i_var = var;
	}
	
	
}
