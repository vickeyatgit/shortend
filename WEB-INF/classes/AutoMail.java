
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import dbaction.Dbclass;

public class AutoMail extends HttpServlet {
  Dbclass db = new Dbclass();
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
      response.setHeader("Access-Control-Allow-Credentials", "true");
      System.out.println("AutoMail");
      PrintWriter out = response.getWriter();
      //1. get conformation 
      String pcid = request.getParameter("pcid");
      String rawCookie = request.getHeader("Cookie");
      if(rawCookie.length() > 0){
        String authRole = db.roleChecker(pcid,rawCookie);
        roleChecker = authRole.equals("admin");
      }
      String email="";
        String userPassword="";
        if(roleChecker){
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
            byte[] actualByte = Base64.getDecoder().decode(token);
            String actualToken = new String(actualByte);
            email = db.getNameFromSession(actualToken,pcid);
            userPassword = db.GetUserPassword(email);
        }
        System.out.println("email: "+email);
        System.out.println("password: "+userPassword);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

      }

}