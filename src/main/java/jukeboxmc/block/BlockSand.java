package jukeboxmc.block;

import org.jukeboxmc.block.type.SandType;
import org.jukeboxmc.item.ItemSand;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSand extends Block {

    public BlockSand() {
        super( "minecraft:sand" );
    }

    @Override
    public ItemSand toItem() {
        return new ItemSand( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SAND;
    }

    public BlockSand setSandType( SandType sandType ) {
       return this.setState( "sand_type", sandType.name().toLowerCase() );
    }

    public SandType getSandType() {
        return this.stateExists( "sand_type" ) ? SandType.valueOf( this.getStringState( "sand_type" ) ) : SandType.NORMAL;
    }

}
