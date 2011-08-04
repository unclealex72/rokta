package uk.co.unclealex.rokta.client.views;

import java.io.Serializable;
import java.util.Date;

public class InitialDatesView implements Serializable {

	private Date i_initialDate;
	private Date i_earliestDate;
	private Date i_latestDate;
	
	protected InitialDatesView() {
		// Default no-argument constructor for serialisation.
	}
	
	public InitialDatesView(Date initialDate, Date earliestDate, Date latestDate) {
		super();
		i_initialDate = initialDate;
		i_earliestDate = earliestDate;
		i_latestDate = latestDate;
	}
	
	public Date getInitialDate() {
		return i_initialDate;
	}

	public Date getEarliestDate() {
		return i_earliestDate;
	}

	public Date getLatestDate() {
		return i_latestDate;
	}
}
