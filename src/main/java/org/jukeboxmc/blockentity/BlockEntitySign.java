package org.jukeboxmc.blockentity;

import com.google.common.base.Joiner;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtMapBuilder;
import org.cloudburstmc.protocol.bedrock.packet.BlockEntityDataPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.block.BlockSignChangeEvent;
import org.jukeboxmc.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntitySign extends BlockEntity {

    private final List<String> lines;

    public BlockEntitySign( Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
        this.lines = new ArrayList<>( 4 );
    }

    @Override
    public void fromCompound( NbtMap compound ) {
        super.fromCompound( compound );
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

        BlockSignChangeEvent blockSignChangeEvent = new BlockSignChangeEvent( this.block, player, lineList );
        Server.getInstance().getPluginManager().callEvent( blockSignChangeEvent );

        if ( blockSignChangeEvent.isCancelled() ) {
            return;
        }

        this.lines.clear();
        this.lines.addAll( blockSignChangeEvent.getLines() );
        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( this.block.getLocation().toVector3i());
        blockEntityDataPacket.setData( this.toCompound().build() );
        Server.getInstance().broadcastPacket( blockEntityDataPacket );
    }


    public void updateBlockEntitySign() {
        BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
        blockEntityDataPacket.setBlockPosition( this.block.getLocation().toVector3i() );
        blockEntityDataPacket.setData( this.toCompound().build() );
        Server.getInstance().broadcastPacket( blockEntityDataPacket );
    }
}
