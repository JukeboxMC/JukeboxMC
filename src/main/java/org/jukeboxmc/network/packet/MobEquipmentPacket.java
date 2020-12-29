package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.network.Protocol;

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
    public void read() {
        super.read();
        this.entityId = this.readUnsignedVarLong();
        this.item = this.readItem();
        this.inventarSlot = this.readByte();
        this.hotbarSlot = this.readByte();
        this.windowId = this.readByte();
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarLong( this.entityId );
        this.writeItem( this.item );
        this.writeByte( this.inventarSlot );
        this.writeByte( this.hotbarSlot );
        this.writeByte( this.windowId );
    }
}
