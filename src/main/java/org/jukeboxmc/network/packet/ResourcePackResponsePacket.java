package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.packet.type.ResourcePackEntry;
import org.jukeboxmc.network.packet.type.ResourcePackStatus;
import org.jukeboxmc.utils.BinaryStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class ResourcePackResponsePacket extends Packet {

    private final List<ResourcePackEntry> resourcePackEntries = new ArrayList<>();

    private ResourcePackStatus responseStatus;

    @Override
    public int getPacketId() {
        return Protocol.RESOURCE_PACK_RESPONSE_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        byte responseStatusId = stream.readByte();
        this.responseStatus = ResourcePackStatus.retrieveResponseStatusById(responseStatusId);

        short resourcePackEntriesLength = stream.readLShort();
        for (int i = 0; i < resourcePackEntriesLength; i++) {
            final String[] resourcePackEntryElements = stream.readString().split("_");
            final String resourcePackEntryUuid = resourcePackEntryElements[0];
            final String resourcePacketEntryVersion = resourcePackEntryElements[1];

            this.resourcePackEntries.add(new ResourcePackEntry(resourcePackEntryUuid, resourcePacketEntryVersion));
        }
    }
}
