package sg.ic.pg.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OTPRequest {

    @JsonProperty("mobileCountryCode")
    private String mobileCountryCode;

    @JsonProperty("mobileNo")
    private String mobileNo;

    public OTPRequest() {
        // Empty Constructor
    }

    public String getMobileCountryCode() {
        return mobileCountryCode;
    }

    public void setMobileCountryCode(String mobileCountryCode) {
        this.mobileCountryCode = mobileCountryCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

}
