<%-- 
    Document   : null
    Created on : 2015/9/16, 下午 04:04:41
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="會員登入"/>
</jsp:include>
<div id="section">section</div>
<div id="article">
        <%
            String s = null;            
        %>
        <%= s.length()%>
</div>
<div id="aside">aside</div>  
<jsp:include page="/WEB-INF/subviews/footer.jsp" />
