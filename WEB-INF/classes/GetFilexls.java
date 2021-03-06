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
import org.json.*;
import java.io.*;


public class GetFilexls extends HttpServlet {
    // download excel file
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                Dbclass db = new Dbclass();
                response.setContentType( "application/vnd.ms-excel" ); 
                response.setHeader("Content-disposition","attachment; filename=Example.xls");
                String newData="<table><tr><th>S_NO.</th><th>Name</th><th>Email Id</th><th>Mobile Number</th><th>Role</th></tr>";
                JSONArray ja = new JSONArray();
                ja = db.getUserDateWithRole();
                try {
                    for (int count = 0; count < ja.length(); count++) {
                        JSONObject item = ja.getJSONObject(count);
                        String name = item.getString("name");
                        String email = item.getString("email");
                        String mobile = item.getString("mobile");
                        JSONArray temp = item.getJSONArray("role");
                        newData +="<tr>";
                        newData += "<td>"+String.valueOf((count+1))+"</td>";
                        newData += "<td>"+name+"</td>";
                        newData += "<td>"+email+"</td>";
                        newData += "<td>"+mobile+"</td>";
                        String role = "";
                        for (int j = 0; j < temp.length(); j++) {
                            String pet = temp.getString(j);
                                role += pet;
                                if(j != temp.length()-1){
                                    role += ",";
                                }
                            }
                        newData += "<td>"+role+"</td>";
                        newData += "</tr>";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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