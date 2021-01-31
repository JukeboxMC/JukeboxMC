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
public class BlockLavaCauldron extends Block {

    public BlockLavaCauldron() {
        super( "minecraft:lava_cauldron" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        this.setLiquid( LiquidType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getLiquidType().ordinal() );
    }

    public void setFillLevel( int value ) { //0-6
        this.setState( "fill_level", value );
    }

    public int getFillLevel() {
        return this.stateExists( "fill_level" ) ? this.getIntState( "fill_level" ) : 0;
    }

    public void setLiquid( LiquidType liquidType ) {
        this.setState( "cauldron_liquid", liquidType.name().toLowerCase() );
    }

    public LiquidType getLiquidType() {
        return this.stateExists( "cauldron_liquid" ) ? LiquidType.valueOf( this.getStringState( "cauldron_liquid" ).toUpperCase() ) : LiquidType.WATER;
    }

    public enum LiquidType {
        WATER,
        LAVA
    }
}
