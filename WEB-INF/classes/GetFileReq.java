

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



public class GetFileReq extends HttpServlet {
    // download csv file
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                Dbclass db = new Dbclass();
                JSONArray hi = new JSONArray();
                hi = db.readersData();
                String data = "a,b,c,d";
                String newData="S_NO,Name,Email Id,Mobile Number,Role\n";
                JSONArray ja = new JSONArray();
                ja = db.getUserDateWithRole();
                try {
                    for (int i=0; i<ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        String s_no = String.valueOf((i+1));
                        String name = jo.getString("name");
                        String email = jo.getString("email");
                        String mobile = jo.getString("mobile");
                        JSONArray temp = jo.getJSONArray("role");
                        String role = "\"";
                        ArrayList<String> array = new ArrayList<String>();
                        for (int j = 0; j < temp.length(); j++) {
                            String pet = temp.getString(j);
                            role += pet;
                            if(j != temp.length()-1){
                                role += ",";
                            }
                            array.add(pet);
                        }
                        role += "\"";
                        newData = newData + s_no + "," + name + "," + email + "," + mobile + "," + role + "\n";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(newData);
                response.setContentType("text/csv");
                response.setHeader("Content-Disposition", "attachment; filename=\"LibrarianData.csv\"");
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