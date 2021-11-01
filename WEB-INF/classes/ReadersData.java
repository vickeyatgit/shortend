
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        JSONArray ja = new JSONArray();
        // ja = db.librarianBook();
        ja = db.readersData();
        out.println(ja);
    }
}
