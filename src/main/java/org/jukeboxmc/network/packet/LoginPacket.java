package org.jukeboxmc.network.packet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.player.info.Device;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.info.UIProfile;
import org.jukeboxmc.player.skin.*;
import org.jukeboxmc.utils.BinaryStream;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@ToString
@EqualsAndHashCode ( callSuper = true )
public class LoginPacket extends Packet {

    private int protocol;

    private String username;
    private String xuid;
    private UUID uuid;
    private DeviceInfo deviceInfo;
    private String languageCode;
    private String gameVersion;
    private Skin skin;

    @Override
    public int getPacketId() {
        return Protocol.LOGIN_PACKET;
    }

    @Override
    public void read() {
        this.protocol = this.readInt();

        this.setBuffer( this.readBytes( new BinaryStream( this.getBuffer() ).readUnsignedVarInt() ) );
        String chainToken = this.readString();

        Map chainMap = new Gson().fromJson( chainToken, Map.class );
        List<String> chains = new ArrayList<>();
        for ( Object o : ( (List<?>) chainMap.get( "chain" ) ) ) {
            chains.add( o.toString() );
        }
        for ( String data : chains ) {
            String chainJson = this.readToken( data );
            JsonObject chainData = new Gson().fromJson( chainJson, JsonObject.class );

            if ( chainData != null ) {
                if ( chainData.has( "extraData" ) ) {
                    JsonObject extraData = chainData.get( "extraData" ).getAsJsonObject();
                    if ( extraData.has( "displayName" ) ) {
                        this.username = extraData.get( "displayName" ).getAsString();
                    }
                    if ( extraData.has( "XUID" ) ) {
                        this.xuid = extraData.get( "XUID" ).getAsString();
                    }
                    if ( extraData.has( "identity" ) ) {
                        this.uuid = UUID.fromString( extraData.get( "identity" ).getAsString() );
                    }
                }
            }
        }

        String skinToken = this.readToken( this.readString() );
        JsonObject skinMap = new Gson().fromJson( skinToken, JsonObject.class );

        //ChainData
        if ( skinMap.has( "DeviceModel" ) && skinMap.has( "DeviceId" ) &&
                skinMap.has( "ClientRandomId" ) && skinMap.has( "DeviceOS" ) && skinMap.has( "GuiScale" ) ) {
            String deviceModel = skinMap.get( "DeviceModel" ).getAsString();
            String deviceId = skinMap.get( "DeviceId" ).getAsString();
            long clientId = skinMap.get( "ClientRandomId" ).getAsLong();
            int deviceOS = skinMap.get( "DeviceOS" ).getAsInt();
            int guiScale = skinMap.get( "UIProfile" ).getAsInt();
            this.deviceInfo = new DeviceInfo( deviceModel, deviceId, clientId, Device.getDevice( deviceOS ), UIProfile.getUIProfile( guiScale ) );
        }

        if ( skinMap.has( "LanguageCode" ) ) {
            this.languageCode = skinMap.get( "LanguageCode" ).getAsString();
        }

        if ( skinMap.has( "GameVersion" ) ) {
            this.gameVersion = skinMap.get( "GameVersion" ).getAsString();
        }

        //SkinData
        this.skin = new Skin();
        if ( skinMap.has( "SkinId" ) ) {
            this.skin.setSkinId( skinMap.get( "SkinId" ).getAsString() );
        }

        if ( skinMap.has( "SkinResourcePatch" ) ) {
            this.skin.setResourcePatch( new String( Base64.getDecoder().decode( skinMap.get( "SkinResourcePatch" ).getAsString() ), StandardCharsets.UTF_8 ) );
        }

        if ( skinMap.has( "SkinGeometryData" ) ) {
            this.skin.setGeometryData( new String( Base64.getDecoder().decode( skinMap.get( "SkinGeometryData" ).getAsString() ), StandardCharsets.UTF_8 ) );
        }

        if ( skinMap.has( "AnimationData" ) ) {
            this.skin.setAnimationData( new String( Base64.getDecoder().decode( skinMap.get( "AnimationData" ).getAsString() ), StandardCharsets.UTF_8 ) );
        }

        if ( skinMap.has( "CapeId" ) ) {
            this.skin.setCapeId( skinMap.get( "CapeId" ).getAsString() );
        }

        if ( skinMap.has( "SkinColor" ) ) {
            this.skin.setSkinColor( skinMap.get( "SkinColor" ).getAsString() );
        }

        if ( skinMap.has( "ArmSize" ) ) {
            this.skin.setArmSize( skinMap.get( "ArmSize" ).getAsString() );
        }

        if ( skinMap.has( "PlayFabID" ) ) {
            this.skin.setPlayFabId( skinMap.get( "PlayFabID" ).getAsString() );
        }

        this.skin.setSkinData( this.getImage( skinMap, "Skin" ) );
        this.skin.setCapeData( this.getImage( skinMap, "Cape" ) );

        if ( skinMap.has( "PremiumSkin" ) ) {
            this.skin.setPremium( skinMap.get( "PremiumSkin" ).getAsBoolean() );
        }

        if ( skinMap.has( "PersonaSkin" ) ) {
            this.skin.setPersona( skinMap.get( "PersonaSkin" ).getAsBoolean() );
        }

        if ( skinMap.has( "CapeOnClassicSkin" ) ) {
            this.skin.setCapeOnClassic( skinMap.get( "CapeOnClassicSkin" ).getAsBoolean() );
        }

        if ( skinMap.has( "AnimatedImageData" ) ) {
            JsonArray array = skinMap.get( "AnimatedImageData" ).getAsJsonArray();
            for ( JsonElement jsonElement : array ) {
                this.skin.getSkinAnimations().add( this.getSkinAnimationData( jsonElement.getAsJsonObject() ) );
            }
        }

        if ( skinMap.has( "PersonaPieces" ) ) {
            JsonArray array = skinMap.get( "PersonaPieces" ).getAsJsonArray();
            for ( JsonElement jsonElement : array ) {
                this.skin.getPersonaPieces().add( this.getPersonaPiece( jsonElement.getAsJsonObject() ) );
            }
        }

        if ( skinMap.has( "PieceTintColors" ) ) {
            JsonArray array = skinMap.get( "PieceTintColors" ).getAsJsonArray();
            for ( JsonElement jsonElement : array ) {
                this.skin.getPersonaPieceTints().add( this.getPersonaPieceTint( jsonElement.getAsJsonObject() ) );
            }
        }
    }

    private Image getImage( JsonObject skinMap, String name ) {
        if ( skinMap.has( name + "Data" ) ) {
            byte[] skinImage = Base64.getDecoder().decode( skinMap.get( name + "Data" ).getAsString() );
            if ( skinMap.has( name + "ImageHeight" ) && skinMap.has( name + "ImageWidth" ) ) {
                int width = skinMap.get( name + "ImageWidth" ).getAsInt();
                int height = skinMap.get( name + "ImageHeight" ).getAsInt();
                return new Image( width, height, skinImage );
            } else {
                return Image.getImage( skinImage );
            }
        }
        return new Image( 0, 0, new byte[0] );
    }

    private SkinAnimation getSkinAnimationData( JsonObject animationData ) {
        byte[] data = Base64.getDecoder().decode( animationData.get( "Image" ).getAsString() );
        int width = animationData.get( "ImageWidth" ).getAsInt();
        int height = animationData.get( "ImageHeight" ).getAsInt();
        float frames = animationData.get( "Frames" ).getAsFloat();
        int type = animationData.get( "Type" ).getAsInt();
        int expression = animationData.get( "AnimationExpression" ).getAsInt();
        return new SkinAnimation( new Image( width, height, data ), type, frames, expression );
    }

    private PersonaPiece getPersonaPiece( JsonObject personaPiece ) {
        String pieceId = personaPiece.get( "PieceId" ).getAsString();
        String pieceType = personaPiece.get( "PieceType" ).getAsString();
        String packId = personaPiece.get( "PackId" ).getAsString();
        String productId = personaPiece.get( "ProductId" ).getAsString();
        boolean isDefault = personaPiece.get( "IsDefault" ).getAsBoolean();
        return new PersonaPiece( pieceId, pieceType, packId, productId, isDefault );
    }

    private PersonaPieceTint getPersonaPieceTint( JsonObject personaPiceTint ) {
        String pieceType = personaPiceTint.get( "PieceType" ).getAsString();
        List<String> colors = new ArrayList<>();
        for ( JsonElement element : personaPiceTint.get( "Colors" ).getAsJsonArray() ) {
            colors.add( element.getAsString() );
        }
        return new PersonaPieceTint( pieceType, colors );
    }

    private String readToken( String token ) {
        String[] base = token.split( "\\." );
        if ( base.length < 2 ) {
            return null;
        }
        return new String( Base64.getDecoder().decode( base[1] ), StandardCharsets.UTF_8 );
    }

    @Override
    public String readString() {
        byte[] bytes = new byte[this.readLInt()];
        this.readBytes( bytes );
        return new String( bytes, StandardCharsets.UTF_8 );
    }
}
