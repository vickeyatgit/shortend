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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        Dbclass db = new Dbclass();
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ArrayList<String> roles = new ArrayList<String>();
        ArrayList<String> listOfRes = new ArrayList<String>();
        String role = "";
        roles = db.getAllRole();
        for(int i=0; i<roles.size(); i++){
            System.out.println("role: " + roles.get(i));
            Boolean isRole = (Boolean) request.isUserInRole(roles.get(i));
            if(isRole){
                role = roles.get(i);
                System.out.println("isRole: " + roles.get(i));
                listOfRes = db.getResource(roles.get(i));
                break;
            }
        }

        PrintWriter out = response.getWriter();
        // JSONArray ja = new JSONArray();
        JSONObject result = new JSONObject();
        System.out.println("GetRoleNav=>"+request.getRemoteUser());
        // ja = db.roleAndResourse();
        try {
          if(role.length()>0){
            result.put("role", role);
            result.put("resources", listOfRes);
          }
          // for (int i=0; i<ja.length(); i++) {
          //   JSONObject item = ja.getJSONObject(i);
          //   String name = item.getString("role");
          //   Boolean checkRole = request.isUserInRole(name);
          //   if(checkRole){
          //     System.out.println(name);
          //     result.put("role", name);
          //     result.put("resources", item.getJSONArray("resources"));
          //     // JSONArray temp = item.getJSONArray("resources");
          //     // System.out.println(temp.length());
          //     // for (int j = 0; j < temp.length(); j++) {
          //     //   String pet = temp.getString(j);
          //     //   System.out.println(i+j + pet);
          //     // }
          //   }                
          //   }
        } catch (Exception e) {
          e.printStackTrace();
        }
        out.println(result);
        out.flush();
    }
}
/*
ArrayList<String> list = new ArrayList<String>();
list = db.getResource(role);
JSONArray ja = new JSONArray();
ja.put("role", role);
ja.put("resources", list);

*/ 