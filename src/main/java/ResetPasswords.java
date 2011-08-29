import org.springframework.context.support.ClassPathXmlApplicationContext;

import uk.co.unclealex.rokta.server.process.PersonService;


public class ResetPasswords {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctxt = new ClassPathXmlApplicationContext("applicationContext-rokta-core.xml");
		PersonService personService = ctxt.getBean(PersonService.class);
		personService.resetAllPasswords();
		ctxt.close();
	}

}
