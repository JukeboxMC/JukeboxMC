package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author Kaooot
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SimulationTypePacket extends Packet {

    private SimulationType simulationType;

    @Override
    public int getPacketId() {
        return Protocol.SIMULATION_TYPE_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.simulationType = SimulationType.values()[this.readByte()];
    }

    @Override
    public void write() {
        super.write();
        this.writeByte( this.simulationType.ordinal() );
    }

    public enum SimulationType {
        GAME,
        EDITOR,
        TEST
    }
}