package sg.ic.pg.rest.service;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("sessions")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionService extends BaseService {

    public SessionService() {
        log = getLogger(this.getClass());
    }

    @GET
    @Path("clear")
    public Response clearSession() {
        final String methodName = "clearSession";
        start(methodName);

        // Clear old sessions
        if (getSession() != null) {
            getSession().invalidate();
        }
        setTrackingID();
        completed(methodName);
        return buildSuccessResponse();

    }

    @GET
    @Path("reset")
    public Response resetSession() {
        final String methodName = "resetSession";
        start(methodName);

        // Clear old sessions
        if (getSession() != null) {
            getSession().invalidate();
        }
        setTrackingID();
        completed(methodName);
        return buildSuccessResponse();

    }

}
