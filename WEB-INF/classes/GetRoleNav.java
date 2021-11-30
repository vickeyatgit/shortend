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
        PrintWriter out = response.getWriter();
        JSONArray ja = new JSONArray();
        JSONObject result = new JSONObject();
        ja = db.roleAndResourse();
        try {
          for (int i=0; i<ja.length(); i++) {
            JSONObject item = ja.getJSONObject(i);
            String name = item.getString("role");
            Boolean checkRole = request.isUserInRole(name);
            if(checkRole){
              System.out.println(name);
              result.put("role", name);
              result.put("resources", item.getJSONArray("resources"));
              // JSONArray temp = item.getJSONArray("resources");
              // System.out.println(temp.length());
              // for (int j = 0; j < temp.length(); j++) {
              //   String pet = temp.getString(j);
              //   System.out.println(i+j + pet);
              // }
            }                
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        out.println(result);
        out.flush();
    }
}