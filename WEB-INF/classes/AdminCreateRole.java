
import dbaction.Dbclass;
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

public class AdminCreateRole extends HttpServlet {

  Dbclass db = new Dbclass();

  // insert new role and resource
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    System.out.println("Adminauthority");
    PrintWriter out = response.getWriter();

    String role = request.getParameter("role");
    String resource = request.getParameter("resource");

    ArrayList<String> list = new ArrayList<>(Arrays.asList(resource.split(",")));
    String res[] = new String[list.size()];
    for (int j = 0; j < list.size(); j++) {
      res[j] = list.get(j);
    }
    int businessId = db.getBusinessId(request.getRemoteUser());
    Boolean result1 = db.createRole(role, res,businessId);
    JSONObject resSent = new JSONObject();
    if (result1) {
      try{
        resSent.put("result","pass");
      }catch(Exception e){
        e.printStackTrace();
      }
    }else {
      try{
        resSent.put("result","fail");
      }
      catch(Exception e){
        e.printStackTrace();
      }
    }
    out.println(resSent);
    out.flush();
    out.close();
  }
}