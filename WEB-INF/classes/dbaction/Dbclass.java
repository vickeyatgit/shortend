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
import java.sql.Timestamp;



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
        String sql = "SELECT * FROM admin WHERE username = '" + user + "'AND password = '" + pass + "'";
        try {
            Connection con = getConnection();
            Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            while (r.next()) {
                res = true;
            }
            con.close();
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
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    //list of librarian for admin
    public ArrayList<ArrayList<String>> librarianGetList() {
        ArrayList<ArrayList<String>> graph = new ArrayList<>();
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
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return graph;
    }

    //add Admin
    public Boolean createAdmin(String user, String pass) {
        Boolean res = false;
        String query = "INSERT INTO admin (username,password) VALUES (?,?)";
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);
            int countInserted = ps.executeUpdate();
            if (countInserted == 1) {
                res = true;
            }
            con.close();
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
        try {
            Connection con = getConnection();
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
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //delete librarian
    public Boolean deleteLib(String user) {
        boolean res = false;
        String query = "Delete From librarian WHERE  username = '" + user + "'";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            int result = state.executeUpdate(query);
            if (result == 1) {
                System.out.println("Successfully Deleted");
                res = true;
            }
            con.close();
            state.close();
        } catch (Exception e) {
            e.printStackTrace();
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
            con.close();
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
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return geek;
    }

    //add book 
    public Boolean addBook(
            String title, String subtitle, String[] author, String category,
            String language, String publish, String edition, 
            String tot,String[] genre,int businessId
    ) {
        String ss = "SELECT bookunify FROM bookrack WHERE title=? AND subtitle=? AND author=? AND language=? AND category=? AND publish=? AND genre=? AND edition=? AND businessid=? ;";
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
            ps1.setInt(9,businessId);
            System.out.println("getting result");
            ResultSet r = ps1.executeQuery();
            System.out.println("finish result");
            if (r.next() == false) {
                //insert new 
                System.out.println("not exist");
                //insert
                String sql = "INSERT INTO bookrack (title,subtitle,author,category,publish,genre,edition,language,intake,available,businessid) VALUES (?,?,?,?,?,?,?,?,?,?,?) RETURNING bookunify";
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
                ps.setInt(11,businessId);
                ResultSet rs = ps.executeQuery();
                total--;
                int BookNO;
                System.out.println("finished query");
                if (rs.next() == false) {
                    System.out.println("enter to false session ");
                    con.close();
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
                else {
                    con.close();
                    return false;
                }
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
                    {
                        con.close();
                        return false;
                    }
            }
            if(completePath){
                String sql = "INSERT INTO bookrack (title,subtitle,author,category,publish,genre,edition,language,intake,available,bookunify,businessid) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
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
                ps.setInt(12, businessId);
                for (int i = 0; i < total; i++) {
                    int countInserted = ps.executeUpdate();
                    if (countInserted == 0){
                        con.close();
                        return false;
                    }
                }
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("hello welcome to catch block");
            return false;
        }
    }

    //list of books
    public JSONArray librarianBook(int businessId) {

        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        JSONArray ja = new JSONArray();
        // JSONObject bookunify = new JSONObject();
        Dictionary bookunify = new Hashtable();
        try {
            Connection con = getConnection();

            String bookRack = "SELECT * FROM bookrack WHERE businessid='"+businessId+"';";
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
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }
    

    //check reader availability
    public Boolean ReadersCheck(String userName,int businessId) {
        Boolean check = false;
        String sql = "SELECT * FROM reader where username='" + userName + "' AND businessid='"+businessId+"' ";
        try {
            Connection con = getConnection();
            Statement stat = con.createStatement();
            ResultSet r = stat.executeQuery(sql);
            if (r.next() == false) {
                check = true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //add reader
    public Boolean addReader(String username,String name,int businessId) {
        Boolean check = false;
        String sql = "INSERT INTO reader (username,name,businessid) VALUES('"+username+"','"+name+"','"+businessId+"')";
        try {
            // Boolean userCheck = ReadersCheck(username);
            // if (userCheck) {
                Connection con = getConnection();
                Statement stat = con.createStatement();
                int addCount = stat.executeUpdate(sql);
                if (addCount > 0) {
                    check = true;
                }
                con.close();
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //delete books
    public String RemoveBooks(String bookId, String bookCount,int businessId) {
        String check = "Try Again SomeTime";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            //1.check number of book
            String sql1 = "SELECT * FROM bookunify Where bookId='" + bookId + "';";
            ResultSet result = state.executeQuery(sql1);
            if (result.next() == false)
                {
                    con.close();
                    return "Book Could not Found";
                }
            int getBookId = result.getInt("bookid");
            int getBookCount = result.getInt("bookaddup");
            if(Integer.parseInt(bookCount) > getBookCount)  return "Please Check Book Available Count";
            //2.get id
            String sql2 = "SELECT id FROM bookrack WHERE available=true AND bookunify='"+bookId+"' AND businessid='"+ businessId +"' LIMIT '" + bookCount + "';";
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
                sql3 += ") AND businessid='"+ businessId +"';";
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
                    con.close();
                    return "Successfully Removed From Library Rack";
                } else {
                    con.close();
                    return "failed to update in Some AREA";
                }
            }else{
                con.close();
                return "No enough Books Available, May Some be Borrowed";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //give book to reader
    public String lendBook(String bookId, String username,int businessId) {
        String check = "Try Again SomeTime";
        try {
            String sql1 = "SELECT * FROM bookrack WHERE bookunify='" + bookId + "' AND businessid='"+ businessId +"' AND available= true LIMIT 1;";
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(sql1);
            //book is not Available
            if (result.next() == false)
                return "book is not Available";
            int lendBookId = result.getInt("id");
            //get reader id
            String sql2 = "SELECT id FROM READER WHERE username='" + username + "' AND businessid='"+ businessId +"';";
            result = state.executeQuery(sql2);
            if (result.next() == false)
                return check;
            int readerId = result.getInt("id");
            //insert query
            String sql3 = "INSERT INTO booklend (readerid,Bookid,businessid) VALUES(?,?,?);";
            PreparedStatement ps = con.prepareStatement(sql3);
            ps.setInt(1, readerId);
            ps.setInt(2, lendBookId);
            ps.setInt(3,businessId);
            int booksCount = ps.executeUpdate();
            if (booksCount > 0) {
                String sql4 = "UPDATE bookrack SET available = false WHERE id = '" + lendBookId + "' AND businessid = '"+businessId+"';";
                int up = state.executeUpdate(sql4);
                if (up > 0)
                    check = "Successfully book is borrowed";
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //filter books
    public JSONArray filterBooks(String title,String lang,String auth,String cat ,String avail,int businessId) {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        JSONArray ja = new JSONArray();
        Dictionary bookunify = new Hashtable();
        try {
            Connection con = getConnection();

            Boolean andCheck= false;
            String bookRack = "SELECT * FROM bookrack WHERE businessid='"+businessId+"' ";
            if (title.length() > 0) {
                bookRack += "AND LOWER(title) LIKE '%" + title + "%'";
                andCheck = true;
            }
            if (lang.length() > 0) {
                bookRack += "AND LOWER(language) like '%" + lang + "%'";
                andCheck = true;
            }
            if (avail.length() > 0) {
                bookRack += "AND available='" + avail + "'";
                andCheck = true;
            }
            if (cat.length() > 0) {
                bookRack += "AND LOWER(category) like '%" + cat + "%'";
                andCheck = true;
            }
            if (auth.length() > 0) {
                bookRack += "AND array_to_string(author,',') like '%" + auth + "%'";
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
            con.close();
            //end here
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }

    //readers lend books
    public JSONArray readersData(int businessId) {
        JSONArray ja = new JSONArray();
        try {
            Connection con = getConnection();
            String bookLend = "SELECT * FROM booklend WHERE businessid = '"+businessId+"';";
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
            bookData += ids.substring(0, ids.length() - 1) + ") AND businessid = '"+businessId+"';";
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
            readersData += ids.substring(0, ids.length() - 1) + ") AND businessid = '"+businessId+"' ;";
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
            con.close();
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
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //add mail items to mail
    public Boolean mail(String from,String to,String sub,String body,String time,String attachment,String type,int minutes,String date){
        Boolean check = false;
        String query = "INSERT INTO mails (mailfrom,mailto,subject,body,attachment,type,intervalminutes,ondate) VALUES (?,?,?,?,?,?,?,?);";
        // Connection con = getConnection();
        
        // public static final Calendar tzUTC = Calendar.getInstance(TimeZone.getTimeZone("UTC"));  
        // LocalDate localDate = LocalDate.now();
        try {
            // PreparedStatement ps = con.prepareStatement(query);
            // ps.setString(1, from);
            // ps.setString(2, to);
            // ps.setString(3, sub);
            // ps.setString(4, body);
            // ps.setString(5, attachment);
            // ps.setString(6, type);
            // ps.setInt(7, minutes);
            // ps.setInt(8,"" );
            // ps.setTimestamp(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
            // ps.setString(5, time);
            // int countInserted = ps.executeUpdate();
            // if (countInserted == 1) {
            //     check = true;
            // }
            // System.out.println(check);
            
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
            con.close();
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
            con.close();
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
            con.close();
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
            con.close();
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
            con.close();
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
            con.close();
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

    //account check login credentials for all in one
    public ArrayList<String> checkLoginCredentials(String username,String password){
        ArrayList<String> list = new ArrayList<String>();
        String query = "SELECT * FROM role WHERE id=(SELECT roleid FROM user_role WHERE userid=(SELECT id FROM useraccount WHERE email='"+username+"' AND password='"+password+"'))";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                list.add(result.getString("rolename"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        System.out.println("list => ");
        for(String s : list){
            System.out.println(s);
        } System.out.println("list => => => => => => => => => => => =>");
        return list;
    }

    //create new user
    public Boolean makeLoginCredentials(String email,String fullname,String phone,String password){
        Boolean suc = false;
        String query = "INSERT INTO account (email,password,fullname,mobilenumber) VALUES (?,?,?,?);";
        try {  
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, fullname);
            pstmt.setString(4, phone);
            int affectedrows = pstmt.executeUpdate();
            if(affectedrows>0) suc=true;
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return suc;
    }

    //list of unrole user
    public JSONArray listOfUnRole(){
        System.out.println("in list of unrule");
        JSONArray ja = new JSONArray();
        String bookLend = "SELECT * FROM account WHERE role IS NULL; ";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(bookLend);
            while (result.next()) {
                System.out.println("true");
                JSONObject jo = new JSONObject();
                jo.put("email", result.getString("email"));
                jo.put("fullname", result.getString("fullname"));
                ja.put(jo);
                
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }

    //get lst of role and resources
    public JSONArray roleAndResourse() {
        System.out.println("enter role");
        JSONArray ja = new JSONArray();
        String bookLend = "SELECT * FROM resourcecheck; ";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(bookLend);
            while (result.next()) {
                System.out.println("true");
                JSONObject jo = new JSONObject();
                jo.put("role", result.getString("role"));
                ArrayList list=new ArrayList();
                Array cities = result.getArray("resource");
                String[] str_cities = (String[])cities.getArray();
                for (String str : str_cities) 
                    list.add(str);
                jo.put("resources", list);
                ja.put(jo);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }

    //insert role and resource
    public boolean insertRoleToUser(String user,String[] role){
        boolean check = false;
        String query1 = "UPDATE account SET role = ? WHERE email = ?;";
        try {
            Connection con = getConnection();
            PreparedStatement pstmt1 = con.prepareStatement(query1);
            pstmt1.setArray(1, con.createArrayOf("TEXT", role));// array
            pstmt1.setString(2, user);
            int affectedrows1 = pstmt1.executeUpdate();
            pstmt1.close();
            if(affectedrows1 > 0) {
                check=true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //create new role and resource
    public boolean createRoleAndResource(String role,String[] resource){
        boolean check = false;
        String query1 = "INSERT INTO resourcecheck (role,resource) VALUES (?,?);";
        try {
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(query1);
            pstmt.setString(1, role);
            pstmt.setArray(2, con.createArrayOf("TEXT", resource));
            int affectedrows = pstmt.executeUpdate();
            if(affectedrows>0) check=true;
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            check = false;
        }
        return check;
    }

    //get userData
    public JSONObject getUserData(String email){
        JSONObject jo = new JSONObject();
        String query = "SELECT email,fullname,mobilenumber FROM account WHERE email = '"+email+"';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                jo.put("email", result.getString("email"));
                jo.put("name", result.getString("fullname"));
                jo.put("mobile", result.getString("mobilenumber"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }

    //uncomment after testing
    //delete user data
    public boolean deleteAccount(String email,int businessId){
        boolean check = false;
        System.out.println("in delete");
        try {
            Connection con = getConnection();
            // get user id
            String query = "SELECT id FROM useraccount WHERE email = '"+email+"';";
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            if (result.next()) {
                int id = result.getInt("id");
                System.out.println("id => "+id);
                // check business branch
                query = "SELECT id FROM business_user WHERE '"+id+"' = ANY(userids) AND id = '"+businessId+"';";
                result = state.executeQuery(query);
                if (result.next()) {
                    int businessid = result.getInt("id");
                    System.out.println("businessid get=> "+businessid);
                    if (businessid == businessId) {
                        System.out.println("businessid => "+businessid);
                        // delete user
                        query = "DELETE FROM useraccount WHERE id = "+id+";";
                        int affectedrows = state.executeUpdate(query);
                        if (affectedrows > 0) {
                            check = true;
                        }
                        System.out.println("delete user");
                    }
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    // get user data from account with roles only
    public JSONArray getUserDateWithRole(){
        JSONArray ja = new JSONArray();
        String query = "SELECT email,fullname,mobilenumber,role FROM account WHERE role IS NOT NULL;";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                JSONObject jo = new JSONObject();
                jo.put("email", result.getString("email"));
                jo.put("name", result.getString("fullname"));
                jo.put("mobile", result.getString("mobilenumber"));
                // jo.put("role", result.getString("role"));

                ArrayList list=new ArrayList();
                Array cities = result.getArray("role");
                String[] str_cities = (String[])cities.getArray();
                for (String str : str_cities) 
                    list.add(str);
                jo.put("role", list);
                ja.put(jo);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }

    //filter user data with role
    public JSONArray filterUserDataWithRole(String name){
        JSONArray ja = new JSONArray();
        // String sql = " SELECT * FROM librarian WHERE username LIKE '%" + value + "%' ;";
        String query = "SELECT email,fullname,mobilenumber,role FROM account WHERE fullname LIKE '%"+name+"%';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                JSONObject jo = new JSONObject();
                jo.put("email", result.getString("email"));
                jo.put("name", result.getString("fullname"));
                jo.put("mobile", result.getString("mobilenumber"));
                ArrayList list=new ArrayList();
                Array cities = result.getArray("role");
                String[] str_cities = (String[])cities.getArray();
                for (String str : str_cities) 
                    list.add(str);
                jo.put("role", list);
                ja.put(jo);
            }
            con.close();
        // for (int i=0; i<ja.length(); i++) {
        //     JSONObject item = ja.getJSONObject(i);
        //     String name1 = item.getString("name");
        //     String email = item.getString("email");
        //     String mobile = item.getString("mobile");
        //     JSONArray temp = item.getJSONArray("role");
        //     System.out.println("data: " + name1 + " " + email + " " + mobile );
        //     for (int j = 0; j < temp.length(); j++) {
        //     String pet = temp.getString(j);
        //     System.out.println("role: " + pet);
        //     }
        // }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ja;
    }

    //get password from account
    public String getPassword(String email){
        String password = "";
        String query = "SELECT password FROM account WHERE email = '"+email+"';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                password = result.getString("password");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    //get role from resource
    //but now not applicable
    public ArrayList<String> getRole(String email){
        ArrayList<String> list = new ArrayList<String>();
        String query = "SELECT role FROM resourcecheck WHERE email = '"+email+"';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                ArrayList<String> temp = new ArrayList<String>();
                Array cities = result.getArray("role");
                String[] str_cities = (String[])cities.getArray();
                for (String str : str_cities) 
                    temp.add(str);
                list.addAll(temp);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //create user
    public Boolean createUser(String email, String name, String mobile, String password, String business){
        Boolean check = false;
        try {
            Connection con = getConnection();
            String query = "INSERT INTO useraccount (email,password,fullname,mobilenumber) VALUES (?,?,?,?) RETURNING id;";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, mobile);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                System.out.println("id: " + id);
                query = "UPDATE business_user SET userids = array_append(userids,'"+id+"') WHERE businessname = '"+business+"';";
                Statement state = con.createStatement();
                int affectedrows = state.executeUpdate(query);
                System.out.println("affected rows: " + affectedrows);
                if(affectedrows>0) check=true;
            }
            System.out.println("new user created: ");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    // create role and resource
    public Boolean createRole(String role, String[] res,int businessId){
        Boolean check = false;
        String query = "INSERT INTO role (rolename,businessid) VALUES (?,?) RETURNING id;";
        int roleid = 0;
        int resourceid = 0;
        try {
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, role);
            pstmt.setInt(2, businessId);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                roleid = result.getInt("id");
            }
            if(roleid!=0){
                query = "INSERT INTO resources (res) VALUES (?) RETURNING id;";
                pstmt = con.prepareStatement(query);
                pstmt.setArray(1, con.createArrayOf("text", res));
                result = pstmt.executeQuery();
                while (result.next()) {
                    resourceid = result.getInt("id");
                }
                if(resourceid!=0){
                    query = "INSERT INTO role_resource (roleid,resourceid) VALUES (?,?);";
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, roleid);
                    pstmt.setInt(2, resourceid);
                    int affectedrows = pstmt.executeUpdate();
                    if(affectedrows>0) check=true;
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    // get role and resource combine
    public JSONArray roleResourseCombine(int id) {
        System.out.println("enter role");
        JSONArray ja = new JSONArray();
        String query1 = "SELECT role.rolename,resources.res FROM role,resources,role_resource WHERE role_resource.roleid=role.id AND role_resource.resourceid=resources.id AND role.businessid='"+id+"' ;";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query1);
            while (result.next()) {
                JSONObject jo = new JSONObject();
                jo.put("role", result.getString("rolename"));
                ArrayList list=new ArrayList();
                Array cities = result.getArray("res");
                String[] str_cities = (String[])cities.getArray();
                for (String str : str_cities) 
                    list.add(str);
                jo.put("resources", list);
                ja.put(jo);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }

    //get new unrole user list
    public JSONArray newlistOfUnRole(int businessId){
        JSONArray ja = new JSONArray();
        // String query = "SELECT * FROM useraccount u LEFT OUTER JOIN user_role r ON r.userid = u.id;";
        String query = "SELECT userids FROM business_user WHERE id = '"+businessId+"';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            Array useridlist = null;
            while(result.next()){
                useridlist = result.getArray("userids");
            }
            query = "SELECT * FROM useraccount u LEFT OUTER JOIN user_role r ON r.userid = u.id WHERE u.id = ANY('"+useridlist+"');";
            result = state.executeQuery(query);
            while (result.next()) {
                int userid = result.getInt("userid");
                if(userid == 0) {
                    JSONObject jo = new JSONObject();
                    jo.put("email", result.getString("email"));
                    jo.put("fullname", result.getString("fullname"));
                    jo.put("userid", result.getInt("id"));
                    ja.put(jo);
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return ja;
    }

    // set new role and resource
    public Boolean setNewRole(String user,String[] role,int businessId){
        Boolean check = false;
        // String query1 = "SELECT id FROM useraccount WHERE email = '"+user+"';";
        // String query2 = "SELECT id FROM role WHERE rolename = '"+role[0]+"';";
        String query = "INSERT INTO user_role (userid,roleid) VALUES ((SELECT id FROM useraccount WHERE email = '"+user+"'),(SELECT id FROM role WHERE rolename = '"+role[0]+"' AND businessid='"+businessId+"'));";
        int userid = 0;
        int roleid = 0;
        try {
            Connection con = getConnection();
            PreparedStatement pstmt = con.prepareStatement(query);
            int affectedrows = pstmt.executeUpdate();
            if(affectedrows>0) check=true;
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    //get new user 
    public JSONArray newlistOfUser(int businessId,String username){
        JSONArray ja = new JSONArray();
        String query = "SELECT * FROM user_role,useraccount,role WHERE user_role.userid = useraccount.id AND user_role.roleid = role.id AND role.businessid = '"+businessId+"';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                JSONObject jo = new JSONObject();
                if (!(result.getString("email").equals(username))){
                    jo.put("email", result.getString("email"));
                    jo.put("name", result.getString("fullname"));
                    jo.put("mobile", result.getString("mobilenumber"));
                    jo.put("role", result.getString("rolename"));
                    ja.put(jo);
                }

            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return ja;
    }

    //get new user via filter
    public JSONArray newlistOfUserFilter(String filter,int businessId,String username){
        JSONArray ja = new JSONArray();
        String query = "SELECT * FROM user_role,useraccount,role WHERE user_role.userid = useraccount.id AND user_role.roleid = role.id AND useraccount.fullname LIKE '%"+filter+"%' AND role.businessid ='"+businessId+"';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                JSONObject jo = new JSONObject();
                if (!(result.getString("email").equals(username))){
                    jo.put("email", result.getString("email"));
                    jo.put("name", result.getString("fullname"));
                    jo.put("mobile", result.getString("mobilenumber"));
                    jo.put("role", result.getString("rolename"));
                    ja.put(jo);
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    return ja;
    }

    //get role via username and password
    public ArrayList<String> getRole(String username,String password){
        ArrayList<String> list = new ArrayList<String>();
        String query = "SELECT * FROM role WHERE id=(SELECT roleid FROM user_role WHERE userid=(SELECT id FROM useraccount WHERE email='"+username+"' AND password='"+password+"'))";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                list.add(result.getString("rolename"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return list;
    }

    // get resource via role
    public ArrayList<String> getResource(String role,int businessId){
        ArrayList<String> list = new ArrayList<String>();
        String query = "SELECT res FROM resources WHERE id=(SELECT resourceid FROM role_resource WHERE roleid=(SELECT id FROM role WHERE rolename='"+role+"' AND businessid='"+businessId+"' )) ";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                Array resource = result.getArray("res");
                String[] temp = (String[])resource.getArray();
                for (String str : temp) 
                    list.add(str);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return list;
    }

    //get all role
    public ArrayList<String> getAllRole(int businessId){
        ArrayList<String> list = new ArrayList<String>();
        String query = "SELECT * FROM role WHERE businessid = '"+businessId+"';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                list.add(result.getString("rolename"));
            }
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return list;
    }

    // check business name exist
    public Boolean getExtstingBusiness(String business){
        Boolean check = true;
        String query = "SELECT * FROM business_user WHERE businessname='"+business+"';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                check = false;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return check;
    }

    // check email exist
    public Boolean checkEmail(String email){
        Boolean check = true;
        String query = "SELECT * FROM useraccount WHERE email='"+email+"';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while (result.next()) {
                check = false;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return check;
    }

    // create owner
    public Boolean createOwner(String name,String mobile,String user,String pass,String url){
        Boolean check = false;
        String query = "INSERT INTO useraccount (email,password,fullname,mobilenumber) VALUES ('"+user+"','"+pass+"','"+name+"','"+mobile+"') RETURNING id;";
        int userId = 0;
        int businessId = 0;
        int preCheck = 0;

        try {
        Connection con = getConnection();
        Statement state = con.createStatement();
        // insert user and get id
        ResultSet result = state.executeQuery(query);
        while (result.next()) {
            userId = result.getInt("id");
        }
        // insert business and get id
        if(userId!=0){
            query = "INSERT INTO business_user (businessname,userids) VALUES (?,?) RETURNING id;";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1,url);
            Integer[] id = {userId};
            pstmt.setArray(2,con.createArrayOf("INTEGER",id));
            result = pstmt.executeQuery();
            while (result.next()) {
                businessId = result.getInt("id");
            }
            pstmt.close();
        }
        // insert role to user
        if(businessId!=0 && userId!=0){
            // get owner role id
            query = "SELECT id FROM role WHERE rolename = 'owner' AND businessid=0;";
            result = state.executeQuery(query);
            int roleId = -1;
            while (result.next()) {
            roleId = result.getInt("id");
            }
            // insert role to user
            if(roleId!=-1){
            query = "INSERT INTO user_role (userid,roleid) VALUES (?,?);";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1,userId);
            pstmt.setInt(2,roleId);
            int upt = pstmt.executeUpdate();
                if(upt!=0){
                    check = true;
                }
            pstmt.close();
            }
        }
        con.close();
        } catch (Exception e) {
        e.printStackTrace();
        }
        return check;
    }

    // get business id by user id
    public int getBusinessId(String email){
        int businessId = -1;
        String query = "SELECT id FROM useraccount WHERE email='"+email+"';";
        try {
                Connection con = getConnection();
                Statement state = con.createStatement();
                ResultSet result = state.executeQuery(query);
                int userId = 0;
                while (result.next()) {
                userId = result.getInt("id");
                System.out.println("user id - "+userId);
                query = "SELECT id FROM business_user WHERE '"+userId+"'=ANY(userids);";
                result = state.executeQuery(query);
                while (result.next()) {
                    System.out.println("business id - "+result.getInt("id"));
                    businessId = result.getInt("id");
                }
                }
                con.close();    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return businessId;
    }

    // get user details
    public JSONObject getUserProfile(String email){
        JSONObject obj = new JSONObject();
        String query = "SELECT id,fullname,mobilenumber FROM useraccount WHERE email='"+email+"';";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            int id = -1;
            while (result.next()) {
                id = result.getInt("id");
                obj.put("id", result.getInt("id"));
                obj.put("email", email);
                obj.put("fullname", result.getString("fullname"));
                obj.put("mobilenumber", result.getString("mobilenumber"));
            }
            if(id != -1){
                query = "SELECT businessname FROM business_user WHERE '"+id+"'=ANY(userids);";
                result = state.executeQuery(query);
                while (result.next()) {
                    obj.put("businessname", result.getString("businessname"));
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    // get urllist
    public Dictionary<String, String> getUrlList(){
        Dictionary urlDetails = new Hashtable();

        String query = "SELECT * FROM urllist;";
        try {
            Connection con = getConnection();
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery(query);
            while(result.next()){
                urlDetails.put("name",result.getString("name"));
                urlDetails.put("aliaes",result.getString("aliaes"));
                urlDetails.put("app",result.getString("appbase"));
            }
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return urlDetails;
    }


    }

