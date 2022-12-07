package sg.ic.pg.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidateEmailRequest {

    @JsonProperty("email")
    private String email;

    public ValidateEmailRequest() {
        // Empty Constructor
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
