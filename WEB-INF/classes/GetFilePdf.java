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
// import com.itextpdf.text.Element;
// import com.itextpdf.text.Font;
// import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
// import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
// import com.itextpdf.text.pdf.PdfPCell;
// import com.itextpdf.text.pdf.PdfPTable;
// import com.itextpdf.text.pdf.PdfWriter;

public class GetFilePdf extends HttpServlet {
        
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                Dbclass db = new Dbclass();
                response.setContentType("application/pdf");  
                response.setHeader("Content-disposition","inline; filename='javatpoint.pdf'");  
                try{
                    Document document = new Document();
                    PdfWriter.getInstance(document, response.getOutputStream());

                    document.open();
                    PdfPTable table = new PdfPTable(6);
                    ArrayList<ArrayList<String>> graph = new ArrayList<>();
                    graph = db.librarianGetList();

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
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                }

            }
}