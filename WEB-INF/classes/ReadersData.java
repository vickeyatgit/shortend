
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import dbaction.Dbclass;
import org.json.*;
import java.io.IOException;

public class ReadersData extends HttpServlet {
    Dbclass db = new Dbclass();
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // response.setHeader("Access-Control-Allow-Origin", "*");
        // String rawCookie = request.getHeader("Cookie");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        PrintWriter out = response.getWriter();
        String user = request.getRemoteUser();
        out.println(user);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        PrintWriter out = response.getWriter();
        JSONArray ja = new JSONArray();
        // ja = db.librarianBook();
        ja = db.readersData();
        out.println(ja);
    }
}
