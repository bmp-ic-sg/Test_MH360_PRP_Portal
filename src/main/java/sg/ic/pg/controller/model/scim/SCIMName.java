package sg.ic.pg.controller.model.scim;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SCIMName {

    @JsonProperty("familyName")
    private String familyName;

    public SCIMName() {
        // Empty Constructor
    }

    public SCIMName(String lastName) {
        this.familyName = lastName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

}
