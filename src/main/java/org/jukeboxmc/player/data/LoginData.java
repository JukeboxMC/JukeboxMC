package org.jukeboxmc.player.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import com.nimbusds.jose.proc.JWSVerifierFactory;
import com.nimbusds.jwt.SignedJWT;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket;
import org.cloudburstmc.protocol.bedrock.util.EncryptionUtils;
import org.cloudburstmc.protocol.common.util.Preconditions;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.info.Device;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.info.UIProfile;
import org.jukeboxmc.player.skin.*;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class LoginData {

    private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();
    private static ECPublicKey MOJANG_PUBLIC_KEY_OLD = null;
    private static ECPublicKey MOJANG_PUBLIC_KEY = null;

    private boolean xboxAuthenticated;
    private String displayName;
    private String xuid;
    private UUID uuid;
    private DeviceInfo deviceInfo;
    private String languageCode;
    private String gameVersion;
    private Skin skin;

    static {
        try {
            MOJANG_PUBLIC_KEY_OLD = generateKey("MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkixyLcwlZryUQcu1TvPOmI2B7vX83ndnWRUaXm74wFfa5f/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFSNBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V");
            MOJANG_PUBLIC_KEY = generateKey("MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAECRXueJeTDqNRRgJi/vlRufByu/2G0i2Ebt6YMar5QX/R0DIIyrJMcUpruK4QveTfJSTp3Shlq4Gk34cD/4GUWwkv0DVuzeuB+tXija7HBxii03NHDbPAD0AKnLr2wdAp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LoginData( LoginPacket loginPacket ) {
        this.decodeChainData( loginPacket.getChain() );
        this.decodeSkinData( loginPacket.getExtra() );
    }

    public boolean isXboxAuthenticated() {
        return this.xboxAuthenticated;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getXuid() {
        return this.xuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public String getLanguageCode() {
        return this.languageCode;
    }

    public String getGameVersion() {
        return this.gameVersion;
    }

    public Skin getSkin() {
        return this.skin;
    }

    private void decodeChainData( List<String> chainData ) {
        List<SignedJWT> signedJWTList = new ArrayList<>();
        for (String data : chainData) {
            try {
                signedJWTList.add(SignedJWT.parse(data));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            this.xboxAuthenticated = validateChain(chainData, Server.getInstance().isOnlineMode());
        } catch ( Exception e ) {
            this.xboxAuthenticated = false;
        }

        for ( SignedJWT chain : signedJWTList ) {
            JsonNode chainMap = decodeToken( chain );
            if ( chainMap == null ) {
                continue;
            }
            if ( chainMap.has( "extraData" ) ) {
                JsonNode extraData = chainMap.get( "extraData" );
                this.displayName = extraData.get( "displayName" ).asText();
                this.uuid = UUID.fromString( extraData.get( "identity" ).asText() );
                this.xuid = extraData.get( "XUID" ).asText();
            }
        }
    }

    private void decodeSkinData( String skinData ) {
        try {
            JsonNode skinMap = decodeToken( SignedJWT.parse(skinData) );
            if ( skinMap.has( "DeviceModel" ) && skinMap.has( "DeviceId" ) &&
                    skinMap.has( "ClientRandomId" ) && skinMap.has( "DeviceOS" ) && skinMap.has( "GuiScale" ) ) {
                String deviceModel = skinMap.get( "DeviceModel" ).asText();
                String deviceId = skinMap.get( "DeviceId" ).asText();
                long clientId = skinMap.get( "ClientRandomId" ).asLong();
                int deviceOS = skinMap.get( "DeviceOS" ).asInt();
                int uiProfile = skinMap.get( "UIProfile" ).asInt();
                this.deviceInfo = new DeviceInfo( deviceModel, deviceId, clientId, Device.getDevice( deviceOS ), UIProfile.getById( uiProfile ) );
            }

            if ( skinMap.has( "LanguageCode" ) ) {
                this.languageCode = skinMap.get( "LanguageCode" ).asText();
            }

            if ( skinMap.has( "GameVersion" ) ) {
                this.gameVersion = skinMap.get( "GameVersion" ).asText();
            }

            this.skin = new Skin();
            if ( skinMap.has( "SkinId" ) ) {
                this.skin.setSkinId( skinMap.get( "SkinId" ).asText() );
            }

            if ( skinMap.has( "SkinResourcePatch" ) ) {
                this.skin.setResourcePatch( new String( Base64.getDecoder().decode( skinMap.get( "SkinResourcePatch" ).asText() ), StandardCharsets.UTF_8 ) );
            }

            if ( skinMap.has( "SkinGeometryData" ) ) {
                this.skin.setGeometryData( new String( Base64.getDecoder().decode( skinMap.get( "SkinGeometryData" ).asText() ), StandardCharsets.UTF_8 ) );
            }

            if ( skinMap.has( "AnimationData" ) ) {
                this.skin.setAnimationData( new String( Base64.getDecoder().decode( skinMap.get( "AnimationData" ).asText() ), StandardCharsets.UTF_8 ) );
            }

            if ( skinMap.has( "CapeId" ) ) {
                this.skin.setCapeId( skinMap.get( "CapeId" ).asText() );
            }

            if ( skinMap.has( "SkinColor" ) ) {
                this.skin.setSkinColor( skinMap.get( "SkinColor" ).asText() );
            }

            if ( skinMap.has( "ArmSize" ) ) {
                this.skin.setArmSize( skinMap.get( "ArmSize" ).asText() );
            }

            if ( skinMap.has( "PlayFabID" ) ) {
                this.skin.setPlayFabId( skinMap.get( "PlayFabID" ).asText() );
            }

            this.skin.setSkinData( this.getImage( skinMap, "Skin" ) );
            this.skin.setCapeData( this.getImage( skinMap, "Cape" ) );

            if ( skinMap.has( "PremiumSkin" ) ) {
                this.skin.setPremium( skinMap.get( "PremiumSkin" ).asBoolean() );
            }

            if ( skinMap.has( "PersonaSkin" ) ) {
                this.skin.setPersona( skinMap.get( "PersonaSkin" ).asBoolean() );
            }

            if ( skinMap.has( "CapeOnClassicSkin" ) ) {
                this.skin.setCapeOnClassic( skinMap.get( "CapeOnClassicSkin" ).asBoolean() );
            }

            if ( skinMap.has( "AnimatedImageData" ) ) {
                JsonNode array = skinMap.get( "AnimatedImageData" );
                for ( JsonNode jsonElement : array ) {
                    this.skin.getSkinAnimations().add( this.getSkinAnimationData( jsonElement ) );
                }
            }

            if ( skinMap.has( "PersonaPieces" ) ) {
                for ( JsonNode jsonElement : skinMap.get( "PersonaPieces" ) ) {
                    this.skin.getPersonaPieces().add( this.getPersonaPiece( jsonElement ) );
                }
            }

            if ( skinMap.has( "PieceTintColors" ) ) {
                for ( JsonNode jsonElement : skinMap.get( "PieceTintColors" ) ) {
                    this.skin.getPersonaPieceTints().add( this.getPersonaPieceTint( jsonElement ) );
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private JsonNode decodeToken( SignedJWT token ) {
        try {
            return JSON_MAPPER.readTree( token.getPayload().toBytes() );
        } catch ( IOException e ) {
            throw new IllegalArgumentException( "Invalid token JSON", e );
        }
    }

    private boolean validateChain(List<String> chainArray, boolean strict) throws Exception {
        if (strict && chainArray.size() > 3) {
            // We dont expect larger chain
            return false;
        }

        ECPublicKey lastKey = null;
        boolean authed = false;
        Iterator<String> iterator = chainArray.iterator();
        while(iterator.hasNext()){
            SignedJWT jwt = SignedJWT.parse(iterator.next());

            URI x5u = jwt.getHeader().getX509CertURL();
            if (x5u == null) {
                throw new JOSEException("Key not found");
            }

            ECPublicKey expectedKey = generateKey(jwt.getHeader().getX509CertURL().toString());
            if (lastKey == null) {
                lastKey = expectedKey;
            } else if (strict && !lastKey.equals(expectedKey)) {
                // Make sure the previous key matches the header of the current
                throw new IllegalArgumentException("Key does not match");
            }

            if (!verifyJwt(jwt, lastKey)) {
                if (strict) {
                    throw new JOSEException("Login JWT was not valid");
                }
                return false;
            }

            if (MOJANG_PUBLIC_KEY.equals(lastKey) || MOJANG_PUBLIC_KEY_OLD.equals(lastKey)) {
                authed = true;
            } else if (authed) {
                return !iterator.hasNext();
            }

            JsonObject payload = (JsonObject) JsonParser.parseString(jwt.getPayload().toString());
            Preconditions.checkArgument(payload.has("identityPublicKey"), "IdentityPublicKey node is missing in chain!");
            JsonElement ipkNode = payload.get("identityPublicKey");
            lastKey = generateKey(ipkNode.getAsString());
        }
        return authed;
    }

    private static ECPublicKey generateKey(String b64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return (ECPublicKey) KeyFactory.getInstance("EC").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(b64)));
    }

    private boolean verifyJwt(JWSObject jws, ECPublicKey key) throws JOSEException {
        return jws.verify(new ECDSAVerifier(key));
    }

    private Image getImage( JsonNode skinMap, String name ) {
        if ( skinMap.has( name + "Data" ) ) {
            byte[] skinImage = Base64.getDecoder().decode( skinMap.get( name + "Data" ).asText() );
            if ( skinMap.has( name + "ImageHeight" ) && skinMap.has( name + "ImageWidth" ) ) {
                int width = skinMap.get( name + "ImageWidth" ).asInt();
                int height = skinMap.get( name + "ImageHeight" ).asInt();
                return new Image( width, height, skinImage );
            } else {
                return Image.getImage( skinImage );
            }
        }
        return new Image( 0, 0, new byte[0] );
    }

    private SkinAnimation getSkinAnimationData( JsonNode animationData ) {
        byte[] data = Base64.getDecoder().decode( animationData.get( "Image" ).asText() );
        int width = animationData.get( "ImageWidth" ).asInt();
        int height = animationData.get( "ImageHeight" ).asInt();
        float frames = animationData.get( "Frames" ).floatValue();
        int type = animationData.get( "Type" ).asInt();
        int expression = animationData.get( "AnimationExpression" ).asInt();
        return new SkinAnimation( new Image( width, height, data ), type, frames, expression );
    }

    private PersonaPiece getPersonaPiece( JsonNode personaPiece ) {
        String pieceId = personaPiece.get( "PieceId" ).asText();
        String pieceType = personaPiece.get( "PieceType" ).asText();
        String packId = personaPiece.get( "PackId" ).asText();
        String productId = personaPiece.get( "ProductId" ).asText();
        boolean isDefault = personaPiece.get( "IsDefault" ).asBoolean();
        return new PersonaPiece( pieceId, pieceType, packId, productId, isDefault );
    }

    private PersonaPieceTint getPersonaPieceTint( JsonNode personaPiceTint ) {
        String pieceType = personaPiceTint.get( "PieceType" ).asText();
        List<String> colors = new ArrayList<>();
        for ( JsonNode element : personaPiceTint.get( "Colors" ) ) {
            colors.add( element.textValue() );
        }
        return new PersonaPieceTint( pieceType, colors );
    }

}
