package hibernatecrud;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();

		} catch (Throwable e) {
			e.printStackTrace();
			System.err.println("intial SessionFactory Creation failes" + e);
			throw new ExceptionInInitializerError(e);

		}

	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}