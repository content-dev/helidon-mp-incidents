package io.helidon.mp.incidents;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

import io.helidon.microprofile.server.Server;

public final class Main {
	/**
     * Cannot be instantiated.
     */
    private Main() {
    }

    /**
     * Application main entry point.
     * @param args command line arguments
     * @throws IOException if there are problems reading logging properties
     */
    public static void main(final String[] args) throws IOException {
    	// load logging configuration
        setupLogging();

        // start the server
        Server server = startServer();
        
        // start the server
        	/**
        Server server = Server.builder()
        	      .addApplication(IncidentApplication.class)
        	      .port(8081)
        	      .build();
        	    server.start();
         	**/
        System.out.println("http://localhost:" + server.port() + "/incidents");
    }

    /**
     * Start the server.
     * @return the created {@link Server} instance
     */
    static Server startServer() {
        // Server will automatically pick up configuration from
        // microprofile-config.properties
        // and Application classes annotated as @ApplicationScoped
        return Server.create().start();
    }

    /**
     * Configure logging from logging.properties file.
     */
    private static void setupLogging() throws IOException {
        try (InputStream is = Main.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        }
    }
}
