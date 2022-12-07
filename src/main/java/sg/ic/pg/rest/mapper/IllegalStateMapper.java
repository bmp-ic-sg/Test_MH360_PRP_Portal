package sg.ic.pg.rest.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import sg.ic.pg.rest.model.ServiceResponse;

@Provider
public class IllegalStateMapper implements ExceptionMapper<IllegalStateException> {

    @Override
    public Response toResponse(IllegalStateException exception) {
        return Response.status(Status.NOT_ACCEPTABLE).entity(new ServiceResponse(Status.NOT_ACCEPTABLE)).build();
    }
}
