package sg.ic.pg.controller.model.scim;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SCIMModel {

    @JsonProperty("schemas")
    private final String[] schemas =
            {"urn:ietf:params:scim:schemas:core:2.0:User", "urn:ietf:params:scim:schemas:extension:gluu:2.0:User"};

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("name")
    private SCIMName name;

    @JsonProperty("emails")
    private List<SCIMEmail> emailList;

    @JsonProperty("urn:ietf:params:scim:schemas:extension:gluu:2.0:User")
    private SCIMExtension extension;

    @JsonProperty("active")
    private boolean active;

    public SCIMModel() {
        emailList = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SCIMName getName() {
        return name;
    }

    public void setName(SCIMName name) {
        this.name = name;
    }

    public List<SCIMEmail> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<SCIMEmail> emailList) {
        this.emailList = emailList;
    }

    public void addEmail(SCIMEmail email) {
        emailList.add(email);
    }

    public SCIMExtension getExtension() {
        return extension;
    }

    public void setExtension(SCIMExtension extension) {
        this.extension = extension;
    }

    public String[] getSchemas() {
        return schemas;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



}
