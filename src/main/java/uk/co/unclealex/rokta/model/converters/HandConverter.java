package uk.co.unclealex.rokta.model.converters;

import java.util.Map;

import uk.co.unclealex.rokta.model.Hand;

import com.opensymphony.webwork.util.WebWorkTypeConverter;

public class HandConverter extends WebWorkTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		return Hand.valueOf(values[0]);
	}

	@Override
	public String convertToString(Map context, Object o) {
		return ((Hand) o).toString();
	}

}
