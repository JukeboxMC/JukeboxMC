package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.DirtType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemDirt;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDirt extends Block {

    public BlockDirt( Identifier identifier ) {
        super( identifier );
    }

    public BlockDirt( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        if ( itemInHand.isHoe() ) {
            player.getWorld().setBlock( this.location, Block.create( BlockType.FARMLAND ) );
            player.getWorld().playSound( this.location, SoundEvent.ITEM_USE_ON, 5756 );
            player.swingArm();
            itemInHand.updateItem( player, 1 );
            return true;
        } else if ( itemInHand.isShovel() ) {
            player.getWorld().setBlock( this.location, Block.create( BlockType.GRASS_PATH ) );
            player.getWorld().playSound( this.location, SoundEvent.ITEM_USE_ON, 12259 );
            player.swingArm();
            itemInHand.updateItem( player, 1 );
            return true;
        }
        return false;
    }

    @Override
    public Item toItem() {
        return Item.<ItemDirt>create( ItemType.DIRT ).setDirtType( this.getDirtType() );
    }

    public BlockDirt setDirtType( DirtType dirtType ) {
        return this.setState( "dirt_type", dirtType.name().toLowerCase() );
    }

    public DirtType getDirtType() {
        return this.stateExists( "dirt_type" ) ? DirtType.valueOf( this.getStringState( "dirt_type" ) ) : DirtType.NORMAL;
    }
}
