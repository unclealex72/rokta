import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.unclealex.rokta.model.Round;
import uk.co.unclealex.rokta.model.dao.RoundDao;

/**
 * 
 */

/**
 * @author alex
 *
 */
public class TestRokta {

	/**
	 * @param context
	 */
	private void run(ApplicationContext context) {
		RoundDao roundDao = (RoundDao) context.getBean("roundDao");
		List<Round> rounds = roundDao.getFinalRounds();
		for (Round round : rounds) {
			System.out.println("Round " + round.getId() + ", " + round.getPlays().size());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
		new TestRokta().run(context);
	}
}
