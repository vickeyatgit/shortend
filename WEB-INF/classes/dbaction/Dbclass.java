package dbaction;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.*;
import java.sql.Date;
import org.json.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.Base64; 
import java.util.ArrayList;


public class Dbclass {
    String url = "jdbc:postgresql://localhost:5432/lib_management", userName = "postgres", password = "hello123";

    //connect to db
    public Connection getConnection() {
        Connection connect = null;
        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection(url, userName, password);
            System.out.println("Connected database successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect;
    }

    //admin login
    public Boolean login(String user, String pass) {
        Boolean res = false;
        Connection con = getConnection();
        String sql = "SELECT * FROM admin WHERE username = '" + user + "'AND password = '" + pass + "'";
        try {
            Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            while (r.next()) {
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }


    //filter librarian
    public ArrayList<ArrayList<String>> filterLibrarian(String value) {
        ArrayList<ArrayList<String>> graph = new ArrayList<>();
        try {
            Connection con = getConnection();
            String sql = " SELECT * FROM librarian WHERE username LIKE '%" + value + "%' ;";
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(sql);
            while (result.next()) {
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(result.getString("username"));
                temp.add(result.getString("firstname"));
                temp.add(result.getString("lastname"));
                temp.add(result.getString("emailid"));
                temp.add(result.getString("mobilenumner"));
                temp.add(result.getString("bookstally"));
                graph.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }
    //list of librarian for admin
    public ArrayList<ArrayList<String>> librarianGetList() {
        ArrayList<ArrayList<String>> graph = new ArrayList<>();
        // ArrayList<Librarianlist> libList = new ArrayList<Librarianlist>();
        try {
            Connection con = getConnection();
            String sql = " SELECT * FROM librarian ;";
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(sql);
            while (result.next()) {
                // Librarianlist e = new Librarianlist();
                // e.setUsername(result.getString("username"));
                // e.setPassword(pass);
                // e.setfirstname(result.getString("firstname"));
                // e.setlastname(result.getString("lastname"));
                // e.setemailid(result.getString("emailid"));
                // e.setmobilenumber(result.getString("mobilenumner"));
                // e.setbookstally(result.getInt("bookstally"));
                // libList.add(e);

                ArrayList<String> temp = new ArrayList<String>();
                temp.add(result.getString("username"));
                temp.add(result.getString("firstname"));
                temp.add(result.getString("lastname"));
                temp.add(result.getString("emailid"));
                temp.add(result.getString("mobilenumner"));
                temp.add(result.getString("bookstally"));
                graph.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    //add Admin
    public Boolean createAdmin(String user, String pass) {
        Boolean res = false;
        Connection con = getConnection();
        String query = "INSERT INTO admin (username,password) VALUES (?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            int countInserted = ps.executeUpdate();
            if (countInserted == 1) {
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //add librarian
    public Boolean insertLib(String username, String password, String firstname, String lastname, String email,
            String mobile) {
        Boolean res = false;
        String query = "INSERT INTO librarian (username,password,firstname,lastname,mobilenumner,emailid,bookstally) VALUES (?,?,?,?,?,?,?);";
        Connection con = getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, firstname);
            ps.setString(4, lastname);
            ps.setString(5, mobile);
            ps.setString(6, email);
            ps.setInt(7, 0);
            int countInserted = ps.executeUpdate();
            if (countInserted == 1) {
                res = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //delete librarian
    public Boolean deleteLib(String user) {
        boolean res = false;
        String query = "Delete From librarian WHERE  username = '" + user + "'";
        Connection con = getConnection();
        try {
            Statement state = con.createStatement();
            int result = state.executeUpdate(query);
            if (result == 1) {
                System.out.println("Successfully Deleted");
                res = true;
            }

            state.close();
        } catch (Exception e) {

        }
        return res;
    }

    //login librarian
    public Boolean loginLibrarian(String user, String pass) {
        String sql = "SELECT * FROM librarian WHERE username = '" + user + "'AND password = '" + pass + "'";
        // Dictionary geek = new Hashtable();
        Boolean check=false ;
        try {
            Connection con = getConnection();
            Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            if (r.next() != false) {
                System.out.println("entered inDB " + check);
                check = true;
            }
            System.out.println("in DB module " + check);
            // if (r.next() == false) {
            //     return geek;
            // }
            // do {
            //     geek.put("username", r.getString("username"));
            //     geek.put("firstname", r.getString("firstname"));
            //     geek.put("lastname", r.getString("lastname"));
            //     geek.put("mobilenumner", r.getString("mobilenumner"));
            //     geek.put("emailid", r.getString("emailid"));
            //     geek.put("bookstally", r.getString("bookstally"));
            // } while (r.next());
        } catch (Exception e) {
            e.printStackTrace();
            check = false;
        }
        return check;
    }

    //get details of librarian
    public Dictionary<String, String> getLibrarianData(String user) {
        Dictionary geek = new Hashtable();
        String sql = "SELECT * FROM librarian WHERE username = '" + user + "'";
        try {
            Connection con = getConnection();
            Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            if (r.next() == false) {
                return geek;
            }
            do {
                geek.put("username", r.getString("username"));
                geek.put("firstname", r.getString("firstname"));
                geek.put("lastname", r.getString("lastname"));
                geek.put("mobilenumner", r.getString("mobilenumner"));
                geek.put("emailid", r.getString("emailid"));
                geek.put("bookstally", r.getString("bookstally"));
            } while (r.next());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geek;
    }

    //add book 
    public Boolean addBook(
            String title, String subtitle, String[] author, String category,
            String language, String publish, String edition, 
            String tot,String[] genre
    ) {
        String ss = "SELECT bookunify FROM bookrack WHERE title=? AND subtitle=? AND author=? AND language=? AND category=? AND publish=? AND genre=? AND edition=? ";
        int total = Integer.parseInt(tot);
        int BookunifyNumber;
        Boolean completePath = false;
        try {
            System.out.println("hello welcome to try block");
            Connection con = getConnection();
            PreparedStatement ps1 = con.prepareStatement(ss);
            ps1.setString(1, title);
            ps1.setString(2, subtitle);
            ps1.setArray(3, con.createArrayOf("TEXT", author));
            ps1.setString(4, language);
            ps1.setString(5, category);
            ps1.setDate(6, java.sql.Date.valueOf(publish));
            ps1.setArray(7, con.createArrayOf("TEXT", genre));
            ps1.setString(8, edition);
            System.out.println("getting result");
            ResultSet r = ps1.executeQuery();
            System.out.println("finish result");
            if (r.next() == false) {
                //insert new 
                System.out.println("not exist");
                //insert
                String sql = "INSERT INTO bookrack (title,subtitle,author,category,publish,genre,edition,language,intake,available) VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING bookunify";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, title);
                ps.setString(2, subtitle);
                Array array = con.createArrayOf("TEXT", author);
                ps.setArray(3, array);
                ps.setString(4,category);
                ps.setDate(5, java.sql.Date.valueOf(publish));
                array = con.createArrayOf("TEXT", genre);
                ps.setArray(6,array);
                ps.setString(7,edition);
                ps.setString(8, language);
                ps.setDate(9, java.sql.Date.valueOf(java.time.LocalDate.now()));
                ps.setBoolean(10, true);
                ResultSet rs = ps.executeQuery();
                total--;
                int BookNO;
                System.out.println("finished query");
                if (rs.next() == false) {
                    System.out.println("enter to false session ");
                    return false;
                } 
                do{
                    System.out.println("enter into true query");
                    BookNO = rs.getInt("bookunify");
                    BookunifyNumber = BookNO;
                    System.out.println("enter into true query  "+BookNO);
                }
                while (rs.next());
                sql = "INSERT INTO bookunify (bookid,bookaddup) VALUES (?,?) ;";
                ps = con.prepareStatement(sql);
                ps.setInt(1, BookNO);
                ps.setInt(2, Integer.parseInt(tot));
                int count = ps.executeUpdate();
                System.out.println("finished"+count);
                if (count > 0)
                completePath = true;
                else return false;
            } else {
                System.out.println("true exist");
                int BookNO;
                BookNO = r.getInt("bookunify");
                BookunifyNumber = BookNO;
                //insert
                //update
                ss = "UPDATE bookunify SET bookaddup=bookaddup+? WHERE bookid=?";
                PreparedStatement ps2 = con.prepareStatement(ss);
                ps2.setInt(1, Integer.parseInt(tot));
                ps2.setInt(2, BookNO);
                int count = ps2.executeUpdate();
                System.out.println("finished" + count);
                if (count > 0)
                    completePath = true;
                else
                    return false;
            }
            if(completePath){
                String sql = "INSERT INTO bookrack (title,subtitle,author,category,publish,genre,edition,language,intake,available,bookunify) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, title);
                ps.setString(2, subtitle);
                Array array = con.createArrayOf("TEXT", author);
                ps.setArray(3, array);
                ps.setString(4, category);
                ps.setDate(5, java.sql.Date.valueOf(publish));
                array = con.createArrayOf("TEXT", genre);
                ps.setArray(6, array);
                ps.setString(7, edition);
                ps.setString(8, language);
                ps.setDate(9, java.sql.Date.valueOf(java.time.LocalDate.now()));
                ps.setBoolean(10, true);
                ps.setInt(11, BookunifyNumber);
                
                for (int i = 0; i < total; i++) {
                    int countInserted = ps.executeUpdate();
                    if (countInserted == 0)
                        return false;
                }
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("hello welcome to catch block");
            return false;
        }
    }

    //list of books
    public JSONArray librarianBook() {

        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        // String bookUnify = "SELECT * FROM bookunify;";
        JSONArray ja = new JSONArray();
        // JSONObject bookunify = new JSONObject();
        Dictionary bookunify = new Hashtable();
        try {
            Connection con = getConnection();

            String bookRack = "SELECT * FROM bookrack";
            String bookUnify = "SELECT * FROM bookunify;";
            Statement state = con.createStatement();
            //get book unify
            ResultSet result1 = state.executeQuery(bookUnify);
            while (result1.next()) {
                bookunify.put(result1.getInt("bookid"), result1.getInt("bookaddup"));
            }
            //get book rack
            ResultSet result = state.executeQuery(bookRack);
            while (result.next()) {
                JSONObject jo = new JSONObject();
                jo.put("id", result.getInt("id"));
                jo.put("title", result.getString("title"));
                jo.put("subtitle", result.getString("subtitle"));
                jo.put("author", result.getArray("author"));
                jo.put("category", result.getString("category"));
                jo.put("genre", result.getArray("genre"));
                jo.put("intake", result.getDate("intake"));
                jo.put("publish", result.getDate("publish"));
                jo.put("bookunify", result.getInt("bookunify"));
                jo.put("edition", result.getString("edition"));
                jo.put("language", result.getString("language"));
                jo.put("available", result.getBoolean("available"));
                jo.put("bookcount",bookunify.get(result.getInt("bookunify")) );
                ja.put(jo);
            }
            result.close();
            System.out.println("finished dbclass");
            System.out.println(ja);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }
    

    //check reader availability
    public Boolean ReadersCheck(String userName) {
        Boolean check = false;
        String sql = "SELECT * FROM reader where username='" + userName + "'";
        try {
            Connection con = getConnection();
            Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            if (r.next() == false) {
                check = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //add reader
    public Boolean addReader(String username,String name) {
        Boolean check = false;
        String sql = "INSERT INTO reader (username,name) VALUES('"+username+"','"+name+"')";
        try {
            Boolean userCheck = ReadersCheck(username);
            if (userCheck) {
                Connection con = getConnection();
                Statement stat = con.createStatement();
                int addCount = stat.executeUpdate(sql);
                if (addCount > 0) {
                    check = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //delete books
    public String RemoveBooks(String bookId, String bookCount) {
        String check = "Try Again SomeTime";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            //1.check number of book
            String sql1 = "SELECT * FROM bookunify Where bookId='" + bookId + "'";
            ResultSet result = state.executeQuery(sql1);
            if (result.next() == false)
                return "Book Could not Found";
            int getBookId = result.getInt("bookid");
            int getBookCount = result.getInt("bookaddup");
            if(Integer.parseInt(bookCount) > getBookCount)  return "Please Check Book Available Count";
            //2.get id
            String sql2 = "SELECT id FROM bookrack WHERE available=true AND bookunify='"+bookId+"' LIMIT '" + bookCount + "';";
            System.out.println(sql2);
            result = state.executeQuery(sql2);
            ArrayList<Integer> rackBookId = new ArrayList<Integer>();
            while (result.next()) {
                System.out.println(result.getInt("id"));
                rackBookId.add(result.getInt("id"));
            }
            if (rackBookId.size() == Integer.parseInt(bookCount)) {
                String sql3 = "DELETE FROM bookrack WHERE id in (";
                String temp ="";
                for (int i = 0; i < Integer.parseInt(bookCount); i++) {
                    temp += "?,";
                }
                sql3 += temp.substring(0, temp.length() - 1);
                sql3 += ");";
                System.out.println(sql3);
                PreparedStatement ps = con.prepareStatement(sql3);
                for (int i = 0; i < Integer.parseInt(bookCount); i++) {
                    ps.setInt(i + 1, rackBookId.get(i));
                }
                //delete book from bookrack
                int c = ps.executeUpdate();
                //minus book from bookunify
                System.out.println("hello ENter to update");
                String sql4 = "UPDATE bookunify SET bookaddup = bookaddup -'"+bookCount+"' WHERE bookid='" + bookId + "' ;";
                int count = state.executeUpdate(sql4);
                if (count > 0) {
                    return "Successfully Removed From Library Rack";
                } else {
                    return "failed to update in Some AREA";
                }
            }else{
                return "No enough Books Available, May Some be Borrowed";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //give book to reader
    public String lendBook(String bookId, String username) {
        String check = "Try Again SomeTime";
        try {
            String sql1 = "SELECT * FROM bookrack WHERE bookunify='" + bookId + "' AND available= true LIMIT 1;";
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(sql1);
            //book is not Available
            if (result.next() == false)
                return "book is not Available";
            int lendBookId = result.getInt("id");
            //get reader id
            String sql2 = "SELECT id FROM READER WHERE username='" + username + "'";
            result = state.executeQuery(sql2);
            if (result.next() == false)
                return check;
            int readerId = result.getInt("id");
            //insert query
            String sql3 = "INSERT INTO booklend (readerid,Bookid) VALUES(?,?);";
            PreparedStatement ps = con.prepareStatement(sql3);
            ps.setInt(1, readerId);
            ps.setInt(2, lendBookId);
            int booksCount = ps.executeUpdate();
            if (booksCount > 0) {
                String sql4 = "UPDATE bookrack SET available = false WHERE id = '" + lendBookId + "'";
                int up = state.executeUpdate(sql4);
                if (up > 0)
                    check = "Successfully book is borrowed";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //filter books
    public JSONArray filterBooks(String title,String lang,String auth,String cat ,String avail) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        JSONArray ja = new JSONArray();
        Dictionary bookunify = new Hashtable();
        try {
            Connection con = getConnection();

            Boolean andCheck= false;
            String bookRack = "SELECT * FROM bookrack WHERE ";
            if (title.length() > 0) {
                bookRack += "LOWER(title) LIKE '%" + title + "%'";
                andCheck = true;
            }
            if (lang.length() > 0) {
                if (andCheck)
                    bookRack += " AND ";
                bookRack += "LOWER(language) like '%" + lang + "%'";
                andCheck = true;
            }
            if (avail.length() > 0) {
                if (andCheck)
                    bookRack += " AND ";
                bookRack += "available='" + avail + "'";
                andCheck = true;
            }
            if (cat.length() > 0) {
                if (andCheck)
                    bookRack += " AND ";
                bookRack += "LOWER(category) like '%" + cat + "%'";
                andCheck = true;
            }
            if (auth.length() > 0) {
                if (andCheck)
                    bookRack += " AND ";
                bookRack += "array_to_string(author,',') like '%" + auth + "%'";
            }
            System.out.println(bookRack);
            //after remove commends
            String bookUnify = "SELECT * FROM bookunify;";
            Statement state = con.createStatement();
            //get book unify
            ResultSet result1 = state.executeQuery(bookUnify);
            while (result1.next()) {
                bookunify.put(result1.getInt("bookid"), result1.getInt("bookaddup"));
            }
            //get book rack
            ResultSet result = state.executeQuery(bookRack);
            while (result.next()) {
                JSONObject jo = new JSONObject();
                jo.put("id", result.getInt("id"));
                jo.put("title", result.getString("title"));
                jo.put("subtitle", result.getString("subtitle"));
                jo.put("author", result.getArray("author"));
                jo.put("category", result.getString("category"));
                jo.put("genre", result.getArray("genre"));
                jo.put("intake", result.getDate("intake"));
                jo.put("publish", result.getDate("publish"));
                jo.put("bookunify", result.getInt("bookunify"));
                jo.put("edition", result.getString("edition"));
                jo.put("language", result.getString("language"));
                jo.put("available", result.getBoolean("available"));
                jo.put("bookcount",bookunify.get(result.getInt("bookunify")) );
                ja.put(jo);
            }
            result.close();
            System.out.println("finished dbclass");
            System.out.println(ja);

            //end here
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }

    //readers lend books
    public JSONArray readersData() {
        JSONArray ja = new JSONArray();
        try {
            Connection con = getConnection();
            String bookLend = "SELECT * FROM booklend";
            Statement state = con.createStatement();
            ArrayList<Integer> readersId = new ArrayList<>();
            ArrayList<Integer> bookId = new ArrayList<>();
            ArrayList<ArrayList<Integer>> lendingDate = new ArrayList<ArrayList<Integer>>();
            int i = 0;
            //get leaders Data
            ResultSet result = state.executeQuery(bookLend);
            while (result.next()) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(result.getInt("readerid"));
                temp.add(result.getInt("bookid"));
                lendingDate.add(temp);
                readersId.add(result.getInt("readerid"));
                bookId.add(result.getInt("bookid"));
            }
            result.close();
            //get book data
            String bookData = "SELECT * FROM bookrack WHERE id IN (";
            String ids = "";
            for (Integer temp : bookId) {
                ids += String.valueOf(temp) + ",";
            }
            bookData += ids.substring(0, ids.length() - 1) + ");";
            Dictionary bookDetails = new Hashtable();
            result = state.executeQuery(bookData);
            while (result.next()) {
                bookDetails.put(result.getInt("id"), result.getString("title"));
            }
            result.close();
            //get readers details
            String readersData = "SELECT * FROM reader WHERE id IN (";
            ids = "";
            for (Integer temp : readersId) {
                ids += String.valueOf(temp) + ",";
            }
            readersData += ids.substring(0, ids.length() - 1) + ");";
            Dictionary readersDetails = new Hashtable();
            result = state.executeQuery(readersData);
            while (result.next()) {
                // result.getString("username"), result.getString("name")
                String[] det = new String[2];
                det[0] = result.getString("username");
                det[1] = result.getString("name");
                readersDetails.put(result.getInt("id"), det);
            }

            //arranging data
            for (int j = 0; j < lendingDate.size(); j++) {
                JSONObject jo = new JSONObject();
                jo.put("id", j+1);
                jo.put("readerid", lendingDate.get(j).get(0));
                System.out.println(readersDetails.get(lendingDate.get(j).get(0)));
                String[] t = (String[]) readersDetails.get(lendingDate.get(j).get(0));
                jo.put("readerusername", t[0]);
                jo.put("readername", t[1]);
                jo.put("bookid", lendingDate.get(j).get(1));
                jo.put("bookname", bookDetails.get(lendingDate.get(j).get(1)));
                ja.put(jo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }

    //pic chart
    public String getDashChart() {
        String res = "";
        try {
            Connection con = getConnection();
            String sql = "SELECT COUNT(*) FROM booklend ;"; //no book borrowed
            Statement state = con.createStatement();
            ResultSet rs = state.executeQuery(sql);
            if (rs.next() != false) {
                res += String.valueOf(rs.getInt("count"))+",";
            }
            sql = "SELECT COUNT(*) FROM bookrack ;"; //no of book 
            rs = state.executeQuery(sql);
            if (rs.next() != false) {
                res += String.valueOf(rs.getInt("count"));
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //add mail items to mail
    public Boolean mail(String from,String to,String sub,String body,String time){
        Boolean check = false;
        String query = "INSERT INTO mails (emailfrom,emailto,subject,body,time) VALUES (?,?,?,?,?);";
        Connection con = getConnection();
        // public static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));  
        // LocalDate localDate = LocalDate.now();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, from);
            ps.setString(2, to);
            ps.setString(3, sub);
            ps.setString(4, body);
            // ps.setTimestamp(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
            ps.setString(5, time);
            int countInserted = ps.executeUpdate();
            if (countInserted == 1) {
                check = true;
            }
            System.out.println(check);
            
        } catch (Exception e) {
            e.printStackTrace();
            check=false;
        }
        return check;
    }

    //login into session
    public Boolean sessionReg(String pcId,String sessionId,String username,String role ){
        Boolean check = false;
        String query = "INSERT INTO sessionlogin (username,role,pcid,sessionid) VALUES (?,?,?,?);";
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, role);
            ps.setString(3, pcId);
            ps.setString(4, sessionId);
            int countInserted = ps.executeUpdate();
            if (countInserted == 1) {
                check = true;
            }
            System.out.println(check);
        }catch (Exception e) {
            e.printStackTrace();
            check=false;
        }
        return check;
    }

    //session is active or not
    public String sessionChecker(String token,String pcid){
        String query = "SELECT id,role FROM sessionlogin WHERE sessionid='"+token+"' AND pcid='"+pcid+"';";
        String carry = "error";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                carry = result.getString("role");
            }
            System.out.println("carry => "+carry);
            Boolean sessionRemover = removeUserFromSession();
        }catch (Exception e) {
            e.printStackTrace();
            
        }
        return carry;
    }

    //session logout
    public Boolean sessionLogout(String token,String pcid){
        Boolean suc = false;
        String query = "DELETE FROM sessionlogin WHERE sessionid=? AND pcid=? ;";
        try {  
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, token);
            pstmt.setString(2, pcid);

            int affectedrows = pstmt.executeUpdate();
            if(affectedrows>0) suc=true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return suc;
    }

    //return role of user
    public String roleChecker(String pcid,String rawCookie){
        // String roleOut = "erroe";
        //get cookied
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
        if(token.length() <= 0) return "nocookie";
        byte[] actualByte = Base64.getDecoder().decode(token);
        String actualToken = new String(actualByte);
        String getResult =  sessionChecker(actualToken,pcid);
        return getResult;
    }

    //get username by token and pc id in ency format
    public String getUserName(String pcid,String rawCookie){
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
        if(token.length() <= 0) return "nocookie";
        byte[] actualByte = Base64.getDecoder().decode(token);
        String actualToken = new String(actualByte);
        String getResult = getNameFromSession(actualToken,pcid);
        return getResult;
    }
    
    //get name from session
    public String getNameFromSession(String token,String pcid){
        String query = "SELECT id,username FROM sessionlogin WHERE sessionid='"+token+"' AND pcid='"+pcid+"';";
        String carry = "error";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                carry = result.getString("username");
            }
            System.out.println("carry => "+carry);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return carry;
    }

    //get user password
    public  String GetUserPassword(String username){
        String query = "SELECT password FROM admin WHERE username='"+username+"';";
        String carry = "";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                carry = result.getString("password");
            }
            System.out.println("carry => "+carry);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return carry;
    }

    //remove user from session
    public Boolean removeUserFromSession(){
        Boolean suc = false;
        String query = " DELETE FROM sessionlogin WHERE date < (NOW() - INTERVAL '60' MINUTE) ;";
        try {  
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            // pstmt.setString(1, username);

            int affectedrows = pstmt.executeUpdate();
            if(affectedrows>0) suc=true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return suc;
    }

    //send mail automatic in regular interval
    public Boolean storeMailInterval(String from,String to,String subject,String body,String fileType,String fileName,String type,String minutes,String onDate){
        Boolean suc = false;
        // String query = "INSERT INTO sendmail (mailfrom,mailto,subject,body,attachment,type,intervalminutes,ondate) VALUES (?,?,?,?,?,?,?,?);";
        // try {  
        //     Connection con = getConnection();
        //     PreparedStatement pstmt = con.prepareStatement(query);
        //     pstmt.setString(1, from);
        //     pstmt.setString(2, to);
        //     pstmt.setString(3, subject);
        //     pstmt.setString(4, body);
        //     pstmt.setString(5, fileType);
        //     pstmt.setString(6, type);
        //     pstmt.setString(7, minutes);
        //     pstmt.setString(7, onDate);

        //     int affectedrows = pstmt.executeUpdate();
        //     if(affectedrows>0) suc=true;
        // }catch (Exception e) {
        //     e.printStackTrace();
        // }
        return suc;
    }


}

