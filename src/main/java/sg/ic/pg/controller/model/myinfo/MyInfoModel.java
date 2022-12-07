package sg.ic.pg.controller.model.myinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyInfoModel {

    @JsonProperty("name")
    private MyInfoSection name;
    @JsonProperty("dob")
    private MyInfoSection dob;
    @JsonProperty("sex")
    private MyInfoSection sex;
    @JsonProperty("nationality")
    private MyInfoSection nationality;
    @JsonProperty("race")
    private MyInfoSection race;
    @JsonProperty("uinfin")
    private MyInfoSection uinfin;
    @JsonProperty("email")
    private MyInfoSection email;
    @JsonProperty("mobileno")
    private MyInfoMobile mobileNo;

    public MyInfoModel() {
        // Emtpy Constructor
    }

    public MyInfoSection getName() {
        return name;
    }

    public void setName(MyInfoSection name) {
        this.name = name;
    }

    public MyInfoSection getDob() {
        return dob;
    }

    public void setDob(MyInfoSection dob) {
        this.dob = dob;
    }

    public MyInfoSection getSex() {
        return sex;
    }

    public void setSex(MyInfoSection sex) {
        this.sex = sex;
    }

    public MyInfoSection getNationality() {
        return nationality;
    }

    public void setNationality(MyInfoSection nationality) {
        this.nationality = nationality;
    }

    public MyInfoSection getRace() {
        return race;
    }

    public void setRace(MyInfoSection race) {
        this.race = race;
    }

    public MyInfoSection getUinfin() {
        return uinfin;
    }

    public void setUinfin(MyInfoSection uinfin) {
        this.uinfin = uinfin;
    }

    public MyInfoSection getEmail() {
        return email;
    }

    public void setEmail(MyInfoSection email) {
        this.email = email;
    }

    public MyInfoMobile getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(MyInfoMobile mobileNo) {
        this.mobileNo = mobileNo;
    }

}
