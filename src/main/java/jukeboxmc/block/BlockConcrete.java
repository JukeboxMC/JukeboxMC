package jukeboxmc.block;

import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.item.ItemConcrete;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockConcrete extends Block {

    public BlockConcrete() {
        super( "minecraft:concrete" );
    }



    @Override
    public ItemConcrete toItem() {
        return new ItemConcrete( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CONCRETE;
    }

    public BlockConcrete setColor( BlockColor color ) {
       return this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ) ) : BlockColor.WHITE;
    }
}
