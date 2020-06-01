<%@page import="uuu.totalbuy.domain.VIP"%>
<%@page import="uuu.totalbuy.domain.Customer"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>登入成功</title>
    </head>
    <%
        Customer user = (Customer)session.getAttribute("user");
    %>
    <body>
        <h1>登入成功</h1>
        <table cellpadding="5">
            <tr><td>ID:</td><td><%= user.getId() %></td></tr>
            <tr><td>Name:</td><td><%= user.getName() %></td></tr>
            <tr><td>Gender:</td><td><%= user.getGender() %></td></tr>
            <tr><td>Email:</td><td><%= user.getEmail()%></td></tr>
            <tr><td>Married:</td><td><%= user.isMarried()%></td></tr>
            <tr><td>Address:</td><td><%= user.getAddress() %></td></tr>
            <tr><td>BirthDate:</td><td><%= user.getBirthDateString()%></td></tr>
            <%if(user instanceof VIP){%>
            <tr><td>Discount:</td><td><%= ((VIP)user).getDiscount() %>%</td></tr>
            <%}%>
        </table>
    </body>
</html>