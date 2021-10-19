package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class PacketViolationWarningPacket extends Packet {

    private PacketViolationType type;
    private PacketViolationSeverity severity;
    private int packetId;
    private String context;

    @Override
    public int getPacketId() {
        return Protocol.PACKET_VIOLATION_WARNING_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.type = PacketViolationType.values()[stream.readSignedVarInt() + 1];
        this.severity = PacketViolationSeverity.values()[stream.readSignedVarInt() + 1];
        this.packetId = stream.readSignedVarInt();
        this.context = stream.readString();
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
