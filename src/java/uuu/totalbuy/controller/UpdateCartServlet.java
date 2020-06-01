/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uuu.totalbuy.domain.Product;
import uuu.totalbuy.domain.ShoppingCart;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/update_cart.do"})
public class UpdateCartServlet extends HttpServlet {

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
        //1. get data & check data
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart)session.getAttribute("cart");
        if(cart!=null){
            //2. call business model
            Set<Product> keySet = cart.getKeySet();
            Set<Product> removedSet = new HashSet<>();
            for(Product p:keySet){
                String delete = request.getParameter("delete_" + p.getId());
                if(delete!=null){
                    removedSet.add(p);                    
                }
            }
            for(Product p:removedSet){
                cart.remove(p);
            }
            for(Product p:keySet){
                String quantity = request.getParameter("quantity_" + p.getId());
                if(quantity!=null && quantity.matches("\\d+")){
                    int q = Integer.parseInt(quantity);
                    if(q>0 && q<=10){
                        cart.update(p, q);
                    }
                }
            }
        }
        String submit = request.getParameter("submit");
        //3. redirect to cart.jap
        if("回賣場".equals(submit)){
            response.sendRedirect("products_list.jsp");
        }else if("確認結帳".equals(submit)){
            response.sendRedirect(request.getContextPath()+"/user/check_out.jsp");  
        }else{
            response.sendRedirect("cart.jsp");
        }
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
