package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockDirt;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.nbt.NbtMapBuilder;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDirt extends Item {

    public ItemDirt() {
        super( "minecraft:dirt", 3 );
    }

    @Override
    public int getMaxAmount() {
        return 64;
    }

    @Override
    public BlockDirt getBlock() {
        return new BlockDirt();
    }

    @Override
    public void toNetwork( NbtMapBuilder builder ) {
        super.toNetwork( builder );
        /*ByteArrayInputStream inputStream = new ByteArrayInputStream( Base64.getDecoder().decode( "CgAACgkARmlyZXdvcmtzCQoARXhwbG9zaW9ucwoBAAAABw0ARmlyZXdvcmtDb2xvcgEAAAAIBwwARmlyZXdvcmtGYWRlAAAAAAEPAEZpcmV3b3JrRmxpY2tlcgABDQBGaXJld29ya1RyYWlsAAEMAEZpcmV3b3JrVHlwZQAAAQYARmxpZ2h0AQAA" ) );
        NBTInputStream readerLE = NbtUtils.createReaderLE( inputStream );

        try {
            ((NbtMap) readerLE.readTag()).forEach( builder::put );
        } catch ( IOException e ) {
            e.printStackTrace();
        }*/
    }

}
