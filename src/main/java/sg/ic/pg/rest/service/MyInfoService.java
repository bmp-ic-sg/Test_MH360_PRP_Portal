package sg.ic.pg.rest.service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sg.ic.pg.controller.MyInfoController;
import sg.ic.pg.controller.model.myinfo.MyInfoModel;
import sg.ic.pg.model.MyInfoUser;
import sg.ic.pg.rest.model.MyInfoUserResponse;
import sg.ic.pg.rest.validator.MyInfoValidator;
import sg.ic.pg.util.property.AuditConstant;
import sg.ic.pg.util.property.Constant;
import sg.ic.pg.util.property.Property;

@Path("myinfo")
@Singleton
public class MyInfoService extends BaseService {

    @Inject
    private MyInfoController controller;

    @Inject
    private MyInfoValidator validator;

    public MyInfoService() {
        log = getLogger(this.getClass());
    }

    @GET
    @Path("authorize")
    public Response authorize() {
        final String methodName = "getMyInfoAuthorize";
        Response response;
        start(methodName);
        String url = controller.authorize();
        response = buildRedirectResponse(url);
        audit(AuditConstant.ACTION_MYINFO_AUTHORIZE, "Get MyInfo Authorization URL", true);
        completed(methodName);
        return response;
    }

    @GET
    @Path("callback")
    public Response callback(@QueryParam("code") String code) {
        final String methodName = "MyInfoCallback";
        Response response =
                buildRedirectResponse(getProperty(Property.APP_CONTEXT_FQDN) + getProperty(Property.APP_CONTEXT_PATH));
        boolean result = false;
        start(methodName);

        if (validator.validate(code)) {
            MyInfoModel model = controller.getPersonInfo(code);

            if (null != model) {
                MyInfoUser user = convertMyInfoModel(model);

                result = true;

                // Set Session with User
                setSessionAttribute(Constant.SESSION_REG_MYINFO, true);
                setSessionAttribute(Constant.SESSION_REG_MYINFO_USER, user);
                response = buildRedirectResponse(getProperty(Property.APP_CONTEXT_FQDN)
                        + getProperty(Property.APP_CONTEXT_PATH) + getProperty(Property.APP_CONTEXT_REGISTRATION_PATH));
                audit(AuditConstant.ACTION_MYINFO_RETRIEVE, "MyInfo user found "+ user.getName() +" : "+user.getId(), true);
            } else {
                log.debug(AuditConstant.ACTION_MYINFO_RETRIEVE, "Could Not Retrieve Model, redirecting back to index");
            }
        } else {
            log.debug(AuditConstant.ACTION_MYINFO_RETRIEVE, "Could Not Retrieve SP Code, redirecting back to index");
        }

        if (!result) {
            response = buildRedirectResponseWithErrorMsg(getProperty(Property.APP_CONTEXT_FQDN) + getProperty(Property.APP_CONTEXT_PATH), "No User Retrieved");
            setSessionAttribute(Constant.SESSION_REG_MYINFO, false);
            setSessionAttribute(Constant.SESSION_REG_MYINFO_USER, null);
            audit(AuditConstant.ACTION_MYINFO_RETRIEVE, "No User Retrieved", false);
        }

        completed(methodName);
        return response;
    }

    private MyInfoUser convertMyInfoModel(MyInfoModel model) {
        MyInfoUser user = new MyInfoUser();

        user.setName(model.getName().getValue());
        user.setDob(model.getDob().getValue());
        user.setGender(model.getSex().getDescription().toLowerCase());
        user.setNationality(model.getNationality().getDescription());
        user.setId(model.getUinfin().getValue());
        user.setEmail(model.getEmail().getValue());
        user.setMobileNo(model.getMobileNo().getNumber().getValue());
        user.setMobileCountryCode(model.getMobileNo().getAreaCode().getValue());
        return user;
    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonInfo() {
        final String methodName = "getPersonInfo";
        Response response;
        start(methodName);
        MyInfoUserResponse objResponse = new MyInfoUserResponse();

        if (hasSessionAttribute(Constant.SESSION_REG_MYINFO)) {

            objResponse.setUser(getSessionAttribute(Constant.SESSION_REG_MYINFO_USER, MyInfoUser.class));
            objResponse.setMyInfo(true);
        } else {
            objResponse.setMyInfo(false);
        }

        response = buildSuccessResponse(objResponse);
        completed(methodName);
        return response;
    }
}
