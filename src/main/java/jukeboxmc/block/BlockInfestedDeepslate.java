package jukeboxmc.block;

import org.jukeboxmc.item.ItemInfestedDeepslate;
import org.jukeboxmc.math.Axis;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockInfestedDeepslate extends Block {

    public BlockInfestedDeepslate() {
        super( "minecraft:infested_deepslate" );
    }

    @Override
    public ItemInfestedDeepslate toItem() {
        return new ItemInfestedDeepslate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.INFESTED_DEEPSLATE;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ) ) : Axis.Y;
    }
}
