/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.TotalBuyException;
import uuu.totalbuy.model.CustomerService;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login.do"})
public class LoginServlet extends HttpServlet {

//    public LoginServlet() {
//        System.out.println("LoginServlet is created...");
//    }
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
        //1.get form data, check data        
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        String checkCode = request.getParameter("checkCode");
        //System.out.println(id + "," + checkCode);
        //check data...
        List<String> errors = new ArrayList<>();
        HttpSession session = request.getSession();
        if (id == null || (id = id.trim()).length() == 0) {
            errors.add("必須輸入帳號");
        }
        if (pwd == null || (pwd = pwd.trim()).length() == 0) {
            errors.add("必須輸入密碼");
        }
        if (checkCode == null || (checkCode = checkCode.trim()).length() == 0) {
            errors.add("必須輸入檢核碼");
        } else {
            String rand = (String) session.getAttribute("ImageCheckServlet");
            if (rand == null || !rand.equalsIgnoreCase(checkCode)) {
                errors.add("檢核碼不正確");
            }
        }

        //2. call business service
        if (errors.isEmpty()) {
            session.removeAttribute("ImageCheckServlet");
            CustomerService service = new CustomerService();
            try {
                Customer c = service.login(id, pwd);

                //add id, auto to cookie
                String auto = request.getParameter("auto");

                Cookie idCookie = new Cookie("id", id);
                Cookie autoCookie = new Cookie("auto", "checked");

                int maxAge = 0;
                if (auto != null) {
                    maxAge = 7 * 24 * 60 * 60;
                }

                idCookie.setMaxAge(maxAge);
                autoCookie.setMaxAge(maxAge);
                response.addCookie(idCookie);
                response.addCookie(autoCookie);

                //3.1 forward to login_ok.jsp                
                session.setAttribute("user", c);
//                RequestDispatcher dispatcher
//                        = request.getRequestDispatcher("/");
//                dispatcher.forward(request, response);
                response.sendRedirect(request.getContextPath());
                return;
            } catch (TotalBuyException ex) {
                errors.add(ex.toString());
            } catch (Exception ex) {
                //ex.printStackTrace();
                this.log("使用者登入功能出現Unchecked Exception:", ex);
                errors.add(ex.toString());
            }
        }

        //3.2 forward to login.jsp(with errors)
        request.setAttribute("errors", errors);
        RequestDispatcher dispatcher
                = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
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
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
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

//    @Override
//    protected void finalize() throws Throwable {
//        System.out.println("finalize...");
//    }
}
