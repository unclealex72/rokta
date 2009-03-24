/**
 * 
 */
package uk.co.unclealex.rokta.model.converters;

import java.util.Map;

import uk.co.unclealex.rokta.model.Colour;
import uk.co.unclealex.rokta.model.dao.ColourDao;

import com.opensymphony.webwork.util.WebWorkTypeConverter;

/**
 * @author alex
 *
 */
public class ColourConverter extends WebWorkTypeConverter {

	private ColourDao i_colourDao;
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		String name = values[0];
		Colour colour = getColourDao().getColourByName(name);
		if (colour == null) {
			colour = getColourDao().getColourByHtmlName(name);
		}
		return colour;
	}

	@Override
	public String convertToString(Map context, Object o) {
		return ((Colour) o).getName();
	}

	/**
	 * @return the colourDao
	 */
	public ColourDao getColourDao() {
		return i_colourDao;
	}

	/**
	 * @param colourDao the colourDao to set
	 */
	public void setColourDao(ColourDao colourDao) {
		i_colourDao = colourDao;
	}

}
