package jukeboxmc.block;

import org.jukeboxmc.item.ItemSeaPickle;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSeaPickle extends BlockWaterlogable {

    public BlockSeaPickle() {
        super( "minecraft:sea_pickle" );
    }

    @Override
    public ItemSeaPickle toItem() {
        return new ItemSeaPickle();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SEA_PICKLE;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void clusterCount( int value ) { //0-3
        this.setState( "cluster_count", value );
    }

    public int getClusterCount() {
        return this.stateExists( "cluster_count" ) ? this.getIntState( "cluster_count" ) : 0;
    }

    public void setDead( boolean value ) {
        this.setState( "dead_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDead() {
        return this.stateExists( "dead_bit" ) && this.getByteState( "dead_bit" ) == 1;
    }
}
