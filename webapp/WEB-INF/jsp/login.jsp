<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- title, character encoding, Metadata, style, script, external file(for page rendering image etc..)-->
    <title>File Explorer - ESTsecurity pilot project</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js">
    <link rel="icon" href="${pageContext.request.contextPath}/assets/img/al.jpg">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css"/>
</head>

<body>
<div class="centered">
    <img id="logo" src="${pageContext.request.contextPath}/assets/img/ESTsecurity_CI_green.png">
    <form id="signform" action="${pageContext.request.contextPath}/login" method="POST">
        <input name="email" id="email" type="email">
        <label for="email">Email</label>
        <input name="password" id="password" type="password" class="validate">
        <label for="password">Password</label>
        <c:if test="${auth == false}">
        <h6 class="red-text center-align">※Incorrect identification: Please enter a correct username
            and password.
            </c:if>
            <br>
            <br>
            <div>
                <button type="submit" class="btn btn-link">
                    Submit
                </button>
            </div>
    </form>
</div>

</body>
</html>