
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

    // remover book from rack
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String bookId =request.getParameter("bookid");
        String bookkCount = request.getParameter("bookcount");
        PrintWriter out = response.getWriter();
        String check = "Try Again SomeTime";
        try {
            int businessId = db.getBusinessId(request.getRemoteUser());
            check = db.RemoveBooks(bookId, bookkCount,businessId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println(check);
		out.flush();
    }
}