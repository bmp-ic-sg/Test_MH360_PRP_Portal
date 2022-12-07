package sg.ic.pg.manager;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@Singleton
@ApplicationPath("rest")
public class ServiceManager extends ResourceConfig {
	public ServiceManager() {

		// Packages to Auto Discover
		packages("sg.ic.pg.rest");
	}
}
