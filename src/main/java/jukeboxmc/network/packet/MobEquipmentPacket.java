package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class MobEquipmentPacket extends Packet {

    private long entityId;
    private Item item;
    private int inventarSlot;
    private int hotbarSlot;
    private int windowId;

    @Override
    public int getPacketId() {
        return Protocol.MOB_EQUIPMENT_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.entityId = stream.readUnsignedVarLong();
        this.item = stream.readItem();
        this.inventarSlot = stream.readByte();
        this.hotbarSlot = stream.readByte();
        this.windowId = stream.readByte();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeUnsignedVarLong( this.entityId );
        stream.writeItem( this.item );
        stream.writeByte( this.inventarSlot );
        stream.writeByte( this.hotbarSlot );
        stream.writeByte( this.windowId );
    }
}
