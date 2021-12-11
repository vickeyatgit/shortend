
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dbaction.Dbclass;
import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;
import javax.servlet.*;



public  class CheckReader extends HttpServlet {
    Dbclass db = new Dbclass();

    // check reader is valid or not
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        Boolean userCheck = false;
        try {
            String names = request.getRemoteUser();
            int businessId = db.getBusinessId(names);
            if (businessId > 0) 
            {
                userCheck = db.ReadersCheck(username,businessId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userCheck) {
            out.println("s");
			out.flush();
        }
        else {
            out.println("f");
			out.flush();
        }
    }
}