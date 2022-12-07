package sg.ic.pg.controller;

import sg.ic.pg.manager.PropertyManager;
import sg.ic.pg.util.log.AppLogger;

public class BaseController {

    protected AppLogger log;

    public BaseController() {
        // Empty Constructor
    }

    protected <T> AppLogger getLogger(Class<T> clazz) {
        return new AppLogger(clazz);
    }

    protected void start(String methodName) {
        log.debug(methodName, "Start");
    }

    protected void completed(String methodName) {
        log.debug(methodName, "Completed");
    }

    protected String getProperty(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }

    protected int getIntProperty(String key) {
        return PropertyManager.getInstance().getIntProperty(key);
    }

    protected boolean getBoolProperty(String key) {
        return PropertyManager.getInstance().getBooleanProperty(key);
    }
}
