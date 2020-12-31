package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerActionPacket extends Packet {

    private long entityId;
    private Action action;
    private Vector position;
    private int face;

    @Override
    public int getPacketId() {
        return Protocol.PLAYER_ACTION_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.entityId = this.readUnsignedVarLong();
        this.action = Action.values()[this.readSignedVarInt()];
        this.position = new Vector( this.readSignedVarInt(), this.readUnsignedVarInt(), this.readSignedVarInt() );
        this.face = this.readSignedVarInt();
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarLong( this.entityId );
        this.writeSignedVarInt( this.action.ordinal() );
        this.writeSignedVarInt( this.position.getFloorX() );
        this.writeUnsignedVarInt( this.position.getFloorY() );
        this.writeSignedVarInt( this.position.getFloorZ() );
        this.writeSignedVarInt( this.face );
    }

    public enum Action {
        START_BREAK,
        ABORT_BREAK,
        STOP_BREAK,
        GET_UPDATED_BLOCK,
        DROP_ITEM,
        START_SLEEPING,
        STOP_SLEEPING,
        RESPAWN,
        JUMP,
        START_SPRINT,
        STOP_SPRINT,
        START_SNEAK,
        STOP_SNEAK,
        DIMENSION_CHANGE_REQUEST,
        DIMENSION_CHANGE_ACK,
        START_GLIDE,
        STOP_GLIDE,
        BUILD_DENIED,
        CONTINUE_BREAK,
        SET_ENCHANTMENT_SEED,
        START_SWIMMING,
        STOP_SWIMMING,
        START_SPIN_ATTACK,
        STOP_SPIN_ATTACK,
        ACTION_INTERACT_BLOCK
    }
}
