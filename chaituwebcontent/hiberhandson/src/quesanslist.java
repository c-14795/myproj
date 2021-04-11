import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class quesanslist {

	public static void main(String[] args) {
		try {
			Configuration c = new Configuration();
			Session s = c.configure().buildSessionFactory().openSession();
			Question q = new Question();
			q.setQanswer("answer");
			q.setQname("first");
			List<Answer> list = new ArrayList<Answer>();
			list.add(new Answer("answer,true,false,error"));
			q.setOption(list);
			Transaction tx = s.beginTransaction();
			s.save(q);
			tx.commit();
			s.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
