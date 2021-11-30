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
import java.util.Random;


public class DbCreateOrg {
  String url = "jdbc:postgresql://localhost:5432/institution", userName = "postgres", password = "hello123";

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

  // create new organization
  public Boolean CreateBase(String first,String last,String org,String mobile,String user,String pass) {
    Connection con = getConnection();
    Boolean result = false;

    String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
    // String user = "sdert";
    char[] ch = user.toCharArray();
    String Db = "";
    for (int i = 0; i < ch.length; i++) {
      int hu = (random.nextInt(36)+ch[i])%36;
      String Db = Db + chars.charAt(hu);
    }
    
    // try {
    //   String sql = "INSERT INTO organization(first,last,org,mobile,user,pass) VALUES(?,?,?,?,?,?)";
    //   PreparedStatement pst = con.prepareStatement(sql);
    //   pst.setString(1, first);
    //   pst.setString(2, last);
    //   pst.setString(3, org);
    //   pst.setString(4, mobile);
    //   pst.setString(5, user);
    //   pst.setString(6, pass);
    //   pst.executeUpdate();
    //   System.out.println("Created new organization successfully");
    //   return true;
    // } catch (Exception e) {
    //   e.printStackTrace();
    //   return false;
    // } 
    return result;
  }

}