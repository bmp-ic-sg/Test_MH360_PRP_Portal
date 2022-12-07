package sg.ic.pg.rest.binder;

import org.atteo.classindex.ClassIndex;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import sg.ic.pg.controller.Controller;
import sg.ic.pg.manager.APIEncryptionManager;
import sg.ic.pg.rest.validator.Validator;
import sg.ic.pg.util.log.AppLogger;

public class ApplicationBinder extends AbstractBinder {
    private AppLogger log;

    public ApplicationBinder() {
        log = new AppLogger(this.getClass());
    }

    @Override
    protected void configure() {
        final String methodName = "configure";

        log.debug(methodName, "start");

        // Controllers
        ClassIndex.getAnnotated(Controller.class).forEach(this::bindAsContract);

        // Validators
        ClassIndex.getAnnotated(Validator.class).forEach(this::bindAsContract);

        // API Encryption Manager Singleton
        bind(APIEncryptionManager.getInstance()).to(APIEncryptionManager.class);

        log.debug(methodName, "completed");
    }

}
