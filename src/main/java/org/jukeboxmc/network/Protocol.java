package org.jukeboxmc.network;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public interface Protocol {

    int PROTOCOL = 440;
    String MINECRAFT_VERSION = "1.17.0";

    //Minecraft
    int BATCH_PACKET = 0xfe;
    byte LOGIN_PACKET = 0x01;
    byte PLAY_STATUS_PACKET = 0x02;
    byte RESOURCE_PACKS_INFO_PACKET = 0x06;
    byte RESOURCE_PACK_STACK_PACKET = 0x07;
    byte RESOURCE_PACK_RESPONSE_PACKET = 0x08;
    byte INCOMPATIBLE_PROTOCOL_VERSION = 0x19;
    byte START_GAME_PACKET = 0x0b;
    int CREATIVE_CONTENT_PACKET = 0x91;
    byte BIOME_DEFINITION_LIST_PACKET = 0x7A;
    byte REQUEST_CHUNK_RADIUS_PACKET = 0x45;
    byte CHUNK_RADIUS_UPDATE_PACKET = 0x46;
    byte NETWORK_CHUNK_PUBLISHER_UPDATE_PACKET = 0x79;
    byte DISCONNECT_PACKET = 0x05;
    byte PLAYER_MOVE_PACKET = 0x13;
    byte TICK_SYNC_PACKET = 0x17;
    byte INTERACT_PACKET = 0x21;
    int EMOTE_LIST_PACKET = 0x98;
    byte SET_LOCAL_PLAYER_AS_INITIALIZED_PACKET = 0x71;
    byte LEVEL_CHUNK_PACKET = 0x3a;
    byte SET_TIME_PACKET = 0x0a;
    byte AVAILABLE_ENTITY_IDENTIFIERS_PACKET = 0x77;
    int CLIENT_CACHE_STATUS_PACKET = 0x81;
    byte UPDATE_ATTRIBUTES_PACKET = 0x1d;
    byte SET_ENTITY_DATA_PACKET = 0x27;
    byte ADVENTURER_SETTINGS_PACKET = 0x37;
    byte LEVEL_SOUND_EVENT_PACKET = 0x7b;
    byte PLAY_SOUND_PACKET = 0x56;
    byte UPDATE_BLOCK_PACKET = 0x15;
    byte TEXT_PACKET = 0x09;
    byte CONTAINER_OPEN_PACKET = 0x2e;
    byte PLAYER_ACTION_PACKET = 0x24;
    byte ANIMATE_PACKET = 0x2c;
    byte INVENTORY_TRANSACTION_PACKET = 0x1e;
    byte CONTAINER_CLOSE_PACKET = 0x2f;
    byte INVENTORY_CONTENT_PACKET = 0x31;
    byte MOB_EQUIPMENT_PACKET = 0x1f;
    byte INVENTORY_SLOT_PACKET = 0x32;
    byte ADD_ENTITY_PACKET = 0x0d;
    byte PLAYER_LIST_PACKET = 0x3f;
    byte ADD_PLAYER_PACKET = 0x0c;
    byte REMOVE_ENTITY_PACKET = 0x0e;
    byte LEVEL_EVENT_PACKET = 0x19;
    byte BLOCK_PICK_REQUEST_PACKET = 0x22;
    byte BLOCK_ENTITY_DATA_PACKET = 0x38;
    byte SET_DIFFICULTY_PACKET = 0x3c;
    byte SPAWN_PARTICLE_EFFECT_PACKET = 0x76;
    byte CHANGE_DIMENSION_PACKET = 0x3d;

    //Raknet
    byte CONNECTED_PING = 0x00;
    byte UNCONNECTED_PING = 0x01;
    byte UNCONNECTED_PONG = 0x1c;
    byte CONNECTED_PONG = 0x03;
    byte OPEN_CONNECTION_REQUEST_1 = 0x05;
    byte OPEN_CONNECTION_REPLY_1 = 0x06;
    byte OPEN_CONNECTION_REQUEST_2 = 0x07;
    byte OPEN_CONNECTION_REPLY_2 = 0x08;
    byte CONNECTION_REQUEST = 0x09;
    byte CONNECTION_REQUEST_ACCEPTED = 0x10;
    byte NEW_INCOMING_CONNECTION = 0x13;
    byte DISCONNECT_NOTIFICATION = 0x15;

    byte ACKNOWLEDGE_PACKET = (byte) 0xc0;
    byte NACKNOWLEDGE_PACKET = (byte) 0xa0;
    byte QUERY = (byte) 0xfe;
}
