package sg.ic.pg.controller.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import sg.ic.pg.util.json.LocalDateTimeDeserializer;
import sg.ic.pg.util.json.LocalDateTimeSerializer;

public class AuditEvent {

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("function")
    private String function;

    @JsonProperty("details")
    private String details;

    @JsonProperty("result")
    private boolean result;

    @JsonProperty("eventDt")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDt;

    public AuditEvent() {
        // Empty Constructor
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public AuditEvent(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public LocalDateTime getEventDt() {
        return eventDt;
    }

    public void setEventDt(LocalDateTime eventDt) {
        this.eventDt = eventDt;
    }

}
