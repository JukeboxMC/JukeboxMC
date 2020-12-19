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
        return this.states( "infiniburn_bit", infiniBurn ? (byte) 1 : (byte) 0 );
    }

}
