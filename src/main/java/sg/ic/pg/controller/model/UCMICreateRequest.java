package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.pg.model.User;

public class UCMICreateRequest extends BaseAPIRequest {

    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("email")
    private String email;
    @JsonProperty("mobileNo")
    private String mobileNo;
    @JsonProperty("mobileCountryCode")
    private String mobileCountryCode;
    @JsonProperty("identificationId")
    private String id;
    @JsonProperty("countryOfResidence")
    private String country;
    @JsonProperty("gender")
    private String gender;

    public UCMICreateRequest(User user) {
        super();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.fullName = user.getFirstName() + user.getLastName();
        this.dob = user.getDob();
        this.email = user.getEmail();
        this.mobileNo = user.getMobileNo();
        this.mobileCountryCode = user.getMobileCountryCode();
        this.id = user.getId();
        this.country = user.getCountry();
        this.gender = user.getGender().toUpperCase();
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
