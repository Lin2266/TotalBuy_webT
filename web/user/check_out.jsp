<%@page import="java.util.List"%>
<%@page import="uuu.totalbuy.domain.Customer"%>
<%@page import="uuu.totalbuy.domain.Order"%>
<%@page import="uuu.totalbuy.domain.VIP"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="uuu.totalbuy.domain.Outlet"%>
<%@page import="uuu.totalbuy.domain.Product"%>
<%@page import="uuu.totalbuy.domain.ShoppingCart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Customer user = (Customer) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="確認結帳"/>
</jsp:include>

<%
    //Customer user = (Customer) session.getAttribute("user");
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMinimumFractionDigits(1);
    nf.setMaximumFractionDigits(2);

    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
    if (cart != null && !cart.getKeySet().isEmpty()) {
%>
<script>

    function copy() {
        $("#receiver_name").val($("#user_name").val());
        $("#receiver_email").val($("#user_email").val());
        $("#receiver_phone").val($("#user_phone").val());
        $("#receiver_address").val($("#user_address").val());
    }
</script>
<div id="section"></div> 
<div id="article">
    <form method="POST" action="<%=request.getContextPath()%>/user/check_out.do">
        <table border="0" style="width:80%;margin:auto;border:solid thin lightgrey;"><!--border: solid thin lightgrey;-->
            <tr><th>No.</th><th>名稱</th><th>圖片</th><th>原價</th><th>折扣</th><th>售價</th><th>數量</th></tr>
                    <%for (Product p : cart.getKeySet()) {%>
            <tr>
                <td><%=p.getId()%></td>
                <td><%=p.getName()%></td>
                <td><img style="width:120px" src="<%=p.getUrl() != null ? p.getUrl() : "../images/phone.png"%>" alt="<%=p.getName()%>"></td>
                <td><%= p instanceof Outlet ? ((Outlet) p).getListPrice() : p.getUnitPrice()%></td>
                <td><%= p instanceof Outlet ? ((Outlet) p).getDiscount() : ""%></td>
                <td><%= nf.format(p.getUnitPrice())%></td>
                <td><%= cart.getQuentity(p)%></td>                
            </tr>
            <%}
                if (cart.getUser() != null && cart.getUser() instanceof VIP) {
            %>
            <tr><td style="text-align: right" colspan="4">原總金額:</td><td colspan="3"><%= cart.getListTotalAmount()%></td></tr>            
            <tr><td style="text-align: right" colspan="4">VIP 折扣:</td><td colspan="3"><%= ((VIP) cart.getUser()).getDiscount()%>%</td></tr>             
            <%}%>
            <tr><td style="text-align: right" colspan="4">總金額:</td><td colspan="3"><%= cart.getTotalAmount()%></td></tr>
            <tr>
                <td style="text-align: right" colspan="4">
                    <label for="paymentType">付款方式: </label>
                    <select name="paymentType" id="paymentType" required>
                        <option value="">請選擇...</option>
                        <% for (Order.PaymentType pType : Order.PaymentType.values()) {%>
                        <option value="<%=pType.ordinal()%>" 
                                <%= String.valueOf(pType.ordinal()).equals(request.getParameter("paymentType")) ? "selected" : "" %>>
                            <%=pType.getDescription()%></option>
                            <%}%>
                    </select>
                </td>
                <td colspan="3">
                    <label for="shippingType">貨運方式: </label>
                    <select name="shippingType" id="paymentType" required>
                        <option value="">請選擇...</option>
                        <% for (Order.ShippingType sType : Order.ShippingType.values()) {%>                        
                        <option value="<%=sType.ordinal()%>"
                                <%= String.valueOf(sType.ordinal()).equals(request.getParameter("shippingType")) ? "selected" : "" %>>
                            <%=sType.getDescription()%></option>
                            <%}%>
                    </select>
                </td>
            </tr>            
            <tr>
                <td colspan="7">
                    <div style="float:left;width:50%">
                        <fieldset>
                            <legend>訂購人資料</legend>
                            <label for="user_name">姓名:</label><input id ="user_name" value="<%=user.getName()%>" readonly><br>
                            <label for="user_email">電郵:</label><input id ="user_email" value="<%=user.getEmail()%>" readonly><br>
                            <label for="user_phone">電話:</label><input id ="user_phone" value="<%=user.getPhone() == null ? "" : user.getPhone()%>" readonly><br>
                            <label for="user_address">地址:</label><input id ="user_address" value="<%=user.getAddress() == null ? "" : user.getAddress()%>" readonly><br>
                        </fieldset>
                    </div>
                    <div style="float:left;width:50%;">
                        <fieldset>
                            <legend>收件人資料(<a href="javascript:copy()">複製訂購人</a>)</legend>
                            <label for="receiver_name">姓名:</label><input id ="receiver_name" name="receiver_name" 
                                                                         value="<%= request.getParameter("receiver_name") == null ? "" : request.getParameter("receiver_name").trim()%>" required><br>
                            <label for="receiver_email">電郵:</label><input id ="receiver_email" name="receiver_email"
                                                                          value="<%= request.getParameter("receiver_email") == null ? "" : request.getParameter("receiver_email").trim()%>" required><br>
                            <label for="receiver_phone">電話:</label><input id ="receiver_phone" name="receiver_phone" 
                                                                          value="<%= request.getParameter("receiver_phone") == null ? "" : request.getParameter("receiver_phone").trim()%>" required><br>
                            <label for="receiver_address">地址:</label><input id ="receiver_address" name="receiver_address" 
                                                                            value="<%= request.getParameter("receiver_address") == null ? "" : request.getParameter("receiver_address").trim()%>" required><br>                         
                        </fieldset>                        
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="4"><input type="button" value="回賣場" name="submit" onclick="location.href = '<%= request.getContextPath()%>/products_list.jsp'"></td>
                <td colspan="3"><input type="submit" value="確認結帳" name="submit"></td>                    
            </tr>
        </table>
    </form>
    <%} else {%>
    <h4>購物車是空的</h4>
    <%}%>
</div>
<div id="aside">
    <%
        List<String> errors = (List<String>) request.getAttribute("errors");
        if (errors != null && !errors.isEmpty()) {
    %>
    <script>
        $(function () {
            $("#dialog").dialog({
                autoOpen: true, width: 500, height: 300,
                show: {
                    effect: "blind",
                    duration: 500
                },
                hide: {
                    effect: "blind",
                    duration: 300
                }
            });
        });

        function showErrors() {
            $("#dialog").dialog("open");
        }
    </script>
    <div id="dialog" title="錯誤清單">
        <ul id="errorList">
            <% for (String msg : errors) {%>
            <li><%=msg%></li>
                <%}%>
        </ul>
    </div>
    <button onclick="showErrors()" style="color:red">檢視錯誤清單</button>
    <%}%>

</div>
<jsp:include page="/WEB-INF/subviews/footer.jsp" />
