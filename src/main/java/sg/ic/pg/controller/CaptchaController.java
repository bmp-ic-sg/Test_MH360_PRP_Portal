package sg.ic.pg.controller;

import sg.ic.pg.controller.model.CaptchaResponse;
import sg.ic.pg.manager.EncryptionManager;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPContentType;
import sg.ic.pg.util.http.model.HTTPParameter;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.json.JsonHelper;
import sg.ic.pg.util.property.Property;

@Controller
public class CaptchaController extends BaseController {

    public CaptchaController() {
        log = getLogger(this.getClass());
    }

    public boolean verifyCaptcha(String token) {
        final String methodName = "verifyCaptcha";
        start(methodName);
        boolean result = false;

        HTTPRequest request =
                new HTTPRequest.Builder(getProperty(Property.CAPTCHA_BASE_URL))
                        .setContentType(HTTPContentType.APPLICATION_FORM_URLENCODED).build();

        HTTPParameter params = new HTTPParameter();
        params.addParameter("secret", EncryptionManager.getInstance().decrypt(getProperty(Property.CAPTCHA_SECRET_KEY)));
        params.addParameter("response", token);

        HTTPResponse httpResponse = HTTPClient.post(request, params);
        log.debug(methodName, httpResponse.getBody());

        if (httpResponse.getCode() == 200) {
            CaptchaResponse response = JsonHelper.fromJson(httpResponse.getBody(), CaptchaResponse.class);
            String errorMsg = "";
            if(response.getErrorCodes()!=null) {
                for (String str : response.getErrorCodes()) {
                    errorMsg = str;
                }
            }
            if (response.isSuccess() && errorMsg.isEmpty()) {
                result = true;
            } else {
                log.debug(methodName, "Error message : " + errorMsg);
            }
        }

        completed(methodName);
        return result;
    }
}
