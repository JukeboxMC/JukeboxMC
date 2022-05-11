package org.jukeboxmc;

import io.netty.util.ResourceLeakDetector;
import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.network.Network;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Bootstrap {

    private final Server server;

    public static void main( String[] args ) {
        new Bootstrap();
    }

    public Bootstrap() {
        ResourceLeakDetector.setLevel( ResourceLeakDetector.Level.DISABLED );
        Logger logger = new Logger();
        logger.info( "Starting JukeboxMC (Bedrock Edition " + Network.CODEC.getMinecraftVersion() + " with Protocol " + Network.CODEC.getProtocolVersion() + ")" );
        this.server = new Server( logger );
    }

    public Server getServer() {
        return this.server;
    }
}
