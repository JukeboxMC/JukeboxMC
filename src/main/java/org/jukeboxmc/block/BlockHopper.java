package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntityHopper;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemHopper;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockHopper extends BlockWaterlogable {

    public BlockHopper() {
        super( "minecraft:hopper" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setBlockFace( blockFace == BlockFace.UP ? BlockFace.DOWN : blockFace.opposite() );
        this.setToggle( false );
        boolean value = super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        if ( value ) {
            BlockEntityType.HOPPER.<BlockEntityHopper>createBlockEntity( this ).spawn();
        }
        return value;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityHopper blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public ItemHopper toItem() {
        return new ItemHopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HOPPER;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityHopper getBlockEntity() {
        return (BlockEntityHopper) this.world.getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public void setToggle( boolean value ) {
        this.setState( "toggle_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isToggle() {
        return this.stateExists( "toggle_bit" ) && this.getByteState( "toggle_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
