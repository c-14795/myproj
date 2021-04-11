package hiberhandson;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Hiberannotations {

	public static void main(String[] args) {

		Employee e = new Employee();
		e.setName("sabarish");
		Configuration c = new Configuration();
		c.configure();
		SessionFactory sf = c.buildSessionFactory();
		Session s = sf.openSession();
		s.beginTransaction();
		s.save(e);
		s.getTransaction().commit() ;

	}

}
