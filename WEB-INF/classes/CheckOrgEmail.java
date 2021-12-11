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

public class CheckOrgEmail extends HttpServlet {

  // check business name exist or not
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    PrintWriter out = response.getWriter();
    // String url = request.getParameter("org");
    // DbCreateOrg db = new DbCreateOrg();
    // Boolean check = db.checkOrgUrl(url);
    // JSONObject json = new JSONObject();
    // try {
    //   json.put("check", check);
    // } catch (JSONException e) {
    //   e.printStackTrace();
    // }
    // out.print(json);
    out.flush();
    out.close();
  }

  // check email already exist or not
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String email = request.getParameter("email");
    Dbclass db = new Dbclass();
    Boolean check = db.checkEmail(email);
    JSONObject json = new JSONObject();
    try {
      json.put("check", check);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    out.print(json);
    out.flush();
    out.close();
  
  }
}