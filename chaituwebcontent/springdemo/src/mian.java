
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class mian {
	public static void main(String[] args) {
		try {
			ApplicationContext c = new ClassPathXmlApplicationContext("Beans.xml");
			demo d = (demo) c.getBean("d");
			System.out.println(d.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
