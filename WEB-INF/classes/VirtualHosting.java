
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import dbaction.Dbclass;
import java.io.IOException;

import org.apache.catalina.ContainerServlet;
import org.apache.catalina.Wrapper;

public abstract class VirtualHosting extends HttpServlet
    implements
    ContainerServlet 
    {

      // public void setWrapper(Wrapper wrapper){
      //   System.out.println("wrapper");
      // }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Credentials", "true");
    PrintWriter out = response.getWriter();
    String k = "123";

    try {
      // Wrapper hi = getWrapper();

      // Context context = (Context) wrapper.getParent();
      k = "000";
    } catch (Exception e) {
      e.printStackTrace();
      //TODO: handle exception
    }
    out.println(k);
  }
}

// ContainerServlet