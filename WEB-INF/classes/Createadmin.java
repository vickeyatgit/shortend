
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import dbaction.Dbclass;

import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;

import javax.servlet.*;  

public class Createadmin extends HttpServlet {
    static Boolean check;
    Dbclass db = new Dbclass();


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                // if (check) {
                //     request.setAttribute("message", "Successfully Created !");
                // } else {
                //     request.setAttribute("message", "please use some other UserName :( ");
                // }
                // RequestDispatcher rd = request.getRequestDispatcher("createAdmin.jsp");
		        // rd.forward(request, response);
    }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
                String u = request.getParameter("username");
                String p = request.getParameter("password");
                check = db.createAdmin(u, p);
                PrintWriter out = response.getWriter();
                if (check) {
                    out.println("s");
                    out.flush();
                } else {
                    out.println("f");
                    out.flush();
                }
                
                // doGet(request, response);
    }
	
}
