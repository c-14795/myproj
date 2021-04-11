import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class crud {

	public static void main(String[] args) {

		try {
			Configuration c = new Configuration();
			c.configure("hibernate.cfg.xml");
			Session s = c.buildSessionFactory().openSession();
			insert(s);
//			retriveone(s, 101);
//		 	retriveall(s);
//			updatecname(s, 100);
//			delete(s,101);
			
			s.flush();
			s.clear();
			s.close();

		} catch (HibernateException e) {

			e.printStackTrace();
		}
	}

	private static void delete(Session s,int id) {
		child ch1 = (child) s.get(child.class, id);
		s.getTransaction().begin();
		s.delete(ch1);
		s.getTransaction().commit();
	}

	private static void updatecname(Session s, int id) {
		child ch1 = (child) s.get(child.class, id);
		ch1.setName("chaitanya");
		s.getTransaction().begin();
		s.update(ch1);
		s.getTransaction().commit();
	}

//	private static void retriveone(Session s, int childid) {
//		child ch = (child) s.get(child.class, childid);
//		System.out.println(ch.getChid() + ch.getName()
//				+ ch.getParentobj().getName() + ch.getParentobj().getPid());
//	}

	@SuppressWarnings("unchecked")
//	private static void retriveall(Session s) {
//		List<child> ch1 = s.createQuery("from child").list();
//		Iterator<child> it = ch1.iterator();
//		while (it.hasNext()) {
//			child Child = (child) it.next();
//			System.out.println(Child.getChid() + Child.getName());
//			System.out.println(Child.getParentobj().getName());
//		}
//	}

	private static void insert(Session s) {
		child ch = new child();
		child ch1 = new child();
		ch.setChid(100);
		ch.setName("Chaitu");
		ch1.setChid(101);
		ch1.setName("Siri");
		Set<parent> se= new HashSet<parent>();
		se.add(new parent("Vimala",10001));
		ch1.setParentobj(se);
		ch.setParentobj(se);
		Transaction tx = s.beginTransaction();
		s.save(ch);
		s.save(ch1);
		tx.commit();
	}
}
