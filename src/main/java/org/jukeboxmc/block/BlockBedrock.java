package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBedrock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBedrock extends Block {

    public BlockBedrock() {
        super( "minecraft:bedrock" );
    }

    public BlockBedrock setInfiniBurn( boolean infiniBurn ) {
        return this.setState( "infiniburn_bit", infiniBurn ? (byte) 1 : (byte) 0 );
    }

    public boolean isInfiniBurn() {
        return this.stateExists( "infiniburn_bit" ) && this.getByteState( "infiniburn_bit" ) == 1;
    }

    @Override
    public ItemBedrock toItem() {
        return new ItemBedrock();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BEDROCK;
    }

}
