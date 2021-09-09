package jukeboxmc.block;

import org.jukeboxmc.item.ItemFire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFire extends Block {

    public BlockFire() {
        super( "minecraft:fire" );
    }

    @Override
    public ItemFire toItem() {
        return new ItemFire();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.FIRE;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setAge( int value ) { // 0-15
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
