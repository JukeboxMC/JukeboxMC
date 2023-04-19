package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.GrassType;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.event.block.BlockSpreadEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;

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
        } else if ( itemInHand.getType().equals( ItemType.BONE_MEAL ) ) {
            this.growGrass( this.location.getWorld(), this.location );
            this.location.getWorld().spawnParticle( LevelEvent.PARTICLE_CROP_GROWTH, this.location );
            if ( !player.getGameMode().equals( GameMode.CREATIVE ) ) {
                player.getInventory().setItemInHand( itemInHand.decreaseAmount() );
            }
        }
        return false;
    }

    @Override
    public List<Item> getDrops( Item item ) {
        return Collections.singletonList( Item.create( ItemType.DIRT ) );
    }

    private void growGrass( World world, Vector vector ) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for ( int i = 0; i < 128; ++i ) {
            int num = 0;

            int x = vector.getBlockX();
            int y = vector.getBlockY() + 1;
            int z = vector.getBlockZ();

            while ( true ) {
                if ( num >= i / 16 ) {
                    if ( world.getBlock( x, y, z ).getType().equals( BlockType.AIR ) ) {
                        if ( random.nextInt( 8 ) == 0 ) {
                            if ( random.nextBoolean() ) {
                                world.setBlock( x, y, z, Block.create( BlockType.YELLOW_FLOWER ) );
                            } else {
                                world.setBlock( x, y, z, Block.create( BlockType.RED_FLOWER ) );
                            }
                        } else {
                            world.setBlock( x, y, z, Block.<BlockTallGrass>create( BlockType.TALLGRASS ).setGrassType( GrassType.DEFAULT ) );
                        }
                    }

                    break;
                }

                x += random.nextInt( -1, 2 );
                y += random.nextInt( -1, 2 ) * random.nextInt( 3 ) / 2;
                z += random.nextInt( -1, 2 );

                if ( world.getBlock( x, y - 1, z ).getType() != BlockType.GRASS || y > 255 || y < 0 ) {
                    break;
                }
                ++num;
            }
        }
    }
}
