package sg.ic.pg.controller.model.scim;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SCIMEmail {

    @JsonProperty("value")
    private String value;

    @JsonProperty("primary")
    private boolean primary;

    public SCIMEmail() {
        // Empty Constructor
    }

    public SCIMEmail(String value, boolean primary) {
        this.value = value;
        this.primary = primary;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

}
