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
public class InventoryContentPacket extends Packet {

    private WindowId windowId;
    private Item[] items;

    @Override
    public int getPacketId() {
        return Protocol.INVENTORY_CONTENT_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarInt( this.windowId.getId() );
        this.writeUnsignedVarInt( this.items.length );

        for ( Item item : this.items ) {
            if ( item != null ) {
                this.writeSignedVarInt( item.getRuntimeId() );
                this.writeItem( item );
            }
        }
    }
}
