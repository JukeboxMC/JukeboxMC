package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.packet.type.TextType;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class TextPacket extends Packet {

    private TextType type;
    private boolean isLocalized;
    private String sender;
    private String message;
    private String[] arguments = new String[0];
    private String xuid;
    private String deviceId;

    @Override
    public int getPacketId() {
        return Protocol.TEXT_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.type = TextType.values()[stream.readByte()];
        this.isLocalized = stream.readBoolean();

        switch ( this.type ) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                this.sender = stream.readString();
            case RAW:
            case TIP:
            case SYSTEM:
            case OBJECT:
            case OBJECT_WHISPER:
                this.message = stream.readString();
                break;
            case POPUP:
            case JUKEBOX_POPUP:
            case TRANSLATION:
                this.message = stream.readString();
                byte count = stream.readByte();
                this.arguments = new String[count];
                for ( int i = 0; i < count; i++ ) {
                    this.arguments[i] = stream.readString();
                }
        }
        this.xuid = stream.readString();
        this.deviceId = stream.readString();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeByte( this.type.ordinal() );
        stream.writeBoolean( false );

        if ( this.type == TextType.POPUP ) {
            this.message += "\n" + this.sender;
        }

        switch ( this.type ) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                stream.writeString( this.sender );

            case RAW:
            case TIP:
            case SYSTEM:
                stream.writeString( this.message );
                break;

            case POPUP:
            case JUKEBOX_POPUP:
            case TRANSLATION:
                stream.writeString( this.message );
                stream.writeByte( this.arguments.length );
                for ( String argument : this.arguments ) {
                    stream.writeString( argument );
                }
                break;
        }

        stream.writeString( this.xuid );
        stream.writeString( this.deviceId );
    }
}
