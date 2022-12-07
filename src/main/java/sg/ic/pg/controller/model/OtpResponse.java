package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtpResponse extends BaseOtpResponse {

    @JsonProperty("result")
    private OtpResult result;

    public OtpResponse() {
    }

    public OtpResult getResult() {
        return result;
    }

    public void setResult(OtpResult result) {
        this.result = result;
    }
}
