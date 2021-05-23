package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockInfestedStone;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.MonsterEggStoneType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMonsterEgg extends Item {

    public ItemMonsterEgg( int blockdRuntimeId ) {
        super( 97, blockdRuntimeId );
    }

    @Override
    public BlockInfestedStone getBlock() {
        return (BlockInfestedStone) BlockType.getBlock( this.blockRuntimeId );
    }

    public MonsterEggStoneType getMonsterEggStoneType() {
        return this.getBlock().getMonsterEggStoneType();
    }

}
