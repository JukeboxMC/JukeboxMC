package org.jukeboxmc.server.console

import com.google.common.util.concurrent.ThreadFactoryBuilder
import net.minecrell.terminalconsole.SimpleTerminalConsole
import org.jline.reader.LineReader
import org.jline.reader.LineReaderBuilder
import org.jukeboxmc.server.JukeboxServer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TerminalConsole : SimpleTerminalConsole() {

    private val executor: ExecutorService =
        Executors.newSingleThreadExecutor(ThreadFactoryBuilder().setNameFormat("JukeboxMC Terminal Console").build())

    private val server: JukeboxServer = JukeboxServer.getInstance()

    override fun runCommand(command: String?) {
        if (command == null) return
        if (command.equals("exception", true)) {
            println("JukeboxMC-Main Exception")
            val exception = Exception()
            exception.stackTrace = this.server.getMainThread().stackTrace
            exception.printStackTrace(System.out)
        } else {
            this.server.getScheduler().execute {
                this.server.dispatchCommand(this.server.getConsoleSender(), command)
            }
        }
    }

    override fun isRunning(): Boolean {
        return true
    }

    override fun shutdown() {
        this.executor.shutdown()
    }

    override fun buildReader(builder: LineReaderBuilder): LineReader? {
        builder.completer(CommandCompleter())
        builder.appName("JukeboxMC")
        builder.option(LineReader.Option.HISTORY_BEEP, false)
        builder.option(LineReader.Option.HISTORY_IGNORE_DUPS, true)
        builder.option(LineReader.Option.HISTORY_IGNORE_SPACE, true)
        return super.buildReader(builder)
    }

    fun startConsole() {
        this.executor.execute { this.start() }
    }

    fun stopConsole() {
        this.shutdown()
    }
}