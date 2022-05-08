package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSoulFire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSoulFire extends Block {

    public BlockSoulFire() {
        super( "minecraft:soul_fire" );
    }

    @Override
    public ItemSoulFire toItem() {
        return new ItemSoulFire();
    }

    @Override
    public BlockType getType() {
        return BlockType.SOUL_FIRE;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
