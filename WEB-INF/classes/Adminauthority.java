
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

public class Adminauthority extends HttpServlet {
  private static final long serialVersionUID = 1L;
  Dbclass db = new Dbclass();

  //get role and resource of all user
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    PrintWriter out = response.getWriter();
    JSONArray ja = new JSONArray();
    int businessId = db.getBusinessId(request.getRemoteUser());
    System.out.println("businessId: " + businessId);
    ja = db.roleResourseCombine(businessId);
    out.println(ja);
    out.close();
  }


  //insert user and role
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    PrintWriter out = response.getWriter();
    Boolean result = false;
    String user = request.getParameter("username");
    String role = request.getParameter("role");
    ArrayList<String> list1 = new ArrayList<>(Arrays.asList(role.split(",")));
    String rol[] = new String[list1.size()];
    for (int j = 0; j < list1.size(); j++) {
      rol[j] = list1.get(j);
    }
    int businessId = db.getBusinessId(request.getRemoteUser());
    result = db.setNewRole(user,rol,businessId);
    JSONObject resSent = new JSONObject();
    if (result) {
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