
import org.hibernate.*;

public class Client {
	public static void main(String[] args) {
		// Create Session object
		Session session = HibernateUtil.getSessionFactory().openSession();
		// Perform life-cycle operations under a transaction
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			// Crеatеa Studеnt1 objеct and savеit
			Student s1 = new Student();
		
			s1.setId(35);
			s1.setNamе("chaitu");
			session.save(s1);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {
			session.close();
			
		}
	}
}
