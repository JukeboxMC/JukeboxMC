package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;
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
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeString( this.sound.getSound() );
        stream.writeSignedVarInt( this.position.getBlockX() );
        stream.writeUnsignedVarInt( this.position.getBlockY() );
        stream.writeSignedVarInt( this.position.getBlockZ() );
        stream.writeLFloat( this.volume );
        stream.writeLFloat( this.pitch );
    }
}
