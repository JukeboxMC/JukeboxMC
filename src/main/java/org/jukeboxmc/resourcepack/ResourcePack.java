package org.jukeboxmc.resourcepack;

import lombok.ToString;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Kaooot
 * @version 1.0
 */
@ToString(exclude = {"file"})
public class ResourcePack {

    private final File file;

    private final String name;
    private final String uuid;
    private final String version;
    private final long size;
    private final byte[] sha256;

    private byte[] chunk;

    public ResourcePack(final File file, final String name, final String uuid, final String version, final long size, final byte[] sha256, final byte[] chunk) {
        this.file = file;
        this.name = name;
        this.uuid = uuid;
        this.version = version;
        this.size = size;
        this.sha256 = sha256;
        this.chunk = chunk;
    }

    public String getName() {
        return this.name;
    }

    public UUID getUuid() {
        return UUID.fromString( this.uuid );
    }

    public String getVersion() {
        return this.version;
    }

    public long getSize() {
        return this.size;
    }

    public byte[] getSha256() {
        return this.sha256;
    }

    public byte[] getChunk(final int offset, final int length) {
        if ((this.size - offset) > length) {
            this.chunk = new byte[length];
        } else {
            this.chunk = new byte[(int) (this.size - offset)];
        }

        try (final FileInputStream fileInputStream = new FileInputStream(this.file)) {
            fileInputStream.skip(offset);
            fileInputStream.read(this.chunk);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return this.chunk;
    }
}