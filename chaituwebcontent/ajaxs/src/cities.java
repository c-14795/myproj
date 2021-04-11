import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class cities
 */
@WebServlet("/cities")
public class cities extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public cities() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String suggestion = "";
		String[] cities = { "Agartala", "Agra", "Agumbe", "Ahmedabad",
				"Aizawl", "Ajmer", "Alappuzha Beach", "Allahabad", "Alleppey",
				"Almora", "Amarnath", "Amritsar" };
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String s = request.getParameter("cname");
		if (s.length() > 0) {
			for (int i = 0; i < cities.length; i++) {
				if (cities[i].toUpperCase().startsWith(s.toUpperCase())) {
					suggestion = suggestion + cities[i] + "<br>";
				}
			}
			
		}
		
		out.print(suggestion);
	}
}
