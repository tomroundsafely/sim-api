package com.rs.sim.controller;

/**
 * This set of constants helps determine the ordering of interceptors applied to incoming calls.
 *
 * <p>All interceptors should be annotated as follows: <code>
 * @Component
 * @Order(InterceptorOrdering.INTERCEPTOR_NAME)
 * </code>
 */
public abstract class InterceptorOrdering {
  public static final int METRICS = 1;
  public static final int IMPERSONATION = 2;
  public static final int BEARER_AUTHENTICATION = 3;
  public static final int AUTHENTICATION = 4;
  public static final int FEATURE_FLAGS = 5;
  public static final int MDC_PROPERTIES = 6;
  public static final int ROLE_REQUEST = 7;
  public static final int PATH_ORG_VARIABLE = 8;
  public static final int PATH_MAKER_VARIABLE = 9;
}
