
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class five
 */
@WebServlet("/five")
public class five extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public five() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (request.getParameter("states").equals("ap")) {
			out.print("<table><tr><td>Tirupathi</td></tr><tr><td>Vijayawada</td></tr><tr><td>Vishakapatanam</td></tr></table>");

		} else if (request.getParameter("states").equals("mh")) {
			out.print("<table><tr><td>Mumbai</td></tr><tr><td>Pune</td></tr><tr><td>Kolhapur</td></tr></table>");

		} else if (request.getParameter("states").equals("ke")) {
			out.print("<table><tr><td>Trivendrum</td></tr><tr><td>Kochi</td></tr><tr><td>Kannur</td></tr></table>");

		} else if (request.getParameter("states").equals("ka")) {
			out.print("<table><tr><td>Bangalore</td></tr><tr><td>Mangalore</td></tr><tr><td>Mysore</td></tr></table>");

		} else if (request.getParameter("states").equals("tn")) {
			out.print("<table><tr><td>Coimbatore</td></tr><tr><td>Chennai</td></tr><tr><td>Tirchy</td></tr></table>");

		}
	}

}
