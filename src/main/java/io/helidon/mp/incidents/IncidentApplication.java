package io.helidon.mp.incidents;


import java.util.Set;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.helidon.common.CollectionsHelper;
import io.helidon.mp.incidents.resource.IncidentEndpoint;

@ApplicationScoped
@ApplicationPath("/")
public class IncidentApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		return CollectionsHelper.setOf(IncidentEndpoint.class);
	}

}
