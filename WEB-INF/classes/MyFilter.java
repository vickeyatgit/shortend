import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.*;  
import java.util.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.security.Principal;

public class MyFilter implements Filter{  
    public void init(FilterConfig filterConfig) throws ServletException {  
        System.out.println("MyFilter: init()");  
    }  
  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {  
        System.out.println("MyFilter: doFilter()");  
        
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;
        
        // Boolean isLibrarian = (Boolean) request1.isUserInRole("librarian");
        // System.out.println("isLibrarian: " + isLibrarian);
        // Boolean isAdmin = (Boolean) request1.isUserInRole("admin");
        // System.out.println("isAdmin: " + isAdmin);

        String url = request1.getRequestURI();
        System.out.println("url: " + url);


        Boolean reDirect = false;
        System.out.println("is admin: " + request1.isUserInRole("admin"));
        System.out.println("is user: " + request1.getRemoteUser());
        if(request1.isUserInRole("admin") && !reDirect){
          String[] adminUrlContainer = {
            "/shortend/AdminChecker","/shortend/GetLibrarianData","/shortend/AdminLibDelete",
            "/shortend/GetFilePdf","/shortend/GetFileHtml","/shortend/GetFileReq",
            "/shortend/GetFilexls","/shortend/Mailme","/shortend/StoreMail",
            "/shortend/AdminLibInsert","/shortend/Logout","/shortend/Adminrequest",
            "/shortend/AdminInsertRole","/shortend/Adminauthority","/shortend/AdminCreateRole",
          };  // array of strings
          List<String> adminUrlList = new ArrayList<>(Arrays.asList(adminUrlContainer));
          reDirect = adminUrlList.contains(url);
          System.out.println("in condition in admin: " + reDirect);
        }
        else if(request1.isUserInRole("librarian") && !reDirect){
          String[] librarianUrlContainer = {
            "/shortend/LibrarianChecker","/shortend/LibBookInsert","/shortend/AddReader",
            "/shortend/LendingData","/shortend/LibrarianLogin","/shortend/LibraryBook",
            "/shortend/FilterBooks","/shortend/RemoveBooks","/shortend/LibrarianLendBook",
            "/shortend/Logout","/shortend/CheckReader"
          };
          List<String> libUrlList = new ArrayList<>(Arrays.asList(librarianUrlContainer));
          reDirect = libUrlList.contains(url);
          System.out.println("in condition in librarian: " + reDirect);
        }
        else{
            // reDirect = true;
            if(url.equals("/shortend/Createadmin")){
              reDirect = true;
            }
        }
        // reDirect = true;
        // System.out.println("j security check");
        // if("shortend/j_security_check".equals(url) || "shortend/MakeLogin".equals(url)
        //     || "shortend/MakeLogout".equals(url) || "shortend/LibrarianChecker".equals(url)
        // ){
        //     // chain.doFilter(request, response);
        //     System.out.println("j security: ");
        //     reDirect = true;
        // }

        // PrintWriter out = response1.getWriter();

        if(reDirect){
            System.out.println("MyFilter: doFilter() - redirect : true");
            chain.doFilter(request, response);
        }
        else{
            System.out.println("false no too");
            System.out.println("MyFilter: doFilter() - redirect : false");
            RequestDispatcher rd=request1.getRequestDispatcher("/MakeLogin");
            rd.include(request, response); 
            System.out.println("finished");
            // response1.sendRedirect("/shortend/MakeLogin");
            // out.print("eror");
        }
        // chain.doFilter(request, response);  
        //sends request to next resource  http://localhost:8080/shortend/MakeError
        // RequestDispatcher rd=req.getRequestDispatcher("index.html");  
        //  
    }  
  
    public void destroy() {  
        System.out.println("MyFilter: destroy()");  
    }  
}