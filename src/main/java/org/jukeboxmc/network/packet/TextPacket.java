package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class TextPacket extends Packet {

    private Type type;
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
    public void read() {
        super.read();
        this.type = Type.values()[this.readByte()];
        this.isLocalized = this.readBoolean();

        switch ( this.type ) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                this.sender = this.readString();
            case RAW:
            case TIP:
            case SYSTEM:
            case OBJECT:
            case OBJECT_WHISPER:
                this.message = this.readString();
                break;
            case POPUP:
            case JUKEBOX_POPUP:
            case TRANSLATION:
                this.message = this.readString();
                byte count = this.readByte();
                this.arguments = new String[count];
                for ( int i = 0; i < count; i++ ) {
                    this.arguments[i] = this.readString();
                }
        }
        this.xuid = this.readString();
        this.deviceId = this.readString();
    }

    @Override
    public void write() {
        super.write();
        this.writeByte( this.type.ordinal() );
        this.writeBoolean( false );

        if ( this.type == Type.POPUP ) {
            this.message += "\n" + this.sender;
        }

        switch ( this.type ) {
            case CHAT:
            case WHISPER:
            case ANNOUNCEMENT:
                this.writeString( this.sender );

            case RAW:
            case TIP:
            case SYSTEM:
                this.writeString( this.message );
                break;

            case POPUP:
            case JUKEBOX_POPUP:
            case TRANSLATION:
                this.writeString( this.message );
                this.writeByte( this.arguments.length );
                for ( String argument : this.arguments ) {
                    this.writeString( argument );
                }
                break;
        }

        this.writeString( this.xuid );
        this.writeString( this.deviceId );
    }

    public enum Type {
        RAW,
        CHAT,
        TRANSLATION,
        POPUP,
        JUKEBOX_POPUP,
        TIP,
        SYSTEM,
        WHISPER,
        ANNOUNCEMENT,
        OBJECT,
        OBJECT_WHISPER
    }
}
