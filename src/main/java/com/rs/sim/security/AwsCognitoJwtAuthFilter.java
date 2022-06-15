package com.rs.sim.security;

import jakarta.servlet.*;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AwsCognitoJwtAuthFilter extends GenericFilter {

  private static final Log logger = LogFactory.getLog(AwsCognitoJwtAuthFilter.class);

  public AwsCognitoJwtAuthFilter() {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    Authentication authentication;

    // TODO Get bearer token or SecurityContextHolder.getContext().getAuthentication()
    // TODO Fetch email and validdate against db

    chain.doFilter(request, response);
  }
}
