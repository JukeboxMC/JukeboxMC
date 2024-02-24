package org.jukeboxmc.server.player

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.crypto.ECDSAVerifier
import com.nimbusds.jwt.SignedJWT
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket
import org.cloudburstmc.protocol.common.util.Preconditions
import org.jukeboxmc.api.player.info.Device
import org.jukeboxmc.api.player.info.DeviceInfo
import org.jukeboxmc.api.player.info.UIProfile
import org.jukeboxmc.api.player.skin.*
import org.jukeboxmc.server.JukeboxServer
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.interfaces.ECPublicKey
import java.security.spec.X509EncodedKeySpec
import java.text.ParseException
import java.util.*
import kotlin.properties.Delegates

class LoginData{

    var xboxAuthenticated by Delegates.notNull<Boolean>()
    lateinit var displayName: String
    lateinit var xuid: String
    lateinit var uuid: UUID
    lateinit var deviceInfo: DeviceInfo
    lateinit var languageCode: String
    lateinit var gameVersion: String
    lateinit var skin: Skin
    lateinit var identityPublicKey: ECPublicKey

    private val jsonMapper = JsonMapper.builder().build()
    private var mojangPublicKeyOld: ECPublicKey? = null
    private var mojangPublicKey: ECPublicKey? = null

    init {
        mojangPublicKeyOld =
            this.generateKey("MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkixyLcwlZryUQcu1TvPOmI2B7vX83ndnWRUaXm74wFfa5f/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFSNBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V")
        mojangPublicKey =
            this.generateKey("MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAECRXueJeTDqNRRgJi/vlRufByu/2G0i2Ebt6YMar5QX/R0DIIyrJMcUpruK4QveTfJSTp3Shlq4Gk34cD/4GUWwkv0DVuzeuB+tXija7HBxii03NHDbPAD0AKnLr2wdAp")
    }

    fun fromLoginPacket(loginPacket: LoginPacket): LoginData {
        decodeChainData(loginPacket.chain)
        decodeSkinData(loginPacket.extra)
        return this
    }

    private fun decodeChainData(chainData: List<String>) {
        val signedJWTList: MutableList<SignedJWT> = ArrayList()
        for (data in chainData) {
            try {
                signedJWTList.add(SignedJWT.parse(data))
            } catch (e: ParseException) {
                throw RuntimeException(e)
            }
        }
        xboxAuthenticated = try {
            validateChain(chainData, JukeboxServer.getInstance().isOnlineMode())
        } catch (e: Exception) {
            false
        }
        for (chain in signedJWTList) {
            val chainMap = decodeToken(chain) ?: continue
            if (chainMap.has("extraData")) {
                val extraData = chainMap["extraData"]
                displayName = extraData["displayName"].asText()
                uuid = UUID.fromString(extraData["identity"].asText())
                xuid = extraData["XUID"].asText()

                identityPublicKey = this.generateKey((chain.payload.toJSONObject())["identityPublicKey"] as String)
            }
        }
    }

    private fun decodeSkinData(skinData: String) {
        val skinMap = decodeToken(SignedJWT.parse(skinData))
        if (skinMap!!.has("DeviceModel") && skinMap.has("DeviceId") &&
            skinMap.has("ClientRandomId") && skinMap.has("DeviceOS") && skinMap.has("GuiScale")
        ) {
            val deviceModel = skinMap["DeviceModel"].asText()
            val deviceId = skinMap["DeviceId"].asText()
            val clientId = skinMap["ClientRandomId"].asLong()
            val deviceOS = skinMap["DeviceOS"].asInt()
            val uiProfile = skinMap["UIProfile"].asInt()
            deviceInfo = DeviceInfo(
                deviceModel,
                deviceId,
                clientId,
                Device.getDevice(deviceOS)!!,
                UIProfile.getById(uiProfile)!!
            )
        }
        if (skinMap.has("LanguageCode")) {
            languageCode = skinMap["LanguageCode"].asText()
        }
        if (skinMap.has("GameVersion")) {
            gameVersion = skinMap["GameVersion"].asText()
        }
        skin = Skin()
        if (skinMap.has("SkinId")) {
            skin.setSkinId(skinMap["SkinId"].asText())
        }
        if (skinMap.has("SkinResourcePatch")) {
            skin.setResourcePatch(
                String(
                    Base64.getDecoder().decode(skinMap["SkinResourcePatch"].asText()),
                    StandardCharsets.UTF_8
                )
            )
        }
        if (skinMap.has("SkinGeometryData")) {
            skin.setGeometryData(
                String(
                    Base64.getDecoder().decode(skinMap["SkinGeometryData"].asText()),
                    StandardCharsets.UTF_8
                )
            )
        }
        if (skinMap.has("AnimationData")) {
            skin.setAnimationData(
                String(
                    Base64.getDecoder().decode(skinMap["AnimationData"].asText()),
                    StandardCharsets.UTF_8
                )
            )
        }
        if (skinMap.has("CapeId")) {
            skin.setCapeId(skinMap["CapeId"].asText())
        }
        if (skinMap.has("SkinColor")) {
            skin.setSkinColor(skinMap["SkinColor"].asText())
        }
        if (skinMap.has("ArmSize")) {
            skin.setArmSize(skinMap["ArmSize"].asText())
        }
        if (skinMap.has("PlayFabID")) {
            skin.setPlayFabId(skinMap["PlayFabID"].asText())
        }
        skin.setSkinData(getImage(skinMap, "Skin")!!)
        skin.setCapeData(getImage(skinMap, "Cape")!!)
        if (skinMap.has("PremiumSkin")) {
            skin.setPremium(skinMap["PremiumSkin"].asBoolean())
        }
        if (skinMap.has("PersonaSkin")) {
            skin.setPersona(skinMap["PersonaSkin"].asBoolean())
        }
        if (skinMap.has("CapeOnClassicSkin")) {
            skin.setCapeOnClassic(skinMap["CapeOnClassicSkin"].asBoolean())
        }
        if (skinMap.has("AnimatedImageData")) {
            val array = skinMap["AnimatedImageData"]
            for (jsonElement in array) {
                skin.getSkinAnimations().add(getSkinAnimationData(jsonElement))
            }
        }
        if (skinMap.has("PersonaPieces")) {
            for (jsonElement in skinMap["PersonaPieces"]) {
                skin.getPersonaPieces().add(getPersonaPiece(jsonElement))
            }
        }
        if (skinMap.has("PieceTintColors")) {
            for (jsonElement in skinMap["PieceTintColors"]) {
                skin.getPersonaPieceTints().add(getPersonaPieceTint(jsonElement))
            }
        }
    }

    private fun decodeToken(token: SignedJWT): JsonNode? {
        return jsonMapper.readTree(token.payload.toBytes())
    }

    private fun validateChain(chainArray: List<String>, strict: Boolean): Boolean {
        if (strict && chainArray.size > 3) {
            return false
        }
        var lastKey: ECPublicKey? = null
        var authed = false
        val iterator = chainArray.iterator()
        while (iterator.hasNext()) {
            val jwt = SignedJWT.parse(iterator.next())
            val x5u = jwt.header.x509CertURL ?: throw JOSEException("Key not found")
            val expectedKey: ECPublicKey = generateKey(x5u.toString())
            if (lastKey == null) {
                lastKey = expectedKey
            } else require(!(strict && lastKey != expectedKey)) {
                "Key does not match"
            }
            if (!verifyJwt(jwt, lastKey)) {
                if (strict) {
                    throw JOSEException("Login JWT was not valid")
                }
                return false
            }
            if (mojangPublicKey == lastKey || mojangPublicKeyOld == lastKey) {
                authed = true
            } else if (authed) {
                return !iterator.hasNext()
            }
            val payload = JsonParser.parseString(jwt.payload.toString()) as JsonObject
            Preconditions.checkArgument(payload.has("identityPublicKey"), "IdentityPublicKey node is missing in chain!")
            val ipkNode = payload["identityPublicKey"]
            lastKey = generateKey(ipkNode.asString)
        }
        return authed
    }

    private fun verifyJwt(jws: JWSObject, key: ECPublicKey?): Boolean {
        return jws.verify(ECDSAVerifier(key))
    }

    private fun getImage(skinMap: JsonNode, name: String): SkinImage? {
        if (skinMap.has(name + "Data")) {
            val skinImage = Base64.getDecoder().decode(skinMap[name + "Data"].asText())
            return if (skinMap.has(name + "ImageHeight") && skinMap.has(name + "ImageWidth")) {
                val width = skinMap[name + "ImageWidth"].asInt()
                val height = skinMap[name + "ImageHeight"].asInt()
                SkinImage(width, height, skinImage)
            } else {
                SkinImage.getImage(skinImage)
            }
        }
        return SkinImage(0, 0, ByteArray(0))
    }

    private fun getSkinAnimationData(animationData: JsonNode): SkinAnimation {
        val data = Base64.getDecoder().decode(animationData["Image"].asText())
        val width = animationData["ImageWidth"].asInt()
        val height = animationData["ImageHeight"].asInt()
        val frames = animationData["Frames"].floatValue()
        val type = animationData["Type"].asInt()
        val expression = animationData["AnimationExpression"].asInt()
        return SkinAnimation(SkinImage(width, height, data), type, frames, expression)
    }

    private fun getPersonaPiece(personaPiece: JsonNode): PersonaPiece {
        val pieceId = personaPiece["PieceId"].asText()
        val pieceType = personaPiece["PieceType"].asText()
        val packId = personaPiece["PackId"].asText()
        val productId = personaPiece["ProductId"].asText()
        val isDefault = personaPiece["IsDefault"].asBoolean()
        return PersonaPiece(pieceId, pieceType, packId, productId, isDefault)
    }

    private fun getPersonaPieceTint(personaPiceTint: JsonNode): PersonaPieceTint {
        val pieceType = personaPiceTint["PieceType"].asText()
        val colors: MutableList<String> = ArrayList()
        for (element in personaPiceTint["Colors"]) {
            colors.add(element.textValue())
        }
        return PersonaPieceTint(pieceType, colors)
    }

    private fun generateKey(b64: String): ECPublicKey {
        return KeyFactory.getInstance("EC")
            .generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(b64))) as ECPublicKey
    }

    override fun toString(): String {
        return "LoginData(displayName='$displayName', xuid='$xuid', uuid=$uuid, deviceInfo=$deviceInfo, languageCode='$languageCode', gameVersion='$gameVersion', skin=$skin, jsonMapper=$jsonMapper, mojangPublicKeyOld=$mojangPublicKeyOld, mojangPublicKey=$mojangPublicKey)"
    }


}