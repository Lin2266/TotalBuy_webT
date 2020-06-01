<%@page import="uuu.totalbuy.domain.Customer"%>
<%@page import="uuu.totalbuy.domain.VIP"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="uuu.totalbuy.domain.Outlet"%>
<%@page import="uuu.totalbuy.domain.Product"%>
<%@page import="uuu.totalbuy.domain.ShoppingCart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="購物車"/>
</jsp:include>
<div id="section">section</div>
<div id="article">
    <%
        //Customer user = (Customer) session.getAttribute("user");
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(2);

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart != null && !cart.getKeySet().isEmpty()) {
            if (cart.getUser() == null) {
                Customer user = (Customer) session.getAttribute("user");
                if(user!=null) cart.setUser(user);
            }
    %>
    <form method="POST" action="<%=request.getContextPath()%>/update_cart.do">
        <table border="0" style="width:80%;border: solid thin lightgrey;margin:auto">
            <tr><th>No.</th><th>名稱</th><th>圖片</th><th>原價</th><th>折扣</th><th>售價</th><th>數量</th><th>刪除</th></tr>
                    <%for (Product p : cart.getKeySet()) {%>
            <tr>
                <td><%=p.getId()%></td>
                <td><%=p.getName()%></td>
                <td><img style="width:120px" src="<%=p.getUrl() != null ? p.getUrl() : "images/phone.png"%>" alt="<%=p.getName()%>"></td>
                <td><%= p instanceof Outlet ? ((Outlet) p).getListPrice() : p.getUnitPrice()%></td>
                <td><%= p instanceof Outlet ? ((Outlet) p).getDiscount() : ""%></td>
                <td><%= nf.format(p.getUnitPrice())%></td>
                <td><input style="width:5ex" type="number" value="<%= cart.getQuentity(p)%>" name="quantity_<%=p.getId()%>" min="1" max="10"></td>
                <td><input type="checkbox" name="delete_<%=p.getId()%>"></td>
            </tr>
            <%}
                if (cart.getUser() != null && cart.getUser() instanceof VIP) {
            %>
            <tr><td style="text-align: right" colspan="5">原總金額:</td><td colspan="3"><%= cart.getListTotalAmount()%></td></tr>            
            <tr><td style="text-align: right" colspan="5">VIP 折扣:</td><td colspan="3"><%= ((VIP) cart.getUser()).getDiscount()%>%</td></tr>             
            <%}%>
            <tr><td style="text-align: right" colspan="5">總金額:</td><td colspan="3"><%= cart.getTotalAmount()%></td></tr>
            <tr>
                <td colspan="4"><input type="submit" value="回賣場" name="submit"></td>
                <td colspan="2">
                    <input type="submit" value="修改購物車" name="submit">
                </td>
                <td colspan="2">
                    <input type="submit" value="確認結帳" name="submit">
                </td>                    
            </tr>
        </table>
    </form>
    <%} else {%>
    <h4>購物車是空的</h4>
    <%}%>
</div>
<div id="aside">aside</div>  
<jsp:include page="/WEB-INF/subviews/footer.jsp" />
