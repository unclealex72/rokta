/**
 * 
 */
package uk.co.unclealex.rokta.process.league.granularity;

/**
 * @author alex
 *
 */
public abstract class AbstractGranularity implements Granularity {
	
	private int i_calendarField;
	private String i_description;
	
	/**
	 * @param calendarField
	 * @param description
	 */
	public AbstractGranularity(int calendarField, String description) {
		super();
		i_calendarField = calendarField;
		i_description = description;
	}
	
	/**
	 * @return the calendarField
	 */
	public int getCalendarField() {
		return i_calendarField;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return i_description;
	}
	
}
