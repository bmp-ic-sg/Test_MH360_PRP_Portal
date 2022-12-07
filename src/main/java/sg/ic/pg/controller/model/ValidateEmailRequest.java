package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidateEmailRequest extends BaseAPIRequest {

    @JsonProperty("user")
    private USSUser user;

    public ValidateEmailRequest() {
        // Empty Constructor
    }

    public ValidateEmailRequest(USSUser user) {
        this.user = user;
    }

    public USSUser getUser() {
        return user;
    }

    public void setUser(USSUser user) {
        this.user = user;
    }
}
