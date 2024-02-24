package org.jukeboxmc.api

class JukeboxMC {

    companion object {
        private var server : Server? = null

        fun setServer(server: Server) {
            if(JukeboxMC.server == null) {
                this.server = server
                return
            }
            throw UnsupportedOperationException("Cannot redefine singleton server")
        }

        fun getServer() : Server {
            return server!!
        }
    }
}