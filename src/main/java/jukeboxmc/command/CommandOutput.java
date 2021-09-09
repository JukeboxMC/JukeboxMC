package jukeboxmc.command;

import lombok.Getter;

/**
 * @author Kaooot
 * @version 1.0
 */
@Getter
public class CommandOutput {

    private boolean success;
    private String message;

    public CommandOutput() {

    }

    /**
     * Creates a new {@link CommandOutput} with a message which describes whether the procession
     * of the {@link Command} was a success or not
     *
     * @param success whether the {@link Command} was executed successfully
     * @param message which represents the message which describes the success
     */
    private CommandOutput( boolean success, String message ) {
        this.success = success;
        this.message = message;
    }

    /**
     * Retrieves a successful {@link CommandOutput}
     *
     * @param message which stands for the message which should be sent
     *
     * @return a fresh {@link CommandOutput}
     */
    public CommandOutput success( String message ) {
        return new CommandOutput( true, message );
    }

    /**
     * Retrieves a {@link CommandOutput} which was a failure
     *
     * @param message which stands for the message which should be sent
     *
     * @return a fresh {@link CommandOutput}
     */
    public CommandOutput fail( String message ) {
        return new CommandOutput( false, message );
    }

    /**
     * Retrieves a {@link CommandOutput} which was a failure
     *
     * @param throwable which describes the failure
     *
     * @return a fresh {@link CommandOutput}
     */
    public CommandOutput fail( Throwable throwable ) {
        return this.fail( throwable.getMessage() );
    }
}