package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.pg.model.User;

public class DigiHealthCreateRequest extends BaseAPIRequest {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("mobileNo")
    private String mobileNo;
    @JsonProperty("mobileNoCountryCode")
    private String mobileNoCountryCode;
    @JsonProperty("identificationID")
    private String identificationId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("consentMarketing")
    private boolean consentMarketing;
    @JsonProperty("smsFlag")
    private boolean marketingSmsFlag;

    public DigiHealthCreateRequest(User user) {
        super();
        uid = user.getUid();
        mobileNo = user.getMobileNo();
        mobileNoCountryCode = user.getMobileCountryCode();
        identificationId = user.getId();
        email = user.getEmail();
        dob = user.getDob().replace("-", "");
        consentMarketing = user.isConsentMarketing();
        marketingSmsFlag = user.isMarketingSmsFlag();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNoCountryCode() {
        return mobileNoCountryCode;
    }

    public void setMobileNoCountryCode(String mobileNoCountryCode) {
        this.mobileNoCountryCode = mobileNoCountryCode;
    }

    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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
