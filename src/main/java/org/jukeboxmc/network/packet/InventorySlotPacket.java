package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class InventorySlotPacket extends Packet {

    private WindowId windowId;
    private int slot;
    private Item item;

    @Override
    public int getPacketId() {
        return Protocol.INVENTORY_SLOT_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarInt( this.windowId.getId() );
        this.writeUnsignedVarInt( this.slot );

        this.writeSignedVarInt( this.item.getRuntimeId() );
        this.writeItem( this.item );

    }
}
