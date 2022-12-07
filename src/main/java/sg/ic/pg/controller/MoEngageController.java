package sg.ic.pg.controller;

import sg.ic.pg.model.MoEngageRequest;
import sg.ic.pg.model.User;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPContentType;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.json.JsonHelper;
import sg.ic.pg.util.property.Property;

@Controller
public class MoEngageController extends BaseAPIController {

    public MoEngageController() {
        log = getLogger(this.getClass());
    }

    public boolean create(User user) {
        final String methodName = "create";
        start(methodName);
        MoEngageRequest payload = convertUser(user);
        logRequest(methodName, payload);

        String uri = getProperty(Property.MOENGAGE_CREATE_USER_URL);

        HTTPRequest request =
                new HTTPRequest.Builder(uri)
                        .setContentType(HTTPContentType.APPLICATION_JSON)
                        .addHeader(getProperty(Property.MOENGAGE_KEY_HEADER), getProperty(Property.MOENGAGE_KEY_VALUE))
                        .build();

        HTTPResponse httpResponse = HTTPClient.post(request, JsonHelper.toJson(payload));

        logResponse(methodName, httpResponse);

        boolean result = httpResponse.getCode() == 200;
        completed(methodName);
        return result;
    }

    private MoEngageRequest convertUser(User user) {

        MoEngageRequest request = new MoEngageRequest();

        request.setFirstName(user.getFirstName());
        if (!user.getLastName().trim().isEmpty()) {
            request.setLastName(user.getLastName());
        }

        request.setEmail(user.getEmail());
        request.setGender(user.getGender());
        request.setDob(user.getDob());
        request.setNationality(user.getNationality());

        request.setCountry(user.getCountry());
        request.setMobileCountryCode(user.getMobileCountryCode());
        request.setMobileNo(user.getMobileNo());
        request.setConsentMarketing(user.isConsentMarketing());
        request.setMarketingSmsFlag(user.isMarketingSmsFlag());
        request.setType("app");

        return request;
    }
}
