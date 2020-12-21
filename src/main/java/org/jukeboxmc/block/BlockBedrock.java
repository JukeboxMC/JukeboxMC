package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBedrock extends Block {

    public BlockBedrock() {
        super( "minecraft:bedrock" );
    }

    public BlockBedrock setInfiniBurn( boolean infiniBurn ) {
        return this.setStates( "infiniburn_bit", infiniBurn ? (byte) 1 : (byte) 0 );
    }

    public boolean isInfiniBurn() {
        return this.states.containsKey( "infiniburn_bit" ) && this.states.getByte( "infiniburn_bit" ) == 1;
    }

}
