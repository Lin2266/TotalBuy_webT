/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uuu.totalbuy.domain.BloodType;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.model.CustomerService;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register.do"})
public class RegisterServlet extends HttpServlet {

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
        List<String> errors = new ArrayList<>();
        //1.讀取請求中的表單資料並檢查
        request.setCharacterEncoding("UTF-8");

        //1.1 以下為必要欄位
        String id = request.getParameter("id");
        if (id != null) {
            id = id.trim();
        }

        String name = request.getParameter("name");
        if (name != null) {
            name = name.trim();
        }
        System.out.println("name:" + name);

        String password1 = request.getParameter("pwd1");
        String password2 = request.getParameter("pwd2");
        if (password1 != null && (password1 = password1.trim()).length() > 0
                && password2 != null && (password2 = password2.trim()).length() > 0) {
            if (!password1.equals(password2)) {
                errors.add("會員密碼與確認密碼不一致.");
            }
        } else {
            errors.add("會員密碼與確認密碼都必須輸入");
        }

        char gender;
        if(request.getParameter("gender")!=null){
            gender = request.getParameter("gender").charAt(0);
        }else{
            gender = Customer.MALE;
            errors.add("必須輸入性別");
        }

        String email = request.getParameter("email");
        if (email != null) {
            email = email.trim();
        }

        //以下為非必要欄位
        String birthday = request.getParameter("birthdate");
        if (birthday != null) {
            birthday = birthday.trim();
        }

        String address = request.getParameter("address");
        if (address != null) {
            address = address.trim();
        }

        String phone = request.getParameter("phone");
        if (phone != null) {
            phone = phone.trim();
        }

        String married = request.getParameter("married");

        String bloodType = request.getParameter("bloodType");

        String checkCode = request.getParameter("checkCode");
        HttpSession session = request.getSession();
        if (checkCode == null || (checkCode = checkCode.trim()).length() == 0) {
            errors.add("必須輸入checkCode");
        } else {
            String rand = (String) session.getAttribute("RegisterImageCheckServlet");
            if (rand == null || !rand.equalsIgnoreCase(checkCode)) {
                errors.add("checkCode不正確");
            }
        }

//        response.setContentType("text/html");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
        //2.呼叫商業邏輯
        if (errors.isEmpty()) {
            session.removeAttribute("RegisterImageCheckServlet");

            Customer m = new Customer();
            try {
                m.setId(id);
                m.setName(name);
                m.setPassword(password1);
                m.setGender(gender);
                m.setEmail(email);

                m.setBirthDate(birthday);
                m.setAddress(address);
                m.setPhone(phone);
                m.setMarried(married != null);

                BloodType bType = null;
                if (bloodType != null && (bloodType = bloodType.trim()).length() > 0) {
                    bType = BloodType.valueOf(bloodType);
                }
                m.setBloodType(bType);

                CustomerService service = new CustomerService();
                service.register(m);
                request.setAttribute("member", m);

                //3.1 forward to ok JSP
                request.getRequestDispatcher("/WEB-INF/views/register_ok.jsp").forward(request, response);
                return;

            } catch (Exception ex) {
                errors.add(ex.toString());
                this.log("新會員註冊失敗", ex);
            }
        }
        //3.2 forward to register           
        request.setAttribute("errors", errors);
        //3.1 forward to ok JSP
        request.getRequestDispatcher("/register.jsp").forward(request, response);

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
