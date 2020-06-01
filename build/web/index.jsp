<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="Home"/>
</jsp:include>
<div id="section">section</div>
<div id="article">
    <p id='test1'>Hello World!  </p>
    <p id='test1'>Hello World!</p>
</div>
<%--
        CustomerService service = new CustomerService();
        Customer c = service.login("A123456789", "123456");
        out.println(c);
--%>
<div id="aside">aside</div>  
<jsp:include page="/WEB-INF/subviews/footer.jsp" />