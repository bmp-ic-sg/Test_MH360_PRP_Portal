package sg.ic.pg.util.http;

import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import sg.ic.pg.util.log.BaseLogger;

@Singleton
public class HTTPConnectionManager {

    private static HTTPConnectionManager instance;

    private PoolingHttpClientConnectionManager connManager;

    private BaseLogger log;

    private HTTPConnectionManager() {

        log = new BaseLogger(HTTPConnectionManager.class);
        connManager = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", trustSelfSignCert()).build());
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(20);
    }

    public static HTTPConnectionManager getInstance() {
        if (instance == null) {
            instance = new HTTPConnectionManager();
        }
        return instance;
    }

    public PoolingHttpClientConnectionManager getConnectionManager() {
        return connManager;
    }

    private SSLConnectionSocketFactory trustSelfSignCert() {
        SSLConnectionSocketFactory sslConnFactory = null;
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial((chain, authType) -> true).build();
            sslConnFactory = new SSLConnectionSocketFactory(sslContext, new String[] {"TLSv1.2"}, null,
                    NoopHostnameVerifier.INSTANCE);
        } catch (Exception ex) {
            log.error("trustSelfSignCert", ex);
        }
        return sslConnFactory;
    }
}
