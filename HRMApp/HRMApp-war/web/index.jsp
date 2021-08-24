<%-- 
    Document   : index
    Created on : May 4, 2021, 8:57:24 AM
    Author     : heymeowcat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Heymeowcat/HRMsystem</title>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
        <link href="css/style.css" type="text/css" rel="stylesheet" media="screen,projection"/>
        <link href="css/animate.css" type="text/css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
        crossorigin="anonymous"></script>
        <link   rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
        <script type='text/javascript' src='dwr/engine.js'></script>
        <script type='text/javascript' src='dwr/interface/mdbCaller.js'></script>
        <script type='text/javascript' src='dwr/util.js'></script>
        <style>
            .tabs .tab a{
                color:#90caf9 ;
            }
            .tabs .tab a:hover {
                background-color:#e1f5fe  ;
                color:#0d47a1 ;
            } 
            .tabs .tab a.active {
                background-color:#90caf9    ;
                color:#0d47a1 ;
            } 
            .tabs .tab a:focus.active {
                background-color: #90caf9   
            }
            .tabs .indicator {
                background-color:#90caf9 ;
            }

        </style>
    </head>



    <body class="grey lighten-5"> 

        <%
            if (request.getSession().getAttribute("employeeId") == null) {

        %>

        <nav class="blue lighten-5" role="navigation ">
            <div class="nav-wrapper container"><a id="logo-container" href="#" class="brand-logo black-text">Siyarata HRM</a>
            </div>
        </nav>
        <section style="height: 90vh" class="valign-wrapper StickyContent animated zoomIn ">
            <div class="row  ">
                <div id="login" class="col s12 ">
                    <div class="card-panel" >
                        <form action="login" method="post">
                            <div class="row">
                                <div class="input-field col s12 m12">
                                    <input  id="usn" name="usn" type="text" class="validate" required="">
                                    <label for="usn">Username</label>
                                </div>
                                <div class="input-field col s12 m12">
                                    <input  id="psn" name="psn" type="password" class="validate" required="">
                                    <label for="psn">Password</label>

                                    <div class="g-signin2 " data-onsuccess="onSignIn" data-theme="light" ></div></center>
                                </div>
                            </div>
                            <input type="submit" value="login" class="blue lighten-5 black-text btn center" >
                        </form>

                    </div>
                </div>
                <%                            if (request.getParameter("error") != null) {
                %>
                <p class="center">Username or Password is Incorrect</p>
                <%}%>
            </div>

        </section>
        <%
        } else {
            int uid = Integer.parseInt(request.getSession().getAttribute("employeeId").toString());
        %>

        <section  style="height: 100vh;">
            <nav class="blue lighten-5" role="navigation ">
                <div class="nav-wrapper container"><a id="logo-container" href="#" class="brand-logo black-text">Siyarata HRM</a>
                </div>
            </nav>
            <center>
                <div class="center-align white card container " >

                    <ul class=" tabs tabs-fixed-width  ">
                        <li class="tab col s6 m6 l6"><a class="active" href="#admin">Admin</a></li>
                        <li class="tab col s6 m6 l6"><a  href="#pim">PIM</a></li>
                        <li class="tab col s6 m6 l6"><a  href="#leave">Leave</a></li>
                        <li class="tab col s6 m6 l6"><a  href="#time">Time</a></li>
                        <li class="tab col s6 m6 l6"><a  href="#recruitment">Recruitment</a></li>
                        <li class="indicator" style="left: 0px; right: 0px;"></li>
                    </ul>

                    <div  id="admin" style="padding: 20px" >


                        <ul class="collapsible">
                            <li class="active">
                                <div class="collapsible-header"><i class="material-icons">person</i>Users</div>
                                <div class="collapsible-body">

                                    <div class="row">
                                        <div class="input-field col s12 m6">
                                            <input id="username" type="text" class="validate">
                                            <label for="username">Username</label>
                                        </div>
                                        <div class="input-field col s12 m6">
                                            <input id="employee_name" type="text" class="validate">
                                            <label for="employee_name">Employee Name</label>
                                        </div>
                                        <button class="left blue lighten-1 btn white-text">Search</button>
                                        <button onclick="showAllUsers(<%=uid%>);" style="margin-left: 10px" class="left red lighten-1 btn "><i class="material-icons">refresh</i></button>

                                    </div>


                                    <div id="usertable">

                                    </div>


                                </div>
                            </li>
                            <li>
                                <div class="collapsible-header"><i class="material-icons">person_add</i>Add Users</div>
                                <div class="collapsible-body">

                                    <div class="row">
                                        <div class="input-field col s12 m6">
                                            <input id="newUserRole" type="text" class="validate">
                                            <label for="newUserRole">User Role</label>
                                        </div>
                                        <div class="input-field col s12 m6">
                                            <input id="newEmployeeNic" type="text" class="validate">
                                            <label for="newEmployeeNic">NIC</label>
                                        </div>
                                        <div class="input-field col s12 m6">
                                            <input id="newEmployeefirstname" type="text" class="validate">
                                            <label for="newEmployeefirstname">Employee First Name</label>
                                        </div>
                                        <div class="input-field col s12 m6">
                                            <input id="newEmployeelastname" type="text" class="validate">
                                            <label for="newEmployeelastname">Employee Last Name</label>
                                        </div>
                                        <div class="input-field col s12 m6">
                                            <input id="newEmployeeGender" type="text" class="validate">
                                            <label for="newEmployeeGender">Gender</label>
                                        </div>
                                        <div class="input-field col s12 m6">
                                            <input id="newEmployeeUsername" type="text" class="validate">
                                            <label for="newEmployeeUsername">Username</label>
                                        </div>
                                        <div class="input-field col s12 m6">
                                            <input id="newEmployeePassword" type="password" class="validate">
                                            <label for="newEmployeePassword">Password</label>
                                        </div>
                                        <div class="input-field col s12 m6">
                                            <input id="newEmployeeCPassword" type="password" class="validate">
                                            <label for="newEmployeeCPassword">Confirm Password</label>
                                        </div>

                                        <button onclick="addNewUser();" class="left blue lighten-1 btn white-text">Save</button>

                                    </div>

                                </div>
                            </li>
                            <li>
                                <div class="collapsible-header"><i class="material-icons">work</i>Job </div>
                                <div class="collapsible-body"><span>Lorem ipsum dolor sit amet.</span></div>
                            </li>

                        </ul>
                    </div>



                    <div id="pim" class="col s12">

                    </div>
                </div>

            </center>
        </section>

        <div id="peekprofile" class=" modal bottom-sheet card" style="max-height:100%">
            <div id="profilepeek" class="container">

            </div>
        </div>

        <script type="text/javascript" src="js/materialize.js"></script>
        <script>
                                            $(document).ready(function () {
                                                $('.tabs').tabs();
                                                $('.collapsible').collapsible();
                                                $(".dropdown-trigger").dropdown();
                                                $('#peekprofile').modal();
                                                $("body").on("contextmenu", function (e) {
                                                    return false;
                                                });
                                                showAllUsers(<%=uid%>);
                                            });

                                            function showAllUsers(uid) {
                                                userid = uid;
                                                var xhttp;
                                                xhttp = new XMLHttpRequest();
                                                xhttp.onreadystatechange = function () {
                                                    if (this.readyState == 4 && this.status == 200) {
                                                        document.getElementById("usertable").innerHTML = this.responseText;
                                                        $('.collapsible').collapsible();
                                                    }
                                                };
                                                xhttp.open("GET", "employees?uid=" + uid, true);
                                                xhttp.send();
                                            }
                                            function showprofile(str) {
                                                var xhttp;
                                                xhttp = new XMLHttpRequest();
                                                xhttp.onreadystatechange = function () {
                                                    if (this.readyState == 4 && this.status == 200) {
                                                        document.getElementById("profilepeek").innerHTML = this.responseText;
                                                        $('.collapsible').collapsible();
                                                    }
                                                };
                                                xhttp.open("GET", "profilefullview?q=" + str, true);
                                                xhttp.send();
                                            }
                                            function addNewUser() {
                                                var newRole = $('#newUserRole').val();
                                                var newNic = $('#newEmployeeNic').val();
                                                var newFname = $('#newEmployeefirstname').val();
                                                var newLname = $('#newEmployeelastname').val();
                                                var newUsn = $('#newEmployeeUsername').val();
                                                var newPsw = $('#newEmployeePassword').val();
                                                var newCPsw = $('#newEmployeeCPassword').val();
                                                var newGender = $('#newEmployeeGender').val();
                                                if (newCPsw == newPsw) {

                                                    var xhttp;
                                                    xhttp = new XMLHttpRequest();
                                                    xhttp.onreadystatechange = function () {
                                                        if (this.readyState == 4 && this.status == 200) {
                                                            $('#newUserRole').val('');
                                                            $('#newEmployeeNic').val('');
                                                            $('#newEmployeefirstname').val('');
                                                            $('#newEmployeelastname').val('');
                                                            $('#newEmployeeUsername').val('');
                                                            $('#newEmployeePassword').val('');
                                                            $('#newEmployeeCPassword').val('');
                                                            $('#newEmployeeGender').val('');
                                                            showAllUsers(<%=uid%>);
                                                        }
                                                    };
                                                    xhttp.open("GET", "addNewEmployee?newRole=" + newRole + "&newNic=" + newNic + "&newFname=" + newFname + "&newLname=" + newLname + "&newUsn=" + newUsn + "&newPsw=" + newPsw + "&newGender=" + newGender, true);
                                                    xhttp.send();
                                                }

                                            }
        </script> 
    </body>
    <%}%>
</html>
