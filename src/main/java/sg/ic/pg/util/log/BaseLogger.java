package sg.ic.pg.util.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseLogger {
    protected final Logger log;

    public BaseLogger(Class<?> clazz) {
        log = LogManager.getLogger(clazz);
    }

    // DEBUG METHODS
    public void debug(String text) {

        log.debug(() -> text);
    }

    public void debug(String methodName, String text) {

        log.debug(() -> format(methodName, text));
    }

    public void debug(String id, String methodName, String text) {

        log.debug(() -> format(id, methodName, text));
    }

    // INFO METHODS
    public void info(String text) {
        log.info(() -> text);
    }

    public void info(String methodName, String text) {
        log.info(() -> format(methodName, text));
    }

    public void info(String id, String methodName, String text) {
        log.debug(() -> format(id, methodName, text));
    }

    // WARN METHODS
    public void warn(String text) {
        log.warn(() -> text);
    }

    public void warn(String methodName, String text) {
        log.warn(() -> format(methodName, text));
    }

    public void warn(String id, String methodName, String text) {
        log.debug(() -> format(id, methodName, text));
    }

    // ERROR METHODS
    public void error(String methodName, String text) {
        log.error(() -> format(methodName, text));
    }

    public void error(String methodName, Exception ex) {
        log.error(() -> methodName, ex);
    }

    public void error(String methodName, String text, Exception ex) {
        log.error(() -> format(methodName, text), ex);
    }

    public void error(String id, String methodName, String text, Exception ex) {
        log.error(() -> format(id, methodName, text), ex);
    }

    protected String format(String id, String methodName, String text) {
        return "[" + id + "] [" + methodName + "] " + text;
    }

    protected String format(String methodName, String text) {
        return "[" + methodName + "] " + text;
    }
}
