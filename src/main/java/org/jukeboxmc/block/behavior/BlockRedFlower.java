package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.FlowerType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemRedFlower;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedFlower extends Block {

    public BlockRedFlower( Identifier identifier ) {
        super( identifier );
    }

    public BlockRedFlower( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public Item toItem() {
        return Item.<ItemRedFlower>create( ItemType.RED_FLOWER ).setFlowerType( this.getFlowerType() );
    }

    public BlockRedFlower setFlowerType( FlowerType flowerType ) {
        return this.setState( "flower_type", flowerType.name().toLowerCase() );
    }

    public FlowerType getFlowerType() {
        return this.stateExists( "flower_type" ) ? FlowerType.valueOf( this.getStringState( "flower_type" ) ) : FlowerType.TULIP_RED;
    }

}
