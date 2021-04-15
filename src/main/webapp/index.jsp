<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:p="http://primefaces.org/ui">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Register</title>

    </head>
    <body>
        <center><h2>Java Registration application using MVC and MySQL </h2></center>


        <form name="form" action="registroServlet" method="post" onsubmit="return validate()">
            <table align="center">
                <td>Username</td>
                <td><input type="text" name="username" /></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" /></td>
                </tr>
                <select id="textFieldTextJS" class="form-control" onchange="singleSelectChangeText()" >
                    <option value="0">admin</option>
                    <option value="1">Campo</option>
                    <option value="2">Laboratorio</option>
                    <option value="3">Almacen</option>
                </select>

                <input type="text" id="type" class="form-control" name="tipo"
                       placeholder="valor">
                <tr>
                    <td></td>
                    <td><input type="submit" value="Register"></input></td>
                </tr>
                <br> </br>

        </form>

    </table>
    <script>

        function singleSelectChangeText() {
            var selObj = document.getElementById("textFieldTextJS");
            var selValue = selObj.options[selObj.selectedIndex].text;

            //Setting Value
            document.getElementById("type").value = selValue;
        }
        function validate()
        {
            var username = document.form.username.value;
            var password = document.form.password.value;
            var type = document.getElementById("type").value;
            alert(type)
            if (username == null || username == "")
            {
                alert("Username can't be blank");
                return false;
            } else if (password == null || password == "") {
                alert("password can't be blank");
                return false;
            } else if (type == null || type == "") {
                alert("type can't be blank");
                return false;
            }
        }
    </script> 
</body>
</html>
