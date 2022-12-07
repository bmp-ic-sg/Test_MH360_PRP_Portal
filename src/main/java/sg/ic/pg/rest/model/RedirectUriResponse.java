package sg.ic.pg.rest.model;

import javax.ws.rs.core.Response;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RedirectUriResponse extends ServiceResponse {

    @JsonProperty("redirectUri")
    private String redirectUri;

    public RedirectUriResponse() {
        super(Response.Status.OK);
    }

    public RedirectUriResponse(String redirectUri) {
        super(Response.Status.OK);
        this.redirectUri = redirectUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

}
