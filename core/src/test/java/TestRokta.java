import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
		context.getBean("personDao");
		//List<Round> rounds = roundDao.getFinalRounds();
		//for (Round round : rounds) {
		//	System.out.println("Round " + round.getId() + ", " + round.getPlays().size());
		//}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext-test.xml");
		new TestRokta().run(context);
	}
}
