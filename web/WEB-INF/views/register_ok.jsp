<%@page import="uuu.totalbuy.domain.*"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" info="註冊成功">
        <title>註冊成功</title>
    </head>
    <%
        Customer member = (Customer)request.getAttribute("member");
    %>
    <body>
        <h1>註冊成功</h1>
        <table cellpadding="5">
            <tr><td>ID:</td><td><%= member.getId() %></td></tr>
            <tr><td>Name:</td><td><%= member.getName() %></td></tr>
            <tr><td>Gender:</td><td><%= member.getGender() %></td></tr>
            <tr><td>Email:</td><td><%= member.getEmail()%></td></tr>
            <tr><td>Married:</td><td><%= member.isMarried()%></td></tr>
            <tr><td>Address:</td><td><%= member.getAddress() %></td></tr>
            <tr><td>BirthDate:</td><td><%= member.getBirthDateString()%></td></tr>
            <%if(member instanceof VIP){%>
            <tr><td>Discount:</td><td><%= ((VIP)member).getDiscount() %>%</td></tr>
            <%}%>
        </table>
    </body>
</html>