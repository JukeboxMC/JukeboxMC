package org.jukeboxmc.server.network

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockServerInitializer
import org.jukeboxmc.server.JukeboxServer
import java.util.Objects

abstract class BedrockServerExceptionInitializer : BedrockServerInitializer() {

    @Deprecated("Deprecated in Java", ReplaceWith(
        "JukeboxServer.getInstance().getLogger().error(cause?.message)",
        "org.jukeboxmc.server.JukeboxServer"
    )
    )
    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        JukeboxServer.getInstance().getLogger().error(cause?.message)
        //super.exceptionCaught(ctx, cause)
    }
}