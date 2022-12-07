package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.pg.model.User;

public class CreateUserRequest extends BaseAPIRequest {


    @JsonProperty("uid")
    protected String uid;

    @JsonProperty("firstName")
    protected String firstName;

    @JsonProperty("lastName")
    protected String lastName;

    @JsonProperty("emailAddress")
    protected String email;

    @JsonProperty("dob")
    protected String dob;

    @JsonProperty("identificationID")
    protected String id;

    @JsonProperty("employeeType")
    protected String employeeType;

    @JsonProperty("mobileNo")
    protected String mobileNo;

    @JsonProperty("mobileNoCountryCode")
    protected String mobileCountryCode;

    @JsonProperty("gender")
    protected String gender;

    @JsonProperty("nationality")
    protected String nationality;

    @JsonProperty("countryOfResidence")
    protected String country;

    @JsonProperty("password")
    protected String password;

    public CreateUserRequest() {
        super();
    }


    public CreateUserRequest(User user) {
        super();
        setUid(user.getUid());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setEmail(user.getEmail());
        setDob(user.getDob());
        setId(user.getId());
        setEmployeeType(user.getEmployeeType());
        setMobileCountryCode(user.getMobileCountryCode());
        setMobileNo(user.getMobileNo());
        setGender(user.getGender());
        setNationality(user.getNationality());
        setCountry(user.getCountry());
        setPassword(user.getPassword());
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



}
