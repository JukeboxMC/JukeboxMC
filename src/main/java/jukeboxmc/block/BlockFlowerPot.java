package jukeboxmc.block;

import org.jukeboxmc.item.ItemFlowerPot;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFlowerPot extends BlockWaterlogable {

    public BlockFlowerPot() {
        super( "minecraft:flower_pot" );
    }

    @Override
    public ItemFlowerPot toItem() {
        return new ItemFlowerPot();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.FLOWER_POT;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setUpdate( boolean value ) {
        this.setState( "update_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpdate() {
        return this.stateExists( "update_bit" ) && this.getByteState( "update_bit" ) == 1;
    }

}
