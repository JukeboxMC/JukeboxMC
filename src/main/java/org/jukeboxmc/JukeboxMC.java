package org.jukeboxmc;

import lombok.Getter;
import org.jukeboxmc.console.TerminalConsole;

import java.net.InetSocketAddress;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class JukeboxMC {

    @Getter
    private static JukeboxMC instance;
    @Getter
    private Server server;
    @Getter
    private InetSocketAddress address;

    public static void main( String[] args ) {
        instance = new JukeboxMC();
    }

    private JukeboxMC() {
        System.out.println( "Server wird gestartet...." );

        this.server = new Server();
        this.address = new InetSocketAddress( "127.0.0.1", 19132 );

        TerminalConsole terminalConsole = new TerminalConsole( this.server );
        terminalConsole.getConsoleThread().start();

        if ( this.server.startServer( this.address ) ) {
            System.out.println( "JukeboxMC läuft nun auf dem Port " + this.address.getPort() );
        }
    }
}
