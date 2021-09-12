package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntityShulkerBox;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemUndyedShulkerBox;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockUndyedShulkerBox extends Block {

    public BlockUndyedShulkerBox() {
        super( "minecraft:undyed_shulker_box" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        boolean value = super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        if ( value ) {
            BlockEntityType.SHULKER_BOX.<BlockEntityShulkerBox>createBlockEntity( this ).setUndyed( true ).spawn();
        }
        return value;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityShulkerBox blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityShulkerBox getBlockEntity() {
        return (BlockEntityShulkerBox) this.world.getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public ItemUndyedShulkerBox toItem() {
        return new ItemUndyedShulkerBox();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.UNDYED_SHULKER_BOX;
    }

}
