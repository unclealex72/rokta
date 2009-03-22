package uk.co.unclealex.rokta.model.dao;

import java.util.SortedSet;
import java.util.TreeSet;

import uk.co.unclealex.rokta.model.Colour;

public class HibernateColourDao extends HibernateStoreRemoveDao<Colour> implements ColourDao {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.ColourDao#getColourByName(java.lang.String)
	 */
	public Colour getColourByName(final String name) {
		return (Colour) getSession().getNamedQuery("colour.byName").setString("name", name).uniqueResult();
	}
	
	public Colour getColourByHtmlName(final String name) {
		return (Colour) getSession().getNamedQuery("colour.byHtmlName").setString("name", name).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.ColourDao#getEverybody()
	 */
	public SortedSet<Colour> getColours() {
		return new TreeSet<Colour>(getSession().getNamedQuery("colour.getAll").list());
	}
}
