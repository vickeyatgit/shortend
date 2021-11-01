
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

        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");

        if(rawCookie.length() > 0){
            String authRole = db.roleChecker(pcid,rawCookie);
            if(authRole.equals("librarian")){
                ja = db.librarianBook();
            }
        }
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println(ja);
        out.println(ja);


    }
}
