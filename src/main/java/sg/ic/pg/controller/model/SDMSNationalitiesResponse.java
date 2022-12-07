package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SDMSNationalitiesResponse extends BaseAPIResponse{

    @JsonProperty("count")
    private int count;

    @JsonProperty("nationalities")
    private List<SDMSNationality> nationalities;

    public SDMSNationalitiesResponse() {
        //Empty constructor
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SDMSNationality> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<SDMSNationality> nationalities) {
        this.nationalities = nationalities;
    }
}
