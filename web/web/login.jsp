<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="會員登入"/>
</jsp:include>
<div id="section">section</div>
<div id="article">
    <script>
        function refreshImage() {
            var checkImage = document.getElementById("checkImage");
            checkImage.src = "images/check.jpg?get=" + new Date();
        }
    </script>  
    <%
        String idCookie = "";
        String autoCookie = "";

        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("id")) {
                idCookie = c.getValue();
            } else if (c.getName().equals("auto")) {
                autoCookie = c.getValue();
            }
        }

        List<String> errors = (List<String>) request.getAttribute("errors");
        if (errors != null && !errors.isEmpty()) {
    %>
    <ul>
        <% for (String msg : errors) {%>
        <li><%=msg%></li>
            <%}%>
    </ul>
    <%}%>
    <form method="POST" action="login.do">
        <p>
            <label for="id">會員帳號</label>
            <input type="text" id="id" name="id" pattern="[A-Za-z][12][0-9]{8}" 
                   value="<%= request.getParameter("id") == null ? idCookie : request.getParameter("id")%>"
                   required placeholder="請輸入身分證號">                
            <input type="checkbox" id="auto" name="auto" 
                   <%= request.getParameter("auto") != null ? "checked" : request.getParameter("id") != null ? "" : autoCookie%>
                   >
            <label for="auto">記住帳號</label>   
        </p>
        <p>
            <label for="pwd">會員密碼</label>
            <input type="password" id="pwd" name="pwd" required placeholder="請輸入密碼">                
        </p>
        <p>
            <a href="javascript: refreshImage()" title="點選即可更新圖片">
                <img id="checkImage" src="images/check.jpg" alt="檢核碼"/>
            </a>
            <input type="text" id="checkCode" name="checkCode" 
                   value="<%= request.getParameter("checkCode") == null ? "" : request.getParameter("checkCode")%>"
                   required placeholder="請依左圖輸入英數字">                
        </p>           
        <input type="submit" value="確定">
    </form>   
</div>
<div id="aside">aside</div>  
<jsp:include page="/WEB-INF/subviews/footer.jsp" />