package uk.co.unclealex.rokta.server.dao;

import uk.co.unclealex.hibernate.dao.KeyedDao;
import uk.co.unclealex.rokta.server.model.Colour;

public interface ColourDao extends KeyedDao<Colour> {

	public Colour getColourByName(String name);
	public Colour getColourByHtmlName(String name);
	public Colour getColourForUser(String username);
}