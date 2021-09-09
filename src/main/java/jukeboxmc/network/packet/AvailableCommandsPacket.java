package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.jukeboxmc.command.Command;
import org.jukeboxmc.utils.BinaryStream;

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
    public void write( BinaryStream stream ) {
        super.write(stream);
        int flag_valid = 0x100000;
        int flag_enum = 0x200000;
        int flag_postfix = 0x1000000;

        LinkedHashSet<String> enumValues = new LinkedHashSet<>();
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

        }

        stream.writeUnsignedVarInt( enumValues.size() );
        for ( String enumValue : enumValues ) {
            stream.writeString( enumValue );
        }

        stream.writeUnsignedVarInt( 0 );

        stream.writeUnsignedVarInt( enums.entrySet().size() );
        for ( Map.Entry<String, List<String>> enumEntry : enums.entrySet() ) {
            String commandName = enumEntry.getKey();
            List<String> aliases = enumEntry.getValue();
            stream.writeString( commandName );

            stream.writeUnsignedVarInt( aliases.size() );
            for ( String alias : aliases ) {
                int i = new ArrayList<>( enumValues ).indexOf( alias );

                if ( i >= 0 ) {
                    if ( enumValues.size() < 256 ) {
                        stream.writeByte( i );
                    } else if ( enumValues.size() < 65536 ) {
                        stream.writeShort( (short) i );
                    } else {
                        stream.writeInt( i );
                    }
                }
            }
        }

        stream.writeUnsignedVarInt( this.commands.size() );
        for ( Command command : this.commands ) {
            String commandName = command.getName();
            String commandDescription = command.getDescription();
            short commandFlags = 0;
            byte commandPermission = 0;

            stream.writeString( commandName );
            stream.writeString( commandDescription );
            stream.writeLShort( commandFlags );
            stream.writeByte( commandPermission );

            if ( command.getAliases().isEmpty() ) {
                stream.writeLInt( -1 );
            } else {
                stream.writeLInt( new ArrayList<>( enums.values() ).indexOf( enums.get( commandName ) ) );
            }

            stream.writeUnsignedVarInt( 0 );
        }
        stream.writeUnsignedVarInt( 0 ); // soft enums size
        stream.writeUnsignedVarInt( 0 );
    }
}