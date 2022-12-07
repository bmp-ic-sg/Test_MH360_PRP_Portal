package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SDMSRequest extends BaseAPIRequest {

    @JsonProperty("languageCode")
    private String languageCode;
    @JsonProperty("version")
    private String version;

    public SDMSRequest() {
        //Empty constructor
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
