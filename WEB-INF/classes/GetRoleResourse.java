
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import dbaction.Dbclass;
import java.util.ArrayList;
import org.json.*; 

public class AdmiGetRoleResourse extends HttpServlet {

  Dbclass db = new Dbclass();

  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
  throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    PrintWriter out = response.getWriter();
    JSONArray ja = new JSONArray();
    ja=db.listOfUnRole();
    out.println(ja);
    out.flush();
    out.close();

	}

  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
  throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    PrintWriter out = response.getWriter();
    // String op[] = db.checkLoginCredentials("mywps010@gmail.com", "PointBreak@1999");
    // out.println("check");
  }

}