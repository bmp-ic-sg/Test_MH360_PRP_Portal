package sg.ic.pg.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("uid")
    protected String uid;

    @JsonProperty("firstName")
    protected String firstName;

    @JsonProperty("lastName")
    protected String lastName;

    @JsonProperty("email")
    protected String email;

    @JsonProperty("dob")
    protected String dob;

    @JsonProperty("id")
    protected String id;

    @JsonProperty("employeeType")
    protected String employeeType;

    @JsonProperty("mobileNo")
    protected String mobileNo;

    @JsonProperty("mobileCountryCode")
    protected String mobileCountryCode;

    @JsonProperty("gender")
    protected String gender;

    @JsonProperty("nationality")
    protected String nationality;

    @JsonProperty("country")
    protected String country;

    @JsonProperty("password")
    protected String password;

    @JsonProperty("consentMarketing")
    protected boolean consentMarketing;

    @JsonProperty("marketingSmsFlag")
    protected boolean marketingSmsFlag;

    @JsonProperty("token")
    protected String token;


    public User() {
        // Empty Constructor
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileCountryCode() {
        return mobileCountryCode;
    }

    public void setMobileCountryCode(String mobileCountryCode) {
        this.mobileCountryCode = mobileCountryCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public boolean isMarketingSmsFlag() {
        return marketingSmsFlag;
    }

    public void setMarketingSmsFlag(boolean marketingSmsFlag) {
        this.marketingSmsFlag = marketingSmsFlag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
