package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import uuu.totalbuy.domain.Customer;
import java.text.NumberFormat;
import uuu.totalbuy.domain.Outlet;
import uuu.totalbuy.domain.Product;
import java.util.List;
import uuu.totalbuy.model.ProductService;

public final class products_005flist_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/subviews/header.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("subtitle", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("產品清單", request.getCharacterEncoding()), out, false);
      out.write("\r\n");
      out.write("<div id=\"section\">section</div>\r\n");
      out.write("<div id=\"article\">\r\n");
      out.write("    ");

        //Customer user = (Customer) session.getAttribute("user");
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(2);

        ProductService service = new ProductService();
        List<Product> list = service.getAll();
        if (!list.isEmpty()) {
    
      out.write("\r\n");
      out.write("    <script>\r\n");
      out.write("        $(function () {\r\n");
      out.write("            $(\"#dialog\").dialog({\r\n");
      out.write("                autoOpen: false, width:500,height:300,\r\n");
      out.write("                show: {\r\n");
      out.write("                    effect: \"blind\",\r\n");
      out.write("                    duration: 500\r\n");
      out.write("                },\r\n");
      out.write("                hide: {\r\n");
      out.write("                    effect: \"blind\",\r\n");
      out.write("                    duration: 300\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("        function get_product(pid) {\r\n");
      out.write("            $.ajax({\r\n");
      out.write("                url: \"");
      out.print( request.getContextPath());
      out.write("/get_product.jsp?pid=\" + pid,\r\n");
      out.write("                type: \"GET\",\r\n");
      out.write("                contentType: \"text/html;charset=UTF-8\"\r\n");
      out.write("            }).done(function (result) {\r\n");
      out.write("                $(\"#dialog\").html(result);\r\n");
      out.write("                $(\"#dialog\").dialog(\"open\");\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("    </script>\r\n");
      out.write("    <ul id=\"pro-list\">\r\n");
      out.write("        ");
for (Product p : list) {
      out.write("\r\n");
      out.write("        <li>\r\n");
      out.write("            <h4>");
      out.print( p.getName());
      out.write("</h4>\r\n");
      out.write("            <div style=\"float: left;width: 45%\" class=\"logo\">\r\n");
      out.write("                <a title=\"產品說明\" href=\"javascript: get_product(");
      out.print( p.getId());
      out.write(")\">\r\n");
      out.write("                    <img alt=\"");
      out.print( p.getName());
      out.write("\" src=\"");
      out.print( p.getUrl() == null ? "images/phone.png" : p.getUrl());
      out.write("\">\r\n");
      out.write("                </a>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div style=\"float: right;width: 55%\">\r\n");
      out.write("                <a href=\"add_cart.do?pid=");
      out.print( p.getId());
      out.write("\" title=\"加入購物車\"><img src=\"images/cart.png\" alt=\"\"/></a>\r\n");
      out.write("                <div>\r\n");
      out.write("                    ");
if (p instanceof Outlet) {
      out.write("\r\n");
      out.write("                    原價: ");
      out.print( nf.format(((Outlet) p).getListPrice()));
      out.write("<br>\r\n");
      out.write("                    優惠: ");
      out.print( 100 - ((Outlet) p).getDiscount());
      out.write(" 折<br>\r\n");
      out.write("                    ");
}
      out.write("\r\n");
      out.write("                    售價:");
      out.print( nf.format(p.getUnitPrice()));
      out.write("\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </li>\r\n");
      out.write("        ");
}
      out.write("        \r\n");
      out.write("    </ul>\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("</div>\r\n");
      out.write("<div id=\"dialog\" title=\"產品說明\"></div>\r\n");
      out.write("<div id=\"aside\">aside</div>  \r\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/subviews/footer.jsp", out, false);
      out.write('\r');
      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
