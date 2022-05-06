package org.jukeboxmc.command;

import lombok.ToString;
import org.jukeboxmc.command.annotation.Description;
import org.jukeboxmc.command.annotation.Name;
import org.jukeboxmc.command.annotation.Permission;

/**
 * @author Kaooot, LucGamesYT
 * @version 1.0
 */
@ToString
public abstract class Command {

    protected CommandData commandData;

    public Command( CommandData commandData ) {
        this.commandData = commandData;
        Class<?> clazz = this.getClass();

        if ( clazz.isAnnotationPresent( Name.class ) ) {
            this.commandData.setName( clazz.getAnnotation( Name.class ).value() );
        }

        if ( clazz.isAnnotationPresent( Description.class ) ) {
            this.commandData.setDescription( clazz.getAnnotation( Description.class ).value() );
        }

        if ( clazz.isAnnotationPresent( Permission.class ) ) {
            this.commandData.setPermission( clazz.getAnnotation( Permission.class ).value() );
        }

        this.commandData = this.commandData.rebuild();
    }

    /**
     * This method is called when the command is executed.
     *
     * @param commandSender The sender of the command.
     * @param command       The command that was executed.
     * @param args          The arguments of the command.
     */
    public abstract void execute( CommandSender commandSender, String command, String[] args );

    public CommandData getCommandData() {
        return this.commandData;
    }
}