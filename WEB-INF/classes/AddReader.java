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
import org.json.*; 

public class AddReader extends HttpServlet {
    Dbclass db = new Dbclass();

    //insert reader username and fullname
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        JSONObject resSent = new JSONObject();
        Boolean authCheck = false;
        String names = request.getRemoteUser();
        int businessId = db.getBusinessId(names);
        authCheck = db.addReader(username, name, businessId);
        if (authCheck) {
            try{
                resSent.put("result","Reader is Added");
            }catch(Exception e){
                e.printStackTrace();
            }
        }else {
            try{
                resSent.put("result","username Already exist");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        out.println(resSent);
        out.flush();
    }
}
