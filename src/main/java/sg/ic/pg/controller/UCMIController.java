package sg.ic.pg.controller;

import sg.ic.pg.controller.model.BaseAPIResponse;
import sg.ic.pg.controller.model.UCMICreateRequest;
import sg.ic.pg.model.User;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.property.Property;

@Controller
public class UCMIController extends BaseAPIController {

    public UCMIController() {
        log = getLogger(this.getClass());
    }

    public boolean create(User user) {
        final String methodName = "create";
        boolean result = false;
        start(methodName);

        UCMICreateRequest request = new UCMICreateRequest(user);

        String encryptedRequest = encryptRequest(request);

        logRequest(methodName, request);

        HTTPRequest httpRequest = buildAPIRequest(getProperty(Property.API_UCMI_CREATE_URL));

        log.debug(methodName, "URL : " + httpRequest.getUrl());

        HTTPResponse httpResponse = HTTPClient.post(httpRequest, encryptedRequest);

        BaseAPIResponse response = decryptResponse(httpResponse, BaseAPIResponse.class);

        logResponse(methodName, response);

        result = isSuccessResponse(httpResponse);

        completed(methodName);
        return result;
    }
}
