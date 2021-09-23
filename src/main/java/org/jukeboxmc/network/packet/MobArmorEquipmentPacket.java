package org.jukeboxmc.network.packet;

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
public class MobArmorEquipmentPacket extends Packet {

    private long entityId;
    private Item helmet;
    private Item chestplate;
    private Item leggings;
    private Item boots;

    @Override
    public int getPacketId() {
        return Protocol.MOB_ARMOR_EQUIPMENT_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.entityId = stream.readUnsignedVarLong();
        this.helmet = stream.readItem();
        this.chestplate = stream.readItem();
        this.leggings = stream.readItem();
        this.boots = stream.readItem();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeUnsignedVarLong( this.entityId );
        stream.writeItem( this.helmet );
        stream.writeItem( this.chestplate );
        stream.writeItem( this.leggings );
        stream.writeItem( this.boots );
    }
}
