/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.Order;
import uuu.totalbuy.domain.ShoppingCart;
import uuu.totalbuy.domain.TotalBuyException;
import uuu.totalbuy.model.OrderService;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/user/check_out.do"})
public class CheckOutServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        List<String> errors = new ArrayList<>();

        //1. get data & check data
        HttpSession session = request.getSession();
        Customer user = (Customer) session.getAttribute("user");
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (user == null || cart == null) {
            errors.add("請重新登入後購物");
        }

        String pType = request.getParameter("paymentType");
        if (pType == null || !pType.matches("[0-3]")) {
            errors.add("必須選擇付款方式");
        }

        String sType = request.getParameter("shippingType");
        if (sType == null || !pType.matches("[0-2]")) {
            errors.add("必須選擇貨運方式");
        }

        String name = request.getParameter("receiver_name");
        if (name == null || (name = name.trim()).length() == 0) {
            errors.add("必須輸入收件人姓名");
        }

        String email = request.getParameter("receiver_email");
        if (email == null || (email = email.trim()).length() == 0) {
            errors.add("必須輸入收件人電郵");
        }

        String phone = request.getParameter("receiver_phone");
        if (phone == null || (phone = phone.trim()).length() == 0) {
            errors.add("必須輸入收件人電話");
        }

        String address = request.getParameter("receiver_address");
        if (address == null || (address = address.trim()).length() == 0) {
            errors.add("必須輸入收件人地址");
        }

        if (errors.isEmpty()) {
            //2. call business service
            try {
                Order order = new Order(user, cart);
                Order.PaymentType paymentType = Order.PaymentType.values()[Integer.parseInt(pType)];
                order.setPaymentType(paymentType);
                order.setPaymentAmount(paymentType.getAmount());

                Order.ShippingType shippingType = Order.ShippingType.values()[Integer.parseInt(sType)];
                order.setShippingType(shippingType);
                order.setShippingAmount(shippingType.getAmount());

                order.setReceiverName(name);
                order.setReceiverEmail(email);
                order.setReceiverPhone(phone);
                order.setShippingAddress(address);

                OrderService service = new OrderService();
                service.checkOut(order);
                
                session.removeAttribute("cart");
                
                //3.1 redirect /user/orders_history
                response.sendRedirect(request.getContextPath() + "/user/orders_history.jsp");
                return;
            } catch (TotalBuyException ex) {
                ex.printStackTrace(System.out);
                errors.add(ex.toString());
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                this.log("結帳失敗!", ex);
                errors.add(ex.toString());
            }
        }

        System.out.println(errors);
        //3.2 forward to /user/check_out.jsp
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/user/check_out.jsp").forward(request, response);        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
