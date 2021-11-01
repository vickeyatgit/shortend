import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import dbaction.Dbclass;

import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;

import javax.servlet.*;
import org.json.*;
import java.io.*;


public class GetFilexls extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                Dbclass db = new Dbclass();
                response.setContentType( "application/vnd.ms-excel" ); 
                response.setHeader("Content-disposition","attachment; filename=Example.xls");

                String newData="<table><tr><th>S_NO.</th><th>Username</th><th>First Name</th><th>Last Name</th><th>Email Id</th><th>Mobile Number</th></tr>";
                ArrayList<ArrayList<String>> graph = new ArrayList<>();
                graph = db.librarianGetList();
                for (int count = 0; count < graph.size(); count++) {
                    newData +="<tr>";
                    newData += "<td>"+String.valueOf((count+1))+"</td>";
                    newData += "<td>"+graph.get(count).get(0)+"</td>";
                    newData += "<td>"+graph.get(count).get(1)+"</td>";
                    newData += "<td>"+graph.get(count).get(2)+"</td>";
                    newData += "<td>"+graph.get(count).get(3)+"</td>";
                    newData += "<td>"+graph.get(count).get(4)+"</td>";
                    newData += "</tr>";
                }
                newData += "</table > ";  
                try
                {
                    OutputStream outputStream = response.getOutputStream();
                    String outputResult = newData;
                    outputStream.write(outputResult.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                }
            }
}