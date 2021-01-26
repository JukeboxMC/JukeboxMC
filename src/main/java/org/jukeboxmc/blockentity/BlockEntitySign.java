package org.jukeboxmc.blockentity;

import com.google.common.base.Joiner;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.network.packet.BlockEntityDataPacket;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntitySign extends BlockEntity {

    private List<String> lines;

    public BlockEntitySign( Block block ) {
        super( block );
        this.lines = new ArrayList<>( 4 );
    }

    @Override
    public void setCompound( NbtMap compound ) {
        super.setCompound( compound );
        String text = compound.getString( "Text", "" );
        this.lines.addAll( Arrays.asList( text.split( "\n" ) ) );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        compound.putString( "id", "Sign" );
        compound.putString( "Text", Joiner.on( "\n" ).skipNulls().join( this.lines ) );
        return compound;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public void updateBlockEntitySign( NbtMap nbt ) {
        String text = nbt.getString( "Text", "" );
        this.lines = Arrays.asList( text.split( "\n" ) );

        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( this.block.getBlockPosition() );
        blockEntityDataPacket.setNbt( nbt );
        for ( Player player : this.block.getWorld().getPlayers() ) {
            player.getPlayerConnection().sendPacket( blockEntityDataPacket );
        }
    }

    public void updateBlockEntitySign() {
        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( this.block.getBlockPosition() );
        blockEntityDataPacket.setNbt( this.toCompound().build() );
        this.block.getWorld().sendWorldPacket( blockEntityDataPacket );
    }
}
