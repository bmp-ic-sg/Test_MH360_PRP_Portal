package sg.ic.pg.util.property;

public class Constant {

    private Constant() {}

    public static final String COMMON_PROPERTY_FILENAME = "application.common.properties";

    public static final String PROPERTY_FILENAME = "application.properties";

    // SESSION
    public static final String SESSION_USER_DETAILS = "pg_user";
    public static final String SESSION_LOGIN_INFO = "pg_login_info";
    public static final String SESSION_MOBILE_NO = "pg_mobile_no";
    public static final String SESSION_MOBILE_COUNTRY_CODE = "pg_mobile_country_code";
    public static final String SESSION_OTP_RETRY_COUNT = "pg_otp_retry_count";
    public static final String SESSION_MOBILE_VERFIED = "pg_mobile_verified";
    public static final String SESSION_OTP_REQUEST = "pg_otp_request";

    public static final String SESSION_REG_MYINFO = "pg_reg_myinfo";
    public static final String SESSION_REG_MYINFO_USER = "pg_reg_myinfo_user";

    // ROLES
    public static final String ROLE_USER_VERIFIED = "role_user_verified";

    public static final String SESSION_TRACKING_ID = "pg_tracking_id";

    // Redirect Cookie
    public static final String REDIRECT_COOKIE = "redirectURI";

    //password regex
    public static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}|:\\\"<>?~`\\-=\\[\\]\\\\;',.\\/]).{8,}$";

    public static final String OTP_EMPTY = "Please enter valid OTP";
    public static final String OTP_EMPTY_2 = "We do not have your mobile number registered in our records. Please contact helpdesk";
    public static final String OTP_INVALID_NUMBER_MSG = "We have tried to send an OTP but are unable to reach your phone. Please check your mobile signal and try again";
    public static final String OTP_RESEND_MSG = "OTP successfully resent";
    public static final String OTP_INVALID_OTP_MSG = "You may have entered an invalid pin, please try keying in the OTP again or request a new one";
    public static final String OTP_EXPIRED_MSG = "OTP you have entered has expired, please request for a new OTP";

    public static final String KEY_OTP_SMS   = "SMS";
    public static final String KEY_OTP_VOICE = "VOICE";
    public static final String ERROR_INVALID_OTP_MSG  = "Incorrect OTP";
    public static final String ERROR_EXPIRED_OTP_MSG  = "Otp expired";

    public static final String KEY_LANGUAGE_CODE = "EN";
    public static final String KEY_VERSION = "PUBLISHED";
}
