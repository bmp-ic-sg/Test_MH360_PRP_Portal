package sg.ic.pg.rest.service;

import sg.ic.pg.controller.OTPController;
import sg.ic.pg.controller.model.OtpResponse;
import sg.ic.pg.controller.model.OtpValidateResponse;
import sg.ic.pg.controller.model.OtpValidateResult;
import sg.ic.pg.model.User;
import sg.ic.pg.rest.model.ValidateOTPRequest;
import sg.ic.pg.util.property.AuditConstant;
import sg.ic.pg.util.property.Constant;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class BaseOTPService extends BaseService {

    @Inject
    private OTPController otpController;

    protected OtpResponse sendOTP(String mobile, String OTPType, User user) {
        final String methodName = "sendOTP";

        log.debug(methodName, "Sending " + OTPType + " OTP for " + user.getEmail() + " " + mobile + " ...");

        OtpResponse response = otpController.send(mobile, OTPType);
        log.debug(methodName, response.getResult().getMessage());

        // Reset OTP retry count whenever a new OTP is fired
        resetOTPRetryCountInSession();

        return response;
    }

    protected Response validateOTP(String action, String statusInSess, ValidateOTPRequest request, User user) {
        final String methodName = "doValidateOTP";

        Response response;

        String userEmail = user.getEmail();
        String mobile = user.getMobileCountryCode() + user.getMobileNo();
        log.debug(methodName, userEmail + " " + mobile);

        int maxRetryCount = otpController.getOTPRetryCount();
        log.debug(methodName, "Max retry count: " + maxRetryCount);

        int retryCount = 0;
        if (hasSessionAttribute(Constant.SESSION_OTP_RETRY_COUNT)) {
            log.debug(methodName, "Otp retry count found in session.");
            retryCount = getSessionAttribute(Constant.SESSION_OTP_RETRY_COUNT, Integer.class);
        }

        log.debug(methodName, "Otp retry count (session): " + retryCount);

        if (retryCount >= maxRetryCount) {
            
            String logMsg = "Retry Limit Reached";
            log.debug(methodName, logMsg);

            String auditMsg = buildValidateOtpAuditMessage
                    (AuditConstant.AUDIT_VALIDATE_OTP_RETRY_COUNT_RESULT, mobile, request.getOtp());
            audit(AuditConstant.ACTION_VALIDATE_OTP, auditMsg, false);

            response = buildBadRequestResponse(logMsg);
        } else {

            String otp = request.getOtp();
            log.debug(methodName, "Validating OTP (" + otp + ")");

            OtpValidateResponse validateResponse = otpController.validate(mobile, otp);
            OtpValidateResult   result = validateResponse.getResult();
            log.debug(methodName, result.getMessage());

            if (result.isValidate()) {
                // Set status into session. Could be used by FE or subsequent calls to BE
                setSessionAttribute(statusInSess, true);

                String auditMsg = buildValidateOtpAuditMessage
                        (AuditConstant.AUDIT_VALIDATE_OTP_SUCCESS_RESULT, mobile, request.getOtp());
                audit(AuditConstant.ACTION_VALIDATE_OTP, auditMsg, true);

                response = buildSuccessResponse();
            } else {

                if (result.getMessage().contains(Constant.ERROR_EXPIRED_OTP_MSG)) {
                    log.debug(methodName, "Otp expired for " + mobile);

                    String auditMsg = buildValidateOtpAuditMessage
                            (AuditConstant.AUDIT_VALIDATE_OTP_EXPIRED_RESULT, mobile, request.getOtp());
                    audit(AuditConstant.ACTION_VALIDATE_OTP, auditMsg, false);

                    response = buildBadRequestResponse(Constant.OTP_EXPIRED_MSG);
                } else {
                    log.debug(methodName, "Incorrect OTP For " + mobile);
                    setSessionAttribute(Constant.SESSION_OTP_RETRY_COUNT, (retryCount + 1));
                    log.debug(methodName, "Set Retry Count to : " + (retryCount + 1));

                    String auditMsg = buildValidateOtpAuditMessage
                            (AuditConstant.AUDIT_VALIDATE_OTP_FAILED_RESULT, mobile, request.getOtp());
                    audit(AuditConstant.ACTION_VALIDATE_OTP, auditMsg, false);

                    response = buildBadRequestResponse(Constant.OTP_INVALID_OTP_MSG);
                }
            }

        }

        return response;
    }

    private String buildValidateOtpAuditMessage(String message, String mobile, String otp)
    {
        return AuditConstant.AUDIT_VALIDATE_OTP_BASE_MESSAGE
            .replace("{message}",message)
            .replace("{mobileNumber}",mobile)
            .replace("{OTP}",otp);
    }

    // -- Session variables settings

    protected void resetOTPRetryCountInSession() {
        final String methodName = "resetOTPRetryCountInSession";

        log.debug(methodName, "Resetting OTP Retry Count to 0");
        setSessionAttribute(Constant.SESSION_OTP_RETRY_COUNT, 0);
    }
}
