package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

public class BlockAmethystBud extends Block implements Waterlogable {

    public BlockAmethystBud(Identifier identifier) {
        super(identifier);
    }

    public BlockAmethystBud(Identifier identifier, NbtMap blockStates) {
        super(identifier, blockStates);
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        Block blockDown = this.getSide(BlockFace.DOWN);
        if (!blockDown.isSolid()) {
            return false;
        }
        if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1,  false);
        }
        this.setBlockFace(blockFace);
        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }

    public BlockAmethystBud setBlockFace(BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
        return this;
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
