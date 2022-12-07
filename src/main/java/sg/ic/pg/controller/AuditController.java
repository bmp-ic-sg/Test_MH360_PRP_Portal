package sg.ic.pg.controller;

import sg.ic.pg.controller.model.AuditEvent;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPContentType;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.property.Property;

@Controller
public class AuditController extends BaseAPIController {

    public AuditController() {
        log = getLogger(this.getClass());
    }

    public void audit(AuditEvent event) {
        final String methodName = "audit";
        start(methodName);
        if (getBoolProperty(Property.AUDIT_ENABLED)) {
            HTTPRequest httpRequest = new HTTPRequest.Builder(getProperty(Property.AUDIT_URL))
                    .setContentType(HTTPContentType.APPLICATION_JSON).build();

            HTTPClient.post(httpRequest, toJson(event));

        } else {
            log.debug(methodName, toJson(event));
        }
        completed(methodName);
    }

}
