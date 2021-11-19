
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import dbaction.Dbclass;
import org.json.JSONObject;

public class AutoMail extends HttpServlet {
  Dbclass db = new Dbclass();
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
      //for interval
      response.setHeader("Access-Control-Allow-Credentials", "true");
      System.out.println("AutoMail");
      PrintWriter out = response.getWriter();
      JSONObject jo = new JSONObject();
      String from = request.getRemoteUser();
      String to = request.getParameter("to");
      String subject = request.getParameter("subject");
      String body = request.getParameter("body");
      Boolean attachCheck = Boolean.parseBoolean(request.getParameter("attachCheck"));
      String attache = request.getParameter("attache");
      String sendType = request.getParameter("sendType");
      int type1Min = Integer.parseInt(request.getParameter("type1Min"));
      String type2Date = request.getParameter("type2Date");

      
      if(!attachCheck){
        attache = "";
      }
      
      if(sendType.equals("one")){
        type1Min=0;
      }else{
        type2Date = "";
      }
      System.out.println("from: "+from);
      System.out.println("to: "+to);
      System.out.println("subject: "+subject);
      System.out.println("body: "+body);
      System.out.println("attachCheck: "+attachCheck);
      System.out.println("attache: "+attache);
      System.out.println("sendType: "+sendType);
      System.out.println("type1Min: "+type1Min);
      System.out.println("type2Date: "+type2Date);
      // Boolean result = db.storeMail(from, to, subject, body, attachCheck, sendType, type1Min, type2Date);




// Boolean isStore = db.storeMail(pcid,from,to,subject,body,fileType,type,interval);
      try {
        jo.put("result","feature is not enabled yet");
      }catch (Exception e) {
        e.printStackTrace();
      }

      out.println(jo);
      out.flush();

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