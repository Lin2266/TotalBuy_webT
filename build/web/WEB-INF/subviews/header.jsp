<%@page import="uuu.totalbuy.domain.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String subtitle = request.getParameter("subtitle");
    if (subtitle == null) {
        subtitle = "TotalBuy";
    }

    String url = request.getRequestURL().toString();
    Customer user = (Customer) session.getAttribute("user");
%>
<html>
    <head>
        <title><%= subtitle%></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%if (url.indexOf("/user") > 0 && user == null) { %>
        <meta http-equiv="refresh" content="0; url=<%= request.getContextPath() %>/login.jsp"/>
        <%return;}%>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <link href="<%= request.getContextPath()%>/style/totalbuy.css" rel="stylesheet" type="text/css"/>
        <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script> 
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <script>
            function logout() {
                $.ajax({
                    url: "<%= request.getContextPath()%>/logout.do",
                    type: "POST",
                    contentType: "text/plain;charset=UTF-8"
                }).done(function (msg) {
                    alert(msg);
                    $("#welcomeSpan").text("你好, 歡迎光臨");
                    if(location.href.indexOf("/user")>0 || location.href.indexOf("/admin")>0){
                        location.href = "<%=request.getContextPath()%>/";
                    }else{
                        $("#userSpan").html("<a href='<%=request.getContextPath()%>/login.jsp'>會員登入</a> | " +
                                "<a href='<%= request.getContextPath()%>/register.jsp'>會員註冊</a> | ");
                    }
                });
            }
        </script>

    </head>
    <body>
        <div id="header">
            <h1>TotalBuy購物網 <span style="font-size: medium"><%=subtitle%></span></h1>
            <span id="welcomeSpan" style="font-size:small">你好, <%= user != null ? user.getName() : "歡迎光臨"%>!</span>
        </div>

        <div id="nav">
            <a href="<%= request.getContextPath()%>/">Home</a> &nbsp; | 
            <a href="<%= request.getContextPath()%>/products_list.jsp">產品清單</a> | 
            <a href="<%= request.getContextPath()%>/cart.jsp">購物車</a> &nbsp; | 
            <span id="userSpan">
                <% if (user == null) {%>
                <a href="<%= request.getContextPath()%>/login.jsp">會員登入</a> | 
                <a href="<%= request.getContextPath()%>/register.jsp">會員註冊</a> | 
                <%} else {%>
                <a href="<%= request.getContextPath()%>/user/orders_history.jsp">查詢訂單</a> |                 
                <a href="<%= request.getContextPath()%>/user/update.jsp">修改會員</a> |                 
                <a href="javascript: logout()">會員登出</a> |                 
                <%}%>
            </span>
            <a href="<%= request.getContextPath()%>/html範例">html範例</a> | 
            <a href="<%= request.getContextPath()%>/jsp範例">jsp範例</a> | 
            <a href="<%= request.getContextPath()%>//hello.view">HelloServlet</a> &nbsp; | 
            <a href="<%= request.getContextPath()%>/hello.jsp">hello.jsp</a> &nbsp; | 
            <a href="http://www.google.com">Google</a>        
        </div>
        <hr>