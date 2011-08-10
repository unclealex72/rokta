import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.google.common.base.Joiner;


public class HibernateQueries {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ApplicationContext ctxt = new FileSystemXmlApplicationContext("file:/home/alex/workspace/rokta/src/main/resources/applicationContext-rokta-core.xml");
		SessionFactory sessionFactory = ctxt.getBean(SessionFactory.class);
		Session session = sessionFactory.openSession();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while ((line = reader.readLine()) != null) {
			try {
				@SuppressWarnings("unchecked")
				List<Object> objs = session.createQuery(line).list();
				for (Object obj : objs) {
					if (obj.getClass().isArray()) {
						System.out.println(Joiner.on(", ").join((Object[]) obj));
					}
					System.out.println(obj);
				}
			}
			catch (Throwable t) {
				t.printStackTrace(System.err);
			}
		}
	}
}
