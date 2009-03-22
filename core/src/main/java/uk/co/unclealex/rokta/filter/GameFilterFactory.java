package uk.co.unclealex.rokta.filter;

import uk.co.unclealex.rokta.encodable.EncodableFactory;

public interface GameFilterFactory extends EncodableFactory<GameFilter> {

	public static final char ALL_PREFIX = 'a';
	public static final char WEEK_PREFIX = 'w';
	public static final char MONTH_PREFIX = 'm';
	public static final char YEAR_PREFIX = 'y';
	public static final char BEFORE_PREFIX = 'b';
	public static final char SINCE_PREFIX = 's';
	public static final char BETWEEN_PREFIX = 't';
	public static final char PREDICATE_PREFIX = 'p';	
}
