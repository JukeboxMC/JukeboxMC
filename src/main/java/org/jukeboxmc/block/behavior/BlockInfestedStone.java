package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.MonsterEggStoneType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemInfestedStone;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockInfestedStone extends Block {

    public BlockInfestedStone( Identifier identifier ) {
        super( identifier );
    }

    public BlockInfestedStone( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public Item toItem() {
        return Item.<ItemInfestedStone>create( ItemType.INFESTED_STONE ).setMonsterEggStoneType( this.getMonsterEggStoneType() );
    }

    public BlockInfestedStone setMonsterEggStoneType( MonsterEggStoneType monsterEggStoneType ) {
        return this.setState( "monster_egg_stone_type", monsterEggStoneType.name().toLowerCase() );
    }

    public MonsterEggStoneType getMonsterEggStoneType() {
        return this.stateExists( "monster_egg_stone_type" ) ? MonsterEggStoneType.valueOf( this.getStringState( "monster_egg_stone_type" ) ) : MonsterEggStoneType.STONE;
    }
}
