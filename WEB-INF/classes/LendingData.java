import java.io.IOException;
import org.json.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import dbaction.Dbclass;
import java.io.PrintWriter;
import dbaction.TokenCheck;


public class LendingData extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Dbclass db = new Dbclass();
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        JSONArray hi = new JSONArray();
        System.out.println("getting in");
        System.out.println("try");

        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");

        if(rawCookie.length() > 0){
            String authRole = db.roleChecker(pcid,rawCookie);
            if(authRole.equals("librarian")){
                hi = db.readersData();
            }
        }
        out.println(hi);
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                // Dbclass db = new Dbclass();
                PrintWriter out = response.getWriter();
                // JSONArray hi = new JSONArray();
                // System.out.println("getting in");
                // try {
                //     System.out.println("try");
                //     hi = db.readersData();
                //     // out.println(hi);
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }
                // System.out.println("finished");
                out.println("finished");
                out.flush();

            }

}