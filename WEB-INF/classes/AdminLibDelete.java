
import dbaction.Dbclass;
import dbaction.TokenCheck;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;
import org.json.*; 

public class AdminLibDelete extends HttpServlet {
    Dbclass db = new Dbclass();
    TokenCheck ck = new TokenCheck();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        System.out.println("delete in lib");
        String userName = request.getParameter("username");
        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");

        JSONObject resSent = new JSONObject();
        
        if(rawCookie.length() > 0){
            String authRole = db.roleChecker(pcid,rawCookie);
            if(authRole.equals("admin")){
                Boolean check = db.deleteLib(userName);
                if (check) {
                    try{
                        resSent.put("result","pass");
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    try{
                        resSent.put("result","fail");
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    
                }
            }
            else{
                try{
                    resSent.put("result","error");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        else{
            try{
                resSent.put("result","error");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        out.println(resSent);
        
    }
    
}
