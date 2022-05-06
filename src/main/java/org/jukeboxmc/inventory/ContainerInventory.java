package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.ContainerOpenPacket;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class ContainerInventory extends Inventory {

    public ContainerInventory( InventoryHolder holder, long holderId, int slots ) {
        super( holder, holderId, slots );
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.CONTAINER;
    }

    @Override
    public void removeViewer( Player player ) {
        this.onClose( player );

        super.removeViewer( player );
    }

    @Override
    public void sendContents( Player player ) {
        InventoryContentPacket inventoryContentPacket = new InventoryContentPacket();
        inventoryContentPacket.setContainerId( WindowId.OPEN_CONTAINER.getId() );
        List<ItemData> itemDataList = new ArrayList<>();
        for ( Item content : this.getContents() ) {
            itemDataList.add( content.toNetwork() );
        }
        inventoryContentPacket.setContents( itemDataList );
        player.sendPacket( inventoryContentPacket );
    }

    @Override
    public void sendContents( int slot, Player player ) {
        InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
        inventorySlotPacket.setContainerId( WindowId.OPEN_CONTAINER.getId() );
        inventorySlotPacket.setSlot( slot );
        inventorySlotPacket.setItem( this.contents[slot].toNetwork() );
        player.sendPacket( inventorySlotPacket );
    }

    public void addViewer( Player player, Vector position, byte windowId ) {
        ContainerOpenPacket containerOpenPacket = new ContainerOpenPacket();
        containerOpenPacket.setUniqueEntityId( this.holderId );
        containerOpenPacket.setId( windowId );
        containerOpenPacket.setType( this.getWindowTypeId() );
        containerOpenPacket.setBlockPosition( position.toVector3i() );
        player.sendPacket( containerOpenPacket );
        super.addViewer( player );
        this.onOpen( player );
    }

    public void onOpen( Player player ) {

    }

    public void onClose( Player player ) {

    }

}
