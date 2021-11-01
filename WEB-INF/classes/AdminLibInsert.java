
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



public class AdminLibInsert extends HttpServlet {
    Dbclass db = new Dbclass();
    TokenCheck ck = new TokenCheck();

    public AdminLibInsert() {
        super();
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String qu = request.getParameter("val");
        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");
        System.out.println(qu);
        PrintWriter out = response.getWriter();
        JSONArray ja = new JSONArray();
        try{
            if(rawCookie.length() > 0){
                String authRole = db.roleChecker(pcid,rawCookie);
                if(authRole.equals("admin")){
                    ArrayList<ArrayList<String>> graph = new ArrayList<>();
                    graph = db.filterLibrarian(qu);
                    for (int count = 0; count < graph.size(); count++) {
                        JSONObject jo = new JSONObject();
                        try {
                            jo.put("Username", graph.get(count).get(0));
                            jo.put("FirstName", graph.get(count).get(1));
                            jo.put("LastName", graph.get(count).get(2));
                            jo.put("Email", graph.get(count).get(3));
                            jo.put("Mobile", graph.get(count).get(4));
                            jo.put("Books", graph.get(count).get(5));
                            ja.put(jo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        out.println(ja);
        

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        System.out.println("insert in lib");
        String userName, firstName, lastName, email, mobile, password, pcid;
        userName=request.getParameter("username");
        firstName=request.getParameter("firstname");
        lastName=request.getParameter("lastname");
        email=request.getParameter("email");
        mobile=request.getParameter("mobile");
        password = request.getParameter("password");
        pcid = request.getParameter("pcid");

        JSONObject resSent = new JSONObject(); 

        String rawCookie = request.getHeader("Cookie");
        System.out.println("cookies"+rawCookie);
        if(rawCookie.length() > 0){
            String authRole = db.roleChecker(pcid,rawCookie);
            if(authRole.equals("admin")){
                Boolean check = db.insertLib(userName, password, firstName, lastName, email, mobile);
                System.out.println(check);
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
            }else if(authRole.equals("nocookie")){
                try{
                        resSent.put("result","nocookie");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }else{
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
                resSent.put("result","nocookie");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        out.println(resSent);
        out.flush();
    }
    
}