package org.jukeboxmc.server.network

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption
import org.cloudburstmc.protocol.bedrock.BedrockPong
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec
import org.cloudburstmc.protocol.bedrock.codec.v712.Bedrock_v712
import org.cloudburstmc.protocol.bedrock.data.EncodingSettings
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockServerInitializer
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.player.JukeboxPlayer
import java.net.InetSocketAddress

class BedrockServer(private val bindAddress: InetSocketAddress, private val server: JukeboxServer) {

    private var bedrockPong: BedrockPong = BedrockPong()
    private lateinit var channel: Channel

    companion object {
        val BEDROCK_CODEC: BedrockCodec = Bedrock_v712.CODEC
    }

    private val encodingSettings = EncodingSettings.builder()
        .maxListSize(Int.MAX_VALUE)
        .maxByteArraySize(Int.MAX_VALUE)
        .maxNetworkNBTSize(Int.MAX_VALUE)
        .maxItemNBTSize(Int.MAX_VALUE)
        .maxStringLength(Int.MAX_VALUE)
        .build()

    init {
        this.bedrockPong.edition("MCPE")
        this.bedrockPong.gameType(this.server.getDefaultWorld().getWorldData().gameType.identifier)
        this.bedrockPong.motd(this.server.getMotd())
        this.bedrockPong.subMotd(this.server.getSubMotd())
        this.bedrockPong.playerCount(this.server.getOnlinePlayers().size)
        this.bedrockPong.maximumPlayerCount(this.server.getMaxPlayers())
        this.bedrockPong.ipv4Port(this.bindAddress.port)
        this.bedrockPong.nintendoLimited(false)
        this.bedrockPong.protocolVersion(BEDROCK_CODEC.protocolVersion)
        this.bedrockPong.version(BEDROCK_CODEC.minecraftVersion)
    }

    fun bind() {
        this.channel = ServerBootstrap()
            .channelFactory(RakChannelFactory.server(NioDatagramChannel::class.java))
            .option(RakChannelOption.RAK_ADVERTISEMENT, this.bedrockPong.toByteBuf())
            .group(NioEventLoopGroup())
            .childHandler(object : BedrockServerInitializer() {
                override fun initSession(session: BedrockServerSession) {
                    session.codec = BEDROCK_CODEC
                    session.peer.codecHelper.encodingSettings = encodingSettings
                    server.addJukeboxPlayer(JukeboxPlayer(server, session))
                }
            })
            .bind(this.bindAddress)
            .syncUninterruptibly().channel()
        this.server.getLogger()
            .info("Server started successfully at " + bindAddress.hostString + ":" + bindAddress.port + "!")
    }

    fun updateBedrockPong() {
        this.bedrockPong.edition("MCPE")
        this.bedrockPong.gameType(this.server.getDefaultWorld().getWorldData().gameType.identifier)
        this.bedrockPong.motd(this.server.getMotd())
        this.bedrockPong.subMotd(this.server.getSubMotd())
        this.bedrockPong.playerCount(this.server.getOnlinePlayers().size)
        this.bedrockPong.maximumPlayerCount(this.server.getMaxPlayers())
        this.bedrockPong.ipv4Port(this.bindAddress.port)
        this.bedrockPong.nintendoLimited(false)
        this.bedrockPong.protocolVersion(BEDROCK_CODEC.protocolVersion)
        this.bedrockPong.version(BEDROCK_CODEC.minecraftVersion)
        channel.config().setOption(RakChannelOption.RAK_ADVERTISEMENT, bedrockPong.toByteBuf())
    }

    fun close() {
        this.channel.close().awaitUninterruptibly()
    }

}