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
import uuu.totalbuy.domain.BloodType;
import uuu.totalbuy.domain.Customer;
import uuu.totalbuy.domain.VIP;
import uuu.totalbuy.model.CustomerService;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/user/update.do"})
public class UpdateServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        Customer user = (Customer) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        //1.1 以下為必要欄位
        String id = user.getId();

        String name = request.getParameter("name");
        if (name != null) {
            name = name.trim();
        }

        String password = request.getParameter("password");
        if (password == null || (password = password.trim()).length() == 0) {
            errors.add("必須輸入原會員密碼");
        } else {
            if (!password.equals(user.getPassword())) {
                errors.add("原會員密碼不正確");
            }
        }
        String password1 = request.getParameter("pwd1");
        String password2 = request.getParameter("pwd2");
        if (password1 != null && (password1 = password1.trim()).length() > 0
                && password2 != null && (password2 = password2.trim()).length() > 0) {
            if (!password1.equals(password2)) {
                errors.add("修改密碼與確認密碼不一致.");
            }
        } else {
            if ((password1 == null || (password1 = password1.trim()).length() == 0)
                    && ((password2 == null || (password2 = password2.trim()).length() == 0))) {
                password1 = password;
            } else {
                errors.add("修改密碼與確認密碼必須同時輸入");
            }
        }

        char gender = Customer.MALE;
        if (request.getParameter("gender") != null) {
            gender = request.getParameter("gender").charAt(0);
        } else {
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
        if (checkCode == null || (checkCode = checkCode.trim()).length() == 0) {
            errors.add("必須輸入checkCode");
        } else {
            String rand = (String) session.getAttribute("RegisterImageCheckServlet");
            if (rand == null || !rand.equalsIgnoreCase(checkCode)) {
                errors.add("checkCode不正確");
            }
        }

        //2.呼叫商業邏輯
        if (errors.isEmpty()) {
            session.removeAttribute("RegisterImageCheckServlet");
            try {                
                Customer m = user.getClass().newInstance();
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
                if(m instanceof VIP){
                    ((VIP)m).setDiscount(((VIP)user).getDiscount());
                }                
                m.setStatus(user.getStatus());

                CustomerService service = new CustomerService();
                service.update(m);
                session.setAttribute("user", m);

                //3.1 sendRedirect to ok JSP
                response.sendRedirect(request.getContextPath());
                return;
            } catch (Exception ex) {
                errors.add(ex.toString());
                this.log("會員資料修改失敗", ex);
            }
        }
        //3.2 forward to register           
        request.setAttribute("errors", errors);
        //3.1 forward to ok JSP
        request.getRequestDispatcher("/user/update.jsp").forward(request, response);

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
