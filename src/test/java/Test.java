import java.util.SortedSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.model.dao.GameDao;
import uk.co.unclealex.rokta.model.dao.PersonDao;

/**
 * 
 */

/**
 * @author alex
 *
 */
public class Test {

	/**
	 * @param context
	 */
	private void run(ApplicationContext context) {
		GameDao gameDao = (GameDao) context.getBean("gameDao");
		PersonDao personDao = (PersonDao) context.getBean("personDao");
		
		SortedSet<Person> everybody = personDao.getEverybody();
		Game lastGame = gameDao.getLastGame();
		System.out.println("Last game: " + lastGame.getId());
		
		for (Person person : everybody) {
			System.out.println(person.getName());
			Game lastGameForPerson = gameDao.getLastGamePlayed(person);
			System.out.println(person.getName() + ": " + lastGameForPerson.getId());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
		new Test().run(context);
	}
}
