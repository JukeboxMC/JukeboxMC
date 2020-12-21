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
public class AnimatePacket extends Packet {

    private long entityId;
    private Action action;
    private float rowingTime;

    @Override
    public int getPacketId() {
        return Protocol.ANIMATE_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.action = Action.getActionFromId( this.readSignedVarInt() );
        this.entityId = this.readUnsignedVarLong();
        if ( ( this.action.getId() & 0x80 ) != 0 ) {
            this.rowingTime = this.readLFloat();
        }
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarInt( this.action.getId() );
        this.writeUnsignedVarLong( this.entityId );

        if ( ( this.action.getId() & 0x80 ) != 0 ) {
            this.writeLFloat( this.rowingTime );
        }
    }

    public enum Action {
        NO_ACTION( 0 ),
        SWING_ARM( 1 ),
        WAKE_UP( 3 ),
        CRITICAL_HIT( 4 ),
        MAGIC_CRITICAL_HIT( 5 ),
        ROW_RIGHT( 128 ),
        ROW_LEFT( 129 );

        private int id;

        Action( int id ) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static Action getActionFromId( int id ) {
            switch ( id ) {
                case 1:
                    return SWING_ARM;
                case 3:
                    return WAKE_UP;
                case 4:
                    return CRITICAL_HIT;
                case 5:
                    return MAGIC_CRITICAL_HIT;
                case 128:
                    return ROW_RIGHT;
                case 129:
                    return ROW_LEFT;
                default:
                    return NO_ACTION;
            }
        }
    }
}
