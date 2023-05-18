package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.LiquidType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCauldron extends Block implements Waterlogable{

    public BlockCauldron( Identifier identifier ) {
        super( identifier );
    }

    public BlockCauldron( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
        }
        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public void setFillLevel(int value ) { //0-6
        this.setState( "fill_level", value );
    }

    public int getFillLevel() {
        return this.stateExists( "fill_level" ) ? this.getIntState( "fill_level" ) : 0;
    }

    public void setLiquid( LiquidType liquidType ) {
        this.setState( "cauldron_liquid", liquidType.name().toLowerCase() );
    }

    public LiquidType getLiquidType() {
        return this.stateExists( "cauldron_liquid" ) ? LiquidType.valueOf( this.getStringState( "cauldron_liquid" ) ) : LiquidType.WATER;
    }
}
