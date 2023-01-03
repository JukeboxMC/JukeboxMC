package org.jukeboxmc.inventory;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPacket;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FakeChestInventory extends FakeInventory {

    public FakeChestInventory( InventoryHolder holder, int holderId, int size ) {
        super( holder, holderId, size );
    }

    public FakeChestInventory( String customName ) {
        super( null, -1, 27 );
        this.customName = customName;
    }

    public FakeChestInventory() {
        super( null, -1, 27 );
    }

    @Override
    public InventoryHolder getInventoryHolder() {
        return null;
    }

    @Override
    public ContainerType getWindowTypeId() {
        return ContainerType.CONTAINER;
    }

    @Override
    public InventoryType getType() {
        return InventoryType.FAKE_CHEST;
    }

    @Override
    public List<Vector> onOpenChest( Player player ) {
        Vector position = new Vector( player.getBlockX(), player.getBlockY() + 2, player.getBlockZ() );
        this.placeFakeChest( player, position );
        return Collections.singletonList( position );
    }

    protected void placeFakeChest( Player player, Vector position ) {
        UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
        updateBlockPacket.setRuntimeId( Block.create( BlockType.CHEST ).getRuntimeId() );
        updateBlockPacket.setBlockPosition( position.toVector3i() );
        updateBlockPacket.setDataLayer( 0 );
        updateBlockPacket.getFlags().addAll( UpdateBlockPacket.FLAG_ALL_PRIORITY );
        player.getPlayerConnection().sendPacket( updateBlockPacket );

        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( position.toVector3i() );
        blockEntityDataPacket.setData( this.toChestNBT( position ) );

        player.getPlayerConnection().sendPacket( blockEntityDataPacket );
    }

    private NbtMap toChestNBT( Vector position ) {
        return NbtMap.builder()
                .putString( "id", "Chest" )
                .putInt( "x", position.getBlockX() )
                .putInt( "y", position.getBlockY() )
                .putInt( "z", position.getBlockZ() )
                .putString( "CustomName", this.customName == null ? "Chest" : this.customName )
                .build();
    }
}
