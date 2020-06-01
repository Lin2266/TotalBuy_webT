package uuu.totalbuy.view;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "HelloServlet", urlPatterns = {"/hello.view"})
public class HelloServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */            
            request.getRequestDispatcher("/WEB-INF/subviews/header.jsp").include(request, response);
            out.println("<div id=\"section\">section</div>");
            out.println("<div id=\"article\">");
            out.println("<p>你好，現在時間是: " + new Date() + "</p>");            
            ServletContext application = this.getServletContext();
            Integer visiters = (Integer)application.getAttribute("app.visitors");
            if(visiters==null){
                visiters = 0;
            }            
            application.setAttribute("app.visitors", ++visiters); //應寫在session created事件
            out.println("<p>拜訪人次: " + visiters + "</p>");
            out.println("<div id=\"aside\">aside</div>");
            request.getRequestDispatcher("/WEB-INF/subviews/footer.jsp").include(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}