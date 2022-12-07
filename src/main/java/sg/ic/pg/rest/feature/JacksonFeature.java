package sg.ic.pg.rest.feature;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import sg.ic.pg.util.json.CustomObjectMapper;

@Provider
public class JacksonFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(getJsonProvider());
        return true;
    }

    private JacksonJaxbJsonProvider getJsonProvider() {
        return new JacksonJaxbJsonProvider(CustomObjectMapper.getInstance().getObjectMapper(),
                JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
    }
    
}
