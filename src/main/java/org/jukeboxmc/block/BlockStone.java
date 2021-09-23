package org.jukeboxmc.block;

import org.jukeboxmc.block.type.StoneType;
import org.jukeboxmc.item.ItemStone;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStone extends Block {

    public BlockStone() {
        super( "minecraft:stone" );
    }

    @Override
    public ItemStone toItem() {
        return new ItemStone( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE;
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

    public BlockStone setStoneType( StoneType stoneType ) {
        return this.setState( "stone_type", stoneType.name().toLowerCase() );
    }

    public StoneType getStoneType() {
        return this.stateExists( "stone_type" ) ? StoneType.valueOf( this.getStringState( "stone_type" ) ) : StoneType.STONE;
    }

}
