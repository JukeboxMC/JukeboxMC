package org.jukeboxmc.form;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author GoMint
 * @version 1.0
 */
@Getter
public class FormListener<R> {

    private Consumer<R> responseConsumer = r -> { };
    private Consumer<Void> closeConsumer = aVoid -> { };

    public @NotNull FormListener<R> onResponse(Consumer<R> consumer ) {
        this.responseConsumer = consumer;
        return this;
    }

    public @NotNull FormListener<R> onClose(Consumer<Void> consumer ) {
        this.closeConsumer = consumer;
        return this;
    }

}