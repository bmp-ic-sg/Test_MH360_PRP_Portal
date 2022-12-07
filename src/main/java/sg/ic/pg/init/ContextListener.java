package sg.ic.pg.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import sg.ic.pg.manager.APIEncryptionManager;
import sg.ic.pg.manager.AccessTokenManager;
import sg.ic.pg.manager.ConnectionManager;
import sg.ic.pg.manager.EncryptionManager;
import sg.ic.pg.manager.PropertyManager;
import sg.ic.pg.util.log.AppLogger;

@WebListener
public class ContextListener implements ServletContextListener {

    public final AppLogger log;

    public ContextListener() {
        log = new AppLogger(this.getClass());
    }

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        log.info("Application Init Started");
        PropertyManager.getInstance();
        EncryptionManager.getInstance();
        ConnectionManager.getInstance();
        AccessTokenManager.getInstance();
        APIEncryptionManager.getInstance();
        log.info("Application Init Completed");
    }

    @Override
    public void contextDestroyed(ServletContextEvent evt) {
        log.info("Application Shutdown Started");
        PropertyManager.getInstance().shutdown();
        EncryptionManager.getInstance().shutdown();
        ConnectionManager.getInstance().shutdown();
        AccessTokenManager.getInstance().shutdown();

        log.info("Application Shutdown Completed");
    }
}
