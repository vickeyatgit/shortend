import dbaction.DbCreateOrg;
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

public class CheckOrgEmail extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // doPost(request, response);
    response.setHeader("Access-Control-Allow-Credentials", "true");
    PrintWriter out = response.getWriter();
    String url = request.getParameter("org");
    DbCreateOrg db = new DbCreateOrg();
    Boolean check = db.checkOrgUrl(url);
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

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String email = request.getParameter("email");
    DbCreateOrg db = new DbCreateOrg();
    Boolean check = db.checkOrgEmail(email);
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