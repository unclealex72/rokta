/**
 * 
 */
package uk.co.unclealex.rokta.tags;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author alex
 *
 */
public class DateTag extends TagSupport {

	private String i_format;
	private String i_field;
	private int i_value;
	private String i_var;
	
	@Override
	public int doStartTag() throws JspException {
		DateFormat fmt = new SimpleDateFormat(getFormat());
		int field;
		try {
			field = Calendar.class.getField(getField()).getInt(null);
		} catch (IllegalArgumentException e) {
			throw new JspException(e);
		} catch (SecurityException e) {
			throw new JspException(e);
		} catch (IllegalAccessException e) {
			throw new JspException(e);
		} catch (NoSuchFieldException e) {
			throw new JspException(e);
		}
		Calendar cal = new GregorianCalendar();
		cal.add(field, getValue());
		pageContext.setAttribute(getVar(), fmt.format(cal.getTime()));
		return EVAL_PAGE;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return i_field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		i_field = field;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return i_format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		i_format = format;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return i_value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		i_value = value;
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
