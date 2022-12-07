package sg.ic.pg.controller;

import javax.ws.rs.core.Response.Status;
import sg.ic.pg.controller.model.BaseAPIResponse;
import sg.ic.pg.controller.model.DigiHealthCreateRequest;
import sg.ic.pg.model.User;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.property.Property;

@Controller
public class DigiHealthController extends BaseAPIController {

    public DigiHealthController() {
        log = getLogger(this.getClass());
    }

    public boolean create(User user) {
        final String methodName = "create";
        boolean result = false;
        start(methodName);

        DigiHealthCreateRequest request = new DigiHealthCreateRequest(user);

        String encryptedRequest = encryptRequest(request);

        logRequest(methodName, request);

        HTTPRequest httpRequest = buildAPIRequest(getProperty(Property.API_DIGIHEALTH_CREATE_URL));

        log.debug(methodName, "URL : " + httpRequest.getUrl());

        HTTPResponse httpResponse = HTTPClient.post(httpRequest, encryptedRequest);

        BaseAPIResponse response = decryptResponse(httpResponse, BaseAPIResponse.class);

        logResponse(methodName, response);

        result = isStatus(httpResponse, Status.CREATED); 
        completed(methodName);
        return result;
    }
}
