

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
import dbaction.TokenCheck;
import java.util.*;

public class Adminlogin extends HttpServlet {
	
	Dbclass db = new Dbclass();
	TokenCheck ck = new TokenCheck();
	String role,user,pass;
	LoginContext loginContext = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		// /////////////////
		// if (role == null) {
    //         role = "nothing";
    //     }
    //     System.out.println("result "+authCheck);
		
		// String encodedString = "";
		// Boolean sessionCheck = false;
		// if(authCheck){
		// 	Random random = new Random();
		// 	String setOfCharacters = "a1b2c3d4e5f6g7h8i9j0k0l!m@n#o$p%q^r&s*t(u=v-w+x/y*z";
		// 	String key="";
		// 	for(int j=0;j<2;j++){
		// 		for(int i=0;i<(random.nextInt(20-10) + 10);i++){
		// 			int randomInt = random.nextInt(setOfCharacters.length());
		// 			key += setOfCharacters.charAt(randomInt);
		// 		}
		// 		if(j==2) break;
		// 		key += username;
		// 	}
		// 	key +=fingerprint;
		// 	System.out.println("key");
		// 	System.out.println(key);
		// 	Base64.Encoder simpleEncoder = Base64.getEncoder();
		// 	encodedString = simpleEncoder.encodeToString(key.getBytes());
		// 	sessionCheck = db.sessionReg(fingerprint,key,username,role);
		// }
    // if (authCheck && role.equals("admin") && sessionCheck) {
		// 	JSONObject nowSent = new JSONObject();
		// 	try {
		// 			nowSent.put("token",encodedString);
		// 			nowSent.put("auth",true);
		// 			nowSent.put("role","admin");
		// 		} catch (Exception e) {
		// 			e.printStackTrace();
		// 		}
		// 	out.println(nowSent);
		// 	out.flush();
		// }
		// else if (authCheck && role.equals("librarian") && sessionCheck){
		// 	JSONObject nowSent = new JSONObject();
		// 	try {
		// 			nowSent.put("token",encodedString);
		// 			nowSent.put("auth",true);	
		// 			nowSent.put("role","librarian"); 
		// 		} catch (Exception e) {
		// 			e.printStackTrace();
		// 		}
			
		// 	out.println(nowSent);
		// 	out.flush();
		// }
		// else {
		// 	JSONObject nowSent = new JSONObject();
		// 	try {
		// 			nowSent.put("auth",false);
		// 			nowSent.put("userProfile","null");
		// 			nowSent.put("role","null");
		// 		} catch (Exception e) {
		// 			e.printStackTrace();
		// 		}
		// 	out.println(nowSent);
		// 	out.flush();
		// } 
		///////////////      //  
		out.println(authCheck);
		out.flush();
	}

}
