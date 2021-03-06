
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
import dbaction.Dbclass;

public class CreateOrg extends HttpServlet {

  // check if business name is already in use
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setHeader("Access-Control-Allow-Credentials", "true");
    PrintWriter out = response.getWriter();

    Dbclass db = new Dbclass();
    String name = request.getParameter("name");
    Boolean result = db.getExtstingBusiness(name);
    JSONObject jo = new JSONObject();
    try {
      jo.put("result", result);
    } catch (Exception e) {
      e.printStackTrace();
    }
    out.print(jo);
    out.flush();
    out.close();
  }

  // create user with owner of org and info of org
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setContentType("text/html");
    
    PrintWriter out = response.getWriter();
    
    String name = request.getParameter("name");
    // String org = request.getParameter("org");
    String org = "";
    String mobile = request.getParameter("mobile");
    String user = request.getParameter("user");
    String pass = request.getParameter("pass");
    String url = request.getParameter("url");
    
    System.out.println("name: " + name);
    System.out.println("org: " + org);
    System.out.println("mobile: " + mobile);
    System.out.println("user: " + user);
    System.out.println("pass: " + pass);
    System.out.println("url: " + url);
    Dbclass db = new Dbclass();
    Boolean result = db.createOwner(name, mobile, user, pass, url);

    JSONObject jo = new JSONObject();
    try {
      jo.put("result", result);
    } catch (Exception e) {
      e.printStackTrace();
    }

    out.print(jo);
    out.flush();
    out.close();
  
  }
}