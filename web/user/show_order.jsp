<%@page import="uuu.totalbuy.domain.Product"%>
<%@page import="uuu.totalbuy.domain.OrderItem"%>
<%@page import="uuu.totalbuy.domain.Order"%>
<%@page import="uuu.totalbuy.model.OrderService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(request.getMethod().equalsIgnoreCase("get")){
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
    }
    
    String oid = request.getParameter("oid");
    if(oid!=null && oid.matches("\\d+")){
        
        OrderService service = new OrderService();
        Order order = service.get(Integer.parseInt(oid));
        if(order!=null){
%>
<!DOCTYPE html>
<div>
    <h5>訂單編號: <%= order.getId() %>, 訂購時間: <%= order.getOrderTime() %></h5>
    <table cellspacing="5">
        <tr><th>No.</th><th>名稱.</th><th>價格</th><th>數量</th><th>贈品</th></tr>
        <% for(OrderItem item:order.getItems()) {%>
        <tr><td><%= item.getProduct().getId() %></td>
            <td><%= item.getProduct().getName() %></td>
            <td><%= Product.PRICE_FORMAT.format(item.getPrice()) %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.isFree()?"是":"否" %></td></tr>
        <%}%>
        <tr><td colspan="5">收件人姓名: <%= order.getReceiverName() %></td></tr>
        <tr><td colspan="5">收件人電郵: <%= order.getReceiverEmail() %></td></tr>
        <tr><td colspan="5">收件人電話: <%= order.getReceiverPhone() %></td></tr>
        <tr><td colspan="5">收件人地址: <%= order.getShippingAddress() %></td></tr>  
    </table>        
    <div>
      
    </div>    
</div>
<%      return;
        }else{ %>
    <h5>查無訂單!</h5>
<%      } %>
    <h5>查無訂單編號!</h5>
<%  } %>