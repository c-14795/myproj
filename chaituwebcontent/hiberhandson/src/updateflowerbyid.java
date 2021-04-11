import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class updateflowerbyid {

	public static void update(Session s, int id) {

		flower f = (flower) s.get(flower.class, id);
		if (f != null) {
			System.out.println("enter the price to  update");
			f.setPrice(new Scanner(System.in).nextInt());
			s.getTransaction().begin();
			s.update(f);
			s.getTransaction().commit();
		} else {
			System.out.println("enter a valid id");
		}
	}

	public static void main(String[] args) {
		Configuration c = new Configuration();
		Session s = c.configure().buildSessionFactory().openSession();
		System.out.println("enter the flower id");
		int id = new Scanner(System.in).nextInt();
		update(s, id);
		display(s, id);
		s.close();

	}

	private static void display(Session s, int id) {
		flower f = (flower) s.get(flower.class, id);
		System.out.println(f.getFlowerid() + f.getPrice() + f.getColor() + f.getFlowername());

	}
}
