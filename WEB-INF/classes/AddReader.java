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
import dbaction.TokenCheck;
import org.json.*; 

public class AddReader extends HttpServlet {
    Dbclass db = new Dbclass();
    TokenCheck ck = new TokenCheck();
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");
        JSONObject resSent = new JSONObject();
        try {
                    if(rawCookie.length() > 0){
                        String authRole = db.roleChecker(pcid,rawCookie);
                        if(authRole.equals("librarian")){
                            Boolean authCheck = false;
                            try {
                                authCheck = db.addReader(username, name);
                                System.out.println("added reader  " + authCheck);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (authCheck) {
                                try{
                                    resSent.put("result","Reader is Added");
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                            else {
                                try{
                                    resSent.put("result","username Already exist");
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                        else{
                            try{
                                resSent.put("result","Invalid user role");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    else{
                        try{
                            resSent.put("result","sorry, I cannot find your details from login");
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

        out.println(resSent);
        out.flush();
    }
}
