package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.event.block.BlockSpreadEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.Dimension;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGrass extends Block {

    public BlockGrass( Identifier identifier ) {
        super( identifier );
    }

    public BlockGrass( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason.equals( UpdateReason.RANDOM ) ) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            int x = random.nextInt( this.location.getBlockX() - 1, this.location.getBlockX() + 2 );
            int y = random.nextInt( this.location.getBlockY() - 3, this.location.getBlockY() + 2 );
            int z = random.nextInt( this.location.getBlockZ() - 1, this.location.getBlockZ() + 2 );

            Block block = this.location.getWorld().getBlock( x, y, z, 0 );
            if ( block.getType().equals( BlockType.DIRT ) && block.getSide( BlockFace.UP ).getType().equals( BlockType.AIR ) ) {
                BlockSpreadEvent blockSpreadEvent = new BlockSpreadEvent( block, this, Block.create( BlockType.GRASS ) );
                Server.getInstance().getPluginManager().callEvent( blockSpreadEvent );
                if ( !blockSpreadEvent.isCancelled() ) {
                    this.location.getWorld().setBlock( new Vector( x, y, z ), blockSpreadEvent.getNewState(), 0, Dimension.OVERWORLD, false );
                }
            } else if ( block.getType().equals( BlockType.GRASS ) && block.getSide( BlockFace.UP ).isSolid() ) {
                BlockSpreadEvent blockSpreadEvent = new BlockSpreadEvent( block, this, Block.create( BlockType.DIRT ) );
                Server.getInstance().getPluginManager().callEvent( blockSpreadEvent );
                if ( !blockSpreadEvent.isCancelled() ) {
                    this.location.getWorld().setBlock( new Vector( x, y, z ), blockSpreadEvent.getNewState(), 0, Dimension.OVERWORLD, false );
                }
            }
        }
        return -1;
    }

    @Override
    public List<Item> getDrops( Item item ) {
        return Collections.singletonList( Item.create( ItemType.DIRT ) );
    }
}
