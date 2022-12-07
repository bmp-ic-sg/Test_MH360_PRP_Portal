package sg.ic.pg.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseAPIRequest {

    @JsonProperty("securityHeader")
    protected SecurityHeader securityHeader;

    public BaseAPIRequest() {
        securityHeader = new SecurityHeader();
    }

    public SecurityHeader getSecurityHeader() {
        return securityHeader;
    }

    public void setSecurityHeader(SecurityHeader securityHeader) {
        this.securityHeader = securityHeader;
    }
}
