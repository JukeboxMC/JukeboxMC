package org.jukeboxmc;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.console.ConsoleSender;
import org.jukeboxmc.logger.Logger;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.plugin.PluginManager;
import org.jukeboxmc.scheduler.Scheduler;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.generator.Generator;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class JukeboxMC {

    private static Server server;

    public static void setServer( Server server ) {
        JukeboxMC.server = server;
    }

    public static Logger getLogger() {
        return server.getLogger();
    }

    public static Scheduler getScheduler() {
        return server.getScheduler();
    }

    public static World getDefaultWorld() {
        return server.getDefaultWorld();
    }

    public static Collection<World> getWorlds() {
        return server.getWorlds();
    }

    public static World getWorld( String name ) {
        return server.getWorld( name );
    }

    public static World loadWorld( String name ) {
        return server.loadWorld( name );
    }

    public static World loadWorld(String name, @NotNull Map<Dimension, String> generatorMap ) {
        return server.loadWorld( name, generatorMap );
    }

    public static void registerWorldGenerator( String name, Class<? extends Generator> clazz, Dimension... dimensions ) {
        server.registerGenerator( name, clazz, dimensions );
    }

    public static void unloadWorld(@NotNull String name ) {
        server.unloadWorld( name );
    }

    public static void unloadWorld(@NotNull String worldName, @NotNull Consumer<Player> consumer ) {
        server.unloadWorld( worldName, consumer );
    }

    public static boolean isWorldLoaded(@NotNull String name ) {
        return server.isWorldLoaded( name );
    }

    public static Player getPlayer( String name ) {
        return server.getPlayer( name );
    }

    public static Player getPlayer( UUID uuid ) {
        return server.getPlayer( uuid );
    }

    public static int getMaxPlayers() {
        return server.getMaxPlayers();
    }

    public static Collection<Player> getOnlinePlayers() {
        return server.getOnlinePlayers();
    }

    public static PluginManager getPluginManager() {
        return server.getPluginManager();
    }

    public static ConsoleSender getConsoleSender() {
        return server.getConsoleSender();
    }

    public static double getCurrentTps() {
        return server.getCurrentTps();
    }

    public static @NotNull InetSocketAddress getAddress() {
        return new InetSocketAddress( server.getServerAddress(), server.getPort() );
    }

    public static int getPort() {
        return server.getPort();
    }

    public static void dispatchCommand(@NotNull ConsoleSender consoleSender, String command ) {
        server.dispatchCommand( consoleSender, command );
    }

    public static void broadcastMessage( String message ) {
        server.broadcastMessage( message );
    }

}