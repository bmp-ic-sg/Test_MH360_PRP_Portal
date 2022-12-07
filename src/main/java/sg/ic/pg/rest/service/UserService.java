package sg.ic.pg.rest.service;

import sg.ic.pg.controller.*;
import sg.ic.pg.model.User;
import sg.ic.pg.rest.model.ValidateEmailRequest;
import sg.ic.pg.rest.validator.UserValidator;
import sg.ic.pg.util.json.JsonHelper;
import sg.ic.pg.util.property.AuditConstant;
import sg.ic.pg.util.property.Constant;
import sg.ic.pg.util.property.LogConstant;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("users")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class UserService extends BaseService {

    @Inject
    private UserController userController;

    @Inject
    private SCIMController scimController;

    @Inject
    private UCMIController ucmiController;

    @Inject
    private DigiHealthController dhController;

    @Inject
    private MoEngageController meController;

    @Inject
    private CaptchaController captchaController;

    @Inject
    private UserValidator validator;

    public UserService() {
        log = getLogger(this.getClass());
    }

    @POST
    @Path("submit")
    public Response submit() {
        final String methodName = "createUser";
        Response response;
        start(methodName);

        boolean hasUserSession = hasSessionAttribute(Constant.SESSION_USER_DETAILS);
        log.debug(methodName, LogConstant.SESSION_USER_MSG +hasUserSession);
        if (!hasUserSession) {
            completed(methodName);
            return buildBadRequestResponse();
        }
        User userRequest = getSessionAttribute(Constant.SESSION_USER_DETAILS, User.class);

        boolean valid = validator.validate(userRequest);
        log.debug(methodName, LogConstant.PAYLOAD_VALIDATION_MSG + valid);
        if (!valid) {
            audit(AuditConstant.ACTION_CREATE_USER, JsonHelper.toJson(userRequest), false);
            completed(methodName);
            return buildBadRequestResponse();
        }

        // Set UID
        userRequest.setUid(generateUid());

        // Validate Email Address
        boolean result = userController.validateEmail(userRequest.getEmail());

        if (!result) {
            String conflictMsg = "Duplicate Email Address  : " + userRequest.getEmail();
            log.debug(methodName, conflictMsg);
            audit(AuditConstant.ACTION_CREATE_USER, conflictMsg, false);
            return buildConflictResponse();
        } else {

            // if Retrieve from MyInfo, key fields are set by MyInfo
            String auditMsg = AuditConstant.AUDIT_LDAP_CREATE_MESSAGE;
            if (hasSessionAttribute(Constant.SESSION_REG_MYINFO)) {
                boolean isMyInfo = getSessionAttribute(Constant.SESSION_REG_MYINFO, Boolean.class);
                if (isMyInfo) {
                    auditMsg = "[MYINFO] " + auditMsg;
                }
            } else {
                auditMsg = "[MANUAL] " +auditMsg;
            }
            result = userController.create(userRequest);
            log.debug(methodName, "LDAP create user : " +result);

            final String trackingId = getTrackingID();

            // Create User in Gluu & UCMI only if created in LDAP successfully
            if (result) {
                executor.execute(() -> {

                    boolean scimResult = scimController.create(userRequest);
                    String scimAuditMessage = auditMessageBuilder(scimResult, AuditConstant.AUDIT_SCIM_CREATE_MESSAGE, userRequest);
                    audit(trackingId, AuditConstant.ACTION_CREATE_USER_SCIM, scimAuditMessage, scimResult);
                    log.debug(methodName, "SCIM create user : " +scimResult);

                    boolean ucmiResult = ucmiController.create(userRequest);
                    String ucmiAuditMessage = auditMessageBuilder(ucmiResult, AuditConstant.AUDIT_UCMI_CREATE_MESSAGE, userRequest);

                    audit(trackingId, AuditConstant.ACTION_CREATE_USER_UCMI, ucmiAuditMessage, ucmiResult);
                    log.debug(methodName, "UCMI create user : " +ucmiResult);

                    boolean dhResult = dhController.create(userRequest);
                    String dhAuditMessage = auditMessageBuilder(dhResult, AuditConstant.AUDIT_DIGIHEALTH_CREATE_MESSAGE, userRequest);
                    audit(trackingId, AuditConstant.ACTION_CREATE_USER_DIGIHEALTH, dhAuditMessage, dhResult);
                    log.debug(methodName, "SDH create user : " +dhResult);

                    boolean meResult = meController.create(userRequest);
                    String meAuditMessage = auditMessageBuilder(meResult, AuditConstant.AUDIT_MOENGAGE_CREATE_MESSAGE, userRequest);
                    audit(trackingId, AuditConstant.ACTION_CREATE_USER_MOENGAGE, meAuditMessage, meResult);
                    log.debug(methodName, "MoEngage create user : " +meResult);

                });
                auditMsg = auditMessageBuilder(true, auditMsg, userRequest);
                audit(AuditConstant.ACTION_CREATE_USER, auditMsg, true);
                response = buildSuccessResponse();
            } else {
                auditMsg = auditMessageBuilder(false, auditMsg, userRequest);
                audit(AuditConstant.ACTION_CREATE_USER, auditMsg, false);
                response = buildErrorResponse();
            }
        }

        completed(methodName);
        return response;

    }

    @POST
    @Path("email/validate")
    public Response validateEmail(ValidateEmailRequest request) {
        final String methodName = "validateEmail";
        Response response;
        start(methodName);
        log.debug(methodName, JsonHelper.toJson(request));

        boolean validaPayload = validator.validate(request);
        log.debug(methodName, LogConstant.PAYLOAD_VALIDATION_MSG + validaPayload);

        if (!validaPayload) {
            completed(methodName);
            return buildBadRequestResponse();
        } else {
            boolean result = userController.validateEmail(request.getEmail());
            if (result) {
                log.debug(methodName, LogConstant.EMAIL_VALIDATION + false);
                response = buildSuccessResponse();
            } else {
                log.debug(methodName, LogConstant.EMAIL_VALIDATION + true);
                response = buildConflictResponse();
            }
            String auditResult = result ? AuditConstant.AUDIT_RESULT_SUCCESS : AuditConstant.AUDIT_RESULT_FAILED;
            String auditMsg = AuditConstant.AUDIT_VALIDATE_EMAIL_MESSAGE
                    .replace("{email}", request.getEmail())
                    .replace("{result}", auditResult);

            audit(AuditConstant.ACTION_VALIDATE_EMAIL,auditMsg, result);
        }
        completed(methodName);
        return response;
    }
    
    @POST
    @Path("store")
    public Response store(User request) {
        final String methodName = "store";
        Response response = buildBadRequestResponse();
        start(methodName);

        log.debug(methodName, JsonHelper.toJson(request));

        boolean validaPayload = validator.validate(request);
        log.debug(methodName, LogConstant.PAYLOAD_VALIDATION_MSG + validaPayload);

        if (validaPayload) {
            //validate email
            boolean result = userController.validateEmail(request.getEmail());
            log.debug(methodName, LogConstant.EMAIL_VALIDATION + result);
            if (result) {

                // Captcha verify
                boolean valid = captchaController.verifyCaptcha(request.getToken());
                log.debug(methodName, LogConstant.TOKEN_VALIDATION_MSG + valid);
                if (valid) {
                    setSessionAttribute(Constant.SESSION_USER_DETAILS, request);
                    setSessionAttribute(Constant.SESSION_MOBILE_NO, request.getMobileNo());
                    setSessionAttribute(Constant.SESSION_MOBILE_COUNTRY_CODE, request.getMobileCountryCode());
                    response = buildSuccessResponse();
                } else {
                    response = buildForbiddenResponse();
                }
            } else {
                response = buildConflictResponse();
            }
        }
        completed(methodName);
        return response;
    }

    private String generateUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    private String auditMessageBuilder(boolean result, String message, User user)
    {
        String auditResult = result ? AuditConstant.AUDIT_RESULT_SUCCESS : AuditConstant.AUDIT_RESULT_FAILED;
        return message
                .replace("{result}", auditResult)
                .replace("{email}", user.getEmail())
                .replace("{userId}", user.getUid());
    }
}
