package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SDMSCountriesResponse extends BaseAPIResponse{

    @JsonProperty("count")
    private int count;

    @JsonProperty("countries")
    private List<SDMSCountry> countries;

    public SDMSCountriesResponse() {
        //Empty constructor
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SDMSCountry> getCountries() {
        return countries;
    }

    public void setCountries(List<SDMSCountry> countries) {
        this.countries = countries;
    }
}
