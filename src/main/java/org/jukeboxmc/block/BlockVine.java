package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemVine;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockVine extends BlockWaterlogable {

    public BlockVine() {
        super( "minecraft:vine" );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public ItemVine toItem() {
        return new ItemVine();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.VINE;
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
    public boolean canPassThrough() {
        return true;
    }

    public void setVineDirection( int value ) { //0-15 TODO -> Add Direction Class
        this.setState( "vine_direction_bits", value );
    }

    public int getVineDirection() {
        return this.stateExists( "vine_direction_bits" ) ? this.getIntState( "vine_direction_bits" ) : 0;
    }
}
