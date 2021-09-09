package jukeboxmc.block;

import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.ItemConcretePowder;

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

    public BlockConcretePowder setColor( BlockColor color ) {
        return this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
