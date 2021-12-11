import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import dbaction.Dbclass;

public class GetRoleNav extends HttpServlet {

  //get role and resource of particular user
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        Dbclass db = new Dbclass();
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ArrayList<String> roles = new ArrayList<String>();
        ArrayList<String> listOfRes = new ArrayList<String>();
        String role = "";
        System.out.println("in get role nav");
        System.out.println((Boolean) request.isUserInRole("owner"));
        if((Boolean) request.isUserInRole("owner")){
          listOfRes = db.getResource("owner",0);
          role = "owner";
          for(String res : listOfRes){
            System.out.println(res);
          }
        }
        else{
          int businessId = db.getBusinessId(request.getRemoteUser());
          roles = db.getAllRole(businessId);
          for(int i=0; i<roles.size(); i++){
            System.out.println("role: " + roles.get(i));
            Boolean isRole = (Boolean) request.isUserInRole(roles.get(i));
            if(isRole){
                role = roles.get(i);
                System.out.println("isRole: " + roles.get(i));
                listOfRes = db.getResource(roles.get(i),businessId);
                break;
            }
          }
        }

        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();
        System.out.println("GetRoleNav=>"+request.getRemoteUser());
        try {
          if(role.length()>0){
            result.put("role", role);
            result.put("resources", listOfRes);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        out.println(result);
        out.flush();
    }
}
