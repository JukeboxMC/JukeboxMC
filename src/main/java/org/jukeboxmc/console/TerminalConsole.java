package org.jukeboxmc.console;

import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jukeboxmc.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TerminalConsole extends SimpleTerminalConsole {

    private final Server server;
    private final ExecutorService executor;

    public TerminalConsole( Server server ) {
        this.server = server;
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void runCommand( String command ) {
        if(command.equals( "checkexception" )) {
            Exception exception = new Exception();
            exception.setStackTrace( this.server.getMainThread().getStackTrace() );
            exception.printStackTrace(System.out);
        }
        if ( this.isRunning() ) {
            this.server.getScheduler().execute( () -> {
                this.server.dispatchCommand( this.server.getConsoleSender(), command );
            } );
        }
    }

    @Override
    protected void shutdown() {
        this.executor.shutdownNow();
    }

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

    @Override
    public void start() {
        super.start();
    }

    public void startConsole() {
        this.executor.execute( this::start );
    }

    public void stopConsole() {
        this.executor.shutdownNow();
    }
}
