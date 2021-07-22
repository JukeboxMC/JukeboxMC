package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.command.annotation.Parameter;
import org.jukeboxmc.command.annotation.ParameterSection;
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

    /*@Override
    public void write() {
        final List<String> values = new ArrayList<>();
        final List<String> postfixes = new ArrayList<>();
        final List<String> enums = new ArrayList<>();
        commands.forEach( command -> {
            *//*command.getAliases().forEach( alias -> {

            } );*//*
            enums.addAll(command.getAliases());
            command.getParameterSections().forEach( overload -> {
                for ( Parameter param : overload.value() ) {
                    values.addAll( Arrays.asList( param.arguments() ) );
                    enums.addAll( Arrays.asList( param.arguments() ) );
                    postfixes.add(  param.postfix() );
                }
            } );
        } );

        writeUnsignedVarInt( values.size() );
        values.forEach( this::writeString );

        writeUnsignedVarInt( postfixes.size() );
        postfixes.forEach( this::writeString );

        writeUnsignedVarInt( enums.size() );
        enums.forEach( it -> {
            writeString( String.valueOf( it.hashCode() ) );
            this.writeUnsignedVarInt( it.size() );

            for ( String alias : aliases ) {
                int i = enumValues.indexOf( alias );

                if ( i >= 0 ) {
                    if ( enumValues.size() < 256 ) {
                        System.out.println( "i (byte): " + i );
                        this.writeByte( i );
                    } else if ( enumValues.size() < 65536 ) {
                        System.out.println( "i (short): " + i );
                        this.writeShort( (short) i );
                    } else {
                        System.out.println( "i (int): " + i );
                        this.writeInt( i );
                    }
                }
            }
        } );
    }*/


    @Override
    public void write() {
        super.write();
        int flag_valid = 0x100000;
        int flag_enum = 0x200000;
        int flag_postfix = 0x1000000;

        List<String> enumValues = new ArrayList<>();
        List<String> postFixes = new ArrayList<>();
        Map<String, List<String>> enums = new HashMap<>();

        for ( Command command : this.commands ) {
            enumValues.add( command.getName() );
            enumValues.addAll( command.getAliases() );

            for ( Parameter parameter : command.getParameters() ) {
                postFixes.add( parameter.postfix() );
            }

            enums.put( command.getName(), command.getAliases() );
        }

        System.out.println( "enumValues.size(): " + enumValues.size() );
        this.writeUnsignedVarInt( enumValues.size() );

        for ( String enumValue : enumValues ) {
            System.out.println( "enumValue: " + enumValue );
            this.writeString( enumValue );
        }

        System.out.println( "postFixes.size(): " + postFixes.size() );
        this.writeUnsignedVarInt( postFixes.size() );

        for ( String postfix : postFixes ) {
            System.out.println( "postfix: " + postfix );
            this.writeString( postfix );
        }

        System.out.println( "enums.size(): " + enums.entrySet().size() );
        this.writeUnsignedVarInt( enums.entrySet().size() );

        for ( Map.Entry<String, List<String>> enumEntry : enums.entrySet() ) {
            String commandName = enumEntry.getKey();
            List<String> aliases = enumEntry.getValue();

            System.out.println( "commandName: " + commandName );
            this.writeString( commandName );
            System.out.println( "aliases.size(): " + aliases.size() );
            this.writeUnsignedVarInt( aliases.size() );

            for ( String alias : aliases ) {
                int i = enumValues.indexOf( alias );

                if ( i >= 0 ) {
                    if ( enumValues.size() < 256 ) {
                        System.out.println( "i (byte): " + i );
                        this.writeByte( i );
                    } else if ( enumValues.size() < 65536 ) {
                        System.out.println( "i (short): " + i );
                        this.writeShort( (short) i );
                    } else {
                        System.out.println( "i (int): " + i );
                        this.writeInt( i );
                    }
                }
            }
        }

        System.out.println( "this.commands.size(): " + this.commands.size() );
        this.writeUnsignedVarInt( this.commands.size() );

        for ( Command command : this.commands ) {
            String commandName = command.getName();
            String commandDescription = command.getDescription();
            short commandFlags = 0;
            byte commandPermission = 0;

            System.out.println( "commandName: " + commandName );
            System.out.println( "commandDescription: " + commandDescription );
            System.out.println( "commandFlags: " + commandFlags );
            System.out.println( "commandPermission: " + commandPermission );
            this.writeString( commandName );
            this.writeString( commandDescription );
            this.writeLShort( commandFlags );
            this.writeByte( commandPermission );
            System.out.println( "command aliases LInt: " + ( command.getAliases().isEmpty() ? -1 :
                    enums.get( commandName ).indexOf( command.getAliases() ) ) );
            this.writeLInt( ( command.getAliases().isEmpty() ? -1 :
                    enums.get( commandName ).indexOf( command.getAliases() ) ) );
            System.out.println( "command.getParameterSections().size(): " + command.getParameterSections().size() );
            this.writeUnsignedVarInt( command.getParameterSections().size() );

            for ( ParameterSection parameterSection : command.getParameterSections() ) {
                Parameter[] parameters = parameterSection.value();

                System.out.println( "parameters.length: " + parameters.length );
                this.writeUnsignedVarInt( parameters.length );

                for ( Parameter parameter : parameters ) {
                    System.out.println( "parameter.name(): " + parameter.name() );
                    this.writeString( parameter.name() );

                    int type = 0;
                    String postfix = parameter.postfix();

                    if ( postfix != null ) {
                        int i = postFixes.indexOf( postfix );

                        if ( i >= 0 ) {
                            type = flag_postfix | i;
                        }
                    } else {
                        type |= flag_valid;

                        if ( parameter.arguments() != null ) {
                            type |= flag_enum | enums.get( commandName ).indexOf( parameter.arguments() );
                        } else {
                            type |= parameter.type().getIdentifier();
                        }
                    }

                    System.out.println( "type: " + type );
                    this.writeLInt( type );
                    System.out.println( "parameter.optional(): " + parameter.optional() );
                    this.writeBoolean( parameter.optional() );
                    this.writeByte( 0 ); // parameter options
                }
            }
        }

        this.writeUnsignedVarInt( 0 ); // soft enums size
        this.writeUnsignedVarInt( 0 );
    }
}