package org.jukeboxmc;

import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.network.BedrockServer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Bootstrap {

    public static void main( String[] args ) {
        Logger logger = new Logger();
        logger.info( "Starting JukeboxMC (Bedrock Edition " + BedrockServer.CODEC.getMinecraftVersion() + " with Protocol " + BedrockServer.CODEC.getProtocolVersion() + ")" );
        new Server(logger);
    }
}
