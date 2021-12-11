import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dbaction.Dbclass;
import javax.servlet.RequestDispatcher;
import java.io.PrintWriter;
import javax.servlet.*;
import java.io.*;
import org.json.*;

public class GetFileHtml extends HttpServlet {
    // download html file
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/plain");
                response.setHeader("Content-Disposition", "attachment; filename=\"librariabView.html\"");
                String newData="<table style='border: 1px solid black;'>\n\t<tr>\n\t\t<th style='border: 1px solid black;'>S_NO.</th>\n\t\t<th style='border: 1px solid black;'>Name</th>\n\t\t<th style='border: 1px solid black;'>Email Id</th>\n\t\t<th style='border: 1px solid black;'>Mobile Number</th>\n\t\t<th style='border: 1px solid black;'>Role</th>\n\t</tr>";
                Dbclass db = new Dbclass();
                JSONArray ja = new JSONArray();
                ja = db.getUserDateWithRole();
                try {
                        for (int count = 0; count < ja.length(); count++) {
                        JSONObject item = ja.getJSONObject(count);
                        String name = item.getString("name");
                        String email = item.getString("email");
                        String mobile = item.getString("mobile");
                        JSONArray temp = item.getJSONArray("role");
                        newData +="\n\t<tr>";
                        newData += "\n\t\t<td style='border: 1px solid black;'>"+String.valueOf((count+1))+"</td>";
                        newData += "\n\t\t<td style='border: 1px solid black;'>"+name+"</td>";
                        newData += "\n\t\t<td style='border: 1px solid black;'>"+email+"</td>";
                        newData += "\n\t\t<td style='border: 1px solid black;'>"+mobile+"</td>";
                        newData += "\n\t\t<td style='border: 1px solid black;'>";
                        for (int j = 0; j < temp.length(); j++) {
                                String pet = temp.getString(j);
                                newData += pet;
                                if(j != temp.length()-1){
                                    newData += ",";
                                }
                            }
                        newData += "</td>";
                        newData += "\n\t</tr>";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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