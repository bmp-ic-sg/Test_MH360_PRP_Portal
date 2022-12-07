package sg.ic.pg.rest.service;

import org.apache.http.HttpStatus;
import sg.ic.pg.controller.model.OtpResponse;
import sg.ic.pg.model.User;
import sg.ic.pg.rest.model.ValidateOTPRequest;
import sg.ic.pg.rest.validator.OTPValidator;
import sg.ic.pg.util.property.AuditConstant;
import sg.ic.pg.util.property.Constant;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("otp")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class OTPService extends BaseOTPService {

    @Inject
    private OTPValidator validator;

    public OTPService() {
        log = getLogger(this.getClass());
    }

    @POST
    @Path("request")
    public Response requestOTP() {
        final String methodName = "requestOTP";
        log.debug(methodName, "POST /otp/request (SMS OTP)");
        start(methodName);

        Response response = doRequestOTP(Constant.KEY_OTP_SMS);

        completed(methodName);
        return response;
    }

    @POST
    @Path("request/voice")
    public Response requestVoiceOTP() {
        final String methodName = "requestVoiceOTP";
        log.debug(methodName, "POST /otp/request/voice (VOICE OTP)");
        start(methodName);

        Response response = doRequestOTP(Constant.KEY_OTP_VOICE);

        completed(methodName);
        return response;
    }

    private Response doRequestOTP(String OTPType) {
        final String methodName = "doRequestOTP";
        Response response = buildBadRequestResponse();

        if (!hasSessionAttribute(Constant.SESSION_USER_DETAILS)) {
            completed(methodName);
            log.debug(methodName, AuditConstant.ACTION_REQUEST_OTP + " : No session user detail. ");
            return buildBadRequestResponse();
        }

        boolean isVerified = false;
        if (hasSession() && hasSessionAttribute(Constant.SESSION_MOBILE_VERFIED)) {
            isVerified = getSessionAttribute(Constant.SESSION_MOBILE_VERFIED, Boolean.class);
        }

        User   user   = getSessionAttribute(Constant.SESSION_USER_DETAILS, User.class);
        String mobile = user.getMobileCountryCode() + user.getMobileNo();
        log.debug(methodName, OTPType + " : " + user.getEmail() + " " + mobile);

        if (isVerified) { // Verified User
            log.debug(methodName, "User is already verified. Not sending OTP.");
        } else {

            OtpResponse otpResponse = sendOTP(mobile, OTPType, user);

            if (otpResponse.getCode() == HttpStatus.SC_BAD_REQUEST) {
                String message = otpResponse.getMessage();
                response = doRequestOTPBadResponse(OTPType, mobile, message);
            } else {
                if (otpResponse.getResult().isSend()) {
                    response = doRequestOTPSuccessResponse(OTPType, mobile);
                }
            }
        }
        return response;
    }

    private Response doRequestOTPBadResponse(String OTPType, String mobile, String message) {
        final String methodName = "doRequestOTPBadResponse";
        log.debug(methodName, AuditConstant.ACTION_REQUEST_OTP + " for " + mobile + ": " + false);

        String auditMsg = buildAuditMessage(false,AuditConstant.AUDIT_REQUEST_OTP_FAILED_MESSAGE,
                OTPType, "sending", mobile, message);

        audit(AuditConstant.ACTION_REQUEST_OTP, auditMsg, false);

        return buildBadRequestResponse(message);
    }

    private Response doRequestOTPSuccessResponse(String OTPType, String mobile) {
        final String methodName = "doRequestOTPSuccessResponse";

        if (!hasSessionAttribute(Constant.SESSION_MOBILE_VERFIED)) {
            setSessionAttribute(Constant.SESSION_MOBILE_VERFIED, false);
        }

        // Track OTP Type to support switching between SMS OTP and Voice OTP
        // that are having different expiry time
        setSessionAttribute(Constant.SESSION_OTP_REQUEST, OTPType);

        log.debug(methodName, AuditConstant.ACTION_REQUEST_OTP + " for " + mobile + ": " + true);

        String auditMsg = buildAuditMessage(true,AuditConstant.AUDIT_REQUEST_OTP_SUCCESS_MESSAGE,
                OTPType, "send", mobile, null);

        audit(AuditConstant.ACTION_REQUEST_OTP, auditMsg, true);

        return buildSuccessResponse();
    }

    @POST
    @Path("resend")
    @PermitAll
    public Response resendOTP() {
        final String methodName = "resendOTP";
        log.debug(methodName, "POST /otp/resend (SMS OTP)");
        start(methodName);

        Response response = doResendOTP(Constant.KEY_OTP_SMS);

        completed(methodName);
        return response;
    }

    @POST
    @Path("resend/voice")
    @PermitAll
    public Response resendVoiceOTP() {
        final String methodName = "resendVoiceOTP";
        log.debug(methodName, "POST /otp/resend/voice (VOICE OTP)");
        start(methodName);

        Response response = doResendOTP(Constant.KEY_OTP_VOICE);

        completed(methodName);
        return response;
    }

    private Response doResendOTP(String OTPType) {
        final String methodName = "doResendOTP";
        Response response = buildSuccessResponse();

        boolean isVerified = getSessionAttribute(Constant.SESSION_MOBILE_VERFIED, Boolean.class);

        if (isVerified) { // Verified User
            log.debug(methodName, "User is already verified. Not resending OTP.");
        } else {
            String mobileNo = getSessionAttribute(Constant.SESSION_MOBILE_NO, String.class);
            String mobileCountryCode = getSessionAttribute(Constant.SESSION_MOBILE_COUNTRY_CODE, String.class);
            String mobile = mobileCountryCode + mobileNo;

            User user = getSessionAttribute(Constant.SESSION_USER_DETAILS, User.class);
            log.debug(methodName, OTPType + " : " + user.getEmail() + " " + mobile);

            OtpResponse otpResponse = sendOTP(mobile, OTPType, user);

            if (otpResponse.getCode() == HttpStatus.SC_BAD_REQUEST) {
                String message = otpResponse.getMessage();
                response = doResendOTPBadResponse(OTPType, mobile, message);
            } else {
                // even if HttpStatus.SC_OK, OtpResult can contain error. Check for isSend() flag
                if (otpResponse.getResult().isSend()) {
                    response = doResendOTPSuccessResponse(OTPType, mobile);
                } else {
                    String message = otpResponse.getResult().getMessage();
                    response = doResendOTPBadResponse(OTPType, mobile, message);
                }
            }
        }

        return response;
    }

    private Response doResendOTPBadResponse(String OTPType, String mobile, String message) {
        final String methodName = "doResendOTPBadResponse";
        log.debug(methodName, AuditConstant.ACTION_RESEND_OTP + " for " + mobile + ": " + false);

        String auditMsg = buildAuditMessage(false,AuditConstant.AUDIT_REQUEST_OTP_FAILED_MESSAGE,
                OTPType, "resending", mobile, message);

        audit(AuditConstant.ACTION_RESEND_OTP, auditMsg, false);

        return buildBadRequestResponse(message);
    }

    private Response doResendOTPSuccessResponse(String OTPType, String mobile) {
        final String methodName = "doResendOTPSuccessResponse";

        log.debug(methodName, AuditConstant.ACTION_RESEND_OTP + " for " + mobile + ": " + true);

        String auditMsg = buildAuditMessage(true,AuditConstant.AUDIT_REQUEST_OTP_SUCCESS_MESSAGE,
                OTPType, "resend", mobile, null);

        audit(AuditConstant.ACTION_RESEND_OTP, auditMsg, true);

        return buildSuccessResponse();
    }
    @POST
    @Path("validate")
    @PermitAll
    public Response validateOTP(ValidateOTPRequest request) {
        final String methodName = "validateOTP";
        log.debug(methodName, "POST /otp/validate");
        Response response;
        start(methodName);

        if (!validator.validate(request)) {
            completed(methodName);
            log.debug(methodName, AuditConstant.ACTION_VALIDATE_OTP + " Invalid Request - OTP EMPTY ");
            return buildBadRequestResponse(Constant.OTP_EMPTY);
        }

        boolean isMobileVerified = getSessionAttribute(Constant.SESSION_MOBILE_VERFIED, Boolean.class);

        if (isMobileVerified) {
            log.debug(methodName, "User is already verified. Not validating OTP.");
            response = buildSuccessResponse();
        } else {
            User user = getSessionAttribute(Constant.SESSION_USER_DETAILS, User.class);
            response = validateOTP(AuditConstant.ACTION_VALIDATE_OTP, Constant.SESSION_MOBILE_VERFIED,  request, user);
        }

        completed(methodName);
        return response;
    }

    private String buildAuditMessage(boolean result, String baseMessage, String otpType, String action,
                                     String mobile, String message) {

        String auditMessage = baseMessage
                .replace("{otpType}", otpType)
                .replace("{action}", action)
                .replace("{mobileNumber}", mobile);
        if (result) {
            return auditMessage;
        } else {
            return auditMessage.replace("{message}", message);
        }
    }
}
