package uk.co.unclealex.rokta.filter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import uk.co.unclealex.rokta.process.restriction.GameRestriction;


public abstract class NamedPeriodGameFilter extends BetweenGameFilter {

	@Override
	public String getDescription() {
		return "for " + new SimpleDateFormat(getPeriodNameDateFormat()).format(getFirstDateAsCalendar().getTime());
	}
	
	@Override
	protected GameRestriction getGameRestriction() {
		Calendar cal = getFirstDateAsCalendar();
		Date from = cal.getTime();
		cal.add(getPeriodLengthField(), 1);
		cal.add(Calendar.MILLISECOND, -1);
		Date to = cal.getTime();
		setFrom(from);
		setTo(to);
		return super.getGameRestriction();
	}

	@Override
	protected void decodeInfo(String encodingInfo) throws IllegalFilterEncodingException {
		int[] lengths = getNumberLengths();
		int totalLength = 0;
		for (int length : lengths) {
			totalLength += length;
		}
		if (encodingInfo.length() != totalLength) {
			throw new IllegalFilterEncodingException(encodingInfo + " is not the correct length.");
		}
		int[] numbers = new int[lengths.length];
		int pos = 0;
		for (int idx = 0; idx < lengths.length; idx++) {
			int length = lengths[idx];
			try {
				numbers[idx] = new Integer(encodingInfo.substring(pos, pos + length));
			}
			catch (NumberFormatException e) {
				throw new IllegalFilterEncodingException("Cannot parse " + encodingInfo, e);
			}
			pos += length;
		}
		decodeNumbers(numbers);
	}

	@Override
	protected String encodeInfo() {
		Formatter formatter = new Formatter();
		int[] numberLengths = getNumberLengths();
		int[] encodeNumbers = encodeNumbers();
		for (int idx = 0; idx < encodeNumbers.length; idx++) {
			formatter.format("%" + numberLengths[idx] + "d", encodeNumbers[idx]);
		}
		return formatter.toString();
	}

	protected abstract int[] getNumberLengths();
	protected abstract void decodeNumbers(int[] numbers);
	protected abstract int[] encodeNumbers();
	
	protected abstract Calendar getFirstDateAsCalendar();
	protected abstract int getPeriodLengthField();
	
	protected abstract String getPeriodNameDateFormat();

	@Override
	protected abstract char getEncodingPrefix();


}
