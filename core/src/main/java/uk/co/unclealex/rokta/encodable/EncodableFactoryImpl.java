package uk.co.unclealex.rokta.encodable;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import uk.co.unclealex.rokta.filter.IllegalFilterEncodingException;
import uk.co.unclealex.rokta.model.dao.GameDao;

public class EncodableFactoryImpl<T extends Encodable> implements EncodableFactory<T> {

	private GameDao i_gameDao;
	private List<T> i_encodableTemplates;
	
	public T decode(String encodedGameFilter) throws IllegalFilterEncodingException {		
		if (StringUtils.isEmpty(encodedGameFilter)) {
			throw new IllegalArgumentException("An encoded game filter cannot be empty.");
		}
		for (T template : getEncodableTemplates()) {
			template.setEncodableFactory(this);
			if (template.isDecodable(encodedGameFilter)) {
				template.decode(encodedGameFilter);
				return template;
			}
		}
		throw new IllegalFilterEncodingException("No game filter could be found for encoding " + encodedGameFilter);
	}

	public String encode(T encodable) {
		return encodable.encode();
	}

	public GameDao getGameDao() {
		return i_gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		i_gameDao = gameDao;
	}

	public List<T> getEncodableTemplates() {
		return i_encodableTemplates;
	}

	public void setEncodableTemplates(List<T> encodableTemplates) {
		i_encodableTemplates = encodableTemplates;
	}

}
