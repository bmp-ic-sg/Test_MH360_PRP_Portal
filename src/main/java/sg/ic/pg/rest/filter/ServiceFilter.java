package sg.ic.pg.rest.filter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;
import sg.ic.pg.rest.model.ServiceResponse;
import sg.ic.pg.util.property.Constant;

@Provider
public class ServiceFilter implements ContainerRequestFilter {


    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest httpServletRequest;

    private static final ResponseBuilder ACCESS_DENIED =
            Response.status(Response.Status.UNAUTHORIZED).entity(new ServiceResponse(Response.Status.UNAUTHORIZED));

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if (hasSecurityAnnotations(resourceInfo, PermitAll.class)
                || hasSecurityAnnotations(resourceInfo, RolesAllowed.class)) {

            if (hasSession(httpServletRequest)) {
                final HttpSession session = httpServletRequest.getSession(false);

                if (hasSecurityAnnotations(resourceInfo, RolesAllowed.class)
                        && !hasRoleRequirements(resourceInfo, session)) {
                    requestContext.abortWith(ACCESS_DENIED.build());
                }

            } else { // Requester does not have session
                requestContext.abortWith(ACCESS_DENIED.build());
            }
        }
    }

    private boolean hasRoleRequirements(ResourceInfo resourceInfo, HttpSession session) {
        Method method = resourceInfo.getResourceMethod();
        boolean result = false;
        RolesAllowed rolesAllowed;

        if (method.isAnnotationPresent(RolesAllowed.class)) {
            rolesAllowed = method.getAnnotation(RolesAllowed.class);
        } else {
            Class<?> clazz = resourceInfo.getResourceClass();
            rolesAllowed = clazz.getAnnotation(RolesAllowed.class);
        }

        for (String role : rolesAllowed.value()) {
            if (role.equals(Constant.ROLE_USER_VERIFIED)) {
                // Get User's Verification status
                boolean verified = (boolean) session.getAttribute(Constant.SESSION_MOBILE_VERFIED);

                result = verified;
            }
        }
        return result;
    }

    private boolean hasSession(HttpServletRequest request) {
        return null != request.getSession(false);
    }

    private boolean hasSecurityAnnotations(ResourceInfo resourceInfo, Class<? extends Annotation> securityClass) {
        Method method = resourceInfo.getResourceMethod();
        Class<?> clazz = resourceInfo.getResourceClass();
        return method.isAnnotationPresent(securityClass) || clazz.isAnnotationPresent(securityClass);
    }

}
