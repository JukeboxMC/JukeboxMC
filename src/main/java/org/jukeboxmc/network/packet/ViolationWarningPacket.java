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
public class ViolationWarningPacket extends Packet {

    public PacketViolationType type;
    public PacketViolationSeverity severity;
    public int packetId;
    public String context;

    @Override
    public int getPacketId() {
        return Protocol.VIOLATION_WARNING_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.type = PacketViolationType.values()[this.readSignedVarInt() + 1];
        this.severity = PacketViolationSeverity.values()[this.readSignedVarInt()];
        this.packetId = this.readSignedVarInt();
        this.context = this.readString();
    }

    public enum PacketViolationType {
        UNKNOWN,
        MALFORMED_PACKET
    }

    public enum PacketViolationSeverity {
        UNKNOWN,
        WARNING,
        FINAL_WARNING,
        TERMINATING_CONNECTION
    }
}