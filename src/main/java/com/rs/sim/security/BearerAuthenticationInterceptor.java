package com.rs.sim.security;

import com.rs.sim.controller.InterceptorOrdering;
import com.rs.sim.exception.BearerAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Order(InterceptorOrdering.BEARER_AUTHENTICATION)
public class BearerAuthenticationInterceptor implements HandlerInterceptor {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(BearerAuthenticationInterceptor.class);

  @Autowired
  public BearerAuthenticationInterceptor() {}

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws BearerAuthenticationException {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }

    final HandlerMethod handlerMethod = (HandlerMethod) handler;
    final boolean authenticated =
        handlerMethod.getMethod().getAnnotation(AuthenticateBearerToken.class) != null;

    if (!authenticated) {
      LOGGER.debug("Request is not authenticated: {}", request.getRequestURI());
      return true;
    }

    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null) {
      final var message =
          String.format("Request does not contain bearer token: %s", request.getRequestURI());
      LOGGER.warn(message);

      throw new BearerAuthenticationException(message);
    }

    //    try {
    //      String token = authHeader.substring("Bearer".length()).trim();
    //      if (!token.equals(expectedBearerToken)) {
    //        final var message = "Invalid bearer token";
    //        LOGGER.warn(message);
    //
    //        throw new BearerAuthenticationException(message);
    //      }
    //    } catch (BearerAuthenticationException ex) {
    //      throw ex;
    //    } catch (Exception ex) {
    //      final var message = "Invalid bearer token";
    //      LOGGER.warn(message);
    //
    //      throw new BearerAuthenticationException(message);
    //    }

    LOGGER.debug("Authentication completed");

    return true;
  }
}
