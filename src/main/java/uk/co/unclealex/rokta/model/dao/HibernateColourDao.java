package uk.co.unclealex.rokta.model.dao;

import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import uk.co.unclealex.rokta.model.Colour;

public class HibernateColourDao extends HibernateDaoSupport implements ColourDao {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.ColourDao#getColourByName(java.lang.String)
	 */
	public Colour getColourByName(final String name) {
		return (Colour) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						return session.getNamedQuery("colour.byName").setString("name", name).uniqueResult();
					}
				});
	}
	
	public Colour getColourByHtmlName(final String name) {
		return (Colour) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						return session.getNamedQuery("colour.byHtmlName").setString("name", name).uniqueResult();
					}
				});
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.ColourDao#getEverybody()
	 */
	public SortedSet<Colour> getColours() {
		return new TreeSet<Colour>(getHibernateTemplate().findByNamedQuery("colour.getAll"));
	}
}
