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
import dbaction.TokenCheck;
import org.json.JSONObject;
import java.util.*;

public class UserChecker extends HttpServlet {
    Dbclass db = new Dbclass();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");
        System.out.println("cookies");
        System.out.println(rawCookie);
        String token = "";
        String[] rawCookieParams = rawCookie.split(";");
        for(String rawCookieNameAndValue :rawCookieParams)
        {
            String[] rawCookieNameAndValuePair = rawCookieNameAndValue.split("=");
            String tempstring = new String(rawCookieNameAndValuePair[0]);
            tempstring.trim();
            if(tempstring.contains("token")){
                String temp2 = new String(rawCookieNameAndValuePair[1]);
                token = temp2;
            }
        }

        if(token.length() > 0){
            byte[] actualByte = Base64.getDecoder().decode(token);
            String actualToken = new String(actualByte);
            System.out.println(actualToken);
            JSONObject nowSent = new JSONObject();
            try {
                Boolean authCheck = db.sessionLogout(actualToken, pcid);
                System.out.println("logout  " + authCheck);
                nowSent.put("auth",authCheck);
            } catch (Exception e) {
                e.printStackTrace();
            }
            out.println(nowSent);
            out.flush();
        }
        else{
            JSONObject nowSent = new JSONObject();
            try{
                    nowSent.put("auth",false);
                }
            catch (Exception e) {
                    e.printStackTrace();
                }
            out.println(nowSent);
            out.flush();
        }
    }
		
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setHeader("Access-Control-Allow-Credentials", "true");
                PrintWriter out = response.getWriter();
                String pcid = request.getParameter("pcid");
                String rawCookie = request.getHeader("Cookie");
                System.out.println("cookies");
                System.out.println(rawCookie);
                System.out.println("inn check");
                String token = "";
                String[] rawCookieParams = rawCookie.split(";");
                for(String rawCookieNameAndValue :rawCookieParams)
                {
                    String[] rawCookieNameAndValuePair = rawCookieNameAndValue.split("=");
                    String tempstring = new String(rawCookieNameAndValuePair[0]);
                    tempstring.trim();
                    if(tempstring.contains("token")){
                        String temp2 = new String(rawCookieNameAndValuePair[1]);
                        token = temp2;
                    }
                }
                if(token.length() > 0){
                    System.out.println("accp");
                    byte[] actualByte = Base64.getDecoder().decode(token);
                    String actualToken = new String(actualByte);
                    System.out.println(actualToken);
                    String authCheck = "error";
                    try {
                        authCheck = db.sessionChecker(actualToken, pcid);
                        System.out.println("added reader  " + authCheck);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    JSONObject nowSent = new JSONObject();
                    try{
                        nowSent.put("role",authCheck);
                        if(authCheck.equals("error")){
                            nowSent.put("auth",false);
                        }else{
                            nowSent.put("auth",true);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    out.println(nowSent);
                    out.flush();
                }else{
                    JSONObject nowSent = new JSONObject();
                    try{
                            nowSent.put("auth",false);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    out.println(nowSent);
                    out.flush();
                }
            }
}