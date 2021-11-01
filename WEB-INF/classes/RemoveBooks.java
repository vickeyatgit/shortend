
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import dbaction.Dbclass;
import org.json.*;
import java.io.IOException;


public class RemoveBooks extends HttpServlet {

    Dbclass db = new Dbclass();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String bookId =request.getParameter("bookid");
        String bookkCount = request.getParameter("bookcount");
        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");
        PrintWriter out = response.getWriter();
        String check = "Try Again SomeTime";
        if(rawCookie.length() > 0){
            String authRole = db.roleChecker(pcid,rawCookie);
            if(authRole.equals("librarian")){
                try {
                    check = db.RemoveBooks(bookId, bookkCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                check = "Invalid Authorization";
            }
        }        
        out.println(check);
		out.flush();
    }
}