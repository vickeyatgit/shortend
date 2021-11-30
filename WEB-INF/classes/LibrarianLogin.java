
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import dbaction.Dbclass;
import dbaction.RolePrincipal;
import dbaction.UserPrincipal;
import java.security.Principal;


import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.*;
import java.util.*;
import dbaction.CallbackhandlerLog;
import org.json.*;
import javax.security.auth.Subject;
import dbaction.TokenCheck;

public class LibrarianLogin extends HttpServlet{
    Dbclass db = new Dbclass();
    TokenCheck ck = new TokenCheck();
    String u;
    String p;
    String role;
    // private Subject subject;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException 
    {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        // JSONObject obj = new JSONObject();
        // String username = request.getRemoteUser();
        // // get user role
        // boolean isMgr = request.isUserInRole("librarian");
        // System.out.println("isMgr chech role: " + isMgr);
        // // to get user name
        // java.security.Principal principal = request.getUserPrincipal();
        // String remoteUser = principal.getName();
        
        // System.out.println("remoteUser: " + remoteUser);
        // Dictionary geek = new Hashtable();
        // geek = db.getLibrarianData(username);
        // try {
        //     obj.put("username",geek.get("username"));
        //     obj.put("firstname",geek.get("firstname"));
        //     obj.put("lastname",geek.get("lastname"));
        //     obj.put("mobile",geek.get("mobilenumner"));
        //     obj.put("email",geek.get("emailid"));
        //     obj.put("tally",geek.get("bookstally"));
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        String username = request.getRemoteUser();
        System.out.println("username: " + username);
        JSONObject item = new JSONObject();
        item = db.getUserData(username);
        out.print(item);
        out.flush();
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        u = request.getParameter("username");
        p = request.getParameter("password");
        Boolean check = false;
        PrintWriter out = response.getWriter();

        CallbackhandlerLog callback = new CallbackhandlerLog(u, p);
        Boolean authCheck = false;
        
        try{
			System.out.println("Entering in try block");
			LoginContext loginContext = new LoginContext("jaas", callback);
            loginContext.login();
            // loginContext.();
            // System.out.println(loginContext.getSubject().getPrincipals());
            // System.out.println(request.getUserPrincipal());
            final Subject subject = loginContext.getSubject();
            // System.out.println(subject.getPrincipals(UserPrincipal.class).iterator().next().getName());
            role = subject.getPrincipals(RolePrincipal.class).iterator().next().getName().toString();
            System.out.println(subject.getPrincipals(RolePrincipal.class).iterator().next().getName());
            // System.out.println(subject.getPrincipals(UserPrincipal.class).getClass().getName());
            // System.out.println(subject.getPrincipals(RolePrincipal.class).iterator().next().getName());

            // System.out.println(loginContext.getClass().getName());
            // subject.getPrincipals().iterator().getClass().getName();
            // System.out.println("==================");
            // System.out.println(subject.getPrincipals().iterator().getClass().getName());
            // System.out.println(subject.getPrincipals().getClass().getName());
            
            // System.out.println(request.isUserInRole("librarian"));
            // System.out.println(loginContext.getSubject());
            // System.out.println(request.getRemoteUser());
            // System.out.println(request.getUserPrincipal().getName());
            // loginContext.getSubject().getPrincipals().getAuth();
            
			authCheck = true;
			System.out.println("suc");
		}
        catch (LoginException e) {
            e.printStackTrace();
            authCheck = false;
        }
        System.out.print("librarian module" + authCheck);
        
        // System.out.println("li "+ request.getRemoteUser());
        // System.out.println("li "+ request.getAuthType());
        // System.out.println("li " + request.getUserPrincipal().getName());
        // System.out.println("li " + (request.getUserPrincipal().toString()));
        // System.out.print("li " + request.isUserInRols("admin"));
        if (role == null) {
            role = "nothing";
        }
        if (authCheck && role.equals("librarian")) {
            
            HttpSession session = request.getSession();
            session.setAttribute("librarian", u);
            out.println("s");
			out.flush();
        }
        else {
            out.println("f");
			out.flush();
        }
        // Dictionary geek = new Hashtable();
        // geek = db.getLibrarianData(u, p);
        // ArrayList<String> data = new ArrayList<String>();
        // for (Enumeration i = geek.elements(); i.hasMoreElements();) 
        // {
        //     // System.out.println("Value in Dictionary : " + i.nextElement());
        // }
        // data.add(geek.get("key"));

        // if (!(geek.isEmpty())) {
        //     HttpSession session = request.getSession();
        //     session.setAttribute("librarian", data);
        //     out.println("s");
        //     out.flush();
        // } else {
        //     out.println("f");
        //     out.flush();
        // }
        // if (!(geek.isEmpty())) {
        //     PrintWriter writer = response.getWriter();
		// 	// response.sendRedirect("librarianView.jsp");
        //     // response.sendRedirect("Adminpanel");
        //     String htmlRespone = "<html>";
        //     htmlRespone += "<title>Librarian panel</title>";
        //     htmlRespone += "<h3> Username : "+geek.get("username")+"<br/>";
        //     htmlRespone += " First Name : "+geek.get("firstname")+"<br/>";
        //     htmlRespone += " Last Name : "+geek.get("lastname")+"<br/>";
        //     htmlRespone += " Mobile Number : "+geek.get("mobilenumner")+"<br/>";
        //     htmlRespone += " Email Id : "+geek.get("emailid")+"<br/>";
        //     htmlRespone += " Books tally : "+geek.get("bookstally")+"</h3>";
        //     htmlRespone += "</html>";
        //     // response.getWriter().println("htmlRespone");
        //     writer.println(htmlRespone);
		// }
		// else {
		// 	response.sendRedirect("login.jsp");
		// }        
    }
}
