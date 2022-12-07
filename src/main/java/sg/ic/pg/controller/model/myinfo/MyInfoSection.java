package sg.ic.pg.controller.model.myinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyInfoSection {

    @JsonProperty("lastupdated")
    private String lastUpdated;

    @JsonProperty("source")
    private String source;

    @JsonProperty("classifiation")
    private String classification;

    @JsonProperty("value")
    private String value;

    @JsonProperty("desc")
    private String description;

    public MyInfoSection() {
        // Empty Constructor
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
