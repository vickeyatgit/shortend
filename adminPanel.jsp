<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "org.json.*" %>

<%ArrayList<ArrayList<String>> graph = new ArrayList<>();
if(session.getAttribute("ckecklist")!=null){graph=(ArrayList)session.getAttribute("ckecklist");}
JSONArray ja = new JSONArray();
if(session.getAttribute("newchecklist")!=null){
ja = (JSONArray) session.getAttribute("newchecklist");
}
%>

<%-- if(${ckecklist}!=null){
  if(%>${ckecklist}<%!=null){import org.json.*; 
  graph = (ArrayList)  %>${ckecklist}<% ;
}
  graph=(ArrayList)${ckecklist};
  } --%>

<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<title>Admin Panel</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>


<script>
$(document).ready(function(){
  $.ajax({
    type:'GET',
    url:'Adminlogin',
    success:function(result){
      console.log("===========================");
      var data="<tr><th>S.NO.</th><th>Username</th><th>First Name</th><th>Last Name</th><th>Email Id</th><th>Mobile Number</th><th>Books Tally</th></tr>";
      var parsedJSON = JSON.parse(result);
      if(parsedJSON.length > 0){

      for (var i=0;i<parsedJSON.length;i++) {
        // console.log(parsedJSON[i]);
        data+='<tr><td style="text-align: center;"><h6>'+(i+1)+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Username+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].FirstName+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].LastName+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Email+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Mobile+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Books+'</h6></td>';
        data+='<th style="background-color: coral;cursor: pointer;" onclick="onDelete(\'' + parsedJSON[i].Username + '\');"><i class="far fa-trash-alt" style="color:white"></i></th></tr>';
      }
      $("#tablelibdata").html(data);
      }else{
        $("#tablelibdata").html("<h3>No Record Found</h3>");
      }
    }
  });
});
</script>
<body>

<% 
if(session.getAttribute("username")==null) {
  response.sendRedirect("login.jsp");
}
%>
<div class="container">
    <ul>
            <li><a class="active" onclick="openCity(event,'view')" style="cursor:Pointer;" >View Librarian</a></li>
            <li><a onclick="openCity(event,'add')" style="cursor:Pointer;">Add Librarian</a></li>
            <li><a onclick="openCity(event,'del')" style="cursor:Pointer;">Delete Librarian</a></li>
            <li style="float:right">
            <form action="Logout">
            <%-- <a style="cursor:Pointer;" action="Logout"type="submit" >Sign out</a> --%>
            <button  class="out" onclick="logout();" type="button" ><span>Sign out</span></button>
            </form>
            </li>
    </ul>
    <div class="city" id="view" style="display:block">
        <h3 style="text-align: center;font-family:verdana;">View Librarian</h3>
        <div style="display: flex;flex-direction: column;margin: auto;width: 60%;">
        <input type="text" onchange="getFilter()" class="inp" id="filterusername" name="filterusername" placeholder="Enter Username ..." style="margin-bottom: 25px;"/>
        <%-- <%
        if(ja.length()<=0){
            %><h5 style="text-align: center;font-family:verdana;">No Record Found</h5><%
        }
        else{
            %> --%>
        <table>
            <div id="tablelibdata"></div>
            <%-- <%
            for(int count=0;count<ja.length();count++){
              JSONObject temp = ja.getJSONObject(count);
                %> --%>
                <%-- <tr> --%>
                    <%-- <td style="text-align: center;"><%out.print(count+1);%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(temp.optString("Username"));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(temp.optString("FirstName"));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(temp.optString("LastName"));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(temp.optString("Email"));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(temp.optString("Mobile"));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(temp.optString("Books"));%></td> --%>
                    <%-- <th style="background-color: coral;cursor: pointer;" onclick='onDelete("<%out.print(temp.optString("Username"));%>");'><i class="far fa-trash-alt" style="color:white"></i></th> --%>
                    
                    <%-- <td style="text-align: center;"><%out.print(graph.get(count).get(0));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(graph.get(count).get(0));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(graph.get(count).get(1));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(graph.get(count).get(2));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(graph.get(count).get(3));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(graph.get(count).get(4));%></td> --%>
                    <%-- <td style="text-align: center;"><%out.print(graph.get(count).get(5));%></td> --%>
                <%-- </tr> --%>
                <%-- <%
            }
        }
        %> --%>
        </table>
        </div>
    </div>

    <div class="city" id="add" style="display:none">
    <form >
    <div class="addLibrarian">
            <h3 style="text-align: center;font-family:verdana;">Add Librarian</h1>
            <h4 style="font-family:courier;">UserName</h4>
            <input type="text" class="inp" id="alusername" name="alusername" placeholder="Enter UserName">
            <h4 style="font-family:courier;">First Name</h3>
            <input type="text" class="inp" id="alfirstname" name="alfirstname" placeholder="Enter First Name">
            <h4 style="font-family:courier;">Last Name</h3>
            <input type="text" class="inp" id="allasttname" name="allasttname" placeholder="Enter First Name">
            <h4 style="font-family:courier;">Mobile Number</h4>
            <input type="number" class="inp" id="almobile" name="almobile" placeholder="Eg 23678923">
            <h4 style="font-family:courier;">Email Id</h4>
            <input type="email" class="inp" id="alemail" name="alemail" placeholder="Eg hello@gmail.com">
            <h4 style="font-family:courier;">password</h4>
            <input type="password" class="inp" id="alpassword" name="alpassword" placeholder="**********">
            <h4 style="font-family:courier;">Conform password</h4>
            <input type="password" class="inp" id="alrepass" name="alrepass" placeholder="**********">
            <p id="checkpass" style="color: red;"></p>
            <h6 id="mismatch" style="text-align: center;color:red;"></h6>
            <button class="button button2" style="margin-top: 30px;margin-bottom:30px;border-radius: 10px;" 
            onclick="insertLib();" type="button"
            >Submit</button>
        </div>
      </form>
    </div>

    <div class="city" id="del" style="display:none">
        <form  method="POST">
            <div class="addLibrarian">
            <h3 style="text-align: center;font-family:verdana;">Delete Librarian</h3>
            <h4 style="font-family:courier;">UserName</h4>
            <input type="text" class="inp" id="delusername" name="delusername" placeholder="Enter UserName">
            <h6 id="nofound" style="text-align: center;color:red;"></h6>
            <button class="bouncy" type="button" onclick="deleteLib();"><span>Delete</span></button>
            </div>
        </form>
    </div>

</div>

<%-- <script src="lib/jquery-3.4.1.min.js" type="text/javascript"></script>
<script src="lib/jquery.min.js" type="text/javascript"></script>
<script src="lib/jquery.js" type="text/javascript"></script> --%>
<%-- <h1><% out.print(ckecklist.size()); %></h1> --%>

<script>

// function getFilter(){
//   console.log("hello");
//   var val = $('#filterusername').val();
//   val = val.trim();
//   if(val.length > 0){
//     console.log("pass condition");
//     $.ajax({
//     type:'GET',
//     url:'AdminLibInsert',
//     data:{
//       val:val,
//     },
//     success:function(result){
//       console.log("+++++++++++")
//       var parsedJSON = JSON.parse(result);
//       if(parsedJSON.length > 0){
//         console.log("same");
//         var data="<tr><th>S.NO.</th><th>Username</th><th>First Name</th><th>Last Name</th><th>Email Id</th><th>Mobile Number</th><th>Books Tally</th></tr>";
//         for (var i=0;i<parsedJSON.length;i++) {
//           data+='<tr><td style="text-align: center;"><h6>'+(i+1)+'</h6></td>';
//           data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Username+'</h6></td>';
//           data+='<td style="text-align: center;"><h6>'+parsedJSON[i].FirstName+'</h6></td>';
//           data+='<td style="text-align: center;"><h6>'+parsedJSON[i].LastName+'</h6></td>';
//           data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Email+'</h6></td>';
//           data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Mobile+'</h6></td>';
//           data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Books+'</h6></td></tr>';
//         }
//         console.log(data);
//         $("#tablelibdata").html(data);
//       } 
//     }
//     });
//   }
//   else{
//     console.log("else")
//     $.ajax({
//     type:'GET',
//     url:'Adminlogin',
//     success:function(result){
//       console.log("===========================");
//       var data="<tr><th>S.NO.</th><th>Username</th><th>First Name</th><th>Last Name</th><th>Email Id</th><th>Mobile Number</th><th>Books Tally</th></tr>";
//       var parsedJSON = JSON.parse(result);
//       for (var i=0;i<parsedJSON.length;i++) {
//         data+='<tr><td style="text-align: center;"><h6>'+(i+1)+'</h6></td>';
//         data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Username+'</h6></td>';
//         data+='<td style="text-align: center;"><h6>'+parsedJSON[i].FirstName+'</h6></td>';
//         data+='<td style="text-align: center;"><h6>'+parsedJSON[i].LastName+'</h6></td>';
//         data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Email+'</h6></td>';
//         data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Mobile+'</h6></td>';
//         data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Books+'</h6></td></tr>';
//       }
//       $("#tablelibdata").html(data);
//     }
//   })
//   }
// }

$("#filterusername").on("keyup", function() {
  var value = $('#filterusername').val();
  value = value.trim();
  console.log(value);
  if(value.length > 0){
    console.log("if");
    $.ajax({
      type:'GET',
      url:'AdminLibInsert',
      data:{
        val:value,
      },
      success:function(result){
        console.log(result);
        var parsedJSON = JSON.parse(result);
        if(parsedJSON.length > 0){
          console.log("same");
          var data="<tr><th>S.NO.</th><th>Username</th><th>First Name</th><th>Last Name</th><th>Email Id</th><th>Mobile Number</th><th>Books Tally</th></tr>";
          for (var i=0;i<parsedJSON.length;i++) {
            data+='<tr><td style="text-align: center;"><h6>'+(i+1)+'</h6></td>';
            data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Username+'</h6></td>';
            data+='<td style="text-align: center;"><h6>'+parsedJSON[i].FirstName+'</h6></td>';
            data+='<td style="text-align: center;"><h6>'+parsedJSON[i].LastName+'</h6></td>';
            data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Email+'</h6></td>';
            data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Mobile+'</h6></td>';
            data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Books+'</h6></td>';
            data+='<th style="background-color: coral;cursor: pointer;" onclick="onDelete(\'' + parsedJSON[i].Username + '\');"><i class="far fa-trash-alt" style="color:white"></i></th></tr>';
          }
          console.log(data);
          $("#tablelibdata").html(data);
        }else{
          $("#tablelibdata").html("<h3>No Record Found</h3>");
        } 
      }
    });
  }
  else{
    console.log("else");
    console.log("else")
    $.ajax({
    type:'GET',
    url:'Adminlogin',
    success:function(result){
      console.log("===========================");

      var data="<tr><th>S.NO.</th><th>Username</th><th>First Name</th><th>Last Name</th><th>Email Id</th><th>Mobile Number</th><th>Books Tally</th></tr>";
      var parsedJSON = JSON.parse(result);
      if(parsedJSON.length > 0){
      for (var i=0;i<parsedJSON.length;i++) {
        data+='<tr><td style="text-align: center;"><h6>'+(i+1)+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Username+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].FirstName+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].LastName+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Email+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Mobile+'</h6></td>';
        data+='<td style="text-align: center;"><h6>'+parsedJSON[i].Books+'</h6></td>';
        data+='<th style="background-color: coral;cursor: pointer;" onclick="onDelete(\'' + parsedJSON[i].Username + '\');" ><i class="far fa-trash-alt" style="color:white"></i></th></tr>';
      }
      $("#tablelibdata").html(data);
      }else{
        $("#tablelibdata").html("<h3>No Record Found</h3>");
      }
    }
  })
  }

});

// $("#filterusername").on("keyup", function() {
//   var value = $(this).val();
//   console.log("hello")
//   $("table tr").each(function(index) {
//     if (index !== 0) {
//       $row = $(this);
//       var id = $row.find("td:nth-child(2)").text(); //username $.trim(str)
//       var id1 = $row.find("td:nth-child(3)").text(); //firstname
//       var id2 = $row.find("td:nth-child(4)").text(); //lastname
//       var id3 = $row.find("td:nth-child(5)").text(); //email
//       var id4 = $row.find("td:nth-child(6)").text(); //mobile
//       var id5 = $row.find("td:nth-child(7)").text(); // books
//       if (id.indexOf(value) !== 0 && 
//           id1.indexOf(value) !== 0 && 
//           id2.indexOf(value) !== 0 && 
//           id3.indexOf(value) !== 0 &&
//           id4.indexOf(value) !== 0 && 
//           id5.indexOf(value) !== 0  ) {
//         $row.hide();
//       } else {
//         $row.show();
//       }
//     }
//   });
// });

function checkpass(){
  var password = $('#alpassword').val();
  var repassword = $('#alrepass').val();
  if(password!=repassword){
    $("#checkpass").html("Passwords does not match!");
  }else{
    $("#checkpass").html("");
  }
}
async function RefreshTable() {
  await new Promise(resolve => setTimeout(resolve, 3000)); 
    $( "#view" ).load( "adminPanel.jsp #view" );
  }
$(document).ready(function () {
  $("#alrepass").keyup(checkpass);
  // $("#refresh-btn").on("click", RefreshTable);
  
});
function insertLib(){
  // formaction="AdminInsert"
  var username = $('#alusername').val();
  username=username.replace(/ +/g, "");
  console.log(username,"1");
  var firstname = $('#alfirstname').val();
  var lastname = $('#allasttname').val();
  var mobile = $('#almobile').val();
  var email = $('#alemail').val();
  var password = $('#alpassword').val();
  var repassword = $('#alrepass').val();
  console.log("in click");

  if(password==repassword){
    console.log("check pass");
    $.ajax({
      type:'POST',
      url:'AdminLibInsert',
      data:{
        username:username,
        firstname:firstname,
        lastname:lastname,
        mobile:mobile,
        email:email,
        password:password,
      },
    success:function(result){
      var msg=result[0];
      if(msg=="s"){
        window.alert("Successfully Added");
        $('#mismatch').html("");
        $(':input').val('');
        window.location.reload();
      }
      else if(msg =="f"){
        window.alert("please use some other UserName ");
        $('#mismatch').html("please use some other UserName ");
        $('#alusername').val('');
      }
      else{
        window.alert("something");
      }
    }
    });
  }
  else{
    // document.getElementById("mismatch").value = "password mismatch";
  }
}

function deleteLib(){
  var username = $('#delusername').val();
  $.ajax({
    type:'POST',
      url:'AdminLibDelete',
      data:{
        username:username,
      },
    success:function(result){
      var msg=result[0];
      if(msg=="s"){
        window.alert("Successfully Deleted");
        $('#nofound').html("");
        $(':input').val('');
        window.location.reload();
      }
      else if(msg =="f"){
        window.alert("User Couldn't Found!");
        $('#nofound').html("User Couldn't Found!");
      }
    }
  });
}

function logout(){
$.ajax({
      type:'POST',
      url:'AdminLogout',
    success:function(result){
      window.location.replace('');
    }
    })

}

function onDelete(user){
  console.log(user);
  var username = $('#delusername').val();
  $.ajax({
    type:'POST',
      url:'AdminLibDelete',
      data:{
        username:user,
      },
    success:function(result){
      var msg=result[0];
      if(msg=="s"){
        window.alert("Successfully Deleted");
        $('#nofound').html("");
        $(':input').val('');
        window.location.reload();
        // RefreshTable();
      }
      else if(msg =="f"){
        window.alert("User Couldn't Found!");
        $('#nofound').html("User Couldn't Found!");
      }
    }
  });
}


</script>

</body>

<script>
function openCity(evt, org) {
  var i, x, tablinks;
  x = document.getElementsByClassName("city");
  for (i = 0; i < x.length; i++) {
    x[i].style.display = "none";
  }
  document.getElementById(org).style.display = "block";
}

</script>
<style>
div.container {
    display: flex;
    flex-direction: column;
}
div.header{
    display: flex;
    flex-direction: row;
    background-color: blue;
}
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #333;
}
li {
  float: left;
  border-right:1px solid #bbb;
}

li:last-child {
  border-right: none;
}

li a {
  display: block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

li a:hover:not(.active) {
  background-color: #111;
}
div.addLibrarian {
    display: flex;
    flex-direction: column;
    margin: auto;
    width: 30%;
    /* height: 200px; */
    /* width: 400px; */
    background-color:white;
}
input[type=text]:focus {
  box-shadow: 2px 2px 3px #007bff;
}
input[type=email]:focus {
     box-shadow: 2px 2px 3px #007bff;
}
input[type=password]:focus {
     box-shadow: 2px 2px 3px #007bff;
}
input[type=number]:focus {
     box-shadow: 2px 2px 3px #007bff;
}

input.inp {
    outline: 0;
    padding: 10px;
    margin-left: 10px;
    margin-right: 10px;
    border-radius: 10px;
    border: none;
    border:solid 1px #ccc;
    box-shadow: 2px 2px 3px #666;
    font-size: 10px;
}
button.button {
    background-color:#008CBA; /* #4CAF50; Green */
    border: none;
    color: white;
    padding: 16px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    transition-duration: 0.4s;
    cursor: pointer;
}

/* .button2 {
  background-color: white; 
  color: black; 
  border: 2px solid #008CBA;
} */
.button2:hover {
  background-color: white;
  color: #008CBA;
  border: 2px solid #008CBA;
}

.bouncy{
  margin-Top:50px;
  width:90px;
  margin-Left:80%;
  background-color: #f44336;
  font-size: 12px;
  padding:5px;
  color:white;
  border-radius: 10px;
  cursor: pointer;
  text-align: center;
  border: none;
}
table, th, td {
  border: 1px solid white;
  border-collapse: collapse;
  border-collapse: separate;
  border-spacing: 25px;
}
th, td {
  background-color: #96D4D4;
  border-radius: 10px;
  padding-right: 5px;
  padding-left: 5px;
  margin:5px;
}
button.out {
  background-color:#333333;
  border-width: 0;
  color:red;
  margin-top:10px;
  cursor:Pointer;
  margin-right:20px;
}

</style>
</html>