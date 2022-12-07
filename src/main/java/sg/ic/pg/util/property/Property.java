package sg.ic.pg.util.property;

public class Property {

    private Property() {}

    // ENV PROPERTY
    // APP
    public static final String APP_CONTEXT_FQDN = "context.fqdn";
    public static final String APP_CONTEXT_PATH = "context.path";
    public static final String APP_SERVER_KEY = "app.server.key";
    public static final String APP_LOGIN_URL = "app.login.url";
    public static final String APP_CONTEXT_REGISTRATION_PATH = "context.registration.path";

    // DB
    public static final String DB_JDBC_URL = "db.jdbc.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_POOL_SIZE = "db.pool-size";

    // OAUTH
    public static final String OAUTH_BASE_URL = "oauth.base.url";
    public static final String OAUTH_CLIENT_ID = "oauth.client.id";
    public static final String OAUTH_CLIENT_SECRET = "oauth.client.secret";

    // SCIM
    public static final String SCIM_BASE_URL = "scim.base.url";

    // API
    public static final String API_BASE_URL = "api.base.url";
    public static final String API_SERVER_PUBLIC_KEY = "api.server.public.key";

    // MYINFO
    public static final String MYINFO_REDIRECT_URI = "myinfo.redirect.uri";

    // MoEngage
    public static final String MOENGAGE_CREATE_USER_URL = "moengage.ms.create";
    public static final String MOENGAGE_KEY_HEADER = "moengage.ms.api-key.header";
    public static final String MOENGAGE_KEY_VALUE = "moengage.ms.key.value";

    // OTP
    public static final String OTP_URL = "otp.ms.url";
    public static final String OTP_KEY_HEADER = "otp.ms.api-key.header";
    public static final String OTP_KEY_VALUE = "otp.ms.api-key.value";

    // AUDIT
    public static final String AUDIT_URL = "audit.url";
    public static final String AUDIT_ENABLED = "audit.enabled";

    // COMMON PROPERTY
    // DB
    public static final String DB_DRIVER_CLASSNAME = "db.driver.className";
    public static final String DB_TYPE = "db.type";

    // OTP
    public static final String OTP_RETRY_COUNT = "otp.retry.count";

    // OAUTH
    public static final String OAUTH_TOKEN_URL = "oauth.token.url";
    public static final String OAUTH_CLIENT_SCOPES = "oauth.client.scopes";

    // SCIM
    public static final String SCIM_USERS_URL = "scim.users.url";

    // API
    public static final String API_MYINFO_AUTH_URL = "api.myinfo.authorize.url";
    public static final String API_MYINFO_PERSON_URL = "api.myinfo.person.url";
    public static final String API_EMAIL_VALIDATE_URL = "api.email.validate.url";
    public static final String API_USERS_CREATE_URL = "api.users.create.url";
    public static final String API_UCMI_CREATE_URL = "api.ucmi.create.url";
    public static final String API_DIGIHEALTH_CREATE_URL = "api.digihealth.create.url";
    public static final String API_RETRIEVE_COUNTRIES_URL = "api.retrieve.countries.url";
    public static final String API_RETRIEVE_NATIONALITIES_URL = "api.retrieve.nationalities.url";

    // MyInfo
    public static final String MYINFO_PURPOSE = "myinfo.purpose";
    public static final String MYINFO_ATTRIBUTES = "myinfo.attributes";

    // Captcha
    public static final String CAPTCHA_BASE_URL = "recaptcha.base-url";
    public static final String CAPTCHA_SITE_KEY = "recaptcha.site-key";
    public static final String CAPTCHA_SECRET_KEY = "recaptcha.secret-key";
    public static final String CAPTCHA_THRESHOLD = "recaptcha.threshold";
}
