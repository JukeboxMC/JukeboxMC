package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStone extends Block {

    public BlockStone() {
        super( "minecraft:stone" );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getStoneType().ordinal() );
    }

    public void setStoneType( StoneType stoneType ) {
        this.setState( "stone_type", stoneType.name().toLowerCase() );
    }

    public StoneType getStoneType() {
        return this.stateExists( "stone_type" ) ? StoneType.valueOf( this.getStringState( "stone_type" ).toUpperCase() ) : StoneType.STONE;
    }

    public enum StoneType {
        STONE,
        GRANITE,
        GRANITE_SMOOTH,
        DIORITE,
        DIORITE_SMOOTH,
        ANDESITE,
        ANDESITE_SMOOTH
    }

}
