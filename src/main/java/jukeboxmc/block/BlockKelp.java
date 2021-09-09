package jukeboxmc.block;

import org.jukeboxmc.item.ItemKelp;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockKelp extends BlockWaterlogable {

    public BlockKelp() {
        super( "minecraft:kelp" );
    }

    @Override
    public ItemKelp toItem() {
        return new ItemKelp();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.KELP;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setKelpAge( int value ) { //0-25
        this.setState( "kelp_age", value );
    }

    public int getKelpAge() {
        return this.stateExists( "kelp_age" ) ? this.getIntState( "kelp_age" ) : 0;
    }
}
