package org.jukeboxmc.block;

import org.jukeboxmc.block.type.PrismarineType;
import org.jukeboxmc.item.ItemPrismarine;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPrismarine extends Block {

    public BlockPrismarine() {
        super( "minecraft:prismarine" );
    }

    @Override
    public ItemPrismarine toItem() {
        return new ItemPrismarine( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PRISMARINE;
    }

    @Override
    public double getHardness() {
        return 1.5;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    public BlockPrismarine setPrismarineType( PrismarineType prismarineType ) {
        return this.setState( "prismarine_block_type", prismarineType.name().toLowerCase() );
    }

    public PrismarineType getPrismarineType() {
        return this.stateExists( "prismarine_block_type" ) ? PrismarineType.valueOf( this.getStringState( "prismarine_block_type" ) ) : PrismarineType.DEFAULT;
    }

}
