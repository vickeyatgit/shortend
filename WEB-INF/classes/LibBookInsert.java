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
import dbaction.TokenCheck;
import org.json.*; 

public class LibBookInsert extends HttpServlet {

    Dbclass db = new Dbclass();
    TokenCheck ck = new TokenCheck();
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

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

        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");

        JSONObject resSent = new JSONObject();

        if(rawCookie.length() > 0){
            String authRole = db.roleChecker(pcid,rawCookie);
            if(authRole.equals("librarian")){
                Boolean get = db.addBook(title, subtitle, author, category, language, publish, edition, tot, genre);
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
                try{
                    resSent.put("result","sorry, I cannot find your details from login");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        out.println(resSent);
    }

}
