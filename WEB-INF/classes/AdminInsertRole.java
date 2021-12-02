import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import dbaction.Dbclass;
import java.util.ArrayList;
import org.json.*; 
import java.util.Arrays;

public class AdminInsertRole extends HttpServlet {

  Dbclass db = new Dbclass();

  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
  throws ServletException, IOException {
    System.out.println("AdminInsertRole");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    PrintWriter out = response.getWriter();
    String role = request.getParameter("role");
    String[] resource = request.getParameterValues("resource");
    System.out.println("role: " + role);
    System.out.println("resource: ");
    System.out.println(Arrays.toString(resource));
    System.out.println(resource.length);
    // for(int i=0;i<resource.length;i++)
    // {
    //     System.out.println(resource[i]);
    // }
    // System.out.println("resource: " + resource);
    // String[] innerArray=resource[0].split(",");
    // System.out.println("innerArray: " + innerArray);

    // System.out.println("role: " + role);
    // for(String s: resource) {
    //   System.out.println(s);
    // }
    // Boolean res = db.insertRoleAndResource(role, resource);
    // JSONArray ja = new JSONArray();
    // ja=db.listOfUnRole();
    out.println(Arrays.toString(resource));
    out.flush();
    out.close();

	}

  protected void doPost(HttpServletRequest request, HttpServletResponse response) 
  throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    // response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    PrintWriter out = response.getWriter();
    // String op[] = db.checkLoginCredentials("mywps010@gmail.com", "PointBreak@1999");
    // out.println("check");
    String role = request.getParameter("role");
    String[] resource = request.getParameterValues("resource");
    System.out.println("role: " + role);
    System.out.println("resource: ");
    System.out.println(Arrays.toString(resource));
    System.out.println(resource.length);
  }

}