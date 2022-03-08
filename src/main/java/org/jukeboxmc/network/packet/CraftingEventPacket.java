package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.utils.BinaryStream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class CraftingEventPacket extends Packet {

    private int windowId;
    private int type;
    private UUID uuid;

    private List<Item> input = new ArrayList<>();
    private List<Item> output = new ArrayList<>();

    @Override
    public int getPacketId() {
        return Protocol.CRAFTING_EVENT_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );

        this.windowId = stream.readByte();
        this.type = stream.readUnsignedVarInt();
        this.uuid = stream.readUUID();

        int count = stream.readUnsignedVarInt();
        for ( int i = 0; i < count; i++ ) {
            this.input.add( stream.readItem() );
        }

        count = stream.readUnsignedVarInt();
        for ( int i = 0; i < count; i++ ) {
            this.input.add( stream.readItem() );
        }
    }
}
