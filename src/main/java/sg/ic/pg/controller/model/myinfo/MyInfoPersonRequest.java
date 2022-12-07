package sg.ic.pg.controller.model.myinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.pg.controller.model.SecurityHeader;

public class MyInfoPersonRequest {

    @JsonProperty("securityHeader")
    private SecurityHeader securityHeader;

    @JsonProperty("authCode")
    private String authCode;

    @JsonProperty("redirectUri")
    private String redirectUri;

    public MyInfoPersonRequest() {
        securityHeader = new SecurityHeader();
    }

    public SecurityHeader getSecurityHeader() {
        return securityHeader;
    }

    public void setSecurityHeader(SecurityHeader securityHeader) {
        this.securityHeader = securityHeader;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

}
