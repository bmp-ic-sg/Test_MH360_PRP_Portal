package sg.ic.pg.controller.model.scim;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SCIMExtension {

    @JsonProperty("pplDOB")
    private String dob;

    @JsonProperty("pplGender")
    private String gender;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("pplCountryCode")
    private String mobileCountryCode;

    @JsonProperty("pplID")
    private String id;

    @JsonProperty("employeeType")
    private String employeeType;

    @JsonProperty("cn")
    private String firstName;

    @JsonProperty("pplNationality")
    private String nationality;

    @JsonProperty("pplCountry")
    private String country;


    public SCIMExtension() {
        // Empty Constructor
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileCountryCode() {
        return mobileCountryCode;
    }

    public void setMobileCountryCode(String mobileCountryCode) {
        this.mobileCountryCode = mobileCountryCode;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
}
