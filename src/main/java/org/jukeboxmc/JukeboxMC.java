package org.jukeboxmc;

import io.netty.util.ResourceLeakDetector;
import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.network.packet.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class JukeboxMC {

    private static JukeboxMC instance;
    private final Logger logger;
    private final Server server;

    public static void main( String[] args ) {
        JukeboxMC.setJukeboxMC( new JukeboxMC() );
    }

    private JukeboxMC() {
        ResourceLeakDetector.setLevel( ResourceLeakDetector.Level.DISABLED );

        this.logger = new Logger();

        this.logger.info( "Starting JukeboxMC (Bedrock Edition " + Protocol.MINECRAFT_VERSION + " with Protocol " + Protocol.CURRENT_PROTOCOL + ")" );
        this.server = new Server( this.logger );
    }

    public static void setJukeboxMC( JukeboxMC instance ) {
        JukeboxMC.instance = instance;
    }

    public static JukeboxMC getJukeboxMC() {
        return instance;
    }

    public Server getServer() {
        return this.server;
    }

    public Logger getLogger() {
        return this.logger;
    }
}
