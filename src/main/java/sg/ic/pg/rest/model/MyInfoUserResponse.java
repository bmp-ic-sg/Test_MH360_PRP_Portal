package sg.ic.pg.rest.model;

import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.pg.model.MyInfoUser;

public class MyInfoUserResponse extends ServiceResponse {

    @JsonProperty("isMyInfo")
    private boolean myInfo;

    @JsonProperty("user")
    private MyInfoUser user;

    public MyInfoUserResponse() {
        super(Status.OK);
    }

    public boolean isMyInfo() {
        return myInfo;
    }

    public void setMyInfo(boolean myInfo) {
        this.myInfo = myInfo;
    }

    public MyInfoUser getUser() {
        return user;
    }

    public void setUser(MyInfoUser user) {
        this.user = user;
    }


}
