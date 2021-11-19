
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;


public class AdminChecker extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
  throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    PrintWriter out = response.getWriter();
    out.println("admin");
	}

}