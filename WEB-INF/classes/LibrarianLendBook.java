
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import dbaction.Dbclass;
import org.json.*;
import java.io.IOException;

public class LibrarianLendBook extends HttpServlet {
    

    Dbclass db = new Dbclass();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String bookId = request.getParameter("bookid");
        String UserName = request.getParameter("username");
        String check="Try Again SomeTime";
        PrintWriter out = response.getWriter();
        try {
            check = db.lendBook(bookId, UserName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        out.println(check);
		out.flush();
    }
}
