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

public class SendMail extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");
        Dbclass db = new Dbclass();
        Boolean sol = db.mail(from,to,subject,body);
        JSONObject jo = new JSONObject();
        try {
			jo.put("result",sol);
		}catch (Exception e) {
			e.printStackTrace();
		}
        out.println(jo);
		out.flush();
    }
}