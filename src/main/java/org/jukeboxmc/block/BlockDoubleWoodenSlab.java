package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.WoodType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.item.ItemWoodenSlab;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoubleWoodenSlab extends BlockSlab {

    public BlockDoubleWoodenSlab() {
        super( "minecraft:double_wooden_slab" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemWoodenSlab toItem() {
        return new ItemWoodenSlab( Block.STATES.get( "minecraft:wooden_slab" ).get( this.blockStates ) );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WOODEN_SLAB;
    }


    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public Block setWoodType( WoodType woodType ) {
        this.setState( "wood_type", woodType.name().toLowerCase() );
        return this;
    }

    public WoodType getWoodType() {
        return this.stateExists( "wood_type" ) ? WoodType.valueOf( this.getStringState( "wood_type" ) ) : WoodType.OAK;
    }
}
