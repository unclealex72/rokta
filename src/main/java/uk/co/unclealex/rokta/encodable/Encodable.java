package uk.co.unclealex.rokta.encodable;

import uk.co.unclealex.rokta.filter.IllegalFilterEncodingException;

public interface Encodable<T extends Encodable> {

	public String encode();

	public boolean isDecodable(String encoded);
	public void decode(String encoded) throws IllegalFilterEncodingException;
	public void setEncodableFactory(EncodableFactory<T> encodableFactory);

}
