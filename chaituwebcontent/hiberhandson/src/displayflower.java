import java.util.List;
import java.util.Iterator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class displayflower {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Configuration c = new Configuration();
		Session s = c.configure().buildSessionFactory().openSession();
		Query q = s.createQuery("from flower where name=?");
		q.setParameter(0, "carnation");
		List<flower> fl = q.list();
		Iterator<flower> it = fl.iterator();
		if (it.hasNext()) {

			flower f1 = (flower) it.next();
			System.out.println(f1.getColor() + f1.getFlowerid() + f1.getFlowername() + f1.getPrice());
		}

	}

}
