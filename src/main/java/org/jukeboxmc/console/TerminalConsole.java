package org.jukeboxmc.console;

import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jukeboxmc.Server;

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
            this.server.unloadWorld( "world" );
            this.server.shutdown();
        } else if ( command.startsWith( "save" ) ) {
            this.server.getWorld( "world" ).save();
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

    public ConsoleThread getConsoleThread() {
        return consoleThread;
    }
}
