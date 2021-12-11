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
import java.util.*;

//pdf
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
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

public class GetFilePdf extends HttpServlet {
        
    // download pdf file
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                Dbclass db = new Dbclass();
                response.setContentType("application/pdf");  
                response.setHeader("Content-disposition","inline; filename='javatpoint.pdf'");  
                try{
                    Document document = new Document();
                    PdfWriter.getInstance(document, response.getOutputStream());
                    document.open();
                    PdfPTable table = new PdfPTable(5);
                    JSONArray ja = new JSONArray();
                    ja = db.getUserDateWithRole();
                    Stream.of("S_NO","Name","Email Id","Mobile Number","Role")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle));
                        table.addCell(header);
                    });
                    for (int i=0; i<ja.length(); i++) {
                        JSONObject item = ja.getJSONObject(i);
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
                        table.addCell(String.valueOf((i+1)));
                        table.addCell(name);
                        table.addCell(email);
                        table.addCell(mobile);
                        table.addCell(role);
                    }
                    Paragraph paragraph = new Paragraph("Librarian Data");
                    document.add(paragraph);
                    document.add(table);
                    document.close();
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                }

            }
}