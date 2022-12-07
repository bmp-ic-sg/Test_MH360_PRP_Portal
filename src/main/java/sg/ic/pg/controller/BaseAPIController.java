package sg.ic.pg.controller;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;
import com.parkway.sdh.gw.encryption.model.Payload;
import sg.ic.pg.manager.APIEncryptionManager;
import sg.ic.pg.manager.AccessTokenManager;
import sg.ic.pg.util.http.model.HTTPContentType;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.json.JsonHelper;
import sg.ic.pg.util.property.Property;

public class BaseAPIController extends BaseController {


    @Inject
    private APIEncryptionManager apiEncryptionManager;

    public BaseAPIController() {
        // Empty Constructor
    }

    protected HTTPRequest buildAPIRequest(String base, String url) {
        return new HTTPRequest.Builder(base + url).setContentType(HTTPContentType.APPLICATION_JSON)
                .setAuthorization("Bearer " + getAccessToken()).build();
    }

    protected HTTPRequest buildAPIRequest(String url) {
        return buildAPIRequest(getProperty(Property.API_BASE_URL), url);
    }

    private String getAccessToken() {
        return AccessTokenManager.getInstance().getAccessToken();
    }

    protected <T> T parseResponse(HTTPResponse response, Class<T> clazz) {
        return JsonHelper.fromJson(response.getBody(), clazz);
    }

    protected void logRequest(String methodName, Object request) {
        log.debug(methodName, "Request: " + toJson(request));
    }

    protected void logResponse(String methodName, Object response) {
        log.debug(methodName, "Response :" + toJson(response));
    }

    protected void logResponse(String methodName, HTTPResponse response) {
        log.debug(methodName, "Response :" + response.getCode() + " : " + toJson(response.getBody()));
    }

    protected String toJson(Object obj) {
        return JsonHelper.toJson(obj);
    }

    protected <T> T fromResponse(HTTPResponse response, Class<T> clazz) {
        return JsonHelper.fromJson(response.getBody(), clazz);
    }

    protected boolean isSuccessResponse(HTTPResponse response) {
        return isStatus(response, Status.OK);
    }

    protected boolean isStatus(HTTPResponse response, Status status) {
        return response.getCode() == status.getStatusCode();
    }

    protected String encryptRequest(Object request) {
        return toJson(apiEncryptionManager.encrypt(toJson(request)));
    }

    protected <T> T decryptResponse(HTTPResponse response, Class<T> clazz) {

        if (response.getCode() == Status.OK.getStatusCode() || response.getCode() == Status.CREATED.getStatusCode()) {
            Payload payload = fromResponse(response, Payload.class);
            return JsonHelper.fromJson(apiEncryptionManager.decrypt(payload), clazz);
        } else {

            return fromResponse(response, clazz);
        }
    }

}
