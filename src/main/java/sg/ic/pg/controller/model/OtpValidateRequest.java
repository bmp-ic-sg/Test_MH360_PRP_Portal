package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpValidateRequest {

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("otp")
    private String otp;

    public OtpValidateRequest() {
        //Empty constructor
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
