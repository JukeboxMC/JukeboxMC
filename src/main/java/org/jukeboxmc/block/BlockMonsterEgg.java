package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMonsterEgg extends Block {

    public BlockMonsterEgg() {
        super( "minecraft:monster_egg" );
    }

    @Override
    public void placeBlock( Player player, World world, BlockPosition placePosition, Item itemIndHand, BlockFace blockFace ) {
        this.setMonsterEggStoneType( MonsterEggStoneType.values()[itemIndHand.getMeta()] );
        world.setBlock( placePosition, this );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getMonsterEggStoneType().ordinal() );
    }

    public void setMonsterEggStoneType( MonsterEggStoneType monsterEggStoneType ) {
        this.setState( "monster_egg_stone_type", monsterEggStoneType.name().toLowerCase() );
    }

    public MonsterEggStoneType getMonsterEggStoneType() {
        return this.stateExists( "monster_egg_stone_type" ) ? MonsterEggStoneType.valueOf( this.getStringState( "monster_egg_stone_type" ).toUpperCase()) : MonsterEggStoneType.STONE;
    }

    public enum MonsterEggStoneType {
        STONE,
        COBBLESTONE,
        STONE_BRICK,
        MOSSY_STONE_BRICK,
        CRACKED_STONE_BRICK,
        CHISELED_STONE_BRICK
    }
}
