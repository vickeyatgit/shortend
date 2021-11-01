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
// import org.json.*;
import java.io.*;


public class GetFileHtml extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                // Dbclass db = new Dbclass();
                response.setContentType("text/plain");
                response.setHeader("Content-Disposition", "attachment; filename=\"librariabView.html\"");
                String newData="<table>\n\t<tr>\n\t\t<th>S_NO.</th>\n\t\t<th>Username</th>\n\t\t<th>First Name</th>\n\t\t<th>Last Name</th>\n\t\t<th>Email Id</th>\n\t\t<th>Mobile Number</th>\n\t</tr>";
                Dbclass db = new Dbclass();
                ArrayList<ArrayList<String>> graph = new ArrayList<>();
                graph = db.librarianGetList();
                for (int count = 0; count < graph.size(); count++) {
                    newData +="\n\t<tr>";
                    newData += "\n\t\t<td>"+String.valueOf((count+1))+"</td>";
                    newData += "\n\t\t<td>"+graph.get(count).get(0)+"</td>";
                    newData += "\n\t\t<td>"+graph.get(count).get(1)+"</td>";
                    newData += "\n\t\t<td>"+graph.get(count).get(2)+"</td>";
                    newData += "\n\t\t<td>"+graph.get(count).get(3)+"</td>";
                    newData += "\n\t\t<td>"+graph.get(count).get(4)+"</td>";
                    newData += "\n\t</tr>";
                }
                newData += "\n</table > "; 
                try {
                    OutputStream outputStream = response.getOutputStream();
                    String outputResult = newData;
                    outputStream.write(outputResult.getBytes());
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    
            }
}