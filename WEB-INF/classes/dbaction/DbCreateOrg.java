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
  String url = "jdbc:postgresql://localhost:5432/firm", userName = "postgres", password = "hello123";

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
  public Boolean CreateBase(String name,String org,String mobile,String user,String pass,String url) {
    Connection con = getConnection();
    Boolean result = false;

    String query = "INSERT INTO organization(fullname,institution,mobile,email,password,business) VALUES(?,?,?,?,?,?)";
    String query2 = "INSERT INTO orglist(url,dbname) VALUES(?,?)";
    try {
      PreparedStatement pst = con.prepareStatement(query);
      pst.setString(1, name);
      pst.setString(2, org);
      pst.setString(3, mobile);
      pst.setString(4, user);
      pst.setString(5, pass);
      pst.setString(6, url);
      int suc = pst.executeUpdate();
      if(suc == 1) {
        pst = con.prepareStatement(query2);
        pst.setString(1, url);
        pst.setString(2, user+"_"+org);
        int suc2 = pst.executeUpdate();
        if(suc2 == 1) {
          result = true;
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  // create all tables and insert data
  public Boolean CreateOrgTables(String dbName){
    //create tables
    // create db
    String createDb = "CREATE DATABASE "+dbName+";";
    // create table for login and user details
    String createRoleResource = "CREATE TABLE role_resource (roleid integer NOT NULL,resourceid integer NOT NULL);";
    String createUserRole = "CREATE TABLE user_role (userid integer NOT NULL,roleid integer NOT NULL);";
    String createResource = "CREATE TABLE resources (id serial PRIMARY KEY,res TEXT[] NOT NULL);";
    String createRole = "CREATE TABLE role (id serial PRIMARY KEY,rolename VARCHAR(100) NOT NULL UNIQUE);";
    String createUserAccount = "CREATE TABLE useraccount(id serial PRIMARY KEY ,email VARCHAR(100) NOT NULL UNIQUE,password VARCHAR(100) NOT NULL,fullname VARCHAR(100) NOT NULL,mobilenumber VARCHAR(100) NOT NULL,ondate TIMESTAMP NOT NULL DEFAULT now());";

    // book borrow table
    String bookLend = "CREATE TABLE booklend(id serial PRIMARY KEY,readerid int NOT NULL,bookid int NOT NULL,ondate TIMESTAMP NOT NULL DEFAULT now());";

    // readers details
    String createReader = "CREATE TABLE reader(id serial PRIMARY KEY,username VARCHAR(100) NOT NULL,name VARCHAR(50) NOT NULL);";

    // add book details
    String bookUnify = "CREATE TABLE bookunify(id serial PRIMARY KEY,bookid INT NOT NULL,bookaddup INT NOT NULL);";
    String bookRack = "CREATE TABLE bookrack(id serial PRIMARY KEY,title VARCHAR(100) NOT NULL,subtitle VARCHAR(100),author TEXT[] NOT NULL,language VARCHAR(100) NOT NULL,category VARCHAR(100) NOT NULL,publish date NOT NULL,genre TEXT[] NOT NULL,edition VARCHAR(50),intake date NOT NULL,available boolean NOT NULL,bookunify integer NOT NULL);";
    
    //send mail later
    String sendMail = "CREATE TABLE sendmail(id serial PRIMARY KEY,mailfrom VARCHAR(255) NOT NULL,mailto VARCHAR(255) NOT NULL,subject VARCHAR(255),body VARCHAR(255),attachment VARCHAR(50),type VARCHAR(10),createdtime TIMESTAMP DEFAULT now(),intervalminutes integer,ondate TIMESTAMP );";
    
    return false;
  
  }

  // search for business name
  public Boolean getExtstingBusiness(String name){
    Boolean result = true;
    String query = "SELECT id FROM organization WHERE business = '"+name+"'";
    try {
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      if(rs.next()) {
        result = false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  //check for email not exist
  public Boolean checkOrgEmail(String email){
    Boolean result = true;
    String query = "SELECT id FROM organization WHERE email = '"+email+"'";
    try {
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      if(rs.next()) {
        result = false;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  // check url in orglist
  public Boolean checkOrgUrl(String url){
    Boolean result = false;
    String query = "SELECT id FROM orglist WHERE url = '"+url+"'";
    try {
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      if(rs.next()) {
        result = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

}