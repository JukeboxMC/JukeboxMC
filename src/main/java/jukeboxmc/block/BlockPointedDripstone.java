package jukeboxmc.block;

import org.jukeboxmc.block.type.DripstoneThickness;
import org.jukeboxmc.item.ItemPointedDripstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPointedDripstone extends Block {

    public BlockPointedDripstone() {
        super( "minecraft:pointed_dripstone" );
    }

    @Override
    public ItemPointedDripstone toItem() {
        return new ItemPointedDripstone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POINTED_DRIPSTONE;
    }

    public void setDripstoneThickness( DripstoneThickness dripstoneThickness ) {
        this.setState( "dripstone_thickness", dripstoneThickness.name().toLowerCase() );
    }

    public DripstoneThickness getDripstoneThickness() {
        return this.stateExists( "dripstone_thickness" ) ? DripstoneThickness.valueOf( this.getStringState( "dripstone_thickness" ) ) : DripstoneThickness.TIP;
    }

    public void setHanging( boolean value ) {
        this.setState( "hanging", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isHanging() {
        return this.stateExists( "hanging" ) && this.getByteState( "hanging" ) == 1;
    }

}
