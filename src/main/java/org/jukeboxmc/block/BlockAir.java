package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAir extends Block {

    public BlockAir() {
        super( "minecraft:air" );
    }

    @Override
    public boolean canBeReplaced() {
        return true;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
