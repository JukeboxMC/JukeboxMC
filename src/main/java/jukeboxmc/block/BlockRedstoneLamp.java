package jukeboxmc.block;

import org.jukeboxmc.item.ItemRedstoneLamp;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedstoneLamp extends Block {

    public BlockRedstoneLamp() {
        super( "minecraft:redstone_lamp" );
    }

    @Override
    public ItemRedstoneLamp toItem() {
        return new ItemRedstoneLamp();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.REDSTONE_LAMP;
    }

}
