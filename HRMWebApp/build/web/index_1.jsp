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
        <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
        crossorigin="anonymous"></script>

        <script type='text/javascript' src='dwr/engine.js'></script>
        <script type='text/javascript' src='dwr/interface/mdbCaller.js'></script>
        <script type='text/javascript' src='dwr/util.js'></script>
        <script>
            function onload() {
                dwr.engine.setActiveReverseAjax(true);//1
                mdbCaller.readJMSMessageToMyTopic();
            }

            function sendJMSMessageToMyTopic()
            {
                var msg = $("#message").val();
                mdbCaller.sendJMSMessageToMyTopic(msg, {
                    callback: function (str) {
                        $('#chathistory').val(str);
                    }
                });
            }


        </script>
    </head>
    <nav class="blue-grey darken-3" role="navigation ">
        <div class="nav-wrapper container"><a id="logo-container" href="#" class="brand-logo">National Train System</a>
        </div>
    </nav>


    <body onload="onload()">
        <div id="trainContainer" class="row container">

        </div>
    </body>

</html>
