package org.jukeboxmc.world.generator.terra;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jukeboxmc.Bootstrap;
import org.jukeboxmc.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public final class BiomeLegacyId2StringIdMap {
    public static final BiomeLegacyId2StringIdMap INSTANCE = new BiomeLegacyId2StringIdMap();

    private final Int2ObjectOpenHashMap<String> legacy2StringMap = new Int2ObjectOpenHashMap<>(82);
    private final Object2IntOpenHashMap<String> string2LegacyMap = new Object2IntOpenHashMap<>(82);

    private BiomeLegacyId2StringIdMap() {
        try (final InputStream inputStream = Bootstrap.class.getClassLoader().getResourceAsStream("jeBiomes.json")) {
            if (inputStream == null) {
                Server.getInstance().getLogger().error( "Could not find java edition biomes!" );
            } else {
                final JsonObject biomeIdMap = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
                for (final Map.Entry<String, JsonElement> entry : biomeIdMap.entrySet()) {
                    final int id = entry.getValue().getAsInt();
                    legacy2StringMap.put(id, entry.getKey());
                    string2LegacyMap.put(entry.getKey(), id);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String legacy2String(int legacyBiomeID) {
        return legacy2StringMap.get(legacyBiomeID);
    }

    public int string2Legacy(String stringBiomeID) {
        return string2LegacyMap.getOrDefault(stringBiomeID, -1);
    }
}
