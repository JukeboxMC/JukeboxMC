package org.jukeboxmc.scheduler;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SchedulerFuture<V> implements Runnable, Future<V> {

    public static <V> SchedulerFuture<V> completed(Scheduler scheduler, V value) {
        return new SchedulerFuture<>(scheduler, value);
    }

    public static <V> SchedulerFuture<V> supplyAsync(Scheduler scheduler, Callable<V> callable) {
        final SchedulerFuture<V> future = new SchedulerFuture<>(scheduler, callable);
        scheduler.executeAsync(future);
        return future;
    }

    private final Scheduler scheduler;
    private final Callable<V> callable;
    private final Set<BiConsumer<V, Throwable>> consumers;
    private final Object waiting;

    private volatile Thread runner;
    private volatile V value;
    private volatile Throwable throwable;
    private volatile boolean finished;
    private volatile boolean cancelled;

    public SchedulerFuture(Scheduler scheduler, V value) {
        this.scheduler = scheduler;
        this.callable = null;
        this.consumers = null;
        this.waiting = null;

        this.runner = Thread.currentThread();
        this.value = value;
        this.finished = true;
        this.cancelled = false;
    }

    public SchedulerFuture(Scheduler scheduler, Callable<V> callable) {
        this.scheduler = scheduler;
        this.callable = callable;
        this.consumers = new LinkedHashSet<>();
        this.waiting = new Object();

        this.finished = false;
        this.cancelled = false;
    }

    @Override
    public void run() {
        if(this.runner != null) return;
        this.runner = Thread.currentThread();

        try {
            this.value = this.callable.call();
        } catch(Throwable throwable) {
            this.throwable = throwable;
        } finally {
            this.finished = true;

            synchronized(this.waiting) {
                this.waiting.notifyAll();
            }

            this.scheduler.execute(() -> {
                for(BiConsumer<V, Throwable> consumer : this.consumers)
                    consumer.accept(this.value, this.throwable);

                this.consumers.clear();
            });
        }
    }

    public SchedulerFuture<V> forceRun() {
        if(this.callable == null || this.finished)
            throw new IllegalStateException("Cannot force run a finished future");
        this.run();
        return this;
    }

    public synchronized SchedulerFuture<V> onFinish(BiConsumer<V, Throwable> consumer) {
        this.scheduler.execute(() -> {
            if(this.finished) consumer.accept(this.value, this.throwable);
            else this.consumers.add(consumer);
        });

        return this;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if(this.finished) return false;
        if(this.runner == null) return false;

        try {
            if(mayInterruptIfRunning) {
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
    public V get() throws InterruptedException, ExecutionException {
        if(this.finished) {
            if(this.throwable != null) throw new ExecutionException(this.throwable);
            return this.value;
        }

        synchronized(this.waiting) {
            this.waiting.wait();

            if(this.throwable != null) throw new ExecutionException(this.throwable);
            return this.value;
        }
    }

    @Override
    public V get(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if(this.finished) {
            if(this.throwable != null) throw new ExecutionException(this.throwable);
            return this.value;
        }

        synchronized(this.waiting) {
            this.waiting.wait(timeUnit.toMillis(timeout));

            if(!this.finished) throw new TimeoutException();

            if(this.throwable != null) throw new ExecutionException(this.throwable);
            return this.value;
        }
    }

}
