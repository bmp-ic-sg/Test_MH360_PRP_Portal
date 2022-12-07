package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserResponse extends BaseAPIResponse {

    @JsonProperty("uid")
    private String uid;

    public CreateUserResponse() {
        // Empty Constructor
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



}
