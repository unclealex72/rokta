package uk.co.unclealex.rokta.encodable;

import uk.co.unclealex.rokta.filter.IllegalFilterEncodingException;

public interface EncodableFactory<T extends Encodable> {

	public String encode(T encodable);
	public T decode(String encoded) throws IllegalFilterEncodingException;

}
