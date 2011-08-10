package uk.co.unclealex.rokta.server.dao;

import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.server.model.Colour;

@Transactional
public class HibernateColourDao extends HibernateKeyedDao<Colour> implements ColourDao {

	public Colour getColourByName(final String name) {
		return uniqueResult(getSession().getNamedQuery("colour.byName").setString("name", name));
	}
	
	public Colour getColourByHtmlName(final String name) {
		return uniqueResult(getSession().getNamedQuery("colour.byHtmlName").setString("name", name));
	}

	@Override
	public Colour createExampleBean() {
		return new Colour();
	}
}
