package sg.ic.pg.controller.model.myinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.pg.controller.model.BaseAPIResponse;

public class MyInfoPersonResponse extends BaseAPIResponse {

    @JsonProperty("info")
    private MyInfoModel user;

    public MyInfoPersonResponse() {
        // Empty Constructor
    }

    public MyInfoModel getUser() {
        return user;
    }

    public void setUser(MyInfoModel user) {
        this.user = user;
    }
}
