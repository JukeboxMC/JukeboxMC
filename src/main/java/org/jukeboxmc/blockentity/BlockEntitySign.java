package org.jukeboxmc.blockentity;

import com.google.common.base.Joiner;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.block.SignChangeEvent;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.network.packet.BlockEntityDataPacket;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntitySign extends BlockEntityContainer {

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
        compound.putString( "Text", Joiner.on( "\n" ).skipNulls().join( this.lines ) );
        return compound;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public void updateBlockEntitySign( NbtMap nbt, Player player ) {
        String text = nbt.getString( "Text", "" );
        String[] splitLine = text.split( "\n" );

        ArrayList<String> lineList = new ArrayList<>();
        Collections.addAll( lineList, splitLine );

        SignChangeEvent signChangeEvent = new SignChangeEvent( this.block, player, lineList );
        Server.getInstance().getPluginManager().callEvent( signChangeEvent );

        if ( signChangeEvent.isCancelled() ) {
            return;
        }

        this.lines.clear();
        this.lines.addAll( signChangeEvent.getLines() );

        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( this.block.getLocation());
        blockEntityDataPacket.setNbt( this.toCompound().build() );
        this.block.getWorld().sendDimensionPacket( blockEntityDataPacket, this.block.getLocation().getDimension() );
    }

    public void updateBlockEntitySign() {
        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( this.block.getLocation() );
        blockEntityDataPacket.setNbt( this.toCompound().build() );
        this.block.getWorld().sendDimensionPacket( blockEntityDataPacket, this.block.getLocation().getDimension() );
    }
}
