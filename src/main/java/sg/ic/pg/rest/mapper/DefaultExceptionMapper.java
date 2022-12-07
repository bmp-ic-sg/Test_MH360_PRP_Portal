package sg.ic.pg.rest.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import sg.ic.pg.rest.model.ServiceResponse;
import sg.ic.pg.util.log.AppLogger;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

    private AppLogger log;

    public DefaultExceptionMapper() {
        log = new AppLogger(this.getClass());
    }

    @Override
    public Response toResponse(Throwable ex) {
        log.error("toResponse", ex);
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ServiceResponse(Status.INTERNAL_SERVER_ERROR))
                .type(MediaType.APPLICATION_JSON).build();
    }

}
