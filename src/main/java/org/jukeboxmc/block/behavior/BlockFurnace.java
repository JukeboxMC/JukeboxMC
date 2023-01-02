package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntityFurnace;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.inventory.FurnaceInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFurnace extends Block {

    public BlockFurnace( Identifier identifier ) {
        super( identifier );
    }

    public BlockFurnace( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        this.setBlockFace( player.getDirection().toBlockFace().opposite() );
        world.setBlock( placePosition, this , 0);
        BlockEntity.create( BlockEntityType.FURNACE, this ).spawn();
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityFurnace blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public void onBlockBreak( Vector breakPosition ) {
        BlockEntityFurnace blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            FurnaceInventory furnaceInventory = blockEntity.getFurnaceInventory();
            for ( Item content : furnaceInventory.getContents() ) {
                if ( content != null && !content.getType().equals( ItemType.AIR ) ){
                    this.location.getWorld().dropItem( content, breakPosition, null ).spawn();
                }
            }
            furnaceInventory.clear();
            furnaceInventory.getViewer().clear();
        }
        super.onBlockBreak( breakPosition );
    }

    @Override
    public BlockEntityFurnace getBlockEntity() {
        return (BlockEntityFurnace) this.location.getWorld().getBlockEntity( this.location, this.location.getDimension() );
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
