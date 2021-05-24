package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.world.Particle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SpawnParticleEffectPacket extends Packet {

    private byte dimensionId;
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
        this.writeByte( this.dimensionId );
        this.writeSignedVarLong( this.entityId );
        this.writeLFloat( this.position.getFloorX() );
        this.writeLFloat( this.position.getFloorY() );
        this.writeLFloat( this.position.getFloorZ() );
        this.writeString( this.particle.getIdentifer() );
    }
}
