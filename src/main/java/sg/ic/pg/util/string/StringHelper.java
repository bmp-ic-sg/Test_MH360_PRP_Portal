package sg.ic.pg.util.string;

import java.util.Base64;

public class StringHelper {

    private StringHelper() {}

    public static boolean validate(String str) {
        return null != str && !str.trim().isEmpty();
    }

    public static String base64Encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String base64Decode(String str) {
        byte[] decoded = Base64.getDecoder().decode(str);
        return new String(decoded);
    }
}
