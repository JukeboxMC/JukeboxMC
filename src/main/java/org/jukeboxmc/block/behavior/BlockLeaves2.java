package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.LeafType2;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemLeaves2;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLeaves2 extends Block implements Waterlogable {

    public BlockLeaves2( Identifier identifier ) {
        super( identifier );
    }

    public BlockLeaves2( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition.add(0, 1, 0), Block.create(BlockType.WATER), 1, false);
        }
        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public Item toItem() {
        return Item.<ItemLeaves2>create( ItemType.LEAVES2 ).setLeafType( this.getLeafType() );
    }

    @Override
    public List<Item> getDrops( Item item ) {
        if ( this.isCorrectToolType( item ) ) {
            return Collections.singletonList( this.toItem() );
        }
        return Collections.emptyList();
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public void setPersistent(boolean value ) {
        this.setState( "persistent_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isPersistent() {
        return this.stateExists( "persistent_bit" ) && this.getByteState( "persistent_bit" ) == 1;
    }

    public void setUpdate( boolean value ) {
        this.setState( "update_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpdate() {
        return this.stateExists( "update_bit" ) && this.getByteState( "update_bit" ) == 1;
    }

    public BlockLeaves2 setLeafType( LeafType2 leafType ) {
        return this.setState( "new_leaf_type", leafType.name().toLowerCase() );
    }

    public LeafType2 getLeafType() {
        return this.stateExists( "new_leaf_type" ) ? LeafType2.valueOf( this.getStringState( "new_leaf_type" ) ) : LeafType2.ACACIA;
    }
}
