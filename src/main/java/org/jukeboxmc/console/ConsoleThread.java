package org.jukeboxmc.console;

public class ConsoleThread extends Thread {

    private final TerminalConsole console;

    public ConsoleThread( TerminalConsole console ) {
        super( "JukeboxMC Console" );
        this.console = console;
    }

    @Override
    public void run() {
        this.console.start();
    }
}
