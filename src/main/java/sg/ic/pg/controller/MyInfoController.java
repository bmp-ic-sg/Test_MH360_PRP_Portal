package sg.ic.pg.controller;

import java.util.UUID;
import sg.ic.pg.controller.model.myinfo.MyInfoPersonRequest;
import sg.ic.pg.controller.model.myinfo.MyInfoPersonResponse;
import sg.ic.pg.controller.model.myinfo.MyInfoModel;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPParameter;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.property.Property;

@Controller
public class MyInfoController extends BaseAPIController {

    public MyInfoController() {
        log = getLogger(this.getClass());
    }

    public String authorize() {
        final String methodName = "authorize";
        start(methodName);

        HTTPRequest httpRequest = buildAPIRequest(getProperty(Property.API_MYINFO_AUTH_URL));
        HTTPParameter parameters = new HTTPParameter();

        parameters.addParameter("attributes", getProperty(Property.MYINFO_ATTRIBUTES));
        parameters.addParameter("purpose", getProperty(Property.MYINFO_PURPOSE));
        parameters.addParameter("redirect_uri", getProperty(Property.MYINFO_REDIRECT_URI));
        parameters.addParameter("state", UUID.randomUUID().toString());

        HTTPResponse httpResponse = HTTPClient.get(httpRequest, parameters);

        String location = httpResponse.getHeader("location");

        log.debug(methodName, "Location : " + location);

        completed(methodName);
        return location;
    }

    public MyInfoModel getPersonInfo(String code) {
        final String methodName = "getPersonInfo";
        start(methodName);

        MyInfoPersonRequest request = new MyInfoPersonRequest();
        request.setAuthCode(code);
        request.setRedirectUri(getProperty(Property.MYINFO_REDIRECT_URI));

        logRequest(methodName, request);

        String encryptedRequest = encryptRequest(request);

        HTTPRequest httpRequest = buildAPIRequest(getProperty(Property.API_MYINFO_PERSON_URL));

        HTTPResponse httpResponse = HTTPClient.post(httpRequest, encryptedRequest);

        logResponse(methodName, httpResponse);

        MyInfoPersonResponse response = decryptResponse(httpResponse, MyInfoPersonResponse.class);

        log.debug(methodName, response);

        completed(methodName);
        return response.getUser();

    }



}
