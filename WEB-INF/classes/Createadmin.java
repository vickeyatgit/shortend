
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

public class Createadmin extends HttpServlet {
    static Boolean check;
    Dbclass db = new Dbclass();


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    }
	
}
