<%@page import="java.text.NumberFormat"%>
<%@page import="uuu.totalbuy.domain.Outlet"%>
<%@page import="uuu.totalbuy.domain.Product"%>
<%@page import="uuu.totalbuy.model.ProductService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMinimumFractionDigits(1);
    nf.setMaximumFractionDigits(2);

    String pid = request.getParameter("pid");
    if (pid != null && pid.matches("\\d+")) {
        ProductService service = new ProductService();
        Product p = service.get(Integer.parseInt(pid));
%>
<div id="article">
    <% if (p == null) {%>
    <h1>查無產品</h1>
    <%} else {%>
    <h4><%= p.getName()%></h4>
    <div style="float: left;width: 45%" class="logo">
        <a title="產品說明" href="get_product.jsp?pid=<%= p.getId()%>">
            <img alt="<%= p.getName()%>" src="<%= p.getUrl() == null ? "images/phone.png" : p.getUrl()%>">
        </a>
    </div>
    <div style="float: right;width: 55%">
        <input style="width:5ex" type="number" value="1" name="quantity_<%=p.getId()%>" min="1" max="10">
        <a href="add_cart.do?pid=<%= p.getId()%>" title="加入購物車"><img src="images/cart.png" alt=""/></a>
        <div>
            <%if (p instanceof Outlet) {%>
            原價: <%= nf.format(((Outlet) p).getListPrice())%><br>
            優惠: <%= 100 - ((Outlet) p).getDiscount()%> 折<br>
            <%}%>
            售價:<%= nf.format(p.getUnitPrice())%>
            <p><%= p.getDescription()==null?"":p.getDescription()%></p>
        </div>
    </div>
    <%}%>
</div>
 <%}else{%>
    <p>查無產品!</p>
 <%}%>