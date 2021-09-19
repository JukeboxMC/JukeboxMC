package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLever;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLever extends BlockWaterlogable {

    public BlockLever() {
        super( "minecraft:lever" );
    }

    @Override
    public ItemLever toItem() {
        return new ItemLever();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.LEVER;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    public void setOpen( boolean value ) {
        this.setState( "open_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOpen() {
        return this.stateExists( "open_bit" ) && this.getByteState( "open_bit" ) == 1;
    }

    public void setLeverDirection( LeverDirection leverDirection ) {
        this.setState( "lever_direction", leverDirection.name().toLowerCase() );
    }

    public LeverDirection getLeverDirection() {
        return this.stateExists( "lever_direction" ) ? LeverDirection.valueOf( this.getStringState( "lever_direction" ) ) : LeverDirection.DOWN_EAST_WEST;
    }

    public enum LeverDirection {
        DOWN_EAST_WEST,
        EAST,
        WEST,
        SOUTH,
        NORTH,
        UP_NORTH_SOUTH,
        UP_EAST_WEST,
        DOWN_NORTH_SOUTH
    }
}
