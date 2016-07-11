package dingwen.jersey.filter;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dingwen.jersey.filter.annotation.LoginAuth;

@Provider
@LoginAuth
public class LoginAuthFilter implements ContainerRequestFilter {

	private static final Logger log = LogManager
			.getLogger(LoginAuthFilter.class);

	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;

	@Override
	public void filter(ContainerRequestContext requestContext) {
		if (request.getSession().getAttribute("userID") == null) {
			URI otherTargetUri = UriBuilder.fromUri(request.getContextPath() + "/login.jsp").build();
			requestContext.abortWith(Response.temporaryRedirect(otherTargetUri).build());
		}
		/*if (request.getSession().getAttribute("userID") == null) {
		  requestContext.abortWith(Response
                  .status(Response.Status.UNAUTHORIZED)
                  .entity("User cannot access the resource.")
                  .build());
		}*/
	}
}
