package org.jukeboxmc;

import io.netty.util.ResourceLeakDetector;
import lombok.Getter;
import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.network.packet.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
public class Bootstrap {

    private final Logger logger;
    private final Server server;

    public static void main( String[] args ) {
        new Bootstrap();
    }

    public Bootstrap() {
        ResourceLeakDetector.setLevel( ResourceLeakDetector.Level.DISABLED );
        this.logger = new Logger();

        this.logger.info( "Starting JukeboxMC (Bedrock Edition " + Protocol.MINECRAFT_VERSION + " with Protocol " + Protocol.CURRENT_PROTOCOL + ")" );
        this.server = new Server( this.logger );
    }
}
