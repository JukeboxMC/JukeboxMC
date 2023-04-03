package org.jukeboxmc.command;

import com.google.common.collect.ImmutableMap;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.command.*;

import java.util.*;

/**
 * @author Cloudburst
 * @version 1.0
 */
@ToString
public class CommandParameter {

    private static final ImmutableMap<CommandParamType, CommandParam> PARAM_MAPPINGS = ImmutableMap.<CommandParamType, CommandParam>builder()
            .put( CommandParamType.INT, CommandParam.INT )
            .put( CommandParamType.FLOAT, CommandParam.FLOAT )
            .put( CommandParamType.VALUE, CommandParam.VALUE )
            .put( CommandParamType.WILDCARD_INT, CommandParam.WILDCARD_INT )
            .put( CommandParamType.OPERATOR, CommandParam.OPERATOR )
            .put( CommandParamType.TARGET, CommandParam.TARGET )
            .put( CommandParamType.WILDCARD_TARGET, CommandParam.WILDCARD_TARGET )
            .put( CommandParamType.FILE_PATH, CommandParam.FILE_PATH )
            .put( CommandParamType.INT_RANGE, CommandParam.INT_RANGE )
            .put( CommandParamType.STRING, CommandParam.STRING )
            .put( CommandParamType.POSITION, CommandParam.POSITION )
            .put( CommandParamType.BLOCK_POSITION, CommandParam.BLOCK_POSITION )
            .put( CommandParamType.MESSAGE, CommandParam.MESSAGE )
            .put( CommandParamType.TEXT, CommandParam.TEXT )
            .put( CommandParamType.JSON, CommandParam.JSON )
            .put( CommandParamType.COMMAND, CommandParam.COMMAND )
            .build();

    public String name;
    public CommandParamType type;
    public boolean optional;
    public byte options = 0;

    public CommandEnum enumData;
    public String postFix;

    public CommandParameter( String name, CommandParamType type, boolean optional ) {
        this.name = name;
        this.type = type;
        this.optional = optional;
    }

    public CommandParameter( String name, String enumType, boolean optional ) {
        this.name = name;
        this.enumData = new CommandEnum( enumType, new ArrayList<>() );
        this.type = CommandParamType.TEXT;
        this.optional = optional;
    }

    public CommandParameter( String name, String enumType ) {
        this( name, enumType, false );
    }

    public CommandParameter( String name, List<String> enumValues ) {
        this( name, enumValues, false );
    }

    public CommandParameter( String name, List<String> enumValues, boolean optional ) {
        this.name = name;
        this.type = CommandParamType.TEXT;
        this.enumData = new CommandEnum( name + "Enums", enumValues );
        this.optional = optional;
    }

    protected CommandParamData toNetwork() {
        CommandParamData commandParameter = new CommandParamData();
        commandParameter.setName( this.name );
        commandParameter.setOptional( this.optional );
        commandParameter.setPostfix( this.postFix );
        commandParameter.setEnumData( this.enumData != null ? new CommandEnumData( this.name, this.toNetwork(this.enumData.getValues().toArray( new String[0] )), false ) : null );
        commandParameter.setType( PARAM_MAPPINGS.get(this.type) );
        return commandParameter;
    }

    protected LinkedHashMap<String, Set<CommandEnumConstraint>> toNetwork( String[] values ) {
        LinkedHashMap<String, Set<CommandEnumConstraint>> map = new LinkedHashMap<>();
        for ( String value : values ) {
            map.put( value, Collections.emptySet() );
        }
        return map;
    }
}
