

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
		

		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		String choice = request.getParameter("choice");
		
		if(choice.equals("get")){
			try {
				jo.put("role",role);
				jo.put("user",user);
				jo.put("pass",pass);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			try{loginContext.logout();
			role="null";
			}
			catch (LoginException e) {
			e.printStackTrace();
			}
			
		}
		
		out.println(jo);
		out.flush();
		// System.out.println("noting is anything");
		// System.out.println(role);
		// //auth token check
        // String name,token;
        // name = request.getParameter("name");
        // token = request.getParameter("token");
        // Boolean authNow = ck.tokenAuthentication(name,token);
        // ArrayList<ArrayList<String>> graph = new ArrayList<>();
        // JSONArray ja = new JSONArray();
        //  if(authNow){
        //  	graph = db.librarianGetList();
		// 	for (int count = 0; count < graph.size(); count++) {
		// 		JSONObject jo = new JSONObject();
		// 		try {
		// 			jo.put("Username", graph.get(count).get(0));
		// 			jo.put("FirstName", graph.get(count).get(1));
		// 			jo.put("LastName", graph.get(count).get(2));
		// 			jo.put("Email", graph.get(count).get(3));
		// 			jo.put("Mobile", graph.get(count).get(4));
		// 			jo.put("Books", graph.get(count).get(5));
		// 			ja.put(jo);	
		// 		} catch (Exception e) {
		// 			e.printStackTrace();
		// 		}
				
		// 	}
		
		// 	HttpSession session = request.getSession();
		// 	session.setAttribute("ckecklist", graph);
		// 	session.setAttribute("newchecklist", ja);
		// 	out.println(ja);
        //  }else{
        //  	out.println(ja);
        //  }
		
		// out.flush();
		// getServletContext().setAttribute("hell123", "conform");
		// getServletContext().setAttribute("ckecklist", graph);
		// getServletContext().getRequestDispatcher("./adminPanel.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
        String username, password,fingerprint;
        username = request.getParameter("username");        
		password = request.getParameter("password");
		fingerprint = request.getParameter("fingerprint");
		user = username;
		pass = password;
		System.out.println("user"+username+"pass"+password+"fp"+fingerprint);
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
		if (role == null) {
            role = "nothing";
        }
        System.out.println("result "+authCheck);
		
		String encodedString = "";
		Boolean sessionCheck = false;
		if(authCheck){
			Random random = new Random();
			String setOfCharacters = "a1b2c3d4e5f6g7h8i9j0k0l!m@n#o$p%q^r&s*t(u=v-w+x/y*z";
			String key="";
			for(int j=0;j<2;j++){
				for(int i=0;i<(random.nextInt(20-10) + 10);i++){
					int randomInt = random.nextInt(setOfCharacters.length());
					key += setOfCharacters.charAt(randomInt);
				}
				if(j==2) break;
				key += username;
			}
			key +=fingerprint;
			System.out.println("key");
			System.out.println(key);
			Base64.Encoder simpleEncoder = Base64.getEncoder();
			encodedString = simpleEncoder.encodeToString(key.getBytes());
			sessionCheck = db.sessionReg(fingerprint,key,username,role);
			// sessionCheck = true;
		}
        if (authCheck && role.equals("admin") && sessionCheck) {
			// HttpSession session = request.getSession();
			// session.setAttribute("username", username);
			// ArrayList<ArrayList<String>> graph = new ArrayList<>();
			// graph = db.librarianGetList();
			// session.setAttribute("ckecklist", graph);
			// JSONArray ja = new JSONArray();
			// for (int count = 0; count < graph.size(); count++) {
			// 	JSONObject jo = new JSONObject();
			// 	try {
			// 		jo.put("Username", graph.get(count).get(0));
			// 		jo.put("FirstName", graph.get(count).get(1));
			// 		jo.put("LastName", graph.get(count).get(2));
			// 		jo.put("Email", graph.get(count).get(3));
			// 		jo.put("Mobile", graph.get(count).get(4));
			// 		jo.put("Books", graph.get(count).get(5));
			// 		ja.put(jo);
			// 	} catch (Exception e) {
			// 		e.printStackTrace();
			// 	}
			// }
			// session.setAttribute("newchecklist", ja);

			JSONObject nowSent = new JSONObject();
			try {
					nowSent.put("token",encodedString);
					nowSent.put("auth",true);
					// nowSent.put("userProfile",username);
					nowSent.put("role","admin");
				} catch (Exception e) {
					e.printStackTrace();
				}
			out.println(nowSent);
			out.flush();
		}
		else if (authCheck && role.equals("librarian") && sessionCheck){
			JSONObject nowSent = new JSONObject();
			try {
					nowSent.put("token",encodedString);
					nowSent.put("auth",true);	
					// nowSent.put("userProfile",username); 
					nowSent.put("role","librarian"); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			out.println(nowSent);
			out.flush();
		}
		else {
			JSONObject nowSent = new JSONObject();
			try {
					nowSent.put("auth",false);
					nowSent.put("userProfile","null");
					nowSent.put("role","null");
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			out.println(nowSent);
			// out.println("f");
			out.flush();
			// response.sendRedirect("login.jsp");
		}        
	}

}
