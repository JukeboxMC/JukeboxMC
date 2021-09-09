package jukeboxmc.block;

import org.jukeboxmc.item.ItemAzaleaLeaves;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAzaleaLeaves extends Block{

    public BlockAzaleaLeaves() {
        super( "minecraft:azalea_leaves" );
    }

    @Override
    public ItemAzaleaLeaves toItem() {
        return new ItemAzaleaLeaves();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.AZALEA_LEAVES;
    }

    public void setPersistent( boolean value ) {
        this.setState( "persistent_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isPersistent() {
        return this.stateExists( "persistent_bit" ) && this.getByteState( "persistent_bit" ) == 1;
    }

    public void setUpdate( boolean value ) {
        this.setState( "update_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpdate() {
        return this.stateExists( "update_bit" ) && this.getByteState( "update_bit" ) == 1;
    }
}
