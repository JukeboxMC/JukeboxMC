package jukeboxmc.block;

import org.jukeboxmc.item.ItemBrownMushroomBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBrownMushroomBlock extends Block {

    public BlockBrownMushroomBlock() {
        super( "minecraft:brown_mushroom_block" );
    }

    @Override
    public ItemBrownMushroomBlock toItem() {
        return new ItemBrownMushroomBlock( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BROWN_MUSHROOM_BLOCK;
    }

    public BlockBrownMushroomBlock setHugeMushroom( int value ) { //0-15
        return this.setState( "huge_mushroom_bits", value );
    }

    public int getHugeMushroom() {
        return this.stateExists( "huge_mushroom_bits" ) ? this.getIntState( "huge_mushroom_bits" ) : 0;
    }

}
