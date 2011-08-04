package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

public abstract class AbstractGameFilter implements GameFilter {

	public abstract String[] toStringArgs();
	
	public abstract <T> T accept(GameFilterVistor<T> gameFilterVisitor);

	public abstract boolean isContinuous();

	@Override
	public String toString() {
		return getClass().getName() + ":" + join(toStringArgs(), '-');
	}

	protected String join(String[] stringArgs, char separator) {
		StringBuffer buffer = new StringBuffer();
		for (int idx = 0; idx < stringArgs.length; idx++) {
			if (idx != 0) {
				buffer.append(separator);
			}
			buffer.append(stringArgs[idx]);
		}
		return buffer.toString();
	}

	protected String makeDateArgument(Date date) {
		return Long.toString(date.getTime());
	}
}
