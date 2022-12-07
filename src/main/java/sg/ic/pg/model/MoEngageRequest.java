package sg.ic.pg.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoEngageRequest {

    @JsonProperty("firstName")
    protected String firstName;
    @JsonProperty("lastName")
    protected String lastName;
    @JsonProperty("gender")
    protected String gender;
    @JsonProperty("dob")
    protected String dob;
    @JsonProperty("nationality")
    protected String nationality;
    @JsonProperty("country")
    protected String country;
    @JsonProperty("email")
    protected String email;
    @JsonProperty("mobileCountryCode")
    protected String mobileCountryCode;
    @JsonProperty("mobileNo")
    protected String mobileNo;
    @JsonProperty("consentMarketing")
    protected boolean consentMarketing;
    @JsonProperty("marketingSmsFlag")
    protected boolean marketingSmsFlag;
    @JsonProperty("type")
    protected String type;

    public MoEngageRequest() {
        //Empty constructor
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
