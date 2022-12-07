package sg.ic.pg.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidateOTPRequest {

    @JsonProperty("otp")
    private String otp;

    public ValidateOTPRequest() {
        // Empty Constructor
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
