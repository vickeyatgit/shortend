
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import dbaction.Dbclass;
import java.util.ArrayList;
import org.json.*; 

public class Adminrequest extends HttpServlet {

  Dbclass db = new Dbclass();

  //get list of user without role assigned
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
  throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    PrintWriter out = response.getWriter();
    JSONArray ja = new JSONArray();
    int businessId = db.getBusinessId(request.getRemoteUser());
    ja=db.newlistOfUnRole(businessId);
    out.println(ja);
    out.flush();
    out.close();

	}

}