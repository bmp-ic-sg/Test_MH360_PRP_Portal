package sg.ic.pg.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidateLoginInfoRequest {

    @JsonProperty("email")
    protected String email;

    @JsonProperty("password")
    protected String password;

    @JsonProperty("consentMarketing")
    protected boolean consentMarketing;

    @JsonProperty("consentHealthcare")
    protected boolean consentHealthcare;

    public ValidateLoginInfoRequest() {
        // Empty Constructor
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isConsentMarketing() {
        return consentMarketing;
    }

    public void setConsentMarketing(boolean consentMarketing) {
        this.consentMarketing = consentMarketing;
    }

    public boolean isConsentHealthcare() {
        return consentHealthcare;
    }

    public void setConsentHealthcare(boolean consentHealthcare) {
        this.consentHealthcare = consentHealthcare;
    }
}
