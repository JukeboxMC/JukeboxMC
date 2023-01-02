package org.jukeboxmc;

import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.network.Network;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Bootstrap {

    public static void main( String[] args ) {
        Logger logger = new Logger();
        logger.info( "Starting JukeboxMC (Bedrock Edition " + Network.CODEC.getMinecraftVersion() + " with Protocol " + Network.CODEC.getProtocolVersion() + ")" );
        new Server(logger);
    }
}
