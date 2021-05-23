package org.jukeboxmc.block;

import org.jukeboxmc.block.type.StoneType;
import org.jukeboxmc.item.ItemStone;

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
        return new ItemStone(this.runtimeId);
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONE;
    }

    public BlockStone setStoneType( StoneType stoneType ) {
       return this.setState( "stone_type", stoneType.name().toLowerCase() );
    }

    public StoneType getStoneType() {
        return this.stateExists( "stone_type" ) ? StoneType.valueOf( this.getStringState( "stone_type" ) ) : StoneType.STONE;
    }

}
