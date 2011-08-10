package uk.co.unclealex.rokta.server.dao;

import java.util.Date;
import java.util.SortedSet;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.server.model.Person;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

@Transactional
public class HibernatePersonDao extends HibernateKeyedDao<Person> implements PersonDao {

	public Person getPersonByName(final String name) {
		return uniqueResult(getSession().getNamedQuery("person.findByName").setString("name", name));
	}
	
	public SortedSet<Person> getPlayers() {
		return asSortedSet(getSession().getNamedQuery("person.getPlayers"));
	}

	public Person findPersonByNameAndPassword(String name, String encodedPassword) {
		Criteria criteria = getSession().createCriteria(Person.class);
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("password", encodedPassword));
		return uniqueResult(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SortedSet<String> getPlayerNamesWhoHavePlayedSince(Date date) {
		Query query = getSession().createQuery(
				"select u.name, max(g.datePlayed) " +
				"from Game g join g.rounds r join r.plays p join p.person u group by u.name having max(g.datePlayed) >= :date");
		query.setDate("date", date);
		Function<Object, String> toNameFunction = new Function<Object, String>() {
			@Override
			public String apply(Object row) {
				return (String) ((Object[]) row)[0];
			}
		};
		return Sets.newTreeSet(Iterables.transform(query.list(), toNameFunction));
	}
	@Override
	public Person createExampleBean() {
		return new Person();
	}
}
