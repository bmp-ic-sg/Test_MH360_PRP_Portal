package sg.ic.pg.util.http.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.apache.http.cookie.Cookie;

public class HTTPRequest {

    private String url;
    private HTTPHeader headers;
    private List<Cookie> cookies;

    public static class Builder {

        private String url;
        private HTTPHeader headers;
        private List<Cookie> cookies;

        public Builder(final String url) {
            this.url = url;
            this.headers = new HTTPHeader();
            this.cookies = new ArrayList<>();
        }

        public Builder setContentType(HTTPContentType contentType) {
            this.addHeader(HttpHeaders.CONTENT_TYPE, contentType.getType());
            return this;
        }

        public Builder setAuthorization(String authorization) {
            this.addHeader(HttpHeaders.AUTHORIZATION, authorization);
            return this;
        }

        public Builder addHeader(String header, String value) {
            this.headers.addHeader(header, value);
            return this;
        }

        public Builder addHeaders(HTTPHeader headers) {
            this.headers.addHeaders(headers);
            return this;
        }

        public Builder addCookies(List<Cookie> cookies) {
            this.cookies.addAll(cookies);
            return this;
        }

        public HTTPRequest build() {
            HTTPRequest request = new HTTPRequest(this.url);
            request.cookies = this.cookies;
            request.headers = this.headers;
            return request;
        }

    }

    private HTTPRequest(final String url) {
        this.url = url;
        this.cookies = new ArrayList<>();
        this.headers = new HTTPHeader();
    }

    public String getUrl() {
        return url;
    }

    public HTTPHeader getHeaders() {
        return headers;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public boolean hasCookies() {
        return cookies != null && !cookies.isEmpty();
    }

    public boolean hasHeaders() {
        return headers != null && !headers.isEmpty();
    }

}
