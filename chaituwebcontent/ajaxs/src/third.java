

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class third
 */
@WebServlet("/third")
public class third extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public third() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		double principal=Double.parseDouble(request.getParameter("principal"));
		double interest=Double.parseDouble(request.getParameter("Interest"))/100;
		double time=Double.parseDouble(request.getParameter("time"));
		String amount=String.valueOf(principal*(1+((interest)*time)));
		out.print(amount);
		System.out.println(amount);
	}

}
