package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemTnt;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTnt extends Block {

    public BlockTnt() {
        super( "minecraft:tnt" );
    }

    @Override
    public ItemTnt toItem() {
        return new ItemTnt();
    }

    @Override
    public BlockType getType() {
        return BlockType.TNT;
    }

    public void setExplode( boolean value ) {
        this.setState( "explode_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isExplode() {
        return this.stateExists( "explode_bit" ) && this.getByteState( "explode_bit" ) == 1;
    }

    public void setAllowUnderwater( boolean value ) {
        this.setState( "allow_underwater_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isAllowUnderwater() {
        return this.stateExists( "allow_underwater_bit" ) && this.getByteState( "allow_underwater_bit" ) == 1;
    }
}
