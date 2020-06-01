/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.totalbuy.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
public class PerformanceFilter implements Filter {

    private String prefix = "Performace: ";
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        String prefix = filterConfig.getInitParameter("prefix");
        if (prefix != null) {
            this.prefix = prefix;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long begin = System.currentTimeMillis();

        chain.doFilter(request, response);

        long end = System.currentTimeMillis();

        StringBuilder msg = new StringBuilder(prefix);
        msg.append(", url- ");
        msg.append(((HttpServletRequest) request).getRequestURL());
        msg.append(", ");
        msg.append(end - begin);
        msg.append(" ms.");

        Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");
        if (ex != null) {
            msg.append(" cause: ");
            msg.append(ex.toString());
        }

        if (ex == null) {
            this.filterConfig.getServletContext().log(msg.toString());
        } else {
            this.filterConfig.getServletContext().log(msg.toString(), ex);
        }
    }

    @Override
    public void destroy() {
    }
}
