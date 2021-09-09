package jukeboxmc.block;

import org.jukeboxmc.item.ItemChorusFlower;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockChorusFlower extends Block {

    public BlockChorusFlower() {
        super( "minecraft:chorus_flower" );
    }

    @Override
    public ItemChorusFlower toItem() {
        return new ItemChorusFlower();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHORUS_FLOWER;
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }

}
