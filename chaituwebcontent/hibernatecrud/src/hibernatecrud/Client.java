package hibernatecrud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Client {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
//			Employee s1 = new Employee();
//			s1.setName("chaitu");
//		session.save(s1);
//			session.flush();
		tx.commit();
		String []s={"Chaitanya","AbdulRahim","BhanuPrakash","SatyaSabarish","Siddalinga"};
			for (int i = 0; i < s.length; i++) {
			tx = session.beginTransaction();
			Employee s2 = new Employee();
			s2.setName(s[i]);
			session.save(s2);
			tx.commit();
				
		}

		//	getEmployees(session);

	//	updateEmployee(session);
			System.out.println("enter the id and name respectively");
		updateemp(new Scanner(System.in).nextInt(),new Scanner(System.in).next(),session);
	//	delete(30,session);
		
			
		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {
			session.close();
			
		}
	}            

	private static void delete(int id, Session session) {
		// TODO Auto-generated method stub
		
		Employee e= (Employee) session.get(Employee.class,id);
		session.delete(e);
		System.out.println("being deleted");
		Transaction s=session.beginTransaction();
		s.commit();
		
	}

	private static void updateemp(int id, String name, Session session) {
		
		Employee e=(Employee) session.get(Employee.class, id);
		
		if (e!=null) {
			e.setName(name);
		System.out.println("geting updatedddd");
		session.update(e);
		Transaction updatee=session.beginTransaction();
		updatee.commit();
		
		}
		else
		{
			System.out.println("invalid id");
		}
	}

	private static void updateEmployee(Session session) {
		Transaction update= session.beginTransaction();
		try {
			
		Query q= session.createQuery("update Employee SET name=? where id=?");
		q.setParameter(0, "annie");
		q.setParameter(1, 16);
		     
		System.out.println("Employee is getting updated");
		q.executeUpdate();
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}update.commit();
	}

	private static void getEmployees(Session session) {
		Transaction retrive=session.beginTransaction();
		ArrayList<Employee> r=(ArrayList<Employee>) session.createQuery("From Employee").list();
		Iterator<Employee> i=r.iterator();
while (i.hasNext()) {
		Employee e = (Employee) i.next();
		System.out.println(e.getId()+e.getName());
}
	}
}
