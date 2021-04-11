       
import java.io.IOException;

import java.io.PrintWriter;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class second
 */
@WebServlet("/second")
public class second extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public second() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		 String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		 Pattern pattern = Pattern.compile(regex);
		 Matcher matcher = pattern.matcher(request.getParameter("email"));
		 System.out.print(request.getParameter("email"));
		 System.out.println(" : "+ matcher.matches());
	
	 
	     if(!matcher.matches()){
	    	
	    	 out.print("please enter a valid email address");
	         }
	     else
	     {
	    	 out.print("valid");
	     }
	     
	}

}
