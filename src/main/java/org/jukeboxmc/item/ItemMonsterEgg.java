package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMonsterEgg;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMonsterEgg extends Item {

    public ItemMonsterEgg() {
        super( "minecraft:monster_egg", 97 );
    }

    @Override
    public BlockMonsterEgg getBlock() {
        return new BlockMonsterEgg();
    }

    public void setMonsterEggType( MonsterEggStoneType monsterEggType ) {
        this.setMeta( monsterEggType.ordinal() );
    }

    public MonsterEggStoneType getMonsterEggStoneType() {
        return MonsterEggStoneType.values()[this.getMeta()];
    }

    public enum MonsterEggStoneType {
        INFESTED_STONE,
        INFESTED_COBBLESTONE,
        INFESTED_STONE_BRICK,
        INFESTED_MOSSY_STONE_BRICK,
        INFESTED_CRACKED_STONE_BRICK,
        INFESTED_CHISELED_STONE_BRICK
    }
}
