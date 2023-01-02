package org.jukeboxmc.item.behavior;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockInfestedStone;
import org.jukeboxmc.block.data.MonsterEggStoneType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.util.BlockPalette;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemInfestedStone extends Item {

    private final BlockInfestedStone block;

    public ItemInfestedStone( Identifier identifier ) {
        super( identifier );
        this.block = Block.create( BlockType.INFESTED_STONE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    public ItemInfestedStone( ItemType itemType ) {
        super( itemType );
        this.block = Block.create( BlockType.INFESTED_STONE );
        this.blockRuntimeId = this.block.getRuntimeId();
    }

    @Override
    public ItemInfestedStone setBlockRuntimeId( int blockRuntimeId ) {
        this.blockRuntimeId = blockRuntimeId;
        this.block.setBlockStates( BlockPalette.getBlockNbt( blockRuntimeId ).getCompound( "states" ) );
        return this;
    }

    public ItemInfestedStone setMonsterEggStoneType( MonsterEggStoneType monsterEggStoneType ) {
        this.blockRuntimeId = this.block.setMonsterEggStoneType( monsterEggStoneType ).getRuntimeId();
        return this;
    }

    public MonsterEggStoneType getMonsterEggStoneType() {
        return this.block.getMonsterEggStoneType();
    }
}
