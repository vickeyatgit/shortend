
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import dbaction.Dbclass;
import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import org.json.JSONObject;
import javax.mail.internet.AddressException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.io.FileOutputStream;
import java.io.OutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chunk;
import java.util.stream.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import java.util.Base64;
import org.json.*;

public class StoreMail extends HttpServlet {

    Dbclass db = new Dbclass();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        JSONObject jo = new JSONObject(); //for send result
        Boolean roleChecker = false;
        Boolean result = false;
        PrintWriter out = response.getWriter();
        //1. get conformation 
        String pcid = request.getParameter("pcid");
        String rawCookie = request.getHeader("Cookie");
        if(rawCookie.length() > 0){
            String authRole = db.roleChecker(pcid,rawCookie);
            roleChecker = authRole.equals("admin");
        }
        System.out.println("roleChecker: "+roleChecker);
        //2. get email  and password
        String email="";
        String userPassword="";
        if(roleChecker){
            String token = "";
            String[] rawCookieParams = rawCookie.split(";");
            for(String rawCookieNameAndValue :rawCookieParams)
            {
                String[] rawCookieNameAndValuePair = rawCookieNameAndValue.split("=");
                String tempstring = new String(rawCookieNameAndValuePair[0]);
                tempstring.trim();
                if(tempstring.contains("token")){
                    String temp2 = new String(rawCookieNameAndValuePair[1]);
                    token = temp2;
                }
            }
            byte[] actualByte = Base64.getDecoder().decode(token);
            String actualToken = new String(actualByte);
            email = db.getNameFromSession(actualToken,pcid);
            userPassword = db.GetUserPassword(email);
        }
        System.out.println("email: "+email);
        System.out.println("password: "+userPassword);
        // 3. send email
        if(roleChecker && userPassword.length() > 0){
            String from = email;
            String pass = userPassword;
            String to = request.getParameter("to");
            String subject = request.getParameter("subject");
            String body = request.getParameter("body");
            String attachment = request.getParameter("attache");
            Boolean attachCheck = Boolean.parseBoolean(request.getParameter("attachCheck"));
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, pass);
                    }
                });
            if(attachCheck){
                String filename="";
                ArrayList<ArrayList<String>> graph = new ArrayList<>();
                graph = db.librarianGetList();
                switch (attachment) {
                        case "html":
                            {
                                String newData="<table>\n\t<tr>\n\t\t<th>S_NO.</th>\n\t\t<th>Username</th>\n\t\t<th>First Name</th>\n\t\t<th>Last Name</th>\n\t\t<th>Email Id</th>\n\t\t<th>Mobile Number</th>\n\t</tr>";
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
                                    FileWriter myWriter = new FileWriter("librarian.html");
                                    myWriter.write(newData);
                                    myWriter.close();
                                    System.out.println("Successfully wrote to the file.");
                                    filename = "librarian.html";
                                } catch (IOException e) {
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                }
                                break;
                            }
                        case "pdf":
                            {
                                try {
                                    Document document = new Document();
                                    OutputStream outputStream = new FileOutputStream(new File("librarian.pdf"));
                                    PdfWriter.getInstance(document, outputStream);
                                    document.open();
                                    PdfPTable table = new PdfPTable(6);
                                    Stream.of("S_NO","Username","First Name","Last Name","Email Id","Mobile Number")
                                    .forEach(columnTitle -> {
                                        PdfPCell header = new PdfPCell();
                                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                        header.setBorderWidth(2);
                                        header.setPhrase(new Phrase(columnTitle));
                                        table.addCell(header);
                                    });
                                    for (int count = 0; count < graph.size(); count++) {
                                        table.addCell(String.valueOf((count+1)));
                                        table.addCell(graph.get(count).get(0));
                                        table.addCell(graph.get(count).get(1));
                                        table.addCell(graph.get(count).get(2));
                                        table.addCell(graph.get(count).get(3));
                                        table.addCell(graph.get(count).get(4));                  
                                    }
                                    Paragraph paragraph = new Paragraph("Librarian Data");
                                    document.add(paragraph);
                                    document.add(table);
                                    document.close();
                                    outputStream.close();
                                    System.out.println("Pdf created successfully.");
                                    filename = "librarian.pdf";
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        case "csv":
                            {
                                String newData="S_NO,Username,First Name,Last Name,Email Id,Mobile Number\n";
                                for (int count = 0; count < graph.size(); count++) {
                                    newData += String.valueOf((count+1))+",";
                                    newData += graph.get(count).get(0)+",";
                                    newData += graph.get(count).get(1)+",";
                                    newData += graph.get(count).get(2)+",";
                                    newData += graph.get(count).get(3)+",";
                                    newData += graph.get(count).get(4)+",\n";
                                }
                                try (PrintWriter writer = new PrintWriter("librarian.csv")) {
                                    writer.write(newData.toString());
                                    System.out.println("done!");
                                    writer.close();
                                    System.out.println("Successfully wrote to the file.");
                                    filename = "librarian.csv";
                                } catch (IOException e) {
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                }
                                break;
                            }
                        case "xls":
                            {
                                String newData="<table>\n\t<tr>\n\t\t<th>S_NO.</th>\n\t\t<th>Username</th>\n\t\t<th>First Name</th>\n\t\t<th>Last Name</th>\n\t\t<th>Email Id</th>\n\t\t<th>Mobile Number</th>\n\t</tr>";
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
                                try (PrintWriter writer = new PrintWriter("librarian.xls")) {
                                    writer.write(newData.toString());
                                    System.out.println("done!");
                                    writer.close();
                                    System.out.println("Successfully wrote to the file.");
                                    filename = "librarian.xls";
                                } catch (IOException e) {
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                }
                                break;
                            }
                        default:
                            break;
                    }
                if(filename.length()>2){
                    try {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(from));
                        message.setRecipients(
                            Message.RecipientType.TO,
                            InternetAddress.parse(to)
                            );
                        message.setSubject(subject);
                        BodyPart messageBodyPart = new MimeBodyPart();
                        messageBodyPart.setText(body);
                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(messageBodyPart);
                        messageBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filename);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(filename);
                        multipart.addBodyPart(messageBodyPart);
                        message.setContent(multipart);
                        System.out.println("taking in");
                        Transport.send(message);
                        System.out.println("Done");
                        result=true;
                        //delete file
                        File file1 = new File(filename);
                        if(file1.delete()){System.out.println("File deleted");}
                        else System.out.println("File doesn't exists");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                System.out.println("from"+from);
                System.out.println("to"+to);
                System.out.println("subject"+subject);
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(to)
                    );
                    message.setSubject(subject);
                    message.setText(body);
                    System.out.println("taking in");
                    Transport.send(message);
                    System.out.println("Done");
                    result=true;
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch(Exception e){
                    System.out.println(e);
                }
            }
        }
        else{
            // jo.put("result", "fail");
            // jo.put("message", "You are not authorized to send email");
        }



            try {
                jo.put("result",result);
            }catch (Exception e) {
                e.printStackTrace();
            }
            out.println(jo);
            out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setHeader("Access-Control-Allow-Credentials", "true");
                System.out.println("doPost");
                PrintWriter out = response.getWriter();
                JSONObject jo = new JSONObject(); //for send result
                Boolean result = false;
                String from = request.getRemoteUser();
                System.out.println("from"+from);
                String pass = db.getPassword(from);
                String to = request.getParameter("to");
                String subject = request.getParameter("subject");
                String body = request.getParameter("body");
                String attachment = request.getParameter("attache");
                Boolean attachCheck = Boolean.parseBoolean(request.getParameter("attachCheck"));
                System.out.println("from"+from);
                System.out.println("pass"+pass);

                System.out.println("to"+to);
                System.out.println("subject"+subject);
                System.out.println("body"+body);
                System.out.println("attachment"+attachment);
                System.out.println("attachCheck"+attachCheck);

                // init property
                Properties prop = new Properties();
                prop.put("mail.smtp.host", "smtp.gmail.com");
                prop.put("mail.smtp.port", "587");
                prop.put("mail.smtp.auth", "true");
                prop.put("mail.smtp.starttls.enable", "true");
                Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, pass);
                    }
                });

                String filename="";
                if(attachCheck){
                    ArrayList<ArrayList<String>> graph = new ArrayList<>();
                    // graph = db.librarianGetList();
                    JSONArray ja = new JSONArray();
                    ja = db.getUserDateWithRole();
                    switch (attachment) {
                        case "html":
                            {
                                String newData="<table style='border: 1px solid black;'>\n\t<tr>\n\t\t<th style='border: 1px solid black;'>S_NO.</th>\n\t\t<th style='border: 1px solid black;'>Name</th>\n\t\t<th style='border: 1px solid black;'>Email Id</th>\n\t\t<th style='border: 1px solid black;'>Mobile Number</th>\n\t\t<th style='border: 1px solid black;'>Role</th>\n\t</tr>";
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
                                // create file
                                try {
                                    FileWriter myWriter = new FileWriter("librarian.html");
                                    myWriter.write(newData);
                                    myWriter.close();
                                    System.out.println("Successfully wrote to the file.");
                                    filename = "librarian.html";
                                } catch (IOException e) {
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                }
                                break;
                            }
                        case "pdf":
                            {
                                try {
                                    Document document = new Document();
                                    OutputStream outputStream = new FileOutputStream(new File("librarian.pdf"));
                                    PdfWriter.getInstance(document, outputStream);
                                    document.open();
                                    PdfPTable table = new PdfPTable(5);
                                    Stream.of("S_NO","Name","Email Id","Mobile Number","Role")
                                    .forEach(columnTitle -> {
                                        PdfPCell header = new PdfPCell();
                                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                        header.setBorderWidth(2);
                                        header.setPhrase(new Phrase(columnTitle));
                                        table.addCell(header);
                                    });
                                    for (int count = 0; count < ja.length(); count++) {
                                        JSONObject item = ja.getJSONObject(count);
                                        String name = item.getString("name");
                                        String email = item.getString("email");
                                        String mobile = item.getString("mobile");
                                        JSONArray temp = item.getJSONArray("role");
                                        String role = "[";
                                        ArrayList<String> array = new ArrayList<String>();
                                        for (int j = 0; j < temp.length(); j++) {
                                            String pet = temp.getString(j);
                                            role += pet;
                                            if(j != temp.length()-1){
                                                role += ",";
                                            }
                                            array.add(pet);
                                        }
                                        role += "]";
                                        table.addCell(String.valueOf((count+1)));
                                        table.addCell(name);
                                        table.addCell(email);
                                        table.addCell(mobile);
                                        table.addCell(role);
                                        // table.addCell(graph.get(count).get(0));
                                        // table.addCell(graph.get(count).get(1));
                                        // table.addCell(graph.get(count).get(2));
                                        // table.addCell(graph.get(count).get(3));
                                        // table.addCell(graph.get(count).get(4));                  
                                    }
                                    Paragraph paragraph = new Paragraph("Librarian Data");
                                    document.add(paragraph);
                                    document.add(table);
                                    document.close();
                                    outputStream.close();
                                    System.out.println("Pdf created successfully.");
                                    filename = "librarian.pdf";
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        case "csv":
                            {
                                String newData="S_NO,Name,Email Id,Mobile Number,Role\n";
                                try {
                                    for (int i=0; i<ja.length(); i++) {
                                        JSONObject item = ja.getJSONObject(i);
                                        String s_no = String.valueOf((i+1));
                                        String name = item.getString("name");
                                        String email = item.getString("email");
                                        String mobile = item.getString("mobile");
                                        JSONArray temp = item.getJSONArray("role");
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
                                // String newData="S_NO,Username,First Name,Last Name,Email Id,Mobile Number\n";
                                // for (int count = 0; count < graph.size(); count++) {
                                //     newData += String.valueOf((count+1))+",";
                                //     newData += graph.get(count).get(0)+",";
                                //     newData += graph.get(count).get(1)+",";
                                //     newData += graph.get(count).get(2)+",";
                                //     newData += graph.get(count).get(3)+",";
                                //     newData += graph.get(count).get(4)+",\n";
                                // }
                                try (PrintWriter writer = new PrintWriter("librarian.csv")) {
                                    writer.write(newData.toString());
                                    System.out.println("done!");
                                    writer.close();
                                    System.out.println("Successfully wrote to the file.");
                                    filename = "librarian.csv";
                                } catch (IOException e) {
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                }
                                break;
                            }
                        case "xls":
                            {
                                String newData="<table>\n\t<tr>\n\t\t<th>S_NO.</th>\n\t\t<th>Name</th>\n\t\t<th>Email Id</th>\n\t\t<th>Mobile Number</th>\n\t\t<th>Role</th>\n\t</tr>";
                                try {
                                    for (int count = 0; count < ja.length(); count++) {
                                        JSONObject item = ja.getJSONObject(count);
                                        String name = item.getString("name");
                                        String email = item.getString("email");
                                        String mobile = item.getString("mobile");
                                        JSONArray temp = item.getJSONArray("role");
                                        newData +="\n\t<tr>";
                                        newData += "\n\t\t<td >"+String.valueOf((count+1))+"</td>";
                                        newData += "\n\t\t<td >"+name+"</td>";
                                        newData += "\n\t\t<td >"+email+"</td>";
                                        newData += "\n\t\t<td >"+mobile+"</td>";
                                        newData += "\n\t\t<td >";
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
                                // String newData="<table>\n\t<tr>\n\t\t<th>S_NO.</th>\n\t\t<th>Username</th>\n\t\t<th>First Name</th>\n\t\t<th>Last Name</th>\n\t\t<th>Email Id</th>\n\t\t<th>Mobile Number</th>\n\t</tr>";
                                // for (int count = 0; count < graph.size(); count++) {
                                //     newData +="\n\t<tr>";
                                //     newData += "\n\t\t<td>"+String.valueOf((count+1))+"</td>";
                                //     newData += "\n\t\t<td>"+graph.get(count).get(0)+"</td>";
                                //     newData += "\n\t\t<td>"+graph.get(count).get(1)+"</td>";
                                //     newData += "\n\t\t<td>"+graph.get(count).get(2)+"</td>";
                                //     newData += "\n\t\t<td>"+graph.get(count).get(3)+"</td>";
                                //     newData += "\n\t\t<td>"+graph.get(count).get(4)+"</td>";
                                //     newData += "\n\t</tr>";
                                // }
                                // newData += "\n</table > ";
                                try (PrintWriter writer = new PrintWriter("librarian.xls")) {
                                    writer.write(newData.toString());
                                    System.out.println("done!");
                                    writer.close();
                                    System.out.println("Successfully wrote to the file.");
                                    filename = "librarian.xls";
                                } catch (IOException e) {
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                }
                                break;
                            }
                        default:
                            break;
                    }
                }
                //send mail
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
                    message.setSubject(subject);
                    if(attachCheck){
                        BodyPart messageBodyPart = new MimeBodyPart();
                        messageBodyPart.setText(body);
                        Multipart multipart = new MimeMultipart();
                        multipart.addBodyPart(messageBodyPart);
                        messageBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(filename);
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(filename);
                        multipart.addBodyPart(messageBodyPart);
                        message.setContent(multipart);
                    }else{
                        message.setText(body);
                    }
                    System.out.println("taking in");
                    Transport.send(message);
                    System.out.println("Done");
                    result=true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    jo.put("result",result);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            out.println(jo);
            out.flush();

        }


}