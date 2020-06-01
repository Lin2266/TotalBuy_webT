<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>99乘法表</title>
    </head>
    <body>
        <h1>99乘法表</h1>
        <table border="1" cellpadding="5" cellspacing="0">
            <% for(int i=1;i<10;i++){ %>
            <tr style="background-color: <%=i%2==0?"lightblue": "lightyellow"%>">
                <%for(int j=1;j<10;j++){%>
                <!-- =等於out.println-->
                <td><%= i %> * <%= j %> = <%= i*j %></td> 
                <%}%>
            </tr>
            <%}%>
        </table>
    </body>
</html>
