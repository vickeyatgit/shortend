import dbaction.Dbclass;
import dbaction.TokenCheck;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;
import org.json.*; 
import java.util.Arrays;

public class CreateOrg extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    
    PrintWriter out = response.getWriter();
    
    String first = request.getParameter("first");
    String last = request.getParameter("last");
    String org = request.getParameter("org");
    String mobile = request.getParameter("mobile");
    String user = request.getParameter("user");
    String pass = request.getParameter("pass");
  
  }
}