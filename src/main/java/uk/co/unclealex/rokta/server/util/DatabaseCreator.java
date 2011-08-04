package uk.co.unclealex.rokta.server.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import uk.co.unclealex.rokta.server.model.Person;

public class DatabaseCreator {

	private static final SessionFactory sessionFactory;

    static {
        try {

        	Configuration configuration = new AnnotationConfiguration().configure();
        	configuration.setProperty("hibernate.hbm2ddl.auto", "create");
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Log exception!
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession()
            throws HibernateException {
        return sessionFactory.openSession();
    }
    
    public static void main(String[] args) {
    	Session session = getSession();
    	Transaction transaction = session.beginTransaction();
    	for(String name : new String[] { "Alex", "Andy", "Anne-Marie", "Marc", "Tony" }) {
    		Person person = new Person();
    		person.setName(name);
    		session.save(person);
    	}
    	transaction.commit();
		System.out.println("Database schema created");
	}
}
