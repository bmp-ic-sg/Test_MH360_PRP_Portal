package sg.ic.pg.controller.model;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SecurityHeader {

    @JsonProperty("state")
    private String state;

    public SecurityHeader() {
        this.state = UUID.randomUUID().toString();
    }

    public SecurityHeader(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
