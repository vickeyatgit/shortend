
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


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            PrintWriter out = response.getWriter();
            String email = request.getParameter("email");
            String name = request.getParameter("name");
            String mobile = request.getParameter("mobile");
            String pass = request.getParameter("pass");
            Boolean check = db.makeLoginCredentials(email, name, mobile, pass);
            JSONObject resSent = new JSONObject();
            try {
                resSent.put("result",check);
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
            }
            out.println(resSent);
            out.flush();
            out.close();
    }
	
}
