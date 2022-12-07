package sg.ic.pg.manager;

import java.sql.Connection;
import java.sql.SQLException;
import javax.inject.Singleton;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import sg.ic.pg.util.property.Property;

@Singleton
public class ConnectionManager extends BaseManager {

    private static ConnectionManager instance;

    private HikariDataSource dataSource;

    private ConnectionManager() {
        log = getLogger(ConnectionManager.class);
        HikariConfig config = new HikariConfig();

        log.info("Initiating Connection to : " + getProperty(Property.DB_JDBC_URL));

        config.setUsername(getProperty(Property.DB_USERNAME));
        config.setPassword(EncryptionManager.getInstance().decrypt(getProperty(Property.DB_PASSWORD)));
        config.setJdbcUrl(getProperty(Property.DB_JDBC_URL));
        config.setDriverClassName(getProperty(Property.DB_DRIVER_CLASSNAME));

        config.setMaximumPoolSize(Integer.parseInt(getProperty(Property.DB_POOL_SIZE)));

        dataSource = new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private String getProperty(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }

    public void shutdown() {
        log.debug("shutdown", "Stopping Data Source");
        dataSource.close();
        dataSource = null;
        log.debug("shutdown", "Data Sources Stopped");
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
}
