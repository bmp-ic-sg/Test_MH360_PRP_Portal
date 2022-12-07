package sg.ic.pg.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyInfoUser extends User {

    @JsonProperty("name")
    private String name;

    @JsonProperty("race")
    private String race;

    public MyInfoUser() {
        this.setCountry("Singapore");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }



}
