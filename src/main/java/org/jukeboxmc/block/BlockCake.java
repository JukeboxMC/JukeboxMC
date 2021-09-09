package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCake;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCake extends BlockWaterlogable {

    public BlockCake() {
        super( "minecraft:cake" );
    }

    @Override
    public ItemCake toItem() {
        return new ItemCake();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CAKE;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void setBiteCounter( int value ) {
        this.setState( "bite_counter", value );
    }

    public int getBiteCounter() {
        return this.stateExists( "bite_counter" ) ? this.getIntState( "bite_counter" ) : 0;
    }
}
