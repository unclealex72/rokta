package uk.co.unclealex.rokta.encodable;

import uk.co.unclealex.rokta.filter.IllegalFilterEncodingException;

public abstract class AbstractEmptyEncodable<T extends Encodable> extends AbstractEncodable<T> {

	@Override
	protected void decodeInfo(String encodingInfo) throws IllegalFilterEncodingException {
		// Ignore any encoding info
	}

	@Override
	protected String encodeInfo() {
		return "";
	}

}
