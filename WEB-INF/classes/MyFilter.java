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

public class MyFilter implements Filter {

  public void init(FilterConfig filterConfig) throws ServletException {
    System.out.println("MyFilter: init()");
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
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
    String url = request1.getRequestURI();
    System.out.println("url: " + url);
    System.out.println("is user: " + request1.getRemoteUser());

    Dbclass db = new Dbclass();

    // get all roles
    ArrayList<String> roles = new ArrayList<String>();
    ArrayList<String> listOfRes = new ArrayList<String>();

    // owner ship
    if ((Boolean) request1.isUserInRole("owner")) {
      listOfRes = db.getResource("owner", 0);
    } else {
      int businessId = db.getBusinessId(request1.getRemoteUser());
      roles = db.getAllRole(businessId);
      for (int i = 0; i < roles.size(); i++) {
        System.out.println("role: " + roles.get(i));
        Boolean isRole = (Boolean) request1.isUserInRole(roles.get(i));
        if (isRole) {
          System.out.println("isRole: " + roles.get(i));
          listOfRes = db.getResource(roles.get(i), businessId);
          break;
        }
      }
    }

    for (String d : listOfRes) {
      System.out.println("listOfRes: " + d);
    }
    if (listOfRes.contains("addbook") && !reDirect) {
      String[] tempUrl = { "/shortend/LibBookInsert" };
      List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
      reDirect = tempUrlList.contains(url);
    }
    if (listOfRes.contains("addreader") && !reDirect) {
      String[] tempUrl = { "/shortend/AddReader", "/shortend/CheckReader" };
      List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
      reDirect = tempUrlList.contains(url);
    }

    if (listOfRes.contains("readersbook") && !reDirect) {
      String[] tempUrl = { "/shortend/LendingData" };
      List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
      reDirect = tempUrlList.contains(url);
    }

    if (listOfRes.contains("booklist") && !reDirect) {
      String[] tempUrl = {
          "/shortend/LibraryBook", // list of books
          "/shortend/FilterBooks",
          "/shortend/RemoveBooks",
          "/shortend/LibrarianLendBook",
          "/shortend/CheckReader",
      };
      List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
      reDirect = tempUrlList.contains(url);
    }

    if (listOfRes.contains("createrole") && !reDirect) {
      String[] tempUrl = {
          "/shortend/Adminauthority",
          "/shortend/AdminCreateRole",
      };
      List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
      reDirect = tempUrlList.contains(url);
    }

    if (listOfRes.contains("request") && !reDirect) {
      String[] tempUrl = {
          "/shortend/Adminrequest",
          "/shortend/Adminauthority"
      };
      List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
      reDirect = tempUrlList.contains(url);
    }

    if (listOfRes.contains("addlibrarian") && !reDirect) {
      String[] tempUrl = {
          "/shortend/AdminLibInsert",
      };
      List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
      reDirect = tempUrlList.contains(url);
    }

    if (listOfRes.contains("deletelibrarian") && !reDirect) {
      String[] tempUrl = {
          "/shortend/AdminLibDelete",
      };
      List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
      reDirect = tempUrlList.contains(url);
    }

    if (listOfRes.contains("librarianlist") && !reDirect) {
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
    if (listOfRes.contains("viewprofile") && !reDirect) {
      String[] tempUrl = {
          "/shortend/GetUserProfile",
      };
      List<String> tempUrlList = new ArrayList<>(Arrays.asList(tempUrl));
      reDirect = tempUrlList.contains(url);
    }

    if (!reDirect) {
      if (url.equals("/shortend/Createadmin") ||
          url.equals("/shortend/GetRoleNav") ||
          url.equals("/shortend/Logout") ||
          url.equals("/shortend/CreateOrg") ||
          url.equals("/shortend/CheckOrgEmail") ||
          url.equals("/shortend/VirtualHosting")) {
        reDirect = true;
      }
    }

    if (reDirect) {
      System.out.println("MyFilter: doFilter() - redirect : true");
      chain.doFilter(request, response);
    } else {
      System.out.println("false no too");
      System.out.println("MyFilter: doFilter() - redirect : false");
      RequestDispatcher rd = request1.getRequestDispatcher("/MakeLogin");
      rd.include(request, response);
      System.out.println("finished");
    }
  }

  public void destroy() {
    System.out.println("MyFilter: destroy()");
  }
}