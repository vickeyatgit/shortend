import java.io.IOException;
import org.json.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import dbaction.Dbclass;
import java.io.PrintWriter;


public class LendingData extends HttpServlet {

    // get list of readers has books 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Dbclass db = new Dbclass();
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        JSONArray hi = new JSONArray();
        System.out.println("getting in");
        System.out.println("try");
        String names = request.getRemoteUser();
        int businessId = db.getBusinessId(names);
        hi = db.readersData(businessId);
        out.println(hi);
        out.flush();
    }

}