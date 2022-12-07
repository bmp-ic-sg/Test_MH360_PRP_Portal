package sg.ic.pg.controller;

import javax.ws.rs.core.Response.Status;
import sg.ic.pg.controller.model.scim.SCIMEmail;
import sg.ic.pg.controller.model.scim.SCIMExtension;
import sg.ic.pg.controller.model.scim.SCIMName;
import sg.ic.pg.controller.model.scim.SCIMModel;
import sg.ic.pg.model.User;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.property.Property;

@Controller
public class SCIMController extends BaseAPIController {

    public SCIMController() {
        log = getLogger(this.getClass());
    }

    public boolean create(User user) {
        final String methodName = "create";
        boolean result = false;
        start(methodName);

        SCIMModel scimUser = convertUser(user);

        HTTPRequest httpRequest =
                buildAPIRequest(getProperty(Property.SCIM_BASE_URL), getProperty(Property.SCIM_USERS_URL));

        logRequest(methodName, scimUser);

        HTTPResponse response = HTTPClient.post(httpRequest, toJson(scimUser));

        logResponse(methodName, response);

        result = isStatus(response, Status.CREATED);

        completed(methodName);
        return result;
    }

    private SCIMModel convertUser(User user) {

        SCIMModel scimUser = new SCIMModel();

        // UID
        scimUser.setUserName(user.getUid());

        // Name
        SCIMName name = new SCIMName(user.getLastName());
        scimUser.setName(name);

        // Email
        SCIMEmail email = new SCIMEmail(user.getEmail(), true);
        scimUser.addEmail(email);

        // Active
        scimUser.setActive(true);

        // Extension
        SCIMExtension extension = new SCIMExtension();
        extension.setEmployeeType(user.getEmployeeType());
        extension.setDob(user.getDob());
        extension.setGender(user.getGender());
        extension.setMobile(user.getMobileNo());
        extension.setId(user.getId());
        extension.setFirstName(user.getFirstName());
        extension.setMobileCountryCode(user.getMobileCountryCode());
        extension.setCountry(user.getCountry());
        extension.setNationality(user.getNationality());

        scimUser.setExtension(extension);

        return scimUser;
    }

}
