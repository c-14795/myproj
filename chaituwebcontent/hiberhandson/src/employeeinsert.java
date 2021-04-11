import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class employeeinsert {
	public static void main(String[] args) {

		Configuration c = new Configuration();
		Session s = c.configure().buildSessionFactory().openSession();
		s.getTransaction().begin();
		String designation[]={"manager","a.manager","consultant","engineer","projmanager"};
		String name[]={"a","f","s","d","g"};
		int []salary={12154,12465,56487,65455,45645};
		for (int i = 0; i < salary.length; i++) {
			employee f = new employee();
			f.setDesignation(designation[i]);
			f.setName(name[i]);
			f.setSalary(salary[i]);
			s.save(f);
			
		}
		s.getTransaction().commit();
	}
}