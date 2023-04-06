package org.jukeboxmc.command;

import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.command.CommandEnumConstraint;
import org.cloudburstmc.protocol.bedrock.data.command.CommandEnumData;

import java.util.*;

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

    protected CommandEnumData toNetwork() {
        String[] aliases;
        if ( this.values.size() > 0 ) {
            List<String> aliasList = new ArrayList<>( this.values );
            aliasList.add( this.name );
            aliases = aliasList.toArray( new String[0] );
        } else {
            aliases = new String[]{ this.name };
        }
        return new CommandEnumData( this.name + "Aliases", this.toNetwork( aliases ), false );
    }

    protected LinkedHashMap<String, Set<CommandEnumConstraint>> toNetwork( String[] values ) {
        LinkedHashMap<String, Set<CommandEnumConstraint>> map = new LinkedHashMap<>();
        for ( String value : values ) {
            map.put( value, Collections.emptySet() );
        }
        return map;
    }
}
