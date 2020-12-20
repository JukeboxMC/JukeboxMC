package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;
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
    public void read() {
        super.read();
        int soundId = this.readUnsignedVarInt();
        this.levelSound = Arrays.stream(LevelSound.values()).filter( sound -> sound.getId() == soundId ).findFirst().orElse( LevelSound.UNDEFINED );
        this.position = new Vector( this.readLFloat(), this.readLFloat(), this.readLFloat() );
        this.extraData = this.readSignedVarInt();
        this.entityIdentifier = this.readString();
        this.isBabyMob = this.readBoolean();
        this.isGlobal = this.readBoolean();
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarInt( this.levelSound.getId() );
        this.writeLFloat( this.position.getX() );
        this.writeLFloat( this.position.getY() );
        this.writeLFloat( this.position.getZ() );
        this.writeSignedVarInt( this.extraData );
        this.writeString( this.entityIdentifier );
        this.writeBoolean( this.isBabyMob );
        this.writeBoolean( this.isGlobal );
    }
}
