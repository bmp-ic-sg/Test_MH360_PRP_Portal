package sg.ic.pg.controller.model.myinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyInfoMobile extends MyInfoSection {

    @JsonProperty("areacode")
    private MyInfoSection areaCode;

    @JsonProperty("prefix")
    private MyInfoSection prefix;

    @JsonProperty("nbr")
    private MyInfoSection number;

    public MyInfoMobile() {
        // Empty Constructor
    }

    public MyInfoSection getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(MyInfoSection areaCode) {
        this.areaCode = areaCode;
    }

    public MyInfoSection getPrefix() {
        return prefix;
    }

    public void setPrefix(MyInfoSection prefix) {
        this.prefix = prefix;
    }

    public MyInfoSection getNumber() {
        return number;
    }

    public void setNumber(MyInfoSection number) {
        this.number = number;
    }
}
