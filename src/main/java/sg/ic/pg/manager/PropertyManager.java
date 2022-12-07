package sg.ic.pg.manager;

import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import javax.inject.Singleton;
import sg.ic.pg.util.property.Constant;
import sg.ic.pg.util.property.Property;

@Singleton
public class PropertyManager extends BaseManager {

    private static PropertyManager instance;

    private Properties prop;

    private PropertyManager() {
        log = getLogger(this.getClass());
        final String methodName = "Constructor";
        start(methodName);
        prop = new Properties();
        try {
            log.debug(methodName, "Loading Common Properties");
            prop.load(PropertyManager.class.getClassLoader().getResourceAsStream(Constant.COMMON_PROPERTY_FILENAME));

            log.debug(methodName, "Loading Environment Properties");
            prop.load(PropertyManager.class.getClassLoader().getResourceAsStream(Constant.PROPERTY_FILENAME));

        } catch (Exception ex) {
            log.error("PropertyManager", "Error Loading Properties File", ex);
        }
        completed(methodName);
    }

    public Set<Object> getKeySet() {
        return prop.keySet();
    }

    public String getProperty(String key) {
        return getProperty(key, "");
    }

    public int getIntProperty(String key) {
        return getIntProperty(key, 0);
    }

    public int getIntProperty(String key, int defaultValue) {
        return Integer.parseInt(getProperty(key, String.valueOf(defaultValue)));
    }

    public long getLongProperty(String key) {
        return getLongProperty(key, 0);
    }

    public long getLongProperty(String key, long defaultValue) {
        return Long.parseLong(getProperty(key, String.valueOf(defaultValue)));
    }

    public double getDoubleProperty(String key) {
        return getDoubleProperty(key, 0);
    }

    public double getDoubleProperty(String key, double defaultValue) {
        return Double.parseDouble(getProperty(key, String.valueOf(defaultValue)));
    }

    public boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean((getProperty(key, "false")));
    }

    private String getProperty(String key, String defaultValue) {
        if (prop != null) {
            return prop.getProperty(key, defaultValue);
        }
        return defaultValue;
    }

    public void shutdown() {
        prop.clear();
    }

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }
}
