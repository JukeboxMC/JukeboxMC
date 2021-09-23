package org.jukeboxmc.block;

import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.ItemConcretePowder;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockConcretePowder extends Block {

    public BlockConcretePowder() {
        super( "minecraft:concretepowder" );
    }

    @Override
    public ItemConcretePowder toItem() {
        return new ItemConcretePowder( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CONCRETEPOWDER;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.SHOVEL;
    }

    public BlockConcretePowder setColor( BlockColor color ) {
        return this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
