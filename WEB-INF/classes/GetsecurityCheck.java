import java.io.IOException;
import org.json.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

public class GetsecurityCheck extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
              
    HttpSession newSession = request.getSession();
    console.log(newSession.getId());
    //setting session to expiry in 5 mins
    // newSession.setMaxInactiveInterval(5*60);

    // Cookie message = new Cookie("message", "Welcome");
    // response.addCookie(message);
    // response.sendRedirect("admin/LoginSuccess.jsp");

    // HttpPost httppost = new HttpPost("http://localhost:4200/login"); 
    // CookieStore cookieStore = new BasicCookieStore(); 
    // BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", getSessionId());

    // //cookie.setDomain("your domain");
    // cookie.setPath("/");

    // cookieStore.addCookie(cookie); 
    // client.setCookieStore(cookieStore); 
    // response = client.execute(httppost);    
    // String url = "http://someserver:8080/j_security_check?j_username=role1&j_password=role";
    // HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
    // connection.setRequestMethod("POST");
    // connection.setDoOutput(true);
    // connection.setDoInput(true);
    // connection.setUseCaches(false);
    // connection.setAllowUserInteraction(false);
    // connection.setRequestProperty("Content-Type", "application/json");
    // connection.setRequestProperty("Accept", "application/json");
    // connection.setRequestProperty("Accept-Charset", "UTF-8");
    // connection.setRequestProperty("Accept-Encoding", "UTF-8");
    // connection.setRequestProperty("Accept-Language", "UTF-8");
    // connection.setRequestProperty("Connection", "close");
    // connection.setRequestProperty("Content-Length", "0");
    // connection.setRequestProperty("Host", "someserver:8080");
    // connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:12.0) Gecko/20100101 Firefox/12.0");

  }

}