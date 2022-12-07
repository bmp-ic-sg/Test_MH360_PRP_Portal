package sg.ic.pg.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NationalityResponse {

    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("isoCode")
    private String isoCode;
    @JsonProperty("flagImageUrl")
    private String flagImageUrl;

    public NationalityResponse() {
        //Empty constructor
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getFlagImageUrl() {
        return flagImageUrl;
    }

    public void setFlagImageUrl(String flagImageUrl) {
        this.flagImageUrl = flagImageUrl;
    }
}
