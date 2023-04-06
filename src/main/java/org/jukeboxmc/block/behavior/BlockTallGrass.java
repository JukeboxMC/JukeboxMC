package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.GrassType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemTallGrass;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTallGrass extends Block {

    public BlockTallGrass( Identifier identifier ) {
        super( identifier );
    }

    public BlockTallGrass( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block blockDown = world.getBlock( blockPosition );
        if ( blockDown.getType().equals( BlockType.GRASS ) || blockDown.getType().equals( BlockType.DIRT ) || blockDown.getType().equals( BlockType.PODZOL ) ) {
            world.setBlock( placePosition, this );
            return true;
        }
        return false;
    }

    @Override
    public Item toItem() {
        return Item.<ItemTallGrass>create( ItemType.TALLGRASS ).setGrassType( this.getGrassType() );
    }

    public BlockTallGrass setGrassType( GrassType grassType ) {
        return this.setState( "tall_grass_type", grassType.name().toLowerCase() );
    }

    public GrassType getGrassType() {
        return this.stateExists( "tall_grass_type" ) ? GrassType.valueOf( this.getStringState( "tall_grass_type" ) ) : GrassType.DEFAULT;
    }
}
