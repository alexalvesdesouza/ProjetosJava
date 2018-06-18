package br.com.helpdesk.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

  private final Log logger = LogFactory.getLog(this.getClass());

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("HelpDesk api | Cors Load");
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

    HttpServletResponse response = (HttpServletResponse) resp;
    HttpServletRequest request = (HttpServletRequest) req;


    response.setHeader("Access-Controll-Allow-Origin", "*");
    response.setHeader("Access-Controll-Allow-Methods", "POST, GET, DELETE, OPTIONS, PUT");
    response.setHeader("Access-Controll-Max-Age", "3600");
    response.setHeader("Access-Controll-Allow-Headers", "x-request-with, authorization, Content-Type, Authorization");

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      chain.doFilter(req, resp);
    }

  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub

  }

}
