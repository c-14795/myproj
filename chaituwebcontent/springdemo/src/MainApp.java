

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
	 public static void main(String[] args) {
	     
		 /*
		 HelloWorld hw= new HelloWorld();
		 hw.setMessage("Feeling Sleepy");
		 hw.getMessage();
		 */
		 
		  
		 ApplicationContext context = 
	             new ClassPathXmlApplicationContext("Beans.xml");

	      demo obj = (demo) context.getBean("helloWorld");
	      obj.getMessage();
	     
		 
		 
	      
	   }
}
