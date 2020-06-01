<%@page import="java.util.List"%>
<%@page import="uuu.totalbuy.domain.*"%>
<%@page import="uuu.totalbuy.model.OrderService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Customer user = (Customer) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    OrderService service = new OrderService();
    List<Order> list = service.getOrdersHistory(user.getId());
%>
<jsp:include page="/WEB-INF/subviews/header.jsp" >
    <jsp:param name="subtitle" value="訂單歷史"/>
</jsp:include>
<div id="section">section</div>
<div id="article">
    <% if (list != null && list.size() > 0) {%>
    <table cellpadding="5" cellspacing="5">
        <thead>
            <tr>
                <th>No.</th>
                <th>訂購時間</th>
                <th>付款方式</th>
                <th>貨運方式</th>
                <th>總金額</th>
                <th>處理狀態</th>                
                <th>檢視</th>                
            </tr>            
        </thead>
        <tbody>
            <%for (int i = 0; i < list.size(); i++) {
                    Order order = list.get(i);
            %>
            <tr>
                <td><%= order.getId()%></td>
                <td><%= order.getOrderTime()%></td>
                <td><%= order.getPaymentType()%></td>
                <td><%= order.getShippingType()%></td>
                <td><%= order.getTotalAmount()%></td>
                <td><%= order.getBadStatus().ordinal() > 0 ? order.getBadStatus() : order.getStatus()%></td>                
                <td><a href="javascript: showOrder(<%= order.getId()%>)">明細</a></td>
            </tr>                        
            <%}%>
        </tbody>
    </table>
    <%}%>
</div>
<div id="aside">aside</div>
<div id="detail" title="產品明細"></div> 
<script>
    $(function () {
        $("#detail").dialog({
            autoOpen: false, width: 500, height: 300,
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

    function showOrder(orderId) {
        //alert(orderId);
        $.ajax({
            url: "<%= request.getContextPath()%>/user/show_order.jsp",
            method: "POST",
            data: {oid: orderId}
        }).done(
                function (result) {
                    //alert(result);
                    $("#detail").html(result);
                    $("#detail").dialog("open");
                }
        ).fail(
                function (xhr, status) {
                    console.log(status);
                }
        );
    }
</script>
<jsp:include page="/WEB-INF/subviews/footer.jsp" />
