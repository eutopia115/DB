<%--
  Created by IntelliJ IDEA.
  User: dongh
  Date: 2023-11-15
  Time: ���� 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=EUC-KR" language="java" pageEncoding="EUC-KR" %>
<html>
<head>
    <meta charset="EUC-KR">
    <title>Introduction to DB</title>
</head>
<body>
<h4><font size="5"> -- Received from main.html the data shown below -- </font></h4>
<%
    String[] getx = new String[4]; // ip, sid, port, user, pass
    request.setCharacterEncoding("EUC-KR");
    getx[0] = request.getParameter("first_name");
    getx[1] = request.getParameter("last_name");
    getx[2] = request.getParameter("sID");
    getx[3] = request.getParameter("course");
    out.println("&nbsp &#8226 My name is <b>"+getx[0]+" "+getx[1]+"</b><br><br>");
    out.println("&nbsp &#8226 My Student ID is <b>"+getx[2]+"</b><br><br>");
    out.println("&nbsp &#8226 I am taking <b>"+getx[3]+" this semester</b><br><br>");
%>

</body>
</html>
