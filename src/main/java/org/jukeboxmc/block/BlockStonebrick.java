package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStonebrick extends Block {

    public BlockStonebrick() {
        super( "minecraft:stonebrick" );
    }

    public void setStoneBrickType( StoneBrickType stoneBrickType ) {
        this.setState( "stone_brick_type", stoneBrickType.name().toLowerCase() );
    }

    public StoneBrickType getStoneBrickType() {
        return this.stateExists( "stone_brick_type" ) ? StoneBrickType.valueOf( this.getStringState( "stone_brick_type" ).toUpperCase() ) : StoneBrickType.DEFAULT;
    }

    public enum StoneBrickType {
        DEFAULT,
        MOSSY,
        CRACKED,
        CHISELED,
        SMOOTH
    }
}
