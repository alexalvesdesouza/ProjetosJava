package br.com.lufamador.filter;

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
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

  private final Log logger = LogFactory.getLog(this.getClass());

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    logger.info("---> LufAmador api | Cors Load");
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

    HttpServletResponse response = (HttpServletResponse) resp;
    HttpServletRequest request = (HttpServletRequest) req;

    String origin = request.getHeader("Origin");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Origin", origin != null && origin.contains("ws") ? "" : origin);
    response.setHeader("Vary", "Origin");
    response.setHeader("Access-Control-Allow-Methods", "*");
    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Authorization, Content-Type, Accept, X-CSRF-TOKEN");
    response.setHeader("Access-Control-Max-Age", "3600");
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
