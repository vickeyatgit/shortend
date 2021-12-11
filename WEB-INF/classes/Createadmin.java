
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
import org.json.*; 

public class Createadmin extends HttpServlet {
    static Boolean check;
    Dbclass db = new Dbclass();
	
    // create user
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            PrintWriter out = response.getWriter();
            String email = request.getParameter("email");
            String name = request.getParameter("name");
            String mobile = request.getParameter("mobile");
            String pass = request.getParameter("pass");
            String org = request.getParameter("org");
            Boolean check = db.createUser(email, name, mobile, pass,org);
            JSONObject resSent = new JSONObject();
            try {
                resSent.put("result",check);
            } catch (Exception e) {
                e.printStackTrace();
            }
            out.println(resSent);
            out.flush();
            out.close();
    }
	
}
