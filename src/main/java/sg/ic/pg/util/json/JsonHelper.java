package sg.ic.pg.util.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import sg.ic.pg.util.log.BaseLogger;

public class JsonHelper {
    private static final BaseLogger log = new BaseLogger(JsonHelper.class);
    private static final ObjectMapper OBJ_MAPPER = CustomObjectMapper.getInstance().getObjectMapper();

    private JsonHelper() {
        // Empty Constructor
    }

    public static String toJson(Object obj) {
        try {
            return OBJ_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("toJson", e);
        }
        return "";
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OBJ_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            log.error("fromJson", e);
        }

        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception ex) {
            log.error("fromJson", "Could not invoke Default Constructor", ex);
        }

        return null;
    }

    public static <T> List<T> fromJsonArray(String json) {
        try {
            return OBJ_MAPPER.readValue(json, new TypeReference<List<T>>() {});
        } catch (IOException e) {
            log.error("fromJsonArray", e);
        }
        return new ArrayList<>();
    }
}
