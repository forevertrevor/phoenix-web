package com.mws.phoenix.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;
import java.io.IOException;

/**
 * Filter that wraps the response object in a gzip enabled wrapper to
 * zip response to the client.
 */
public class GzipFilter implements Filter {

  public void init(FilterConfig parm1) throws javax.servlet.ServletException {
    // nothing to do
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest) {
      HttpServletRequest req = (HttpServletRequest) request;
      HttpServletResponse resp = (HttpServletResponse) response;
      String ae = req.getHeader("Accept-Encoding");
      if (null != ae && ae.indexOf("gzip") != -1) {
        GzipResponseWrapper wrap = new GzipResponseWrapper(resp);
        chain.doFilter(request,wrap);
        wrap.complete();
        return;
      }
    }
    chain.doFilter(request, response);
  }

  public void destroy() {
    // nothing to do
  }

}