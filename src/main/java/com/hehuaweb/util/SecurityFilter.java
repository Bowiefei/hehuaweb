package com.hehuaweb.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mixueqiang
 * @since Oct 12, 2014
 */
public class SecurityFilter implements Filter {
  private static final Log LOG = LogFactory.getLog(SecurityFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

//    String requestUri = req.getRequestURI();
//    System.out.println("requestUri:"+requestUri);
//    if (StringUtils.startsWith(requestUri, "/admin")) {
//      String email = (String)req.getSession().getAttribute("_email");
//      System.out.println("_email:"+email);
//
//      if (email == null) {
//        resp.sendRedirect(getSigninURL(requestUri, req.getQueryString()));
//      }
//    }

    chain.doFilter(request, response);
  }
  
  public String getSigninURL(String requestUri, String queryString) {

      return "/";
  }
  
  @Override
  public void destroy() {
  }

}
