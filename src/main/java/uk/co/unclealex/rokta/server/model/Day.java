package uk.co.unclealex.rokta.server.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.common.base.Objects;

public class Day implements Serializable {

	private int i_year;
	private int i_month;
	private int i_day;
	
	protected Day() {
		super();
		// Default constructor for serialisation.
	}
	
	public Day(Date date) {
		super();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		i_year = calendar.get(Calendar.YEAR);
		i_month = calendar.get(Calendar.MONTH);
		i_day = calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public Date asDate() {
		Calendar calendar = new GregorianCalendar(getYear(), getMonth(), getDay());
		return calendar.getTime();
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getYear(), getMonth(), getDay());
	}
	
	@Override
	public boolean equals(Object obj) {
		Day other;
		return (obj instanceof Day) && 
				getYear() == (other = (Day) obj).getYear() && getMonth() == other.getMonth() && getDay() == other.getDay();
	}
	
	public int getYear() {
		return i_year;
	}
	
	public int getMonth() {
		return i_month;
	}
	
	public int getDay() {
		return i_day;
	}
	
	
}
