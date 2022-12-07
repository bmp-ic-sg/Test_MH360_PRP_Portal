package sg.ic.pg.util.property;

public class AuditConstant {

    private AuditConstant() {}

    public static final String ACTION_CREATE_USER = "Create User";

    public static final String ACTION_CREATE_USER_SCIM = "Create User - SCIM";

    public static final String ACTION_CREATE_USER_DIGIHEALTH = "Create User - DigiHealth";

    public static final String ACTION_CREATE_USER_UCMI = "Create User - UCMI";

    public static final String ACTION_CREATE_USER_MOENGAGE = "Create User - MoEngage";

    public static final String ACTION_VALIDATE_OTP = "Validate OTP";

    public static final String ACTION_REQUEST_OTP = "Request OTP";

    public static final String ACTION_RESEND_OTP = "Resend OTP";

    public static final String ACTION_MYINFO_AUTHORIZE = "MyInfo Authorize";

    public static final String ACTION_MYINFO_RETRIEVE = "MyInfo Retrieve";

    public static final String ACTION_VALIDATE_EMAIL = "Validate Email";

    // Audit message
    public static final String AUDIT_RESULT_SUCCESS = "Success";
    public static final String AUDIT_RESULT_FAILED = "Failed";

    public static final String AUDIT_VALIDATE_EMAIL_MESSAGE = "{result} validate email {email}";

    public static final String AUDIT_LDAP_CREATE_MESSAGE = "{result} create LDAP user {email} : {userId}";
    public static final String AUDIT_SCIM_CREATE_MESSAGE = "{result} create SCIM user {email} : {userId}";
    public static final String AUDIT_UCMI_CREATE_MESSAGE = "{result} create UCMI user {email} : {userId}";
    public static final String AUDIT_DIGIHEALTH_CREATE_MESSAGE = "{result} create Digihealth user {email} : {userId}";
    public static final String AUDIT_MOENGAGE_CREATE_MESSAGE = "{result} create MoEngage user {email}";

    public static final String AUDIT_REQUEST_OTP_SUCCESS_MESSAGE = "{otpType} OTP {action} to {mobileNumber}";
    public static final String AUDIT_REQUEST_OTP_FAILED_MESSAGE = "Error {action} {otpType} OTP to {mobileNumber} : {message}";

    public static final String AUDIT_VALIDATE_OTP_BASE_MESSAGE = "{message} {mobileNumber} : {OTP}";
    public static final String AUDIT_VALIDATE_OTP_SUCCESS_RESULT = "OTP Verified";
    public static final String AUDIT_VALIDATE_OTP_RETRY_COUNT_RESULT = "Retry Limit Reached";
    public static final String AUDIT_VALIDATE_OTP_FAILED_RESULT = "Invalid OTP";
    public static final String AUDIT_VALIDATE_OTP_EXPIRED_RESULT = "OTP expired";


}
