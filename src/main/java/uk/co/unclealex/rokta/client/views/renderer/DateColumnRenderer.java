package uk.co.unclealex.rokta.client.views.renderer;

import java.util.Date;

import uk.co.unclealex.rokta.client.model.Table;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateColumnRenderer extends OnlyForClassColumnRenderer<Date> {

	private final DateTimeFormat i_dateTimeFormat;
	
	public DateColumnRenderer(DateTimeFormat dateTimeFormat) {
		super(Date.class);
		i_dateTimeFormat = dateTimeFormat;
	}

	@Override
	public String doRender(Date cell, Table table, int row, int column) {
		return getDateTimeFormat().format(cell);
	}

	public DateTimeFormat getDateTimeFormat() {
		return i_dateTimeFormat;
	}
}
