import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.*;  
import java.util.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.security.Principal;
import org.json.*; 
import dbaction.Dbclass;
import java.util.ArrayList;

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

        // String url = request1.getRequestURI();
        // System.out.println("url: " + url);


        Boolean reDirect = false;
        // System.out.println("is admin: " + request1.isUserInRole("admin"));
        // System.out.println("is user: " + request1.getRemoteUser());
        // if(request1.isUserInRole("admin") && !reDirect){
        //   String[] adminUrlContainer = {
        //     "/shortend/AdminChecker","/shortend/GetLibrarianData","/shortend/AdminLibDelete",
        //     "/shortend/GetFilePdf","/shortend/GetFileHtml","/shortend/GetFileReq",
        //     "/shortend/GetFilexls","/shortend/Mailme","/shortend/StoreMail",
        //     "/shortend/AdminLibInsert","/shortend/Logout","/shortend/Adminrequest",
        //     "/shortend/AdminInsertRole","/shortend/Adminauthority","/shortend/AdminCreateRole",
        //     "/shortend/GetRoleNav","/shortend/LibrarianLogin"
        //   };  // array of strings
        //   List<String> adminUrlList = new ArrayList<>(Arrays.asList(adminUrlContainer));
        //   reDirect = adminUrlList.contains(url);
        //   System.out.println("in condition in admin: " + reDirect);
        // }
        // else if(request1.isUserInRole("librarian") && !reDirect){
        //   String[] librarianUrlContainer = {
        //     "/shortend/LibrarianChecker","/shortend/LibBookInsert","/shortend/AddReader",
        //     "/shortend/LendingData","/shortend/LibrarianLogin","/shortend/LibraryBook",
        //     "/shortend/FilterBooks","/shortend/RemoveBooks","/shortend/LibrarianLendBook",
        //     "/shortend/Logout","/shortend/CheckReader","/shortend/GetRoleNav"
        //   };
        //   List<String> libUrlList = new ArrayList<>(Arrays.asList(librarianUrlContainer));
        //   reDirect = libUrlList.contains(url);
        //   System.out.println("in condition in librarian: " + reDirect);
        // }
        // else{
        //     // reDirect = true;
        //     if(url.equals("/shortend/Createadmin")||url.equals("/shortend/GetRoleNav")){
        //       reDirect = true;
        //     }
        // }

        String url = request1.getRequestURI();
        System.out.println("url: " + url);
        System.out.println("is user: " + request1.getRemoteUser());
        Dbclass db = new Dbclass();
        JSONArray ja = new JSONArray();
        ArrayList<String> listOfRes = new ArrayList<String>();
        ja = db.roleAndResourse();
        try {
          for (int i=0; i<ja.length(); i++) {
            JSONObject item = ja.getJSONObject(i);
            String name = item.getString("role");
            Boolean checkRole = request1.isUserInRole(name);
            if(checkRole){
              System.out.println("role: " + name);
              JSONArray temp = item.getJSONArray("resources");
              for (int j = 0; j < temp.length(); j++) {
                String pet = temp.getString(j);
                listOfRes.add(pet);
                // System.out.println(i+j + pet);
              }
            }                
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        for(String d: listOfRes){
          System.out.println("listOfRes: " + d);
        }
        if(listOfRes.contains("addbook") && !reDirect){
          String[] tempUrl = {"/shortend/LibBookInsert"};
          List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
          reDirect = tempUrlList.contains(url);
        }
        if(listOfRes.contains("addreader") && !reDirect){
          String[] tempUrl = {"/shortend/AddReader","/shortend/CheckReader"};
          List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
          reDirect = tempUrlList.contains(url);
        }

        if(listOfRes.contains("readersbook") && !reDirect){
          String[] tempUrl = {"/shortend/LendingData"};
          List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
          reDirect = tempUrlList.contains(url);
        }

        if(listOfRes.contains("booklist") && !reDirect){
          String[] tempUrl = {
            "/shortend/LibraryBook",//list of books
            "/shortend/FilterBooks",
            "/shortend/RemoveBooks",
            "/shortend/LibrarianLendBook",
            "/shortend/CheckReader",
          };
          List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
          reDirect = tempUrlList.contains(url);
        }

        if(listOfRes.contains("createrole")&& !reDirect){
          String[] tempUrl = {
            "/shortend/Adminauthority",
            "/shortend/AdminCreateRole",
          };
          List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
          reDirect = tempUrlList.contains(url);
        }

        if(listOfRes.contains("request")&& !reDirect){
          String[] tempUrl = {
            "/shortend/Adminrequest",
            "/shortend/Adminauthority"
          };
          List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
          reDirect = tempUrlList.contains(url);
        }

        if(listOfRes.contains("addlibrarian") && !reDirect){
          String[] tempUrl = {
            "/shortend/AdminLibInsert",
          };
          List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
          reDirect = tempUrlList.contains(url);
        }

        if(listOfRes.contains("deletelibrarian") && !reDirect){
          String[] tempUrl = {
            "/shortend/AdminLibDelete",
          };
          List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
          reDirect = tempUrlList.contains(url);
        }

        if(listOfRes.contains("librarianlist") && !reDirect){
          String[] tempUrl = {
            "/shortend/AdminLibrarian",
            "/shortend/GetFilePdf",
            "/shortend/GetFileHtml",
            "/shortend/GetFileReq",
            "/shortend/GetFilexls",
            "/shortend/StoreMail",
            "/shortend/AutoMail",
            "/shortend/GetLibrarianData",
            "/shortend/AdminLibInsert",
          };
          List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
          reDirect = tempUrlList.contains(url);
        }

        System.out.println("reDirect: " + reDirect);
        if(listOfRes.contains("viewprofile") && !reDirect){
          String[] tempUrl = {
            "/shortend/LibrarianLogin",
          };
          List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
          reDirect = tempUrlList.contains(url);
        }

        if(!reDirect){
          if(url.equals("/shortend/Createadmin")||url.equals("/shortend/GetRoleNav")||url.equals("/shortend/Logout")){
            reDirect = true;
          }
        }



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
        }
    }  
  
    public void destroy() {  
        System.out.println("MyFilter: destroy()");  
    }  
}