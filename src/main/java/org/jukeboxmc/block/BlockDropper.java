package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.blockentity.BlockEntityDropper;
import org.jukeboxmc.blockentity.BlockEntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDropper;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDropper extends Block {

    public BlockDropper() {
        super( "minecraft:dropper" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setBlockFace( player.getDirection().toBlockFace().opposite() );
        world.setBlock( placePosition, this, 0);

        BlockEntityType.DROPPER.<BlockEntityDropper>createBlockEntity( this ).spawn();
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        BlockEntityDropper blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.interact( player, blockPosition, clickedPosition, blockFace, itemInHand );
            return true;
        }
        return false;
    }

    @Override
    public ItemDropper toItem() {
        return new ItemDropper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DROPPER;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntityDropper getBlockEntity() {
        return (BlockEntityDropper) this.world.getBlockEntity( this.location, this.location.getDimension() );
    }

    @Override
    public double getHardness() {
        return 3.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    public void setTriggered( boolean value ) {
        this.setState( "triggered_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isTriggered() {
        return this.stateExists( "triggered_bit" ) && this.getByteState( "triggered_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
