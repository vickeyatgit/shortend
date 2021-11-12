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
import dbaction.Dbclass;
import java.util.Enumeration;

public class myalltempchecks extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            PrintWriter out = response.getWriter();

                HttpServletRequest newRequest = (HttpServletRequest) request;
                System.out.println("\n\nParameters");

            Enumeration params = newRequest.getParameterNames();
            while(params.hasMoreElements()){
                String paramName = (String)params.nextElement();
                System.out.println(paramName + " = " + newRequest.getParameter(paramName));
            }

            System.out.println("\n\n Row data");

             String username, password;
        username = request.getParameter("username");
        System.out.println("username => "+ username);
        Boolean check = true;

        JSONObject nowSent = new JSONObject();
            try {

                    nowSent.put("auth",username);
                    nowSent.put("id",1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            
            out.println(nowSent);
         }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        Dbclass db = new Dbclass();
        PrintWriter out = response.getWriter();
        HttpServletRequest newRequest = (HttpServletRequest) request;
        System.out.println("payload: " + newRequest);


        // Enumeration headerNames = newRequest.getHeaderNames();
        // while(headerNames.hasMoreElements()) {
        //     String headerName = (String)headerNames.nextElement();
        //     System.out.println(headerName + " = " + newRequest.getHeader(headerName));
        // }

        System.out.println("\n\nParameters");

        Enumeration params = newRequest.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String)params.nextElement();
            System.out.println(paramName + " = " + newRequest.getParameter(paramName));
        }

        System.out.println("\n\n Row data");
        
        String username, password;
        username = request.getParameter("username");
        System.out.println("username => "+ username);
        Boolean check = true;
        JSONObject nowSent = new JSONObject();
            try {

                    nowSent.put("auth",username);
                    nowSent.put("id",1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            
            out.println(nowSent);
    }
    
}
