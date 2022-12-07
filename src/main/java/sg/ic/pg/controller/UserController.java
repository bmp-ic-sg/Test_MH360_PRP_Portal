package sg.ic.pg.controller;

import sg.ic.pg.controller.model.CreateUserRequest;
import sg.ic.pg.controller.model.CreateUserResponse;
import sg.ic.pg.controller.model.USSUser;
import sg.ic.pg.controller.model.ValidateEmailRequest;
import sg.ic.pg.model.User;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.property.Property;

@Controller
public class UserController extends BaseAPIController {

    public UserController() {
        log = getLogger(this.getClass());
    }

    public boolean create(User user) {
        final String methodName = "create";
        boolean result = false;
        start(methodName);

        CreateUserRequest request = new CreateUserRequest(user);

        String encryptedRequest = encryptRequest(request);

        logRequest(methodName, request);

        HTTPRequest httpRequest = buildAPIRequest(getProperty(Property.API_USERS_CREATE_URL));

        HTTPResponse httpResponse = HTTPClient.post(httpRequest, encryptedRequest);

        CreateUserResponse response = decryptResponse(httpResponse, CreateUserResponse.class);

        logResponse(methodName, response);

        result = isSuccessResponse(httpResponse);

        completed(methodName);
        return result;
    }

    public boolean validateEmail(String email) {
        final String methodName = "validateEmail";
        boolean result = false;
        start(methodName);

        ValidateEmailRequest request = new ValidateEmailRequest(new USSUser("uid", email));

        logRequest(methodName, request);

        HTTPRequest httpRequest = buildAPIRequest(getProperty(Property.API_EMAIL_VALIDATE_URL));

        HTTPResponse httpResponse = HTTPClient.post(httpRequest, toJson(request));

        logResponse(methodName, httpResponse);

        result = isSuccessResponse(httpResponse);

        completed(methodName);
        return result;

    }
}
