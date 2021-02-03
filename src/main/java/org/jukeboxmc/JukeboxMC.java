package org.jukeboxmc;

import io.netty.util.ResourceLeakDetector;
import org.jukeboxmc.logger.Logger;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class JukeboxMC {

    private static JukeboxMC instance;

    private Logger logger;
    private Server server;

    public static void main( String[] args ) {
        JukeboxMC.setInstance( new JukeboxMC() );
    }

    private JukeboxMC() {
        ResourceLeakDetector.setLevel( ResourceLeakDetector.Level.DISABLED );

        this.logger = Logger.getInstance();
        this.logger.info( "Server is started...." );

        this.server = new Server( this.logger );
        this.server.startServer();

        this.logger.info( "JukeboxMC is now running on the port " + this.server.getAddress().getPort() );
    }

    public static JukeboxMC getInstance() {
        return instance;
    }

    public static void setInstance( JukeboxMC instance ) {
        JukeboxMC.instance = instance;
    }

    public Server getServer() {
        return this.server;
    }

    public Logger getLogger() {
        return this.logger;
    }
}
