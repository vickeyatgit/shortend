
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

public class FilterBooks extends HttpServlet {

    Dbclass db = new Dbclass();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String title = request.getParameter("title");
        String lang = request.getParameter("lang");
        String cat = request.getParameter("cat");
        String auth = request.getParameter("auth");
        String avai = request.getParameter("avai");
        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");

        JSONArray ja = new JSONArray();

        if(rawCookie.length() > 0){
            String authRole = db.roleChecker(pcid,rawCookie);
            if(authRole.equals("librarian")){
                ja = db.filterBooks(title, lang, auth, cat, avai);
            }
        }

        
        System.out.println("==================");
        System.out.println(title);
        System.out.println(lang);
        System.out.println(cat);
        System.out.println(auth);
        System.out.println(avai);
        System.out.println("==================");
        PrintWriter out = response.getWriter();
        out.println(ja);
    }
}