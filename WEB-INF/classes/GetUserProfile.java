
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

public class GetUserProfile extends HttpServlet {

    // get user data
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        Dbclass db = new Dbclass();
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        JSONObject ja = new JSONObject();
        ja = db.getUserProfile(request.getRemoteUser());
        out.println(ja);
        out.flush();
    }
}