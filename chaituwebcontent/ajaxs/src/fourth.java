import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class fourth
 */
@WebServlet("/fourth")
public class fourth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public fourth() {
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
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println(request.getParameter("order"));
		if (request.getParameter("order").equals("\"1\"")) {

			out.print("<p>Chapter1:Nursery rhymes not only tell stories, they also help the children to learn and understand those wonderful sounds of any language. We all know how rhyming words add magic to the poems and it becomes easy for the kids to grasp the fundamentals of the language. Kidsworldfun.com is one such attempt that wants to help teachers, parents and small kids to learn English in a small fun way through the language of poems.</p>");
			System.out.println("working");
		} else if (request.getParameter("order").equals("\"2\"")) {

			out.print("<p>Chapter2ul sounds of any language. We all know how rhyming words add magic to the poems and it becomes easy for the kids to grasp the fundamentals of the language. Kidsworldfun.com is one such attempt that wants to help teachers, parents and small kids to learn English in a small fun way through the language of poems.</p>");
		} else if (request.getParameter("order").equals("\"3\"")) {

			out.print("<p>Chapter3:Nursery rhymes nos of any language. We all know how rhyming words add magic to the poems and it becomes easy for the kids to grasp the fundamentals of the language. Kidsworldfun.com is one such attempt that wants to help teachers, parents and small kids to learn English in a small fun way through the language of poems.</p>");
		}

		else if (request.getParameter("order").equals("\"4\"")) {

			out.print("<p>Chapter4:Nursery rhymes not only tell stories, toems and it becomes easy for the kids to grasp the fundamentals of the language. Kidsworldfun.com is one such attempt that wants to help teachers, parents and small kids to learn English in a small fun way through the language of poems.</p>");
		}

	}

}
