<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="會員登入"/>
</jsp:include>
<div id="section">section</div>
<div id="article">
    <form action="j_security_check" method="POST">
        <table>
            <tr><td>帳號</td><td><input type="text" size="10" name="j_username"/></td></tr>
            <tr><td>密碼</td><td><input type="password" size="10" name="j_password"/></td></tr>
        </table>
        <input class="btn" type="submit" value="登入"/>
    </form>
</div>
<div id="aside">aside</div>  
<jsp:include page="/WEB-INF/subviews/footer.jsp" />
