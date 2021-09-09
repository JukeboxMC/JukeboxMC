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
public class InventoryContentPacket extends Packet {

    private WindowId windowId;
    private Item[] items;

    @Override
    public int getPacketId() {
        return Protocol.INVENTORY_CONTENT_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeUnsignedVarInt( this.windowId.getId() );
        stream.writeUnsignedVarInt( this.items.length );

        for ( Item item : this.items ) {
            if ( item != null ) {
                stream.writeItem( item );
            }
        }
    }
}