package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.GrassType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemTallGrass;
import org.jukeboxmc.item.type.ItemToolType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTallGrass extends Block {

    public BlockTallGrass() {
        super( "minecraft:tallgrass" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block blockDown = world.getBlock( blockPosition );
        if ( blockDown.getBlockType().equals( BlockType.GRASS ) || blockDown.getBlockType().equals( BlockType.DIRT ) || blockDown.getBlockType().equals( BlockType.PODZOL ) ) {
            world.setBlock( placePosition, this );
            return true;
        }
        return false;
    }
    @Override
    public ItemTallGrass toItem() {
        return new ItemTallGrass( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.TALLGRASS;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHEARS;
    }

    public BlockTallGrass setGrassType( GrassType grassType ) {
        return this.setState( "tall_grass_type", grassType.name().toLowerCase() );
    }

    public GrassType getGrassType() {
        return this.stateExists( "tall_grass_type" ) ? GrassType.valueOf( this.getStringState( "tall_grass_type" ) ) : GrassType.DEFAULT;
    }

}
