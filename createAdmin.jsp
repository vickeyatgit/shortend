<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Admin Librarian Management</title>
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
    <h1 class="title">Library Management System</h1>
    <form>
        <div class="card">
            <h1 style="text-align: center;font-family:verdana;">Create Admin</h1>
            <h3 style="font-family:courier;">UserName</h3>
            <input type="text" class="inp" id="username" name="username" placeholder="Create Admin UserName">
            <h3 style="font-family:courier;">Password</h3>
            <input type="password" class="inp" id="password" name="password" placeholder="Create Admin Password">
            <h6 id="mismatch" style="text-align: center;color:red;"></h6>
            <div class="buttom">
                <button class="button" type="button" onclick="createAdmin();"><span>create</span></button>
                <%-- <button class="button" type="submit" id="admin"  name="admin" formaction="login.jsp"><span>Login</span></button> --%>
                <a class="button" id="admin"  name="admin" href="${pageContext.request.contextPath}/login.jsp"><span>Login</span></a>
            </div>
        </div>
        </form>
        <%-- <%out.println(request.getAttribute("message"));%> --%>
</body>
<script>
function createAdmin(){
  var username = $('#username').val();
  var password = $('#password').val();
  console.log(username)
  console.log(password)
  if(username!=null && password != null){
    console.log("entered")
  $.ajax({
    type:'POST',
    url:'Createadmin',
    data:{
      username:username,
      password:password,
    },
    success:function(result){
      var msg=result[0];
      if(msg =="s"){
        window.alert("Successfully Created");
        $('#mismatch').html("");
        $(':input').val('');
      }else if(msg =="f"){
        window.alert("please use some other UserName ");
        $('#mismatch').html("please use some other UserName");
      }else{
        $('#mismatch').html("please use some other UserName");
      }
    }
  })
  }else{
    $('#mismatch').html("please Enter UserName And Password");
  }
}
</script>
</html>