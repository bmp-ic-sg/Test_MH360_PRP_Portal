package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseOtpResponse {

    // Note: Not to extend from BaseAPIResponse since the code and message
    //       variable names are different

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    public BaseOtpResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
