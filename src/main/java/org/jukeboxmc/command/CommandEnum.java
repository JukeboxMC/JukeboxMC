package org.jukeboxmc.command;

import com.nukkitx.protocol.bedrock.data.command.CommandEnumData;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cloudburst
 * @version 1.0
 */
@ToString
public class CommandEnum {

    private final String name;
    private final List<String> values;

    public CommandEnum( String name, List<String> values ) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getValues() {
        return this.values;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    protected @NotNull CommandEnumData toNetwork() {
        String[] aliases;
        if ( this.values.size() > 0 ) {
            List<String> aliasList = new ArrayList<>( this.values );
            aliasList.add( this.name );
            aliases = aliasList.toArray( new String[0] );
        } else {
            aliases = new String[]{ this.name };
        }
        return new CommandEnumData( this.name + "Aliases", aliases, false );
    }
}
