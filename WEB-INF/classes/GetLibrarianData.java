
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

public class GetLibrarianData extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        Dbclass db = new Dbclass();
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");
        System.out.println("cookies"+rawCookie);
        JSONArray ja = new JSONArray();
        try{
            if(rawCookie.length() > 0){
            String authRole = db.roleChecker(pcid,rawCookie);
            if(authRole.equals("admin")){
                ArrayList<ArrayList<String>> graph = new ArrayList<>();
                graph = db.librarianGetList();
                for (int count = 0; count < graph.size(); count++) {
                    JSONObject jo = new JSONObject();
                    try {
                            jo.put("Username", graph.get(count).get(0));
                            jo.put("FirstName", graph.get(count).get(1));
                            jo.put("LastName", graph.get(count).get(2));
                            jo.put("Email", graph.get(count).get(3));
                            jo.put("Mobile", graph.get(count).get(4));
                            jo.put("Books", graph.get(count).get(5));
                            ja.put(jo);	
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
        out.println(ja);
        out.flush();
    }
}