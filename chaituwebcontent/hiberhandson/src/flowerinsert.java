import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class flowerinsert {

	public static void main(String[] args) {

	Configuration c=new  Configuration();
	Session s=c.configure().buildSessionFactory().openSession();
	s.getTransaction().begin();
	flower f= new  flower();
	f.setColor("red");
	f.setFlowerid(1001);
	f.setFlowername("Rose");
	f.setPrice(10);
	s.save(f);
	s.getTransaction().commit();
		
	}

}
