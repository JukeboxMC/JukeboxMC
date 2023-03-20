package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.BambooLeafSize;
import org.jukeboxmc.block.data.BambooStalkThickness;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBamboo extends Block {

    public BlockBamboo( Identifier identifier ) {
        super( identifier );
    }

    public BlockBamboo( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public void setBambooLeafSize(@NotNull BambooLeafSize bambooLeafSize ) {
        this.setState( "bamboo_leaf_size", bambooLeafSize.name().toLowerCase() );
    }

    public @NotNull BambooLeafSize getBambooLeafSize() {
        return this.stateExists( "bamboo_leaf_size" ) ? BambooLeafSize.valueOf( this.getStringState( "bamboo_leaf_size" ) ) : BambooLeafSize.NO_LEAVES;
    }

    public void setAge( boolean value ) {
        this.setState( "age_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean hasAge() {
        return this.stateExists( "age_bit" ) && this.getByteState( "age_bit" ) == 1;
    }

    public void setBambooStalkThickness(@NotNull BambooStalkThickness bambooStalkThickness ) {
        this.setState( "bamboo_stalk_thickness", bambooStalkThickness.name().toLowerCase() );
    }

    public @NotNull BambooStalkThickness getBambooStalkThickness() {
        return this.stateExists( "bamboo_stalk_thickness" ) ? BambooStalkThickness.valueOf( this.getStringState( "bamboo_stalk_thickness" ) ) : BambooStalkThickness.THIN;
    }
}
