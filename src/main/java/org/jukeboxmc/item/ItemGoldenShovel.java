package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockGrass;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.type.Durability;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGoldenShovel extends Item implements Durability {

    public ItemGoldenShovel() {
        super ( "minecraft:golden_shovel" );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        if ( clickedBlock instanceof BlockGrass ) {
            player.getWorld().setBlock( clickedBlock.getLocation(), BlockType.GRASS_PATH.getBlock() );
            this.updateItem( player, this.calculateDurability( 1 ) );
            return true;
        }
        return false;
    }

    @Override
    public ItemToolType getItemToolType() {
        return ItemToolType.SHOVEL;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.GOLD;
    }

    @Override
    public int getMaxDurability() {
        return 32;
    }
}
