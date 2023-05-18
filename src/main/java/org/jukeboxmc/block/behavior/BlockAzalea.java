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

public class BlockAzalea extends Block implements Waterlogable{

    public BlockAzalea(Identifier identifier) {
        super(identifier);
    }

    public BlockAzalea(Identifier identifier, NbtMap blockStates) {
        super(identifier, blockStates);
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        switch (this.getSide(BlockFace.DOWN).getType()) {
            case GRASS, DIRT, FARMLAND, PODZOL, DIRT_WITH_ROOTS, MOSS_BLOCK -> {
                if (world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
                    world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
                }
                world.setBlock(placePosition, this);
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public int getWaterLoggingLevel() {
        return 1;
    }
}
