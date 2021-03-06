/*
 * Copyright (c) 2006 Sun Microsystems, Inc.  All rights reserved.  U.S.
 * Government Rights - Commercial software.  Government users are subject
 * to the Sun Microsystems, Inc. standard license agreement and
 * applicable provisions of the FAR and its supplements.  Use is subject
 * to license terms.
 *
 * This distribution may include materials developed by third parties.
 * Sun, Sun Microsystems, the Sun logo, Java and J2EE are trademarks
 * or registered trademarks of Sun Microsystems, Inc. in the U.S. and
 * other countries.
 *
 * Copyright (c) 2006 Sun Microsystems, Inc. Tous droits reserves.
 *
 * Droits du gouvernement americain, utilisateurs gouvernementaux - logiciel
 * commercial. Les utilisateurs gouvernementaux sont soumis au contrat de
 * licence standard de Sun Microsystems, Inc., ainsi qu'aux dispositions
 * en vigueur de la FAR (Federal Acquisition Regulations) et des
 * supplements a celles-ci.  Distribue par des licences qui en
 * restreignent l'utilisation.
 *
 * Cette distribution peut comprendre des composants developpes par des
 * tierces parties. Sun, Sun Microsystems, le logo Sun, Java et J2EE
 * sont des marques de fabrique ou des marques deposees de Sun
 * Microsystems, Inc. aux Etats-Unis et dans d'autres pays.
 */


package filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import util.Counter;


public final class OrderFilter implements Filter {
    private FilterConfig filterConfig = null;
    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
    
    public void destroy() {
        this.filterConfig = null;
    }
    
    // This method gets called everythime ReceiptServlet gets accessed.
    // See filter mapping of the web.xml file.
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (filterConfig == null) {
            return;
        }
        
        // Render the generic servlet request properties
        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);
        ServletContext context = filterConfig.getServletContext();
        Counter counter = (Counter) context.getAttribute("orderCounter");
        HttpServletRequest hsr = (HttpServletRequest) request;
        HttpSession session = hsr.getSession();
        
        writer.println("=======================================================");
        
        // Log the resulting string
        writer.flush();
        System.out.println(sw.getBuffer().toString());
        chain.doFilter(request, response);
    }
    
    public String toString() {
        if (filterConfig == null) {
            return ("OrderFilter()");
        }
        
        StringBuffer sb = new StringBuffer("OrderFilter(");
        sb.append(filterConfig);
        sb.append(")");
        
        return (sb.toString());
    }
}
