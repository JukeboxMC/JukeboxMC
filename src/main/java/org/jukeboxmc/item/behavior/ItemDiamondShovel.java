package org.jukeboxmc.item.behavior;

import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.item.*;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDiamondShovel extends Item implements Durability {


    public ItemDiamondShovel( Identifier identifier ) {
        super( identifier );
    }

    public ItemDiamondShovel( ItemType itemType ) {
        super( itemType );
    }

    @Override
    public boolean interact( Player player, BlockFace blockFace, Vector clickedVector, Block clickedBlock ) {
        if ( clickedBlock.getType().equals( BlockType.GRASS ) ) {
            player.getWorld().setBlock( clickedBlock.getLocation(), Block.create( BlockType.GRASS_PATH ) );
            player.getWorld().playSound( clickedBlock.getLocation(), SoundEvent.ITEM_USE_ON, 12259 );
            this.updateItem( player, 1 );
            return true;
        }
        return false;
    }

    @Override
    public void addToHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( 4 );
    }

    @Override
    public void removeFromHand( Player player ) {
        Attribute attribute = player.getAttribute( AttributeType.ATTACK_DAMAGE );
        attribute.setCurrentValue( attribute.getMinValue() );
    }

    @Override
    public int getMaxDurability() {
        return 1561;
    }

    @Override
    public ToolType getToolType() {
        return ToolType.SHOVEL;
    }

    @Override
    public TierType getTierType() {
        return TierType.DIAMOND;
    }
}
