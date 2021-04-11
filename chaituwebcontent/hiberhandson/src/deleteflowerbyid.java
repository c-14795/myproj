import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class deleteflowerbyid {
public static void main(String[] args) {
	Configuration c=new Configuration();
	Session s=c.configure().buildSessionFactory().openSession();
	flower f=(flower) s.get(flower.class, 101);
	if (f!=null) {
		
	s.getTransaction().begin();
	s.delete(f);
	s.getTransaction().commit();

	}
	else
	{
		System.out.println("enter a valid id");
	}
	

}
}
