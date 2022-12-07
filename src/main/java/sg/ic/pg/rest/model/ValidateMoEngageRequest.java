package sg.ic.pg.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidateMoEngageRequest {

    @JsonProperty("email")
    protected String email;

    @JsonProperty("consentMarketing")
    protected boolean consentMarketing;

    @JsonProperty("marketingSmsFlag")
    protected boolean marketingSmsFlag;

    public ValidateMoEngageRequest() {
        //Empty constructor
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConsentMarketing() {
        return consentMarketing;
    }

    public void setConsentMarketing(boolean consentMarketing) {
        this.consentMarketing = consentMarketing;
    }

    public boolean isMarketingSmsFlag() {
        return marketingSmsFlag;
    }

    public void setMarketingSmsFlag(boolean marketingSmsFlag) {
        this.marketingSmsFlag = marketingSmsFlag;
    }
}
