package org.jukeboxmc.command;

import lombok.NonNull;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.command.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Cloudburst
 * @version 1.0
 */
@ToString
public class CommandData {

    private String name;
    private String description;
    private String usage;
    private String permission;
    private String permissionMessage;
    private final CommandEnum aliases;
    private final List<CommandParameter[]> overloads;

    public CommandData( String name, String description, String usage, String permission, String permissionMessage, CommandEnum aliases, List<CommandParameter[]> overloads ) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.permissionMessage = permissionMessage;
        this.aliases = aliases;
        this.overloads = overloads;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder( @NonNull String commandName ) {
        return new Builder( commandName );
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission( String permission ) {
        this.permission = permission;
    }

    public List<String> getAliases() {
        return this.aliases.getValues();
    }

    public void removeAlias( String alias ) {
        this.aliases.getValues().remove( alias );
    }

    public List<CommandParameter[]> getOverloads() {
        return this.overloads;
    }

    public String getPermissionMessage() {
        return this.permissionMessage;
    }

    public void setPermissionMessage( String permissionMessage ) {
        this.permissionMessage = permissionMessage;
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage( String usage ) {
        this.usage = usage;
    }

    public CommandData rebuild() {
        if ( this.overloads.size() == 0 ) {
            this.overloads.add( new CommandParameter[]{ new CommandParameter( "args", CommandParamType.TEXT, true ) } );
        }
        return new CommandData( this.name, this.description, this.usage, this.permission, this.permissionMessage, new CommandEnum( this.name, this.getAliases() ), this.overloads );
    }

    public org.cloudburstmc.protocol.bedrock.data.command.CommandData toNetwork() {
        String description = this.description;

        CommandParamData[] overloadData = new CommandParamData[this.overloads.size()];
        for ( int i = 0; i < this.overloads.size(); i++ ) {
            CommandParameter[] parameters = this.overloads.get( i );
            for (CommandParameter parameter : parameters) {
                overloadData[i] = parameter.toNetwork();
            }
        }
        return new org.cloudburstmc.protocol.bedrock.data.command.CommandData( this.name, description, Collections.emptySet(),
                CommandPermission.ANY, this.aliases.toNetwork(), new ArrayList<>(), new CommandOverloadData[]{new CommandOverloadData(false, overloadData)} );
    }

    public static class Builder {
        private String name = "";
        private String description = "";
        private String usage = "";
        private String permission = "";
        private String permissionMessage = "";
        private List<String> aliases = new ArrayList<>();
        private List<CommandParameter[]> overloads = new ArrayList<>();

        public Builder() {
        }

        public Builder( @NonNull String name ) {
            this.name = name.toLowerCase();
        }

        public CommandData build() {
            if ( this.overloads.size() == 0 ) {
                this.overloads.add( new CommandParameter[]{ new CommandParameter( "args", CommandParamType.TEXT, true ) } );
            }
            return new CommandData( this.name, this.description, this.usage, this.permission, this.permissionMessage, new CommandEnum( this.name, this.aliases ), this.overloads );
        }

        public Builder setDescription( @NonNull String description ) {
            this.description = description;
            return this;
        }

        public Builder setUsageMessage( @NonNull String usage ) {
            this.usage = usage;
            return this;
        }

        public Builder setPermissionMessage( @NonNull String message ) {
            this.permissionMessage = message;
            return this;
        }

        public Builder setPermissions( @NonNull String permissions ) {
            this.permission = permissions;
            return this;
        }

        public Builder setAliases( @NonNull String... aliases ) {
            this.aliases = Arrays.asList( aliases );
            return this;
        }

        public Builder addAlias( @NonNull String alias ) {
            this.aliases.add( alias );
            return this;
        }

        public Builder addAliases( @NonNull String... aliases ) {
            this.aliases.addAll( Arrays.asList( aliases ) );
            return this;
        }

        public Builder setParameters( @NonNull CommandParameter[]... paramSet ) {
            this.overloads = Arrays.asList( paramSet );
            return this;
        }

        public Builder setParameters( @NonNull List<CommandParameter[]> parameters ) {
            this.overloads = parameters;
            return this;
        }

        public Builder addParameters( @NonNull CommandParameter[]... paramSet ) {
            this.overloads.addAll( Arrays.asList( paramSet ) );
            return this;
        }

    }
}
