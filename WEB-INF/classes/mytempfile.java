// mytempfile.java

import java.io.IOException;
import org.json.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import dbaction.Dbclass;
import java.io.PrintWriter;
import dbaction.TokenCheck;

public class mytempfile extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            	Dbclass db = new Dbclass();
            	PrintWriter out = response.getWriter();
        		JSONArray hi = new JSONArray();
        		 hi = db.readersData();
            	out.println(hi);
         }
}