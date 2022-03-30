package org.jukeboxmc.world.generator.terra.delegate;

import com.dfsek.terra.api.block.BlockType;
import com.dfsek.terra.api.block.state.BlockState;
import com.dfsek.terra.api.block.state.properties.Property;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAir;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record BlockStateDelegate(Block block) implements BlockState {

    @Override
    public boolean matches( BlockState blockState ) {
        if ( blockState instanceof BlockStateDelegate delegate) {
            return delegate.block.equals( block );
        }
        return false;
    }

    @Override
    public <T extends Comparable<T>> boolean has( Property<T> property ) {
        return block.getBlockStates().containsKey( property.getID() );
    }

    @Override
    public <T extends Comparable<T>> T get( Property<T> property ) {
        return (T) block.getBlockStates().get( property.getID() );
    }

    @Override
    public <T extends Comparable<T>> BlockState set( Property<T> property, T t ) {
        return this;
    }

    @Override
    public BlockType getBlockType() {
        return new TerraBlockType(block);
    }

    @Override
    public String getAsString( boolean b ) {
        return block.toString();
    }

    @Override
    public boolean isAir() {
        return this.block instanceof BlockAir;
    }

    @Override
    public Block getHandle() {
        return block;
    }
}
