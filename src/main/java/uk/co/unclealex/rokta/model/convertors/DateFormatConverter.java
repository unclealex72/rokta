/**
 * 
 */
package uk.co.unclealex.rokta.model.convertors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.opensymphony.webwork.util.WebWorkTypeConverter;

/**
 * @author alex
 *
 */
public class DateFormatConverter extends WebWorkTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		return new SimpleDateFormat(values[0]);
	}

	@Override
	public String convertToString(Map context, Object o) {
		return ((DateFormat) o).toString();
	}

}
