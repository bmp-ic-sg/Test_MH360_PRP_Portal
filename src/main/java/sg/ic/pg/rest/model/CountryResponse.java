package sg.ic.pg.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryResponse {
    @JsonProperty("cor")
    private String cor;
    @JsonProperty("countryName")
    private String countryName;
    @JsonProperty("flagImageUrl")
    private String flagImageUrl;
    @JsonProperty("isoCode")
    private String isoCode;
    @JsonProperty("countryCode")
    private String countryCode;

    public CountryResponse() {
        //Empty constructor
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFlagImageUrl() {
        return flagImageUrl;
    }

    public void setFlagImageUrl(String flagImageUrl) {
        this.flagImageUrl = flagImageUrl;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
