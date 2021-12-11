import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import dbaction.Dbclass;
import java.io.PrintWriter;
import org.json.*; 

public class LibBookInsert extends HttpServlet {

    Dbclass db = new Dbclass();

    // add books
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = response.getWriter();
        String title = request.getParameter("title");
        String subtitle = request.getParameter("subtitle");
        String tmp = request.getParameter("author");
        String[] author = tmp.split("[,]", 0);
        String category = request.getParameter("categ");
        String language = request.getParameter("lang");
        String publish = request.getParameter("publish");
        String edition = request.getParameter("edition");
        String tot = request.getParameter("tot");
        tmp = request.getParameter("genre");
        String[] genre = tmp.split("[,]", 0);
        System.out.println("inn servlet");
        JSONObject resSent = new JSONObject();
        String names = request.getRemoteUser();
        int businessId = db.getBusinessId(names);
        Boolean get = db.addBook(title, subtitle, author, category, language, publish, edition, tot, genre,businessId);
        System.out.print(get);
        if(get){
            try{
                resSent.put("result","Successfully Book is Added");
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            try{
                resSent.put("result","Please, logout and try again");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        out.println(resSent);
    }

}
