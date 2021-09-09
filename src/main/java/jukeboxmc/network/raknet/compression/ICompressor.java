package jukeboxmc.network.raknet.compression;

import io.netty.buffer.ByteBuf;

import java.io.Closeable;
import java.util.zip.DataFormatException;

/**
 * @author Kaooot, LucGamesYT
 * @version 1.0
 */
public interface ICompressor extends Closeable {

    void process(final ByteBuf inputBuffer, final ByteBuf outputBuffer) throws DataFormatException;
}