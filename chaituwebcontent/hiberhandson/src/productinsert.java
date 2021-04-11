import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class productinsert {

	public static void main(String[] args) {

		Configuration c = new Configuration();
		Session s = c.configure().buildSessionFactory().openSession();
		s.getTransaction().begin();
		String name[] = { "a", "f", "s", "d", "g" };
		int[] price = { 12154, 12465, 56487, 65455, 45645 };
		for (int i = 0; i < price.length; i++) {
			product f = new product();
			f.setName(name[i]);
			f.setPrice(price[i]);
			s.save(f);

		}
		s.getTransaction().commit();
	}
}
