package uk.co.unclealex.rokta.encodable;

import org.apache.commons.lang.StringUtils;

import uk.co.unclealex.rokta.filter.IllegalFilterEncodingException;
import uk.co.unclealex.rokta.model.dao.GameDao;

public abstract class AbstractEncodable<T extends Encodable> implements Encodable<T> {

	private EncodableFactory<T> i_encodableFactory;
	private GameDao i_gameDao;
	
	public void decode(String encoded) throws IllegalFilterEncodingException {
		decodeInfo(encoded.substring(1));
	}

	public final String encode() {
		return getEncodingPrefix() + encodeInfo();
	}
	
	public boolean isDecodable(String encoded) {
		return !StringUtils.isEmpty(encoded) && encoded.charAt(0) == getEncodingPrefix();
	}
	
	protected abstract void decodeInfo(String encodingInfo) throws IllegalFilterEncodingException;
	protected abstract String encodeInfo();
	protected abstract char getEncodingPrefix();
	
	public GameDao getGameDao() {
		return i_gameDao;
	}
	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}
	public EncodableFactory<T> getEncodableFactory() {
		return i_encodableFactory;
	}
	
	public void setEncodableFactory(EncodableFactory<T> encodableFactory) {
		i_encodableFactory = encodableFactory;
	}	
}
