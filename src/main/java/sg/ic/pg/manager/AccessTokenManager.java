package sg.ic.pg.manager;

import java.time.LocalDateTime;
import javax.inject.Singleton;
import sg.ic.pg.controller.model.AccessTokenResponse;
import sg.ic.pg.util.http.HTTPClient;
import sg.ic.pg.util.http.model.HTTPContentType;
import sg.ic.pg.util.http.model.HTTPParameter;
import sg.ic.pg.util.http.model.HTTPRequest;
import sg.ic.pg.util.http.model.HTTPResponse;
import sg.ic.pg.util.json.JsonHelper;
import sg.ic.pg.util.log.AppLogger;
import sg.ic.pg.util.property.Property;

@Singleton
public class AccessTokenManager extends BaseManager {

    private static AccessTokenManager instance;

    private String accessToken;
    private LocalDateTime expiryDt;

    private AccessTokenManager() {
        log = new AppLogger(this.getClass());
        updateToken();
    }

    public String getAccessToken() {
        if (expiryDt.isBefore(LocalDateTime.now())) {
            updateToken();
        }
        return accessToken;
    }

    private void updateToken() {
        accessToken = getClientToken();
        expiryDt = LocalDateTime.now().plusMinutes(30);
    }

    private String getClientToken() {
        final String methodName = "getClientToken";
        start(methodName);
        HTTPRequest request =
                new HTTPRequest.Builder(getProp(Property.OAUTH_BASE_URL) + getProp(Property.OAUTH_TOKEN_URL))
                        .setContentType(HTTPContentType.APPLICATION_FORM_URLENCODED).build();

        HTTPParameter params = new HTTPParameter();
        params.addParameter("client_id", getProp(Property.OAUTH_CLIENT_ID));
        params.addParameter("client_secret", getProp(Property.OAUTH_CLIENT_SECRET));
        params.addParameter("scope", getProp(Property.OAUTH_CLIENT_SCOPES));
        params.addParameter("grant_type", "client_credentials");

        HTTPResponse httpResponse = HTTPClient.post(request, params);

        AccessTokenResponse response = JsonHelper.fromJson(httpResponse.getBody(), AccessTokenResponse.class);

        completed(methodName);
        return response.getAccessToken();
    }

    private String getProp(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }

    public static AccessTokenManager getInstance() {
        if (instance == null) {
            instance = new AccessTokenManager();
        }
        return instance;
    }

    public void shutdown() {
        accessToken = null;
        expiryDt = null;
    }
}
