package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.utils.BinaryStream;

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
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeUnsignedVarInt( this.windowId.getId() );
        stream.writeUnsignedVarInt( this.slot );

        stream.writeItem( this.item );
    }
}
