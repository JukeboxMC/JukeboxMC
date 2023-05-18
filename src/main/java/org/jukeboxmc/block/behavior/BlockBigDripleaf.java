package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.BigDripleafTilt;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

public class BlockBigDripleaf extends Block implements Waterlogable {

    public BlockBigDripleaf(Identifier identifier) {
        super(identifier);
    }

    public BlockBigDripleaf(Identifier identifier, NbtMap blockStates) {
        super(identifier, blockStates);
    }

    @Override
    public boolean placeBlock(Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace) {
        Block blockDown = this.getSide(BlockFace.DOWN);

        if (!this.isValid(blockDown.getType())) {
            return false;
        }

        if (blockDown instanceof BlockBigDripleaf blockBigDripleaf) {
            BlockBigDripleaf block = Block.create(BlockType.BIG_DRIPLEAF);
            Direction direction = blockBigDripleaf.getDirection();

            block.setDirection(direction);
            block.setBigDripleafHead(true);

            world.setBlock(placePosition, block, 0, false);

            this.setDirection(direction);
            this.setBigDripleafHead(true);
        } else {
            this.setDirection(player.getDirection().opposite());
            this.setBigDripleafHead(true);
        }

        if(world.getBlock(placePosition) instanceof BlockWater blockWater && blockWater.getLiquidDepth() == 0) {
            world.setBlock(placePosition, Block.create(BlockType.WATER), 1, false);
        }

        world.setBlock(placePosition, this);
        return true;
    }

    @Override
    public int getWaterLoggingLevel() {
        return 2;
    }

    public boolean isValid(BlockType blockType) {
        switch (blockType) {
            case BIG_DRIPLEAF, GRASS, DIRT, DIRT_WITH_ROOTS, MYCELIUM, PODZOL, FARMLAND, MOSS_BLOCK, CLAY -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    public BlockBigDripleaf setBigDripleafTilt(BigDripleafTilt bigDripleafTilt) {
        this.setState("big_dripleaf_tilt", bigDripleafTilt.name());
        return this;
    }

    public BigDripleafTilt getBigDripleafTilt() {
        return this.stateExists("big_dripleaf_tilt") ? BigDripleafTilt.valueOf(this.getStringState("big_dripleaf_tilt")) : BigDripleafTilt.NONE;
    }

    public BlockBigDripleaf setBigDripleafHead( boolean value ) {
        this.setState("big_dripleaf_head", value ? (byte) 1 : (byte) 0);
        return this;
    }

    public boolean isBigDripLeafHead() {
        return this.stateExists("big_dripleaf_head") && this.getByteState("big_dripleaf_head") == 1;
    }

    public BlockBigDripleaf setDirection( Direction direction ) {
        switch ( direction ) {
            case SOUTH -> this.setState( "direction", 0 );
            case WEST -> this.setState( "direction", 1 );
            case NORTH -> this.setState( "direction", 2 );
            case EAST -> this.setState( "direction", 3 );
        }
        return this;
    }

    public Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        return switch ( value ) {
            case 0 -> Direction.SOUTH;
            case 1 -> Direction.WEST;
            case 2 -> Direction.NORTH;
            default -> Direction.EAST;
        };
    }
}
