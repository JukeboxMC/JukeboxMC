package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.Particle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SpawnParticleEffectPacket extends Packet {

    private Dimension dimension;
    private long entityId;
    private Vector position;
    private Particle particle;

    @Override
    public int getPacketId() {
        return Protocol.SPAWN_PARTICLE_EFFECT_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeByte( this.dimension.getId() );
        this.writeSignedVarLong( this.entityId );
        this.writeLFloat( this.position.getBlockX() );
        this.writeLFloat( this.position.getBlockY() );
        this.writeLFloat( this.position.getBlockZ() );
        this.writeString( this.particle.getIdentifer() );
    }
}
