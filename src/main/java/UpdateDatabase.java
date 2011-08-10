import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import uk.co.unclealex.rokta.server.model.Game;


public class UpdateDatabase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctxt = new FileSystemXmlApplicationContext("file:/home/alex/workspace/rokta/src/main/resources/applicationContext-rokta-core.xml");
		SessionFactory sessionFactory = ctxt.getBean(SessionFactory.class);
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Game");
		@SuppressWarnings("unchecked")
		List<Game> games = (List<Game>) query.list();
		Calendar cal = new GregorianCalendar();
		for (Game game : games) {
			cal.setTime(game.getDatePlayed());
			game.setDayPlayed(cal.get(Calendar.DAY_OF_MONTH));
			game.setWeekPlayed(cal.get(Calendar.WEEK_OF_YEAR));
			game.setMonthPlayed(cal.get(Calendar.MONTH));
			game.setYearPlayed(cal.get(Calendar.YEAR));
			session.update(game);
		}
		tx.commit();
	}
}
