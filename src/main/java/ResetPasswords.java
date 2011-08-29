import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.unclealex.rokta.server.process.PersonService;
import uk.co.unclealex.rokta.server.service.NewGameService;


public class ResetPasswords {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext("applicationContext-rokta-core.xml");
		ctxt.getBean(PersonService.class).resetAllPasswords();
		ctxt.getBean(NewGameService.class).updateGames();
		ctxt.close();
	}

}
