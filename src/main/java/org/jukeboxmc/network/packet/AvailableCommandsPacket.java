package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.annotation.Parameter;
import org.jukeboxmc.command.annotation.ParameterSection;
import org.jukeboxmc.command.validator.EnumValidator;
import org.jukeboxmc.command.validator.Validator;
import org.jukeboxmc.network.Protocol;

import java.util.*;

/**
 * @author Kaooot
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class AvailableCommandsPacket extends Packet {

    private List<Command> commands = new ArrayList<>();

    @Override
    public int getPacketId() {
        return Protocol.AVAILABLE_COMMANDS_PACKET;
    }

    @SneakyThrows
    @Override
    public void write() {
        super.write();
        int flag_valid = 0x100000;
        int flag_enum = 0x200000;
        int flag_postfix = 0x1000000;

        LinkedHashSet<String> enumValues = new LinkedHashSet<>();
        LinkedHashSet<String> postFixes = new LinkedHashSet<>();
        LinkedHashMap<String, List<String>> enums = new LinkedHashMap<>();

        for ( Command command : this.commands ) {
            enumValues.add( command.getName() );
            enumValues.addAll( command.getAliases() );

            if ( !command.getAliases().isEmpty() ) {
                ArrayList<String> aliases = new ArrayList<>( command.getAliases() );
                aliases.add( command.getName() );
                enums.put( command.getName(), aliases );
                enumValues.addAll( command.getAliases() );
            }

            for ( Parameter parameter : command.getParameters() ) {
                postFixes.add( parameter.postfix() );

                Validator validator = parameter.validator().newInstance();
                if ( validator instanceof EnumValidator ) {
                    if ( validator.getValues().isEmpty() ) {
                        enums.put( validator.getName(), Arrays.asList( parameter.arguments().clone() ) );
                        enumValues.addAll( Arrays.asList( parameter.arguments().clone() ) );
                    } else {
                        enums.put( validator.getName(), validator.getValues() );
                        enumValues.addAll( validator.getValues() );
                    }
                }
            }
        }

        this.writeUnsignedVarInt( enumValues.size() );
        for ( String enumValue : enumValues ) {
            this.writeString( enumValue );
        }

        this.writeUnsignedVarInt( postFixes.size() );
        for ( String postfix : postFixes ) {
            this.writeString( postfix );
        }

        this.writeUnsignedVarInt( enums.entrySet().size() );
        for ( Map.Entry<String, List<String>> enumEntry : enums.entrySet() ) {
            String commandName = enumEntry.getKey();
            List<String> aliases = enumEntry.getValue();
            this.writeString( commandName );

            this.writeUnsignedVarInt( aliases.size() );
            for ( String alias : aliases ) {
                int i = new ArrayList<>( enumValues ).indexOf( alias );

                if ( i >= 0 ) {
                    if ( enumValues.size() < 256 ) {
                        this.writeByte( i );
                    } else if ( enumValues.size() < 65536 ) {
                        this.writeShort( (short) i );
                    } else {
                        this.writeInt( i );
                    }
                }
            }
        }

        this.writeUnsignedVarInt( this.commands.size() );
        for ( Command command : this.commands ) {
            String commandName = command.getName();
            String commandDescription = command.getDescription();
            short commandFlags = 0;
            byte commandPermission = 0;

            this.writeString( commandName );
            this.writeString( commandDescription );
            this.writeLShort( commandFlags );
            this.writeByte( commandPermission );

            if ( command.getAliases().isEmpty() ) {
                this.writeLInt( -1 );
            } else {
                this.writeLInt( new ArrayList<>( enums.values() ).indexOf( enums.get( commandName ) ) );
            }

            this.writeUnsignedVarInt( command.getParameterSections().size() );
            for ( ParameterSection parameterSection : command.getParameterSections() ) {
                Parameter[] parameters = parameterSection.value();

                this.writeUnsignedVarInt( parameters.length );

                for ( Parameter parameter : parameters ) {
                    this.writeString( parameter.name() );

                    int type = 0;
                    String postfix = parameter.postfix();

                    if ( !postfix.isEmpty() ) {
                        int i = new ArrayList<>( postFixes ).indexOf( postfix );

                        if ( i >= 0 ) {
                            type = flag_postfix | i;
                        }
                    } else {
                        type |= flag_valid;
                        Validator validator = parameter.validator().newInstance();
                        if ( !( validator instanceof EnumValidator ) ) {
                            type |= validator.getCommandParamType().getIdentifier();
                        } else {
                            int indexOf = new ArrayList<>( enums.values() ).indexOf( enums.get( validator.getName() ) );
                            type |= flag_enum | indexOf;
                        }
                    }
                    this.writeLInt( type );
                    this.writeBoolean( parameter.optional() );
                    this.writeByte( 0 ); // parameter options
                }
            }
        }
        this.writeUnsignedVarInt( 0 ); // soft enums size
        this.writeUnsignedVarInt( 0 );
    }
}