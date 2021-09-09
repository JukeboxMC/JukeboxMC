package jukeboxmc.block;

import org.jukeboxmc.item.ItemMobSpawner;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMobSpawner extends BlockWaterlogable {

    public BlockMobSpawner() {
        super( "minecraft:mob_spawner" );
    }

    @Override
    public ItemMobSpawner toItem() {
        return new ItemMobSpawner();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOB_SPAWNER;
    }

}
