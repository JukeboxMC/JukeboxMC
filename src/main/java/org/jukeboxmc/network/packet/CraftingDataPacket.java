package org.jukeboxmc.network.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.crafting.data.ContainerMixData;
import org.jukeboxmc.crafting.data.CraftingData;
import org.jukeboxmc.crafting.data.CraftingDataType;
import org.jukeboxmc.crafting.data.PotionMixData;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.utils.BinaryStream;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class CraftingDataPacket extends Packet {

    private List<CraftingData> craftingData = new ObjectArrayList<>();
    private List<PotionMixData> potionMixData = new ObjectArrayList<>();
    private List<ContainerMixData> containerMixData = new ObjectArrayList<>();

    @Override
    public int getPacketId() {
        return Protocol.CRAFTING_DATA_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );

        stream.writeUnsignedVarInt( this.craftingData.size() );
        for ( CraftingData craftingData : this.craftingData ) {
            CraftingDataType type = craftingData.getType();
            stream.writeSignedVarInt( type.ordinal() );
            switch ( type ) {
                case SHAPELESS:
                case SHULKER_BOX:
                case SHAPELESS_CHEMISTRY:
                    stream.writeString( craftingData.getRecipeId() );

                    stream.writeUnsignedVarInt( craftingData.getInputs().size() );
                    for ( Item input : craftingData.getInputs() ) {
                        stream.writeIngredient( input );
                    }

                    stream.writeUnsignedVarInt( craftingData.getOutputs().size() );
                    for ( Item output : craftingData.getOutputs() ) {
                        stream.writeItem( output, true );
                    }
                    stream.writeUUID( craftingData.getUuid() );
                    stream.writeString( craftingData.getCraftingTag() );
                    stream.writeSignedVarInt( craftingData.getPriority() );
                    stream.writeUnsignedVarInt( craftingData.getNetworkId() );
                    break;
                case SHAPED:
                case SHAPED_CHEMISTRY:
                    stream.writeString( craftingData.getRecipeId() );
                    stream.writeSignedVarInt( craftingData.getWidth() );
                    stream.writeSignedVarInt( craftingData.getHeight() );

                    for ( Item input : craftingData.getInputs() ) {
                        stream.writeIngredient( input );
                    }
                    stream.writeUnsignedVarInt( craftingData.getOutputs().size() );
                    for ( Item output : craftingData.getOutputs() ) {
                        stream.writeItem( output, true );
                    }
                    stream.writeUUID( craftingData.getUuid() );
                    stream.writeString( craftingData.getCraftingTag() );
                    stream.writeSignedVarInt( craftingData.getPriority() );
                    stream.writeUnsignedVarInt( craftingData.getNetworkId() );
                    break;
                case FURNACE:
                case FURNACE_DATA:
                    if ( craftingData.getOutputs().size() > 0 ) {
                        stream.writeSignedVarInt( craftingData.getInputId() );
                        if ( type.equals( CraftingDataType.FURNACE_DATA ) ) {
                            stream.writeSignedVarInt( craftingData.getInputDamage() );
                        }
                        stream.writeItem( craftingData.getOutputs().get( 0 ), true );
                        stream.writeString( craftingData.getCraftingTag() );
                    }
                    break;
                case MULTI:
                    stream.writeUUID( craftingData.getUuid() );
                    stream.writeUnsignedVarInt( craftingData.getNetworkId() );
                    break;
                default:
                    break;
            }
        }

        stream.writeUnsignedVarInt( this.potionMixData.size() );
        for ( PotionMixData potionMixData : this.potionMixData ) {
            stream.writeSignedVarInt( potionMixData.getInputId() );
            stream.writeSignedVarInt( potionMixData.getInputMeta() );
            stream.writeSignedVarInt( potionMixData.getReagentId() );
            stream.writeSignedVarInt( potionMixData.getReagentMeta() );
            stream.writeSignedVarInt( potionMixData.getOutputId() );
            stream.writeSignedVarInt( potionMixData.getOutputMeta() );
        }

        stream.writeUnsignedVarInt( this.containerMixData.size() );
        for ( ContainerMixData containerMixData : this.containerMixData ) {
            stream.writeSignedVarInt( containerMixData.getInputId() );
            stream.writeSignedVarInt( containerMixData.getReagentId() );
            stream.writeSignedVarInt( containerMixData.getOutputId() );
        }

        stream.writeUnsignedVarInt( 0 );
        stream.writeBoolean( true );
    }
}
