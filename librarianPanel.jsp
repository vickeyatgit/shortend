<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "org.json.*" %>

<%Dictionary geek = new Hashtable();%>

<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Luckiest+Guy&display=swap" rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Balsamiq+Sans&display=swap" rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Nunito&display=swap" rel="stylesheet">
<title>Librarian Panel</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
.head{
    padding-top: 15px;
}
.container { 
    height: 200px;
    position: relative;
}

.center {
    margin: 0;
    position: absolute;
    top: 20%;
    left: 50%;
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
}
.quot{
    background: -webkit-linear-gradient(#2ebf91, #8360c3);
  -webkit-background-clip: text;
  cursor: pointer;
  -webkit-text-fill-color: transparent;
  font-family: 'Luckiest Guy', cursive;
}
#ccaarrdd{
    margin:5;
    cursor:pointer;
}
</style>
<script>
$(document).ready(function(){
$.ajax({
    type:'GET',
    url:'LibrarianLogin',
    data:{
        user:"<%out.print(session.getAttribute("librarian"));%>",
    },
    success:function(result){
        console.log("------------------",result);
        
        if(result.username!=null)   $('#username').html(result.username);
        if(result.firstname!=null)  $('#fullname').html(result.firstname);
        if(result.lastname!=null)   $('#lastname').html(result.lastname);
        if(result.mobile!=null) $('#mob').html(result.mobile);
        if(result.email!=null)  $('#email').html(result.email);
        if(result.tally!=null)  $('#mismatch').html(result.tally);
        console.log("fini");
    }
});
onGetDataforLib();
});
</script>
<style>
.sha{
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
input[type=text]:focus {
    box-shadow: 2px 2px 3px #007bff;
}
input.inp {
    outline: 0;
    padding: 8px;
    /* margin-left: 10px; */
    /* margin-right: 10px; */
    border-radius: 10px;
    border: none;
    border:solid 1px #ccc;
    box-shadow: 2px 2px 3px #666;
    font-size: medium;
}
.blockarrow:: -webkit-inner-spin-button, .no-spin::-webkit-outer-spin-button{
    /* -webkit-appearance: none !important;
    margin: 0 !important; */
    background-color:powderblue;
}
/* width */
::-webkit-scrollbar {
  width: 8px;
}

/* Track */
::-webkit-scrollbar-track {
  background: white; 
}
 
/* Handle */
::-webkit-scrollbar-thumb {
  background: #888; 
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #555; 
}
.borderless td, .borderless th {
    border: none;
}
</style>
<body>
<% 
if(session.getAttribute("librarian")==null) {
    response.sendRedirect("login.jsp");
}
%>
<!-- Option 1: Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>

    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
    <a class="navbar-brand" href="#">Library Management</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link text-light"  onclick="onPressNav(event,'dash')" style="cursor:Pointer;" >Dashboard</a>
            </li>
            <li class="nav-item">
                <a class="nav-link"  onclick="onPressNav(event,'add')" style="cursor:Pointer;" >Contribute</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" onclick="onPressNav(event,'view')" style="cursor:Pointer;">Books</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" onclick="onPressNav(event,'addreader')" style="cursor:Pointer;">Add Reader</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" onclick="onPressNav(event,'lending')" style="cursor:Pointer;">Lendings</a>
            </li>
        </ul>      
    </div>
    <span class="navbar-text text-danger">
    <button type="button" class="btn btn-outline-danger btn-sm" onclick="logout();">Logout</button>
    </span>
    </div>
    </nav>

    <div class="books" id="dash" style="display:block">
        <div class="row">
            <div class="col-9">
                <div class="card" style="cursor: wait;margin:10px;width: 30rem;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                    <div class="card-body">
                        <div id="chart_div"></div>
                    </div>
                </div>
            </div>
            <div class="col-3">
                <div class="card" style="cursor: wait;margin:10px;width: 18rem;box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
                    <div class="card-body">
                    <div class="row">
                        <div class="col-2">
                        <img src="https://www.desktopbackground.org/download/o/2011/09/29/273267_ninja-wallpapers-wallpapers-cave_1024x576_h.png" 
                        width="40" height="40" 
                        style="margin-top:10px;border-radius: 10px;"
                        class="d-inline-block align-top" alt="">
                        </div>
                        <div class="col-10">
                            <h5 class="card-title" id="username" style="margin-left: 10px;"></h5>
                            <h6 class="card-subtitle mb-2 text-muted"><span id="fullname" style="margin-left: 10px;"></span> <span id="lastname"></span></h6>
                        </div>
                    </div>
                        
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item" style="text-align:center;" id="mob"></li>
                        <li class="list-group-item" style="text-align:center;"><a id="email" class="card-link"></a></li>
                        
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="books" id="add" style="display:none;">
        <div style="background-color:white;">
            <h3 style="margin:20px;"> Upload a Book</h3>
            <div class="row" >
                <div class="col-sm-8" style="padding-left: 40px;">
                <div class="row">
                <div class="col-md-6">
                    <label for="inputEmail4" class="form-label head">Title</label>
                    <input type="text" class="form-control" id="title" placeholder="Enter Book Title">
                </div>
                <div class="col-md-6">
                    <label for="inputPassword4" class="form-label head">Sub Title</label>
                    <input type="text" class="form-control" id="sub" placeholder="Enter Book Sub-Title">
                </div>
                <div class="col-md-12">
                    <label for="inputPassword4" class="form-label head">Author</label>
                    <input type="text" class="form-control" id="auth" placeholder="Enter Book Author"> 
                </div>
                <div class="col-md-6">
                    <label for="inputPassword4" class="form-label head">Category</label>
                    <input type="text" class="form-control" id="cat" placeholder="Eg Reference,Cooking,Biographies . . .">
                    <%-- <select name="cars" id="cars" class="form-control">
                    <option value="volvo">Volvo</option>
                    <option value="saab">Saab</option>
                    <option value="mercedes">Mercedes</option>
                    <option value="audi">Audi</option>
                    </select> --%>
                </div>
                <div class="col-md-6">
                    <label for="inputPassword4" class="form-label head">Language</label>
                    <input type="text" class="form-control" id="lag" placeholder="Eg English . . .">
                </div>
                <div class="col-md-3">
                    <label for="inputPassword4" class="form-label head">Published on</label>
                    <input type="date" class="form-control" id="pub">
                </div>
                <div class="col-md-2">
                    <label for="inputPassword4" class="form-label head">Edition</label>
                    <input type="number" class="form-control" id="edit" placeholder="Eg 7th">
                </div>
                <div class="col-md-2">
                    <label for="inputPassword4" class="form-label head">Sum up</label>
                    <input type="number" class="form-control" id="sum" placeholder="no of books">
                </div>
                <div class="col-md-5">
                    <label for="inputPassword4" class="form-label head">Genre</label>
                    <input type="text" class="form-control" id="gen" placeholder="Eg Crime, Action, Education . . .">
                </div>
                <div class="container">
                    <div class="center">
                        <button 
                        type="button" 
                        class="btn btn-info" 
                        style="color:white;padding-right: 50px;padding-left: 50px;" 
                        onclick="addbook();">Contribute</button>
                    </div>
                </div>
                

                </div>
                
                </div>
                <%-- side image --%>
                <div class="col-sm-4">
                <h1 class="quot">A book is a dream that you hold in your hand.</h1>
                <img 
                src="https://unblast.com/wp-content/uploads/2020/04/Man-Reading-a-Book-Vector-Illustration-1-1536x1152.jpg" 
                class="img-fluid" alt="book" style="padding-top:10%">
                </div>

            </div>
        </div>
    </div>

    <div class="books" id="view" style="display:none">
    <%-- <h1>A book is a dream that you hold in your hand.</h1> --%>
    <%-- modal --%>
    <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
            <div class="modal-header border-0">
                <h5 class="modal-title" id="exampleModalLabel"><b>Are you Sure ?</b></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body border-0">
            <h5 style="font-family: Balsamiq Sans">Book Count </h5>
            <input style="margin-top: 5px;" value="1" type="number" id="bookondelete" class="form-control" placeholder="No of Books" >
            
            
            </div>
            <div class="modal-footer border-0" style="border-color:white">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">NO</button>
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal" onclick="onBookRemove();">Remove it</button>
            </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="givebook" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
            <div class="modal-header border-0">
                <h5 class="modal-title" id="exampleModalLabel"><b>Readers Details</b></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body border-0">
                <div>
                <h6 style="font-family: Balsamiq Sans">Reader UserName</h6>
                <input style="margin-top: 5px;" onchange="getReader()" type="text" id="userReaderDetails" class="form-control" placeholder="Enter Username" >
                <p id="checkReader" style="margin-top: 2px;"></p>
                </div>
            </div>
            <div class="modal-footer border-0" >
                <div id="checkReaderButton" style="display:none">
                <button type="button" class="btn btn-success" data-bs-dismiss="modal" onclick="AddBookReader();">Give</button>
                </div>
            </div>
            </div>
        </div>
    </div>
    <%-- modal --%>
        
<%-- <div class="container">
   <div class="row">
      <div class="card sha" id="ccaarrdd" data-bs-toggle="modal" data-bs-target="#exampleModal">
         <div class="card-body">
            <div class="row">
            <div class="col-10" style="background-color:green;">
                <div class="row">
               <h2 style="font-family: Balsamiq Sans, cursive;">'+.title+'</h2>
            </div>
            <div class="row">
               <div class = "col-md"><span style="font-size:20px">'+subtitle+'&nbsp;&#183;&nbsp;</span><span style="font-size:18px">'+ed+'</span><span style="margin-left:5px;">'+parsedJSON[i].author.slice(1, -1)+'</span></div>
            </div>
            <div class="row">
               <div class = "col-col-sm" style="background-color:white;">
                  <p>Category : 'category+'</p>
               </div>
               <div class = "col-sm-1" style="background-color:white;">
                  <p>'language+'</p>
               </div>
               <div style="background-color:white;width:auto">
                  <p>publish : '12-12-1232'</p>
               </div>
               <div style="background-color:white;width:auto">
                  <p>genre : 'qwsed,sdf'</p>
               </div>
               <div style="background-color:white;width:auto">
                  <p>Book Count : '1'</p>
               </div>
               <div style="background-color:white;width:auto">
                  <p>Availability : 15</p>
               </div>
            </div>
            
            </div>
            <div class="col-2" style="background-color:red;
            display:flex;
            flex-direction:column;justify-content: space-around;"> 
            <button type="button" class="btn btn-success col-9"><i class="fas fa-plus" style="color:white;padding-right:10px;"></i>Reader</button>
            <button type="button" class="btn btn-danger col-9"><i class="far fa-trash-alt" style="color:white;padding-right:10px;"></i>Remove</button>
            </div>
            </div>
            
         </div>
      </div>
   </div>
</div> --%>
<%--  --%>        
    <div style="margin-left:20px">
        <div class="row">

            <div class="col-sm-2" style="background-color:white;">
                <label class="form-label head">Title</label>
                <input type="text" list="titlesearch" class="form-control" id="fitertitle" placeholder="Enter Book Title">
                <datalist id="titlesearch">
                </datalist>
            </div>
            <div class="col-sm-2" style="background-color:white;">
                <label class="form-label head">Language</label>
                <input type="text" list="languagesearch" name="browser" id="filterlang" class="form-control" placeholder="Enter Category">
                <datalist id="languagesearch">
                </datalist>
            </div>
            <div class="col-sm-2" style="background-color:white;">
                <label class="form-label head">Author</label>
                <input type="text" class="form-control" id="filterauthor" placeholder="Enter Book Title">
                <%-- <datalist id="authorsearch">
                </datalist> --%>
            </div>            
            <div class="col-sm-2" style="background-color:white;">
                <label class="form-label head">Category</label>
                <input type="text" list="categorysearch" name="browser" id="filtercat" class="form-control" placeholder="Enter Category">
                <datalist id="categorysearch">
                </datalist>
            </div>

            <div class="col-sm-2" style="background-color:white;">
                <label class="form-label head">Availability</label>
                <input type="text" list="availablesearch" id="filteravai" name="browser" id="browser" class="form-control" placeholder="Enter Category">
                <datalist id="availablesearch">
                    <option value="True">
                    <option value="False">
                </datalist>
            </div>

            <div class="col-sm-2" style="background-color:white;">
                <button type="button" class="btn btn-primary" style="margin-top:42px;width:90%;" onclick="onFilter();"><i class="fas fa-filter" style="margin-right:10px;"></i>Filter</button>
            </div>
        </div>
    </div>

    <div id="content" style="margin-top:20px;"></div>
    
    </div>

    <div class="books" id="addreader" style="display:none;">
        <div>
        <div class="row" style="margin-top:5%;">
            <div class="col-md-6" style="background-color:white">
                <img src="https://ca-times.brightspotcdn.com/dims4/default/16d095a/2147483647/strip/true/crop/1126x640+0+0/resize/840x477!/quality/90/?url=https%3A%2F%2Fcalifornia-times-brightspot.s3.amazonaws.com%2F61%2F51%2F35d4ba7d4eef81a29dd4050f6632%2Fla-ca-how-to-raise-a-reader-150.JPG" 
                class="img-fluid" alt="Reader_Registation">

            </div>
            <div class="col-md-6" style="background-color:white;margin-top:1%;">
            
                <div class="row">
                    <div class="col-md-7"><h1 class="quot">A word after a word after a word is power</h1></div>
                    <div class="col-md-9">
                        <label for="inputEmail4" class="form-label head col-md-9" style="background-color:white;font-family: 'Nunito', sans-serif;">UserName</label>
                        <%-- <input type="text" onchange="getVal()" class="form-control" id="readeraddusername" placeholder="Enter username"><span id="res"></span> --%>
                        <input type="text" onchange="getVal()" class="inp col-md-9" id="readeraddusername" name="readeraddusername"placeholder="Enter username">
                    </div>
                    <span id="res" class="col-md-9" style="margin-top:10px"></span>
                    <div class="col-md-9">
                        <label for="inputEmail4" class="form-label head col-md-9" style="font-family: 'Nunito', sans-serif;">Full Name</label>
                        <input type="text" class="inp col-md-9" id="readeraddfullname" name="username" placeholder="Enter Name">
                        <%-- <input type="text" class="form-control" id="readeraddfullname" placeholder="Enter Name"> --%>
                    </div>
                </div>
                <div class="row" style="background-color:white">
                    <div class="col-md-7">
                        <button type="button" class="col-md-7 btn btn-primary " style="margin-top:30px;float: right;font-family: 'Nunito', sans-serif;" onclick="addReader();">Add Reader</button>
                    </div>
                </div>
            </div>
        </div>
        </div>
    
    </div>

    <div class="books" id="lending" style="display:none;">
        <div class="container" style="background-color:white;margin-top:1%;">
            <h1>Readers Borrower Lists</h1>
            <%-- modal --%>
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        ...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                    </div>
                </div>
            </div>
            <%-- modal --%>
            <div id="lendingBooks" style="margin-top:20px;">
                <table class="table table-striped borderless" id="lendDetails">
                </table>
            </div>
        </div>
    </div>
    
</body>
<script>

function trimstring(str){
    return str.trim();
}
function addbook(){
    var title = trimstring($('#title').val());
    var sub = trimstring($('#sub').val());
    var author = trimstring($('#auth').val());
    var lag = trimstring($('#lag').val());
    var cat = trimstring($('#cat').val());
    var publish = trimstring($('#pub').val());
    var genre = trimstring($('#gen').val());
    var edition = trimstring($('#edit').val());
    var tot = trimstring($('#sum').val());
    console.log(title,sub,author,lag,cat,publish,genre);
    if((title && sub && author && lag && cat && publish && genre) !=null){
        console.log("check pass");
        $.ajax({
            type:'POST',
            url:'LibBookInsert',
            data:{
                title:title,
                subtitle:sub,
                author:author,
                categ:cat,
                lang:lag,
                publish:publish,
                edition:edition,
                tot:tot,
                genre:genre,
            },
            success:function(result){
                var msg=result[0];
                console.log(result);
                if(msg=="s"){
                    window.alert("Successfully Book is Added");
                    $(':input').val('');
                }
                else if(msg=="f"){
                    window.alert("Please, Try some other Time ");
                }
            }
        })
    }

}

let books = '';

function onPressNav(evt, org){
    console.log("entered")
    var i, x, tablinks;
    x = document.getElementsByClassName("books");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    document.getElementById(org).style.display = "block";
    x = document.getElementsByClassName("nav-link");
    for (i = 0; i < x.length; i++) {
        x[i].className ="nav-link";
    }
    evt.currentTarget.className += " text-light";
    if(org=='view'){
        console.log("enter in book view")
        getBookdata();
    }
    if(org=='lending'){
        console.log("enter in readres view 123")
        onReaderData();
    }
    
}

var avalibalityObj = {};
var titleSet = new Set();
var languageSet = new Set();
var categorySet = new Set();

function getBookdata(){
    avalibalityObj={}
    $.ajax({
            type:'GET',
            url:'LibraryBook',
            success:function(result){
                var tempArray = [];
                books='';
                var parsedJSON = JSON.parse(result);
                for (var i=0;i<parsedJSON.length;i++) {
                    if(!(tempArray.includes(parsedJSON[i].bookunify))){
                        tempArray.push(parsedJSON[i].bookunify);
                        let ed = parsedJSON[i].edition !=null? "Edition "+parsedJSON[i].edition+"&nbsp;&#183;&nbsp;":""
                        books+='<div class="container"><div class="row"><div class="card sha" id="ccaarrdd"><div class="card-body"><div class="row"><div class="col-10" style="background-color:white;"><div class="row"><h2 style="font-family: Balsamiq Sans, cursive;">'+parsedJSON[i].title+'</h2></div><div class="row"><div class = "col-md"><span style="font-size:20px">'+parsedJSON[i].subtitle+'&nbsp;&#183;&nbsp;</span><span style="font-size:18px">'+ed+'</span><span style="margin-left:5px;">'+parsedJSON[i].author.slice(1, -1)+'</span></div></div><div class="row"><div class = "col-col-sm" style="background-color:white;"><p>Category : '+parsedJSON[i].category+'</p></div><div class = "col-sm-1" style="background-color:white;"><p>'+parsedJSON[i].language+'</p></div><div style="background-color:white;width:auto"><p>publish : '+parsedJSON[i].publish+'</p></div><div style="background-color:white;width:auto"><p>genre : '+parsedJSON[i].genre.slice(1, -1)+'</p></div><div style="background-color:white;width:auto"><p>Book Count : '+parsedJSON[i].bookcount+'</p></div><div style="background-color:white;width:auto"><span>Availability : <span id="'+parsedJSON[i].bookunify+'" >0</span></span></div></div></div><div class="col-2" style="background-color:white;display:flex;flex-direction:column;justify-content: space-around;"> <button type="button" class="btn btn-success col-9" onclick="bookRegNo('+parsedJSON[i].bookunify+')" data-bs-toggle="modal" data-bs-target="#givebook"><i class="fas fa-plus" style="color:white;padding-right:10px;"></i>Reader</button><button type="button" class="btn btn-danger col-9" onclick="bookRegNo('+parsedJSON[i].bookunify+')" data-bs-toggle="modal" data-bs-target="#deleteModal"><i class="far fa-trash-alt" style="color:white;padding-right:10px;"></i>Remove</button></div></div></div></div></div></div>';
                    }
                    categorySet.add(parsedJSON[i].category.toLowerCase());
                    languageSet.add(parsedJSON[i].language.toLowerCase());
                    titleSet.add(parsedJSON[i].title.toLowerCase())
                    //avalibality check;
                    if(parsedJSON[i].available){
                        let id = '#'+parsedJSON[i].bookunify;
                        if(avalibalityObj[id]==null){
                            avalibalityObj[id] = 1;
                        }else{
                            avalibalityObj[id] = avalibalityObj[id]+1;
                        }
                    }
                }
                
                $("#content").html(books);
                avalibalitySetdata();
                setCategoryDropdown();
                setTitleDropdown();
                setLanguageDropdown();
            }
            })
}
function setTitleDropdown(){
    var valInopt = '';
    titleSet.forEach(function(value) {
        var hel = '<option value="'+value+'">';
        valInopt=valInopt+hel;
    })
    console.log(valInopt);
    $("#titlesearch").html(valInopt);
}
function setLanguageDropdown(){
    var valInopt = '';
    languageSet.forEach(function(value) {
        var hel = '<option value="'+value+'">';
        valInopt=valInopt+hel;
    })
    console.log(valInopt);
    $("#languagesearch").html(valInopt);
}
function setCategoryDropdown(){
    var valInopt = '';
    categorySet.forEach(function(value) {
        var hel = '<option value="'+value+'">';
        valInopt=valInopt+hel;
    })
    console.log(valInopt);
    $("#categorysearch").html(valInopt);
}
function avalibalitySetdata(){
    for (let key in avalibalityObj) {
        $(key).html(avalibalityObj[key]);
        // console.log(key, avalibalityObj[key]);
    }
}

function logout() {
    $.ajax({
        type:'POST',
        url:'LibrarianLogout',
        data:{ },
        success:function(result){
        window.location.replace('');
        }
    })
}

function addReader(){
    var username = trimstring($('#readeraddusername').val());
    var name = trimstring($('#readeraddfullname').val());
    if((username && name)!=null && username.length>0){
        console.log("check pass");
        $.ajax({
            type:'POST',
            url:'AddReader',
            data:{
                username:username,
                name:name,
            },
            success:function(result){
                var msg=result[0];
                if(msg=="s"){
                    window.alert("Reader is Added");
                    $(':input').val('');
                    $("#res").html("");
                }
                else if(msg=="f"){
                    window.alert("please check username and password");
                }
                }
            });
    }
}

function getVal(){
    const val = trimstring($('#readeraddusername').val());
    console.log(val);
    if(val!=null && val.length>0){
        console.log("hwllo world")
    $.ajax({
            type:'POST',
            url:'CheckReader',
            data:{
                username:val,
            },
            success:function(result){
                var msg=result[0];
                if(msg=="s"){
                    $("#res").html("<span style='color:green'>&#10003; Available</span>");
                }
                else if(msg=="f"){
                    $("#res").html("<span style='color:red'>&#10008; Try someother Username</span>");
                }
                }
    });
        }else{
            $("#res").html("<span style='color:red'>&#10008; Try someother Username</span>");
        }

}
let bookOrgNumber;
function bookRegNo(bookno){
    bookOrgNumber=bookno;
    console.log(bookno);
}

function getReader(){
    const val = trimstring($('#userReaderDetails').val());
    console.log(val);
    // onPressNav checkReaderButton

    if(val!=null && val.length>0){
        console.log("enter in if")
        $.ajax({
            type:'POST',
            url:'CheckReader',
            data:{
                username:val,
            },
            success:function(result){
                var msg=result[0];
                if(msg=="s"){
                    document.getElementById("checkReaderButton").style.display = "none";
                    $("#checkReader").html("<span style='color:red'>&#10008; Username Not Found</span>");
                }
                else if(msg=="f"){
                    document.getElementById("checkReaderButton").style.display = "block";
                    $("#checkReader").html("<span style='color:green'>&#10003; Available </span>");
                }
            }
        });
    }
    else{
        console.log("enter in else")
        $("#checkReader").html("<span style='color:red'>&#10008; Username Not Found</span>");
        document.getElementById("checkReaderButton").style.display = "none";
    }
}

function onBookRemove(){
    var count = $('#bookondelete').val();
    console.log(bookOrgNumber);
    console.log(count);
    if(count>0){
        $.ajax({
            type:'POST',
            url:'RemoveBooks',
            data:{
                bookid:bookOrgNumber,
                bookcount:count,
            },
            success:function(result){
                window.alert(result);
                getBookdata();
            }
            });
    }
    else{
        window.alert("Invalid Count");
    }
}

function AddBookReader(){
    console.log("hello add  clicked")
    const val = trimstring($('#userReaderDetails').val());
    console.log(val);
    console.log(bookOrgNumber);
    if(val.length > 0){
    $.ajax({
            type:'POST',
            url:'LibrarianLendBook',
            data:{
                bookid:bookOrgNumber,
                username:val,
            },
            success:function(result){
                var msg=result[0];
                window.alert(result);
                // if(msg=="s"){
                //     window.alert("----aaa==========");
                //     $(':input').val('');
                // }
                // else if(msg=="f"){
                //     window.alert("please check Reader Username");
                // }
                $("#checkReader").html("");
            }
    });
    }else{
        $("#checkReader").html("<span>Invalid UserName</span>");
    }
    
}

function onFilter(){
    var title = trimstring($('#fitertitle').val());
    var lang = trimstring($('#filterlang').val());
    var author = trimstring($('#filterauthor').val());
    var category = trimstring($('#filtercat').val());
    var available = trimstring($('#filteravai').val());

    console.log(title,lang,author,category,available)
    if(title.length >0 || lang.length >0 || author.length >0 || category.length >0 || available.length >0){
        // $("#content").html("");
        avalibalityObj={}
        $.ajax({
            type:'POST',
            url:'FilterBooks',
            data:{
                title:title.toLowerCase(),
                lang:lang.toLowerCase(),
                cat: category.toLowerCase(),
                auth:author,
                avai:available.toLowerCase(),
            },
            success:function(result){
                $("#content").html("");
                console.log(result)
                books='';
                var tempArray = [];
                var parsedJSON = JSON.parse(result);
                for (var i=0;i<parsedJSON.length;i++) {
                    if(!(tempArray.includes(parsedJSON[i].bookunify))){
                        tempArray.push(parsedJSON[i].bookunify);
                        let ed = parsedJSON[i].edition !=null? "Edition "+parsedJSON[i].edition+"&nbsp;&#183;&nbsp;":""
                        books+='<div class="container"><div class="row"><div class="card sha" id="ccaarrdd"><div class="card-body"><div class="row"><div class="col-10" style="background-color:white;"><div class="row"><h2 style="font-family: Balsamiq Sans, cursive;">'+parsedJSON[i].title+'</h2></div><div class="row"><div class = "col-md"><span style="font-size:20px">'+parsedJSON[i].subtitle+'&nbsp;&#183;&nbsp;</span><span style="font-size:18px">'+ed+'</span><span style="margin-left:5px;">'+parsedJSON[i].author.slice(1, -1)+'</span></div></div><div class="row"><div class = "col-col-sm" style="background-color:white;"><p>Category : '+parsedJSON[i].category+'</p></div><div class = "col-sm-1" style="background-color:white;"><p>'+parsedJSON[i].language+'</p></div><div style="background-color:white;width:auto"><p>publish : '+parsedJSON[i].publish+'</p></div><div style="background-color:white;width:auto"><p>genre : '+parsedJSON[i].genre.slice(1, -1)+'</p></div><div style="background-color:white;width:auto"><p>Book Count : '+parsedJSON[i].bookcount+'</p></div><div style="background-color:white;width:auto"><span>Availability : <span id="'+parsedJSON[i].bookunify+'" >0</span></span></div></div></div><div class="col-2" style="background-color:white;display:flex;flex-direction:column;justify-content: space-around;"> <button type="button" class="btn btn-success col-9" onclick="bookRegNo('+parsedJSON[i].bookunify+')" data-bs-toggle="modal" data-bs-target="#givebook"><i class="fas fa-plus" style="color:white;padding-right:10px;"></i>Reader</button><button type="button" class="btn btn-danger col-9" onclick="bookRegNo('+parsedJSON[i].bookunify+')" data-bs-toggle="modal" data-bs-target="#deleteModal"><i class="far fa-trash-alt" style="color:white;padding-right:10px;"></i>Remove</button></div></div></div></div></div></div>';
                    }
                    categorySet.add(parsedJSON[i].category.toLowerCase());
                    languageSet.add(parsedJSON[i].language.toLowerCase());
                    titleSet.add(parsedJSON[i].title.toLowerCase())
                    //avalibality check;
                    if(parsedJSON[i].available){
                        let id = '#'+parsedJSON[i].bookunify;
                        if(avalibalityObj[id]==null){
                            avalibalityObj[id] = 1;
                        }else{
                            avalibalityObj[id] = avalibalityObj[id]+1;
                        }
                    }
                }
                
                $("#content").html(books);
                avalibalitySetdata();
                // setCategoryDropdown();
                // setTitleDropdown();
                // setLanguageDropdown();
            }
        });
    }else{
        getBookdata();
    }
}

function onReaderData(){
    $.ajax({
        type:'POST',
        url:'ReadersData',
            success:function(result){
                // console.log(result)
                console.log("lending innnnnnnnn")
                console.log(result)
                var readerDetails= ""
                var parsedJSON = JSON.parse(result);
                if(parsedJSON.length > 0) readerDetails+='<thead><tr><th scope="col">S.No</th><th scope="col">Reader UserName</th><th scope="col">Reader Name</th><th scope="col">Book Name</th><th scope="col">Book Id</th></tr></thead><tbody>';
                for (var i=0;i<parsedJSON.length;i++) {
                    readerDetails +='<tr data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="giving(\'' + parsedJSON[i].bookid + '\',\'' + parsedJSON[i].readerid + '\',\'' + parsedJSON[i].readerusername + '\')" ><th scope="row">'+(i+1)+'</th><td>'+parsedJSON[i].readerusername+'</td><td>'+parsedJSON[i].readername+'</td><td>'+parsedJSON[i].bookname+'</td><td>'+parsedJSON[i].bookid+'</td></tr>';
                }
                readerDetails += '</tbody>';
                $("#lendDetails").html(readerDetails);
            }
    });
}

function giving(a,b,c){
    console.log("hello");
    console.log(a,b,c);
    // const obj = JSON.stringify(res);
    // const obj1 = JSON.parse(res);
    // console.log(obj);
    // console.log(obj1);
}

</script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
var bookborrow = 0;
var booklib = 0;
function onGetDataforLib(){
    $.ajax({
            type:'POST',
            url:'Dashboard',
            success:function(result){
                if(result.length >0){
                    console.log(result);
                    var g =result.split(",")
                    console.log("]]]]]");
                    bookborrow = g[0];
                    booklib = g[1];
                    console.log(g[0]);
                    console.log(g[1]);
                }
            }
            });
}
function booklibxx(){
    return booklib;
}
function bookborxx(){
    return bookborrow;
}
        // Load the Visualization API and the corechart package.
        google.charts.load('current', {'packages':['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
        function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Topping');
        data.addColumn('number', 'Slices');
        data.addRows([
            ['Books borrowed', parseFloat(bookborrow)],
            ['Books in Library', (parseFloat(booklib)-parseFloat(bookborrow))],
        ]);

        // Set chart options
        var options = {'title':'Book in Library',
                        'width':400,
                        'height':300};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
        }
    </script>
</html>