/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.Product;
import uuu.totalbuy.domain.ShoppingCart;
import uuu.totalbuy.model.ProductService;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "AddCartServlet", urlPatterns = {"/add_cart.do"})
public class AddCartServlet extends HttpServlet {

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
        //1. get data & check data
        String pid = request.getParameter("pid");
        if (pid != null && pid.matches("\\d+")) {
            //2. call business service
            ProductService service = new ProductService();
            try {
                Product p = service.get(Integer.parseInt(pid));
                if (p != null) {
                    //將p加入購物車
                    HttpSession session = request.getSession();
                    Customer user = (Customer) session.getAttribute("user");
                    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                    if (cart == null) {
                        cart = new ShoppingCart(user);
                        session.setAttribute("cart", cart);
                    }
                    cart.add(p);
                    
                    //3.1 redirect to cart.jsp
                    response.sendRedirect(request.getContextPath() + "/cart.jsp");
                    return;
                }
            } catch (Exception ex) {
                this.log("加入購物車失敗", ex);
            }
        }

        //3.2 redirect to products_list.jsp
        response.sendRedirect(request.getContextPath() + "/products_list.jsp");

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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
