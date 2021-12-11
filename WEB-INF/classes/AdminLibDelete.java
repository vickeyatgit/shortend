import dbaction.Dbclass;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;
import org.json.*; 

public class AdminLibDelete extends HttpServlet {
    Dbclass db = new Dbclass();

    //delete user
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        System.out.println("delete in lib");
        String userName = request.getParameter("username");
        JSONObject resSent = new JSONObject();
        String names = request.getRemoteUser();

        Boolean check = false;
        System.out.println("username is " + userName);
        System.out.println("names is " + names);
        System.out.println("check is " + names.equals(userName));
        if(!(userName.equals(names))){
            int businessId = db.getBusinessId(names);
            check = db.deleteAccount(userName,businessId);
            if (check) {
                try{
                    resSent.put("result","pass");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else {
                try{
                    resSent.put("result","fail");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            try{
                resSent.put("result","error");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        out.println(resSent);
        
    }
    
}
