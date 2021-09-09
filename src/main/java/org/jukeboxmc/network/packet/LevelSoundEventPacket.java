package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.world.LevelSound;

import java.util.Arrays;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class LevelSoundEventPacket extends Packet {

    private LevelSound levelSound;
    private Vector position;
    private int extraData = -1;
    private String entityIdentifier = ":";
    private boolean isBabyMob;
    private boolean isGlobal;

    @Override
    public int getPacketId() {
        return Protocol.LEVEL_SOUND_EVENT_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        int soundId = stream.readUnsignedVarInt();
        this.levelSound = Arrays.stream(LevelSound.values()).filter( sound -> sound.getId() == soundId ).findFirst().orElse( LevelSound.UNDEFINED );
        this.position = new Vector( stream.readLFloat(), stream.readLFloat(), stream.readLFloat() );
        this.extraData = stream.readSignedVarInt();
        this.entityIdentifier = stream.readString();
        this.isBabyMob = stream.readBoolean();
        this.isGlobal = stream.readBoolean();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeUnsignedVarInt( this.levelSound.getId() );
        stream.writeLFloat( this.position.getX() );
        stream.writeLFloat( this.position.getY() );
        stream.writeLFloat( this.position.getZ() );
        stream.writeSignedVarInt( this.extraData );
        stream.writeString( this.entityIdentifier );
        stream.writeBoolean( this.isBabyMob );
        stream.writeBoolean( this.isGlobal );
    }
}
