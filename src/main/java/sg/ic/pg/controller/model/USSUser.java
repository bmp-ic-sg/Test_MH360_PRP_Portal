package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class USSUser {
    @JsonProperty("uid")
    private String uid;

    @JsonProperty("email")
    private String email;

    public USSUser(String uid, String email) {
        super();
        this.uid = uid;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
