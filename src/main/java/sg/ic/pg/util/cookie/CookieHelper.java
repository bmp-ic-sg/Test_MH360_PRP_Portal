package sg.ic.pg.util.cookie;

import javax.servlet.http.Cookie;
import javax.ws.rs.core.NewCookie;
import org.apache.http.impl.cookie.BasicClientCookie;

public class CookieHelper {

    private CookieHelper() {}

    public static BasicClientCookie convertRSCoreToBasicClientCookie(javax.ws.rs.core.Cookie cookie, String domain) {
        BasicClientCookie newCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
        newCookie.setDomain(domain);
        newCookie.setPath("/");
        return newCookie;
    }

    public static BasicClientCookie convertHttpCookieToBasicClientCookie(javax.servlet.http.Cookie cookie,
            String domain) {
        BasicClientCookie newCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
        newCookie.setComment(cookie.getComment());
        newCookie.setVersion(cookie.getVersion());
        newCookie.setDomain(domain);
        newCookie.setPath("/");
        return newCookie;

    }

    public static BasicClientCookie convertHttpCookieToBasicClientCookie(javax.servlet.http.Cookie cookie) {
        BasicClientCookie newCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
        newCookie.setComment(cookie.getComment());
        newCookie.setVersion(cookie.getVersion());
        newCookie.setDomain(cookie.getDomain());
        newCookie.setPath("/");
        return newCookie;
    }

    public static Cookie convertRSCoreToHttpCookie(javax.ws.rs.core.Cookie cookie, String domain) {
        Cookie newCookie = new Cookie(cookie.getName(), cookie.getValue());
        newCookie.setDomain(domain);
        newCookie.setPath("/");
        return newCookie;
    }

    public static NewCookie removeNewCookie(String name, String domain, String path, boolean isSecure) {
        return new NewCookie(name, "", path, domain, "", 0, isSecure, true);
    }

    public static NewCookie convertHttpCookieToNewCookie(javax.servlet.http.Cookie cookie, String domain, int maxAge) {
        return new NewCookie(cookie.getName(), cookie.getValue(), cookie.getPath(), domain, cookie.getComment(), maxAge,
                cookie.getSecure(), cookie.isHttpOnly());
    }

    public static NewCookie convertHttpCookieToNewCookie(org.apache.http.cookie.Cookie cookie, String domain,
            boolean isSecure, boolean isHttpOnly) {
        int time = -1;
        if (cookie.getExpiryDate() != null) {
            time = (int) ((cookie.getExpiryDate().getTime() - System.currentTimeMillis()) / 1000);
        }
        return new NewCookie(cookie.getName(), cookie.getValue(), cookie.getPath(), domain, cookie.getVersion(),
                cookie.getComment(), time, cookie.getExpiryDate(), isSecure, isHttpOnly);
    }
}
