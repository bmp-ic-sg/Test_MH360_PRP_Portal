package sg.ic.pg.controller;

import org.apache.http.HttpStatus;
import sg.ic.pg.controller.model.*;
import sg.ic.pg.model.User;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPContentType;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.json.JsonHelper;
import sg.ic.pg.util.property.Property;

@Controller
public class OTPController extends BaseAPIController {

    private static String otpBaseUrl;
    private static final String OTP_RETRY    = "/retry-count";
    private static final String OTP_SEND     = "/request";
    private static final String OTP_VALIDATE = "/validate";

    public OTPController() {
        log = getLogger(this.getClass());
        otpBaseUrl = getProperty(Property.OTP_URL);
    }

    public int getOTPRetryCount() {
        final String methodName = "getOTPRetryCount";
        start(methodName);

        int count  = getIntProperty(Property.OTP_RETRY_COUNT);
        String uri = otpBaseUrl + OTP_RETRY;

        HTTPResponse httpResponse = HTTPClient.get(buildHTTPRequest(uri));
        logResponse(methodName, httpResponse);

        OTPRetryCountResponse response = JsonHelper.fromJson(httpResponse.getBody(), OTPRetryCountResponse.class);
        if (httpResponse.getCode() == HttpStatus.SC_OK && response != null) {
            count = response.getCount();
        }

        log.debug(methodName, "OTP Retry Count : " + count);

        completed(methodName);
        return count;
    }

    public OtpResponse send(String mobile, String OTPType) {
        final String methodName = "send";
        start(methodName);

        String uri = otpBaseUrl + OTP_SEND;

        // email and first name not required since PRP only handles SMS/Voice OTP
        OtpRequest payload = new OtpRequest();
        payload.setOtpType(OTPType);
        payload.setMobile(mobile);

        logRequest(methodName, payload);

        HTTPResponse httpResponse = HTTPClient.post(buildHTTPRequest(uri), JsonHelper.toJson(payload));

        logResponse(methodName, httpResponse);

        OtpResponse response = JsonHelper.fromJson(httpResponse.getBody(), OtpResponse.class);

        completed(methodName);
        return response;
    }

    public OtpValidateResponse validate(String mobile, String otp) {
        final String methodName = "validate";
        start(methodName);

        String uri = otpBaseUrl + OTP_VALIDATE;

        OtpValidateRequest payload = new OtpValidateRequest();
        payload.setMobile(mobile);
        payload.setOtp(otp);

        logRequest(methodName, payload);

        HTTPResponse httpResponse = HTTPClient.post(buildHTTPRequest(uri), JsonHelper.toJson(payload));

        logResponse(methodName, httpResponse);

        OtpValidateResponse response = JsonHelper.fromJson(httpResponse.getBody(), OtpValidateResponse.class);

        completed(methodName);
        return response;
    }

    private HTTPRequest buildHTTPRequest(String uri) {

        return new HTTPRequest.Builder(uri)
                .setContentType(HTTPContentType.APPLICATION_JSON)
                .addHeader(getProperty(Property.OTP_KEY_HEADER), getProperty(Property.OTP_KEY_VALUE))
                .build();
    }


}
