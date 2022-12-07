package sg.ic.pg.rest.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.inject.Singleton;
import javax.servlet.http.Cookie;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sg.ic.pg.rest.model.RedirectUriResponse;
import sg.ic.pg.util.property.Constant;
import sg.ic.pg.util.property.Property;

@Singleton
@Path("redirect")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RedirectService extends BaseService {


    public RedirectService() {
        log = getLogger(this.getClass());
    }

    @GET
    public Response getRedirectUrl() {
        final String methodName = "getRedirectUrl";
        String redirectUri = getProperty(Property.APP_LOGIN_URL);

        for (Cookie cookie : httpServletRequest.getCookies()) {

            // Check for Cookie
            if (cookie.getName().equals(Constant.REDIRECT_COOKIE)) {

                byte[] byteArr = Base64.getDecoder().decode(cookie.getValue());

                redirectUri = new String(byteArr, StandardCharsets.UTF_8);
            }
        }

        log.info(methodName, "Redirect URL : " + redirectUri);

        return buildSuccessResponse(new RedirectUriResponse(redirectUri));
    }

}
