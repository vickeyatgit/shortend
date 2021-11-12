
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import dbaction.Dbclass;

public class AutoMail extends HttpServlet {
  Dbclass db = new Dbclass();
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
      //for interval
      response.setHeader("Access-Control-Allow-Credentials", "true");
      System.out.println("AutoMail");
      PrintWriter out = response.getWriter();
      //1. get conformation 
      String pcid = request.getParameter("pcid");
      String rawCookie = request.getHeader("Cookie");
      Boolean roleChecker = false;
      if(rawCookie.length() > 0){
        String authRole = db.roleChecker(pcid,rawCookie);
        roleChecker = authRole.equals("admin");
      }
      System.out.println("roleChecker: "+roleChecker);
      String from="";
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
        from = db.getNameFromSession(actualToken,pcid);
        String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");
        String attachment = request.getParameter("attache");
        Boolean attachCheck = Boolean.parseBoolean(request.getParameter("attachCheck"));
        String type = request.getParameter("type");
        int interval = Integer.parseInt(request.getParameter("interval"));
        String onDate = request.getParameter("onDate");
        System.out.println(from);
        System.out.println(to);
        System.out.println(subject);
        System.out.println(body);
        System.out.println(attachment);
        System.out.println(attachCheck);
        System.out.println(type);
        System.out.println(interval);
        System.out.println(onDate);
        // System.out.println(from,to,subject,body,attachment,attachCheck,type,interval,onDate);
        String fileType = "";
        // if(attachCheck){
        //   fileType = attachment;
        // }
        // Boolean isStore = db.storeMail(pcid,from,to,subject,body,fileType,type,interval);

      }
      else{
        out.println("<script type=\"text/javascript\">");
        out.println("alert('You are not authorized to access this page');");
        out.println("location='/';");
        out.println("</script>");
      }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        System.out.println("AutoMail");
        PrintWriter out = response.getWriter();
        //1. get conformation 
        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");
        Boolean roleChecker = false;
        if(rawCookie.length() > 0){
          String authRole = db.roleChecker(pcid,rawCookie);
          roleChecker = authRole.equals("admin");
        }
        System.out.println("roleChecker: "+roleChecker);
        //get email id
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
          String from = db.getNameFromSession(actualToken,pcid);
          System.out.println("from "+from);
          String to = request.getParameter("to");
          System.out.println("to "+to);
          String subject = request.getParameter("subject");
          System.out.println("subject"+subject);
          String body = request.getParameter("body");
          System.out.println("body"+body);
          String attachment = request.getParameter("attache");
          System.out.println("attachment"+attachment);
          Boolean attachCheck = Boolean.parseBoolean(request.getParameter("attachCheck"));
          System.out.println("attachCheck"+attachCheck);
          String type = request.getParameter("type");
          System.out.println("type"+type);
          String interval = request.getParameter("interval");
          System.out.println("interval"+interval);
          String onDate = request.getParameter("onDate");
          System.out.println("onDate"+onDate);
          
        }
        
      }

}