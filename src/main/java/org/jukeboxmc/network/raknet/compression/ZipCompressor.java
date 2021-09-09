package org.jukeboxmc.network.raknet.compression;

import io.netty.buffer.ByteBuf;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author LucGamesYT, Kaooot
 * @version 1.0
 */
public class ZipCompressor implements ICompressor {

    private final byte[] buffer = new byte[8192];
    private final boolean compress;
    private final Deflater deflater;
    private final Inflater inflater;

    public ZipCompressor(final boolean compress, final int level, final boolean raw) {
        this.compress = compress;

        if (compress) {
            this.deflater = new Deflater(level, raw);
            this.inflater = null;
        } else {
            this.deflater = null;
            this.inflater = new Inflater(raw);
        }
    }

    @Override
    public void process(final ByteBuf inputBuffer, final ByteBuf outputBuffer) throws DataFormatException {
        final byte[] inData = new byte[inputBuffer.readableBytes()];
        inputBuffer.readBytes(inData);

        if (this.compress && this.deflater != null) {
            this.deflater.setInput(inData);
            this.deflater.finish();

            while (!this.deflater.finished()) {
                outputBuffer.writeBytes(this.buffer, 0, this.deflater.deflate(this.buffer));
            }

            this.deflater.reset();
        } else {
            if (this.inflater != null) {
                this.inflater.setInput(inData);

                while (!this.inflater.finished() && this.inflater.getTotalIn() < inData.length) {
                    outputBuffer.writeBytes(this.buffer, 0, this.inflater.inflate(this.buffer));
                }

                this.inflater.reset();
            }
        }
    }

    @Override
    public void close() {
        if (this.compress && this.deflater != null) {
            this.deflater.end();
        } else {
            if (this.inflater != null) {
                this.inflater.end();
            }
        }
    }
}