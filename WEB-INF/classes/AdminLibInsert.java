
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



public class AdminLibInsert extends HttpServlet {
    Dbclass db = new Dbclass();

    // get list of user are filtered by the search criteria
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String qu = request.getParameter("val");
        System.out.println(qu);
        PrintWriter out = response.getWriter();
        JSONArray ja = new JSONArray();
        int businessId = db.getBusinessId(request.getRemoteUser());
        ja = db.newlistOfUserFilter(qu,businessId,request.getRemoteUser());
        out.println(ja);
        out.close();
    }

    // add librarian but not in use
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        System.out.println("insert in lib");
        String userName, firstName, lastName, email, mobile, password;
        userName=request.getParameter("username");
        firstName=request.getParameter("firstname");
        lastName=request.getParameter("lastname");
        email=request.getParameter("email");
        mobile=request.getParameter("mobile");
        password = request.getParameter("password");
        JSONObject resSent = new JSONObject(); 

        Boolean check = db.insertLib(userName, password, firstName, lastName, email, mobile);
        System.out.println(check);
        if (check) {
            try{
                resSent.put("result","pass");
            }
            catch(Exception e){
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
        out.println(resSent);
        out.flush();
    }
    
}
