<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Library Management</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<style>
div.container {
    display: flex;
    flex-direction: column;
}
h1.title {
    text-align: center;
    /* background-color: aqua; */
    width: 100%;
    font-family:verdana;
    color:#FF6F61;
}
div.card {
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
input[type=password]:focus {
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
    font-size: medium;
}
div.buttom{
    display: flex;
    flex-direction: row;
    margin-top: 30px;
    justify-content: space-around;
}
.button {
  border-radius: 4px;
  background-color: #f4511e;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 15px;
  padding: 5px;
  width: 200px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
}

.button span {
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.button span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.button:hover span {
  padding-right: 25px;
}

.button:hover span:after {
  opacity: 1;
  right: 0;
}
</style>
<body>
<div class="container">
        <h1 class="title">Library Management System</h1>
        <%-- action="login.jsp" --%>
        <!-- <form  method="POST">
        <div class="card">
            <h1 style="text-align: center;font-family:verdana;">Login</h1>
            <h3 style="font-family:courier;">UserName</h3>
            <input type="text" class="inp" id="username" name="username">
            <h3 style="font-family:courier;">Password</h3>
            <input type="password" class="inp" id="password" name="password">
            <h5 id="res" style="text-align: center;color:red;"></h5>
            <div class="buttom">
                <p>hello world ass</p>
                <button id="adminpanel" class="button" onclick="login()"><span>Ass Admin </span></button>
                <button class="button" type="button" onclick="liblogin()"><span>As Librarian </span></button>
            </div>
            <%-- <a href="http://localhost:8080/shortend/createAdmin.jsp" style="text-align:center;margin-top:30px;">Create Admin</a> --%>
            <a href="${pageContext.request.contextPath}/createAdmin.jsp" style="text-align:center;margin-top:30px;">Create Admin</a>
            <a href="http://localhost:8080/shortend/ReadersData" style="text-align:center;margin-top:30px;">Create Admin</a>
        </div>
        </form> -->
        <!-- action="j_security_check" -->
        <form method=post >
          <div>
            <span>Username:</span>
            <br />
            <input type="text" name="j_username" id="j_username">
          </div>
          <div>
            <span>Password:</span>
            <br />
            <input type="password" name="j_password" id="j_password">
          </div>
          <!-- <input type="submit" value="Login"> -->
          <p>hello world ass</p>
        </form>
        
    </div>
</body>
<script>
  $(document).ready(function () {
      $("p").click(function () {
        // alert("The paragraph was clicked.");
        console.log("hello");
        var username = $('#j_username').val();
        var password = $('#j_password').val();
        console.log(username);
        console.log(password);

        $.ajax({
          url: 'http://localhost:8080/shortend/j_security_check',
          type: 'POST',
          data: {
            j_username:username,
            j_password:password
          },
          timeout: 30000,
          async: false,
          success: function (data) {
            console.log("////////////////////////")
            console.log(data);
            console.log("////////////////////////")
            // if(data.status == "success"){
            //   window.location.href = "http://localhost:8080/shortend/admin.jsp";
            // }
            // else{
            //   $('#res').text("Invalid Username or Password");
            // }
          },
        });
        })
      });
  var msg = null;
function login(){
  
  console.log("login");
  var username = $('#username').val();
  var password = $('#password').val();
  window.location.replace('www.google.com')
  // console.log(username);
  // console.log("hello");
  // if(username!=null && password != null){
  // $.ajax({
  //   type:'POST',
  //   url:'Adminlogin',
  //   xhrFields: {
  //     withCredentials: true,
  //   },
  //   data:{
  //       username:username,
  //       password:password
  //     },
  //   success:function(result){
  //     console.log("hello");
  //     console.log(result);
  //     // var msg=result[0];
  //     // console.log(result);
  //     // if(msg=="s"){
  //       // window.alert("suc now go on");
  //       // window.location.replace('ReadersData')
  //       // window.alert(location.hostname);
  //     // }
  //     // if(msg=="f"){
  //     //   window.alert("please check username and password");
  //     //   $('#res').html("please check username and password");
  //     // }
  //     // else{
  //     //   $('#res').html("something went wrong");
  //     // }
  //   }
  // });
  // }
}

function liblogin(){
  var username = $('#username').val();
  var password = $('#password').val();
// if(username!=null && password != null){
//   $.ajax({
//     type:'POST',
//     url:'LibrarianLogin',
//     data:{
//         username:username,
//         password:password
//       },
//     success:function(result){
//       var msg=result[0];
//       console.log(result);
//       if(msg=="s"){
//         window.location.replace('librarianPanel.jsp')
//         // window.alert(location.hostname);
//       }
//       if(msg=="f"){
//         window.alert("please check username and password");
//         $('#res').html("please check username and password");
//       }
//       // else{
//       //   $('#res').html("something went wrong");
//       // }
//     }
//   });
//   }
}


</script>
</html>