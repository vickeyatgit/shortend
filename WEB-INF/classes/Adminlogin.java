

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
import javax.servlet.http.*;
import org.json.*;
import org.json.JSONObject;
import org.json.JSONArray;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import dbaction.CallbackhandlerLog;
import javax.security.auth.Subject;
import dbaction.RolePrincipal;
import dbaction.UserPrincipal;
import java.util.Base64;
import java.util.Random;
import java.util.*;

public class Adminlogin extends HttpServlet {
	
	Dbclass db = new Dbclass();
	String role,user,pass;
	LoginContext loginContext = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ji");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
    String username, password,fingerprint;
    username = request.getParameter("username");        
		password = request.getParameter("password");
		fingerprint = request.getParameter("fingerprint");
		user = username;
		pass = password;
		CallbackhandlerLog  callback = new CallbackhandlerLog(username,password);
		Boolean authCheck = false;
		try{
			System.out.println("Entering in try block");
			loginContext = new LoginContext("jaas", callback);
			loginContext.login();
			final Subject subject = loginContext.getSubject();
            role = subject.getPrincipals(RolePrincipal.class).iterator().next().getName().toString();
            System.out.println(subject.getPrincipals(RolePrincipal.class).iterator().next().getName());
			authCheck = true;
		}
		catch (LoginException e) {
			e.printStackTrace();
			authCheck = false;
		}  
		out.println(authCheck);
		out.flush();
	}

}
