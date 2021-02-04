package org.jukeboxmc.console;

import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jukeboxmc.Server;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TerminalConsole extends SimpleTerminalConsole {

    private Server server;
    private ConsoleThread consoleThread;

    public TerminalConsole( Server server ) {
        this.server = server;
        this.consoleThread = new ConsoleThread( this );
    }

    @Override
    protected void runCommand( String command ) {
        if ( command.startsWith( "stop" ) ) {
            for ( World world : this.server.getWorlds() ) {
                this.server.unloadWorld( world.getName() );
            }
            this.server.shutdown();
        } else if ( command.startsWith( "save" ) ) {
            for ( World world : this.server.getWorlds() ) {
                if ( world != null ) {
                    world.save();
                }
            }
        }
    }

    @Override
    protected void shutdown() {
        this.server.shutdown();
    }

    @Override
    protected boolean isRunning() {
        return !this.server.isShutdown();
    }

    @Override
    protected LineReader buildReader( LineReaderBuilder builder ) {
        builder.completer( new CommandCompleter( this.server ) );
        builder.appName( "JukeboxMC" );
        builder.option( LineReader.Option.HISTORY_BEEP, false );
        builder.option( LineReader.Option.HISTORY_IGNORE_DUPS, true );
        builder.option( LineReader.Option.HISTORY_IGNORE_SPACE, true );
        return super.buildReader( builder );
    }

    @Override
    public void start() {
        super.start();
    }

    public ConsoleThread getConsoleThread() {
        return consoleThread;
    }
}
