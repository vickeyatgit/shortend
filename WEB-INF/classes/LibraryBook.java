
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import dbaction.Dbclass;
import org.json.*;
import java.io.IOException;
import dbaction.TokenCheck;


public class LibraryBook extends HttpServlet {
    
    Dbclass db = new Dbclass();
    TokenCheck ck = new TokenCheck();
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //get books
        JSONArray ja = new JSONArray();
        System.out.println("clicked innnnn");
        PrintWriter out = response.getWriter();
        ja = db.librarianBook();
        System.out.println(ja);
        out.println(ja);


    }
}
