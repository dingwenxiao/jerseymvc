package dingwen.jersey.filter;

import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dingwen.jersey.filter.annotation.BasicAuth;
import dingwen.jersey.utilities.BasicAuthHelper;

/**
 * authorization filter
 * 
 * @author dixiao alternative way is following
 *
 *         Root REST resource class.
 *
 @ApplicationPath("/rest") public class RootResource extends ResourceConfig {
 * 
 *                           // Initializes all resources from REST package.
 * 
 *                           public RootResource() {
 *                           packages("com.example.rest");
 *                           register(AuthRequestFilter.class); } }
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
@BasicAuth
public class AuthorizationRequestFilter implements ContainerRequestFilter {

	private static final Logger log = LogManager
			.getLogger(AuthorizationRequestFilter.class);

	@Context
	HttpServletRequest webRequest;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		final Request rq = requestContext.getRequest();

		/*
		 * final SecurityContext securityContext = requestContext
		 * .getSecurityContext(); if (securityContext == null ||
		 * !securityContext.isUserInRole("privileged")) {
		 * 
		 * requestContext.abortWith(Response
		 * .status(Response.Status.UNAUTHORIZED)
		 * .entity("User cannot access the resource.").build()); }
		 */

		try {
			if ((webRequest.getSession().getAttribute("userId") == null)
					&& !BasicAuthHelper.checkHeaderAuth(webRequest)) {
				// Not allowed, so report he's unauthorized
				log.info("unauthorized visit");
				requestContext.abortWith(Response
						.status(Response.Status.UNAUTHORIZED)
						.header("WWW-Authenticate",
								"BASIC realm=\"my-default-reaml\"").build());
			}
		} catch (IOException e) {
			log.error(e);
		}
	}
}
