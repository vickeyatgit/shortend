
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import dbaction.Dbclass;
import org.json.*;
import java.io.IOException;

public class LibraryBook extends HttpServlet {
    
    Dbclass db = new Dbclass();
    // get list of books
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        JSONArray ja = new JSONArray();
        String names = request.getRemoteUser();
        int businessId = db.getBusinessId(names);
        PrintWriter out = response.getWriter();
        ja = db.librarianBook(businessId);
        out.println(ja);
        out.close();
    }
}
