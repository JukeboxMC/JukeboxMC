package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.world.Sound;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class PlaySoundPacket extends Packet {

    private Sound sound;
    private Vector position;
    private float volume;
    public float pitch;

    @Override
    public int getPacketId() {
        return Protocol.PLAY_SOUND_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeString( this.sound.getSound() );
        this.writeSignedVarInt( this.position.getFloorX() );
        this.writeUnsignedVarInt( this.position.getFloorY() );
        this.writeSignedVarInt( this.position.getFloorZ() );
        this.writeLFloat( this.volume );
        this.writeLFloat( this.pitch );
    }
}
