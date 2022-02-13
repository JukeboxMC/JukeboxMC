package org.jukeboxmc.world.chunk;

import lombok.Getter;
import org.jukeboxmc.scheduler.Scheduler;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * @author Kevims
 * @version 1.0
 */
public class ChunkFuture implements Runnable, Future<Chunk> {

    public static ChunkFuture completed( Scheduler scheduler, Chunk chunk ) {
        return new ChunkFuture( scheduler, chunk );
    }

    private final Scheduler scheduler;
    private final Callable<Chunk> callable;
    private final Set<BiConsumer<Chunk, Throwable>> consumers;
    private final Object waiting;

    private volatile Thread runner;
    private volatile Chunk value;
    private volatile Throwable throwable;
    private volatile boolean finished;
    private volatile boolean cancelled;

    public ChunkFuture( Scheduler scheduler, Chunk value ) {
        this.scheduler = scheduler;
        this.callable = null;
        this.consumers = null;
        this.waiting = null;

        this.runner = Thread.currentThread();
        this.value = value;
        this.finished = true;
        this.cancelled = false;
    }

    public ChunkFuture( Scheduler scheduler, Callable<Chunk> callable ) {
        this.scheduler = scheduler;
        this.callable = callable;
        this.consumers = new LinkedHashSet<>();
        this.waiting = new Object();

        this.finished = false;
        this.cancelled = false;
    }

    @Override
    public void run() {
        if ( this.runner != null ) {
            return;
        }

        this.runner = Thread.currentThread();

        try {
            this.value = this.callable.call();
        } catch ( Throwable throwable ) {
            this.throwable = throwable;
        } finally {
            this.finished = true;

            synchronized (this.waiting) {
                this.waiting.notifyAll();
            }

            this.scheduler.addTask( () -> {
                for ( BiConsumer<Chunk, Throwable> consumer : this.consumers ) {
                    consumer.accept( this.value, this.throwable );
                }

                this.consumers.clear();
            }, 0, 0, false );
        }
    }

    public synchronized ChunkFuture onFinish( BiConsumer<Chunk, Throwable> consumer ) {
        this.scheduler.addTask( () -> {
            if ( this.finished ) {
                consumer.accept( this.value, this.throwable );
            } else {
                this.consumers.add( consumer );
            }
        }, 0, 0, false );

        return this;
    }

    @Override
    public boolean cancel( boolean mayInterruptIfRunning ) {
        if ( this.finished || this.runner == null ) {
            return false;
        }

        try {
            if ( mayInterruptIfRunning ) {
                this.runner.interrupt();
                return true;
            }

            return false;
        } finally {
            this.cancelled = true;
        }
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public boolean isDone() {
        return this.finished;
    }

    @Override
    public Chunk get() throws InterruptedException, ExecutionException {
        if ( this.finished ) {
            if ( this.throwable != null ) {
                throw new ExecutionException( this.throwable );
            }

            return this.value;
        }

        synchronized (this.waiting) {
            this.waiting.wait();

            if ( this.throwable != null ) {
                throw new ExecutionException( this.throwable );
            }

            return this.value;
        }
    }

    @Override
    public Chunk get( long timeout, TimeUnit unit ) throws InterruptedException, ExecutionException, TimeoutException {
        if ( this.finished ) {
            if ( this.throwable != null ) {
                throw new ExecutionException( this.throwable );
            }

            return this.value;
        }

        synchronized (this.waiting) {
            this.waiting.wait( unit.toMillis( timeout ) );

            if ( !this.finished ) {
                throw new TimeoutException();
            }

            if ( this.throwable != null ) {
                throw new ExecutionException( this.throwable );
            }

            return this.value;
        }
    }

}
