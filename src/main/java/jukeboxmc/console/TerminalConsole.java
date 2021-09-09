package jukeboxmc.console;

import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jukeboxmc.Server;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TerminalConsole extends SimpleTerminalConsole {

    private final Server server;
    private final ConsoleThread consoleThread;

    public TerminalConsole( Server server ) {
        this.server = server;
        this.consoleThread = new ConsoleThread( this );
    }

    @Override
    protected void runCommand( String command ) {
        if ( this.isRunning() ) {
            this.server.addToMainThread( () -> {
                this.server.dispatchCommand( this.server.getConsoleSender(), command );
            } );
        }
    }

    @Override
    protected void shutdown() {}

    @Override
    protected boolean isRunning() {
        return this.server.isRunning();
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

    public void startConsole() {
        this.consoleThread.start();
    }

    public void stopConsole() {
        this.consoleThread.interrupt();
    }
}
