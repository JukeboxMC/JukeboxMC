package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDirt extends Block {

    public BlockDirt() {
        super( "minecraft:dirt" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setDirtType( DirtType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getDirtType().ordinal() );
    }

    public BlockDirt setDirtType( DirtType dirtType ) {
        return this.setState( "dirt_type", dirtType.name().toLowerCase() );
    }

    public DirtType getDirtType() {
        return this.stateExists( "dirt_type" ) ? DirtType.valueOf( this.getStringState( "dirt_type" ).toUpperCase() ) : DirtType.NORMAL;
    }

    public enum DirtType {
        NORMAL,
        COARSE
    }

}
